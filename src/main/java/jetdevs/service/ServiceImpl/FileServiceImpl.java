package jetdevs.service.ServiceImpl;


import jetdevs.constants.ErrorCodes;
import jetdevs.dto.UploadFileDetailsDTO;
import jetdevs.exception.FileUploadException;
import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import jetdevs.model.UploadFile;
import jetdevs.model.User;
import jetdevs.repository.FileHeaderRepository;
import jetdevs.repository.FileRecordsRepository;
import jetdevs.repository.FileRepository;
import jetdevs.service.FileHeaderService;
import jetdevs.service.FileRecordService;
import jetdevs.service.FileService;
import jetdevs.utils.UserHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileServiceImpl extends BaseServiceImpl implements FileService {

    private FileRepository fileRepository;
    private FileHeaderRepository fileHeaderRepository;
    private FileRecordsRepository fileRecordsRepository;
    private FileRecordService fileRecordsService;
    private FileHeaderService fileHeaderService;

    @Transactional
    @Override
    public UploadFile save(final UploadFile files) {
        return fileRepository.save(files);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UploadFile> getAllFiles() {
        return (List<UploadFile>) fileRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(long fileId) {
        UploadFile file = getById(fileId);
        List<FileHeaders> fileHeadersList = fileHeaderRepository.findByFileId(fileId);
        List<FileHeaders> updatedHeaderList = fileHeadersList
                .stream()
                .map(fileHeader -> fileHeader
                        .toBuilder().deleted(Boolean.TRUE)
                        .updatedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        List<FileRecords> fileRecordsList = fileRecordsRepository.findByFileId(fileId);
        List<FileRecords> updatedRecordsList = fileRecordsList
                .stream()
                .map(fileHeader -> fileHeader
                        .toBuilder().deleted(Boolean.TRUE)
                        .updatedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        fileHeaderService.saveAll(updatedHeaderList);
        fileRecordsService.saveAll(updatedRecordsList);
        save(file.toBuilder().deleted(Boolean.TRUE).updatedDate(LocalDateTime.now()).build());
    }

    @Transactional(readOnly = true)
    public UploadFile getById(Long id) throws FileUploadException {
        return fileRepository
                .findById(id)
                .orElseThrow(() -> new FileUploadException(HttpStatus.NOT_FOUND.value(), ErrorCodes.FILE_NOT_EXIST_ID));
    }

    @Transactional()
    @Override
    public UploadFileDetailsDTO getFileInfoById(Long fileId) throws FileUploadException {
        UploadFile file = getById(fileId);
        file.setLastReviewedTime(LocalDateTime.now());
        file.setLastReviewedUserId(getAuthenticatedUser().getId());
        fileRepository.save(file);
        List<FileHeaders> fileHeadersList = fileHeaderRepository.findByFileId(fileId);
        List<FileRecords> fileRecordsList = fileRecordsRepository.findByFileId(fileId);
        return new UploadFileDetailsDTO().toBuilder().fileName(file.getFileName()).totalRecords(file.getTotalRecords())
                .totalHeaders(file.getTotalHeaders())
                .status(file.getStatus())
                .deleted(file.getDeleted())
                .userId(file.getUserId()).totalUploaded(file.getTotalUploaded()).
                lastReviewedUserId(getAuthenticatedUser().getId()).lastReviewedTime(LocalDateTime.now()).
                fileHeadersList(fileHeadersList).fileRecordsList(fileRecordsList).
                build();
    }

    @Override
    public User getReviewedByUserInfo(Long fileId) {
        UploadFile file = getById(fileId);
        return UserHelper.getUserById(file.getLastReviewedUserId());
    }

    @Override
    public String checkFileProgress(long fileId) {
        return fileRepository
                .findById(fileId)
                .orElseThrow(() -> new FileUploadException(HttpStatus.NOT_FOUND.value(),
                        ErrorCodes.FILE_NOT_EXIST_ID)).getStatus();
    }
}



