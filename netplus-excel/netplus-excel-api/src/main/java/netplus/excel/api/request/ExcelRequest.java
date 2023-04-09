package netplus.excel.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
public class ExcelRequest implements Serializable {

    private String username;
    private String batchCode;
    private String filePath;
    private String version;
    private Integer startRowNum;

}