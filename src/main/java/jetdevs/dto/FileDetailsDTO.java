package jetdevs.dto;



import jetdevs.model.FileHeaders;
import jetdevs.model.FileRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
public class FileDetailsDTO {
    private Map<Integer, FileHeaders> fileHeaders = new HashMap<>();
    private Map<Integer, ArrayList<FileRecords>> fileRecords =  new HashMap<>();
}

