package jetdevs.service;

import jetdevs.model.FileRecords;

import java.util.List;

public interface FileRecordService {

    void saveAll(final List<FileRecords> fileRecords);
}
