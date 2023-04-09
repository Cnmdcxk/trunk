package netplus.component.authbase.utils;


import netplus.component.entity.version.IVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class VersionUtils {

    private static Logger logger = LoggerFactory.getLogger(VersionUtils.class);


    private final static long _5min = 5 * 60 * 1000;

    private static float currentVer = 0.01f;
    private static long verUpdateTime = 0L;

    public static void setVer(IVersion version, HttpServletRequest request) {

        if (version != null) {
            long now = new Date().getTime();

            // ver, 调用量比较大，本地缓存5分种
            if (now - verUpdateTime >= _5min) {
                currentVer = version.getVer();
                verUpdateTime = now;
            }
        }


        request.setAttribute("ver", currentVer);
    }


}
