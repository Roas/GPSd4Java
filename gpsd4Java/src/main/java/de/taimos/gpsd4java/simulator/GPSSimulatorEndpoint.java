package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GPSSimulatorEndpoint implements IGPSdEndpoint
{
    public static int DEFAULT_TIME_ACCELERATION_FACTOR = 1;
    private static final Logger LOG = LoggerFactory.getLogger(GPSSimulatorEndpoint.class);
    private SimulatorThread listenThread;
    private final List<IObjectListener> listeners = new ArrayList<IObjectListener>(1);
    protected FlightPathProcessor flightPathProcessor;


    public GPSSimulatorEndpoint(String flightCode, File flightplansXmlFile) throws Exception
    {
        this(flightCode, flightplansXmlFile, DEFAULT_TIME_ACCELERATION_FACTOR);
    }

    public GPSSimulatorEndpoint(String flightCode, File flightplansXmlFile, int timeAccelerationFactor) throws Exception
    {
        FlightPath flightPath = FlightPlanReader.getFlightPathFromXml(flightCode, flightplansXmlFile);
        this.flightPathProcessor = new FlightPathProcessor(flightPath, timeAccelerationFactor);
    }

    /**
     * start the endpoint
     */
    @Override
    public void start()
    {
        this.listenThread = new SimulatorThread(this);
        this.listenThread.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (final InterruptedException e)
        {
            GPSSimulatorEndpoint.LOG.debug("Interrupted while sleeping", e);
        }
    }

    /**
     * Stops the endpoint.
     */
    @Override
    public void stop()
    {
        try
        {
            this.listeners.clear();
            if (this.listenThread != null)
            {
                this.listenThread.halt();
            }
        }
        catch (final Exception e)
        {
            GPSSimulatorEndpoint.LOG.debug("Interrupted while waiting for listenThread to stop", e);
        }
        this.listenThread = null;
    }

    /**
     * Poll GPSd version
     *
     * @return {@link VersionObject}
     * @throws IOException on IO error in socket
     */
    @Override
    public VersionObject version() throws IOException
    {
        VersionObject version = new VersionObject();
        version.setProtocolMajor(1);
        version.setProtocolMinor(0);
        version.setRelease("Simulator release");
        version.setRev("Rev 1");
        return version;
    }

    /**
     * @param listener the listener to add
     */
    @Override
    public void addListener(final IObjectListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    @Override
    public void removeListener(final IObjectListener listener)
    {
        this.listeners.remove(listener);
    }

    /*
     * handle incoming messages and dispatch them
     */
    void handle(final IGPSObject object) {
        if (object instanceof TPVObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleTPV((TPVObject) object);
            }
        } else if (object instanceof SKYObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleSKY((SKYObject) object);
            }
        } else if (object instanceof ATTObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleATT((ATTObject) object);
            }
        } else if (object instanceof SUBFRAMEObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleSUBFRAME((SUBFRAMEObject) object);
            }
        } else if (object instanceof DevicesObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleDevices((DevicesObject) object);
            }
        } else if (object instanceof DeviceObject) {
            for (final IObjectListener l : this.listeners) {
                l.handleDevice((DeviceObject) object);
            }
        }
    }
}
