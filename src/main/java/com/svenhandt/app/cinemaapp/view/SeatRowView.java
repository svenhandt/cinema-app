package com.svenhandt.app.cinemaapp.view;

import java.util.List;


public class SeatRowView
{
	private List<SeatView> seatViews;

	public SeatRowView()
	{

	}

	public SeatRowView(List<SeatView> seatViews)
	{
		this.seatViews = seatViews;
	}

	public List<SeatView> getSeatViews()
	{
		return seatViews;
	}

	public void setSeatViews(List<SeatView> seatViews)
	{
		this.seatViews = seatViews;
	}
}
