package shv.project.preprocessing;

import shv.project.data.*;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class that processes the data parsed by a Parser.
 */
public class SpotifyHistoryProcessor {
	private final static int printCount = 25;
	private SpotifyDataSetBundle parsedData;

	/**
	 * Constructor for a SpotifyHistoryProcessor-object. A parser needs to have been run beforehand.
	 */
	public SpotifyHistoryProcessor(){
		if(!SpotifyHistoryParser.isParsed())
			throw new NullPointerException("A file needs to be parsed using a SpotifyHistoryParser before this constructor can be called.");
		parsedData = SpotifyHistoryParser.getBundle();
	}

	/**
	 * A constructer that takes a list of files to parse and process.
	 * @param  filenames             the files to parse
	 * @throws FileNotFoundException if the file couldn't be loaded
	 */
	public SpotifyHistoryProcessor(String[] filenames) throws FileNotFoundException {
		if(!SpotifyHistoryParser.parse(filenames))
			throw new FileNotFoundException();
		parsedData = SpotifyHistoryParser.getBundle();
	}

	/**
	 * Gets list of songs sorted by listening time.
	 * @return sorted list of songs
	 */
	public List<Song> getSongRanking(){
		return getRanking(parsedData.getSonglist());
	}

	/**
	 * Gets list of artists sorted by listening time.
	 * @return sorted list of artists
	 */
	public List<Artist> getArtistRanking(){
		return getRanking(parsedData.getArtistlist());
	}

	/**
	 * Gets list of albums sorted by listening time.
	 * @return sorted list of albums
	 */
	public List<Album> getAlbumRanking(){
		return getRanking(parsedData.getAlbumlist());
	}

	/**
	 * Sorts a TreeSet with SpotifyData into a list ordered by listening time.
	 * @param  data Set with SpotifyData Objects
	 * @return      sorted list of SpotifyData objects
	 */
	private List getRanking(TreeSet data){
		ArrayList<SpotifyData> ranking = new ArrayList<SpotifyData>(data.size());
		ranking.addAll(data);
		Comparator sdc = new SpotifyDataComparator();
		Collections.sort(ranking, sdc);
		return ranking;
	}

	/**
	 * Prints a Ranking to the console.
	 * @param data sorted List of data
	 * @param name name of the objects in the list
	 * @param len  number of elements to print from the start
	 */
	private static void printRanking(List data, String name, int len){
		int rankLength = (int)Math.floor(Math.log10(len))+1;
		System.out.println("Top " + len + " " + name + ":");
		for(int i = 0; i < Integer.min(len, data.size()); i++)
			System.out.printf("%0" + rankLength + "d. " + data.get(i) + "\n", i + 1);
	}

	/**
	 * Tester function that loads json-data and prints a ranking.
	 * @param  args                  filenames of your listening history
	 * @throws FileNotFoundException if the files couldn't be loaded
	 */
	public static void main(String[] args) throws FileNotFoundException {
		SpotifyHistoryProcessor processor = new SpotifyHistoryProcessor(args);
		printRanking(processor.getSongRanking(), "Songs", printCount);
		System.out.println();
		printRanking(processor.getArtistRanking(), "Artists", printCount);
		System.out.println();
		printRanking(processor.getAlbumRanking(), "Albums", printCount);
	}
}