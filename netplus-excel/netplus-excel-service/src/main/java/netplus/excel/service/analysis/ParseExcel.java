package netplus.excel.service.analysis;

import netplus.component.entity.exceptions.NetPlusException;
import netplus.utils.object.ObjectUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lhp on 2017/5/18.
 */
public class ParseExcel {
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}

	private InputStream inputStream;
	private String batchCode;
	private String appId;
	private String version;
	private int startRowNo = 0;
	private Map<String, String> head;
	private List<Map<String, String>> body;

	private void checkVersion(Workbook workbook) {

		if (ObjectUtils.isEmpty(version)) {
			return;
		}

		Sheet vSheet = workbook.getSheet("版本");
		if (ObjectUtils.isEmpty(vSheet)) {
			throw new NetPlusException("excel 文件不能读取版本信息");
		}

		Row row = vSheet.getRow(0);
		if (ObjectUtils.isEmpty(row)) {
			throw new NetPlusException("excel 文件不能读取版本信息");
		}

		Cell cell = row.getCell(0);
		if (version.equals(ExcelUtil.getCellStringValue(cell))) {
			throw new NetPlusException("excel 版本不对，当前版本为" + version);
		}
	}

	private void checkParam() {
		if (ObjectUtils.isEmpty(appId)) {
			throw new NetPlusException("appId 不能为空");
		}

		if (ObjectUtils.isEmpty(inputStream)) {
			throw new NetPlusException("文件流 不能为空");
		}
	}

	private void checkWorkbook(Workbook workbook) {
		if (ObjectUtils.isEmpty(workbook)) {
			throw new NetPlusException("该文件解析成excel出错了");
		}

		Sheet sheet = workbook.getSheetAt(0);
		if (ObjectUtils.isEmpty(sheet)) {
			throw new NetPlusException("无法读取数据，请确认第一个sheet是否有数据");
		}

		Row row = sheet.getRow(startRowNo);
		if (ObjectUtils.isEmpty(row)) {
			throw new NetPlusException("无法读取数据，请确认第一个sheet是否有数据");
		}
	}

	private Map<String, String> getHead(Sheet sheet) {
		Map<String, String> head = new HashMap();

		Row row = sheet.getRow(startRowNo);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			head.put("field" + (i + 1),
					ExcelUtil.getCellStringValue(row.getCell(i)));
		}
		return head;
	}

	private List<Map<String, String>> getBody(Sheet sheet) {
		List<Map<String, String>> items = new ArrayList<>();

		int maxRowNo = sheet.getLastRowNum();
		int maxCellNo = 0;
		for (int rowNo = startRowNo + 1; rowNo <= maxRowNo; rowNo++) {
			Row row = sheet.getRow(rowNo);
			if (!ObjectUtils.isEmpty(row) && nonEmptyRow(row)) {
				if (maxCellNo == 0) {
					maxCellNo = row.getLastCellNum();
				}

				Map<String, String> item = new HashMap<>();
				for (int i = 0; i < row.getLastCellNum(); i++) {
					item.put("field" + (i + 1), ExcelUtil.getCellStringValue(row.getCell(i)));
				}

				items.add(item);
			}
		}
		return items;
	}

	private Boolean nonEmptyRow(Row row) {

		List<String> items = new ArrayList<>();

		row.forEach(c -> {
			items.add(ExcelUtil.getCellStringValue(c));
		});

		return items.stream().anyMatch(ObjectUtil::nonEmpty);
	}

	public void parse() {
		checkParam();

		Workbook workbook = ExcelUtil.getWorkbookByStream(inputStream);

		checkWorkbook(workbook);
		checkVersion(workbook);

		Sheet sheet = workbook.getSheetAt(0);
		head = getHead(sheet);
		body = getBody(sheet);
	}

	public Map<String, String> getHead() {
		return head;
	}

	public List<Map<String, String>> getBody() {
		return body;
	}

	public String getBatchCode() {
		return this.batchCode;
	}

	public String getVersion() {
		return this.version;
	}

	public String getAppId() {
		return this.appId;
	}
}
