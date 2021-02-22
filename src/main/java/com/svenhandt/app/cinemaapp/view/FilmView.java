package com.svenhandt.app.cinemaapp.view;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class FilmView
{

	private int id;
	private String title;

	public FilmView()
	{

	}

	public FilmView(int id, String title)
	{
		this.id = id;
		this.title = title;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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
