package jetdevs.utils;



import jetdevs.constants.ErrorCodes;
import jetdevs.dto.FileDetailsDTO;
import jetdevs.exception.FileUploadException;
import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileHelper {

    public static FileDetailsDTO excelToFile(InputStream inputStream) {
        try {
             Map<Integer, ArrayList<FileRecords>> columns = new HashMap<>();
             Map<Integer, FileHeaders> headers = new HashMap<>();
             Workbook workbook = new XSSFWorkbook(inputStream);
             Sheet sheet = workbook.getSheetAt(0);
             Iterator<Row> rows = sheet.iterator();

            AtomicBoolean isFirstRow = new AtomicBoolean(true);
            rows.forEachRemaining(currentRow -> {
                if (isFirstRow.get()) {
                    currentRow.forEach(cell -> {
                        headers.put(cell.getColumnIndex(), new FileHeaders().toBuilder().headerName(cell.getStringCellValue()).build());
                    });
                    isFirstRow.set(false);
                    return;
                }
                currentRow.forEach(cell -> {
                if (!columns.containsKey(cell.getColumnIndex())) {
                    columns.put(cell.getColumnIndex(),
                           new ArrayList<>(Arrays.asList(
                                   new FileRecords().toBuilder().recordName(cell.getStringCellValue()).build())));
                } else {
                    columns.get(cell.getColumnIndex()).add(new FileRecords().toBuilder().recordName(cell.getStringCellValue()).build());
                }
                });
        });
            FileDetailsDTO fileDetails = new FileDetailsDTO();
            fileDetails.setFileHeaders(headers);
            fileDetails.setFileRecords(columns);
            return fileDetails;
        } catch (Exception e) {
            throw new FileUploadException(HttpStatus.BAD_REQUEST.value(), String.format(ErrorCodes.FILE_UPLOAD_FAILED, e.getCause()));
        }
    }
}
