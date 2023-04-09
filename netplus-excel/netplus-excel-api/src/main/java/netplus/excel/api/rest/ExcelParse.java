package netplus.excel.api.rest;


import netplus.excel.api.pojo.ExcelTempData;
import netplus.excel.api.pojo.ExcelTempMemo;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.utils.excel.entity.EntityWrap;
import netplus.utils.excel.entity.FieldWrap;
import netplus.utils.excel.validation.validations.CellName;
import netplus.utils.object.ObjectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lhp on 2017/6/1.
 * 采购通用处理逻辑
 */
public class ExcelParse {

    private ExcelParse(){}

    public static <T extends EntityWrap> Map<String, Object> errMap(T item) {
        Map<String, Object> fieldWrapMap = ObjectUtil.transBeanToMap(item);
        List<Map<String, Object>> errMegMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<String, Object> entry: fieldWrapMap.entrySet()) {
            Object object = entry.getValue();
            if (object.getClass().equals(FieldWrap.class)) {
                FieldWrap wrap = (FieldWrap) entry.getValue();
                if (!wrap.getIsValid()) {
                    Map<String, Object> eMap = new HashMap<>();
                    eMap.put("valid", false);
                    eMap.put("msg", wrap.getMessage());
                    eMap.put("value", wrap.getValue());
                    errMegMap.add(eMap);
                }

                map.put(entry.getKey(), wrap.getValue());
            }
        }

        if (ObjectUtil.nonEmpty(item.getMessages())) {

            List<String> msgs = errMegMap.stream().map(p->{
                Optional optional = Optional.ofNullable(p.get("msg"));
                return optional.isPresent() ? optional.get().toString():"";
            }).collect(Collectors.toList());

            item.getMessages().forEach(msg->{
                if (!msgs.contains(msg)) {
                    Map<String, Object> eMap = new HashMap<>();
                    eMap.put("valid", false);
                    eMap.put("msg", msg);
                    eMap.put("value", "message");

                    errMegMap.add(eMap);
                }
            });
        }

        map.put("errorMgs", errMegMap);
        return map;
    }

    public static FieldWrap g(Map<String, Object> map, Map<String, ExcelTempMemo> titleMap, String name) {
        ExcelTempMemo entity = titleMap.get(name);
        if (entity == null) {
            return new FieldWrap("");
        }

        String key = entity.getField();
        Object value = map.get(key);
        if (value == null) {
            return new FieldWrap("");
        }
        return new FieldWrap(value.toString());
    }

    public static <T, R> R mkItemVo(T t, Map<String, ExcelTempMemo> titleMap, Class<R> rClass) {
        if (t == null) {
            return null;
        }

        Field[] fields = rClass.getDeclaredFields();
        if (fields == null) {
            return null;
        }

        try {
            R vo = rClass.newInstance();
            Map<String, Object> map = ObjectUtil.transBeanToMap(t);

            for (Field field : fields) {
                if  (field.getType().isAssignableFrom(FieldWrap.class)) {
                    field.setAccessible(true);
                    field.set(vo, g(map, titleMap, field.getName()));
                }
            }
            return vo;

        }catch (Exception e) {
            return null;
        }

    }

    public static Map<String, String> getCellNameMap(Class<? extends EntityWrap> c) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = c.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return map;
        }

        for (Field field: fields) {
            if (!field.getType().isAssignableFrom(FieldWrap.class))
                continue;

            for (Annotation a: field.getDeclaredAnnotations()) {
                if (a.annotationType().isAssignableFrom(CellName.class)) {
                    CellName cellName = (CellName) a;
                    String code = field.getName();
                    for (String name: cellName.name().split("@@")) {
                        map.put(name, code);
                    }
                }
            }
        }

        return map;
    }

    public static <T extends EntityWrap> List<T> parseItems(ExcelDataVo vo, Class<T> tClass) {
        List<T> items = new ArrayList<>();

        Map<String, String> titleMap = getCellNameMap(tClass);

        Map<String, ExcelTempMemo> excelTempMemoEntityMap = new HashMap<>();

        for (ExcelTempMemo entity: vo.getHead()) {

            if (titleMap.containsKey(entity.getValue())) {
                excelTempMemoEntityMap.put(titleMap.get(entity.getValue()), entity);
            }

        }
        for (ExcelTempData entity: vo.getBody()) {
            T itemVo = mkItemVo(entity, excelTempMemoEntityMap, tClass);
            items.add(itemVo);
        }

        return items;
    }

}
