import java.util.List;

public class TestDatasetLoader {
    public static void main(String[] args) {
        try {
            // Chemin vers votre fichier CSV
            String filePath = "ObesityDataSet_raw_and_data_sinthetic.csv";
            
            // Chargement des données
            List<Patient> patients = DatasetLoader.loadTrainingData(filePath);
            
            // Affichage des premières entrées pour vérification
            System.out.println("Nombre total de patients: " + patients.size());
            System.out.println("\nAperçu des 5 premières entrées:");
            
            int count = 0;
            for (Patient patient : patients) {
                if (count++ >= 10) break;
                System.out.println("-------------------------------------");
                System.out.println("Patient #" + count + ":");
                System.out.println("Genre: " + patient.getGender());
                System.out.println("Âge: " + patient.getAge());
                System.out.println("Taille: " + patient.getHeight() + " m");
                System.out.println("Poids: " + patient.getWeight() + " kg");
                System.out.println("IMC calculé: " + patient.calculateBMI());
                System.out.println("Historique familial d'obésité: " + (patient.isFamilyHistoryWithOverweight() ? "Oui" : "Non"));
                System.out.println("Mode de transport: " + patient.getMtrans());
                System.out.println("Catégorie de poids: " + patient.getNObeyesdad());
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des données: " + e.getMessage());
            e.printStackTrace();
        }
    }
}