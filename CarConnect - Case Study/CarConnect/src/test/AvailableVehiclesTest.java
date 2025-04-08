package test;

import entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.VehicleService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableVehiclesTest {
    private VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        vehicleService = new VehicleService();
        Vehicle vehicle = new Vehicle(13, "Venue", "Hyundai", 2023, "White", "MH20DE4321", true, 1250.0);
        vehicleService.addVehicle(vehicle);
    }

    @Test
    public void testGetAvailableVehicles() {
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
        assertNotNull(availableVehicles);
        assertTrue(availableVehicles.size() > 0);
    }
}
