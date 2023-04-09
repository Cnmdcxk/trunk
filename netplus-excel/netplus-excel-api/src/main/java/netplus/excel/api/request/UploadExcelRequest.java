package netplus.excel.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.excel.api.pojo.ExcelTempData;
import netplus.excel.api.pojo.ExcelTempMemo;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UploadExcelRequest  implements Serializable {

    private List<ExcelTempMemo> head;
    private List<ExcelTempData> body;

}
