package shv.project.data;

import java.time.Duration;
import java.util.TreeSet;

public abstract class SpotifyData implements Comparable<SpotifyData>{
	private TreeSet<ListeningEvent> eventlist;
	private Duration totalDuration;

	public SpotifyData() {
		eventlist = new TreeSet<ListeningEvent>();
		totalDuration = Duration.ZERO;
	}

	public synchronized void addEvent(ListeningEvent e){
		eventlist.add(e);
		totalDuration = totalDuration.plus(e.getDuration());
	}

	public Duration getTotalDuration(){
		return totalDuration;
	}
}