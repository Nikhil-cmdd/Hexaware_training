package entity;

import java.sql.Timestamp;

public class Reservation {
    private int reservationID;
    private int customerID;
    private int vehicleID;
    private Timestamp startDate;
    private Timestamp endDate;
    private double totalCost;
    private String status;

    public Reservation() {}

    public Reservation(int reservationID, int customerID, int vehicleID, Timestamp startDate, Timestamp endDate, double totalCost, String status) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.status = status;
    }
    
 // Getters and setters
    public double calculateTotalCost(long rentalDays, double dailyRate) {
        return rentalDays * dailyRate;
    }

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
	    return "ReservationID: " + reservationID + ", VehicleID: " + vehicleID +
	           ", Start: " + startDate + ", End: " + endDate +
	           ", Total: ₹" + totalCost + ", Status: " + status;
	}
    
}
