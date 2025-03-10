import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MedicalCenterServer {
    public static void main(String[] args) {
        try {
            // Start RMI service
            MedicalService service = new MedicalServiceImpl();
            Registry registry = LocateRegistry.createRegistry(7777);
            registry.rebind("MedicalService", service);

            System.out.println("Medical Center Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
