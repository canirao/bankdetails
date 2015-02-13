package bankdetails;

import java.util.Date;

public class BankCardDetails {

	private String bankName;
	private String cardNumber;
	private Date expDate;

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public BankCardDetails() {
	}

	public BankCardDetails(String bName, String bNum, Date exDate) {
		this.bankName = bName;
		this.cardNumber = bNum;
		this.expDate = exDate;

	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


}
