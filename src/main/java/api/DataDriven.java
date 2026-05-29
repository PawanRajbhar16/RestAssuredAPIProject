package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public ArrayList<String> getData(String testcasename) throws IOException {

		FileInputStream fis = new FileInputStream("./data/data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheetname = workbook.getSheet("TestData");

		ArrayList<String> data = new ArrayList<>();
		int k = 0;
		int col = 0;
		Iterator<Row> row = sheetname.iterator();
		Row firstRow = row.next();
		Iterator<Cell> cellIterator = firstRow.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cel = cellIterator.next();
			if (cel.getStringCellValue().equalsIgnoreCase("TestCase")) {
				// desired coloumn value;
				col = k;
			}
			k++;
		}

		while (row.hasNext()) {

			Row r = row.next();
			if (r.getCell(col).getStringCellValue().equalsIgnoreCase(testcasename)) {

				Iterator<Cell> c = r.cellIterator();
				while (c.hasNext()) {
					Cell cellValu = c.next();
						
					data.add(String.valueOf(cellType(cellValu)));
					System.out.println(data);
				}
			}
		}

		workbook.close();
		return data;
	}

	public Object cellType(Cell cell) {

	    if (cell == null) {
	        return null;
	    }

	    CellType celltype = cell.getCellType();

	    switch (celltype) {

	        case NUMERIC:

	            // Check if date format
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue();
	            } else {

	                // Return integer if no decimal
	                double value = cell.getNumericCellValue();

	                if (value == (long) value) {
	                    return (long) value;
	                } else {
	                    return value;
	                }
	            }

	        case STRING:
	            return cell.getStringCellValue();

	        case BOOLEAN:
	            return cell.getBooleanCellValue();

	        case FORMULA:

	            // Handle formula result type
	            switch (cell.getCachedFormulaResultType()) {

	                case NUMERIC:
	                    return cell.getNumericCellValue();

	                case STRING:
	                    return cell.getStringCellValue();

	                case BOOLEAN:
	                    return cell.getBooleanCellValue();

	                default:
	                    return null;
	            }

	        case BLANK:
	            return "";

	        default:
	            throw new IllegalArgumentException("Unexpected value: " + celltype);
	    }
	}
	
	
	public static void main(String[] args) throws IOException {

		DataDriven datadriven=new DataDriven();
			System.out.println(datadriven.getData("Login"));
		
	}
}
