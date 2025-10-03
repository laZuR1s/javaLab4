import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileService {

    private String currentFilePath;
    private long CurrentChecksum;
    private final Map<String, User> users = new LinkedHashMap<>();

    public void loadFile(String filePath) throws IOException, CustomExceptions.FileFormatException,
            CustomExceptions.ChecksumException {
        currentFilePath = filePath;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        if (lines.isEmpty()) {
            throw new CustomExceptions.FileFormatException("Пустой файл");
        }

        String checksumLine = lines.getFirst().trim();
        long fileChecksum;
        try {
            fileChecksum = Long.parseLong(checksumLine);
        } catch (NumberFormatException e) {
            throw new CustomExceptions.FileFormatException("Неверный формат контрольной суммы");
        }

    }
}
