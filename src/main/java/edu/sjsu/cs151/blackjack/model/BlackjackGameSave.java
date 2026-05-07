package edu.sjsu.cs151.blackjack.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlackjackGameSave {
    private static final String DEFAULT_SAVE_PATH = "src/main/java/edu/sjsu/cs151/blackjack/saves/"; // for Mac/Linux
    private final ObjectMapper objectMapper;
    private final String savePath;

    public BlackjackGameSave() {
        this(DEFAULT_SAVE_PATH);
    }

     // appends File.separator when missing so paths work on Windows vs Mac/Linux.
    public BlackjackGameSave(String saveDirectory) {
        this.objectMapper = new ObjectMapper();
        String base = saveDirectory;
        if (!base.endsWith("/") && !base.endsWith(File.separator)) {
            base = base + File.separator;
        }
        this.savePath = base;
    }

    public String save(BlackjackGame game) {
        File saveFolder = new File(savePath);
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }


        File saveFilePath = new File(savePath, "blackjack-save-" + System.currentTimeMillis() + ".json");

        try {
            objectMapper.writeValue(saveFilePath, game);
            return String.valueOf(saveFilePath.toString().hashCode());
        } catch (IOException e) {
            e.getMessage();
        }

        return "error: saved failed";
    }

    public BlackjackGame load(String saveStateString) throws FileNotFoundException {
        File saveFolder = new File(savePath);
        if (!saveFolder.exists()) {
            throw new FileNotFoundException("Save file does not exist");
        }

        File[] saveFiles = saveFolder.listFiles();
        if (saveFiles == null) {
            return null;
        }
        for (File file : saveFiles) {
            if (!file.isFile()) {
                continue;
            }
            String hashString = String.valueOf(file.toString().hashCode());

            if (saveStateString.equals(hashString)) {
                try {
                    return objectMapper.readValue(file, BlackjackGame.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
