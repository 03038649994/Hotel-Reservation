/**
 * A receipt formatting interface (strategy pattern)
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public interface ReceiptFormatter {
	
	/**
	 * A method to format the string based on the strategy pattern
	 * @return a string in the correct format
	 */
	String format();

}
