public class Record
{
    private Object[] attributes = new Object[DecisionTreeClassifier.numberOfAttributes];
    private int target;
    public void set(int index,Object val)
    {
    	if(DecisionTreeClassifier.isCategorical(index)==true)
    		this.attributes[index] = (String)val;
    	else
    		this.attributes[index] = (Double)val;
    }
    public void setTargetAttribute(int target)
    {
        this.target = target;
    }
    public int getTarget()
    {
        return this.target;
    }
    public Object getAttribute(int index)
    {
        return attributes[index];
    }
}