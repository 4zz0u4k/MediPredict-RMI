import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalClient {
    public static void main(String[] args) {
        String trainDatasetsPath = "data/split/train/";
        String testDatasetsPath = "data/split/test/";

        try {
            // Connect to RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 7777);
            MedicalService service = (MedicalService) registry.lookup("MedicalService");

            Scanner scanner = new Scanner(System.in);

            // Ask for hospital ID
            System.out.print("Enter Hospital ID: ");
            int hospital_id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String hospitalAnnotatedDataset = trainDatasetsPath + "hospital_" + hospital_id + ".csv";
            List<Patient> patients = DatasetLoader.loadTrainingData(hospitalAnnotatedDataset);
            int sentBatch = 1;
            String hospitalTestDataset = testDatasetsPath + "hospital_" + hospital_id + ".csv";
            List<Patient> testPatients = DatasetLoader.loadTrainingData(hospitalTestDataset);

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
                        List<Patient> patientsBatch = new ArrayList<>(patients.subList((sentBatch - 1) * 100, Math.min((sentBatch) * 100, patients.size())));
                        service.sendPatientData(patientsBatch);
                        System.out.println("✅ Sent " + patientsBatch.size() + " patient records to Medical Center.\nBatch : " + sentBatch );
                        sentBatch++;
                        break;

                    case 2:

                        int correctPredictions = 0;
                        String divider = "═══════════════════════════════════════════════════════════════════════════";

                        if (testPatients.size() > 0) {
                            Patient firstPatient = testPatients.get(0);
                            String firstPrediction = service.predictObesity(firstPatient).replace('_', ' ');

                            if (firstPrediction.equals("None")) {
                                System.out.println("\n" + divider);
                                System.out.println("║ ⚠️ MODEL ERROR                                                          ║");
                                System.out.println(divider);
                                System.out.println("║ No trained model available! Please train the model before testing.      ║");
                                System.out.println("╘═════════════════════════════════════════════════════════════════════════╛");
                                break; // Exit the method or use break if within a loop
                            }
                        }
                        System.out.println("\n" + divider);
                        System.out.println("║  🔍 PREDICTION RESULTS                                                  ║");
                        System.out.println(divider);
                        System.out.println("║ #   │ PREDICTED CATEGORY        │ ACTUAL CATEGORY           │ RESULT    ║");
                        System.out.println("╟─────┼───────────────────────────┼───────────────────────────┼───────────╢");

                        for (int i = 0; i < testPatients.size(); i++) {
                            Patient testPatient = testPatients.get(i);
                            String predictedValue = service.predictObesity(testPatient).replace('_', ' ');
                            String realValue = testPatient.getObesityCategory();

                            boolean isCorrect = predictedValue.equals(realValue);
                            if (isCorrect) {
                                correctPredictions++;
                            }

                            // Format the output row
                            String index = String.format("%-3d", i + 1);
                            String predicted = fitString(predictedValue, 25);
                            String actual = fitString(realValue, 25);
                            String result = isCorrect ? "✅ Match" : "❌ Miss ";

                            System.out.printf("║ %s │ %s │ %s │ %s ║\n", index, predicted, actual, result);
                        }

                        System.out.println("╘═════╧═══════════════════════════╧═══════════════════════════╧══════════╛");

                        double accuracy = (double) correctPredictions / testPatients.size() * 100;
                        System.out.println("\n📊 PREDICTION SUMMARY:");
                        System.out.printf("  ✓ Correct predictions: %d/%d (%.1f%%)\n",
                                correctPredictions, testPatients.size(), accuracy);
                        System.out.printf("  ✗ Incorrect predictions: %d/%d (%.1f%%)\n",
                                testPatients.size() - correctPredictions, testPatients.size(),
                                100 - accuracy);

                        System.out.println("\n📏 Accuracy gauge:");
                        System.out.println("  " + generateAccuracyGauge(accuracy, 50));
                        System.out.println(divider);

                        break;

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
    private static String fitString(String input, int length) {
        if (input == null) {
            input = "N/A";
        }

        if (input.length() > length) {
            return input.substring(0, length - 3) + "...";
        } else {
            return String.format("%-" + length + "s", input);
        }
    }

    // Helper method to generate an accuracy gauge
    private static String generateAccuracyGauge(double percentage, int length) {
        int filledLength = (int) (percentage * length / 100);
        StringBuilder gauge = new StringBuilder("[");

        for (int i = 0; i < length; i++) {
            if (i < filledLength) {
                gauge.append("█");
            } else {
                gauge.append("░");
            }
        }

        gauge.append("] ").append(String.format("%.1f%%", percentage));
        return gauge.toString();
    }
}
