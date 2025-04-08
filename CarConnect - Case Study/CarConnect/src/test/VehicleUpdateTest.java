package test;

import entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.VehicleService;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleUpdateTest {
    private VehicleService vehicleService;
    private Vehicle vehicle;

    @BeforeEach
    public void setup() {
        vehicleService = new VehicleService();
        vehicle = new Vehicle(12, "Nexon", "Tata", 2022, "Silver", "MH14XC9876", true, 1400.0);
        vehicleService.addVehicle(vehicle);
    }

    @Test
    public void testUpdateVehicleDetails() {
        vehicle.setColor("Black");
        boolean updated = vehicleService.updateVehicle(vehicle);
        assertTrue(updated);

        Vehicle updatedVehicle = vehicleService.getVehicleById(12);
        assertEquals("Black", updatedVehicle.getColor());
    }
}
