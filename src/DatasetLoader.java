import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatasetLoader {
    
    /**
     * Load patient data from a CSV file.
     * 
     * @param filePath The path to the CSV file
     * @return List of Patient objects
     */
    public static List<Patient> loadTrainingData(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty.");
        }
        
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("The file must be in CSV format.");
        }
        
        List<Patient> patients = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Read header line
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("The file is empty.");
            }
            
            // Expected CSV headers
            String[] expectedHeaders = {
                "Is_Male", "Age", "Height", "Weight", "Familial_Overweight_History",
                "High_Caloric_Food", "Vegetables_In_Meals", "Daily_Main_Meals", "Smoking",
                "Water_Consumption", "Calories_Monitoring", "Physical_Activity", "Screen_Time", "BMI",
                "Eat_Between_Meals_Always", "Eat_Between_Meals_Frequently", "Eat_Between_Meals_No", "Eat_Between_Meals_Sometimes",
                "Alcohol_Consumption_Always", "Alcohol_Consumption_Frequently", "Alcohol_Consumption_No", "Alcohol_Consumption_Sometimes",
                "Transportation_Automobile", "Transportation_Bike", "Transportation_Motorbike", "Transportation_Public_Transportation",
                "Transportation_Walking", "Obesity_Level"
            };
            
            String[] actualHeaders = headerLine.split(",");
            validateHeaders(expectedHeaders, actualHeaders);
            
            // Read data lines
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (values.length == expectedHeaders.length) {
                    Patient patient = createPatientFromValues(values);
                    patients.add(patient);
                } else {
                    System.err.println("Skipping invalid line due to incorrect number of values: " + line);
                }
            }
            
            System.out.println("Data loading complete. " + patients.size() + " patients loaded.");
            
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
        
        return patients;
    }
    

    private static void validateHeaders(String[] expectedHeaders, String[] actualHeaders) {
        if (actualHeaders.length != expectedHeaders.length) {
            throw new IllegalArgumentException("The number of columns in the file does not match the expected format.");
        }
        
        for (int i = 0; i < expectedHeaders.length; i++) {
            if (!actualHeaders[i].trim().equalsIgnoreCase(expectedHeaders[i])) {
                throw new IllegalArgumentException("Column " + (i+1) + " should be '" + 
                    expectedHeaders[i] + "' but found '" + actualHeaders[i].trim() + "'");
            }
        }
    }
    
    /**
     * Convert a CSV row into a Patient object.
     * 
     * @param values The values from a row in the CSV
     * @return A Patient object
     */
    private static Patient createPatientFromValues(String[] values) {
        try {
            return new Patient(
                Integer.parseInt(values[0].trim()),   // isMale
                Double.parseDouble(values[1].trim()), // age
                Double.parseDouble(values[2].trim()), // height
                Double.parseDouble(values[3].trim()), // weight
                Integer.parseInt(values[4].trim()),   // familialOverweightHistory
                Integer.parseInt(values[5].trim()),   // highCaloricFood
                Double.parseDouble(values[6].trim()), // vegetablesInMeals
                Double.parseDouble(values[7].trim()), // dailyMainMeals
                Integer.parseInt(values[8].trim()),   // smoking
                Double.parseDouble(values[9].trim()), // waterConsumption
                Integer.parseInt(values[10].trim()),  // caloriesMonitoring
                Double.parseDouble(values[11].trim()),// physicalActivity
                Double.parseDouble(values[12].trim()),// screenTime
                Double.parseDouble(values[13].trim()),// bmi
                Integer.parseInt(values[14].trim()),  // eatBetweenMealsAlways
                Integer.parseInt(values[15].trim()),  // eatBetweenMealsFrequently
                Integer.parseInt(values[16].trim()),  // eatBetweenMealsNo
                Integer.parseInt(values[17].trim()),  // eatBetweenMealsSometimes
                Integer.parseInt(values[18].trim()),  // alcoholConsumptionAlways
                Integer.parseInt(values[19].trim()),  // alcoholConsumptionFrequently
                Integer.parseInt(values[20].trim()),  // alcoholConsumptionNo
                Integer.parseInt(values[21].trim()),  // alcoholConsumptionSometimes
                Integer.parseInt(values[22].trim()),  // transportationAutomobile
                Integer.parseInt(values[23].trim()),  // transportationBike
                Integer.parseInt(values[24].trim()),  // transportationMotorbike
                Integer.parseInt(values[25].trim()),  // transportationPublicTransportation
                Integer.parseInt(values[26].trim()),  // transportationWalking
                Integer.parseInt(values[27].trim())   // obesityLevel
            );
        } catch (NumberFormatException e) {
            System.err.println("Error converting numeric value: " + e.getMessage());
            throw e;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: The line does not contain enough values.");
            throw e;
        }
    }
}