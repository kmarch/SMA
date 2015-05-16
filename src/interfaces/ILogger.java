package interfaces;

public interface ILogger {

	public void logDeplacement(int numRobot, int x, int y);
	public void logPriseBoite(int numRobot, Element boite);
	public void logPoseBoite(int numRobot, Element boite, Element nid);
}
