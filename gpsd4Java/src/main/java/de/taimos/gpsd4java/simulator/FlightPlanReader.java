package de.taimos.gpsd4java.simulator;

import com.sun.javaws.exceptions.InvalidArgumentException;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.simulator.models.FlightPlans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

public class FlightPlanReader
{
    public static FlightPath getFlightPathFromXml(String flightCode, File flightplansXmlFile) throws Exception
    {
        try
        {
            if(flightCode == null || flightCode.isEmpty())
                throw new Exception("Invalid flightcode");

            if(flightplansXmlFile == null || !flightplansXmlFile.canRead())
                throw new Exception("Invalid xml file");

            FlightPlans flightPlans = readFile(flightplansXmlFile);
            if(flightPlans.getFlightPaths() == null || flightPlans.getFlightPaths().size() == 0)
                throw new Exception("No flight paths specified in the flight plan file");

            // Get the flightpath with the given flightCode from the Xml file
            Optional<FlightPath> foundPath = flightPlans.getFlightPaths().<FlightPath>stream().filter(x -> x.getFlightCode().equals(flightCode)).findFirst();

            if(foundPath.isPresent())
                return foundPath.get();

             throw new Exception("Flight path not found");
        }
        catch (JAXBException e)
        {
            throw new Exception("Cannot read the xml file, because the XML is invalid");
        }
    }

    private static FlightPlans readFile(File xmlFile) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(FlightPlans.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (FlightPlans) jaxbUnmarshaller.unmarshal(xmlFile);
    }
}
