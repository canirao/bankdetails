package bankdetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

public class TestBankDetails {

	BankDetailsController bkdt = new BankDetailsController();
	Map<String, Predicate<String>> map = bkdt.populateMap();

	public List<Date> returnDate() {
		List<Date> expDateList = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
		Date d1 = null;
		Date d2 = null;
		Date d3 = null;

		try {
			d1 = sdf.parse("Jan-2015");
			d2 = sdf.parse("Dec-2014");
			d3 = sdf.parse("Apr-2017");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		expDateList.add(d1);
		expDateList.add(d2);
		expDateList.add(d3);

		return expDateList;
	}

	@Test
	public void testValidateHSBC() {
		Assert.assertTrue(bkdt.validate("HSBC Canada"));
	}

	@Test
	public void testValidateRBC() {
		Assert.assertTrue(bkdt.validate("Royal Bank of Canada"));
	}

	@Test
	public void testValidateAmex() {
		Assert.assertTrue(bkdt.validate("American Express"));
	}

	@Test
	public void testValidateInvalidBankName() {
		Assert.assertFalse(bkdt.validate("UBS"));
	}

	@Test
	public void testHSBCMaskCardNumber() {

		List<BankCardDetails> bankCardDetailsList = new ArrayList<BankCardDetails>();
		BankCardDetails bkCdDetails = new BankCardDetails("HSBC Canada",
				"5601-2345-3446-5678", returnDate().get(0));
		bankCardDetailsList.add(bkCdDetails);
		Assert.assertEquals(
				"56xx-xxxx-xxxx-xxxx",
				bkdt.maskCardNumber(bkCdDetails.getBankName(),
						bkCdDetails.getCardNumber(), map));

	}

	@Test
	public void testRBCCMaskCardNumber() {

		List<BankCardDetails> bankCardDetailsList = new ArrayList<BankCardDetails>();

		BankCardDetails bkCdDetails = new BankCardDetails(
				"Royal Bank of Canada", "4519-4532-4524-2456", returnDate()
						.get(1));
		bankCardDetailsList.add(bkCdDetails);
		Assert.assertEquals(
				"4519-xxxx-xxxx-xxxx",
				bkdt.maskCardNumber(bkCdDetails.getBankName(),
						bkCdDetails.getCardNumber(), map));

	}

	@Test
	public void testAmexMaskCardNumber() {

		List<BankCardDetails> bankCardDetailsList = new ArrayList<BankCardDetails>();

		BankCardDetails bkCdDetails = new BankCardDetails("American Express",
				"3786-7334-8965-345", returnDate().get(2));
		bankCardDetailsList.add(bkCdDetails);
		Assert.assertEquals(
				"xxxx-xxxx-xxxx-345",
				bkdt.maskCardNumber(bkCdDetails.getBankName(),
						bkCdDetails.getCardNumber(), map));

	}

	@Test
	public void testCompareDate() {
		List<BankCardDetails> expectedBankDetailsList = new ArrayList<BankCardDetails>();
		List<BankCardDetails> actualBankDetailsList = new ArrayList<BankCardDetails>();

		expectedBankDetailsList.add(new BankCardDetails("American Express",
				"3786-7334-8965-345", returnDate().get(2)));
		expectedBankDetailsList.add(new BankCardDetails("Royal Bank of Canada",
				"4519-4532-4524-2456", returnDate().get(0)));
		expectedBankDetailsList.add(new BankCardDetails("Royal Bank of Canada",
				"4519-4532-4524-2456", returnDate().get(1)));

		actualBankDetailsList.add(new BankCardDetails("Royal Bank of Canada",
				"4519-4532-4524-2456", returnDate().get(0)));
		actualBankDetailsList.add(new BankCardDetails("American Express",
				"3786-7334-8965-345", returnDate().get(1)));
		actualBankDetailsList.add(new BankCardDetails("Royal Bank of Canada",
				"1233-4532-4524-2456", returnDate().get(2)));

		for (int i = 0; i < expectedBankDetailsList.size(); i++)
			Assert.assertEquals(expectedBankDetailsList.get(i).getExpDate(),
					bkdt.compareDate(actualBankDetailsList).get(i).getExpDate());
	}

}
