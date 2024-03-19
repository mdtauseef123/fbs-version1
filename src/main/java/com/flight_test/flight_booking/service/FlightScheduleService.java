package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.dao.SearchClass;
import com.flight_test.flight_booking.entity.FlightSchedule;

import java.util.List;

public interface FlightScheduleService {

    public FlightSchedule getSchedulesByFlightId(int id);
    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule);

    public void deleteSchedule(int id);

    List<FlightSchedule> search(SearchClass s);
    List<FlightSchedule> getAllFlight();




}
