package netplus.component.entity.response;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.exceptions.NetPlusException;

/**
 * 返回结果类
 */
@Getter
@Setter
public class ApiResponse<T> {
	public static int SUCCESS = 1;
    public static int FAILURE = 0;
    
    private int status;
    private String message;
    private String msg;
    private T data;

    public ApiResponse(){
        this.status = FAILURE;
    }

    public static ApiResponse success(){
        ApiResponse jsonResult = new ApiResponse();
        jsonResult.setStatus(SUCCESS);
        return jsonResult;
    }

    public static <T> ApiResponse success(T data){
        if(null == data)
            throw new NetPlusException("data cannot be null!");
        ApiResponse<T> jsonResult = new ApiResponse<T>();
        jsonResult.setStatus(SUCCESS);
        jsonResult.setData(data);
        return jsonResult;
    }

    /**
    * @Description: 返回成功并添加消息
    * @author: jinx
    * @date: 2020/12/30 15:21
    */
    public static <T> ApiResponse success(T data, String msg){
        if(null == data)
            throw new NetPlusException("data cannot be null!");
        ApiResponse<T> jsonResult = new ApiResponse<T>();
        jsonResult.setStatus(SUCCESS);
        jsonResult.setData(data);
        jsonResult.setMessage(msg);
        jsonResult.setMsg(msg);
        return jsonResult;
    }

    public static ApiResponse fail(String msg){
        ApiResponse jsonResult = new ApiResponse();
        jsonResult.setMessage(msg);
        jsonResult.setMsg(msg);
        return jsonResult;
    }
    /**
     * @Description: 返回失败并添加失败次数
     * @author: zhang
     * @date: 2020/12/30 15:21
     */
    public static ApiResponse fail(String msg,String errorTimes){
        ApiResponse jsonResult = new ApiResponse();
        jsonResult.setMessage(msg);
        jsonResult.setMsg(errorTimes);
        return jsonResult;
    }
}
