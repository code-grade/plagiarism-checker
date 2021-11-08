package codegrade.plagiarismchecker.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
public class HTMLParserService {

    @Data
    public static class MatchResult {
        private String first;
        private String second;
        private Long percentage;
        private Long lines;
    }

    private static Pattern row_data_pattern =
            Pattern.compile("^.+/([a-z0-9-]+)/.\\((\\d+)%\\)$", Pattern.CASE_INSENSITIVE);

    private static MatchResult rowToMatchResult(Element row) {
        var match = new MatchResult();
        var files = row.getElementsByTag("a")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());
        var matchFirst = row_data_pattern.matcher(files.get(0));
        var matchSecond = row_data_pattern.matcher(files.get(1));
        if (matchFirst.find() && matchSecond.find()) {
            match.setFirst(matchFirst.group(1));
            match.setSecond(matchSecond.group(1));
            match.setPercentage(Long.parseLong(matchFirst.group(2)));
            match.setLines(Long.parseLong(row.getElementsByTag("td").get(2).text()));
            return match;
        }
        return null;
    }

    public List<MatchResult> getPlagiarismReportData(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements  = document.getElementsByTag("tr");
            return elements
                    .stream()
                    .skip(1)
                    .map(HTMLParserService::rowToMatchResult)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
