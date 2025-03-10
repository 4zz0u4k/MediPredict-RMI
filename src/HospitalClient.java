import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class HospitalClient {
    public static void main(String[] args) {
        String datasetsPath = "Data/Datasets/";


        try {
            // Connect to RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 7777);
            MedicalService service = (MedicalService) registry.lookup("MedicalService");

            Scanner scanner = new Scanner(System.in);

            // Ask for hospital ID
            System.out.print("Enter Hospital ID: ");
            int hospital_id = scanner.nextInt();
            scanner.nextLine(); // Consume newline


            boolean running = true;

            while (running) {
                System.out.println("\n===== Hospital Client Menu =====");
                System.out.println("1. Send patient data to Medical Center");
                System.out.println("2. Request obesity prediction");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:

                        String hospitalAnnotatedDataset = datasetsPath + "hospital_" + hospital_id + "_test_dataset_.csv";
                        List<Patient> patients = DatasetLoader.loadTrainingData(hospitalAnnotatedDataset);
                        service.sendPatientData(patients);
                        System.out.println("✅ Sent " + patients.size() + " patient records to Medical Center.");
                        break;

                    case 2:
                        String hospitalTestDataset = datasetsPath + "hospital_" + hospital_id + "_test_dataset_.csv";
                        List<Patient> testPatients = DatasetLoader.loadTrainingData(hospitalTestDataset);
                        int correctPredictions = 0;
                        int totalPredictions = testPatients.size();

                        for (Patient testPatient : testPatients) {
                            Patient patient = new Patient(testPatient);
                            String predictedValue = service.predictObesity(patient);
                            String realValue = testPatient.getNObeyesdad();

                            if (predictedValue.equals(realValue)) {
                                correctPredictions++;
                            }
                        }
                        double accuracy = (double) correctPredictions / totalPredictions * 100;

                        System.out.println("Total Predictions: " + totalPredictions);
                        System.out.println("Correct Predictions: " + correctPredictions);
                        System.out.println("Accuracy: " + accuracy + "%");

                    case 3:
                        System.out.println("Exiting... Thank you!");
                        running = false;
                        break;

                    default:
                        System.out.println("⚠ Invalid choice. Please try again.");
                        break;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
