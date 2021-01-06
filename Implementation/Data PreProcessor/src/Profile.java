import java.io.FileNotFoundException;
import java.io.IOException;

public class Profile
{
//	String univeristy;
	int universityRank;
	String program;
	String degree;
	String session;
	String decision;
	double cgpa;
	double verbal,quant;
	double writing;
	int subject_gre;
	String origin;
	public static Profile getProfile(String[] array, Utility ut, int defaultRank, int bucketSize) throws FileNotFoundException, IOException
	{
		Profile profile = new Profile();
		
		
		for(int i=0;i<array.length;i++)
		{
			switch(i)
			{
			case 0:
				profile.universityRank = ut.findRankOfGivenUni(array[i].trim(), defaultRank)/bucketSize + 1;
				break;
			case 1:
				profile.program = array[i];
				break;
			case 2:
				profile.degree = array[i];
				break;
			case 3:
				if(array[i].equals(""))
					return null;
				profile.session = array[i];
				break;
			case 4:
				if(array[i].equals("Accepted")||array[i].equals("Rejected"))
					profile.decision = array[i];
				else
					return null;
				break;
			case 5:
				if(array[i].equals(""))
					return null;
				profile.cgpa = Double.parseDouble(array[i]);
				if(profile.cgpa>4.0)
				{
					profile.cgpa = profile.cgpa/10.0;
				}
				else
				{
					profile.cgpa = profile.cgpa/4.0;
				}
				break;
			case 6:
				if(array[i].equals(""))
					return null;
				profile.verbal = Double.parseDouble(array[i]);
				break;
			case 7:
				if(array[i].equals(""))
					return null;
				profile.quant = Double.parseDouble(array[i]);
				break;
			case 8:
				if(array[i].equals(""))
					profile.writing = (3.0/6.0);
				else
					profile.writing = Double.parseDouble(array[i])/6.0;
				break;
			case 9:
				if(array[i].equals("TRUE"))
				{
					profile.verbal = (profile.verbal-130)/(170-130);
					profile.quant = (profile.quant-130)/(170-130);
				}
				else if(array[i].equals("FALSE"))
				{
					profile.verbal = (profile.verbal-200)/(800-200);
					profile.quant = (profile.quant-200)/(800-200);
				}
				break;
			case 10:
				if(array[i].equals(""))
					profile.subject_gre = -1;
				else
					profile.subject_gre = Integer.parseInt(array[i]);
				break;
			case 11:
				profile.origin = array[i];
				break;
			default:
				break;
			}
		}
		if(profile.origin==null)
			return null;
		return profile;
	}
	private Profile()
	{
		
	}
	@Override public String toString()
	{
		String str = this.universityRank+","+this.program+","+this.degree+","+this.session+","+
				this.cgpa+","+this.verbal+","+this.quant+","+this.writing+","+this.subject_gre+","+this.origin
				+","+this.decision;
		return str;
	}
}
