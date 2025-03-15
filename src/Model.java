import weka.classifiers.trees.RandomForest;
import weka.core.Instances;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Model implements Serializable {
    private static final long serialVersionUID = 1L;

    private RandomForest model;
    private Instances datasetStructure;
    private String version;
    private LocalDateTime timestamp;

    private static final String VERSION_FILE = "model_versions.txt";
    private static final String MODEL_PREFIX = "trained_model_";
    private static final String MODEL_EXTENSION = ".ser";

    public Model(RandomForest model, Instances datasetStructure, String version) {
        this.model = model;
        this.datasetStructure = datasetStructure;
        this.version = version;
        this.timestamp = LocalDateTime.now();
    }

    public RandomForest getModel() {
        return model;
    }

    public Instances getDatasetStructure() {
        return datasetStructure;
    }

    public String getVersion() {
        return version;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private static String getNextVersion() throws IOException {
        if (!Files.exists(Paths.get(VERSION_FILE))) {
            return "v1.0";  // If no version file exists, start from v1.0
        }

        List<String> versions = Files.readAllLines(Paths.get(VERSION_FILE));

        if (versions.isEmpty()) {
            return "v1.0";
        }

        // Extract the latest version and increment
        String lastVersion = versions.get(versions.size() - 1);
        String[] parts = lastVersion.substring(1).split("\\."); // Remove 'v' and split

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);

        minor++; // Increment minor version
        if (minor >= 10) {  // If minor reaches 10, increment major
            major++;
            minor = 0;
        }

        return "v" + major + "." + minor;
    }

    public static void saveModel(Model modelStorage) throws IOException {
        String version = getNextVersion();
        modelStorage.version = version;
        String filePath = MODEL_PREFIX + version + MODEL_EXTENSION;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(modelStorage);
        }

        // Update the version history
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VERSION_FILE, true))) {
            writer.write(version);
            writer.newLine();
        }

        System.out.println("Model saved as: " + filePath + " (Version: " + version + ")");
    }
    public static Model loadLatestModel() throws IOException, ClassNotFoundException {
        if (!Files.exists(Paths.get(VERSION_FILE))) {
            throw new FileNotFoundException("No version file found.");
        }

        List<String> versions = Files.readAllLines(Paths.get(VERSION_FILE));
        if (versions.isEmpty()) {
            throw new FileNotFoundException("No saved model versions found.");
        }

        String latestVersion = versions.get(versions.size() - 1);
        String filePath = MODEL_PREFIX + latestVersion + MODEL_EXTENSION;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Model modelStorage = (Model) ois.readObject();
            System.out.println("Loaded model version: " + latestVersion);
            return modelStorage;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "ModelStorage{" +
                "version='" + version + '\'' +
                ", timestamp=" + timestamp.format(formatter) +
                '}';
    }
}
