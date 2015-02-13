package bankdetails;

/**
 * This class processes all the data with the bank name as
 * "Royal Bank of Canada" and returns the appropriate masked card number.
 */

public class RBCCardPredicate implements Predicate<String> {
	public String apply(String number) {

		String maskedCardNum = number.substring(4, number.length());
		maskedCardNum = maskedCardNum.replaceAll("[0-9]", "x");
		maskedCardNum = number.substring(0, 4).concat(maskedCardNum);
		return maskedCardNum;
	}

}
