package netplus.captcha.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netplus.cache.api.rest.CacheRest;
import netplus.captcha.service.biz.ImageVerifyUtil;
import netplus.captcha.service.request.CheckCodeRequest;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.response.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;


@Api(tags = "验证码页面逻辑")
@RestController
public class CaptchaController extends BasedController {


    protected Log logger = LogFactory.getLog(CaptchaController.class);


    @Autowired
    private CacheRest cacheRest;

    @Anonymous
    @GetMapping("/api/v2/captcha/img/{imgType}/")
    @ApiOperation(value = "获取验证码图片")
    public void img(HttpServletRequest request, HttpServletResponse response,
                    @PathVariable String imgType) throws IOException {

        ImageVerifyUtil imageVerifyUtil = new ImageVerifyUtil();

        // 存储后台imgCode
        String imgCode = imageVerifyUtil.getCode();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        cacheRest.set(uuid, imgCode.toLowerCase(), 5 * 60L);

        // 写cookie到客户端
        Cookie authCookie = new Cookie(imgType, uuid);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        response.addCookie(authCookie);

        // 生成图片
        BufferedImage bufferedImage = imageVerifyUtil.getBufferedImage();
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 兼容手机端，因为手机端没有cookie
        response.setHeader("uuid", uuid);
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
    }

    @Anonymous
    @PostMapping("/api/v2/captcha/checkCode")
    @ApiOperation(value = "校验图形验证码")
    public ApiResponse checkCode(@RequestBody CheckCodeRequest checkCode,
                                 HttpServletRequest request, HttpServletResponse response) {

        String imgCode = checkCode.getImgCode();
        String imgType = checkCode.getImgType();
        String uuid = getCookie(imgType);
        if(StringUtils.isEmpty(imgCode)){
            return ApiResponse.fail("验证码不能为空");
        }
        if(StringUtils.isEmpty(uuid)) {

            // TODO: 2021/2/27 接口参数没有传uuid, 是为了兼容手机端？？？
            uuid = checkCode.getUuid();
        }
        // 获取缓存中uuid对应系统验证码
        String sysCode = cacheRest.get(uuid);
        if(!imgCode.toLowerCase().equals(sysCode)) {
            return ApiResponse.fail("验证码无效");
        }
        return ApiResponse.success();
    }

    /**
     * 获取cookie的值
     * @param name
     * @return
     */
    public static String getCookie(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie[] cookies = request.getCookies();
        if(null == cookies || cookies.length == 0) {
            return null;
        }
        for(Cookie c : cookies) {
            if(c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return null;
    }

}
