package com.svenhandt.app.cinemaapp.view;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;


public class PresentationView
{
	private int id;
	private String dayOfWeek;
	private String startTime;
	private FilmView filmView;
	private RoomView roomView;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDayOfWeek()
	{
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek)
	{
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public FilmView getFilmView()
	{
		return filmView;
	}

	public void setFilmView(FilmView filmView)
	{
		this.filmView = filmView;
	}

	public RoomView getRoomView()
	{
		return roomView;
	}

	public void setRoomView(RoomView roomView)
	{
		this.roomView = roomView;
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
