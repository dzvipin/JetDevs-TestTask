package jetdevs.service;

import jetdevs.dto.UploadFileDetailsDTO;
import jetdevs.model.UploadFile;
import jetdevs.model.User;

import java.util.List;

public interface FileService {
    UploadFile save(final UploadFile files);
    List<UploadFile> getAllFiles();
    void delete(long documentId);
    UploadFileDetailsDTO getFileInfoById(Long fileId);
    User getReviewedByUserInfo(Long fileId);
    String checkFileProgress(long fileId);
}
