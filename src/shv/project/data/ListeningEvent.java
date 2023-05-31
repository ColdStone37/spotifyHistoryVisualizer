package shv.project.data;

import java.time.Duration;
import java.time.Instant;

/**
 * A class for storing a single listening event of a song played on spotify.
 */
public class ListeningEvent implements Comparable<ListeningEvent>{
	private final Listenable listenedTo;
	private final Instant timestamp;
	private final Duration duration;

	/**
	 * A Constructor for a listening event.
	 * @param  l  what was listened to
	 * @param  ts the timestamp of when it was played
	 * @param  ms the time in milliseconds for how long it was played
	 */
	public ListeningEvent(Listenable l, String ts, long ms){
		listenedTo = l;
		timestamp = Instant.parse(ts);
		duration = Duration.ofMillis(ms);
	}

	/**
	 * Compares two Listening events
	 * @param  other the ListeningEvent to compare against
	 * @return       a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(ListeningEvent other){
		int tsCompared = timestamp.compareTo(other.timestamp);
		if(tsCompared != 0)
			return tsCompared;
		int durationCompared = duration.compareTo(other.duration);
		if(durationCompared != 0)
			return durationCompared;
		return listenedTo.getURI().compareTo(other.listenedTo.getURI());
	}

	/**
	 * Returns the duration of how long the song was played.
	 * @return Duration object
	 */
	public Duration getDuration(){
		return duration;
	}
}