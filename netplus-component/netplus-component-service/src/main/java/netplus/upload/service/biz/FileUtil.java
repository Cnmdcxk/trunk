package netplus.upload.service.biz;

import java.io.*;

public class FileUtil {

    public static void uploadFile(String filePath, InputStream inputStream) throws IOException {
        File file = new File(filePath);
        try (
                OutputStream out = new FileOutputStream(file);
        ) {
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length = inputStream.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        }
    }
}
