/**
 * 
 */


import java.util.HashSet;

/**
 * @author SyedMahbub
 *
 */
public class Rule  implements Comparable<Rule>{
	private HashSet<Integer> antecedent;
	/**
	 * @return the antecedent
	 */
	public HashSet<Integer> getAntecedent() {
		return antecedent;
	}
	/**
	 * @param antecedent the antecedent to set
	 */
	public void setAntecedent(HashSet<Integer> antecedent) {
		this.antecedent = antecedent;
	}
	/**
	 * @return the consequent
	 */
	public HashSet<Integer> getConsequent() {
		return consequent;
	}
	/**
	 * @param consequent the consequent to set
	 */
	public void setConsequent(HashSet<Integer> consequent) {
		this.consequent = consequent;
	}
	/**
	 * @return the confidence
	 */
	public Double getConfidence() {
		return confidence;
	}
	/**
	 * @param confidence the confidence to set
	 */
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	private HashSet<Integer> consequent;
	private Double confidence;
	Rule(HashSet<Integer> antecedent, HashSet<Integer> consequent, Double confidence){
		this.setAntecedent(antecedent);
		this.setConsequent(consequent);
		this.setConfidence(confidence);
		
	}
	@Override
	public int compareTo(Rule o) {
		if(this.getConfidence() == o.getConfidence()) return 0;
		else if(this.getConfidence() > o.getConfidence()) return -1;
		else return 1;
	}

}
