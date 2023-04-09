package netplus.log.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 系统业务日志<br/>
 * TableName: sys_biz_log
 * </p>
 *
 * @author comet
 * @since 2021-05-18
 */
@Data
@TableName("ygmalluser.SYS_BIZ_LOG")
public class SysBizLog implements Serializable {

    private Long id;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法
     */
    private String method;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务对象ID
     */
    private String bizObjectId;

    /**
     * 业务对象
     */
    private String bizObject;

    /**
     * 业务操作人ID
     */
    private Long bizHandlerId;

    /**
     * 业务操作人
     */
    private String bizHandler;

    /**
     * 日志类型（0:方法访问日志，1:异常日志，2:业务日志）
     */
    private Integer type;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 异常代码
     */
    private String exceptionCode;

    /**
     * 操作类型(登录、退出、正常、出错)
     */
    private String exceptionDetail;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 用户名称
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Timestamp createTime;

}
