package shv.project.parser;

import org.json.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;
import java.time.Duration;
import shv.project.data.*;

public class SpotifyHistoryParser extends Thread {
	//thread data
	private final String filename;
	private Duration threadDuration;

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

	public SpotifyHistoryParser(String filename){
		this.filename = filename;
		if(threadCount == 0){
			songlist = new TreeSet<Song>();
			artistlist = new TreeSet<Artist>();
			albumlist = new TreeSet<Album>();
			eventlist = new TreeSet<ListeningEvent>();
			durationMonitor = new Object();
			totalDuration = Duration.ZERO;
		}
		threadCount++;
	}

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
	}

	private String loadfile() throws IOException{
		Path p = Path.of(filename);
		return Files.readString(p);
	}

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

	private Duration processSong(JSONObject o){
		if(o.get(name_key) == JSONObject.NULL)
			return Duration.ZERO;

		//artist
		Artist artist = new Artist(o.getString(artist_key));
		artist = (Artist)addAndGet(artist, artistlist);

		//album
		Album album = new Album(o.getString(album_key), artist);
		album = (Album)addAndGet(album, albumlist);

		Song song = new Song(o.getString(name_key), artist, album, o.getString(uri_key));
		if(!addToSet(song, songlist)){
			song = (Song)getFromSet(song, songlist);
		} else {
			artist.addSong(song);
			album.addSong(song);
		}

		ListeningEvent le = new ListeningEvent(song, o.getString(ts_key), o.getLong(ms_key));
		song.addEvent(le);
		artist.addEvent(le);
		album.addEvent(le);

		return le.getDuration();
	}

	private SpotifyData addAndGet(SpotifyData d, TreeSet s){
		if(!addToSet(d, s))
			return getFromSet(d, s);
		return d;
	}

	private boolean addToSet(SpotifyData d, TreeSet s){
		synchronized(s){
			return s.add(d);
		}
	}

	private SpotifyData getFromSet(SpotifyData d, TreeSet s){
		synchronized(s){
			return (SpotifyData)s.floor(d);
		}
	}

	private static void printList(List list, int elements){
		int posLen = (int)Math.floor(Math.log10(elements))+1;
		for(int i = 0; i < elements && i < list.size(); i++){
			System.out.printf("%0" + posLen + "d. " + list.get(i) + "\n", i+1);
		}
	}
	public static void main(String[] args) throws InterruptedException{
		Thread[] parserList = new Thread[args.length];
		long s = System.nanoTime();
		for(int i = 0; i < args.length; i++){
			parserList[i] = new SpotifyHistoryParser(args[i]);
			parserList[i].start();
		}
		for(int i = 0; i < args.length; i++){
			parserList[i].join();
		}

		System.out.println("Time: " + totalDuration.toDays() + " days");
		System.out.println("Songs: " + songlist.size() + " - Artists: " + artistlist.size() + " - Albums: " + albumlist.size());
	}
}