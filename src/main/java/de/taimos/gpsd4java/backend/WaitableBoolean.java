package de.taimos.gpsd4java.backend;

/**
 * Not as efficient as AtomicBoolean but you can wait on it.
 *
 * @author TimW
 */
public class WaitableBoolean {
	
	private boolean val;
	
	public WaitableBoolean(boolean b) {
		this.val = b;
	}

	public synchronized void set(boolean value) {
		this.val = value;
		notifyAll();
	}
	
	public synchronized boolean get() {
		return this.val;
	}

	public synchronized void waitFor(long millis) throws InterruptedException {
		super.wait(millis);
	}
}