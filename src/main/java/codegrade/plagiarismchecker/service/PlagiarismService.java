package codegrade.plagiarismchecker.service;

import codegrade.plagiarismchecker.utils.FileSpace;
import codegrade.plagiarismchecker.utils.PlagiarismRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter @Setter
public class PlagiarismService {

    private final MossService mossService;
    private final HTMLParserService parserService;

    public String generateReportURLByLanguage(String language, Map<String, String> data) {
        FileSpace space = FileSpace.create(UUID.randomUUID().toString());
        data.forEach((userId, source) -> {
            try {
                space.addFile(userId, language, source);
            } catch (IOException e) {
                throw new RuntimeException("failed to create files");
            }
        });
        String reportURL = mossService.getReportURL(language, space.getFiles());
        space.delete();
        return reportURL;
    }

    public Object generateReport(PlagiarismRequest data) {
        return data.getLanguages()
                .parallelStream()
                .map(lang -> generateReportURLByLanguage(lang, data.getByLanguage(lang)))
                .filter(Objects::nonNull)
                .map(parserService::getPlagiarismReportData)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
