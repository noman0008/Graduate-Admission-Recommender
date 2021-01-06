import java.util.HashSet;

/**
 * 
 */

/**
 * @author SyedMahbub
 *
 */
public class UniversityNameAttribute {
	private String usnewsName;
	private HashSet<String> allPossibleNames;
	private int usnewsRank;
	
	public UniversityNameAttribute(String usnewsName, HashSet<String> allPossibleNames, int usnewsRank) {
		this.usnewsName = usnewsName;
		this.allPossibleNames = allPossibleNames;
		this.usnewsRank = usnewsRank;
	}
	public UniversityNameAttribute(HashSet<String> allPossibleNames, int usnewsRank) {
		for(String s:allPossibleNames){
			this.usnewsName = s.trim();
			break;
		}
		this.allPossibleNames = allPossibleNames;
		this.usnewsRank = usnewsRank;
	}
	/**
	 * @return the usnewsName
	 */
	public String getUsnewsName() {
		return usnewsName;
	}
	/**
	 * @param usnewsName the usnewsName to set
	 */
	public void setUsnewsName(String usnewsName) {
		this.usnewsName = usnewsName;
	}
	/**
	 * @return the allPossibleNames
	 */
	public HashSet<String> getAllPossibleNames() {
		return allPossibleNames;
	}
	/**
	 * @param allPossibleNames the allPossibleNames to set
	 */
	public void setAllPossibleNames(HashSet<String> allPossibleNames) {
		this.allPossibleNames = allPossibleNames;
	}
	/**
	 * @return the usnewsRank
	 */
	public int getUsnewsRank() {
		return usnewsRank;
	}
	/**
	 * @param usnewsRank the usnewsRank to set
	 */
	public void setUsnewsRank(int usnewsRank) {
		this.usnewsRank = usnewsRank;
	}

}
