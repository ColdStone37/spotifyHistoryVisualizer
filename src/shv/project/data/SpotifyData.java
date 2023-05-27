package shv.project.data;

import java.time.Duration;
import java.util.TreeSet;

/**
 * An abstract class that is inherited by all data objects and stores listening event data.
 */
public abstract class SpotifyData implements Comparable<SpotifyData>{
	private TreeSet<ListeningEvent> eventlist;
	private Duration totalDuration;

	/**
	 * Constructor for a SpotifyData object.
	 */
	public SpotifyData() {
		eventlist = new TreeSet<ListeningEvent>();
		totalDuration = Duration.ZERO;
	}

	/**
	 * Adds an event to object.
	 * @param e The event to add
	 */
	public synchronized void addEvent(ListeningEvent e){
		eventlist.add(e);
		totalDuration = totalDuration.plus(e.getDuration());
	}

	/**
	 * Gets the total listening duration.
	 * @return total duration
	 */
	public Duration getTotalDuration(){
		return totalDuration;
	}
}