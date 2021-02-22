package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.entity.Film;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.PresentationListService;
import com.svenhandt.app.cinemaapp.view.FilmPresentationsMatrixView;
import com.svenhandt.app.cinemaapp.view.FilmView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PresentationListServiceImpl implements PresentationListService
{

	private PresentationRepository presentationRepository;

	private DataTypeConversionService dataTypeConversionService;

	@Override
	public Set<FilmPresentationsMatrixView> createFilmsAndPresentationsView()
	{
		Map<Integer, FilmPresentationsMatrixView> filmViewsMap = new LinkedHashMap<>();
		List<Presentation> presentations = presentationRepository.findAll(Sort.by(Sort.Direction.ASC, "film", "startTime"));
		if(presentations != null)
		{
			for(Presentation presentation : presentations)
			{
				FilmPresentationsMatrixView currentFilmPresentationsMatrixView = getOrCreateFilmView(filmViewsMap, presentation.getFilm());
				createAndAddPresentationView(currentFilmPresentationsMatrixView, presentation);
			}
		}
		return new LinkedHashSet<>(filmViewsMap.values());
	}

	private FilmPresentationsMatrixView getOrCreateFilmView(Map<Integer, FilmPresentationsMatrixView> filmViewsMap, Film film)
	{
		Validate.notNull(film, "Film of presentation must not be null");
		FilmPresentationsMatrixView filmPresentationsMatrixView;
		int filmId = film.getId();
		if(filmViewsMap.containsKey(filmId))
		{
			filmPresentationsMatrixView = filmViewsMap.get(filmId);
		}
		else {
			filmPresentationsMatrixView = createMatrixViewWithFilm(film);
			filmViewsMap.put(filmId, filmPresentationsMatrixView);
		}
		return filmPresentationsMatrixView;
	}

	private FilmPresentationsMatrixView createMatrixViewWithFilm(Film film)
	{
		FilmPresentationsMatrixView filmPresentationsMatrixView = new FilmPresentationsMatrixView();
		FilmView filmView = new FilmView(film.getId(), film.getTitle());
		filmPresentationsMatrixView.setFilmView(filmView);
		return filmPresentationsMatrixView;
	}

	private void createAndAddPresentationView(FilmPresentationsMatrixView filmPresentationsMatrixView, Presentation presentation)
	{
		createPresentrationsMapIfNull(filmPresentationsMatrixView);
		String dayOfWeek = dataTypeConversionService.getDayOfWeekAbbrev(presentation.getStartTime());
		List<PresentationView> presentationViews = getPresentationViews(filmPresentationsMatrixView, dayOfWeek);
		PresentationView presentationView = createPresentationView(dayOfWeek, presentation);
		presentationViews.add(presentationView);
	}

	private void createPresentrationsMapIfNull(FilmPresentationsMatrixView filmPresentationsMatrixView)
	{
		if(filmPresentationsMatrixView.getWeekdayPresentationsMap() == null)
		{
			Map<String, List<PresentationView>> weekdayPresentationMap = new LinkedHashMap<>();
			filmPresentationsMatrixView.setWeekdayPresentationsMap(weekdayPresentationMap);
		}
	}

	private List<PresentationView> getPresentationViews(FilmPresentationsMatrixView filmPresentationsMatrixView, String dayOfWeek)
	{
		List<PresentationView> result;
		Map<String, List<PresentationView>> weekdayPresentationMap = filmPresentationsMatrixView.getWeekdayPresentationsMap();
		if (weekdayPresentationMap.containsKey(dayOfWeek))
		{
			result = weekdayPresentationMap.get(dayOfWeek);
		}
		else
		{
			result = new ArrayList<>();
			weekdayPresentationMap.put(dayOfWeek, result);
		}
		return result;
	}

	private PresentationView createPresentationView(String dayOfWeek, Presentation presentation)
	{
		PresentationView presentationView = new PresentationView();
		presentationView.setId(presentation.getId());
		presentationView.setDayOfWeek(dayOfWeek);
		String startTime = dataTypeConversionService.getTimeOfDayFormatted(presentation.getStartTime());
		presentationView.setStartTime(startTime);
		return presentationView;
	}

	@Autowired
	public void setPresentationRepository(PresentationRepository presentationRepository)
	{
		this.presentationRepository = presentationRepository;
	}

	@Autowired
	public void setDataTypeConversionService(DataTypeConversionService dataTypeConversionService)
	{
		this.dataTypeConversionService = dataTypeConversionService;
	}

}
