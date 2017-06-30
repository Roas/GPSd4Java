package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.simulator.models.FlightCoordinate;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.TPVObject;
import org.geotools.referencing.GeodeticCalculator;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.List;

public class FlightPathProcessor
{
    private FlightPath flightPath;
    private int coordinateIndex = 0;
    private int currentTime = -1;
    private List<Point2D> intervalCoordinatesList;
    private double currentCourse = -1;
    private int intervalCoordinatesIndex = 0;
    private int timeAccelerationFactor;
    private double currentAltitude = 0;
    private double climbRate = 0;

    public FlightPathProcessor(FlightPath path, int timeAccelerationFactor)
    {
        this.flightPath = path;
        this.timeAccelerationFactor = timeAccelerationFactor;
    }

    public TPVObject getNextTPVObject()
    {
        // Get the current and next coordinate
        FlightCoordinate firstCoordinate = getCoordinate(coordinateIndex);
        FlightCoordinate secondCoordinate = getCoordinate(coordinateIndex + 1);

        // Return null (and stop the thread) when the end is reached
        if(firstCoordinate == null)
            return null;

        // When there is no next coordinate, return the first and up the coordinate index by one. It will stop the next time
        if(secondCoordinate == null) {
            coordinateIndex++;
            return createNewTpvObject(currentTime, firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        }

        // Calculate the time difference between the coordinate points in seconds
        int firstCoordinateTime = getTimeFromCoordinate(firstCoordinate);
        int secondCoordinateTime = getTimeFromCoordinate(secondCoordinate);
        int timeDifference = secondCoordinateTime - firstCoordinateTime;

        // If the currentTime is not set (the first time this is run) set the time to the time of the first coordinate
        if(currentTime == -1)
            currentTime = firstCoordinateTime;

        // Calculate the coordinates between points if not set
        if(intervalCoordinatesList == null)
            calculateCoordinatesBetweenPoints(firstCoordinate, secondCoordinate, timeDifference / timeAccelerationFactor);

        // Get the coordinates and increase the currentTime
        Point2D point = intervalCoordinatesList.get(intervalCoordinatesIndex++);
        currentTime = currentTime + (1 * timeAccelerationFactor);

        // If the time is the same or later than the second coordinate time, continue with that one
        if(currentTime >= secondCoordinateTime)
        {
            coordinateIndex++;
            intervalCoordinatesIndex = 0;
            intervalCoordinatesList = null;
        }

        // Calculate the altitude change
        if(timeDifference != 0) {
            climbRate = (secondCoordinate.getAltitude() - firstCoordinate.getAltitude()) / timeDifference;
            currentAltitude = currentAltitude + (climbRate * timeAccelerationFactor);
        }

        return createNewTpvObject(currentTime, point.getX(), point.getY());
    }



    private FlightCoordinate getCoordinate(int index)
    {
        List<FlightCoordinate> coordinateList = flightPath.getCoordinates().getCoordinate();
        if(coordinateList.size() <= index)
            return null;

        return coordinateList.get(index);
    }

    private void calculateCoordinatesBetweenPoints(FlightCoordinate firstCoordinate, FlightCoordinate secondCoordinate, int timeDifference)
    {
        final GeodeticCalculator calc = new GeodeticCalculator();
        final Point2D startPoint = new Point2D.Double(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        final Point2D endPoint = new Point2D.Double(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());
        calc.setStartingGeographicPoint(startPoint);
        calc.setDestinationGeographicPoint(endPoint);
        intervalCoordinatesList = calc.getGeodeticPath(timeDifference);
        currentCourse = calc.getAzimuth();
    }

    // Return the time from the coordinate in seconds
    private int getTimeFromCoordinate(FlightCoordinate coordinate)
    {
        String[] splitTime = coordinate.getTime().split(":");
        LocalTime localTime = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
        return localTime.toSecondOfDay();
    }

    private TPVObject createNewTpvObject(long timestamp, double longitude, double latitude)
    {
        // Return a new object with coordinates
        TPVObject tpvObject = new TPVObject();
        tpvObject.setTimestampError(0);
        tpvObject.setClimbRateError(0);
        tpvObject.setCourseError(0);
        tpvObject.setAltitudeError(0);
        tpvObject.setLongitudeError(0);
        tpvObject.setLatitudeError(0);
        tpvObject.setSpeedError(0);

        tpvObject.setTimestamp(timestamp);
        tpvObject.setMode(ENMEAMode.ThreeDimensional);
        tpvObject.setTag("SIMULATED");
        tpvObject.setDevice("GPS Simulator");

        tpvObject.setClimbRate(climbRate);
        tpvObject.setCourse(currentCourse);
        tpvObject.setAltitude(currentAltitude);
        tpvObject.setLongitude(longitude);
        tpvObject.setLatitude(latitude);
        return tpvObject;
    }
}
