package shv.project.data;

import java.util.TreeSet;

/**
 * A class that stores data about a Podcast.
 */
public class Podcast extends SpotifyData {
	private final String name;
	private TreeSet<Episode> episodelist;

	/**
	 * Constructor for a Podcast.
	 * @param  name name of the podcast
	 */
	public Podcast(String name) {
		super();
		this.name = name;
		episodelist = new TreeSet<Episode>();
	}

	/**
	 * Custom toString-function.
	 * @return name of the podcast
	 */
	public String toString(){
		return name;
	}

	/**
	 * Compare to another SpotifyData object
	 * @param  other the object to compare against
	 * @return       a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(SpotifyData other){
		if(!(other instanceof Podcast))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Podcast) other);
	}

	/**
	 * Compare to another Podcast object
	 * @param  p the podcast to compare against
	 * @return   a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(Podcast p){
		return name.compareTo(p.name);
	}

	/**
	 * Adds an episode to the podcast.
	 * @param  e the episode to add
	 * @return   true if the episode wasn't already in the podcast
	 */
	public boolean addEpisode(Episode e){
		synchronized(episodelist){
			return episodelist.add(e);
		}
	}
}