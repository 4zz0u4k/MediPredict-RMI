import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class HospitalClient {
    public static void main(String[] args) {
        try {
            // Connect to RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 7777);
            MedicalService service = (MedicalService) registry.lookup("MedicalService");

            Scanner scanner = new Scanner(System.in);

            // Ask for hospital ID
            System.out.print("Enter Hospital ID: ");
            int hospital_id = scanner.nextInt();
            scanner.nextLine(); // Consume newline


            String datasetsPath = "Data/Datasets/";
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

                        String hospitalAnnotatedDataset = datasetsPath + "hospital_" + hospital_id + "dataset_.csv";
                        List<Patient> patients = DatasetLoader.loadTrainingData(hospitalAnnotatedDataset);
                        service.sendPatientData(patients);
                        System.out.println("✅ Sent " + patients.size() + " patient records to Medical Center.");
                        break;

                    case 2:
                        // TODO : Run Some test cases

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
