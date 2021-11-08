package codegrade.plagiarismchecker.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class FileSpace {

    private final String name;
    private File directory;
    private List<File> files;

    private static String STORAGE_DIR = Path.of("storage").toString();

    public static void _delete(File f) throws IOException {
        if (f == null) throw new FileNotFoundException("Failed to delete file");
        if (f.isDirectory()) {
            for (File c : Objects.requireNonNull(f.listFiles())) _delete(c);
        }
        if (!f.delete()) throw new FileNotFoundException("Failed to delete file: " + f);
    }

    private static File _create_temp_directory(String name) {
        try {
            Path temporary_path = Path.of(".", "storage", name);
            Files.deleteIfExists(temporary_path);
            File tempDir = Files.createTempDirectory(Path.of(".", "storage"), name).toFile();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("couldn't create file space");
        }
    }

    public static FileSpace create(String name) {
        FileSpace instance = new FileSpace(name);
        instance.directory = _create_temp_directory(name);
        return instance;
    }


    private FileSpace(String name) {
        this.name = name;
        this.files = new ArrayList<>();
    }

    public void delete() {
        try {
            _delete(this.directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFile(String name, String type, String content) throws IOException {

        String ext = ".code";
        if (Objects.equals(type, "python")) {
            ext = ".py";
        }

        // create temporary file
        String dirPath = this.directory.getAbsolutePath();
        File tempDir = Files.createTempDirectory(Path.of(dirPath), name).toFile();
        File tempFile = Files.createTempFile(Path.of(tempDir.getAbsolutePath()), "solution", ext).toFile();
        tempDir.deleteOnExit();
        tempFile.deleteOnExit();

        // write content to the file
        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
        fw.close();

        // add temporary file to list
        this.files.add(tempFile);
    }

}
