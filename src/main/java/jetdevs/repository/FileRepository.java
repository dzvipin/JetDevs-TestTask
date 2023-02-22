package jetdevs.repository;


import jetdevs.model.UploadFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends PagingAndSortingRepository<UploadFile, Long> {

}
