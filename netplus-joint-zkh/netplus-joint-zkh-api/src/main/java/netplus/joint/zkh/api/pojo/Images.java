package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Images {

    // String Y 图⽚地址 图⽚绝对路径地址
    private String fullPath;

    // Int Y 是否主图 1：是 0：否
    private Integer isPrimary;

    // Int Y 排序 主图可传null ⾮主图传排序数字
    private Integer orderSort;
}
