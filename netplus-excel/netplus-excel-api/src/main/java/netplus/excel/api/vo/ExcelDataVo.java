package netplus.excel.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.excel.api.pojo.ExcelTempData;
import netplus.excel.api.pojo.ExcelTempMemo;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ExcelDataVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3928391443596485528L;
	private String batchCode;
    private List<ExcelTempData> body;
    private List<ExcelTempMemo> head;

}