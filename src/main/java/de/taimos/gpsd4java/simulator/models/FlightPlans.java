package de.taimos.gpsd4java.simulator.models;

import java.util.List;

/**
 * Created by ben on 6/26/2017.
 */
public class FlightPlans {
    private List<FlightPath> flightPaths;

    public List<FlightPath> getFlightPaths() {
        return flightPaths;
    }

    public void setFlightPaths(List<FlightPath> flightPaths) {
        this.flightPaths = flightPaths;
    }
}
