package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.simulator.models.FlightCoordinate;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.simulator.models.FlightPlans;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.TPVObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by ben on 6/26/2017.
 */
public class FlightPathReader
{
    protected FlightPath flightPath;
    protected int coordinateIndex = 0;

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

    public FlightCoordinate getNextCoordinate() {
        List<FlightCoordinate> coordinateList = flightPath.getCoordinates().getCoordinate();
        if(coordinateList.size() <= coordinateIndex)
            return null;

        return coordinateList.get(coordinateIndex++);
    }

    public TPVObject getNextTPVObject() {
        FlightCoordinate coordinate = getNextCoordinate();
        if(coordinate == null)
            return null;

        // Return a new obj
        TPVObject testObj = new TPVObject();
        testObj.setTimestampError(0);
        testObj.setAltitudeError(0);
        testObj.setClimbRateError(0);
        testObj.setCourseError(0);
        testObj.setLongitudeError(0);
        testObj.setLatitude(0);
        testObj.setSpeedError(0);

        // Set the time to now
        long time = new Date().getTime();
        testObj.setTimestamp(time);

        testObj.setMode(ENMEAMode.ThreeDimensional);
        testObj.setTag("SIMULATED");
        testObj.setDevice("GPS Simulator");


        testObj.setAltitude(coordinate.getAltitude());
        testObj.setLongitude(coordinate.getLongitude());
        testObj.setLatitude(coordinate.getLatitude());
        testObj.setSpeed(0);
        testObj.setCourse(0);
        testObj.setClimbRate(0);
        return  testObj;
    }
}
