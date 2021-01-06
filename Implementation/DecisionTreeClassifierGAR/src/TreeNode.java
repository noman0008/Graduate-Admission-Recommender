import java.io.BufferedWriter;
import java.io.IOException;

public class TreeNode 
{
	private TreeNode left,right;
    private int attributeId;
    private int label;
    private double threshold;
    private String value;
    TreeNode()
    {
        this.label = -1;
        this.threshold = -1;
        attributeId = -1;
        left = right = null;
        value = null;
    }
    public boolean isLeaf()
    {
        return (this.left==null)&&(this.right==null);
    }
    public TreeNode getLeft()
    {
        return this.left;
    }
    public TreeNode getRight()
    {
        return this.right;
    }
    public void setLeft(TreeNode node)
    {
    	this.left = node;
    }
    public void setRight(TreeNode node)
    {
    	this.right = node;
    }
    public void setLabel(int label)
    {
        this.label = label;
    }
    public int getLabel()
    {
    	return this.label;
    }
    public void setAttributeID(int id)
    {
        this.attributeId = id;
    }
    public int getAttributeID()
    {
        return this.attributeId;
    }
    public void setThreshold(double threshold) throws Exception
    {
    	if(this.attributeId == -1)
    		throw new Exception();
    	this.threshold = threshold;
    }
    public double getThreshold() throws Exception
    {
    	if(this.threshold == -1)
    		throw new Exception();
    	return this.threshold;
    }
    public void setValue(String str) throws Exception
    {
    	if(this.attributeId == -1)
    		throw new Exception();
    	this.value = str;
    }
    public String getValue() throws Exception
    {
    	if(this.value.equals(""))
    		throw new Exception();
    	return this.value;
    }
    public void printTree(boolean isRight, String indent,BufferedWriter bw) throws IOException
	{
		if (this.right != null) 
		{
			this.right.printTree(true, indent + (isRight ? "        " : " |      "),bw);
		}
		System.out.printf(indent);
		bw.write(indent);
		if (isRight) 
		{
			System.out.printf(" /");
			bw.write(" /");
		} 
		else 
		{
			bw.write(" \\");
			System.out.printf(" \\");
		}
		System.out.printf("----- ");
		bw.write("----- ");
		printNodeValue(bw);
		if (this.left != null)
		{
			this.left.printTree(false, indent + (isRight ? " |      " : "        "),bw);
		}
	}
	private void printNodeValue(BufferedWriter bw) throws IOException {
		if (this.label != -1) 
		{
			if(this.label == 1)
			{
				bw.write("Accepted");
				System.out.printf("Accepted");
			}
			else
			{
				bw.write("Rejected");
				System.out.printf("Rejected");
			}
		}
		else
		{
			if(DecisionTreeClassifier.isCategorical(attributeId)==true)
			{
				bw.write("A"+ this.attributeId + " == " + this.value);
				System.out.print("A"+ this.attributeId + " == " + this.value);
			}
			else
			{
				bw.write("A"+ this.attributeId+ " >= "+this.threshold);
				System.out.print("A"+ this.attributeId+ " >= "+this.threshold);
			}
		}
		bw.newLine();
		System.out.printf("\n");
	}
}