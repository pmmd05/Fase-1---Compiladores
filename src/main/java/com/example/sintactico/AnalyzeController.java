package com.example.sintactico;

import com.example.sintactico.dto.AnalyzeRequest;
import com.example.sintactico.dto.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
