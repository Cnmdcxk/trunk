package netplus.captcha.service.controller;

import io.swagger.annotations.ApiOperation;
import netplus.captcha.service.biz.QRUtil;
import netplus.component.entity.auth.Anonymous;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

/**
 * Created by yandong on 2020/4/8.
 */
@RestController
public class QRCodeController {

    @Autowired
    private QRUtil qrUtil;

    @Anonymous
    @PostMapping("/api/v2/captcha/getQrCodeUrl/{ladingBillCode}")
    @ApiOperation(value = "获取二维码图片")
    public String getQrCodeUrl(@PathVariable String ladingBillCode) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(ladingBillCode.getBytes());
        try {
            return qrUtil.encode(encode, null, String.format("%s%s", QRUtil.DIR, ladingBillCode+".jpg"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
