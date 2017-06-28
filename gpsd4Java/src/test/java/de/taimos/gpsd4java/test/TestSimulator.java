package de.taimos.gpsd4java.test;

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

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.simulator.GPSSimulatorEndpoint;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * This class provides tests during the startup phase of GPSd4Java<br>
 * It will later be replaced by JUnit Tests
 * 
 * created: 17.01.2011
 * 
 */
public class TestSimulator {

	static final Logger log = LoggerFactory.getLogger(TestSimulator.class);


	private TestSimulator() {
	}
	
	/**
	 * @param args
	 *            the args
	 */
	public static void main(final String[] args) {
		try {
			final GPSSimulatorEndpoint ep = new GPSSimulatorEndpoint("string", new File("gpsd4Java/flightPlans.xsd.xml"));
			
			ep.addListener(new ObjectListener() {

				@Override
				public void handleTPV(final TPVObject tpv) {
					TestSimulator.log.info("TPV: {}", tpv);
				}
				
				@Override
				public void handleSKY(final SKYObject sky) {
					TestSimulator.log.info("SKY: {}", sky);
					for (final SATObject sat : sky.getSatellites()) {
						TestSimulator.log.info("  SAT: {}", sat);
					}
				}
				
				@Override
				public void handleSUBFRAME(final SUBFRAMEObject subframe) {
					TestSimulator.log.info("SUBFRAME: {}", subframe);
				}
				
				@Override
				public void handleATT(final ATTObject att) {
					TestSimulator.log.info("ATT: {}", att);
				}
				
				@Override
				public void handleDevice(final DeviceObject device) {
					TestSimulator.log.info("Device: {}", device);
				}
				
				@Override
				public void handleDevices(final DevicesObject devices) {
					for (final DeviceObject d : devices.getDevices()) {
						TestSimulator.log.info("Device: {}", d);
					}
				}
			});
			
			ep.start();
			
			TestSimulator.log.info("Version: {}", ep.version());
			
			Thread.sleep(60000);
		} catch (final Exception e) {
			TestSimulator.log.error("Problem encountered", e);
		}
	}
}
