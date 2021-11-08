package codegrade.plagiarismchecker;

import it.zielke.moji.MossException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;


import java.io.IOException;

@Getter
@Setter
@EnableAsync
@RequiredArgsConstructor
@EnableConfigurationProperties
@SpringBootApplication
public class PlagiarismCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlagiarismCheckerApplication.class, args);
    }

}
