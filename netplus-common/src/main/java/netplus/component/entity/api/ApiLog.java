package netplus.component.entity.api;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiLog {

    private Integer id;
    private String apiName;
    private String content;
    private Date createTime;
    //调用方向，0：调用别人，1：别人调用
    private Integer direction;

    private String response;
    private Date updateTime;
    private String createUser;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 日志类型（0:方法访问日志，1:异常日志，2:业务日志）
     */
    private Integer logType;

    /**
     * 描述
     */
    private String description;
}
