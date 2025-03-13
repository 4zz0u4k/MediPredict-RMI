import java.rmi.RemoteException;
import java.util.List;

public class TestTrainModel {
    public static void main(String[] args) {
        // Update this path to point to your CSV file.
        String csvPath = "../data/processed/dataset.csv";
        
        // Load patient data from CSV using DatasetLoader
        List<Patient> patients = DatasetLoader.loadTrainingData(csvPath);
        System.out.println("Loaded " + patients.size() + " patient records.");
        
        try {
            // Create an instance of MedicalServiceImpl
            MedicalServiceImpl service = new MedicalServiceImpl();
            
            // Send the training data to the service
            service.sendPatientData(patients);
            
            // Test prediction using the first patient from the dataset.
            if (!patients.isEmpty()) {
                int test_patient = 15;
                String prediction = service.predictObesity(patients.get(test_patient));
                System.out.println("Prediction for test patient: " + prediction);
            } else {
                System.out.println("No patient records available for prediction.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
