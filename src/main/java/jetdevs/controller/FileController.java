package jetdevs.controller;


import jetdevs.model.UploadFile;
import jetdevs.service.FileService;
import jetdevs.service.ServiceImpl.FileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @RequestMapping(method = RequestMethod.GET, path = "/status/{fileId}")
    public ResponseEntity<?> checkFileProgress(@PathVariable("fileId") int fileId) {
        try {
            return ResponseEntity.ok(fileService.checkFileProgress(fileId));}
        catch (Exception exception){
            return  ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{fileId}")
    public ResponseEntity<?> getFileInfoById(@PathVariable("fileId") long fileId) {
        try {
            return ResponseEntity.ok(fileService.getFileInfoById(fileId));}
        catch (Exception exception){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "reviewedUserInfo/{fileId}")
    public ResponseEntity<?> getReviewedByUserInfo(@PathVariable("fileId") long fileId) {
        try {
            return ResponseEntity.ok(fileService.getReviewedByUserInfo(fileId));}
        catch (Exception exception){
            return  ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET,path = "getAll")
    public ResponseEntity<List<UploadFile>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{fileId}")
    public void deleteFileById(@PathVariable("fileId") int fileId) {
        fileService.delete(fileId);
    }


}