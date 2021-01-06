/**
 * 
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author SyedMahbub
 *
 */
public class AprioriAlgo {
	private ArrayList<HashSet<Integer>> transactionDataset;
	private double minimumSupportCount;
	private double minimumConfidence;
	private double liftThreshold;
	private HashMap<HashSet<Integer>, Double> frequentItemSetSupportCount;
	/**
	 * @return the frequentItemSetSupportCount
	 */
	public HashMap<HashSet<Integer>, Double> getFrequentItemSetSupportCount() {
		return frequentItemSetSupportCount;
	}

	private ArrayList<HashSet<Integer>> allFrequentItemSetsInSerial;
	private ArrayList<ArrayList<HashSet<Integer>>> levelWiseFrequentItemSets;
	private ArrayList<HashSet<Integer>> frequentItemSetF1;
	/**
	 * @return the liftThreshold
	 */
	public double getLiftThreshold() {
		return liftThreshold;
	}

	/**
	 * @param liftThreshold the liftThreshold to set
	 */
	public void setLiftThreshold(double liftThreshold) {
		this.liftThreshold = liftThreshold;
	}

	/**
	 * @return the minimumConfidence
	 */
	public double getMinimumConfidence() {
		return minimumConfidence;
	}

	/**
	 * @param minimumConfidence the minimumConfidence to set
	 */
	public void setMinimumConfidence(double minimumConfidence) {
		this.minimumConfidence = minimumConfidence;
	}



	public AprioriAlgo(){
		frequentItemSetSupportCount = new HashMap<HashSet<Integer>, Double>();
		allFrequentItemSetsInSerial = new ArrayList<HashSet<Integer>>();
		levelWiseFrequentItemSets = new ArrayList<ArrayList<HashSet<Integer>>>();
		frequentItemSetF1 = new ArrayList<HashSet<Integer>>();

	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashSet<Integer>> generateFrequentItemSet(boolean isFkMinus1WithF1, int numberOfItemsInDataset) {
		allFrequentItemSetsInSerial.clear();
		levelWiseFrequentItemSets.clear();
		frequentItemSetF1.clear();
		for(int i=0; i<numberOfItemsInDataset;i++){
			HashSet<Integer> itemSet = new HashSet<Integer>();
			itemSet.add(i+1);
			double supportCount = computeSupportCount(itemSet);
			if(supportCount >= minimumSupportCount){
				frequentItemSetF1.add(itemSet);
			}
		}
		allFrequentItemSetsInSerial.addAll(frequentItemSetF1);
		levelWiseFrequentItemSets.add(frequentItemSetF1);
		ArrayList<HashSet<Integer>> AllCandidateItemSets = new ArrayList<HashSet<Integer>>(frequentItemSetF1);		
		ArrayList<HashSet<Integer>> frequentItemSetFkMinus1 = new ArrayList<HashSet<Integer>>(frequentItemSetF1);
		ArrayList<HashSet<Integer>> frequentItemSetFk = new ArrayList<HashSet<Integer>>();
		for(;;){
			ArrayList<HashSet<Integer>> candidateItemSetCk = new ArrayList<HashSet<Integer>>();
			if(isFkMinus1WithF1){
				candidateItemSetCk.addAll(generateCandidateItemSetByFkMinus1VS1(frequentItemSetFkMinus1));
			} else {
				candidateItemSetCk.addAll(generateCandidateItemSetByFkMinus1VSFkMinus1(frequentItemSetFkMinus1));
			}
			//			System.out.println("CIS: " + candidateItemSetCk);
			for(HashSet<Integer> eachCandidateItemSetCki:candidateItemSetCk){
				double supportCount = computeSupportCount(eachCandidateItemSetCki);
				if(supportCount >= minimumSupportCount){
					frequentItemSetFk.add(eachCandidateItemSetCki);
				}
			}
			AllCandidateItemSets.addAll(candidateItemSetCk);
			if(frequentItemSetFk.size() == 0) {
				break;	
			}
			frequentItemSetFkMinus1.clear();
			levelWiseFrequentItemSets.add((ArrayList<HashSet<Integer>>) frequentItemSetFk.clone());
			frequentItemSetFkMinus1.addAll(frequentItemSetFk);
			allFrequentItemSetsInSerial.addAll(frequentItemSetFk);
			frequentItemSetFk.clear();
		}
		System.out.println("Number of generated candidate itemsets: " + AllCandidateItemSets.size()); /* + AllCandidateItemSets);*/
		//		System.out.println("Total number of frequent itemsets in serial: \n" + allFrequentItemSetsInSerial);
		return allFrequentItemSetsInSerial;
	}

	public ArrayList<HashSet<Integer>> generateMaximalFrequentItemsets(){
		ArrayList<HashSet<Integer>> maximalFrequentItemsets = new ArrayList<HashSet<Integer>>(); 
		int k=0;
		for(ArrayList<HashSet<Integer>> eachLevelFrequentItemSets:levelWiseFrequentItemSets){
			if(k+1 == levelWiseFrequentItemSets.size())  {
				maximalFrequentItemsets.addAll(eachLevelFrequentItemSets);
				break;
			}
			for(HashSet<Integer> eachItemSet:eachLevelFrequentItemSets){
				ArrayList<HashSet<Integer>> supersets = new ArrayList<HashSet<Integer>>();
				for(HashSet<Integer> eachItemSetFromF1:frequentItemSetF1){
					HashSet<Integer> tempJoinedItemSet = new HashSet<Integer>(eachItemSet);
					tempJoinedItemSet.addAll(eachItemSetFromF1);
					if(tempJoinedItemSet.size() == eachItemSet.size() + eachItemSetFromF1.size()){
						supersets.add(tempJoinedItemSet);
					}
				}
				//				System.out.println("IS:" + eachItemSet +" SS:" + supersets + " NL: " + levelWiseFrequentItemSets.get(k+1));
				boolean flagMaximal = true;
				for(HashSet<Integer> eachISOfSuperSet:supersets){
					for(HashSet<Integer> eachItemSetOfFrequentISOfNextLevel:levelWiseFrequentItemSets.get(k+1)){
						if(eachItemSetOfFrequentISOfNextLevel.equals(eachISOfSuperSet)) {
							flagMaximal = false;
							break;
						}
					}
					if(!flagMaximal) break;
				}
				if(flagMaximal) maximalFrequentItemsets.add(eachItemSet);
			}
			k++;
		}
		return maximalFrequentItemsets;
	}
	public ArrayList<HashSet<Integer>> generateClosedFrequentItemsets() {

		ArrayList<HashSet<Integer>> closedFrequentItemsets = new ArrayList<HashSet<Integer>>(); 
		for(ArrayList<HashSet<Integer>> eachLevelFrequentItemSets:levelWiseFrequentItemSets){
			for(HashSet<Integer> eachItemSet:eachLevelFrequentItemSets){
				boolean flagClosed = true;
				for(HashSet<Integer> eachItemSetFromF1:frequentItemSetF1){
					HashSet<Integer> tempJoinedItemSet = new HashSet<Integer>(eachItemSet);
					tempJoinedItemSet.addAll(eachItemSetFromF1);
					if(tempJoinedItemSet.size() == eachItemSet.size() + eachItemSetFromF1.size()){
						if(computeSupportCount(eachItemSet) == computeSupportCount(tempJoinedItemSet)){
							flagClosed = false;
							break;
						}
					}
				}
				if(flagClosed) {
					closedFrequentItemsets.add(eachItemSet);
				}
				//				System.out.println("IS:" + eachItemSet +" SS:" + supersets + " NL: " + levelWiseFrequentItemSets.get(k+1));

			}
		}
		return closedFrequentItemsets;
	}
	private ArrayList<HashSet<Integer>> generateCandidateItemSetByFkMinus1VS1(
			ArrayList<HashSet<Integer>> frequentItemSetFkMinus1) {
		ArrayList<HashSet<Integer>> candidateItemSetCk = new ArrayList<HashSet<Integer>>();
		for(HashSet<Integer> eachItemSetFromFKMinus1:frequentItemSetFkMinus1){
			for(HashSet<Integer> eachItemSetFromF1:frequentItemSetF1){
				HashSet<Integer> tempJoinedItemSet = new HashSet<Integer>(eachItemSetFromFKMinus1);
				tempJoinedItemSet.addAll(eachItemSetFromF1);
				if(tempJoinedItemSet.size() == eachItemSetFromFKMinus1.size() + eachItemSetFromF1.size()){
					if(ifSupportAprioriProperty(tempJoinedItemSet,frequentItemSetFkMinus1)){//pruning
						if(!candidateItemSetCk.contains(tempJoinedItemSet)){
							candidateItemSetCk.add(tempJoinedItemSet);							
						}
					}
				}
			}
		}
		return candidateItemSetCk;
	}
	private ArrayList<HashSet<Integer>> generateCandidateItemSetByFkMinus1VSFkMinus1(
			ArrayList<HashSet<Integer>> frequentItemSetFkMinus1) {
		ArrayList<HashSet<Integer>> candidateItemSetCk = new ArrayList<HashSet<Integer>>();
		for(HashSet<Integer> eachItemSetFromFKMinus1:frequentItemSetFkMinus1){
			for(HashSet<Integer> eachItemSetFromFkMinus12:frequentItemSetFkMinus1){
				if(checkFirstkMinus2Identical(eachItemSetFromFKMinus1,eachItemSetFromFkMinus12)){
					HashSet<Integer> tempJoinedItemSet = new HashSet<Integer>(eachItemSetFromFKMinus1);
					tempJoinedItemSet.add(findLastElement(eachItemSetFromFkMinus12));
					if(tempJoinedItemSet.size() == eachItemSetFromFKMinus1.size() + 1){
						if(ifSupportAprioriProperty(tempJoinedItemSet,frequentItemSetFkMinus1)){//pruning
							if(!candidateItemSetCk.contains(tempJoinedItemSet)){
								candidateItemSetCk.add(tempJoinedItemSet);							
							}
						}
					}					
				}
			}
		}
		return candidateItemSetCk;
	}

	private Integer findLastElement(HashSet<Integer> eachItemSetFromFkMinus12) {
		int size = eachItemSetFromFkMinus12.size();
		int l=1;
		for(Integer element:eachItemSetFromFkMinus12){
			if(l==size){
				return element;
			}
			l++;
		}
		return null;
	}

	private boolean checkFirstkMinus2Identical(
			HashSet<Integer> eachItemSetFromFKMinus1,
			HashSet<Integer> eachItemSetFromFkMinus12) {
		int kMinus1 = eachItemSetFromFKMinus1.size();
		if(kMinus1==1) return true;
		int l=1, m=1;
		for(Integer element1:eachItemSetFromFKMinus1){
			for(Integer element2:eachItemSetFromFkMinus12){
				if((l==kMinus1 && m==kMinus1) && element1.equals(element2)) return false;
				if((l==m) && (l!=kMinus1) && !element1.equals(element2)) return false;
				m++;
			}
			l++;
		}
		return true;
	}

	private double computeSupportCount(
			HashSet<Integer> itemSet) {
		if(frequentItemSetSupportCount.containsKey(itemSet)) { 
			//System.out.println(frequentItemSetSupportCount.get(itemSet));
			return frequentItemSetSupportCount.get(itemSet);
		} else {
			double supportCount = 0;
			for(HashSet<Integer> eachTransaction:transactionDataset){
				if(eachTransaction.containsAll(itemSet))
					supportCount++;
			}
			frequentItemSetSupportCount.put(itemSet, supportCount);
			return supportCount;
		}

	}



	private boolean ifSupportAprioriProperty(
			HashSet<Integer> tempJoinedItemSet,
			ArrayList<HashSet<Integer>> frequentItemSetFkMinus1) {
		boolean doesSupportAprioriProperty = true;
		for(Integer element:tempJoinedItemSet){
			HashSet<Integer> tempSubSet = new HashSet<Integer>(tempJoinedItemSet);
			tempSubSet.remove(element);
			if(tempSubSet.size() == frequentItemSetFkMinus1.size()){
				for(HashSet<Integer> eachFrequentItemSet:frequentItemSetFkMinus1){
					if(eachFrequentItemSet.containsAll(tempSubSet)){
						break;
					} else {
						doesSupportAprioriProperty = false;
					}
				}
			}
		}
		return doesSupportAprioriProperty;
	}



	public HashMap<HashSet<Integer>,HashSet<Integer>> generateAssociationRules(){
		HashMap<HashSet<Integer>,HashSet<Integer>> allRules = new HashMap<HashSet<Integer>,HashSet<Integer>>();
		int k=1;
		for(ArrayList<HashSet<Integer>> eachLevelFreqISFk:levelWiseFrequentItemSets){
			if(k==1){

			} else {
				for(HashSet<Integer> eachFreqISfk:eachLevelFreqISFk){
					ArrayList<HashSet<Integer>> oneItemConsequentH1 = generateOneItemConsequent(eachFreqISfk);
					apGenRules(eachFreqISfk, oneItemConsequentH1);
				}
			}
			k++;
		}
		return allRules;
	}
	@SuppressWarnings("unchecked")
	private void apGenRules(HashSet<Integer> eachFreqISfk,
			ArrayList<HashSet<Integer>> ruleConsequentHm) {
		System.out.println(eachFreqISfk +" * "+ ruleConsequentHm);
		ArrayList<HashSet<Integer>> ruleConsequentHmPlus1 = new ArrayList<HashSet<Integer>>();
		int k = eachFreqISfk.size();
		int m = ruleConsequentHm.get(0).size();
		if(k > m+1){
			ruleConsequentHmPlus1 = aprioriGen(ruleConsequentHm);
			for(HashSet<Integer> hmPlus1:ruleConsequentHmPlus1){
				HashSet<Integer> fkMinushm1 = (HashSet<Integer>) eachFreqISfk.clone();
				fkMinushm1.removeAll(hmPlus1);
				double currentConfidence = (double) computeSupportCount(eachFreqISfk) / computeSupportCount(fkMinushm1);
				if(currentConfidence >= minimumConfidence){
					System.out.println(fkMinushm1 + " => " +hmPlus1 + currentConfidence);
				} else {
					//					ruleConsequentHmPlus1.remove(hmPlus1);
				}
			}
			apGenRules(eachFreqISfk, ruleConsequentHmPlus1);
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Rule> generateAssociationRulesFinalByMinConf(){
		ArrayList<Rule> allRules = new ArrayList<Rule>();
		int k=1;
		for(ArrayList<HashSet<Integer>> eachLevelFreqISFk:levelWiseFrequentItemSets){
			if(k==1){

			} else {
				for(HashSet<Integer> eachFreqISfk:eachLevelFreqISFk){
					ArrayList<HashSet<Integer>> powerSetsOfeachFreqISfk = generatePowerSet(eachFreqISfk);
					//					System.out.println(powerSetsOfeachFreqISfk);
					for(HashSet<Integer> eachSubsetS:powerSetsOfeachFreqISfk){
						HashSet<Integer> itemIMinusSubsets = (HashSet<Integer>) eachFreqISfk.clone();
						itemIMinusSubsets.removeAll(eachSubsetS);
						double currentConfidence = (double) computeSupportCount(eachFreqISfk) / computeSupportCount(eachSubsetS);
						if(currentConfidence >= minimumConfidence){
//							System.out.println(eachFreqISfk+": "+eachSubsetS + " => " +itemIMinusSubsets +"; "+ currentConfidence);
							allRules.add(new Rule(eachSubsetS,itemIMinusSubsets,currentConfidence));
						}
					}
				}
			}
			k++;
		}
		return allRules;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Rule> generateAssociationRulesFinalByLift(){
		ArrayList<Rule> allRules = new ArrayList<Rule>();
		int k=1;
		for(ArrayList<HashSet<Integer>> eachLevelFreqISFk:levelWiseFrequentItemSets){
			if(k!=1) {
				for(HashSet<Integer> eachFreqISfk:eachLevelFreqISFk){
					ArrayList<HashSet<Integer>> powerSetsOfeachFreqISfk = generatePowerSet(eachFreqISfk);
//					System.out.println(powerSetsOfeachFreqISfk);
					for(HashSet<Integer> eachSubsetS:powerSetsOfeachFreqISfk){
						HashSet<Integer> itemIMinusSubsets = (HashSet<Integer>) eachFreqISfk.clone();
						itemIMinusSubsets.removeAll(eachSubsetS);
						double currentConfidence = (double) computeSupportCount(eachFreqISfk) / computeSupportCount(eachSubsetS);
						double currentLift = (double) currentConfidence /  computeSupport(itemIMinusSubsets );
						if(currentLift >= liftThreshold){
//							System.out.println("ItemSet: "+eachFreqISfk+": eachSubsetS"+eachSubsetS + " => itemIMinusSubsets" +itemIMinusSubsets +"; "+"computeSupport(itemIMinusSubsets ):"+computeSupport(itemIMinusSubsets ) + ";currentConfidence"+ currentConfidence + ";currentLift" + currentLift);
							allRules.add(new Rule(eachSubsetS,itemIMinusSubsets,currentLift));
						}
					}
				}
			}
			k++;
		}
		return allRules;
	}

	private double computeSupport(HashSet<Integer> itemIMinusSubsets) {
		double support = (double) computeSupportCount(itemIMinusSubsets) / transactionDataset.size();
		return support;
	}

	private ArrayList<HashSet<Integer>> generatePowerSet(
			HashSet<Integer> eachFreqISfk) {
		return new Utility().powerSet(eachFreqISfk);
	}

	private ArrayList<HashSet<Integer>> aprioriGen(
			ArrayList<HashSet<Integer>> ruleConsequentHm) {

		ArrayList<HashSet<Integer>> ruleConsequentHmPlus1 = new ArrayList<HashSet<Integer>>();
		for(HashSet<Integer> eachSetFromHm:ruleConsequentHm){
			for(HashSet<Integer> eachSetFromHm2:ruleConsequentHm){
				HashSet<Integer> tempJoinedItemSet = new HashSet<Integer>(eachSetFromHm);
				tempJoinedItemSet.addAll(eachSetFromHm2);
				if(tempJoinedItemSet.size() == eachSetFromHm.size() + eachSetFromHm2.size()){
					if(!ruleConsequentHmPlus1.contains(tempJoinedItemSet)){
						ruleConsequentHmPlus1.add(tempJoinedItemSet);							
					}
				}					

			}
		}
		return ruleConsequentHmPlus1;
	}

	private ArrayList<HashSet<Integer>> generateOneItemConsequent(HashSet<Integer> eachFreqISFk) {
		ArrayList<HashSet<Integer>> oneItemConsequentH1 = new ArrayList<HashSet<Integer>>(); 
		for(Integer eachItem:eachFreqISFk){
			HashSet<Integer> eis = new HashSet<Integer>();
			eis.add(eachItem);
			oneItemConsequentH1.add(eis);
		}
		return oneItemConsequentH1;
	}

	/**
	 * @return the minimumSupport
	 */
	public double getMinimumSupport() {
		return minimumSupportCount;
	}

	/**
	 * @param minimumSupport the minimumSupport to set
	 */
	public void setMinimumSupport(double minimumSupport) {
		this.minimumSupportCount = minimumSupport;
	}

	/**
	 * @return the transactionDataset
	 */
	public ArrayList<HashSet<Integer>> getTransactionDataset() {
		return transactionDataset;
	}

	/**
	 * @param transactionDataset the transactionDataset to set
	 */
	public void setTransactionDataset(ArrayList<HashSet<Integer>> transactionDataset) {
		this.transactionDataset = transactionDataset;
	}
}

