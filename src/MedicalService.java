import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MedicalService extends Remote {
    // Hospitals call this to send patient data for training
    void sendPatientData(List<Patient> patients) throws RemoteException;

    // Hospitals call this to request obesity predictions
    String predictObesity(Patient patient) throws RemoteException;
}
