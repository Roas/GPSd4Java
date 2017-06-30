package com.init;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.simulator.GPSSimulatorEndpoint;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;

import java.io.File;

/**
 * Created by ben on 6/28/2017.
 */
public class GpsInitiator {

    public static TPVObject lastCoordinates;

    public GpsInitiator() {
        GPSSimulatorEndpoint ep = null;
        try {
            ep = new GPSSimulatorEndpoint("string", new File("/home/roas/Documents/Schoolprojects/GPSd4Java/gpsd4Java/flightPlans.xsd.xml"), 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ep.addListener(new MyIObjectListener());
        ep.start();
    }

    private class MyIObjectListener implements IObjectListener {
        @Override
        public void handleTPV(TPVObject tpv) {
            GpsInitiator.lastCoordinates = tpv;
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
    }
}
