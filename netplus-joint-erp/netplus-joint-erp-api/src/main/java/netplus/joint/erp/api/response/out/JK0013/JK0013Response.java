package netplus.joint.erp.api.response.out.JK0013;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JK0013Response {
    private String code;//	结果返回码	000000: 发送成功
//300: 参数转换错误
//3001: 短信服务欠费
//3002: 模板校验错误
//3003: 模板参数校验失败
//3004: 发松频率过高
//3005: 号码单日发送量超限
//3006: 无回执信息
    private String description;//	短信发送结果描述
    private String messageId;//	短信ID
    private List<String> receiver;//	发送的号码
    private List<JK0013SubResponse> result;//	短信发送结果
    private String sign;
    private String templateId;


}
