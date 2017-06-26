package de.taimos.gpsd4java.simulator.models;

import java.time.ZonedDateTime;

/**
 * Created by ben on 6/26/2017.
 */
public class FlightCoordinate {
    private double latitude;
    private double longitude;
    private double altitude;
    private ZonedDateTime time;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
}
