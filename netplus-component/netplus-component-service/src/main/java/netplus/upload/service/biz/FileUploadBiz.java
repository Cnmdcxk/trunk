package netplus.upload.service.biz;


import netplus.component.entity.exceptions.NetPlusException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

@Service
public class FileUploadBiz {

    private static Log logger = LogFactory.getLog(FileUploadBiz.class);

    @Value("${localServer.path}")
    private String localServerPath;

    @Value("${localServer.urlPrefix}")
    private String urlPrefix;

    public String uploadFile(MultipartFile file, boolean randomName) throws IOException {

        if (fileLocationLength(file.getOriginalFilename()) > 60) {
            throw new NetPlusException("文件名称长度不能超过60字符");
        }

        return uploadLocalServer(file);
    }

    public static int fileLocationLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public String uploadFileByUrl(String url) throws IOException {
        return uploadLocalServerByUrl(url);
    }

    private String uploadLocalServer(MultipartFile file) throws IOException {
        try (
                InputStream in = file.getInputStream();
        ) {
            String fileName = String.format("%s.%s", UUID.randomUUID().toString(), FilenameUtils.getExtension(file.getOriginalFilename()));
            String fullPath = localServerPath + fileName;
            FileUtil.uploadFile(fullPath, in);
            return String.format("%s%s", urlPrefix, fileName);
        }
    }

    private String uploadLocalServerByUrl(String url) throws IOException {
        URL url1 = new URL(url);
        URLConnection conn = url1.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(1000);
        conn.setReadTimeout(1000);
        try (
                InputStream in = conn.getInputStream();
        ) {
            String fileName = String.format("%s.%s", UUID.randomUUID().toString(), "pdf");
            String fullPath = localServerPath + fileName;
            FileUtil.uploadFile(fullPath, in);
            return String.format("%s%s", urlPrefix, fileName);
        }
    }
}
