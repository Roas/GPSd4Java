package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.simulator.models.FlightPath;
import de.taimos.gpsd4java.simulator.models.FlightPlans;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ben on 28-5-2017.
 */
public class GPSSimulatorEndpoint implements IGPSdEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(GPSSimulatorEndpoint.class);

    private Socket socket;

    protected BufferedReader in;

    protected BufferedWriter out;

    private SimulatorThread listenThread;

    private final List<IObjectListener> listeners = new ArrayList<IObjectListener>(1);

    private IGPSObject asyncResult = null;

    private final Object asyncMutex = new Object();

    private final Object asyncWaitMutex = new Object();

    private String lastWatch;

    private AtomicLong retryInterval = new AtomicLong(1000);

    protected FlightPathReader flightPathReader;


    public GPSSimulatorEndpoint(String flightCode, File flightplansXmlFile) throws Exception {
        this.flightPathReader = new FlightPathReader(flightCode, flightplansXmlFile);
    }

    /**
     * start the endpoint
     */
    @Override
    public void start() {
        this.listenThread = new SimulatorThread(this);
        this.listenThread.start();

        try {
            Thread.sleep(500);
        } catch (final InterruptedException e) {
            GPSSimulatorEndpoint.LOG.debug("Interrupted while sleeping", e);
        }
    }

    /**
     * Stops the endpoint.
     */
    @Override
    public void stop() {
        try {
            this.listeners.clear();
            if (this.listenThread != null) {
                this.listenThread.halt();
            }
        } catch (final Exception e) {
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
    public VersionObject version() throws IOException {
        VersionObject version = new VersionObject();
        version.setProtocolMajor(1);
        version.setProtocolMinor(0);
        version.setRelease("Simulator release");
        version.setRev("Rev 1");
        return version;
        //return this.syncCommand("?VERSION;", VersionObject.class);
    }

    /**
     * @param listener the listener to add
     */
    @Override
    public void addListener(final IObjectListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    @Override
    public void removeListener(final IObjectListener listener) {
        this.listeners.remove(listener);
    }

    private <T extends IGPSObject> T syncCommand(final String command, final Class<T> responseClass) throws IOException {
        synchronized (this.asyncMutex) {
            this.out.write(command + "\n");
            this.out.flush();
            if (responseClass == WatchObject.class) {
                lastWatch = command;
            }
            while (true) {
                // wait for awaited message
                final IGPSObject result = this.waitForResult();
                if ((result == null) || result.getClass().equals(responseClass)) {
                    return responseClass.cast(result);
                }
            }
        }
    }

    /*
     * wait for a response for one second
     */
    private IGPSObject waitForResult() {
        synchronized (this.asyncWaitMutex) {
            if (this.asyncResult == null) {
                try {
                    this.asyncWaitMutex.wait(1000);
                } catch (final InterruptedException e) {
                    GPSSimulatorEndpoint.LOG.info("Interrupted while waiting for result", e);
                }
            }
            final IGPSObject result = this.asyncResult;
            this.asyncResult = null;
            return result;
        }
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
        } else {
            // object was requested, so put it in the response object
            synchronized (this.asyncWaitMutex) {
                this.asyncResult = object;
                this.asyncWaitMutex.notifyAll();
            }
        }
    }

    /**
     * Our socket thread got disconnect and is exiting.
     */
    void handleDisconnected() throws IOException {
        synchronized (this.asyncMutex) {
            this.listenThread = new SimulatorThread(this);
            this.listenThread.start();
            if (lastWatch != null) { // restore watch if we had one.
                this.syncCommand(lastWatch, WatchObject.class);
            }
        }

    }
}
