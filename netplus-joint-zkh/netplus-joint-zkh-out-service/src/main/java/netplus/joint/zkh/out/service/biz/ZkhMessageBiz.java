package netplus.joint.zkh.out.service.biz;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.http.HttpApiResponse;
import netplus.joint.zkh.api.request.out.DeleteRequest;
import netplus.joint.zkh.api.request.out.MessageRequest;
import netplus.joint.zkh.api.response.BaseResponse;
import netplus.joint.zkh.api.response.out.MessageResponse;
import netplus.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ZkhMessageBiz {

    @Autowired
    ZkhTokenBiz zkhTokenBiz;

    @Value("{zkh.url}")
    private String url;

    public static final Gson gson = JsonUtil.JsonInstance;

    //删除消息 暂时没用
    public  List<MessageResponse> getMessageType(int type) {
        //设置消息请求参数
        String accessToken = zkhTokenBiz.getAccessToken();
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setToken(accessToken);
        messageRequest.setType(type);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("get", "get", gson.toJson(messageRequest));

        Type _type = new TypeToken<BaseResponse<List<MessageResponse>>>() {}.getType();
        BaseResponse<List<MessageResponse>> messageResponse = gson.fromJson(httpApiResponse.getData(), _type);

        if (!messageResponse.getSuccess()) {
            throw new NetPlusException(messageResponse.getResultMessage());
        }

        return messageResponse.getResult();
    }

    //删除消息
    public void deleteMessage(String id) {
        //设置请求参数
        String accessToken = zkhTokenBiz.getAccessToken();
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(id);
        deleteRequest.setToken(accessToken);

        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("delete", "delete", gson.toJson(deleteRequest));
        BaseResponse response = gson.fromJson(httpApiResponse.getData(), BaseResponse.class);

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }
}
