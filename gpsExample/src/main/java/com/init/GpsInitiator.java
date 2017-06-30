package com.init;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.simulator.GPSSimulatorEndpoint;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ben on 6/28/2017.
 */
public class GpsInitiator {

    public static final File XML_FILE_LOCATION = new File("C:\\Users\\ben\\Git\\GPSd4Java\\gpsd4Java\\flightPlans.xsd.xml");
    public static final int DEFAULT_SPEED_FACTOR = 10;
    public static final String DEFAULT_FLIGHT_CODE = "flight1";

    public static TPVObject lastCoordinates;
    public static ArrayList<TPVObject> coordinateLog = new ArrayList<>();
    private static GPSSimulatorEndpoint endpoint;

    public static void start() {
        start(DEFAULT_FLIGHT_CODE, XML_FILE_LOCATION, DEFAULT_SPEED_FACTOR);
    }

    public static void stop()
    {
        GpsInitiator.lastCoordinates = null;
        coordinateLog = new ArrayList<>();
        endpoint.stop();
        endpoint = null;
    }

    public static void start(String flightCode, File xmlFile, int speed) {
        try {
            endpoint = new GPSSimulatorEndpoint(flightCode, xmlFile, speed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        endpoint.addListener(new IObjectListener() {
            @Override
            public void handleTPV(TPVObject tpv) {
                GpsInitiator.lastCoordinates = tpv;
                GpsInitiator.coordinateLog.add(tpv);
            }

            @Override
            public void handleSKY(SKYObject sky) {

            }

            @Override
            public void handleATT(ATTObject att) {

            }

            @Override
            public void handleSUBFRAME(SUBFRAMEObject subframe) {

            }

            @Override
            public void handleDevices(DevicesObject devices) {

            }

            @Override
            public void handleDevice(DeviceObject device) {

            }
        });
        endpoint.start();
    }
}
