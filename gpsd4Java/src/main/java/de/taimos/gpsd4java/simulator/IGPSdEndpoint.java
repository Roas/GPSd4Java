package de.taimos.gpsd4java.simulator;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.types.PollObject;
import de.taimos.gpsd4java.types.VersionObject;
import de.taimos.gpsd4java.types.WatchObject;
import org.json.JSONException;

import java.io.IOException;

public interface IGPSdEndpoint
{
    void start();

    void stop();

    VersionObject version() throws IOException;

    void addListener(IObjectListener listener);

    void removeListener(IObjectListener listener);
}
