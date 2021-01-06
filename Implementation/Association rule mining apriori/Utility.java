




import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author SyedMahbub
 *
 */
public class Utility {
	private int limitOfTopness;
	private HashMap<Integer, Integer> mappingFromAttrValToItemIDDS;
	private ArrayList<MapperItemIDToValue> mappingFromAttrValToItemIDDSSerial;
	/*
	 * "UniversityRank, 0
	 * Program, 1
	 * Degree, 2
	 * Session, 3
	 * CGPA, 4
	 * Verbal, 5
	 * Quant, 6
	 * AWA, 7
	 * SubjectGRE, 8
	 * Origin, 9
	 * Decision 10"
	 * */
	final String[] uniRanks0 = {"University Ranking Tier 1","University Ranking Tier 2","University Ranking Tier 3","University Ranking Tier 4","University Ranking Tier 5","University Ranking Tier 6","University Ranking Tier 7","University Ranking Tier 8","University Ranking Tier 9","University Ranking Tier 10","University Ranking Tier 11","University Ranking Tier 12","University Ranking Tier 13","University Ranking Tier 14","University Ranking Tier 15","University Ranking Tier 16"};
	final String[] program1 = {"CS","ECE","HCI","IS","Other"};
	final String[] degree2 = {"MS","MEng","MBA","Other","PhD"};
	final String[] session3 = {"F09","F10","F11","F12","F13","F14","F15","F16","S10","S11","S12","S13","S14","S15","S16"};
	final String[] cgpa4 = {"0<cgpa<0.4","0.4<cgpa<0.8","0.8<cgpa<1.2","1.2<cgpa<1.6","1.6<cgpa<2.0","2.0<cgpa<2.4","2.4<cgpa<2.8","2.8<cgpa<3.2","3.2<cgpa<3.6","3.6<cgpa<4.0"};
	final String[] verbal5 = {"0%<verbal<10%","10%<verbal<20%","20%<verbal<30%","30%<verbal<40%","40%<verbal<50%","50%<verbal<60%","60%<verbal<70%","70%<verbal<80%","80%<verbal<90%","90%<verbal<100%"};
	final String[] quant6 = {"0%<quant<10%","10%<quant<20%","20%<quant<30%","30%<quant<40%","40%<quant<50%","50%<quant<60%","60%<quant<70%","70%<quant<80%","80%<quant<90%","90%<quant<100%"};
	final String[] awa7 = {"AWA 0.0","AWA 0.6","AWA 1.2","AWA 1.8","AWA 2.4","AWA 3.0","AWA 3.6","AWA 4.2","AWA 4.8","AWA 5.4","AWA 6.0"};
//	final String[] subgre8 = {};//200-900
	final String[] origin9 = {"American","International","Other","International with US Degree"};
	final String[] decision10 = {"Accepted","Rejected"};

	final String[] program1forprint = {"Program CS","Program ECE","Program HCI","ProgramIS","Program Other"};
	final String[] degree2forprint = {"Degree MS","Degree MEng","Degree MBA","Degree Other","Degree PhD"};
	final String[] session3forprint = {"Session Fall 2009","Session Fall 2010","Session Fall 2011","Session Fall 2012","Session Fall 2013","Session Fall 2014","Session Fall 2015","Session Fall 2016","Session Spring 2010","Session Spring 2011","Session Spring 2012","Session Spring 2013","Session Spring 2014","Session Spring 2015","Session Spring 2016"};
	final String[] origin9forprint = {"Origin American","Origin International","Origin Other","Origin International with US Degree"};
	final String[] decision10forprint = {"Decision Accepted","Decision Rejected"};
	
	/**
	 * @return the mappingFromAttrValToItemIDDSSerial
	 */
	public ArrayList<MapperItemIDToValue> getMappingFromAttrValToItemIDDSSerial() {
		return mappingFromAttrValToItemIDDSSerial;
	}
	/**
	 * @param mappingFromAttrValToItemIDDSSerial the mappingFromAttrValToItemIDDSSerial to set
	 */
	public void setMappingFromAttrValToItemIDDSSerial(
			ArrayList<MapperItemIDToValue> mappingFromAttrValToItemIDDSSerial) {
		this.mappingFromAttrValToItemIDDSSerial = mappingFromAttrValToItemIDDSSerial;
	}
	/**
	 * @return the limitOfTopness
	 */
	public int getLimitOfTopness() {
		return limitOfTopness;
	}
	/**
	 * @param limitOfTopness the limitOfTopness to set
	 */
	public void setLimitOfTopness(int limitOfTopness) {
		this.limitOfTopness = limitOfTopness;
	}

	public HashMap<Integer, Integer> getMappingFromAttrValToItemIDYeastDS() {
		return mappingFromAttrValToItemIDDS;
	}
	public void setMappingFromAttrValToItemIDYeastDS(
			HashMap<Integer, Integer> mappingFromAttrValToItemIDYeastDS) {
		this.mappingFromAttrValToItemIDDS = mappingFromAttrValToItemIDYeastDS;

	}
	Utility(){
		this.mappingFromAttrValToItemIDDS = new HashMap<Integer, Integer>();
		this.mappingFromAttrValToItemIDDSSerial = new ArrayList<MapperItemIDToValue>();
	}

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
	public ArrayList<ArrayList<Integer>> formatDataSet(String text, int empericalMissingValue){
		List<String> allRecords = Arrays.asList(text.split("\n"));
		ArrayList<ArrayList<Integer>> dataset = new ArrayList<ArrayList<Integer>>();
		for(String singleRecord:allRecords){
			List<String> featureValues =  Arrays.asList(singleRecord.split(","));
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(String eachValue:featureValues){
				if(eachValue.trim().equals("?")){
					eachValue = String.valueOf(empericalMissingValue);//missingValuePreditionEmperical
				}
				temp.add(Integer.parseInt(eachValue.trim()));
			}
			dataset.add(temp);	
		}
		return dataset;
	}
	public ArrayList<HashSet<Integer>> formatDataSetAsHashSet(String text){
		List<String> allRecords = Arrays.asList(text.split("\n"));
		ArrayList<HashSet<Integer>> dataset = new ArrayList<HashSet<Integer>>();
		for(String singleRecord:allRecords){
			List<String> featureValues =  Arrays.asList(singleRecord.split(","));
			HashSet<Integer> temp = new HashSet<Integer>();
			for(String eachValue:featureValues){
				temp.add(Integer.parseInt(eachValue.trim()));
			}
			dataset.add(temp);	
		}
		return dataset;
	}
	public ArrayList<HashSet<Integer>> formatGradDataSetAsHashSet(String text){
	
		List<String> allRecords = Arrays.asList(text.split("\n"));
		ArrayList<ArrayList<Integer>> datasetBeforeConversion = new ArrayList<ArrayList<Integer>>();
		for(String singleRecord:allRecords){
			if(!singleRecord.trim().isEmpty()){
				List<String> featureValues =  Arrays.asList(singleRecord.split(","));
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for(int i=0;i<featureValues.size();i++){
					String eachValue = featureValues.get(i).trim();
					switch (i) {
					case 0:
						int b=0;
						b += Integer.parseInt(eachValue.trim())+(i+1)*100;
						temp.add(b);
						break;
					case 1: //15
						b=0;
						if(eachValue.trim().equalsIgnoreCase(program1[0])){
							b+=1+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(program1[1])){
							b+=2+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(program1[2])){
							b+=3+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(program1[3])){
							b+=4+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(program1[4])){
							b+=5+(i+1)*100;
						}
						temp.add(b);
						break;
					case 2://5
						b=0;
						if(eachValue.trim().equalsIgnoreCase(degree2[0])){
							b+=1+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[1])){
							b+=2+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[2])){
							b+=3+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[3])){
							b+=4+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[4])){
							b+=5+(i+1)*100;
						}
						temp.add(b);
						break;
					case 3://15
						b=0;
						if(eachValue.trim().equalsIgnoreCase(session3[0])){
							b+=1+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[1])){
							b+=2+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[2])){
							b+=3+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[3])){
							b+=4+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[4])){
							b+=5+(i+1)*100;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[5])){
							b+=6+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[6])){
							b+=7+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[7])){
							b+=8+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[8])){
							b+=9+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[9])){
							b+=10+(i+1)*100;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[10])){
							b+=11+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[11])){
							b+=12+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[12])){
							b+=13+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[13])){
							b+=14+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(session3[14])){
							b+=15+(i+1)*100;
						}
						temp.add(b);
						break;
					case 4:
						Double a = Double.parseDouble(eachValue.trim());
						if     ( a>= 0.00 && a <= 0.10) a = 1.0;
						else if( a > 0.10 && a <= 0.20) a = 2.0;
						else if( a > 0.20 && a <= 0.30) a = 3.0;
						else if( a > 0.30 && a <= 0.40) a = 4.0;
						else if( a > 0.40 && a <= 0.50) a = 5.0;
						else if( a > 0.50 && a <= 0.60) a = 6.0;
						else if( a > 0.60 && a <= 0.70) a = 7.0;
						else if( a > 0.70 && a <= 0.80) a = 8.0;
						else if( a > 0.80 && a <= 0.90) a = 9.0;
						else if( a > 0.90 && a <= 1.00) a = 10.0;
						a +=(i+1)*100;
						temp.add(a.intValue());
						break;
					case 5:
						a = Double.parseDouble(eachValue.trim());
						if     ( a>= 0.00 && a <= 0.10) a = 1.0;
						else if( a > 0.10 && a <= 0.20) a = 2.0;
						else if( a > 0.20 && a <= 0.30) a = 3.0;
						else if( a > 0.30 && a <= 0.40) a = 4.0;
						else if( a > 0.40 && a <= 0.50) a = 5.0;
						else if( a > 0.50 && a <= 0.60) a = 6.0;
						else if( a > 0.60 && a <= 0.70) a = 7.0;
						else if( a > 0.70 && a <= 0.80) a = 8.0;
						else if( a > 0.80 && a <= 0.90) a = 9.0;
						else if( a > 0.90 && a <= 1.00) a = 10.0;
						a +=(i+1)*100;
						temp.add(a.intValue());
						break;
					case 6:
						a = Double.parseDouble(eachValue.trim());
						if     ( a>= 0.00 && a <= 0.10) a = 1.0;
						else if( a > 0.10 && a <= 0.20) a = 2.0;
						else if( a > 0.20 && a <= 0.30) a = 3.0;
						else if( a > 0.30 && a <= 0.40) a = 4.0;
						else if( a > 0.40 && a <= 0.50) a = 5.0;
						else if( a > 0.50 && a <= 0.60) a = 6.0;
						else if( a > 0.60 && a <= 0.70) a = 7.0;
						else if( a > 0.70 && a <= 0.80) a = 8.0;
						else if( a > 0.80 && a <= 0.90) a = 9.0;
						else if( a > 0.90 && a <= 1.00) a = 10.0;
						a +=(i+1)*100;
						temp.add(a.intValue());
						break;
					case 7:
						a = Double.parseDouble(eachValue.trim());
						if     ( a>= 0.00 && a <= 0.10) a = 1.0;
						else if( a > 0.10 && a <= 0.20) a = 2.0;
						else if( a > 0.20 && a <= 0.30) a = 3.0;
						else if( a > 0.30 && a <= 0.40) a = 4.0;
						else if( a > 0.40 && a <= 0.50) a = 5.0;
						else if( a > 0.50 && a <= 0.60) a = 6.0;
						else if( a > 0.60 && a <= 0.70) a = 7.0;
						else if( a > 0.70 && a <= 0.80) a = 8.0;
						else if( a > 0.80 && a <= 0.90) a = 9.0;
						else if( a > 0.90 && a <= 1.00) a = 10.0;
						a +=(i+1)*100;
						temp.add(a.intValue());
						break;
					case 8:
//						b=0;
//						if(eachValue.trim().equals("-1")) b=1;
//						else b = 2;
//						b +=(i+1)*100;
//						temp.add(b);
						break;
					case 9://4
						b=0;
						if(eachValue.trim().equalsIgnoreCase(origin9[0])){
							b+=1+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[1])){
							b+=2+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[2])){
							b+=3+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[3])){
							b+=4+(i+1)*100;
						}
						temp.add(b);
						break;
					case 10:
						b=0;
						if(eachValue.trim().equalsIgnoreCase(decision10[0])){
							b+=1+(i+1)*100;
						} else if(eachValue.trim().equalsIgnoreCase(decision10[1])){
							b+=2+(i+1)*100;
						}
						temp.add(b);
						break;
					}
				}
				datasetBeforeConversion.add(temp);	
			}

		}
//		System.out.println("\nDataset after encoding:\n"+datasetBeforeConversion);
		mappingFromAttrValToItemIDDS.clear();
		mappingFromAttrValToItemIDDSSerial.clear();
		int itemID=1;
		for(ArrayList<Integer> eachRow:datasetBeforeConversion){
			for(Integer eachAttr:eachRow){
				if(!mappingFromAttrValToItemIDDS.containsKey(eachAttr)){
					MapperItemIDToValue mapper = new MapperItemIDToValue(itemID, eachAttr.intValue());
					this.mappingFromAttrValToItemIDDSSerial.add(mapper);
					this.mappingFromAttrValToItemIDDS.put(eachAttr, itemID);
					itemID++;
				}
			}
		}
		//System.out.println(mappingFromAttrValToItemID+" \n"+itemID+"\n"+mappingFromAttrValToItemID.containsValue(3));
		ArrayList<HashSet<Integer>> datasetAfterConversion = new ArrayList<HashSet<Integer>>();
		for(ArrayList<Integer> eachRow:datasetBeforeConversion){
			HashSet<Integer> convertedRow = new HashSet<Integer>(); 
			for(Integer eachAttr:eachRow){
				convertedRow.add(mappingFromAttrValToItemIDDS.get(eachAttr));
			}
			datasetAfterConversion.add(convertedRow);
		}
//		System.out.println("Size: " + mappingFromAttrValToItemIDDSSerial.size());
		//		this.setMappingFromAttrValToItemIDDSSerial(mappingFromAttrValToItemIDDSSerial);
		return datasetAfterConversion;
	}

	public ArrayList<HashSet<Integer>> powerSet(HashSet<Integer> set) {
		int numberOfSubsets = (int) Math.pow(2, set.size());
		ArrayList<HashSet<Integer>> powerSets = new ArrayList<HashSet<Integer>>(numberOfSubsets);
		powerSets.add(new HashSet<Integer>(0)); 
		for (Integer eachItem : set) {
			int startingResultSize = powerSets.size();
			for (int i=0; i<startingResultSize; i++) {
				HashSet<Integer> oldSubset = powerSets.get(i);
				HashSet<Integer> newSubset = new HashSet<Integer>(oldSubset);
				newSubset.add(eachItem);
				powerSets.add(newSubset);
			}
		}
		powerSets.remove(0);
		powerSets.remove(set);
		return powerSets;
	}

	public void printRules(ArrayList<Rule> rules) {
		for(Rule rule:rules){
			HashSet<String> antecedent = new HashSet<String>();
			for(Integer eachElement:rule.getAntecedent()){
				antecedent.add(findAttributeValue(mappingFromAttrValToItemIDDSSerial.get(eachElement.intValue()-1).getCategoryID()));
			}
			HashSet<String> consequent = new HashSet<String>();
			for(Integer eachElement:rule.getConsequent()){
				consequent.add(findAttributeValue(mappingFromAttrValToItemIDDSSerial.get(eachElement.intValue()-1).getCategoryID()));
			}
			//			System.out.println(rule.getAntecedent()+"=>"+rule.getConsequent()+ ", " + rule.getConfidence()*100);
			System.out.println(antecedent+"=>"+consequent+ ", " + rule.getConfidence()*100);

		}
	}
	/*
	 * "UniversityRank, 0
	 * Program, 1
	 * Degree, 2
	 * Session, 3
	 * CGPA, 4
	 * Verbal, 5
	 * Quant, 6
	 * AWA, 7
	 * SubjectGRE, 8
	 * Origin, 9
	 * Decision 10"
	 * */
	private String findAttributeValue(int categoryID) {
		int attNo = (categoryID / 100) - 1;
		int categoryNo = (categoryID % 100 ) - 1;
		switch(attNo){
		case 0:
			return uniRanks0[categoryNo];
		case 1:
			return program1forprint[categoryNo];
		case 2:
			return degree2forprint[categoryNo];
		case 3:
			return session3forprint[categoryNo];
		case 4:
			return cgpa4[categoryNo];
		case 5:
			return verbal5[categoryNo];
		case 6:
			return quant6[categoryNo];
		case 7:
			return awa7[categoryNo];
		case 8:
			break;
		case 9:
			return origin9forprint[categoryNo];
		case 10:
			return decision10forprint[categoryNo];
		}
		return null;
	}
	public void printRulesLift(ArrayList<Rule> rules) {
		for(Rule rule:rules){
			HashSet<String> antecedent = new HashSet<String>();
			for(Integer eachElement:rule.getAntecedent()){
				antecedent.add(findAttributeValue(mappingFromAttrValToItemIDDSSerial.get(eachElement.intValue()-1).getCategoryID()));
			}
			HashSet<String> consequent = new HashSet<String>();
			for(Integer eachElement:rule.getConsequent()){
				consequent.add(findAttributeValue(mappingFromAttrValToItemIDDSSerial.get(eachElement.intValue()-1).getCategoryID()));
			}
			//			System.out.println(rule.getAntecedent()+"=>"+rule.getConsequent()+ ", " + rule.getConfidence()*100);
			System.out.println(antecedent+"=>"+consequent+ ", " + rule.getConfidence()*100);

		}
	}

	public ArrayList<Rule> identifyTopRules(ArrayList<Rule> rules, boolean isLift) {
		ArrayList<Rule> topRules = new ArrayList<Rule>();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(rules);
		if(isLift) limitOfTopness *= 2;
		int limit = rules.size() < limitOfTopness ? rules.size() : limitOfTopness;
		topRules.addAll(rules.subList(0, limit));	
		return topRules;
	}
	public ArrayList<Rule> identifyTop10OfLift2(ArrayList<Rule> rules) {
		ArrayList<Rule> topRules = new ArrayList<Rule>();
		Collections.sort(rules);
		int limit = rules.size() < limitOfTopness ? rules.size() : limitOfTopness;
		topRules.addAll(rules.subList(0, limit));
		return topRules;
	}
	public int findMaxItemNumber(ArrayList<HashSet<Integer>> dataset) {
		int max = 1;
		for(HashSet<Integer> singleRow:dataset){
			int tempMax = Collections.max(singleRow);
			if( max < tempMax) max = tempMax;
		}
		return max;
	}
	/*
	 * 	
	final String[] program1 = {"CS","ECE","HCI","IS","Other"};//5,4,3,2,1
	final String[] degree2 = {"MS","MEng","MBA","Other","PhD"};//4,3,2,1,5
	final String[] subgre8 = {};//200-900
	final String[] origin9 = {"American","International","Other","International with US Degree"};4,2,1,3
	final String[] decision10 = {"Accepted","Rejected"};2,1	
	 * */
	public ArrayList<ArrayList<Double>> formatGradDataSetAsHashSetForNNTwoClasses(String text){
		
		List<String> allRecords = Arrays.asList(text.split("\n"));
		ArrayList<ArrayList<Double>> datasetBeforeConversion = new ArrayList<ArrayList<Double>>();
		for(String singleRecord:allRecords){
			if(!singleRecord.trim().isEmpty()){
				List<String> featureValues =  Arrays.asList(singleRecord.split(","));
				ArrayList<Double> temp = new ArrayList<Double>();
				for(int i=0;i<featureValues.size();i++){
					String eachValue = featureValues.get(i).trim();
					double b=0.0;
					switch (i) {
					case 0:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 1: //5 final String[] program1 = {"CS","ECE","HCI","IS","Other"};//5,4,3,2,1
						b=0.0;
						if(eachValue.trim().equalsIgnoreCase(program1[0])){
							b+=5  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[1])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[2])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[3])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[4])){
							b+=1  ;
						}
						temp.add(b);
						break;
					case 2://5 degree2 = {"MS","MEng","MBA","Other","PhD"};//4,3,2,1,5
						b=0;
						if(eachValue.trim().equalsIgnoreCase(degree2[0])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[1])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[2])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[3])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[4])){
							b+=5  ;
						}
						temp.add(b);
						break;
					case 3://15
						b=0;
						if(eachValue.trim().equalsIgnoreCase(session3[0])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[1])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[2])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[3])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[4])){
							b+=5  ;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[5])){
							b+=6  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[6])){
							b+=7  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[7])){
							b+=8  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[8])){
							b+=9  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[9])){
							b+=10  ;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[10])){
							b+=11  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[11])){
							b+=12  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[12])){
							b+=13  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[13])){
							b+=14  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[14])){
							b+=15  ;
						}
						temp.add(b);
						break;
					case 4:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 5:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 6:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 7:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 8:
						b=0;
						if(eachValue.trim().equals("-1")) b=1;
						else b = 2;
						temp.add(b);
						break;
					case 9://4 origin9 = {"American","International","Other","International with US Degree"};4,2,1,3
						b=0;
						if(eachValue.trim().equalsIgnoreCase(origin9[0])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[1])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[2])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[3])){
							b+=3  ;
						}
						temp.add(b);
						break;
					case 10://decision10 = {"Accepted","Rejected"};1,0
						
						if(eachValue.trim().equalsIgnoreCase(decision10[0])){
							temp.add(1.0);
							temp.add(0.0);
						} else if(eachValue.trim().equalsIgnoreCase(decision10[1])){
							temp.add(0.0);
							temp.add(1.0);
						}
						
						break;
					}
				}
				datasetBeforeConversion.add(temp);	
			}

		}
		for(ArrayList<Double> eachRow:datasetBeforeConversion){
			for(Double eachValue:eachRow){
				System.out.printf(String.valueOf(eachValue)+",");
			}
			System.out.println();
		}
		return datasetBeforeConversion;
	}
public ArrayList<ArrayList<Double>> formatGradDataSetAsHashSetForNNUniTiersClasses(String text){
		
		List<String> allRecords = Arrays.asList(text.split("\n"));
		ArrayList<ArrayList<Double>> datasetBeforeConversion = new ArrayList<ArrayList<Double>>();
		for(String singleRecord:allRecords){
			if(!singleRecord.trim().isEmpty()){
				List<String> featureValues =  Arrays.asList(singleRecord.split(","));
				ArrayList<Double> temp = new ArrayList<Double>();
				for(int i=0;i<featureValues.size();i++){
					String eachValue = featureValues.get(i).trim();
					double b=0.0;
					switch (i) {
					case 0:
						Double z = Double.parseDouble(eachValue.trim());
						for(int j=1;j<=16;j++){
							if(z.intValue()==j){
								temp.add(1.0);
							} else temp.add(0.0);
							
						}
						
						break;
					case 1: //5 final String[] program1 = {"CS","ECE","HCI","IS","Other"};//5,4,3,2,1
						b=0.0;
						if(eachValue.trim().equalsIgnoreCase(program1[0])){
							b+=5  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[1])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[2])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[3])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(program1[4])){
							b+=1  ;
						}
						temp.add(b);
						break;
					case 2://5 degree2 = {"MS","MEng","MBA","Other","PhD"};//4,3,2,1,5
						b=0;
						if(eachValue.trim().equalsIgnoreCase(degree2[0])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[1])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[2])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[3])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(degree2[4])){
							b+=5  ;
						}
						temp.add(b);
						break;
					case 3://15
						b=0;
						if(eachValue.trim().equalsIgnoreCase(session3[0])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[1])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[2])){
							b+=3  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[3])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[4])){
							b+=5  ;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[5])){
							b+=6  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[6])){
							b+=7  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[7])){
							b+=8  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[8])){
							b+=9  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[9])){
							b+=10  ;
						} else 	if(eachValue.trim().equalsIgnoreCase(session3[10])){
							b+=11  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[11])){
							b+=12  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[12])){
							b+=13  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[13])){
							b+=14  ;
						} else if(eachValue.trim().equalsIgnoreCase(session3[14])){
							b+=15  ;
						}
						temp.add(b);
						break;
					case 4:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 5:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 6:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 7:
						temp.add(Double.parseDouble(eachValue.trim()));
						break;
					case 8:
						b=0;
						if(eachValue.trim().equals("-1")) b=1;
						else b = 2;
						temp.add(b);
						break;
					case 9://4 origin9 = {"American","International","Other","International with US Degree"};4,2,1,3
						b=0;
						if(eachValue.trim().equalsIgnoreCase(origin9[0])){
							b+=4  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[1])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[2])){
							b+=1  ;
						} else if(eachValue.trim().equalsIgnoreCase(origin9[3])){
							b+=3  ;
						}
						temp.add(b);
						break;
					case 10://decision10 = {"Accepted","Rejected"};2,1
						b=0;
						if(eachValue.trim().equalsIgnoreCase(decision10[0])){
							b+=2  ;
						} else if(eachValue.trim().equalsIgnoreCase(decision10[1])){
							b+=1  ;
						}
						
						temp.add(b);
						break;
					}
				}
				datasetBeforeConversion.add(temp);	
			}

		}
		for(ArrayList<Double> eachRow:datasetBeforeConversion){
			for(Double eachValue:eachRow){
				System.out.printf(String.valueOf(eachValue)+",");
			}
			System.out.println();
		}
		return datasetBeforeConversion;
	}

}
