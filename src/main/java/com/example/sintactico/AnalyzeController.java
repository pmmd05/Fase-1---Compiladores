package com.example.sintactico;

import com.example.sintactico.dto.AnalyzeRequest;
import com.example.sintactico.dto.AnalyzeResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")  // ajusta si tu frontend corre en otra URL
public class AnalyzeController {

    private final AnalyzeService service;

    @Autowired
    public AnalyzeController(AnalyzeService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public AnalyzeResponse analyze(@RequestBody AnalyzeRequest request) {
        return service.analyze(request);
    }

    @GetMapping("/ir/{filename}")
    public ResponseEntity<Resource> downloadIr(@PathVariable String filename) throws IOException {
        Path file = Path.of("generated_ir", filename);
        if (!Files.exists(file)) return ResponseEntity.notFound().build();
        Resource res = new UrlResource(file.toUri());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(res);
    }

}