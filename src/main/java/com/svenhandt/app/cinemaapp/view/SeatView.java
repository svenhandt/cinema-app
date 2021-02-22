package com.svenhandt.app.cinemaapp.view;

import com.svenhandt.app.cinemaapp.enums.SeatType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SeatView
{


	private int id;
	private int seatRow;
	private int numberInSeatRow;
	private SeatType seatType;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getSeatRow()
	{
		return seatRow;
	}

	public void setSeatRow(int seatRow)
	{
		this.seatRow = seatRow;
	}

	public int getNumberInSeatRow()
	{
		return numberInSeatRow;
	}

	public void setNumberInSeatRow(int numberInSeatRow)
	{
		this.numberInSeatRow = numberInSeatRow;
	}

	public SeatType getSeatType()
	{
		return seatType;
	}

	public void setSeatType(SeatType seatType)
	{
		this.seatType = seatType;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object object)
	{
		return EqualsBuilder.reflectionEquals(this, object);
	}


}
