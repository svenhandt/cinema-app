package com.svenhandt.app.cinemaapp.forms;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreditCardForm
{

	@NotBlank(message = "Bitte geben Sie einen Namen ein")
	private String cardName;

	@Pattern(regexp = "\\d{16}", message = "Kreditkartennummer muss eine 16-stellige Zahl sein")
	private String cardNumber;

	@NotBlank(message = "Bitte wählen Sie einen Ablaufmonat aus")
	private String expiryMonth;

	@NotBlank(message = "Bitte wählen Sie ein Ablaufjahr aus")
	private String expiryYear;

	@NotBlank(message = "Bitte geben Sie eine Prüfziffer ein")
	private String cvv;

	private List<String> expiryMonths;
	private List<String> expiryYears;

	public CreditCardForm()
	{
		initializeExpiryMonths();
		initializeExpiryYears();
	}

	private void initializeExpiryMonths()
	{
		expiryMonths = new ArrayList<>();
		expiryMonths.add("01");
		expiryMonths.add("02");
		expiryMonths.add("03");
		expiryMonths.add("04");
		expiryMonths.add("05");
		expiryMonths.add("06");
		expiryMonths.add("07");
		expiryMonths.add("08");
		expiryMonths.add("09");
		expiryMonths.add("10");
		expiryMonths.add("11");
		expiryMonths.add("12");
	}

	private void initializeExpiryYears()
	{
		expiryYears = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		for(int i=0; i < 10; i++)
		{
			expiryYears.add(Integer.toString(currentYear + i));
		}
	}

	public String getCardName()
	{
		return cardName;
	}

	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getExpiryMonth()
	{
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth)
	{
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear()
	{
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear)
	{
		this.expiryYear = expiryYear;
	}

	public String getCvv()
	{
		return cvv;
	}

	public void setCvv(String cvv)
	{
		this.cvv = cvv;
	}

	public List<String> getExpiryMonths()
	{
		return expiryMonths;
	}

	public void setExpiryMonths(List<String> expiryMonths)
	{
		this.expiryMonths = expiryMonths;
	}

	public List<String> getExpiryYears()
	{
		return expiryYears;
	}

	public void setExpiryYears(List<String> expiryYears)
	{
		this.expiryYears = expiryYears;
	}
}
