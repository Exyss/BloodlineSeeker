package app.backend.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public interface JSONable {
    public JSONObject toJSONObject();

    default public void toJSONFile(String outputPath) throws IOException {

        File dir = new File(outputPath);

        //Create dirs recursively if not existing
        if (outputPath.contains("/") && !dir.exists()) {
            Files.createDirectories(Paths.get(dir.getParent()));
        }

        FileWriter file = new FileWriter(outputPath);

        file.write(this.toJSONObject().toString());

        file.close();
    }
}