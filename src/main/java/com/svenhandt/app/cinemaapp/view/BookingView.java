package com.svenhandt.app.cinemaapp.view;

import java.math.BigDecimal;
import java.util.Map;


public class BookingView
{

	private int id;
	private PresentationView presentationView;
	private BigDecimal totalPrice;
	private String totalPriceFormatted;
	private Map<Integer, SeatView> seatsMap;


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public PresentationView getPresentationView()
	{
		return presentationView;
	}

	public void setPresentationView(PresentationView presentationView)
	{
		this.presentationView = presentationView;
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public String getTotalPriceFormatted()
	{
		return totalPriceFormatted;
	}

	public void setTotalPriceFormatted(String totalPriceFormatted)
	{
		this.totalPriceFormatted = totalPriceFormatted;
	}

	public Map<Integer, SeatView> getSeatsMap()
	{
		return seatsMap;
	}

	public void setSeatsMap(Map<Integer, SeatView> seatsMap)
	{
		this.seatsMap = seatsMap;
	}
}
