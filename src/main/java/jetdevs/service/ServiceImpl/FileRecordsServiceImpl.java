package jetdevs.service.ServiceImpl;


import jetdevs.model.FileRecords;
import jetdevs.repository.FileRecordsRepository;
import jetdevs.service.FileRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FileRecordsServiceImpl extends BaseServiceImpl implements FileRecordService {

    private FileRecordsRepository fileRecordsRepository;

    @Transactional
    @Override
    public void saveAll(final List<FileRecords> fileRecords) {
        fileRecordsRepository.saveAll(fileRecords);
    }
}
