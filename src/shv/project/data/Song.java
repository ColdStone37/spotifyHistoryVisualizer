package shv.project.data;

public class Song extends SpotifyData {
	private final String name, trackURI;
	private final Artist artist;
	private final Album album;

	public Song(String name, Artist artist, Album album, String trackURI) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.trackURI = trackURI;
	}

	public String toString(){
		return name + " - " + artist;
	}

	public int compareTo(SpotifyData other){
		if(!(other instanceof Song))
			return (int)(getTotalDuration().toMillis() - other.getTotalDuration().toMillis());
		return compareTo((Song) other);
	}

	public int compareTo(Song s){
		return trackURI.compareTo(s.trackURI);
	}
}