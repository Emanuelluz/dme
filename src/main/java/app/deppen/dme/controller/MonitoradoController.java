package app.deppen.dme.controller;

import app.deppen.dme.config.ResponseMessage;
import app.deppen.dme.model.entity.Monitorado;
import app.deppen.dme.service.CSVService;
import app.deppen.dme.utils.CSVHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

//@Controller
@RestController
@RequestMapping("/api/csv")
public class MonitoradoController {

    final
    CSVService fileService;

    public MonitoradoController(CSVService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE,
                                                MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Upload simples arquivos")
    public ResponseEntity<ResponseMessage> uploadFileMonitorado(@RequestPart("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.saveMonitorado(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(Objects.requireNonNull(file.getOriginalFilename()))
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }


    @GetMapping("/monitorados")
    public ResponseEntity<List<Monitorado>> getAllMonitorados() {
        try {
            List<Monitorado> tutorials = fileService.getAllTutorials();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
