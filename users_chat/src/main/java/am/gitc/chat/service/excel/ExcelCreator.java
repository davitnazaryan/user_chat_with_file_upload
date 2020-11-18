package am.gitc.chat.service.excel;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExcelCreator {

  Workbook createWorkbook(List<?> data) throws Exception;

}
