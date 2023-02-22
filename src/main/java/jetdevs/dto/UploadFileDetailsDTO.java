package jetdevs.dto;


import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UploadFileDetailsDTO {
    private String fileName;
    private int totalRecords;
    private int totalHeaders;
    private int totalUploaded;
    private String status = null;
    private int userId;
    private int lastReviewedUserId;
    private Boolean deleted = Boolean.FALSE;
    private LocalDateTime lastReviewedTime;
    private List<FileHeaders> fileHeadersList;
    private List<FileRecords> fileRecordsList;
}
