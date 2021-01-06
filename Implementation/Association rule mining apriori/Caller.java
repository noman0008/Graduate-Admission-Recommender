/**
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * @author SyedMahbub
 *
 */
public class Caller {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Utility ut = new Utility();
		String textGradData=ut.readFile("graddataset/dataset.csv");
		try {
			System.setOut(new PrintStream(new File("output-file-graddataset.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		System.out.println(textGradData);
		ArrayList<HashSet<Integer>> datasetGrad = ut.formatGradDataSetAsHashSet(textGradData);
		//		System.out.println("Dataset after mapping: \n"+datasetGrad);
		double minimumSupport = 0.20;
		double minimumConfidence = 0.30;
		double liftThreshold = 1.0;
		double gapMS = 0.10;
		double gapMC = 0.10;
		runApriori(datasetGrad, minimumSupport, minimumConfidence,liftThreshold,gapMS,gapMC,ut);
		System.out.println("Mapping from Attribute Value to ItemID: \n"+ut.getMappingFromAttrValToItemIDYeastDS());
//		ArrayList<ArrayList<Double>> datasetGrad = ut.formatGradDataSetAsHashSetForNNUniTiersClasses(textGradData);

	}
	private static void runApriori(ArrayList<HashSet<Integer>> dataset, double minimumSupport, double minimumConfidence, double liftThreshold, double gapMS, double gapMC, Utility ut){
		int level = 5;
		int numberOfTransactions = dataset.size();
		System.out.println("Number of transactions: "+numberOfTransactions);
		int numberOfItemsInDataset = ut.findMaxItemNumber(dataset);
		System.out.println("ItemID ranges from 1 to "+numberOfItemsInDataset);
		AprioriAlgo apalgo = new AprioriAlgo();
		double tempConf = minimumConfidence;

		for(int i=0;i<level;i++){
			minimumConfidence = tempConf;
			for(int j=0;j<level;j++){
				System.out.println("\n******** MinSupport  " + minimumSupport*100 + "%, MinConf "+minimumConfidence*100+"% ***********");
				double minimumSupportCount = Math.ceil(minimumSupport*numberOfTransactions);
				apalgo.setTransactionDataset(dataset);
				apalgo.setMinimumSupport(minimumSupportCount);
				apalgo.setMinimumConfidence(minimumConfidence);
				apalgo.setLiftThreshold(liftThreshold);
				boolean isFkMinus1WithF1 = true;
				System.out.println("Fk-1 x F1:");
				ArrayList<HashSet<Integer>> fisfk1vs1 = apalgo.generateFrequentItemSet(isFkMinus1WithF1,numberOfItemsInDataset);
				System.out.println("Frequent itemsets: " + fisfk1vs1.size()); /*+ fisfk1vs1);*/
				System.out.println("\nFk-1 x Fk-1:");
				ArrayList<HashSet<Integer>> fisfk1vsfk1 = apalgo.generateFrequentItemSet(!isFkMinus1WithF1,numberOfItemsInDataset);
				System.out.println("Frequent itemsets: " + fisfk1vsfk1.size()); /*+ fisfk1vsfk1);*/
				ArrayList<HashSet<Integer>> cfis = apalgo.generateClosedFrequentItemsets();
				System.out.println("\nClosed Frequent Itemsets: " + cfis.size()); /* + cfis);*/
				ArrayList<HashSet<Integer>> mfis = apalgo.generateMaximalFrequentItemsets();
				System.out.println("Maximal Frequent Itemsets: " + mfis.size()); /*+ mfis);*/

				ArrayList<Rule> rules = apalgo.generateAssociationRulesFinalByMinConf();
				System.out.println("\nNumber of Association Rules By Confidence ("+minimumConfidence*100+"%): " + rules.size());
				//				ut.printRules(rules);
				int limitOfTopness = 30;
				ut.setLimitOfTopness(limitOfTopness );
				ArrayList<Rule> topRules = ut.identifyTopRules(rules,false);
				System.out.println("\nTop "+ topRules.size() + " association rules by Confidence:" );
				ut.printRules(topRules);

				ArrayList<Rule> rulesByLift = apalgo.generateAssociationRulesFinalByLift();
				System.out.println("\nNumber of Association Rules By lift ("+liftThreshold+") : " + rulesByLift.size());
				//				ut.printRulesLift(rulesByLift);
				ArrayList<Rule> topRulesLift = ut.identifyTopRules(rulesByLift,true);
				System.out.println("\nTop "+ topRulesLift.size() + " association rules By Lift:" );
				ut.printRulesLift(topRulesLift);
				//				System.out.println(apa.getFrequentItemSetSupportCount());
				minimumConfidence += gapMC;
			}
			minimumSupport += gapMS;
			System.out.println("\n**********************************************************************************************");
			System.out.println("**********************************************************************************************");
		}
	}

}
