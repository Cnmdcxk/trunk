package netplus.provider.api.pojo.ygmalluser;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ygmalluser.Tbmqq439")
public class Tbmqq439 {
    private String code; //编码

    private String province; // 省份

    private String city; // 市

    private String consigneeaddr; // 详细地址

    private String createdate; // 创建人

    private String createtime; // 创建时间

    private String updatedate; // 修改人

    private String updatetime; // 修改时间

    private String addrtype; // 类型
}