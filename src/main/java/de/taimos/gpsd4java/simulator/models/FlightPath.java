package de.taimos.gpsd4java.simulator.models;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by ben on 6/26/2017.
 */
public class FlightPath {
    private String flightCode;
    private String airplaneCode;
    private DayOfWeek flyDays;
    private String departureLocation;
    private String destinationLocation;
    private List<FlightCoordinate> coordinates;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getAirplaneCode() {
        return airplaneCode;
    }

    public void setAirplaneCode(String airplaneCode) {
        this.airplaneCode = airplaneCode;
    }

    public DayOfWeek getFlyDays() {
        return flyDays;
    }

    public void setFlyDays(DayOfWeek flyDays) {
        this.flyDays = flyDays;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public List<FlightCoordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<FlightCoordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
