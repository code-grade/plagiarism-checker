package codegrade.plagiarismchecker.utils;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceCode {

    private String source;
    private String language;

}