import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * 
 */

/**
 * @author SyedMahbub
 *
 */
public class Caller {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Utility ut = new Utility();
		int defaultRank = 151;
		ut.setAllUniversityNameAttribute(defaultRank);
		System.out.println(ut.findRankOfGivenUni("Ohio University", defaultRank));

	}

}
