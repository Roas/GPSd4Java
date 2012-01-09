/**
 * Copyright 2011 Thorsten Höger, Taimos GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.taimos.gpsd4java.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.ResultParser;
import de.taimos.gpsd4java.types.DeviceObject;
import de.taimos.gpsd4java.types.DevicesObject;
import de.taimos.gpsd4java.types.TPVObject;

/**
 * This class provides tests during the startup phase of GPSd4Java<br>
 * It will later be replaced by JUnit Tests
 * 
 * created: 17.01.2011
 * 
 */
public class Tester {

	static final Logger log = Logger.getLogger(Tester.class.getName());

	private Tester() {
	}

	/**
	 * @param args
	 *            the args
	 */
	public static void main(final String[] args) {
		try {
			String host = "localhost";
			int port = 2947;

			switch (args.length) {
			case 0:
				// Nothing to do, use default
				break;
			case 1:
				// only server specified
				host = args[0];
				break;
			case 2:
				// Server and port specified
				host = args[0];
				if (args[1].matches("\\d+")) {
					port = Integer.parseInt(args[1]);
				}
				break;
			default:
				break;
			}

			final GPSdEndpoint ep = new GPSdEndpoint(host, port, new ResultParser());

			ep.addListener(new ObjectListener() {

				@Override
				public void handleTPV(final TPVObject tpv) {
					log.log(Level.INFO, "Listener: {0}", tpv);
				}

				@Override
				public void handleDevices(final DevicesObject devices) {
					for (final DeviceObject d : devices.getDevices()) {
						log.log(Level.INFO, "{0}", d);
					}
				}
			});

			ep.start();

			log.log(Level.INFO, "Version: {0}", ep.version());

			log.log(Level.INFO, "Watch: {0}", ep.watch(true, true));

			Thread.sleep(60000);
		} catch (final Exception e) {
			log.log(Level.SEVERE, null, e);
		}
	}
}
