package netplus.utils.random;

import java.util.Random;

/**
 * Created by py on 2017/7/8.
 */
public class CaptchaUtil {
    private static char[] letter  = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String makeCaptcha (int figure) {
        Random random = new Random();
        String captcha ="";
        for (int i=0; i<figure; i++) {
            captcha += random.nextInt(10);
        }
        return captcha;
    }

    public static String makeImageCode (int figure) {
        Random random = new Random();
        String code = "";
        for (int i=0; i<figure; i++) {
            code += String.valueOf(letter[random.nextInt(letter.length)]);
        }
        return code;
    }
}
