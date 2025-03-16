import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MedicalServiceImpl extends UnicastRemoteObject implements MedicalService {
    private List<Patient> trainingData = new ArrayList<>();
    private boolean modelTrained = false;
    private RandomForest model; // Store trained model
    private Instances datasetStructure; // Store dataset format

    protected MedicalServiceImpl() throws RemoteException {
        super();
    }

    // Add this field to track when we last trained the model
    private int lastTrainingSize = 0;

    @Override
    public void sendPatientData(List<Patient> patients) throws RemoteException {
        if (patients == null || patients.isEmpty()) {
            return;
        }

        System.out.println("Received " + patients.size() + " new patient records.");

        // Add the new data to our training set
        trainingData.addAll(patients);
        int totalRecords = trainingData.size();

        System.out.println("Current dataset size: " + totalRecords + " records");

        // Model training logic
        if (!modelTrained && totalRecords >= 1000) {
            // First-time training when we reach threshold
            System.out.println("Training initial model with " + totalRecords + " records...");
            trainModel();
            lastTrainingSize = totalRecords;
            modelTrained = true;
        } else if (modelTrained && newDataRequiresRetraining(totalRecords)) {
            // Retrain model when we have 1000+ new records
            System.out.println("Retraining model with updated dataset...");
            trainModel();
            lastTrainingSize = totalRecords;
        }

        // Display comprehensive statistics about the dataset
        printDatasetStatistics();

        if (modelTrained) {
            System.out.println("Model is trained and ready for predictions.");
        } else {
            System.out.println("Need " + (1000 - totalRecords) + " more records to train initial model.");
        }
    }

    // Helper method to decide if retraining is needed
    private boolean newDataRequiresRetraining(int currentSize) {
        // Retrain if we have 1000+ new records since last training
        return currentSize >= lastTrainingSize + 1000;
    }

    // Method to print comprehensive dataset statistics
    private void printDatasetStatistics() {
        if (trainingData.isEmpty()) return;

        int totalRecords = trainingData.size();

        // Obesity level distribution
        int[] obesityLevelCounts = new int[7]; // 0-6 levels
        for (Patient p : trainingData) {
            int level = p.getObesityLevel();
            if (level >= 0 && level < 7) {
                obesityLevelCounts[level]++;
            }
        }

        System.out.println("\nDataset Obesity Category Distribution:");
        for (int i = 0; i < obesityLevelCounts.length; i++) {
            String category = "";
            switch (i) {
                case 0: category = "Insufficient Weight"; break;
                case 1: category = "Normal Weight"; break;
                case 2: category = "Overweight Level I"; break;
                case 3: category = "Overweight Level II"; break;
                case 4: category = "Obesity Type I"; break;
                case 5: category = "Obesity Type II"; break;
                case 6: category = "Obesity Type III"; break;
            }

            double percent = (obesityLevelCounts[i] * 100.0) / totalRecords;
            System.out.printf("- %s: %d (%.1f%%)\n", category, obesityLevelCounts[i], percent);
        }

        // Gender distribution
        int maleCount = 0, femaleCount = 0;
        for (Patient p : trainingData) {
            if (p.getIsMale() == 1) maleCount++;
            else femaleCount++;
        }

        System.out.println("\nGender Distribution:");
        System.out.printf("- Male: %d (%.1f%%)\n", maleCount, (maleCount * 100.0) / totalRecords);
        System.out.printf("- Female: %d (%.1f%%)\n", femaleCount, (femaleCount * 100.0) / totalRecords);

        // Lifestyle factors
        int smokers = 0, caloriesMonitoring = 0, familialHistory = 0;
        double totalPhysicalActivity = 0, totalScreenTime = 0, totalWaterConsumption = 0;

        for (Patient p : trainingData) {
            if (p.getSmoking() == 1) smokers++;
            if (p.getCaloriesMonitoring() == 1) caloriesMonitoring++;
            if (p.getFamilialOverweightHistory() == 1) familialHistory++;

            totalPhysicalActivity += p.getPhysicalActivity();
            totalScreenTime += p.getScreenTime();
            totalWaterConsumption += p.getWaterConsumption();
        }

        System.out.println("\nLifestyle Factors:");
        System.out.printf("- Smokers: %d (%.1f%%)\n", smokers, (smokers * 100.0) / totalRecords);
        System.out.printf("- Monitoring Calories: %d (%.1f%%)\n", caloriesMonitoring, (caloriesMonitoring * 100.0) / totalRecords);
        System.out.printf("- Familial Overweight History: %d (%.1f%%)\n", familialHistory, (familialHistory * 100.0) / totalRecords);
        System.out.printf("- Average Physical Activity: %.1f hours/week\n", totalPhysicalActivity / totalRecords);
        System.out.printf("- Average Screen Time: %.1f hours/day\n", totalScreenTime / totalRecords);
        System.out.printf("- Average Water Consumption: %.1f L/day\n", totalWaterConsumption / totalRecords);

        // Transportation mode distribution
        int automobile = 0, bike = 0, motorbike = 0, publicTransport = 0, walking = 0;
        for (Patient p : trainingData) {
            if (p.getTransportationAutomobile() == 1) automobile++;
            if (p.getTransportationBike() == 1) bike++;
            if (p.getTransportationMotorbike() == 1) motorbike++;
            if (p.getTransportationPublicTransportation() == 1) publicTransport++;
            if (p.getTransportationWalking() == 1) walking++;
        }

        System.out.println("\nTransportation Modes:");
        System.out.printf("- Automobile: %d (%.1f%%)\n", automobile, (automobile * 100.0) / totalRecords);
        System.out.printf("- Bicycle: %d (%.1f%%)\n", bike, (bike * 100.0) / totalRecords);
        System.out.printf("- Motorbike: %d (%.1f%%)\n", motorbike, (motorbike * 100.0) / totalRecords);
        System.out.printf("- Public Transport: %d (%.1f%%)\n", publicTransport, (publicTransport * 100.0) / totalRecords);
        System.out.printf("- Walking: %d (%.1f%%)\n", walking, (walking * 100.0) / totalRecords);

        System.out.println("\nModel Training Status:");
        if (modelTrained) {
            System.out.println("- Model trained with " + lastTrainingSize + " records");
            System.out.println("- Records added since last training: " + (totalRecords - lastTrainingSize));
            System.out.println("- Records needed for next retraining: " + (1000 - (totalRecords - lastTrainingSize)));
        } else {
            System.out.println("- Initial model not yet trained");
            System.out.println("- Records needed for initial training: " + (1000 - totalRecords));
        }
    }

    @Override
    public String predictObesity(Patient patient) throws RemoteException {
        if (!modelTrained) {
            return "Prediction unavailable: Model is not trained yet.";
        }

        try {
            DenseInstance instance = createInstance(patient);
            instance.setDataset(datasetStructure); // Set structure for prediction
            double predictionIndex = model.classifyInstance(instance);
            String predictedLabel = datasetStructure.classAttribute().value((int) predictionIndex);
            return "Predicted Obesity Level: " + predictedLabel;
        } catch (Exception e) {
            return "Error in prediction: " + e.getMessage();
        }
    }

    private void trainModel() {
        System.out.println("Training model with " + trainingData.size() + " patient records...");

        try {
            // Define feature attributes
            ArrayList<Attribute> attributes = defineAttributes();
            
            // Create dataset
            Instances dataset = new Instances("ObesityData", attributes, trainingData.size());
            dataset.setClassIndex(attributes.size() - 1); // Last attribute is the class label
            
            // Store structure for future predictions
            datasetStructure = new Instances(dataset, 0);

            // Add patient data
            for (Patient patient : trainingData) {
                dataset.add(createInstance(patient));
            }

            // Train Random Forest model
            model = new RandomForest();
            model.setNumIterations(100);
            model.buildClassifier(dataset);

            modelTrained = true;
            System.out.println("Model training complete!");
        } catch (Exception e) {
            System.err.println("Error training model: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ArrayList<Attribute> defineAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();

        // Numeric attributes
        attributes.add(new Attribute("Is_Male"));
        attributes.add(new Attribute("Age"));
        attributes.add(new Attribute("Height"));
        attributes.add(new Attribute("Weight"));
        attributes.add(new Attribute("BMI"));
        attributes.add(new Attribute("Familial_Overweight_History"));
        attributes.add(new Attribute("High_Caloric_Food"));
        attributes.add(new Attribute("Vegetables_In_Meals"));
        attributes.add(new Attribute("Daily_Main_Meals"));
        attributes.add(new Attribute("Smoking"));
        attributes.add(new Attribute("Water_Consumption"));
        attributes.add(new Attribute("Calories_Monitoring"));
        attributes.add(new Attribute("Physical_Activity"));
        attributes.add(new Attribute("Screen_Time"));

        // One-hot encoded categorical attributes
        attributes.add(new Attribute("Eat_Between_Meals_Always"));
        attributes.add(new Attribute("Eat_Between_Meals_Frequently"));
        attributes.add(new Attribute("Eat_Between_Meals_No"));
        attributes.add(new Attribute("Eat_Between_Meals_Sometimes"));
        
        attributes.add(new Attribute("Alcohol_Always"));
        attributes.add(new Attribute("Alcohol_Frequently"));
        attributes.add(new Attribute("Alcohol_No"));
        attributes.add(new Attribute("Alcohol_Sometimes"));

        attributes.add(new Attribute("Transportation_Automobile"));
        attributes.add(new Attribute("Transportation_Bike"));
        attributes.add(new Attribute("Transportation_Motorbike"));
        attributes.add(new Attribute("Transportation_Public"));
        attributes.add(new Attribute("Transportation_Walking"));

        // Define class labels
        ArrayList<String> classLabels = new ArrayList<>();
        classLabels.add("Underweight");
        classLabels.add("Normal_Weight");
        classLabels.add("Overweight_Level_I");
        classLabels.add("Overweight_Level_II");
        classLabels.add("Obesity_Type_I");
        classLabels.add("Obesity_Type_II");
        classLabels.add("Obesity_Type_III");
        attributes.add(new Attribute("Obesity_Level", classLabels)); // Class label attribute

        return attributes;
    }

    private DenseInstance createInstance(Patient patient) {
        double[] values = new double[28]; // Number of attributes

        values[0] = patient.getIsMale();
        values[1] = patient.getAge();
        values[2] = patient.getHeight();
        values[3] = patient.getWeight();
        values[4] = patient.getBmi();
        values[5] = patient.getFamilialOverweightHistory();
        values[6] = patient.getHighCaloricFood();
        values[7] = patient.getVegetablesInMeals();
        values[8] = patient.getDailyMainMeals();
        values[9] = patient.getSmoking();
        values[10] = patient.getWaterConsumption();
        values[11] = patient.getCaloriesMonitoring();
        values[12] = patient.getPhysicalActivity();
        values[13] = patient.getScreenTime();

        values[14] = patient.getEatBetweenMealsAlways();
        values[15] = patient.getEatBetweenMealsFrequently();
        values[16] = patient.getEatBetweenMealsNo();
        values[17] = patient.getEatBetweenMealsSometimes();

        values[18] = patient.getAlcoholConsumptionAlways();
        values[19] = patient.getAlcoholConsumptionFrequently();
        values[20] = patient.getAlcoholConsumptionNo();
        values[21] = patient.getAlcoholConsumptionSometimes();

        values[22] = patient.getTransportationAutomobile();
        values[23] = patient.getTransportationBike();
        values[24] = patient.getTransportationMotorbike();
        values[25] = patient.getTransportationPublicTransportation();
        values[26] = patient.getTransportationWalking();

        // Add class label (obesity level)
        values[27] = patient.getObesityLevel();

        return new DenseInstance(1.0, values);
    }
}
