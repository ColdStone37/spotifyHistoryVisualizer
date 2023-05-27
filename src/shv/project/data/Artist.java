package shv.project.data;

import java.util.TreeSet;

public class Artist extends SpotifyData {
	private final String name;
	private TreeSet<Song> songlist;

	public Artist(String name) {

		this.name = name;
		songlist = new TreeSet<Song>();
	}

	public String toString(){
		return name;
	}

	public int compareTo(SpotifyData other){
		if(!(other instanceof Artist))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Artist) other);
	}

	public int compareTo(Artist a){
		return name.compareTo(a.name);
	}

	public boolean addSong(Song s){
		synchronized(songlist){
			return songlist.add(s);
		}
	}
}