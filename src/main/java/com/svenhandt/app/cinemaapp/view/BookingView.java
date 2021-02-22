package com.svenhandt.app.cinemaapp.view;

import java.math.BigDecimal;
import java.util.List;


public class BookingView
{

	private int id;
	private int presentationId;
	private BigDecimal totalPrice;
	private String totalPriceFormatted;
	private List<Integer> seatIds;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPresentationId()
	{
		return presentationId;
	}

	public void setPresentationId(int presentationId)
	{
		this.presentationId = presentationId;
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

	public List<Integer> getSeatIds()
	{
		return seatIds;
	}

	public void setSeatIds(List<Integer> seatIds)
	{
		this.seatIds = seatIds;
	}
}
