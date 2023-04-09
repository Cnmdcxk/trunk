package netplus.component.http;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class ReturnSaleClient {

    /**
     * @author zhanghongbo 20210220
     * 处理与统一认证接口接口
     * 请求方式httppost
     * salemap 需传入的参数Map 格式;
     * type：1，用户登录验证；2，新增用户资料注册；3，密码修改；4用户信息变更
     */
    public static String PushOA(Map salemap,int type) {
        String res = "";
        String url = "https://newcas.rizhaosteel.com:8077/H5NewCas/";
        if (1 == type) {
            url += "CasUserAuth";
        } else if (2 == type) {
            url += "CasUserAdd";
        } else if (3 == type) {
            url += "ResetUserPwd";
        }else if (4 == type) {
            url += "ResetUserPwd";
        }
        JSONObject paraJson = new JSONObject(salemap);
        String msgBody = paraJson.toString();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300 * 1000).setConnectTimeout(300 * 1000).build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            System.out.print("<<<<<<<<<<调用前的json串" + msgBody + "\n");
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            StringEntity postingString = new StringEntity(msgBody, "utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
            System.out.println("调用返回结果" + content);
            res = content;
            return res;


        } catch (Exception e) {
            System.out.print("调用接口失败");
            return msgBody;
        }
    }
    public static void main(String[] args) {
        //供应商登录需要的参数
//        Map<Object, Object> userLoginMap = new HashMap<>();
//        userLoginMap.put("userid","M023456");
//        userLoginMap.put("userpwd","7654321M");
//        userLoginMap.put("code","587896");
//        userLoginMap.put("userip","10.5.99.8");
//        userLoginMap.put("fromsys","EP");
//        String msg=PushOA(userLoginMap,1);
//        System.out.println(msg);
        //供应商登录需要的参数
        Map<Object, Object> userRegistMap = new HashMap<>();
        userRegistMap.put("userid","R025754");
        userRegistMap.put("username","牟金彩");
        userRegistMap.put("picid","");
        userRegistMap.put("is_secret","1");
        userRegistMap.put("email","r000797@rizhaosteel.com");
        userRegistMap.put("sex","男");
        userRegistMap.put("phone","15063321069");
        userRegistMap.put("postcode","H510");
        userRegistMap.put("postname","副处长");
        userRegistMap.put("is_enable","1");
        userRegistMap.put("is_primary","1");
        userRegistMap.put("deptid","H510");
        userRegistMap.put("fromsys","EP");

        String msg2=PushOA(userRegistMap,2);
        System.out.println(msg2);
    }

}