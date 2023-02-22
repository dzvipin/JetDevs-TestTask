package jetdevs.service.ServiceImpl;


import jetdevs.constants.ErrorCodes;
import jetdevs.dto.FileDetailsDTO;
import jetdevs.exception.FileUploadException;
import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import jetdevs.model.UploadFile;
import jetdevs.service.FileHeaderService;
import jetdevs.service.FileRecordService;
import jetdevs.service.FileService;
import jetdevs.service.FileUploadService;
import jetdevs.utils.FileHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileUploadServiceImpl extends BaseServiceImpl implements FileUploadService {

    private FileService fileService;
    private FileRecordService fileRecordService;
    private FileHeaderService fileHeaderService;

    @Transactional
    @Override
    public UploadFile readFile(InputStream inputStream, String fileName) {
        if (!FilenameUtils.isExtension(fileName, new String[]{"xls", "xlsx"})) {
            throw new FileUploadException(HttpStatus.BAD_REQUEST.value(), ErrorCodes.FILE_FORMAT_NOT_ACCEPTED);
        }
        FileDetailsDTO fileRecords = FileHelper.excelToFile(inputStream);
        UploadFile inProgressFile = fileService.save(prepareInProgressFileDetails(fileName));
        if (CollectionUtils.isEmpty(fileRecords.getFileRecords())) {
            throw new FileUploadException(HttpStatus.BAD_REQUEST.value(), ErrorCodes.FILE_EMPTY);
        }
        Set<Integer> totalHeaders = fileRecords.getFileRecords().keySet();
        AtomicInteger totalUploadedRecords = new AtomicInteger();
        totalHeaders.forEach(headerIndex -> {
            FileHeaders fileHeader = fileRecords.getFileHeaders().get(headerIndex);
            ArrayList<FileRecords> uploadedFileRecords = fileRecords.getFileRecords().get(headerIndex);
            List<FileRecords> validatedRecords = validateRecords(uploadedFileRecords);
            FileHeaders fileHeaders = fileHeaderService.save(prepareFileHeader(fileHeader, inProgressFile));
            fileRecordService.saveAll(prepareFileRecords(validatedRecords, inProgressFile, fileHeaders));
            totalUploadedRecords.addAndGet(validatedRecords.size());
        });
        return fileService.save(fileUploadedSuccessfully(totalUploadedRecords.get(), inProgressFile, totalHeaders.size()));
    }


    private List<FileRecords> validateRecords(List<FileRecords> fileRecords) {
        return fileRecords
                .stream()
                .filter(fileRecord -> StringUtils.isNotBlank(fileRecord.getRecordName()))
                .collect(Collectors.toList());
    }


    private List<FileRecords> prepareFileRecords(List<FileRecords> fileRecordsList, UploadFile file, FileHeaders fileHeaders) {
        return fileRecordsList
                .stream()
                .map(book -> book
                        .toBuilder()
                        .file(file)
                        .header(fileHeaders)
                        .build())
                .collect(Collectors.toList());
    }


    private UploadFile prepareInProgressFileDetails(String fileName) {
        return new UploadFile()
                .toBuilder()
                .fileName(fileName)
                .status(UploadFile.FILE_STATUS.IN_PROGRESS.name())
                .userId(Math.toIntExact(getAuthenticatedUser().getId()))
                .build();
    }

    private UploadFile fileUploadedSuccessfully(int totalValidRecords, UploadFile uploadFile, int totalHeaders) {
        return uploadFile
                .toBuilder()
                .totalUploaded(totalValidRecords)
                .totalRecords(totalValidRecords)
                .totalHeaders(totalHeaders)
                .status(UploadFile.FILE_STATUS.COMPLETED.name())
                .build();
    }

    private FileHeaders prepareFileHeader(FileHeaders fileHeader, UploadFile file) {
        return fileHeader
                .toBuilder()
                .file(file)
                .build();
    }
}