package netplus.captcha.service.biz;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by py on 2017/7/18.
 */

@Setter
@Getter
public class ImageVerifyUtil {

    //图片宽度
    private int width = 60;
    //图片高度
    private int height = 30;
    //随机验证码长度
    private int codeLength = 4;
    //干扰线数量（识别难度）
    private int lineCount;
    //随机验证码
    private String code;
    //随机验证码图片流
    private BufferedImage bufferedImage;

    public ImageVerifyUtil() {
        createVerifyImage();
    }

    public ImageVerifyUtil(int width, int height) {
        this.width = width;
        this.height = height;
        createVerifyImage();
    }

    public ImageVerifyUtil(int width, int height, int lineCount) {
        this.width = width;
        this.height = height;
        this.lineCount = lineCount;
        createVerifyImage();
    }

    private void createVerifyImage () {
        //图片对象
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        //获取验证码
        code = CaptchaUtil.makeImageCode(codeLength);

        //获取图形上下文, 相当于图形渲染器
        Graphics2D g2d = bufferedImage.createGraphics();

        //设置背景
        g2d.setBackground(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //描绘干扰线
        renderLine(g2d);
        //描绘验证码
        renderCode(g2d);
        //释放资源
        g2d.dispose();
    }

    private void renderCode (Graphics2D g2d) {
        List<String> codeList = Arrays.asList(code.split(""));
        int xAxis = width / (codeLength + 2);
        int yAxis = height / 3 * 2;

        int index = 2;
        for (String s: codeList) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(s, xAxis+index, yAxis);
            index += 10;
        }
    }

    private void renderLine (Graphics2D g2d) {
        Random random = new Random();
        for (int i=0; i<=lineCount; i++) {
            int xAxisStart = random.nextInt(width);
            int yAxisStart = random.nextInt(height);
            int xAxisEnd = random.nextInt(width);
            int yAxisEnd = random.nextInt(height);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd);
        }
    }
}
