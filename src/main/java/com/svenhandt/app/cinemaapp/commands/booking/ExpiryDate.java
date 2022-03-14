package com.svenhandt.app.cinemaapp.commands.booking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ExpiryDate
{

	private String expiryMonth;
	private String expiryYear;
	private List<String> expiryMonths;
	private List<String> expiryYears;

	public ExpiryDate()
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
