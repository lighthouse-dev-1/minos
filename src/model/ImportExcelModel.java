package model;

import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.json.JSONObject;

import com.nulabinc.backlog4j.IssueType;
import com.nulabinc.backlog4j.ResponseList;

public class ImportExcelModel extends AppModel{
	public JSONObject getJsonIssueTypeList( ResponseList<IssueType> issueTypeList) {		
		HashMap<String, ResponseList<IssueType>> issueTypeMap = new HashMap<String, ResponseList<IssueType>>();
		issueTypeMap.put("data", issueTypeList);
		
		return new JSONObject(issueTypeMap);
	}
	
	@SuppressWarnings("deprecation")
	public JSONObject importExcel(FileItemIterator iterator) throws IllegalStateException, IOException
	{	
		String[][] task = null;
		HashMap<Integer, HashMap<Integer, String>> map = new HashMap<Integer, HashMap<Integer, String>>();
		
		try {
		    while(iterator.hasNext()){
		    	FileItemStream item = iterator.next();

		      	@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(item.openStream());
				int rowindex    =0;
				int columnindex =0;

				XSSFSheet sheet = workbook.getSheetAt(0);
				int rows  = sheet.getPhysicalNumberOfRows();
				int count = 0;

				task = new String[rows-1][4];
				for(rowindex=1; rowindex<rows; rowindex++) {
					XSSFRow row = sheet.getRow(rowindex);
					HashMap<Integer, String> tmp = new HashMap<Integer, String>();
					if(row != null){
						int cells = row.getPhysicalNumberOfCells();
						for(columnindex=0; columnindex <= cells; columnindex++){
							XSSFCell cell  = row.getCell(columnindex);
							String 	 value = "";
							if(cell == null) {
								continue;
							} else {
								switch (cell.getCellType()) {
									case XSSFCell.CELL_TYPE_FORMULA:
										value=cell.getCellFormula();
										break;
									case XSSFCell.CELL_TYPE_NUMERIC:
										value=cell.getNumericCellValue()+"";
										break;
									case XSSFCell.CELL_TYPE_STRING:
										value=cell.getStringCellValue()+"";
										break;
									case XSSFCell.CELL_TYPE_BLANK:
										value=cell.getBooleanCellValue()+"";
										break;
									case XSSFCell.CELL_TYPE_ERROR:
										value=cell.getErrorCellValue()+"";
										break;
								}
							}
							task[rowindex-1][columnindex] = value;
							tmp.put(columnindex, value);
						}
						map.put(count, tmp);
						count++;
					}
				}
		    }

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonTask = new JSONObject(map);
	
		return jsonTask;
	}
}
