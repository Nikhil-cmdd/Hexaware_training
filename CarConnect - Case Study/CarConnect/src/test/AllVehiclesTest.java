package test;

import entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.VehicleService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AllVehiclesTest {
    private VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        vehicleService = new VehicleService();
        Vehicle vehicle = new Vehicle(14, "i20 Active", "Hyundai", 2016, "White", "MH12WV6111", true, 2000.0);
        vehicleService.addVehicle(vehicle);
    }

    @Test
    public void testGetAllVehicles() {
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();
        assertNotNull(allVehicles);
        assertTrue(allVehicles.size() >= 1);
    }
}
