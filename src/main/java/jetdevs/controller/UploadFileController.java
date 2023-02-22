package jetdevs.controller;


import jetdevs.constants.ErrorCodes;
import jetdevs.exception.FileUploadException;
import jetdevs.model.UploadFile;
import jetdevs.service.ServiceImpl.FileUploadServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class UploadFileController {

    private final FileUploadServiceImpl fileUploadService;

    @RequestMapping(method = RequestMethod.POST, path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadFile> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (file != null) {
            return ResponseEntity.ok(fileUploadService.readFile(file.getInputStream(), file.getOriginalFilename()));
        }
        throw new FileUploadException(HttpStatus.NOT_FOUND.value(), ErrorCodes.FILE_NOT_EXIST);
    }
}