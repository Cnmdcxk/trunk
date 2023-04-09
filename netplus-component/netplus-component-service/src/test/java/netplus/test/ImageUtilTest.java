package netplus.test;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class ImageUtilTest {
    public static void main(String[] args) {
        try {
            // 条码
            String name = "1111, 2222,";

            // 目标存储目录
            String filePath = "D:/temp/image";

            // 源文件目录
            String[] filePaths = {"D:/temp/1.jpg", "D:/temp/2.jpg", "D:/temp/3.jpg"};

            String[] names = name.split(",");

            for (String path : filePaths) {
                File file = new File(path);

                String fileName = file.getName();

                for (int i = 0; i < names.length; i++) {
                    String newName = names[i] + "-" + fileName;

                    File newFile = new File(filePath + File.separator + newName);

                    FileUtils.copyFile(file, newFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
