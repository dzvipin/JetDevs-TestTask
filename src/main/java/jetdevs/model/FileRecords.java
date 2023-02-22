package jetdevs.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "file_records")
@NoArgsConstructor
public class FileRecords extends BaseEntity {

    private String recordName;

    @ManyToOne
    @JoinColumn(name = "header_id")
    @NotNull
    private FileHeaders header;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @NotNull
    private UploadFile file;

}

