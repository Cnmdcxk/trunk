package netplus.component.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.xfire.client.Client;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
@Service
public class WebServiceClient {
    String url2="http://prod.rizhaosteel.com/erp/mi/mijiWebSrv.ws?wsdl";//MI模块WSDL
   private String urlStr="http://10.1.198.242/erp/mqz/mqzWS01.ws?wsdl";//MQZ模块WSDL
    public WebServiceClient() throws Exception {
//        URL url = new URL(urlStr);
//        Client client = new Client(url);
//        //参数
//        Object[] para = new Object[] {"R025755",getrank(),"15063321069",2};
//        // 第一次参数是方法名，第二个是参数值---webService【kbjcKzxWS01.java】
//        Object[] resp = client.invoke("sendALiMsg", para);
//        //取得返回的JSO对象
//        JSONArray dataArray = JSONArray.fromObject(resp);
//        System.out.println(dataArray.toString());
    }
    public static  Map<String,String> gencode( Object[] para,String method){
        URL url = null;
        Client client = null;
        String urlStr="http://prod.rizhaosteel.com/erp/mqz/mqzWS01.ws?wsdl";//MQZ模块WSDL
        HashMap<String, String> resultMap = new HashMap<>();
        try {
            url = new URL(urlStr);
            client = new Client(url);
            //参数
            String rank = getrank();
            resultMap.put("randomCode",rank);
            if(para==null){
                resultMap.put("result","ERR");
            }else{
                // 第一次参数是方法名，第二个是参数值---webService【kbjcKzxWS01.java】
                Object[] resp = client.invoke(method, para);
                //取得返回的JSO对象
                JSONArray dataArray = JSONArray.fromObject(resp);
                if (dataArray.size()>0) {
                    JSONObject everyJb = dataArray.getJSONObject(0);
                    //将Json转化为List3
                    String msg = (String) everyJb.get("msg");
                    String type = (String) everyJb.get("type");
                    resultMap.put("result",msg);//结果，成功为OK
                    resultMap.put("msg",type);//描述
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    private static String getrank(){
        String randomNumberSize = "0123456789";
        String randomNumber = "";
        for(int i = 6;i>0;i--) {
            randomNumber+=randomNumberSize.charAt((int)(Math.random()*10));
        }
        return randomNumber;
    }
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Object[] para = new Object[] {"R025755",getrank(),"15063321069",2};
        Map<String, String> codeMap = gencode(para, "sendALiMsg");
    }
}
