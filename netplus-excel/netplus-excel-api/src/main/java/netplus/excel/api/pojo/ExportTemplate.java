package netplus.excel.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("netplus_excel.export_template")
public class ExportTemplate implements Serializable {
    private Integer id;

    private String appId;

    private String templateName;

    private Date createTime;

    private String fieldName;

    private String title;

    private String remark;

    private String isActive;

    private Integer sort;

    private String displayFormat;
}