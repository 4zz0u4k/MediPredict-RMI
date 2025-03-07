import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MedicalService extends Remote {
    // TODO: Method for hospitals to send patient data for training
    void sendPatientData(List<Patient> patients) throws RemoteException;

    // TODO: Method for hospitals to request obesity prediction
    String predictObesity(Patient patient) throws RemoteException;

    // TODO: Method to trigger model training when enough data is collected
    void trainModel() throws RemoteException;
}