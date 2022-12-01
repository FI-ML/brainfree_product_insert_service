package eu.brainfree.product.insert.service.controller;

import eu.brainfree.product.insert.service.logic.FileService;
import eu.brainfree.product.insert.service.model.FileStatusTO;
import eu.brainfree.product.insert.service.model.TemplateTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Max on 05.08.2022
 * @project eu.brainfree.product.insert.service
 * @date 05.08.2022
 **/

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200","https://insert.brainfree.eu","https://brainfree.eu/*"})
@RequestMapping(path = "/generator/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileGeneratorController {

    private final FileService fileService;


    @PostMapping
    public ResponseEntity<FileStatusTO> upload(@RequestBody TemplateTO to) throws Exception {
        return ResponseEntity.ok(fileService.createAndGetStatus(to));
    }

    @GetMapping(value = "/download/{withSQL}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable boolean withSQL) throws IOException {

        InputStream inputStream = fileService.getFileAsInputStream(withSQL);

        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        String headerValue = "attachment; filename=\"" + "insert-product" + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(new InputStreamResource(inputStream));
    }
}
