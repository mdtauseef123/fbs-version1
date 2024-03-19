package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.FlightSchedule;

import java.util.List;

public interface FlightScheduleDAO {
    public FlightSchedule getSchedulesByFlightId(int id);

    public void deleteSchedule(int scheduleId);

    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule);

    List<FlightSchedule> searchFlights(SearchClass flightSchedule);

    List<FlightSchedule> getAllFlight();


}
