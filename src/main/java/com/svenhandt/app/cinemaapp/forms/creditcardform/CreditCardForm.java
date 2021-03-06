package com.svenhandt.app.cinemaapp.forms.creditcardform;



import com.svenhandt.app.cinemaapp.forms.creditcardform.validation.ValidExpiryDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class CreditCardForm
{

	@NotBlank(message = "Bitte geben Sie einen Namen ein")
	private String cardName;

	@Pattern(regexp = "\\d{16}", message = "Kreditkartennummer muss eine 16-stellige Zahl sein")
	private String cardNumber;

	@ValidExpiryDate
	private ExpiryDate expiryDate;

	@Pattern(regexp = "\\d{3,4}", message = "Prüfziffer muss eine 3- oder 4-stellige Zahl sein")
	private String cvv;

	public CreditCardForm()
	{
		this.expiryDate = new ExpiryDate();
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

	public ExpiryDate getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate(ExpiryDate expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public String getCvv()
	{
		return cvv;
	}

	public void setCvv(String cvv)
	{
		this.cvv = cvv;
	}
}
