package netplus.provider.api.pojo.ygmalluser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 用户日志<br/>
 * TableName: sys_log
 * </p>
 *
 * @author comet
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_LOG")
public class SysLog implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户标识
     */
    private String userNo;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 记录时间：记录进入当前页面时间
     */
    private Timestamp enterTime;

    /**
     * 完成时间：记录退出当前页面时间
     */
    private Timestamp outTime;

    /**
     * 访问页面
     */
    private String pageUrl;

    /**
     * 操作类型(登录、退出、正常、出错)
     */
    private Long logType;

    /**
     * 浏览器版本
     */
    private String ieVersion;

    /**
     * SESSION_ID
     */
    private String sessionid;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 扩展字段3
     */
    private String ext3;


}
