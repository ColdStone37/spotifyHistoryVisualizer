package shv.project.data;

import java.util.TreeSet;
import java.time.Duration;
import shv.project.data.*;

/**
 * A class that bundles the data generated by a parser.
 */
public class SpotifyDataSetBundle {
	private TreeSet<Song> songlist;
	private TreeSet<Artist> artistlist;
	private TreeSet<Album> albumlist;
	private TreeSet<Podcast> podcastlist;
	private TreeSet<Episode> episodelist;
	private TreeSet<ListeningEvent> eventlist;
	private Duration totalDuration;

	/**
	 * Constructor for a new bundle.
	 * @param  songlist      set of all songs
	 * @param  artistlist    set of all artists
	 * @param  albumlist     set of all albums
	 * @param  podcastlist   set of all podcasts
	 * @param  episodelist   set of all episodes
	 * @param  eventlist     set of all events
	 * @param  totalDuration total listening duration
	 */
	public SpotifyDataSetBundle(TreeSet<Song> songlist, TreeSet<Artist> artistlist, TreeSet<Album> albumlist, TreeSet<Podcast> podcastlist, TreeSet<Episode> episodelist, TreeSet<ListeningEvent> eventlist, Duration totalDuration){
		this.songlist = songlist;
		this.artistlist = artistlist;
		this.albumlist = albumlist;
		this.podcastlist = podcastlist;
		this.episodelist = episodelist;
		this.eventlist = eventlist;
		this.totalDuration = totalDuration;
	}

	/**
	 * Gets the set of all songs.
	 * @return set of songs
	 */
	public TreeSet<Song> getSonglist(){
		return songlist;
	}

	/**
	 * Gets the set of all artists.
	 * @return set of artists
	 */
	public TreeSet<Artist> getArtistlist(){
		return artistlist;
	}

	/**
	 * Gets the set of all albums.
	 * @return set of albums
	 */
	public TreeSet<Album> getAlbumlist(){
		return albumlist;
	}

	/**
	 * Gets the set of all podcasts.
	 * @return set of podcasts
	 */
	public TreeSet<Podcast> getPodcastlist(){
		return podcastlist;
	}

	/**
	 * Gets the set of all episodes.
	 * @return set of episodes
	 */
	public TreeSet<Episode> getEpisodelist(){
		return episodelist;
	}

	/**
	 * Gets the set of all events.
	 * @return set of events
	 */
	public TreeSet<ListeningEvent> getEventlist(){
		return eventlist;
	}

	/**
	 * Gets the total listening duration.
	 * @return total listening duration
	 */
	public Duration getTotalDuration(){
		return totalDuration;
	}
}