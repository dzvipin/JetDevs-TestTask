package jetdevs.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "files")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UploadFile extends BaseEntity {

    private String fileName;
    private int totalRecords;
    private int totalHeaders;
    private int totalUploaded;
    private String status = null;
    private int userId;
    private int lastReviewedUserId;
    private LocalDateTime lastReviewedTime;

    public enum FILE_STATUS {
        IN_PROGRESS,
        COMPLETED
    }
}
