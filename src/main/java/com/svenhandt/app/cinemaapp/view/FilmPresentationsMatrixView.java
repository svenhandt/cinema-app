package com.svenhandt.app.cinemaapp.view;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;


public class FilmPresentationsMatrixView
{

	private FilmView filmView;
	private Map<String, List<PresentationView>> weekdayPresentationsMap;

	public FilmView getFilmView()
	{
		return filmView;
	}

	public void setFilmView(FilmView filmView)
	{
		this.filmView = filmView;
	}

	public Map<String, List<PresentationView>> getWeekdayPresentationsMap()
	{
		return weekdayPresentationsMap;
	}

	public void setWeekdayPresentationsMap(Map<String, List<PresentationView>> weekdayPresentationsMap)
	{
		this.weekdayPresentationsMap = weekdayPresentationsMap;
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
