package com.svenhandt.app.cinemaapp.view;

import com.svenhandt.app.cinemaapp.enums.CreateBookingStatus;


public class CreateBookingResultView
{
	private int bookingId;
	private CreateBookingStatus createBookingStatus;

	public int getBookingId()
	{
		return bookingId;
	}

	public void setBookingId(int bookingId)
	{
		this.bookingId = bookingId;
	}

	public CreateBookingStatus getCreateBookingStatus()
	{
		return createBookingStatus;
	}

	public void setCreateBookingStatus(CreateBookingStatus createBookingStatus)
	{
		this.createBookingStatus = createBookingStatus;
	}
}
