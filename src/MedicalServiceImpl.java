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

    @Override
    public void sendPatientData(List<Patient> patients) throws RemoteException {
        System.out.println("Received " + patients.size() + " new patient records.");
        trainingData.addAll(patients);

        // Check if we have enough data to train the model
        if (trainingData.size() >= 1000 && !modelTrained) {
            trainModel();
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
