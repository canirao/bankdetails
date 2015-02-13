package bankdetails;

public class AmexCardPredicate implements Predicate<String> {
	public String apply(String number) {

		String maskedCardNum = number.substring(0, number.length() - 3);
		maskedCardNum = maskedCardNum.replaceAll("[0-9]", "x");
		maskedCardNum = maskedCardNum.concat(number.substring(15,
				number.length()));
		return maskedCardNum;
	}

}
