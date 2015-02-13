package bankdetails;

import java.util.Comparator;

/**
 * 
 * DateSort is a class which implements Comparator interface and helps to
 * compare dates.
 *
 */

public class DateSort implements Comparator<BankCardDetails> {

	/**
	 * this function compares dates and returns them in the decreasing order
	 */
	public int compare(BankCardDetails bd1, BankCardDetails bd2) {
		return bd2.getExpDate().compareTo(bd1.getExpDate());
	}

}
