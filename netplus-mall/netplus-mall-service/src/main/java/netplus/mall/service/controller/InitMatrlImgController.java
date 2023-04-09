package netplus.mall.service.controller;

import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.enums.PictureTypeEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq405;
import netplus.mall.api.pojo.ygmalluser.Tbmqq407;
import netplus.mall.api.pojo.ygmalluser.Tbmqq429;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.service.biz.BasicDataBiz;
import netplus.mall.service.biz.MallBiz;
import netplus.mall.service.biz.PicLibBiz;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.NowDate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 物料图片初始化
 */
@RestController
public class InitMatrlImgController {
    protected static final Log logger = LogFactory.getLog(InitMatrlImgController.class);

    private static final String resourcePath="/app/deploy/upload/piclib/resource";
    private static final String fileSavePath="/app/deploy/upload/%s";
//    private static final String resourcePath="D:\\永钢易购\\商品图片整理\\resource";
//    private static final String fileSavePath="D:\\test\\%s";

    private static final String imageUrlPrefixFormat="https://%s/upload/%s";

    private static final String initImageFolderKey="piclib";
    private static final String initBasicData3FolderKey="basicData3";

    private static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    @Autowired
    PicLibBiz picLibBiz;

    @Autowired
    BasicDataBiz basicDataBiz;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Anonymous
    @PostMapping(Urls.InitMatrlImg.initImage)
    public ApiResponse initImage(@RequestBody(required=false) List<String> supplierNumbers){
        String path=String.format(resourcePath,initImageFolderKey);
        String savePath=String.format(fileSavePath,initImageFolderKey);

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_HOST.code);
        String imageUrlPrefix=String.format(imageUrlPrefixFormat,tbmqq461.getVal(),initImageFolderKey);
        File folder=new File(path);
        if(folder.exists()){
            File[] supplierFolders = null;
            if(ObjectUtils.isEmpty(supplierNumbers)){
                supplierFolders = folder.listFiles();
            }else{
                supplierFolders = folder.listFiles((dir,name) -> supplierNumbers.contains(name));
            }
            if(ObjectUtils.isEmpty(supplierFolders)){
                throw new NetPlusException("未找到需上传的源数据!");
            }

            NowDate nowDate = new NowDate();
            for (File supplierFolder : supplierFolders) {
                if(!supplierFolder.isDirectory()){
                    continue;
                }

                File[] matrlFolders = supplierFolder.listFiles();
                for (File matrlFolder : matrlFolders) {
                    if(!matrlFolder.isDirectory()){
                        continue;
                    }

                    File[] files = matrlFolder.listFiles();
                    List<File> images=new ArrayList<>();
                    List<String> matrlTMList=null;
                    for (File file : files) {
                        if(file.isFile()){
                            if(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")){
                                matrlTMList=getMatrlTMList(file);
                            }else{
                                images.add(file);
                            }
                        }
                    }

                    if(ObjectUtils.isEmpty(images)){
                        logger.error(String.format("无图片信息,目录: %s",matrlFolder.getPath()));
                        continue;
                    }
                    if(ObjectUtils.isEmpty(matrlTMList)){
                        logger.error(String.format("无条码信息,目录: %s",matrlFolder.getPath()));
                        continue;
                    }

                    String filePathPrefix = File.separator + supplierFolder.getName()+ File.separator + matrlFolder.getName();

                    for (String matrlTM : matrlTMList) {
                        try {
                            logger.info(String.format("目录: %s ,条码: %s 开始处理!",matrlFolder.getPath(),matrlTM));

                            HashMap<String, Object> reqMap = new HashMap<>();
                            reqMap.put("matrlTm",matrlTM);
                            List<Tbmqq405> infoList = basicDataBiz.getMatrl405No(reqMap);

                            if(ObjectUtils.isEmpty(infoList)){
                                logger.error(String.format("物料条码: %s 未找到,所属目录: %s",matrlTM,matrlFolder.getPath()));
                                continue;
                            }

                            Tbmqq405 info=infoList.get(0);

                            List<Tbmqq429> list=new ArrayList<>();
                            for (File image : images) {

                                String pathEndPart=filePathPrefix + File.separator + matrlTM + "_" + image.getName();

                                File newPath=new File(savePath + pathEndPart);
                                FileUtils.copyFile(image, newPath);
                                String url=imageUrlPrefix+pathEndPart.replace(File.separator,"/");

                                String sort = image.getName().substring(0, image.getName().indexOf("."));

                                Tbmqq429 tbmqq429 = new Tbmqq429();
                                tbmqq429.setPictureurl(url);
                                tbmqq429.setPicturename(String.format("%s_%s", matrlTM, sort));
                                tbmqq429.setPicturenum(sort);
                                tbmqq429.setSupplierno(supplierFolder.getName());
                                tbmqq429.setPicturetype(PictureTypeEnum.MAIN.getCode());
                                tbmqq429.setDeleted("N");
                                tbmqq429.setCreateuser(SysCodeEnum.JOB.code);
                                tbmqq429.setCreatedate(nowDate.getDateStr());
                                tbmqq429.setCreatetime(nowDate.getTimeStr());
                                tbmqq429.setUpdateuser(SysCodeEnum.JOB.code);
                                tbmqq429.setUpdatedate(nowDate.getDateStr());
                                tbmqq429.setUpdatetime(nowDate.getTimeStr());
                                tbmqq429.setMatrlno(info.getMatrlno());
                                tbmqq429.setMatrlid(info.getMatrlid());
                                tbmqq429.setMatrltm(info.getMatrltm());
                                list.add(tbmqq429);
                            }
                            if(list.size()>0){
                                picLibBiz.batchInsert(list);
                                logger.info(String.format("目录: %s ,条码: %s 处理完成!",matrlFolder.getPath(),matrlTM));
                            }
                        }catch (Exception e){
                            logger.error(String.format("目录: %s ,条码: %s 处理失败!",matrlFolder.getPath(),matrlTM),e);
                        }
                    }

                }
            }

        }
        return ApiResponse.success();
    }

    private List<String> getMatrlTMList(File file){
        List<String> list = new ArrayList<>();
        if(!ObjectUtils.isEmpty(file)){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                Workbook wb = null;
                if(file.getName().endsWith(".xls")){
                    wb=new HSSFWorkbook(fis);
                }else if(file.getName().endsWith(".xlsx")){
                    wb=new XSSFWorkbook(fis);
                }

                Sheet sheet = wb.getSheetAt(0);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    String matrlTM = getMatrlTM(sheet.getRow(i));
                    if(!StringUtils.isEmpty(matrlTM) && !p.matcher(matrlTM).find()){
                        list.add(matrlTM);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return list;
    }

    private String getMatrlTM(Row row){
        if(row != null){
            Cell cell = row.getCell(0);
            if(cell != null){

                cell.setCellType(Cell.CELL_TYPE_STRING);

                return cell.getStringCellValue();
            }
        }
        return null;
    }


    @Anonymous
    @PostMapping(Urls.InitMatrlImg.initErrorFormatImg)
    public void initErrorFormatImg(@RequestBody(required=false) List<String> supplierNumbers){
        String path=String.format(resourcePath,initImageFolderKey);
        String savePath=String.format(fileSavePath,initImageFolderKey);

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_HOST.code);
        String imageUrlPrefix=String.format(imageUrlPrefixFormat,tbmqq461.getVal(),initImageFolderKey);

        File folder=new File(path);
        if(folder.exists()) {
            File[] supplierFolders = null;
            if (ObjectUtils.isEmpty(supplierNumbers)) {
                supplierFolders = folder.listFiles();
            } else {
                supplierFolders = folder.listFiles((dir, name) -> supplierNumbers.contains(name));
            }
            if (ObjectUtils.isEmpty(supplierFolders)) {
                throw new NetPlusException("未找到需上传的源数据!");
            }

            NowDate nowDate = new NowDate();
            Map<String,Tbmqq405> map=new HashMap<>();

            for (File supplierFolder : supplierFolders) {
                String supplierName=supplierFolder.getName();
                String fullImageUrlPrefix=imageUrlPrefix+ File.separator+supplierName;
                String imgSavePath=savePath+File.separator+supplierName;
                recursionFindImg(supplierFolder.listFiles(),supplierName,nowDate,map,fullImageUrlPrefix,imgSavePath);
            }
        }
    }

    private void recursionFindImg(File[] files, String supplierName, NowDate nowDate, Map<String,Tbmqq405> map, String imageUrlPrefix, String imgSavePath){
        for (File file : files) {
            if(file.isDirectory()){
                recursionFindImg(file.listFiles(), supplierName, nowDate, map, imageUrlPrefix, imgSavePath);
            }else{
                try {
                    logger.info(String.format("开始处理: %s",file.getPath()));
                    File img=file;
                    String imgName = img.getName();

                    String url=imageUrlPrefix+File.separator+ imgName;
                    url=url.replace(File.separator,"/");
                    String[] s = imgName.substring(0, imgName.indexOf(".")).split("_");
                    String matrlTM=s[0];
                    String sort=s[1];


                    Tbmqq405 info = map.get(matrlTM);
                    if(info==null){
                        HashMap<String, Object> reqMap = new HashMap<>();
                        reqMap.put("matrlTm",matrlTM);
                        List<Tbmqq405> infoList = basicDataBiz.getMatrl405No(reqMap);
                        if(ObjectUtils.isEmpty(infoList)){
                            info=new Tbmqq405();
                        }else{
                            info=infoList.get(0);
                        }
                        map.put(matrlTM,info);
                    }

                    if(StringUtils.isEmpty(info.getMatrltm())){
                        logger.error(String.format("物料条码: %s 未找到,所属目录: %s",matrlTM,file.getPath()));
                        continue;
                    }

                    Tbmqq429 tbmqq429 = new Tbmqq429();
                    tbmqq429.setPictureurl(url);
                    tbmqq429.setPicturename(String.format("%s_%s", matrlTM, sort));
                    tbmqq429.setPicturenum(sort);
                    tbmqq429.setSupplierno(supplierName);
                    tbmqq429.setPicturetype(PictureTypeEnum.MAIN.getCode());
                    tbmqq429.setDeleted("N");
                    tbmqq429.setCreateuser(SysCodeEnum.JOB.code);
                    tbmqq429.setCreatedate(nowDate.getDateStr());
                    tbmqq429.setCreatetime(nowDate.getTimeStr());
                    tbmqq429.setUpdateuser(SysCodeEnum.JOB.code);
                    tbmqq429.setUpdatedate(nowDate.getDateStr());
                    tbmqq429.setUpdatetime(nowDate.getTimeStr());
                    tbmqq429.setMatrlno(info.getMatrlno());
                    tbmqq429.setMatrlid(info.getMatrlid());
                    tbmqq429.setMatrltm(info.getMatrltm());
                    picLibBiz.insertOne(tbmqq429);
                    File newPath=new File(imgSavePath+File.separator+imgName);
                    FileUtils.copyFile(img, newPath);
                    logger.info(String.format("处理完成: %s",file.getPath()));
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error(String.format("处理失败: %s",file.getPath()),e);
                }
            }
        }

    }


    @Anonymous
    @PostMapping(Urls.InitMatrlImg.initBasicData3)
    public ApiResponse initBasicData3(@RequestBody(required=false) List<String> supplierNumbers){
        String path=String.format(resourcePath,initBasicData3FolderKey);
        String savePath=String.format(fileSavePath,initBasicData3FolderKey);

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_HOST.code);
        String imageUrlPrefix=String.format(imageUrlPrefixFormat,tbmqq461.getVal(),initBasicData3FolderKey);

        File folder=new File(path);
        if(folder.exists()){
            File[] supplierFolders = null;
            if(ObjectUtils.isEmpty(supplierNumbers)){
                supplierFolders = folder.listFiles();
            }else{
                supplierFolders = folder.listFiles((dir,name) -> supplierNumbers.contains(name));
            }
            if(ObjectUtils.isEmpty(supplierFolders)){
                throw new NetPlusException("未找到需上传的源数据!");
            }

            NowDate nowDate = new NowDate();
            for (File supplierFolder : supplierFolders) {
                if(!supplierFolder.isDirectory()){
                    continue;
                }
                if(supplierFolder.getName().equals("02049")){
                    continue;
                }

                File[] matrlFolders = supplierFolder.listFiles();
                for (File matrlFolder : matrlFolders) {
                    if(!matrlFolder.isDirectory()){
                        continue;
                    }

                    File[] files = matrlFolder.listFiles();
                    List<File> images=new ArrayList<>();
                    List<String> matrlTMList=null;
                    for (File file : files) {
                        if(file.isFile()){
                            if(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")){
                                matrlTMList=getMatrlTMList(file);
                            }else{
                                images.add(file);
                            }
                        }
                    }

                    if(ObjectUtils.isEmpty(images)){
                        logger.error(String.format("无图片信息,目录: %s",matrlFolder.getPath()));
                        continue;
                    }
                    if(ObjectUtils.isEmpty(matrlTMList)){
                        logger.error(String.format("无条码信息,目录: %s",matrlFolder.getPath()));
                        continue;
                    }

                    String filePathPrefix = File.separator + supplierFolder.getName()+ File.separator + matrlFolder.getName();

                    for (String matrlTM : matrlTMList) {
                        try {
                            logger.info(String.format("目录: %s ,条码: %s 开始处理!",matrlFolder.getPath(),matrlTM));

                            int count = basicDataBiz.getPicCountByMatrlTm(matrlTM);
                            if(count>0){
                                logger.info(String.format("目录: %s ,条码: %s 已存在,跳过!",matrlFolder.getPath(),matrlTM));
                                continue;
                            }

                            HashMap<String, Object> reqMap = new HashMap<>();
                            reqMap.put("matrlTm",matrlTM);
                            List<Tbmqq405> infoList = basicDataBiz.getMatrl405No(reqMap);

                            if(ObjectUtils.isEmpty(infoList)){
                                logger.error(String.format("物料条码: %s 未找到,所属目录: %s",matrlTM,matrlFolder.getPath()));
                                continue;
                            }

                            Tbmqq405 info=infoList.get(0);

                            List<Tbmqq407> list=new ArrayList<>();
                            for (File image : images) {

                                String pathEndPart=filePathPrefix + File.separator + matrlTM + "_" + image.getName();

                                File newPath=new File(savePath + pathEndPart);
                                FileUtils.copyFile(image, newPath);
                                String url=imageUrlPrefix+pathEndPart.replace(File.separator,"/");

                                String sort = image.getName().substring(0, image.getName().indexOf("."));


                                Tbmqq407 tbmqq407 = new Tbmqq407();
                                tbmqq407.setMatrlno(info.getMatrlno());
                                tbmqq407.setPictureurl(url);
                                tbmqq407.setPicturename(String.format("%s_%s", matrlTM, sort));
                                tbmqq407.setPicturenum(sort);
                                tbmqq407.setCreateuser(SysCodeEnum.JOB.code);
                                tbmqq407.setCreatedate(nowDate.getDateStr());
                                tbmqq407.setCreatetime(nowDate.getTimeStr());
                                tbmqq407.setUpdateuser(SysCodeEnum.JOB.code);
                                tbmqq407.setUpdatedate(nowDate.getDateStr());
                                tbmqq407.setUpdatetime(nowDate.getTimeStr());
                                tbmqq407.setMatrltm(info.getMatrltm());
                                tbmqq407.setMatrlid(info.getMatrlid());

                                list.add(tbmqq407);
                            }
                            if(list.size()>0){
                                basicDataBiz.batchInsert(list);
                                logger.info(String.format("目录: %s ,条码: %s 处理完成!",matrlFolder.getPath(),matrlTM));
                            }
                        }catch (Exception e){
                            logger.error(String.format("目录: %s ,条码: %s 处理失败!",matrlFolder.getPath(),matrlTM),e);
                        }
                    }

                }
            }

        }
        return ApiResponse.success();
    }


    @Anonymous
    @PostMapping(Urls.InitMatrlImg.initErrorFormatBasicData3)
    public ApiResponse initErrorFormatBasicData3(@RequestBody(required=false) List<String> supplierNumbers){
        String path=String.format(resourcePath,initBasicData3FolderKey);
        String savePath=String.format(fileSavePath,initBasicData3FolderKey);

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_HOST.code);
        String imageUrlPrefix=String.format(imageUrlPrefixFormat,tbmqq461.getVal(),initBasicData3FolderKey);

        HashMap<String, Boolean> isExistMap = new HashMap<>();
        HashMap<String, Tbmqq405> matrlInfoMap = new HashMap<>();

        File folder=new File(path);
        if(folder.exists()){
            File[] supplierFolders = null;
            if(ObjectUtils.isEmpty(supplierNumbers)){
                supplierFolders = folder.listFiles();
            }else{
                supplierFolders = folder.listFiles((dir,name) -> supplierNumbers.contains(name));
            }
            if(ObjectUtils.isEmpty(supplierFolders)){
                throw new NetPlusException("未找到需上传的源数据!");
            }

            NowDate nowDate = new NowDate();
            for (File supplierFolder : supplierFolders) {
                if(!supplierFolder.isDirectory()){
                    continue;
                }

                File[] imgs = supplierFolder.listFiles();
                for (File imgFile : imgs) {
                    String pathEndPart = File.separator + supplierFolder.getName()+ File.separator + imgFile.getName();

                    String imgName = imgFile.getName().substring(0, imgFile.getName().indexOf("."));

                    String matrlTM=imgName.split("_")[0];

                    String sort = imgName.split("_")[1];

                    try{
                        logger.info(String.format("目录: %s ,条码: %s 开始处理!",imgFile.getPath(),matrlTM));

                        Boolean isExist = isExistMap.get(matrlTM);
                        if(isExist == null){
                            int count = basicDataBiz.getPicCountByMatrlTm(matrlTM);
                            isExist=count>0;
                            isExistMap.put(matrlTM,isExist);
                        }

                        if(isExist){
                            logger.info(String.format("目录: %s ,条码: %s 已存在,跳过!",imgFile.getPath(),matrlTM));
                            continue;
                        }


                        Tbmqq405 info = matrlInfoMap.get(matrlTM);

                        if(info == null) {
                            HashMap<String, Object> reqMap = new HashMap<>();
                            reqMap.put("matrlTm", matrlTM);
                            List<Tbmqq405> infoList = basicDataBiz.getMatrl405No(reqMap);
                            if(ObjectUtils.isEmpty(infoList)){
                                info = new Tbmqq405();
                            }else{
                                info=infoList.get(0);
                            }
                            matrlInfoMap.put(matrlTM,info);
                        }

                        if(StringUtils.isEmpty(info.getMatrltm())){
                            logger.error(String.format("物料条码: %s 未找到,所属目录: %s",matrlTM,imgFile.getPath()));
                            continue;
                        }

                        File newPath=new File(savePath + pathEndPart);
                        FileUtils.copyFile(imgFile, newPath);
                        String url=imageUrlPrefix+pathEndPart.replace(File.separator,"/");



                        Tbmqq407 tbmqq407 = new Tbmqq407();
                        tbmqq407.setMatrlno(info.getMatrlno());
                        tbmqq407.setPictureurl(url);
                        tbmqq407.setPicturename(imgName);
                        tbmqq407.setPicturenum(sort);
                        tbmqq407.setCreateuser(SysCodeEnum.JOB.code);
                        tbmqq407.setCreatedate(nowDate.getDateStr());
                        tbmqq407.setCreatetime(nowDate.getTimeStr());
                        tbmqq407.setUpdateuser(SysCodeEnum.JOB.code);
                        tbmqq407.setUpdatedate(nowDate.getDateStr());
                        tbmqq407.setUpdatetime(nowDate.getTimeStr());
                        tbmqq407.setMatrltm(info.getMatrltm());
                        tbmqq407.setMatrlid(info.getMatrlid());
                        basicDataBiz.insert(tbmqq407);
                        logger.info(String.format("目录: %s ,条码: %s 处理完成!",imgFile.getPath(),matrlTM));
                    }catch (Exception e){
                        logger.error(String.format("目录: %s ,条码: %s 处理失败!",imgFile.getPath(),matrlTM),e);
                    }


                }
            }

        }
        return ApiResponse.success();
    }
}
