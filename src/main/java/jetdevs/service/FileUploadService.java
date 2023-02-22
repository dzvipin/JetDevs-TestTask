package jetdevs.service;

import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import jetdevs.model.UploadFile;

import java.io.InputStream;
import java.util.List;

public interface FileUploadService {
    UploadFile readFile(InputStream inputStream, String fileName);
}
