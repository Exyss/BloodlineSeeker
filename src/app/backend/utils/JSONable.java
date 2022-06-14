package app.backend.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

/**
 * This is an interface which makes the classes that implement it serializable.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public interface JSONable {
	
	/**
	 * A method that must be implemented by the classes that use this interface.
	 */
    public JSONObject toJSONObject();

    /**
     * Calls the toJSONObject method and save it in a File.
     * @param outputPath The path of the output file.
     * @throws IOException if the given file doesn't exists.
     */
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