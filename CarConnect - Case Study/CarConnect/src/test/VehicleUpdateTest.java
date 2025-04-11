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
        vehicle = new Vehicle("i10", "Hyundai", 2017, "Silver", "MH12MW8773", true, 1400.0);
        vehicleService.addVehicle(vehicle);
        vehicle = vehicleService.getVehicleByRegistrationNumber("MH14XC9876");
    }

    @Test
    public void testUpdateVehicleDetails() {
        System.out.println("Before update: " + vehicle.getColor());

        vehicle.setColor("Black");
        boolean updated = vehicleService.updateVehicle(vehicle);
        assertTrue(updated);

        Vehicle updatedVehicle = vehicleService.getVehicleById(vehicle.getVehicleID());
        System.out.println("After update: " + updatedVehicle.getColor());

        assertEquals("Black", updatedVehicle.getColor());
    }
    
    
}
