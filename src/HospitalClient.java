import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class HospitalClient {
    public static void main(String[] args) {
        try {
            // Connect to RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 7777);
            MedicalService service = (MedicalService) registry.lookup("MedicalService");

            // Load hospital's patient data (200 samples per hospital)
            List<Patient> patients = DatasetLoader.loadTrainingData("hospital_data.csv");
            service.sendPatientData(patients);
            System.out.println("Sent " + patients.size() + " patient records to Medical Center.");

            // Wait for training completion (In real-world, this would be asynchronous)
            Thread.sleep(5000);

            // Request predictions for remaining 111 patients
            List<Patient> testPatients = DatasetLoader.loadTrainingData("test_data.csv");
            for (Patient p : testPatients) {
                String prediction = service.predictObesity(p);
                System.out.println("Prediction for patient: " + prediction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
