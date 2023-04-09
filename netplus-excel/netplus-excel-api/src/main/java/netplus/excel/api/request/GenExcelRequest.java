package netplus.excel.api.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenExcelRequest<T> {

    private String templateName;
    private String sheetName;
    private List<T> items;
    private String head;
    private String hasRemark;

}
