package edu.sjsu.cs151.blackjack.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlackjackGameSave {
    private ObjectMapper objectMapper;
    private final String SAVE_PATH = "src/main/java/edu/sjsu/cs151/blackjack/saves/";

    public BlackjackGameSave() {
        this.objectMapper = new ObjectMapper();
    }

    public String save(BlackjackGame game) {
        File saveFolder = new File(SAVE_PATH);
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }

        File saveFilePath = new File(SAVE_PATH, "blackjack-save-" + new Date().toString());
        
        try {
            objectMapper.writeValue(saveFilePath, game);
            return String.valueOf(saveFilePath.toString().hashCode());
        } catch (IOException e) {
            e.getMessage();
        }
        
        return "error: saved failed";
    }

    public BlackjackGame load(String saveStateString) throws FileNotFoundException {
        File saveFolder = new File(SAVE_PATH);
        if (!saveFolder.exists()) {
            throw new FileNotFoundException("Save file does not exist");
        }

        File[] saveFiles = saveFolder.listFiles();
        for (File file : saveFiles) {
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
