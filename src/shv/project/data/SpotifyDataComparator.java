package shv.project.data;

import java.util.Comparator;

/**
 * Comparator that compares the duration of the objects.
 */
public class SpotifyDataComparator implements Comparator<SpotifyData> {
	/**
	 * Compares to SpotifyData-Objects
	 * @param  d1 the first object
	 * @param  d2 the seconds object
	 * @return    a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	@Override
	public int compare(SpotifyData d1, SpotifyData d2){
		return (int)(d2.getTotalDuration().toMillis() - d1.getTotalDuration().toMillis());
	}
}