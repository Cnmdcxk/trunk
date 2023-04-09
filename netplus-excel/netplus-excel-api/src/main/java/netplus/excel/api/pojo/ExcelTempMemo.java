package netplus.excel.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("netplus_excel.excel_temp_memo")
public class ExcelTempMemo implements Serializable {
    private Long id;

    private String batchCode;

    private String field;

    private String value;

    private Date createDate;

    private String createPerson;
}