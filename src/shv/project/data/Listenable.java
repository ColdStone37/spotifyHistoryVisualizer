package shv.project.data;

/**
 * A interface that is inherited by all objects that can be directly listened to (songs and episodes).
 */
public interface Listenable {
	/**
	 * Gets the URI of the listenable
	 * @return URI String
	 */
	public String getURI();
}