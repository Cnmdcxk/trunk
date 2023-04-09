package netplus.joint.zkh.api.response.out.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageType6ResultVo {

    private String skuId;
    private int state; //1添加，2删除
}
