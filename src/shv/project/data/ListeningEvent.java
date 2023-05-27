package shv.project.data;

import java.time.Duration;
import java.time.Instant;

public class ListeningEvent implements Comparable<ListeningEvent>{
	private final Song song;
	private final Instant timestamp;
	private final Duration duration;

	public ListeningEvent(Song s, String ts, long ms){
		song = s;
		timestamp = Instant.parse(ts);
		duration = Duration.ofMillis(ms);
	}

	public int compareTo(ListeningEvent other){
		int tsCompared = timestamp.compareTo(other.timestamp);
		if(tsCompared != 0)
			return tsCompared;
		int durationCompared = duration.compareTo(other.duration);
		if(durationCompared != 0)
			return durationCompared;
		return song.compareTo(other.song);
	}

	public Duration getDuration(){
		return duration;
	}
}