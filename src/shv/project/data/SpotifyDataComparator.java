package shv.project.data;

import java.util.Comparator;

public class SpotifyDataComparator implements Comparator<SpotifyData> {
	@Override
	public int compare(SpotifyData a1, SpotifyData a2){
		return (int)(a2.getTotalDuration().toMillis() - a1.getTotalDuration().toMillis());
	}
}