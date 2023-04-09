package netplus.serial.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 流水号生成工具-参数
 */
@Getter
@Setter
@ApiModel("流水号生成工具-参数")
public class SerialParam implements Serializable {

    /**
     * appId
     */
    @ApiModelProperty(value = "应用ID-必填", required = true)
    @NotNull(message = "应用ID必须填写")
    private String appId;

    /**
     * 流水号前缀
     */
    @ApiModelProperty(value = "流水号前缀-必填", required = true)
    @NotNull(message = "流水号前缀必须填写")
    private String prefix;

    /**
     * 起始序列
     */
    @ApiModelProperty(value = "起始序列")
    private Integer startSq;

    /**
     * 序列号模版
     */
    @ApiModelProperty(value = "序列号模版: 默认default")
    private String sqFormat;

    public SerialParam(String prefix, String appId) {
        this.prefix = prefix;
        this.appId = appId;
    }

    public SerialParam(){}

}
