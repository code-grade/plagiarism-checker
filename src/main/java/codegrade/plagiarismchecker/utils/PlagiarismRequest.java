package codegrade.plagiarismchecker.utils;

import lombok.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlagiarismRequest {

    private String assignmentId;
    private String questionId;
    private Map<String, SourceCode> sources;

    public Set<String> getLanguages() {
        return this.sources.values().stream()
                .map(SourceCode::getLanguage)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    public Map<String, String> getByLanguage(String language) {
        return this.sources.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getLanguage(), language))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        en -> en.getValue().getSource()
                ));
    }
}
