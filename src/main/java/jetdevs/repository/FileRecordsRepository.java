package jetdevs.repository;



import jetdevs.model.FileRecords;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRecordsRepository extends PagingAndSortingRepository<FileRecords, Long> {

    List<FileRecords> findByFileId(long id);

}


