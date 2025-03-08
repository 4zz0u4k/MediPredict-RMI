import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatasetLoader {
    
    /**
     * Charge les données d'entraînement à partir d'un fichier CSV
     * 
     * @param filePath 
     * @return Liste d'objets Patient
     */
    public static List<Patient> loadTrainingData(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Le chemin du fichier ne peut pas être vide");
        }
        
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Le fichier doit être au format CSV.");
        }
        
        List<Patient> patients = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Lecture de la ligne d'en-tête (pour vérifier le format)
            String headerLine = br.readLine();
            
            if (headerLine == null) {
                throw new IOException("Le fichier est vide");
            }
            
            // Vérification des colonnes attendues
            String[] expectedHeaders = {
                "Gender", "Age", "Height", "Weight", "family_history_with_overweight", 
                "FAVC", "FCVC", "NCP", "CAEC", "SMOKE", "CH2O", "SCC", "FAF", 
                "TUE", "CALC", "MTRANS", "NObeyesdad"
            };
            
            String[] actualHeaders = headerLine.split(",");
            validateHeaders(expectedHeaders, actualHeaders);
            
            // Lecture des données
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (values.length == expectedHeaders.length) {
                    Patient patient = createPatientFromValues(values);
                    patients.add(patient);
                } else {
                    System.err.println("Ligne ignorée car nombre de valeurs incorrect: " + line);
                }
            }
            
            System.out.println("Chargement de données terminé. " + patients.size() + " patients chargés.");
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV: " + e.getMessage(), e);
        }
        
        return patients;
    }
    

    private static void validateHeaders(String[] expectedHeaders, String[] actualHeaders) {
        if (actualHeaders.length != expectedHeaders.length) {
            throw new IllegalArgumentException("Le nombre de colonnes dans le fichier ne correspond pas au format attendu");
        }
        
        for (int i = 0; i < expectedHeaders.length; i++) {
            if (!actualHeaders[i].trim().equals(expectedHeaders[i])) {
                throw new IllegalArgumentException("La colonne " + (i+1) + " devrait être '" + 
                    expectedHeaders[i] + "' mais est '" + actualHeaders[i].trim() + "'");
            }
        }
    }
    
    /**
     * @param values Les valeurs d'une ligne
     * @return Un objet Patient
     */
    private static Patient createPatientFromValues(String[] values) {
        Patient patient = new Patient();
        
        try {

            patient.setGender(values[0].trim());
            patient.setAge(Double.parseDouble(values[1].trim()));
            patient.setHeight(Double.parseDouble(values[2].trim()));
            patient.setWeight(Double.parseDouble(values[3].trim()));
            patient.setFamilyHistoryWithOverweight(values[4].trim().equalsIgnoreCase("yes"));
            patient.setFavc(values[5].trim().equalsIgnoreCase("yes"));
            patient.setFcvc(Double.parseDouble(values[6].trim()));
            patient.setNcp(Double.parseDouble(values[7].trim()));
            patient.setCaec(values[8].trim());
            patient.setSmoke(values[9].trim().equalsIgnoreCase("yes"));
            patient.setCh2o(Double.parseDouble(values[10].trim()));
            patient.setScc(values[11].trim().equalsIgnoreCase("yes"));
            patient.setFaf(Double.parseDouble(values[12].trim()));
            patient.setTue(Double.parseDouble(values[13].trim()));
            patient.setCalc(values[14].trim());
            patient.setMtrans(values[15].trim());
            patient.setNObeyesdad(values[16].trim());
        } catch (NumberFormatException e) {
            System.err.println("Erreur lors de la conversion d'une valeur numérique: " + e.getMessage());
            throw e;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erreur: la ligne ne contient pas assez de valeurs");
            throw e;
        }
        
        return patient;
    }
}