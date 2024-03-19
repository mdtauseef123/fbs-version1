package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.FlightSchedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class FlightScheduleDAOImpl implements FlightScheduleDAO{

    private EntityManager entityManager;

    public FlightScheduleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public FlightSchedule getSchedulesByFlightId(int id) {
        return entityManager.find(FlightSchedule.class , id);
    }

    @Override
    @Transactional
    public void deleteSchedule(int scheduleId) {
            try {
                entityManager.createQuery("DELETE FROM BookingDetails b WHERE b.scheduleId = :scheduleId")
                        .setParameter("scheduleId", scheduleId)
                        .executeUpdate();

                entityManager.createQuery("DELETE FROM FlightSchedule fs WHERE fs.scheduleId = :scheduleId")
                        .setParameter("scheduleId", scheduleId)
                        .executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete schedule with ID: " + scheduleId, e);
            }
    }


    @Override
    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule) {
        FlightSchedule newFlightSchedule = entityManager.merge(flightSchedule);
        return newFlightSchedule;
    }
    public void allPaths(Map<String, List<String>> adj, Map<String, Boolean> visited, List<List<String>> res, List<String> currPath, String u, String v) {
        if (visited.get(u)) {
            return;
        }
        visited.put(u, true);
        currPath.add(u);

        if (u.equals(v)) {
            List<String> path = new ArrayList<>(currPath);
            res.add(path);
            visited.put(u, false);
            currPath.remove(currPath.size() - 1);
            return;
        }
        for (String x : adj.get(u)) {
            allPaths(adj, visited, res, currPath, x, v);
        }
        currPath.remove(currPath.size() - 1);
        visited.put(u, false);
    }

    public void allPaths_2(Map<String, List<String>> adj, Map<String, Boolean> visited, List<List<String>> res, List<String> currPath, String u, String v) {
        if (visited.get(u) || adj.get(u) == null) {
            return;
        }
        visited.put(u, true);
        currPath.add(u);

        if (u.equals(v)) {
            List<String> path = new ArrayList<>(currPath);
            res.add(path);
            visited.put(u, false);
            currPath.remove(currPath.size() - 1);
            return;
        }
        for (String x : adj.get(u)) {
            allPaths_2(adj, visited, res, currPath, x, v);
        }
        currPath.remove(currPath.size() - 1);
        visited.put(u, false);
    }




    @Override
    public List<FlightSchedule> searchFlights(SearchClass searchClass) {
        String jpql = "SELECT f FROM FlightSchedule f WHERE f.source = :from AND f.destination = :to " +
                "AND FUNCTION('DATE', f.departureTime) = :departureDate";
        String flightNumber = searchClass.getFlightNumber();
        LocalDate returningTime = searchClass.getReturningDate();
        if(flightNumber.length() != 0){
            jpql += " AND f.flightNumber = :flightNumber";
        }
        if(returningTime != null){
            jpql += " OR f.source = :to AND f.destination = :from AND FUNCTION('DATE', f.departureTime) = DATE(:returningDate)";
        }
        TypedQuery<FlightSchedule> query = entityManager.createQuery(jpql, FlightSchedule.class);
        query.setParameter("from", searchClass.getSource());
        query.setParameter("to", searchClass.getDestination());
        query.setParameter("departureDate", searchClass.getDepartureDate());
        //System.out.println(searchClass.getSource() + " " + searchClass.getDestination() + " " + searchClass.getDepartureDate());
        if(returningTime != null) {
            query.setParameter("returningDate", searchClass.getReturningDate());
        }
        if(flightNumber.length() != 0){
            query.setParameter("flightNumber", flightNumber);
        }
        List<FlightSchedule> nonStop = query.getResultList();
        List<FlightSchedule> withStop;
        //nonStop.addAll(withStop);
        return nonStop;
    }
    @Override
    public List<FlightSchedule> getAllFlight() {
        TypedQuery<FlightSchedule> allFlights = entityManager.createQuery("FROM FlightSchedule", FlightSchedule.class);
        return allFlights.getResultList();
    }
}
