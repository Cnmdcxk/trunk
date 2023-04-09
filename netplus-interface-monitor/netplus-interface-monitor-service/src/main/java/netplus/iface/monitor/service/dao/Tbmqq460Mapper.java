package netplus.iface.monitor.service.dao;

import netplus.component.entity.data.KeyValue;
import netplus.iface.monitor.api.pojo.db.Tbmqq460;
import netplus.iface.monitor.api.pojo.db.Tbmqq460Key;
import netplus.iface.monitor.api.pojo.db.Tbmqq460WithBLOBs;
import netplus.iface.monitor.api.vo.Tbmqq460Vo;

import java.util.List;
import java.util.Map;

public interface Tbmqq460Mapper {
    int deleteByPrimaryKey(Tbmqq460Key key);

    int insert(Tbmqq460WithBLOBs record);

    int insertSelective(Tbmqq460WithBLOBs record);

    Tbmqq460WithBLOBs selectByPrimaryKey(Tbmqq460Key key);

    int updateByPrimaryKeySelective(Tbmqq460WithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(Tbmqq460WithBLOBs record);

    int updateByPrimaryKey(Tbmqq460 record);

    int getCount(Map<String,Object> mapParam);

    List<KeyValue> getCalleeKV(Map<String,Object> mapParam);

    List<KeyValue> getCallerKV(Map<String,Object> mapParam);

    List<KeyValue> getReqNameKV(Map<String,Object> mapParam);

    List<Tbmqq460Vo> getInterFaceDataList(Map<String, Object> mapParam);

    Tbmqq460Vo selectRespData(String id);

    int deleteInformation(long beforeTime);
}