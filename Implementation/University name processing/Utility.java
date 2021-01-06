import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author SyedMahbub
 *
 */
public class Utility {
	ArrayList<UniversityNameAttribute> allUniversityNameAttribute;

	public String readFile(String path) throws FileNotFoundException,
	IOException {
		String texts = null;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			texts = sb.toString();
		}
		return texts;
	}

	public HashSet<String> extractUniNameFromDataSet(String text){
		List<String> allRecords = Arrays.asList(text.split("\n"));
		System.out.println("Records: "+allRecords.size());
		HashSet<String> uniNameAttribute = new HashSet<String>();
		int k=0;
		for(String singleRecord:allRecords){
			List<String> featureValues =  Arrays.asList(singleRecord.split(","));
			if(featureValues.size()>3){
				String temp = featureValues.get(1).trim();
				if(temp.length()<80 && temp.length()>1){
					//					String s = removeSymbols(temp);
					uniNameAttribute.add(temp.replaceAll("\"", ""));
					k++;}
			} 
			//			else System.out.println("Empty: "+singleRecord);
		}
		System.out.println("Has uni names: "+k);
		return uniNameAttribute;
	}

	public void printAHashSet(HashSet<String> extractUniNameFromDataSet) {
		for(String s:extractUniNameFromDataSet){
			System.out.println(s);
		}

	}
	public ArrayList<HashSet<String>> createArrayListOfUniqueUniNamesInGroups(
			String uniqueUnisInGroups) {
		List<String> allUniNames = Arrays.asList(uniqueUnisInGroups.split("\n"));
		ArrayList<HashSet<String>> uniqueUniNames = new ArrayList<HashSet<String>>();
		for(String singleRecord:allUniNames){
			List<String> multipleNames =  Arrays.asList(singleRecord.split(","));
			HashSet<String> uniqueUniMultiNames = new HashSet<String>();
			for(String eachUniName:multipleNames){
				uniqueUniMultiNames.add(eachUniName.trim());
			}
			uniqueUniNames.add(uniqueUniMultiNames);
		}
		return uniqueUniNames;
	}
	public ArrayList<UniversityNameAttribute> findAllPossibleUniRanks(
			ArrayList<HashSet<String>> unisInGroups, String usnewsRanks, int defaultRank) {
		ArrayList<UniversityNameAttribute> allUniNameAttributeValues1 = new ArrayList<UniversityNameAttribute>(); 
		List<String> allUniNameRank = Arrays.asList(usnewsRanks.split("\n"));
		for(HashSet<String> eachRow:unisInGroups){
			boolean found = false;

			for(String singleUni:eachRow){
				int rank = 1;
				for(String singleUniRank:allUniNameRank){
					if(singleUniRank.trim().equalsIgnoreCase(singleUni.trim())){
						found = true;
						UniversityNameAttribute nodeUniName = new UniversityNameAttribute(singleUniRank.trim(), eachRow, rank);
						allUniNameAttributeValues1.add(nodeUniName);
						break;
					}
					rank++;	
				}
				if(found) break;
			}
			if(!found) {
				UniversityNameAttribute nodeUniName = new UniversityNameAttribute(eachRow, defaultRank);
				allUniNameAttributeValues1.add(nodeUniName);
			}
		}
		return allUniNameAttributeValues1;
	}
	public void printUniRanks(ArrayList<UniversityNameAttribute> temp) {
		for(UniversityNameAttribute each:temp){
			//			if(each.getUsnewsRank() < 151)
			System.out.println(each.getUsnewsName()+"->"+each.getUsnewsRank());//+"==="+each.getAllPossibleNames());
		}
	}
	public ArrayList<UniversityNameAttribute> generateUniqueUniNameAttributeValues(int defaultRank) throws FileNotFoundException, IOException{
		String uniqueUnisInGroups = readFile("Unique_uni_names_in_groups_usnews_name.txt");
		ArrayList<HashSet<String>> unisInGroups = createArrayListOfUniqueUniNamesInGroups(uniqueUnisInGroups);
		String usnewsRanks = readFile("first_150_unis_by_usnews_rank.txt");		
		return findAllPossibleUniRanks(unisInGroups,usnewsRanks, defaultRank); 
		
	}
	public int findRankOfGivenUni(String uniname, int defaultRank) throws FileNotFoundException, IOException{
		
		for(UniversityNameAttribute eachRow:getAllUniversityNameAttribute()){
			if(eachRow.getAllPossibleNames().contains(uniname.trim())) {
				return eachRow.getUsnewsRank();
			}
		}
		return defaultRank;
	}

	/**
	 * @return the allUniversityNameAttribute
	 */
	public ArrayList<UniversityNameAttribute> getAllUniversityNameAttribute() {
		return allUniversityNameAttribute;
	}

	/**
	 * @param allUniversityNameAttribute the allUniversityNameAttribute to set
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void setAllUniversityNameAttribute(int defaultRank) throws FileNotFoundException, IOException {
		this.allUniversityNameAttribute = generateUniqueUniNameAttributeValues(defaultRank);
//		printUniRanks(allUniversityNameAttribute);
	}
}
