package shv.project.parser;

import org.json.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;
import java.time.Duration;
import shv.project.data.*;

/**
 * A Class that parses the data stored in the Extended Streaming data that can be requested from spotify.
 */
public class SpotifyHistoryParser extends Thread {
	//thread data
	private final String filename;
	private Duration threadDuration;
	private boolean dataLoaded = false;

	//Shared data
	private static int threadCount = 0;
	private static TreeSet<Song> songlist;
	private static TreeSet<Artist> artistlist;
	private static TreeSet<Album> albumlist;
	private static TreeSet<ListeningEvent> eventlist;
	private static Duration totalDuration;
	private static Object durationMonitor;
	private final static String name_key = "master_metadata_track_name";
	private final static String artist_key = "master_metadata_album_artist_name";
	private final static String album_key = "master_metadata_album_album_name";
	private final static String uri_key = "spotify_track_uri";
	private final static String ts_key = "ts";
	private final static String ms_key = "ms_played";

	/**
	 * Constructor for a parser that expects a filename.
	 * @param  filename The file to load
	 */
	public SpotifyHistoryParser(String filename){
		this.filename = filename;
		if(threadCount == 0)
			reset();
		threadCount++;
	}

	/**
	 * Resets all static variables in the object.
	 */
	public static void reset(){
		songlist = new TreeSet<Song>();
		artistlist = new TreeSet<Artist>();
		albumlist = new TreeSet<Album>();
		eventlist = new TreeSet<ListeningEvent>();
		durationMonitor = new Object();
		totalDuration = Duration.ZERO;
	}

	/**
	 * The function that loads and parses the file (called asynchronously by .start()).
	 */
	public void run(){
		//load the file into a String
		String historyString = null;
		try {
			historyString = loadfile();
		} catch(IOException e){
			System.out.println("ERROR: file " + filename + " was not found.");
			return;
		}

		//parse the json
		JSONArray historyJson = new JSONArray(historyString);
		processJSON(historyJson);
		dataLoaded = true;
	}

	/**
	 * Whether the thread finished
	 * @return true if the thread terminated successfully
	 */
	public boolean finished(){
		return dataLoaded;
	}

	/**
	 * Loads a textfile into a string.
	 * @return Textfile
	 * @throws IOException   if the file wasn't read successfully
	 */
	private String loadfile() throws IOException{
		Path p = Path.of(filename);
		return Files.readString(p);
	}

	/**
	 * Processes the parsed JSON data from the file
	 * @param historyJson JSONArray Object containing the data that was parsed
	 */
	private void processJSON(JSONArray historyJson){
		Iterator i = historyJson.iterator();
		Duration d = Duration.ZERO;
		while(i.hasNext()){
			d = d.plus(processSong((JSONObject) i.next()));
		}
		synchronized(durationMonitor){
			totalDuration = totalDuration.plus(d);
		}
	}

	/**
	 * Processes a single song
	 * @param  o JSON-Object containing a song
	 * @return   Duration of the song
	 */
	private Duration processSong(JSONObject o){
		if(o.get(name_key) == JSONObject.NULL)
			return Duration.ZERO;

		//artist
		Artist artist = new Artist(o.getString(artist_key));
		artist = (Artist)addAndGet(artist, artistlist);

		//album
		Album album = new Album(o.getString(album_key), artist);
		album = (Album)addAndGet(album, albumlist);

		//song
		Song song = new Song(o.getString(name_key), artist, album, o.getString(uri_key));
		if(!addToSet(song, songlist)){
			song = (Song)getFromSet(song, songlist);
		} else {
			artist.addSong(song);
			album.addSong(song);
		}

		//event
		ListeningEvent le = new ListeningEvent(song, o.getString(ts_key), o.getLong(ms_key));
		song.addEvent(le);
		artist.addEvent(le);
		album.addEvent(le);

		return le.getDuration();
	}

	/**
	 * Adds a SpotifyData-Object (Song, Artist or Album) if it wasn't already in the TreeSet and returns the object from the set
	 * @param  d object to add
	 * @param  s set to add to
	 * @return   object in the set
	 */
	private SpotifyData addAndGet(SpotifyData d, TreeSet s){
		if(!addToSet(d, s))
			return getFromSet(d, s);
		return d;
	}

	/**
	 * Adds a SpotifyData-Object (Song, Artist or Album) to a set and makes sure that there are no multithreading problems
	 * @param  d object to add
	 * @param  s set to add to
	 * @return   object in the set
	 */
	private boolean addToSet(SpotifyData d, TreeSet s){
		synchronized(s){
			return s.add(d);
		}
	}

	/**
	 * Gets an object from the set if it is already contained.
	 * @param  d object to get
	 * @param  s set to get from
	 * @return   SpotifyData object
	 */
	private SpotifyData getFromSet(SpotifyData d, TreeSet s){
		synchronized(s){
			return (SpotifyData)s.floor(d);
		}
	}

	/**
	 * Loads multiple json-files containing the extended Spotify History.
	 * @param  filenames The names of the files to load
	 * @return           true if all files were loaded successfully
	 */
	public static boolean loadFiles(String[] filenames){
		SpotifyHistoryParser[] parserList = new SpotifyHistoryParser[filenames.length];
		for(int i = 0; i < parserList.length; i++){
			parserList[i] = new SpotifyHistoryParser(filenames[i]);
			parserList[i].start();
		}
		for(int i = 0; i < parserList.length; i++){
			try {
				parserList[i].join();
				if(!parserList[i].finished())
					return false;
			} catch(InterruptedException e){
				return false;
			}
		}
		return true;
	}

	/**
	 * Test function that loads the files and outputs some data about them.
	 * @param args filenames
	 */
	public static void main(String[] args){
		loadFiles(args);

		System.out.println("Time: " + totalDuration.toDays() + " days");
		System.out.println("Songs: " + songlist.size() + " - Artists: " + artistlist.size() + " - Albums: " + albumlist.size());
	}
}