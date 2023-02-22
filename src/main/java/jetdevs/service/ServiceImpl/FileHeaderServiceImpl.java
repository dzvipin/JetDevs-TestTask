package jetdevs.service.ServiceImpl;


import jetdevs.model.FileHeaders;
import jetdevs.repository.FileHeaderRepository;
import jetdevs.service.FileHeaderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FileHeaderServiceImpl extends BaseServiceImpl implements FileHeaderService {

    private FileHeaderRepository fileHeaderRepository;

    @Transactional
    @Override
    public FileHeaders save(final FileHeaders documentHeader) {
        return fileHeaderRepository.save(documentHeader);
    }

    @Transactional
    @Override
    public void saveAll(final List<FileHeaders> fileHeaders) {
        fileHeaderRepository.saveAll(fileHeaders);
    }
}
