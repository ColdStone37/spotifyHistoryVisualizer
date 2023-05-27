package shv.project.data;

import java.util.TreeSet;

/**
 * A class that stores data about an Album.
 */
public class Album extends SpotifyData {
	private final String name;
	private final Artist artist;
	private TreeSet<Song> songlist;

	/**
	 * Constructor for an Album-object.
	 * @param  name   Name of the album
	 * @param  artist Artist of the album.
	 */
	public Album(String name, Artist artist) {
		super();
		this.name = name;
		this.artist = artist;
		songlist = new TreeSet<Song>();
	}

	/**
	 * Custom toString-function.
	 * @return Name and artist of the album
	 */
	public String toString(){
		return name + " - " + artist.toString();
	}

	/**
	 * Compare to another SpotifyData object
	 * @param  other the object to compare against
	 * @return       a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(SpotifyData other){
		if(!(other instanceof Album))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Album) other);
	}

	/**
	 * Compare to another Album object
	 * @param  a the album to compare against
	 * @return   a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	public int compareTo(Album a){
		int nameCompared = name.compareTo(a.name);
		return nameCompared == 0 ? artist.compareTo(a.artist) : nameCompared;
	}

	/**
	 * Adds a song to the album.
	 * @param  s the song to add
	 * @return   true if the song wasn't already in the album
	 */
	public boolean addSong(Song s){
		synchronized(songlist){
			return songlist.add(s);
		}
	}
}