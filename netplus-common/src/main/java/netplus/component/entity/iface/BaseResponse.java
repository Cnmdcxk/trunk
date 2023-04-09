package netplus.component.entity.iface;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class BaseResponse<T> implements Serializable {

    private String reqId;
    private String respTime;

    private String status;
    private String message;
    private T respData;


    public static  BaseResponse success(String reqId) {
        return success(reqId, "处理成功", null);
    }

    public static <T> BaseResponse<T> success(String reqId, T respData) {
        return success(reqId, "处理成功", respData);
    }

    public static  BaseResponse success(String reqId, String message) {
        return success(reqId, message, null);
    }

    public static <T> BaseResponse<T> success(String reqId , String message, T respData){

        BaseResponse<T> resp = new BaseResponse();
        resp.reqId = reqId;
        resp.status = "1";
        resp.message = message;
        resp.respData = respData;
        resp.respTime = String.valueOf(new Date().getTime());
        return resp;
    }

    public static  BaseResponse fail(String reqId){
        return fail(reqId, "处理失败");
    }
    
    public static  BaseResponse fail(String reqId, String message){
        return fail(reqId, message, null);
    }

    public static <T>  BaseResponse<T> fail(String reqId, String message,  T respData){

        BaseResponse resp = new BaseResponse();
        resp.reqId = reqId;
        resp.status = "0";
        resp.message = message;
        resp.respTime = String.valueOf(new Date().getTime());
        resp.respData = respData;
        return resp;
    }


}
