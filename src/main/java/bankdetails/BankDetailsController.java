package bankdetails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * BankDetailsMainImpl class provides methods to read a file, process them and
 * display the data.
 */
public class BankDetailsController {

	public static void main(String[] a) {

		BankDetailsController bd = new BankDetailsController();
		List<BankCardDetails> bankDetailsList = bd.readFile();
		List<BankCardDetails> sortedBankDetailsList = bd
				.compareDate(bankDetailsList);
		Map<String, Predicate<String>> bankNamesMap = bd.populateMap();
		List<BankCardDetails> finalList = new ArrayList<BankCardDetails>();

		for (BankCardDetails bkCardDet : sortedBankDetailsList) {

			boolean valid = bd.validate(bkCardDet.getBankName());

			if (valid) {
				String updCardNum = bd.maskCardNumber(bkCardDet.getBankName(),
						bkCardDet.getCardNumber(), bankNamesMap);
				bkCardDet.setCardNumber(updCardNum);
				finalList.add(bkCardDet);
			} else {
				System.out.println("BankName not supported");
				return;
			}
		}

		bd.display(finalList);

	}

	/**
	 * this method reads a pipe delimited text file as input, processes
	 * them,maps them to a bank card details object and stores them in a list
	 * and returns the same.
	 * 
	 * @return List<BankDetails>
	 */

	public List<BankCardDetails> readFile() {
		List<BankCardDetails> bdPojoList = new ArrayList<BankCardDetails>();
		BufferedReader buffReader = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("BankDetailsData.txt")
					.getFile());

			buffReader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = buffReader.readLine()) != null) {

				String[] fields = line.split("\\|");

				BankCardDetails bankDet = new BankCardDetails();
				bankDet.setBankName(fields[0]);
				bankDet.setCardNumber(fields[1]);

				SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
				Date d;
				try {
					d = sdf.parse(fields[2]);
					bankDet.setExpDate(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				bdPojoList.add(bankDet);
			}
		} catch (FileNotFoundException file) {
			file.printStackTrace();
			System.out.println("File not found");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bdPojoList;
	}

	/**
	 * This method is used to populate a hash map with all the bank names.
	 * 
	 * @return HashMap<String, Predicate<String>>
	 */

	public <T> Map<String, Predicate<String>> populateMap() {

		Map<String, Predicate<String>> map = new HashMap<String, Predicate<String>>();
		map.put("HSBC Canada", new HSBCCardPredicate());
		map.put("Royal Bank of Canada", new RBCCardPredicate());
		map.put("American Express", new AmexCardPredicate());
		return map;

	}

	/**
	 * this method takes in the bank name and checks if the bank name is valid
	 * or not
	 * 
	 * @param bankName
	 * @return boolean
	 */

	public boolean validate(String bankName) {
		HashSet<String> bkNamesSet = BankNames.getEnums();
		if (bkNamesSet.contains(bankName)) {
			return true;
		}
		return false;
	}

	/**
	 * this method takes the card number and the map containing the names of the
	 * banks as parameters and masks the card number accordingly.
	 * 
	 * @param cardNumber
	 * @param bankName
	 * @param bankNamesMap
	 * @return String
	 */

	public String maskCardNumber(String bankName, String cardNumber,
			Map<String, Predicate<String>> bankNamesMap) {

		Predicate<String> pd = bankNamesMap.get(bankName);
		String updatedCardNum = pd.apply(cardNumber);
		return updatedCardNum;

	}

	/**
	 * This method takes a list of bank detail beans as parameter. this is used
	 * to compare dates and will return the sorted list of bank card detail
	 * objects
	 * 
	 * @param bankDetailsList
	 * @return
	 */

	public List<BankCardDetails> compareDate(
			List<BankCardDetails> bankDetailsList) {
		DateSort ds = new DateSort();
		Collections.sort(bankDetailsList, ds);
		return bankDetailsList;
	}

	/**
	 * This method takes the final list of bank card details as parameter and
	 * displays the same.
	 * 
	 * @param finalList
	 */
	public void display(List<BankCardDetails> finalList) {
		for (BankCardDetails b : finalList) {
			SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String expiryDate = formatter.format(b.getExpDate());
			System.out.println(b.getBankName() + " " + b.getCardNumber() + " "
					+ expiryDate);
		}
	}

}
