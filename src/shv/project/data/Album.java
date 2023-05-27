package shv.project.data;

import java.util.TreeSet;

public class Album extends SpotifyData {
	private final String name;
	private final Artist artist;
	private TreeSet<Song> songlist;

	public Album(String name, Artist artist) {
		super();
		this.name = name;
		this.artist = artist;
		songlist = new TreeSet<Song>();
	}

	public String toString(){
		return name + " - " + artist.toString();
	}

	public int compareTo(SpotifyData other){
		if(!(other instanceof Album))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Album) other);
	}

	public int compareTo(Album a){
		int nameCompared = name.compareTo(a.name);
		return nameCompared == 0 ? artist.compareTo(a.artist) : nameCompared;
	}

	public boolean addSong(Song s){
		synchronized(songlist){
			return songlist.add(s);
		}
	}
}