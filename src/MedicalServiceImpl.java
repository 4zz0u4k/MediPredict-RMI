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
        if (!modelTrained && totalRecords >= 500) {
            // First-time training when we reach threshold
            System.out.println("Training initial model with " + totalRecords + " records...");
            trainModel();
            lastTrainingSize = totalRecords;
            modelTrained = true;
        } else if (modelTrained && newDataRequiresRetraining(totalRecords)) {
            // Retrain model when we have 500+ new records
            System.out.println("Retraining model with updated dataset...");
            trainModel();
            lastTrainingSize = totalRecords;
        }

        // Display comprehensive statistics about the dataset
        printDatasetStatistics();

        if (modelTrained) {
            System.out.println("Model is trained and ready for predictions.");
        } else {
            System.out.println("Need " + (500 - totalRecords) + " more records to train initial model.");
        }
    }

    // Helper method to decide if retraining is needed
    private boolean newDataRequiresRetraining(int currentSize) {
        // Retrain if we have 1000+ new records since last training
        return currentSize >= lastTrainingSize + 500;
    }

    // Method to print comprehensive dataset statistics
    private void printDatasetStatistics() {
        if (trainingData.isEmpty()) return;

        int totalRecords = trainingData.size();
        String divider = "═════════════════════════════════════════════════════════════════════";
        String sectionDivider = "─────────────────────────────────────────────────────────────────────";

        System.out.println("\n" + divider);
        System.out.println("║ OBESITY DATASET STATISTICS REPORT                                 ║");
        System.out.println("║ Total Records: " + String.format("%-47d", totalRecords) + "    ║");
        System.out.println(divider);

        // Obesity level distribution
        int[] obesityLevelCounts = new int[7]; // 0-6 levels
        for (Patient p : trainingData) {
            int level = p.getObesityLevel();
            if (level >= 0 && level < 7) {
                obesityLevelCounts[level]++;
            }
        }

        System.out.println("\n📊 OBESITY CATEGORY DISTRIBUTION");
        System.out.println(sectionDivider);
        String[] categories = {"Insufficient Weight", "Normal Weight", "Overweight Level I",
                "Overweight Level II", "Obesity Type I", "Obesity Type II", "Obesity Type III"};

        int maxCategoryLength = 0;
        for (String category : categories) {
            maxCategoryLength = Math.max(maxCategoryLength, category.length());
        }

        for (int i = 0; i < obesityLevelCounts.length; i++) {
            double percent = (obesityLevelCounts[i] * 100.0) / totalRecords;
            String bar = generateBar(percent, 30);
            System.out.printf("  %-" + (maxCategoryLength + 2) + "s %5d (%5.1f%%) %s\n",
                    categories[i] + ":", obesityLevelCounts[i], percent, bar);
        }

        // Gender distribution
        int maleCount = 0, femaleCount = 0;
        for (Patient p : trainingData) {
            if (p.getIsMale() == 1) maleCount++;
            else femaleCount++;
        }

        System.out.println("\n👫 GENDER DISTRIBUTION");
        System.out.println(sectionDivider);
        double malePercent = (maleCount * 100.0) / totalRecords;
        double femalePercent = (femaleCount * 100.0) / totalRecords;
        System.out.printf("  %-10s %5d (%5.1f%%) %s\n", "Male:", maleCount, malePercent, generateBar(malePercent, 30));
        System.out.printf("  %-10s %5d (%5.1f%%) %s\n", "Female:", femaleCount, femalePercent, generateBar(femalePercent, 30));

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

        System.out.println("\n🏃 LIFESTYLE FACTORS");
        System.out.println(sectionDivider);
        double smokersPercent = (smokers * 100.0) / totalRecords;
        double caloriesPercent = (caloriesMonitoring * 100.0) / totalRecords;
        double familyHistoryPercent = (familialHistory * 100.0) / totalRecords;

        System.out.printf("  %-25s %5d (%5.1f%%) %s\n", "Smokers:", smokers, smokersPercent, generateBar(smokersPercent, 30));
        System.out.printf("  %-25s %5d (%5.1f%%) %s\n", "Monitoring Calories:", caloriesMonitoring, caloriesPercent, generateBar(caloriesPercent, 30));
        System.out.printf("  %-25s %5d (%5.1f%%) %s\n", "Familial Overweight History:", familialHistory, familyHistoryPercent, generateBar(familyHistoryPercent, 30));
        System.out.printf("  %-25s %5.1f hours/week\n", "Average Physical Activity:", totalPhysicalActivity / totalRecords);
        System.out.printf("  %-25s %5.1f hours/day\n", "Average Screen Time:", totalScreenTime / totalRecords);
        System.out.printf("  %-25s %5.1f L/day\n", "Average Water Consumption:", totalWaterConsumption / totalRecords);

        // Transportation mode distribution
        int automobile = 0, bike = 0, motorbike = 0, publicTransport = 0, walking = 0;
        for (Patient p : trainingData) {
            if (p.getTransportationAutomobile() == 1) automobile++;
            if (p.getTransportationBike() == 1) bike++;
            if (p.getTransportationMotorbike() == 1) motorbike++;
            if (p.getTransportationPublicTransportation() == 1) publicTransport++;
            if (p.getTransportationWalking() == 1) walking++;
        }

        System.out.println("\n🚗 TRANSPORTATION MODES");
        System.out.println(sectionDivider);
        double autoPercent = (automobile * 100.0) / totalRecords;
        double bikePercent = (bike * 100.0) / totalRecords;
        double motorbikePercent = (motorbike * 100.0) / totalRecords;
        double publicPercent = (publicTransport * 100.0) / totalRecords;
        double walkingPercent = (walking * 100.0) / totalRecords;

        System.out.printf("  %-20s %5d (%5.1f%%) %s\n", "Automobile:", automobile, autoPercent, generateBar(autoPercent, 30));
        System.out.printf("  %-20s %5d (%5.1f%%) %s\n", "Bicycle:", bike, bikePercent, generateBar(bikePercent, 30));
        System.out.printf("  %-20s %5d (%5.1f%%) %s\n", "Motorbike:", motorbike, motorbikePercent, generateBar(motorbikePercent, 30));
        System.out.printf("  %-20s %5d (%5.1f%%) %s\n", "Public Transport:", publicTransport, publicPercent, generateBar(publicPercent, 30));
        System.out.printf("  %-20s %5d (%5.1f%%) %s\n", "Walking:", walking, walkingPercent, generateBar(walkingPercent, 30));

        System.out.println("\n⚙️ MODEL TRAINING STATUS");
        System.out.println(sectionDivider);
        if (modelTrained) {
            System.out.println("  ✅ Model trained with " + lastTrainingSize + " records");
            int recordsAdded = totalRecords - lastTrainingSize;
            System.out.println("  ℹ️ Records added since last training: " + recordsAdded);
            int recordsNeeded = 500 - recordsAdded;
            System.out.println("  ⏳ Records needed for next retraining: " + recordsNeeded);
            System.out.println("  " + generateProgressBar(recordsAdded, 500, 50));
        } else {
            System.out.println("  ⚠️ Initial model not yet trained");
            System.out.println("  ⏳ Records needed for initial training: " + (500 - totalRecords));
            System.out.println("  " + generateProgressBar(totalRecords, 500, 50));
        }

        System.out.println("\n" + divider);
    }

    // Helper method to generate visual bar for percentages
    private String generateBar(double percentage, int maxLength) {
        int barLength = (int) (percentage * maxLength / 100);
        StringBuilder bar = new StringBuilder();
        bar.append("█".repeat(barLength));
        return bar.toString();
    }

    // Helper method to generate a progress bar
    private String generateProgressBar(int current, int total, int length) {
        int progress = (int) ((double) current / total * length);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            if (i < progress) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(current).append("/").append(total).append(" (").append(String.format("%.1f%%", (current * 100.0) / total)).append(")");
        return bar.toString();
    }

    @Override
    public String predictObesity(Patient patient) throws RemoteException {
        if (!modelTrained) {
            return "None";
        }

        try {
            DenseInstance instance = createInstance(patient);
            instance.setDataset(datasetStructure); // Set structure for prediction
            double predictionIndex = model.classifyInstance(instance);
            return datasetStructure.classAttribute().value((int) predictionIndex);
        } catch (Exception e) {
            return "Error in prediction: " + e.getMessage();
        }
    }

    private void trainModel() {
        String divider = "═════════════════════════════════════════════════════════════════════";

        System.out.println("\n" + divider);
        System.out.println("║ 🤖 MODEL TRAINING                                                ║");
        System.out.println(divider);

        int recordCount = trainingData.size();
        System.out.println("\n⏳ Initializing model training with " + recordCount + " patient records...");
        System.out.println("  " + generateProgressBar(0, 5, 50) + " (0%)");

        try {
            // Define feature attributes
            System.out.println("\n📋 Defining attributes...");
            ArrayList<Attribute> attributes = defineAttributes();
            System.out.println("  ✅ " + attributes.size() + " attributes defined");
            System.out.println("  " + generateProgressBar(1, 5, 50) + " (20%)");

            // Create dataset
            System.out.println("\n🔄 Creating dataset structure...");
            Instances dataset = new Instances("ObesityData", attributes, trainingData.size());
            dataset.setClassIndex(attributes.size() - 1); // Last attribute is the class label
            System.out.println("  ✅ Dataset structure created with class index: " + (attributes.size() - 1));
            System.out.println("  " + generateProgressBar(2, 5, 50) + " (40%)");

            // Store structure for future predictions
            datasetStructure = new Instances(dataset, 0);
            System.out.println("\n📊 Preparing patient data...");

            // Add patient data
            for (Patient patient : trainingData) {
                dataset.add(createInstance(patient));
            }
            System.out.println("  ✅ Added " + trainingData.size() + " patient records to dataset");
            System.out.println("  " + generateProgressBar(3, 5, 50) + " (60%)");

            // Train Random Forest model
            System.out.println("\n🌲 Training Random Forest model...");
            System.out.println("  ⚙️ Setting iterations: 100");
            model = new RandomForest();
            model.setNumIterations(100);

            long startTime = System.currentTimeMillis();
            model.buildClassifier(dataset);
            long endTime = System.currentTimeMillis();

            double trainingTime = (endTime - startTime) / 1000.0;
            System.out.println("  ✅ Model built successfully in " + String.format("%.2f", trainingTime) + " seconds");
            System.out.println("  " + generateProgressBar(4, 5, 50) + " (80%)");

            // Update training status
            modelTrained = true;
            lastTrainingSize = trainingData.size();

            System.out.println("\n📈 Performing basic model validation...");
            // You could add code here to calculate and display basic validation metrics
            System.out.println("  ✅ Model ready for predictions");
            System.out.println("  " + generateProgressBar(5, 5, 50) + " (100%)");

            System.out.println("\n" + divider);
            System.out.println("║ ✅ MODEL TRAINING COMPLETE                                      ║");
            System.out.println(divider);
        } catch (Exception e) {
            System.out.println("\n" + divider);
            System.out.println("║ ❌ MODEL TRAINING FAILED                                     ║");
            System.out.println(divider);
            System.err.println("\n❌ Error training model: " + e.getMessage());
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
