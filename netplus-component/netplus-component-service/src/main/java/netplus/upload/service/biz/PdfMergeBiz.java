package netplus.upload.service.biz;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import netplus.upload.api.request.FileUploadRequest;
import netplus.upload.api.request.PdfMergeRequest;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * pdf 合并逻辑
 */
@Service
public class PdfMergeBiz {

    private static Log logger = LogFactory.getLog(PdfMergeBiz.class);

    private static final int downloadTimeout = 60 * 1000; // 60s, 1分种

    @Autowired
    private FileUploadBiz fileUploadBiz;

    public String pdfMerge(PdfMergeRequest request) throws IOException {

        if (ObjectUtil.isEmpty(request.getPdfUrlList())) {
            System.out.println("sdd");
            throw new IOException("地址错误");
        }

        List<String> urls = request.getPdfUrlList().stream().distinct().collect(Collectors.toList());

        if (urls.size() == 1) {
            return urls.get(0);
        }

        List<InputStream> ins = new ArrayList<>();
        urls.forEach(_url -> {
            try {
                ins.add(downloadFile(_url));
            } catch (Exception ioe) {
                logger.info("下载pdf失败", ioe);
            }
        });

        try {
            InputStream fileInputStream = PdfMergeBiz.mergePdf(ins);
            MultipartFile file = new MockMultipartFile(
                    "file",
                    "merge.pdf",
                    null,
                    fileInputStream);

            String path = fileUploadBiz.uploadFile(file, true);
            return path;

        } catch (Exception e) {
            logger.info("合并pdf失败", e);
        }

        return urls.get(0);
    }


    private InputStream downloadFile(String src_file) throws Exception {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(src_file);
            httpget.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(downloadTimeout)
                    .setConnectTimeout(downloadTimeout)
                    .setSocketTimeout(downloadTimeout)
                    .build());

            CloseableHttpResponse response = httpclient.execute(httpget);
            org.apache.http.HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            // 复制流
            org.apache.commons.io.output.ByteArrayOutputStream baos = new org.apache.commons.io.output.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            //
            is.close();

            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 合并pdf
     *
     * @param ins
     * @return
     * @throws Exception
     */
    public static InputStream mergePdf(List<InputStream> ins) throws Exception {
        Document document = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            document = new Document();
            PdfCopy copy = new PdfCopy(document, output);
            document.open();
            for (int i = 0; i < ins.size(); i++) {
                PdfReader reader = new PdfReader(ins.get(i));
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return new ByteArrayInputStream(output.toByteArray());
    }

}
