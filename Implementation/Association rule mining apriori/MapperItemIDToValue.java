/**
 * 
 */


/**
 * @author SyedMahbub
 *
 */
public class MapperItemIDToValue {
	private int itemID;
	private int categoryID;
	public MapperItemIDToValue(int itemID, int categoryID) {
		this.itemID = itemID;
		this.categoryID = categoryID;
	}
//	private String valueName;
	/**
	 * @return the itemID
	 */
	public int getItemID() {
		return itemID;
	}
	/**
	 * @param itemID the itemID to set
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}
	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}


}
