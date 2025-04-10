package test;

import entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.VehicleService;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleAddTest {
    private VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        vehicleService = new VehicleService();
    }

    @Test
    public void testAddNewVehicle() {
        Vehicle newVehicle = new Vehicle(11, "Altroz", "Tata", 2023, "Blue", "MH12KL1234", true, 1200.0);
        boolean added = vehicleService.addVehicle(newVehicle);
        assertTrue(added);

        Vehicle fetched = vehicleService.getVehicleById(11);
        assertNotNull(fetched);
        assertEquals("Altroz", fetched.getModel());
    }
}
