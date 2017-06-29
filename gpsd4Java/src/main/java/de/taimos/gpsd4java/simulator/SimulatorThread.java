package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.backend.WaitableBoolean;
import de.taimos.gpsd4java.types.TPVObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorThread extends Thread
{
	private static final int TIMER_INTERVAL = 1000;
	private static final Logger LOG = LoggerFactory.getLogger(SimulatorThread.class);
	private final GPSSimulatorEndpoint endpoint;
	private final WaitableBoolean running = new WaitableBoolean(true);

	public SimulatorThread(final GPSSimulatorEndpoint endpoint)
	{
		if (endpoint == null)
		{
			throw new IllegalArgumentException("endpoint can not be null!");
		}
		this.endpoint = endpoint;

		this.setDaemon(true);
		this.setName("GPS Simulator Socket Thread");
	}

	@Override
	public void run()
	{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				TPVObject tpv = endpoint.flightPathProcessor.getNextTPVObject();
				if(tpv == null)
				{
					timer.cancel();
					return;
				}
				endpoint.handle(tpv);
			}
		}, 0, TIMER_INTERVAL);
	}

	protected void retry()
	{
		SimulatorThread.LOG.debug("Disconnected from GPS socket, retrying connection");

		while (this.running.get())
		{
			try
			{
				running.waitFor(1000);
				SimulatorThread.LOG.debug("Reconnected to GPS socket");
				running.set(false);
			}
			catch (InterruptedException ix)
			{
				break;
			}
		}
	}

	/**
	 * Halts the socket thread.
	 *
	 * @throws InterruptedException
	 */
	public void halt() throws InterruptedException
	{
		this.running.set(false);
		this.join(1000);
	}
}