package jetdevs.service;

import jetdevs.model.FileHeaders;

import java.util.List;

public interface FileHeaderService {
    FileHeaders save(final FileHeaders fileHeader);
    void saveAll(final List<FileHeaders> fileHeaders);
}
