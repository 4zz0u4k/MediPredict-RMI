import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MedicalServiceImpl extends UnicastRemoteObject implements MedicalService {
    private List<Patient> trainingData = new ArrayList<>();
    private boolean modelTrained = false;

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
        // TODO: Implement obesity prediction logic using the trained model
        return "Prediction: TODO (Replace with actual ML model output)";
    }

    // Private method to train the model automatically when enough data is collected
    private void trainModel() {
        System.out.println("Training model with " + trainingData.size() + " patient records...");

        // TODO: Implement ML training logic here (e.g., train Decision Tree, Logistic Regression)

        modelTrained = true;
        System.out.println("Model training complete!");
    }
}
