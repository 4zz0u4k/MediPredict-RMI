import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MedicalCenterServer {
    public static void main(String[] args) {
        try {
            // TODO: Create an instance of MedicalServiceImpl
            // TODO: Start RMI registry and bind the service
            System.out.println("Medical Center Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
