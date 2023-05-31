package shv.project.data;

/**
 * A class that stores data about a Song.
 */
public class Song extends SpotifyData implements Listenable {
	private final String name, trackURI;
	private final Artist artist;
	private final Album album;

	/**
	 * Constructor for a Song.
	 * @param  name     name of the song
	 * @param  artist   artist of the song
	 * @param  album    album of the song
	 * @param  trackURI Spotify URI of the song
	 */
	public Song(String name, Artist artist, Album album, String trackURI) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.trackURI = trackURI;
	}

	/**
	 * Custom toString-function.
	 * @return name of the song and the artist
	 */
	public String toString(){
		return name + " - " + artist;
	}

	/**
	 * Compare to another SpotifyData object
	 * @param  other the object to compare against
	 * @return       a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(SpotifyData other){
		if(!(other instanceof Song))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Song) other);
	}

	/**
	 * Compare to another Song object
	 * @param  s the song to compare against
	 * @return   a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(Song s){
		return trackURI.compareTo(s.trackURI);
	}

	/**
	 * Gets the URI of the song.
	 * @return URI String
	 */
	public String getURI(){
		return trackURI;
	}
}