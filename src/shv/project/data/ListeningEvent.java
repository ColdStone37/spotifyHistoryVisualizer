package shv.project.data;

import java.time.Duration;
import java.time.Instant;

/**
 * A class for storing a single listening event of a song played on spotify.
 */
public class ListeningEvent implements Comparable<ListeningEvent>{
	private final Song song;
	private final Instant timestamp;
	private final Duration duration;

	/**
	 * A Constructor for a listening event.
	 * @param  s  the song that was played
	 * @param  ts the timestamp of when it was played
	 * @param  ms the time in milliseconds for how long it was played
	 */
	public ListeningEvent(Song s, String ts, long ms){
		song = s;
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
		return song.compareTo(other.song);
	}

	/**
	 * Returns the duration of how long the song was played.
	 * @return Duration object
	 */
	public Duration getDuration(){
		return duration;
	}
}