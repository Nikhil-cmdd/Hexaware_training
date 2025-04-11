package interfaces;

import entity.Vehicle;
import java.util.List;

public interface IVehicleService {
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAvailableVehicles();
    List<Vehicle> getAllVehicles();
    boolean addVehicle(Vehicle vehicle);
    boolean updateVehicle(Vehicle vehicle);
    boolean removeVehicle(int vehicleId);
}
