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
        Vehicle vehicle = new Vehicle("Altroz", "Tata", 2023, "Blue", "MH12KL5555", true, 1200.0);
        boolean added = vehicleService.addVehicle(vehicle);
        assertTrue(added);

        Vehicle fetched = vehicleService.getVehicleByRegistrationNumber("MH12KL1234");
        System.out.println("Vehicle added: " + fetched);

        assertNotNull(fetched);
        assertEquals("Altroz", fetched.getModel());
    }
}
