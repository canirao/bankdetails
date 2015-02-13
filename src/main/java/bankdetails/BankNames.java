package bankdetails;

import java.util.HashSet;

/**
 * 
 * BankNames is an enum which contains all the names of the bank and provides
 * a method to get those values when required
 *
 */

public enum BankNames {

	HSBC_CANADA("HSBC Canada"), RBC("Royal Bank of Canada"), AMEX(
			"American Express");

	private String name;

	 BankNames(String name) {
		this.name = name;

	}

	public static HashSet<String> getEnums() {
		HashSet<String> values = new HashSet<String>();

		for (BankNames b : BankNames.values()) {
			values.add(b.name);
		}
		return values;
	}

}
