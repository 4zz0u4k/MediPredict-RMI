import java.util.List;

public class TestDatasetLoader {
    public static void main(String[] args) {
        try {
            String filePath = "../data/processed/dataset.csv";
            int numEntriesToShow = 3; // Change this to show more or fewer entries

            List<Patient> patients = DatasetLoader.loadTrainingData(filePath);
            
            System.out.println("Total number of patients: " + patients.size());
            System.out.println("\nPreview of the first " + numEntriesToShow + " entries:");

            int count = 0;
            for (Patient patient : patients) {
                if (count++ >= numEntriesToShow) break;
                System.out.println("-------------------------------------");
                System.out.println("Patient #" + count + ":");
                System.out.println(patient); // Uses toString() method
            }
            
        } catch (Exception e) {
            System.err.println("Error while loading the dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}