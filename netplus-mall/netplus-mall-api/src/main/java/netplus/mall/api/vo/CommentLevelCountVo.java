package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommentLevelCountVo implements Serializable {
    private String commentLevel;
    private Integer totalCount;
}
