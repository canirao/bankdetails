package bankdetails;

/**
 * This class processes all the data with the bank name as "HSBC Canada" and
 * returns the appropriate masked card number.
 */
public class HSBCCardPredicate implements Predicate<String> {

	public String apply(String number) {

		String maskedCardNum = number.substring(2, number.length());
		maskedCardNum = maskedCardNum.replaceAll("[0-9]", "x");
		maskedCardNum = number.substring(0, 2).concat(maskedCardNum);
		return maskedCardNum;
	}
}
