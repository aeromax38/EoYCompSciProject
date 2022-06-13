import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class LeaderboardState implements Serializable {
  HashMap<String, Integer> leaderboard;
  private static final String fileName = "Leaderboard.ser";

  public String toString () {
    return leaderboard.toString();
  }

  public boolean save () {
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }
   public boolean delete() {
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  public static LeaderboardState restore () {
    try {
		  FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      LeaderboardState state = (LeaderboardState) ois.readObject();
	    ois.close();
	    fis.close();
      return state;
	  } catch(Exception e) {
	    return null;
	  }
  }
}