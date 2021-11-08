package codegrade.plagiarismchecker.service;

import codegrade.plagiarismchecker.config.MossConfig;
import it.zielke.moji.MossException;
import it.zielke.moji.SocketClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Getter @Setter
public class MossService {

    private final MossConfig mossConfig;

    public String getReportURL(String language, List<File> files) {
        SocketClient socketClient = new SocketClient();
        socketClient.setUserID("954905329");
        try {
            socketClient.setLanguage(language);
            socketClient.run();
            for (var f : files) socketClient.uploadFile(f);
            socketClient.sendQuery();
            URL results = socketClient.getResultURL();
            return results.toString();
        } catch (MossException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while uploading files to moss");
        }
    }

}
