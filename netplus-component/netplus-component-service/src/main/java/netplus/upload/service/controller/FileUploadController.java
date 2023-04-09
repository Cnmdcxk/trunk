package netplus.upload.service.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.AccessAnonymous;
import netplus.component.entity.auth.Anonymous;
import netplus.upload.api.request.PdfMergeRequest;
import netplus.upload.api.rest.FileUploadRest;
import netplus.upload.api.rest.Urls;
import netplus.upload.api.vo.UploadResult;
import netplus.upload.service.biz.FileUploadBiz;
import netplus.upload.service.biz.PdfMergeBiz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Api(tags = "文件上传接口文档")
@RestController
public class FileUploadController extends BasedController implements FileUploadRest {

    private static Log logger = LogFactory.getLog(FileUploadController.class);

    @Autowired
    private FileUploadBiz fileUploadBiz;

    @Autowired
    private PdfMergeBiz pdfMergeBiz;

    @ApiOperation(value = "文件上传接口")
    @Anonymous
    @AccessAnonymous
    @PostMapping(value = Urls.doUpload)
    public UploadResult doUpload(MultipartFile file) throws IOException {

        return uploadCore(file, false);

    }

    /*
    * excel下载时使用，文件名随机
    * */
    @Anonymous
    public UploadResult doUpload4Download(MultipartFile file) throws IOException{
        return uploadCore(file, true);
    }


    public UploadResult uploadCore(MultipartFile file, Boolean randomName) throws IOException{
        String filePath = fileUploadBiz.uploadFile(file, randomName);
        UploadResult uploadResult = new UploadResult();
        uploadResult.setCode(0);
        uploadResult.setBatchCode(UUID.randomUUID().toString().replace("-", ""));
        uploadResult.setError("0");
        uploadResult.setState("SUCCESS");
        uploadResult.setUrl(filePath);
        return uploadResult;
    }


    @ApiOperation(value = "文件上传接口")
    @Anonymous
    @AccessAnonymous
    @ResponseBody
    @PostMapping("/api/v2/fileupload/doUploadByUrl/")
    public UploadResult doUploadByUrl(String fileUrl) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String filePath = fileUploadBiz.uploadFileByUrl(fileUrl);

        UploadResult uploadResult = new UploadResult();
        uploadResult.setCode(0);
        uploadResult.setBatchCode(UUID.randomUUID().toString().replace("-", ""));
        uploadResult.setError("0");
        uploadResult.setState("SUCCESS");

        uploadResult.setUrl(filePath);
        return uploadResult;

    }


    @PostMapping(Urls.pdfMerge)
    @ApiOperation(value = "合并pdf文件接口-只支持ajax访问")
    @Anonymous
    @AccessAnonymous
    public String pdfMerge(@RequestBody PdfMergeRequest request) throws IOException {

        return pdfMergeBiz.pdfMerge(request);

    }


}
