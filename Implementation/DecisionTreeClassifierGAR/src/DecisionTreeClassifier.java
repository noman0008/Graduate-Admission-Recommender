import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class DecisionTreeClassifier 
{
	static ArrayList<Record> trainingData = new ArrayList<Record>();
	static ArrayList<Record> testData = new ArrayList<Record>();
	static int numberOfAttributes;
	static enum option{entropy,gini};
	static String[] datasets = {"datasetGAR.csv"};
	static String[][] attributeInfo;
	static int[] attributes = {10};
	static String[] uniRanks = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
	static String[] program = {"CS","ECE","HCI","IS","Other"};
	static String[] degree = {"MS","MEng","MBA","Other","PhD"};
	static String[] session = {"F09","F10","F11","F12","F13","F14","F15","F16",
			"S10","S11","S12","S13","S14","S15","S16"};
	static String[] origin = {"American","International","Other","International with US Degree"};
	public static void main(String[] args) throws Exception 
	{
		for(int index=0;index<1;index++)
		{
			System.out.println("Experiment "+(index+1)+":");
			System.out.println("Running the dataset: "+datasets[index]);
			numberOfAttributes = attributes[index];
			setAttributeInfo(numberOfAttributes);
			int FP=0,TP=0,FN=0,TN=0,count=0;
			
			for(int k=1;k<=10;k++)
			{
				trainingData.clear();
				testData.clear();
				generateTrainingAndTestSet(index,k);
				//System.out.println(calculateEntropy(trainingData));
				TreeNode root = buildDecisionTreeClassifier(trainingData,option.entropy);
				int prediction;
				int actual;
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("treeprint.txt")));
				root.printTree(true, " ",bw);
				bw.close();
				
				for(Record r:testData)
				{
					prediction = traverseTree(root,r);
					actual = r.getTarget();
					if(actual==1&&prediction==1)
			        {
			            count++;
			            TP++;
			        }
			        else if(actual==0&&prediction==1)
			        {
			            FP++;
			        }
			        else if(actual==1&&prediction==0)
			        {
			            FN++;
			        }
			        else if(actual==0&&prediction==0)
			        {
			            count++;
			            TN++;
			        }
				}
			}
			TP /= 10;
			TN /= 10;
			FP /= 10;
			FN /= 10;
			count /= 10;
		    double accuracy,precision,recall,f_measure,g_mean;
		    //System.out.println(TP+" "+TN+" "+FP+" "+FN);
		    accuracy = (count*1.0)/testData.size();
		    precision = (TP*1.0)/(TP+FP);
		    recall = (TP*1.0)/(TP+FN);
		    f_measure = (2*precision*recall)/(precision+recall);
		    g_mean = Math.sqrt((TN*TP*1.0)/((TN+FP)*(TP+FN)));
		
		    //System.out.println("Right answer: "+ count+ " out of "+testData.size());
		    //System.out.print("Accuracy :"+(100*accuracy)+"\n");
		    //System.out.print("Accuracy: "+(100*accuracy)+"\n");
		    //System.out.println("Precision :"+(100*precision));
		
		    //System.out.print("Recall :"+(100*recall)+"\t");
		    //System.out.println("F-Measure :"+(100*f_measure));
		    //System.out.println("G-Mean :"+(100*g_mean));
		    //System.out.println("\n");
		    System.out.println((100*accuracy)+" & "+(100*precision)+" & "+ (100*recall));
		    

		    /*double accuracy,precision,recall,f_measure,g_mean;
		    System.out.println(TP+" "+TN+" "+FP+" "+FN);
		    accuracy = (count*1.0)/testData.size();
		    precision = (TP*1.0)/(TP+FP);
		    recall = (TP*1.0)/(TP+FN);
		    f_measure = (2*precision*recall)/(precision+recall);
		    g_mean = Math.sqrt((TN*TP*1.0)/((TN+FP)*(TP+FN)));
		
		    System.out.println("Right answer: "+ count+ " out of "+testData.size());
		    System.out.print("Accuracy :"+(100*accuracy)+"\t");
		    System.out.println("Precision :"+(100*precision));
		
		    System.out.print("Recall :"+(100*recall)+"\t");
		    System.out.println("F-Measure :"+(100*f_measure));
		    System.out.println("G-Mean :"+(100*g_mean));
		    System.out.println("\n");*/
		}
	}
	
	private static void setAttributeInfo(int numberOfAttributes)
	{
		// TODO Auto-generated method stub
		attributeInfo = new String[numberOfAttributes][];
		attributeInfo[0] = uniRanks;
		attributeInfo[1] = program;
		attributeInfo[2] = degree;
		attributeInfo[3] = session;
		attributeInfo[9] = origin;
	}
	public static boolean isCategorical(int index)
	{
		if(attributeInfo[index] == null)
			return false;
		else
			return true;
	}
	private static int traverseTree(TreeNode node, Record r) throws Exception 
	{
		if(node.isLeaf())
		{
			return node.getLabel();
		}
		
		int id = node.getAttributeID();
		if(isCategorical(id)==true)
		{
			if(((String)(r.getAttribute(id))).equals(node.getValue()))
			{
				return traverseTree(node.getLeft(), r);
			}
			else
			{
				return traverseTree(node.getRight(), r);
			}
		}
		else
		{
			if((Double)r.getAttribute(id)>node.getThreshold())
			{
				return traverseTree(node.getLeft(), r);
			}
			else
			{
				return traverseTree(node.getRight(), r);
			}
		}
		
	}
	private static double findBestThreshold(double min, double max, double rootEntropy,ArrayList<Record> data, int i, option opt) 
	{
		double range = max - min;
		double part = range/5;
		double entropy;
		double gain;
		double maxgain = -1;
		double bestThreshold = -1;
		ArrayList<Record> buffer1 = new ArrayList<Record>();
		ArrayList<Record> buffer2 = new ArrayList<Record>();
		for(double t = min+part;t<max; t+=part)
		{
			entropy = 0.0;
			for(Record r:data)
			{
				if((Double)r.getAttribute(i)>t)
				{
					buffer1.add(r);
				}
				else
				{
					buffer2.add(r);
				}
			}
			if(!buffer1.isEmpty())
			{
				entropy = (buffer1.size()*1.0/data.size())*calculate(buffer1,opt);
			}
			if(!buffer2.isEmpty())
			{
				entropy += (buffer2.size()*1.0/data.size())*calculate(buffer2,opt);
			}
			gain = rootEntropy-entropy;
			if((maxgain == -1)||(gain>maxgain))
			{
				maxgain = gain;
				bestThreshold = t;
			}
			buffer1.clear();
			buffer2.clear();
		}
		return bestThreshold;
	}
	
	private static void generateTrainingAndTestSet(int index,int k) throws Exception 
	{
		BufferedReader br = new BufferedReader(new FileReader(new File(datasets[index])));
		ArrayList<Record> data = new ArrayList<Record>();
		String line;
		switch(index)
		{
		case 0:
			int count = 0;
			while(true)
			{
				line = br.readLine();
				if(line == null)
					break;
				count++;
				if(count == 1)
					continue;
				String[] parts = line.split(",");
				Record r = new Record();
				for(int i=0;i<(parts.length-1);i++)
				{
					if(isCategorical(i)==false)
						r.set(i, Double.parseDouble(parts[i]));
					else
						r.set(i, parts[i]);
				}
				if(parts[parts.length-1].equals("Accepted"))
					r.setTargetAttribute(1);
				else if(parts[parts.length-1].equals("Rejected"))
					r.setTargetAttribute(0);
				data.add(r);
			}
			break;
			default:
				break;
		}
		br.close();
		Collections.shuffle(data);
		int foldsize = (int)(data.size()*0.10);
		
		int r=0;
		for(int i=1;i<=10;i++)
		{
			if(i==k)
			{
				for(int j=0;j<foldsize;j++)
				{
					testData.add(data.get(r++));
				}
			}
			else
			{
				for(int j=0;j<foldsize;j++)
				{
					trainingData.add(data.get(r++));
				}
			}
		}
		while(r<data.size())
		{
			trainingData.add(data.get(r));
			r++;
		}
	}
	private static double calculate(ArrayList<Record> data, option opt) 
	{
		if(opt == option.entropy)
			return calculateEntropy(data);
		else
			return calculateGini(data);
	}
	private static double calculateEntropy(ArrayList<Record> data)
    {
        int[] count = new int[2];
        double entropy = 0.0;
        for(int i=0;i<data.size();i++)
        {
        	switch(data.get(i).getTarget())
        	{
        	case 0:
        		count[0]++;
        		break;
        	case 1:
        		count[1]++;
        		break;
        	}
        }
        //System.out.println(count[0]+" "+count[1]);
        for(int i=0;i<2;i++)
        {
            if(count[i]!=0)
            {
                entropy += -((double)count[i]/data.size())*(Math.log((double)count[i]/data.size())/Math.log(2.0));
            }
        }
        return entropy;
    }
	private static double calculateGini(ArrayList<Record> data) 
	{
        int[] count = new int[2];
        double entropy = 1.0;
        for(int i=0;i<data.size();i++)
        {
        	switch(data.get(i).getTarget())
        	{
        	case 0:
        		count[0]++;
        		break;
        	case 1:
        		count[1]++;
        		break;
        	}
        }
        for(int i=0;i<2;i++)
        {
            if(count[i]!=0)
            {
            	entropy += -Math.pow(((count[i]*1.0)/data.size()),2);
            }
        }
        return entropy;
    }
	private static TreeNode buildDecisionTreeClassifier(ArrayList<Record> data,option opt) throws Exception 
	{
		double rootEntropy = calculate(data,opt);
		/* If entropy of the root is 0, then it is a leaf */
		if(rootEntropy == 0)
		{
			TreeNode root = new TreeNode();
			root.setLabel(data.get(0).getTarget());
			return root;
		}
		//System.out.println("Data Size: "+data.size()+" root entropy = "+rootEntropy);
		if(data.size()<100)
		{
			TreeNode root = new TreeNode();
			int count0=0,count1=0;
            for(Record r:data)
            {
                if(r.getTarget()==1)
                    count0++;
                else if(r.getTarget()==0)
                    count1++;
            }
            
            int l = (count0>count1)?1:0;
            root.setLabel(l);
            return root;
		}
		
		
		double[] min = new double[numberOfAttributes];
		double[] max = new double[numberOfAttributes];
		for(int i=0;i<numberOfAttributes;i++)
		{
			if(isCategorical(i)==false)
			{
				min[i] = max[i] = (Double)data.get(0).getAttribute(i);
				for(int j=1;j<data.size();j++)
				{
					if(min[i]>(Double)data.get(j).getAttribute(i))
					{
						min[i] = (Double)(data.get(j).getAttribute(i));
					}
					else if(max[i]<(Double)data.get(j).getAttribute(i))
					{
						max[i] = (Double)data.get(j).getAttribute(i);
					}
				}
			}
		}
		
		double maxgain = -1;
		int bestAttributeIndex = -1;
		double bestThreshold = -1.0;
		String bestAttributeValue="";
		double threshold=0.0;
		double entropy=0.0;
		double gain;
		String bestValue="";
		ArrayList<Record> buffer1 = new ArrayList<Record>();
		ArrayList<Record> buffer2 = new ArrayList<Record>();
		for(int i=0;i<numberOfAttributes;i++)
		{
			buffer1.clear();
			buffer2.clear();
			if(isCategorical(i)==false)
			{
				entropy = 0.0;
				threshold = (max[i]+min[i])/2;
				//threshold = findBestThreshold(min[i],max[i],rootEntropy,data,i,opt);
				for(Record r:data)
				{
					if((Double)r.getAttribute(i)>threshold)
					{
						buffer1.add(r);
					}
					else
					{
						buffer2.add(r);
					}
				}
				if(!buffer1.isEmpty())
				{
					entropy = (buffer1.size()*1.0/data.size())*calculate(buffer1,opt);
				}
				if(!buffer2.isEmpty())
				{
					entropy += (buffer2.size()*1.0/data.size())*calculate(buffer2,opt);
				}
				buffer1.clear();
				buffer2.clear();
				gain = rootEntropy-entropy;
				//System.out.println("Gain = "+gain+" with threshold = "+threshold +" for attribute "+i);
			}
			else // categorical attribute
			{
				String[] attributeValues = attributeInfo[i];
				double localgain;
				gain = -1.0;
				for(String str: attributeValues)
				{
					for(Record r:data)
					{
						if(((String)r.getAttribute(i)).equals(str))
						{
							buffer1.add(r);
						}
						else
						{
							buffer2.add(r);
						}
					}
					entropy = 0.0;
					if(!buffer1.isEmpty())
					{
						entropy = (buffer1.size()*1.0/data.size())*calculate(buffer1,opt);
					}
					if(!buffer2.isEmpty())
					{
						entropy += (buffer2.size()*1.0/data.size())*calculate(buffer2,opt);
					}
					localgain = rootEntropy-entropy;
					//System.out.println("----localGain = "+localgain+" entropy = "+entropy+" with value = "+str +" for attribute "+i + " and "+buffer1.size()+":"+buffer2.size());
					buffer1.clear();
					buffer2.clear();
					if((gain<localgain)||(gain == -1.0))
					{
						gain = localgain;
						bestValue = str;
					}
				}
				//System.out.println("Gain = "+gain+" with value = "+bestValue +" for attribute "+i);
			}
			if(gain>maxgain)
			{
				maxgain = gain;
				bestAttributeIndex = i;
				if(isCategorical(i)==true)
				{
					bestAttributeValue = bestValue;
				}
				else
				{
					bestThreshold = threshold;
				}
			}
		}

		TreeNode root = new TreeNode();
		root.setAttributeID(bestAttributeIndex);
		//System.out.print("Best Attribute: "+bestAttributeIndex);
		if(isCategorical(bestAttributeIndex)==true)
		{
			root.setValue(bestAttributeValue);
			//System.out.println(" Best Value = "+bestAttributeValue);
		}
		else
		{
			threshold = (max[bestAttributeIndex]+min[bestAttributeIndex])/2;
			root.setThreshold(bestThreshold);
			//System.out.println(" Threshold = "+bestThreshold);
		}
		//Scanner sc1 = new Scanner(System.in);
		//sc1.nextLine();
		buffer1.clear();
		buffer2.clear();
		if(isCategorical(bestAttributeIndex)==true)
		{
			for(Record r:data)
			{
				if(((String)r.getAttribute(bestAttributeIndex)).equals(bestAttributeValue))
				{
					buffer1.add(r);
				}
				else
				{
					buffer2.add(r);
				}
			}
		}
		else
		{
			for(Record r:data)
			{
				if((Double)r.getAttribute(bestAttributeIndex)>bestThreshold)
				{
					buffer1.add(r);
				}
				else
				{
					buffer2.add(r);
				}
			}
		}
		//System.out.println(buffer1.size()+" "+buffer2.size());
		//Scanner sc2 = new Scanner(System.in);
		//sc2.nextLine();
		if(buffer1.isEmpty())
		{
			TreeNode node = new TreeNode();
			int count0=0,count1=0;
            for(Record r:data)
            {
                if(r.getTarget()==1)
                    count0++;
                else if(r.getTarget()==0)
                    count1++;
            }
            
            int l = (count0>count1)?1:0;
            node.setLabel(l);
            root.setLeft(node);
		}
		else
		{
			TreeNode node = buildDecisionTreeClassifier(buffer1,opt);
			root.setLeft(node);
		}
		
		if(buffer2.isEmpty())
		{
			TreeNode node = new TreeNode();
			int count0=0,count1=0;
            for(Record r:data)
            {
                if(r.getTarget()==1)
                    count0++;
                else if(r.getTarget()==0)
                    count1++;
            }
            int l = (count0>count1)?1:0;
            node.setLabel(l);
            root.setRight(node);
		}
		else
		{
			TreeNode node = buildDecisionTreeClassifier(buffer2,opt);
			root.setRight(node);
		}
        return root;
	}
}