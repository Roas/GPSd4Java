
package de.taimos.gpsd4java.simulator.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flightPath complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flightPath">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="airplaneCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coordinates" type="{}coordinates"/>
 *         &lt;element name="departureLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinationLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flightCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flyDays" type="{}flyDays" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flightPath", propOrder = {
    "airplaneCode",
    "coordinates",
    "departureLocation",
    "destinationLocation",
    "flightCode",
    "flyDays"
})
public class FlightPath {

    protected String airplaneCode;
    @XmlElement(required = true)
    protected Coordinates coordinates;
    protected String departureLocation;
    protected String destinationLocation;
    protected String flightCode;
    protected FlyDays flyDays;

    /**
     * Gets the value of the airplaneCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirplaneCode() {
        return airplaneCode;
    }

    /**
     * Sets the value of the airplaneCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirplaneCode(String value) {
        this.airplaneCode = value;
    }

    /**
     * Gets the value of the coordinates property.
     * 
     * @return
     *     possible object is
     *     {@link Coordinates }
     *     
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Coordinates }
     *     
     */
    public void setCoordinates(Coordinates value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the departureLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureLocation() {
        return departureLocation;
    }

    /**
     * Sets the value of the departureLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureLocation(String value) {
        this.departureLocation = value;
    }

    /**
     * Gets the value of the destinationLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationLocation() {
        return destinationLocation;
    }

    /**
     * Sets the value of the destinationLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationLocation(String value) {
        this.destinationLocation = value;
    }

    /**
     * Gets the value of the flightCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightCode() {
        return flightCode;
    }

    /**
     * Sets the value of the flightCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightCode(String value) {
        this.flightCode = value;
    }

    /**
     * Gets the value of the flyDays property.
     * 
     * @return
     *     possible object is
     *     {@link FlyDays }
     *     
     */
    public FlyDays getFlyDays() {
        return flyDays;
    }

    /**
     * Sets the value of the flyDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlyDays }
     *     
     */
    public void setFlyDays(FlyDays value) {
        this.flyDays = value;
    }

}
