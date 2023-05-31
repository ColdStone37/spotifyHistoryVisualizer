package shv.project.data;

/**
 * A class that stores data about an episode of a Podcast.
 */
public class Episode extends SpotifyData implements Listenable {
	private final String name;
	private final String episodeURI;
	private final Podcast podcast;

	/**
	 * Constructor for an episode.
	 * @param  name       name of the episode
	 * @param  podcast    podcast that the episode belongs to
	 * @param  episodeURI URI of the episode
	 */
	public Episode(String name, Podcast podcast, String episodeURI) {
		super();
		this.name = name;
		this.podcast = podcast;
		this.episodeURI = episodeURI;
	}

	/**
	 * Custom toString-function.
	 * @return name of the episode and podcast
	 */
	public String toString(){
		return name + " - " + podcast;
	}

	/**
	 * Compare to another SpotifyData object
	 * @param  other the object to compare against
	 * @return       a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(SpotifyData other){
		if(!(other instanceof Episode))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Episode) other);
	}

	/**
	 * Compare to another Episode object
	 * @param  e the podcast to compare against
	 * @return   a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(Episode e){
		return episodeURI.compareTo(e.episodeURI);
	}

	/**
	 * Gets the URI of the episode.
	 * @return URI String
	 */
	public String getURI(){
		return episodeURI;
	}
}