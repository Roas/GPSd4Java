
package de.taimos.gpsd4java.simulator.models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightPaths" type="{}flightPath" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "flightPaths"
})
@XmlRootElement(name = "flightPlans")
public class FlightPlans {

    @XmlElement(nillable = true)
    protected List<FlightPath> flightPaths;

    /**
     * Gets the value of the flightPaths property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightPaths property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightPaths().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightPath }
     * 
     * 
     */
    public List<FlightPath> getFlightPaths() {
        if (flightPaths == null) {
            flightPaths = new ArrayList<FlightPath>();
        }
        return this.flightPaths;
    }

}
