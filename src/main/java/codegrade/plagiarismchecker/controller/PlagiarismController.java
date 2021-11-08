package codegrade.plagiarismchecker.controller;

import codegrade.plagiarismchecker.service.HTMLParserService;
import codegrade.plagiarismchecker.service.PlagiarismService;
import codegrade.plagiarismchecker.utils.PlagiarismRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RestController
public class PlagiarismController {

    private final PlagiarismService plagiarismService;

    @PostMapping(path = "/report")
    public ResponseEntity<Object> plagiarismReport(@RequestBody PlagiarismRequest request) {
        var report = Map.of(
                "message", "Success",
                "data", plagiarismService.generateReport(request)
        );
        return ResponseEntity.ok(report);
    }
}
