package shv.project.data;

import java.util.TreeSet;

/**
 * A class that stores data about an Artist.
 */
public class Artist extends SpotifyData {
	private final String name;
	private TreeSet<Song> songlist;

	/**
	 * A Constructor for an Artist-object.
	 * @param  name The name of the artist
	 */
	public Artist(String name) {
		this.name = name;
		songlist = new TreeSet<Song>();
	}

	/**
	 * Custom toString-function.
	 * @return Name of the artist
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
		if(!(other instanceof Artist))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Artist) other);
	}

	/**
	 * Compare to another Artist object
	 * @param  a the artist to compare against
	 * @return   a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(Artist a){
		return name.compareTo(a.name);
	}

	/**
	 * Adds a song to the artist.
	 * @param  s the song to add
	 * @return   true if the song wasn't already added to the artist
	 */
	public boolean addSong(Song s){
		synchronized(songlist){
			return songlist.add(s);
		}
	}
}