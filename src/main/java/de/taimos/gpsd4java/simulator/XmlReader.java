package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.simulator.models.FlightPlans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by ben on 6/26/2017.
 */
public class XmlReader
{
    public static FlightPlans readFile(File xmlFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(FlightPlans.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (FlightPlans) jaxbUnmarshaller.unmarshal(xmlFile);
    }
}
