package de.taimos.gpsd4java.simulator;


import de.taimos.gpsd4java.simulator.models.FlightCoordinate;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.simulator.models.FlightPlans;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.TPVObject;
import org.geotools.referencing.GeodeticCalculator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.geom.Point2D;
import java.io.File;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by ben on 6/26/2017.
 */
public class FlightPathReader
{
    protected FlightPath flightPath;
    protected int coordinateIndex = 0;
    protected int timer = -1;
    protected List<Point2D> intervalCoords;
    protected int intervalCoordsIndex = 0;

    public FlightPathReader(String flightCode, File flightplansXmlFile) throws Exception {
        try {
            FlightPlans flightPlans = readFile(flightplansXmlFile);
            flightPath = flightPlans.getFlightPaths().stream().filter(x -> x.getFlightCode().equals(flightCode)).findFirst().get();
        } catch (JAXBException e) {
            throw new Exception("Cannot read the xml file. The file does not exists or contains invalid XML");
        }
    }

    private FlightPlans readFile(File xmlFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(FlightPlans.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (FlightPlans) jaxbUnmarshaller.unmarshal(xmlFile);
    }

    public FlightPath getFlightPath() {
        return flightPath;
    }

    public FlightCoordinate getCoordinate(int index) {
        List<FlightCoordinate> coordinateList = flightPath.getCoordinates().getCoordinate();
        if(coordinateList.size() <= index)
            return null;

        return coordinateList.get(index);
    }

    private LocalTime getTimeFromCoordinate(FlightCoordinate coord) {
        String[] splitTime = coord.getTime().split(":");
        LocalTime lt = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
        return lt;
    }

    public TPVObject getNextTPVObject() {
        FlightCoordinate coordinate = getCoordinate(coordinateIndex);
        FlightCoordinate coordinate2 = getCoordinate(coordinateIndex + 1);

        if(coordinate == null || coordinate2 == null)
            return null;

        LocalTime t1 = getTimeFromCoordinate(coordinate);
        LocalTime t2 = getTimeFromCoordinate(coordinate2);
        int t1Sec = t1.toSecondOfDay();
        int t2Sec = t2.toSecondOfDay();
        int differenceSec = t2Sec - t1Sec;

        if(timer == -1)
        {
            timer = t1Sec;
            intervalCoordsIndex = 0;
            final GeodeticCalculator calc = new GeodeticCalculator();
            final Point2D startPoint = new Point2D.Double(coordinate.getLongitude(), coordinate.getLatitude());
            final Point2D endPoint = new Point2D.Double(coordinate2.getLongitude(), coordinate2.getLatitude());
            calc.setStartingGeographicPoint(startPoint);
            calc.setDestinationGeographicPoint(endPoint);
            intervalCoords = calc.getGeodeticPath(differenceSec);
        } else if(timer >= t2Sec) {
            coordinateIndex++;

            intervalCoordsIndex = 0;
            final GeodeticCalculator calc = new GeodeticCalculator();
            final Point2D startPoint = new Point2D.Double(coordinate.getLongitude(), coordinate.getLatitude());
            final Point2D endPoint = new Point2D.Double(coordinate2.getLongitude(), coordinate2.getLatitude());
            calc.setStartingGeographicPoint(startPoint);
            calc.setDestinationGeographicPoint(endPoint);
            intervalCoords = calc.getGeodeticPath(differenceSec);
        }

        // Return a new obj
        TPVObject testObj = new TPVObject();
        testObj.setTimestampError(0);
        testObj.setAltitudeError(0);
        testObj.setClimbRateError(0);
        testObj.setCourseError(0);
        testObj.setLongitudeError(0);
        testObj.setLatitudeError(0);
        testObj.setLatitude(0);
        testObj.setSpeedError(0);


        Point2D point = intervalCoords.get(intervalCoordsIndex++);

        // Set the time to now
        testObj.setTimestamp(timer);
        testObj.setMode(ENMEAMode.ThreeDimensional);
        testObj.setTag("SIMULATED");
        testObj.setDevice("GPS Simulator");

        testObj.setAltitude(coordinate.getAltitude());
        testObj.setLongitude(point.getX());
        testObj.setLatitude(point.getY());
        testObj.setSpeed(0);
        testObj.setCourse(0);
        testObj.setClimbRate(0);

        timer++;
        return  testObj;
    }
}
