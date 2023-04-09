package netplus.utils.secure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static Log logger = LogFactory.getLog(JwtUtil.class);

    private static final long Expire = 7 * 24 * 60 * 60 * 1000; // 7天时间

    public static String sign(String companyCode, String userNo, String source, String signKey){
        try{
            Date date = new Date(System.currentTimeMillis() + Expire);
            Algorithm algorithm = Algorithm.HMAC256(signKey);
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("companyCode", companyCode)
                    .withClaim("userNo", userNo)
                    .withClaim("source", source)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean verify(String token, String signKey){
        try{

            Algorithm algorithm = Algorithm.HMAC256(signKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }


    /*
    * 可取：compNo, userNo, source
    *
    * */
    public static String getValue(String token, String fieldName){
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(fieldName).asString();
        }
        catch (Exception ex){
            return null;
        }
    }


    public static String signAgain(String authToken, String signKey) {
        String companyCode = getValue(authToken, "companyCode");
        String userNo = getValue(authToken, "userNo");
        String source = getValue(authToken, "source");

        return sign(companyCode, userNo, source, signKey);
    }


    public static boolean canReSign(String token){
        return canReSign(token, 60 * 60 * 1000L);
    }

    /*
    * 1、如果过期时间 - 当前时间 >= deltaTime, 就重新颁发
    * 2、deltaTime默认为1小时（单位毫秒）：60 * 60 * 1000
    * */
    public static boolean canReSign(String token, Long deltaTime){

        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            Map<String, Claim> claims = decodedJWT.getClaims();

            // 计算都折算到毫秒!!
            // 将要过期的时间（unix时间戳到秒），如：1580788453
            Long expAt = claims.get("exp").asLong() * 1000;
            // 当前时间
            Long nowTime = new Date().getTime();

            // 设定要过期的时间长, 反推生成token的时间
            Long lastSignAt = expAt - Expire;

            // 计算离上次token生成时间过了多久
            Long escapeTime = nowTime - lastSignAt;

            logger.debug(String.format("time escape: %s(s)", escapeTime / 1000));

            // 如果过了给定时间，需要重新生成token
            if (escapeTime >= deltaTime) {
                return true;
            }
        }
        catch (Exception ex){
            return false;
        }

        return false;
    }

    /**
     * 测试用户签名
     * 
     * @param args
     */
    public static void main(String[] args) {
        String ret = JwtUtil.sign("022007", "022007", "PC", "yg8cfb5673294611e9bb7c00163e10ccfb");

        String username = JwtUtil.getValue("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwiZXhwIjoxNjQ2MjI4NDgwLCJ1c2VyX25hbWUiOiIxMzk2MjQ1MDI5NSIsImp0aSI6IjQ4Y2ZjZDY5LTRlY2ItNGM1MC05MjllLWVhZGFjMmRlNDUzZCIsImNsaWVudF9pZCI6InRlc3QiLCJzY29wZSI6WyJyZWFkX3VzZXJpbmZvIl19.dMsWS2R7KoQLBtyHS1QBSLaciISwXKo5zjN_-HpVt2I", "user_name");


        logger.info(ret);
        logger.info(username);
    }
}
