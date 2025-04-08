package interfaces;

import entity.Reservation;
import java.util.List;

public interface IReservationService {
    Reservation getReservationById(int reservationId);
    List<Reservation> getReservationsByCustomerId(int customerId);
    boolean createReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    boolean cancelReservation(int reservationId);
}
