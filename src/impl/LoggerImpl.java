package impl;

import interfaces.Element;
import interfaces.ILogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import skynet.Logger;

public class LoggerImpl extends Logger{

	@Override
	protected ILogger make_log() {
		return new ILogger() {

			@Override
			public void logDeplacement(int numRobot, int x, int y) {
				try {
		            File lFile = new File(numRobot + ".log");
		            FileWriter lWriter = new FileWriter(lFile, true);
		            lWriter.write("Deplacement vers " + x + ", " + y + "\n");
		            lWriter.close();
		        } catch (IOException lEx) {
		            lEx.printStackTrace();
		        }
			}

			@Override
			public void logPriseBoite(int numRobot, Element boite) {
				try {
		            File lFile = new File(numRobot + ".log");
		            FileWriter lWriter = new FileWriter(lFile, true);
		            lWriter.write("Prise de la boite de couleur "  + boite.getCouleur().toString());
		            lWriter.close();
		        } catch (IOException lEx) {
		            lEx.printStackTrace();
		        }
			}

			@Override
			public void logPoseBoite(int numRobot, Element boite, Element nid) {
				try {
		            File lFile = new File(numRobot + ".log");
		            FileWriter lWriter = new FileWriter(lFile, true);
		            lWriter.write("Pose de la boite de couleur "  + boite.getCouleur().toString() + 
		            		" dans le nid " + nid.getCouleur().toString());
		            lWriter.close();
		        } catch (IOException lEx) {
		            lEx.printStackTrace();
		        }
			}
			
		};
	}

}
