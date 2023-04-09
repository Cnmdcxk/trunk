package netplus.excel.service.analysis;

import netplus.component.entity.exceptions.NetPlusException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lhp on 2017/4/11.
 * excel 数据操作
 */
public class ExcelUtil {

    private static Log logger = LogFactory.getLog(ExcelUtil.class);

    private static Integer startNum = 0;

    private ExcelUtil() {
    }

    public static Workbook getWorkbookByStream(InputStream excelInputStream) {

        try {

            try {
                logger.info("WorkbookFactory 实例化。。。");
                return WorkbookFactory.create(excelInputStream);
            }catch (Exception e){
                logger.info("WorkbookFactory 失败");
                logger.error(e);
            }

            /*try {

                logger.info("XSSFWorkbook 实例化。。。");
                return new XSSFWorkbook(excelInputStream);
            } catch (Exception e) {
                logger.error(e);
                logger.info("XSSFWorkbook 失败");

                try {

                    logger.info("HSSFWorkbook 实例化。。。");
                    return new HSSFWorkbook(excelInputStream);
                } catch (IOException e2) {

                    logger.info("HSSFWorkbook 失败");

                    logger.error(e2);
                }
            }*/
        } finally {
            if (excelInputStream != null) {
                try {
                    excelInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static List<HashMap<String, Object>> getDataFromExcelBySheetName(InputStream excelInputStream, String sheetName, int startRowno) throws IOException {
        ArrayList list = new ArrayList();

        Workbook workbook = getWorkbookByStream(excelInputStream);

        if (workbook == null)
            throw new IOException("读取excel异常");

        Sheet sheet = workbook.getSheet(sheetName);
        Row firstRow = sheet.getRow(startRowno);
        String[] cellName = new String[firstRow.getLastCellNum()];
        int cellNum = firstRow.getLastCellNum();
        for (int i = 0; i < cellNum; ++i) {
            Cell cell = firstRow.getCell(i);

            String cellValue = getCellStringValue(cell);
            if (cellValue == null || cellValue.length() == 0) {
                break;
            }

            cellName[i] = cellValue.replaceAll("\t|\r|\n", "");
        }

        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

        for (int rowno = startRowno + 1; rowno <= sheet.getLastRowNum(); ++rowno) {
            Row var17 = sheet.getRow(rowno);
            if (var17 != null) {
                boolean var18 = true;
                HashMap rowMap = new HashMap();


                for (int cellno = 0; cellno < cellNum; ++cellno) {
                    Cell cell = var17.getCell(cellno);
                    Object value = getCellValueFormula(cell, formulaEvaluator);
                    if (value != null && !value.equals("")) {
                        var18 = false;
                    }

                    rowMap.put(cellName[cellno], value);
                }

                if (!var18) {
                    list.add(rowMap);
                }
            }
        }

        return list;
    }

    public static List<Map<String, Object>> getDataFromExcelBySheetNo(InputStream excelInputStream, int sheetNo, int startRowno) throws IOException {
        ArrayList list = new ArrayList();
        Workbook workbook = getWorkbookByStream(excelInputStream);

        if (workbook == null)
            throw new IOException("读取excel异常");

        Sheet sheet = workbook.getSheetAt(sheetNo);
        Row firstRow = sheet.getRow(startRowno);
        String[] cellName = new String[firstRow.getLastCellNum()];

        for (int i = 0; i < firstRow.getLastCellNum(); ++i) {
            Cell cell = firstRow.getCell(i);
            String cellValue = getCellStringValue(cell);
            if (cellValue == null || cellValue.length() == 0) {
                break;
            }

            cellName[i] = cellValue.replaceAll("\t|\r|\n", "");
        }

        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        for (int rowno = startRowno + 1; rowno <= sheet.getLastRowNum(); ++rowno) {
            Row var17 = sheet.getRow(rowno);
            if (var17 != null) {
                boolean var18 = true;
                HashMap rowMap = new HashMap();

                for (int cellno = 0; cellno < firstRow.getLastCellNum(); ++cellno) {
                    Cell cell = var17.getCell(cellno);
                    Object value = getCellValueFormula(cell, formulaEvaluator);
                    if (value != null && !value.equals("")) {
                        var18 = false;
                    }

                    rowMap.put(cellName[cellno], value);
                }

                if (!var18) {
                    list.add(rowMap);
                }
            }
        }

        return list;
    }


    public static Workbook exportWorkBook(Workbook workbook, List<Map<String, Object>> list, String[] cellName, String sheetName, ExcelFileType fileType) {
        if (workbook == null) {
            if (ExcelFileType.XLS == fileType) {
                workbook = new HSSFWorkbook();
            } else if (ExcelFileType.XLSX == fileType) {
                workbook = new XSSFWorkbook();
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        if (ExcelFileType.XLS == fileType) {
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        } else if (ExcelFileType.XLSX == fileType) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        cellStyle.setWrapText(true);
        Row row = sheet.createRow(0);

        int currentRowNum;
        for (currentRowNum = 0; currentRowNum < cellName.length; ++currentRowNum) {
            String name = cellName[currentRowNum];
            Cell map = row.createCell(currentRowNum);
            map.setCellValue(name);
        }


        for (int k = 0; k < list.size(); k++) {
            row = sheet.createRow(k + 1);
            HashMap<String, Object> var16 = (HashMap<String, Object>) list.get(k);
            for (int i = 0; i < cellName.length; ++i) {
                String name1 = cellName[i];
                String f = "field" + (i + 1);
//                System.out.println(var16.get(f));
                Cell cell = row.createCell(i);
                cell.setCellValue(String.valueOf(var16.get(f)));
            }
        }

        return workbook;
    }

    public static Workbook exportToWorkBook(Workbook workbook, List<Map<String, Object>> list, String[] cellName, String sheetName, ExcelFileType fileType) {

        if (workbook == null) {
            if (ExcelFileType.XLS == fileType) {
                workbook = new HSSFWorkbook();
            } else if (ExcelFileType.XLSX == fileType) {
                workbook = new XSSFWorkbook();
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        if (ExcelFileType.XLS == fileType) {
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        } else if (ExcelFileType.XLSX == fileType) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 输出备注


        Row row = sheet.createRow(startNum);
        int currentRowNum;
        for (currentRowNum = 0; currentRowNum < cellName.length; ++currentRowNum) {
            String name = cellName[currentRowNum];
            Cell map = row.createCell(currentRowNum);
            map.setCellValue(name);

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
            Font ztFont = workbook.createFont();
            ztFont.setItalic(false);
            ztFont.setColor(Font.COLOR_NORMAL);
            ztFont.setFontName("宋体");
            titleStyle.setFont(ztFont);
            map.setCellStyle(titleStyle);

            sheet.setColumnWidth(currentRowNum, sheet.getColumnWidth(currentRowNum) * 17 / 10);
        }

        currentRowNum = startNum + 1;

        for (Iterator var15 = list.iterator(); var15.hasNext(); ++currentRowNum) {
            HashMap var16 = (HashMap) var15.next();
            Row currentRow = sheet.createRow(currentRowNum);

            for (int i = 0; i < cellName.length; ++i) {
                String name1 = cellName[i];
                Cell cell = currentRow.createCell(i);
                setCellValue(cell, var16.get(name1), workbook, cellStyle);
                if (i == cellName.length - 1 && currentRowNum == 1) {
                    sheet.setColumnWidth(i, getCellStringValue(cell).getBytes().length * 140);
                }
            }
        }

        return workbook;
    }

    public static Workbook exportToWorkBoolWithHead(Workbook workbook, List<Map<String, Object>> list, String head, String[] cellName, String sheetName, ExcelFileType fileType) {
        if (null == head || StringUtils.isEmpty(head)) {
            return exportToWorkBook(workbook, list, cellName, sheetName, fileType);
        }
        startNum = 1;
        workbook = exportToWorkBook(workbook, list, cellName, sheetName, fileType);
        //赋值标题head
        Sheet sheet = workbook.getSheet(sheetName);
        Row row0 = sheet.createRow(0);
        Cell cell0 = row0.createCell(0);
        cell0.setCellValue(head);
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellName.length - 1);//起始行 结束行 开始列 结束列
        //添加头标题
        sheet.addMergedRegion(cellRangeAddress);
        //合并单元格边框
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, workbook);
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, workbook);
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, workbook);
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, workbook);
        RegionUtil.setBottomBorderColor(HSSFColor.BLACK.index, cellRangeAddress, sheet, workbook);
        RegionUtil.setTopBorderColor(HSSFColor.BLACK.index, cellRangeAddress, sheet, workbook);
        RegionUtil.setLeftBorderColor(HSSFColor.BLACK.index, cellRangeAddress, sheet, workbook);
        RegionUtil.setRightBorderColor(HSSFColor.BLACK.index, cellRangeAddress, sheet, workbook);

        return workbook;
    }

    /**
     * 重新设置宽度
     *
     * @param sheet
     * @param size
     */
    private static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue()
                                .getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    } else {
                        int length = getCellStringValue(currentCell).getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
                sheet.setColumnWidth(columnNum, columnWidth * 256);
            }
        }
    }

    /**
     * 带标题的EXCEL输出
     *
     * @param workbook
     * @param list
     * @param cellName
     * @param sheetName
     * @param fileType
     * @return
     */
    public static Workbook exportToWorkBook(String title, Workbook workbook, List<Map<String, Object>> list, String[] cellName, String sheetName, ExcelFileType fileType) {
        if (workbook == null) {
            if (ExcelFileType.XLS == fileType) {
                workbook = new HSSFWorkbook();
            } else if (ExcelFileType.XLSX == fileType) {
                workbook = new XSSFWorkbook();
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        if (ExcelFileType.XLS == fileType) {
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        } else if (ExcelFileType.XLSX == fileType) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        cellStyle.setWrapText(true);
        Row row = sheet.createRow(0);
        if (!StringUtils.isEmpty(title)) {
            //----------------标题样式---------------------
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            Font ztFont = workbook.createFont();
            ztFont.setItalic(false);
            ztFont.setColor(Font.COLOR_NORMAL);
            ztFont.setFontHeightInPoints((short) 16);
            ztFont.setFontName("宋体");
            ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            titleStyle.setFont(ztFont);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellName.length - 1));
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            cell.setCellStyle(titleStyle);
        }
        int currentRowNum;
        row = sheet.createRow(1);
        //----------------二级标题样式----------------------------------
        CellStyle secondTitleStyle = workbook.createCellStyle();
        secondTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        secondTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        secondTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
        secondTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        secondTitleStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
        secondTitleStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
        Font ztFont2 = workbook.createFont();
        ztFont2.setItalic(false);                     // 设置字体为斜体字
        ztFont2.setColor(Font.COLOR_NORMAL);            // 将字体设置为“红色”
        ztFont2.setFontHeightInPoints((short) 11);    // 将字体大小设置为18px
        ztFont2.setFontName("宋体");             // 字体应用到当前单元格上
        ztFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    //加粗
        secondTitleStyle.setFont(ztFont2);

        for (currentRowNum = 0; currentRowNum < cellName.length; ++currentRowNum) {
            String name = cellName[currentRowNum];
            Cell map = row.createCell(currentRowNum);
            map.setCellStyle(secondTitleStyle);
            map.setCellValue(name);
        }

        currentRowNum = 1;

        //-------------单元格样式---------------
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        Font cellFont = workbook.createFont();
        cellFont.setItalic(false);
        cellFont.setColor(Font.COLOR_NORMAL);
        cellFont.setFontHeightInPoints((short) 10);
        cellFont.setFontName("宋体");
        cellStyle.setFont(cellFont);
        cellStyle.setWrapText(true);//设置自动换行
        for (Iterator var15 = list.iterator(); var15.hasNext(); ++currentRowNum) {
            HashMap var16 = (HashMap) var15.next();
            Row currentRow = sheet.createRow(currentRowNum + 1);
            for (int i = 0; i < cellName.length; ++i) {
                String name1 = cellName[i];
                Cell cell = currentRow.createCell(i);
                cell.setCellStyle(cellStyle);
                setCellValue(cell, var16.get(name1), workbook, cellStyle);
            }
        }
        return workbook;
    }

    public static Workbook exportExcelWithCheckInfo(List<Map<String, Object>> list, InputStream excelInputStream, ExcelFileType fileType, String sheetName, int startRowno) throws IOException {
        Workbook workbook = getWorkbookByStream(excelInputStream);
        if (workbook == null)
            throw new IOException("无法读取excel文件流");


        Sheet sheet = workbook.getSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        Row firstRow = sheet.getRow(startRowno);
        String[] cellName = new String[firstRow.getLastCellNum()];

        int currentRowNum;
        for (currentRowNum = 0; currentRowNum < firstRow.getLastCellNum(); ++currentRowNum) {

            Cell cell = firstRow.getCell(currentRowNum);

            String cellValue = getCellStringValue(cell);
            if (cellValue == null || cellValue.length() == 0) {
                break;
            }

            cellName[currentRowNum] = cellValue.replaceAll("\t|\r|\n", "");
        }

        currentRowNum = startRowno + 1;

        for (Iterator var18 = list.iterator(); var18.hasNext(); ++currentRowNum) {
            HashMap var19 = (HashMap) var18.next();
            Row currentRow = sheet.createRow(currentRowNum);

            for (int i = 0; i < cellName.length; ++i) {
                String name = cellName[i];
                Cell cell1 = currentRow.createCell(i);
                setCellValue(cell1, var19.get(name), workbook, cellStyle);
            }
        }

        return workbook;
    }


    public static void setCellValue(Cell cell, Object value, Workbook wb, CellStyle cellStyle) {
        if (value instanceof String) {
            if (((String) value).startsWith("HYPERLINK") || ((String) value).startsWith("=HYPERLINK")) {
                Font cellFont = wb.createFont();
                cellFont.setUnderline((byte) 1);
                cellFont.setColor((byte) 12);
                cellStyle.setFont(cellFont);
                cell.setCellFormula((String) value);
            }

            cell.setCellValue((String) value);
        } else if (value instanceof Date) {
            //cellStyle是全局的，此处新建一个用于日期专用格式，防止其他cell（数字类型）也变成日期格式
            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle dateCellStyle = wb.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("YYYY/MM/DD hh:mm:ss"));
            cell.setCellValue((Date) value);
            cell.setCellStyle(dateCellStyle);
            return;
        } else if (value instanceof Double) {
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.000")); // 三位小数
            cell.setCellValue(((Double) value).doubleValue());
        } else if (value instanceof Integer) {
            String str = String.valueOf(value);
            cell.setCellValue(Double.parseDouble(str));
        } else if (value instanceof Boolean) {
            cell.setCellValue(((Boolean) value).booleanValue());
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(cellStyle);

    }

    public static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }

                cell.setCellType(1);
                return new BigDecimal(cell.getStringCellValue());
            case 1:
                return cell.getRichStringCellValue().getString().trim();
            case 2:
            default:
                return null;
            case 3:
                return "";
            case 4:
                return Boolean.valueOf(cell.getBooleanCellValue());
            case 5:
                return Byte.valueOf(cell.getErrorCellValue());
        }
    }

    public static Object getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell != null && formulaEvaluator != null) {
            if (cell.getCellType() == 2) {
                CellValue value = formulaEvaluator.evaluate(cell);
                switch (value.getCellType()) {
                    case 0:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return cell.getDateCellValue();
                        }

                        cell.setCellType(1);
                        return new BigDecimal(cell.getStringCellValue());
                    case 1:
                        return cell.getRichStringCellValue().getString().trim();
                    case 2:
                    default:
                        break;
                    case 3:
                        return "";
                    case 4:
                        return Boolean.valueOf(cell.getBooleanCellValue());
                    case 5:
                        return Byte.valueOf(cell.getErrorCellValue());
                }
            }

            return getCellValue(cell);
        } else {
            return null;
        }
    }


    public static String getCellStringValue(Cell cell) {

        if (cell == null)
            return "";

        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
            return dateFormat.format(date);
        }

//        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            cell.setCellType(1);
//        }

        // 强制转换成string类型
        cell.setCellType(Cell.CELL_TYPE_STRING);

        return cell.toString().trim()
                .replaceAll("\n", "")
                .replaceAll(" ", "")
                .replaceAll("\t", "");
    }

    public enum ExcelFileType {
        XLS,
        XLSX;

        ExcelFileType() {
        }
    }

    //导出多个sheet
    public static Workbook exportMoreSheetExcel(Workbook workbook, List<Map<String, Object>> list, String[] cellName, String sheetName, ExcelFileType fileType) {

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        if (ExcelFileType.XLS == fileType) {
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        } else if (ExcelFileType.XLSX == fileType) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        cellStyle.setWrapText(true);
        Row row = sheet.createRow(0);

        int currentRowNum;
        for (currentRowNum = 0; currentRowNum < cellName.length; ++currentRowNum) {
            String name = cellName[currentRowNum];
            Cell map = row.createCell(currentRowNum);
            map.setCellValue(name);
        }

        currentRowNum = 1;

        for (Iterator var15 = list.iterator(); var15.hasNext(); ++currentRowNum) {
            HashMap var16 = (HashMap) var15.next();
            Row currentRow = sheet.createRow(currentRowNum);

            for (int i = 0; i < cellName.length; ++i) {
                String name1 = cellName[i];
                Cell cell = currentRow.createCell(i);
                setCellValue(cell, var16.get(name1), workbook, cellStyle);
            }
        }

        return workbook;
    }

    /**
     * 为 第一个sheet 增加一行备注
     */
    public static Workbook addFirstRowRemark(Workbook wb, String remark) throws NetPlusException {
        if (wb == null) {
            throw new NetPlusException("workbook is null");
        }

        Sheet sheet = wb.getSheetAt(0);

        Row titleRow = sheet.getRow(0);
        int totalColumnNum = titleRow.getLastCellNum();
        int totalRowNum = sheet.getLastRowNum();

        // 插入一行数据
        sheet.shiftRows(0, totalRowNum, 1);

        // 为第一行创建一个单元格
        Row row = sheet.createRow(0);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(XSSFCellStyle.ALIGN_LEFT);

        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

        Cell cell = row.createCell(0);
        cell.setCellValue(remark);

        Font ztFont = wb.createFont();
        ztFont.setItalic(false);
        ztFont.setColor(Font.COLOR_NORMAL);
        ztFont.setFontName("宋体");
        cellStyle.setFont(ztFont);
        cell.setCellStyle(cellStyle);
        row.setHeight((short) 1560);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, totalColumnNum);
        sheet.addMergedRegion(cellRangeAddress);

        return wb;
    }

}
