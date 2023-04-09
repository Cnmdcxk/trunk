package netplus.excel.api.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExportExcelRequest<T> {

    private Map<String, String> titles;
    private String sheetName;
    private List<T> items;

}
