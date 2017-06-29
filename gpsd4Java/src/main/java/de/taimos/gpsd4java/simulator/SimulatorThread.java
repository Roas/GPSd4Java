package de.taimos.gpsd4java.simulator;

/*
 * #%L
 * GPSd4Java
 * %%
 * Copyright (C) 2011 - 2012 Taimos GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.DegreeCoordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import de.taimos.gpsd4java.backend.AbstractResultParser;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.WaitableBoolean;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.TPVObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * thread reading input from GPSd server
 *
 * @author thoeger
 */
public class SimulatorThread extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(SimulatorThread.class);
	private final GPSSimulatorEndpoint endpoint;
	private final WaitableBoolean running = new WaitableBoolean(true);

	public SimulatorThread(final GPSSimulatorEndpoint endpoint) {
		if (endpoint == null) {
			throw new IllegalArgumentException("endpoint can not be null!");
		}
		this.endpoint = endpoint;
		
		this.setDaemon(true);
		this.setName("GPS Socket Thread");
	}
	
	@Override
	public void run() {


		//double distance = EarthCalc.getDistance(richmond, kew); //in meters
		//LOG.debug("Distance: " + distance);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
			TPVObject tpv = endpoint.flightPathReader.getNextTPVObject();
			if(tpv == null) {
				timer.cancel();
				return;
			}

			endpoint.handle(tpv);
			}
		}, 0, 1000);

	/*	while (this.running.get()) {

		}
		if (running.get() && !Thread.interrupted()) {
			SimulatorThread.LOG.warn("Problem encountered while reading/parsing/handling line, attempting restart");
			retry();
		}*/
	}
	
	protected void retry() {
		SimulatorThread.LOG.debug("Disconnected from GPS socket, retrying connection");
		
		while (this.running.get()) {
			try {
				running.waitFor(1000);
				this.endpoint.handleDisconnected();
				SimulatorThread.LOG.debug("Reconnected to GPS socket");
				running.set(false);
			} catch (InterruptedException ix) {
				break;
			} catch (IOException e) {
				SimulatorThread.LOG.debug("Still disconnected from GPS socket, retrying connection again");
			}
		}
	}
	
	/**
	 * Halts the socket thread.
	 *
	 * @throws InterruptedException
	 */
	public void halt() throws InterruptedException {
		this.running.set(false);
		/*try {
			this.reader.close();
		} catch (final IOException e) {
			// ignore
		}*/
		this.join(1000);
	}
}