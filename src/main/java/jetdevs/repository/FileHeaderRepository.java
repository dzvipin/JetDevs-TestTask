package jetdevs.repository;


import jetdevs.model.FileHeaders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileHeaderRepository extends PagingAndSortingRepository<FileHeaders, Long> {

    List<FileHeaders> findByFileId(long id);
}
