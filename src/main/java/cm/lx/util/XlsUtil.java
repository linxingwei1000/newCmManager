package cm.lx.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XlsUtil {

    public static void readXls(InputStream is) throws Exception {
        Workbook rwb = Workbook.getWorkbook(is);// 新建工作区
        String[] sheets = rwb.getSheetNames();
        //System.out.println("sheets="+sheets.length);
        for (String sheet : sheets) {
            Sheet st = rwb.getSheet(sheet);// sheet名称获取方式
            int num = st.getRows();// 所有导入行数
            int lie = st.getColumns();//所有列
            for (int row = 0; row < num; row++) {// 第1行开始取数据
                Collection<String> data = new ArrayList<String>();
                for (int i = 0; i < lie; ++i) {
                    Cell cell = st.getCell(i, row);
                    if (cell == null) break;
                    String contents = cell.getContents();
                    if (StringUtils.isNotEmpty(contents)) {
                        data.add(contents.trim());
                    }
                }

                if (!data.isEmpty())
                    break;
            }
        }
    }

    // 读取xlsx文件
    public static List<Collection<String>> readXlsx(InputStream is) throws Exception {
        long startTime = System.currentTimeMillis();   //获取开始时间
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rownum = sheet.getPhysicalNumberOfRows();
        List<Collection<String>> resp = new ArrayList<Collection<String>>();
        for (int i = 1; i < rownum; i++) {
            XSSFRow row = sheet.getRow(i);
            // 获取一行每列的数据
            Collection<String> data = new ArrayList<String>();
            int cells = row.getPhysicalNumberOfCells();
            for (int x = 0; x < cells; x++) {
                XSSFCell cell = row.getCell(x);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                        case XSSFCell.CELL_TYPE_FORMULA:
                        case XSSFCell.CELL_TYPE_BLANK:
                            String value = StringUtils.trimToEmpty(cell.getStringCellValue());
                            data.add(value);
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                String str = new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                                data.add(str);
                            }else{
                                BigDecimal db = new BigDecimal(cell.getNumericCellValue());
                                data.add(db.toString());
                            }
                            break;
                        default:
                            System.out.println("未知格式");
                            break;
                    }
                }else{
                    data.add("");
                }
            }
            resp.add(data);
        }
        return resp;
    }

    public static void readTxt(InputStream is) throws IOException {
        Reader reader = null;
        BufferedReader buffer = null;
        try {
            reader = new InputStreamReader(is);
            buffer = new BufferedReader(reader);

            String line;
            while ((line = buffer.readLine()) != null) {
                line = StringUtils.trimToNull(line);
                if (line == null) continue;
                String[] split = line.split("[\t,]");
                if (split.length > 0)
                    break;
            }
        } finally {
            try {
                if (buffer != null) buffer.close();
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
