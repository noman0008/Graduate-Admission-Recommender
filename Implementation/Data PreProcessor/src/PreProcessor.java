import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
/*
 * Intended University
 * Department
 * Program
 * Session
 * Decision
 * CGPA
 * Verbal
 * Quant
 * Analytical Writing
 * Is_new_gre
 * Subject GRE
 * Origin
 */
import java.util.ArrayList;



public class PreProcessor
{
	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		String fileName = "cs_clean.csv";
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		Profile profile;
		ArrayList<Profile> dataset = new ArrayList<Profile>();
//		int k=0;
		Utility ut = new Utility();
		int defaultRank = 151;
		int bucketSize = 10;
		ut.setAllUniversityNameAttribute(defaultRank);
		
		while(true)
		{
			line = br.readLine();
			if(line == null)
				break;
			String[] parts = line.split(",");
			profile = Profile.getProfile(parts,ut,defaultRank,bucketSize);
			if(profile!=null)
				dataset.add(profile);
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("dataset.csv")));
		bw.write("UniversityRank,Program,Degree,Session,CGPA,Verbal,Quant,AWA,SubjectGRE,Origin,Decision");
		bw.newLine();
		for(int i=0;i<dataset.size();i++)
		{
			bw.write(dataset.get(i).toString());
			bw.newLine();
		}
		bw.close();
		System.out.println(dataset.size());
	}
}
