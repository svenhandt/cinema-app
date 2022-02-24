package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.entity.Film;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.view.FilmPresentationsMatrixView;
import com.svenhandt.app.cinemaapp.view.FilmView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PresentationListServiceImplTest
{

	@MockBean
	private PresentationRepository presentationRepository;

	@MockBean
	private DataTypeConversionService dataTypeConversionService;

	@Test
	public void shouldCreateFilmsAndPresentationsView()
	{
		List<Film> mockFilms = createFilms();
		List<Presentation> mockPresentations = createMockPresentations(mockFilms);
		when(presentationRepository.findAll(Sort.by(Sort.Direction.ASC, "film", "startTime"))).thenReturn(mockPresentations);
		PresentationListServiceImpl presentationListService = new PresentationListServiceImpl();
		presentationListService.setPresentationRepository(presentationRepository);
		presentationListService.setDataTypeConversionService(dataTypeConversionService);
		Set<FilmPresentationsMatrixView> filmPresentationsMatrixViewSet = presentationListService.createFilmsAndPresentationsView();
		performAssertions(filmPresentationsMatrixViewSet);
	}

	private List<Film> createFilms()
	{
		List<Film> mockFilms = new ArrayList<>();
		mockFilms.add(createMockFilm(1, "Ballon"));
		mockFilms.add(createMockFilm(2, "Independence Day"));
		mockFilms.add(createMockFilm(3, "Kung Fu Hussle"));
		mockFilms.add(createMockFilm(4, "Der Untergang"));
		mockFilms.add(createMockFilm(5, "Kill Bill"));
		mockFilms.add(createMockFilm(6, "Starship Troopers"));
		mockFilms.add(createMockFilm(7, "Der Herr der Ringe 1"));
		mockFilms.add(createMockFilm(8, "Zurück in die Zukunft"));
		mockFilms.add(createMockFilm(9, "Die Welle"));
		return mockFilms;
	}

	private List<Presentation> createMockPresentations(List<Film> films)
	{
		List<Presentation> mockPresentations = new ArrayList<>();
		mockPresentations.add(createPresentation(1, films.get(0),  "Saal 1", LocalDateTime.of(2021, 03, 01, 15, 00)));
		mockPresentations.add(createPresentation(2, films.get(1), "Saal 1", LocalDateTime.of(2021, 03, 01, 17, 00)));
		mockPresentations.add(createPresentation(3, films.get(2), "Saal 1", LocalDateTime.of(2021, 03, 01, 21, 30)));

		mockPresentations.add(createPresentation(4, films.get(3), "Saal 2", LocalDateTime.of(2021, 03, 01, 15, 30)));
		mockPresentations.add(createPresentation(5, films.get(4), "Saal 2", LocalDateTime.of(2021, 03, 01, 18, 30)));
		mockPresentations.add(createPresentation(6, films.get(5), "Saal 2", LocalDateTime.of(2021, 03, 01, 22, 30)));

		mockPresentations.add(createPresentation(7, films.get(6), "Saal 3", LocalDateTime.of(2021, 03, 01, 15, 30)));
		mockPresentations.add(createPresentation(8, films.get(7), "Saal 3", LocalDateTime.of(2021, 03, 01, 20, 30)));
		mockPresentations.add(createPresentation(9, films.get(8), "Saal 3", LocalDateTime.of(2021, 03, 01, 23, 30)));

		mockPresentations.add(createPresentation(10, films.get(0), "Saal 1", LocalDateTime.of(2021, 03, 02, 15, 00)));
		mockPresentations.add(createPresentation(11, films.get(1), "Saal 1", LocalDateTime.of(2021, 03, 02, 17, 00)));
		mockPresentations.add(createPresentation(12, films.get(2), "Saal 1", LocalDateTime.of(2021, 03, 02, 21, 30)));

		mockPresentations.add(createPresentation(13, films.get(3), "Saal 2", LocalDateTime.of(2021, 03, 02, 15, 30)));
		mockPresentations.add(createPresentation(14, films.get(4), "Saal 2", LocalDateTime.of(2021, 03, 02, 18, 30)));
		mockPresentations.add(createPresentation(15, films.get(5), "Saal 2", LocalDateTime.of(2021, 03, 02, 22, 30)));

		mockPresentations.add(createPresentation(16, films.get(6), "Saal 3", LocalDateTime.of(2021, 03, 02, 15, 30)));
		mockPresentations.add(createPresentation(17, films.get(7), "Saal 3", LocalDateTime.of(2021, 03, 02, 20, 30)));
		mockPresentations.add(createPresentation(18, films.get(8), "Saal 3", LocalDateTime.of(2021, 03, 02, 23, 30)));

		mockPresentations.add(createPresentation(19, films.get(0), "Saal 1", LocalDateTime.of(2021, 03, 03, 15, 00)));
		mockPresentations.add(createPresentation(20, films.get(1), "Saal 1", LocalDateTime.of(2021, 03, 03, 17, 00)));
		mockPresentations.add(createPresentation(21, films.get(2), "Saal 1", LocalDateTime.of(2021, 03, 03, 21, 30)));

		mockPresentations.add(createPresentation(22, films.get(3), "Saal 2", LocalDateTime.of(2021, 03, 03, 15, 30)));
		mockPresentations.add(createPresentation(23, films.get(4), "Saal 2", LocalDateTime.of(2021, 03, 03, 18, 30)));
		mockPresentations.add(createPresentation(24, films.get(5), "Saal 2", LocalDateTime.of(2021, 03, 03, 22, 30)));

		mockPresentations.add(createPresentation(25, films.get(6), "Saal 3", LocalDateTime.of(2021, 03, 03, 15, 30)));
		mockPresentations.add(createPresentation(26, films.get(7), "Saal 3", LocalDateTime.of(2021, 03, 03, 20, 30)));
		mockPresentations.add(createPresentation(27, films.get(8), "Saal 3", LocalDateTime.of(2021, 03, 03, 23, 30)));

		mockPresentations.add(createPresentation(28, films.get(0), "Saal 1", LocalDateTime.of(2021, 03, 04, 15, 00)));
		mockPresentations.add(createPresentation(29, films.get(1), "Saal 1", LocalDateTime.of(2021, 03, 04, 17, 00)));
		mockPresentations.add(createPresentation(30, films.get(2), "Saal 1", LocalDateTime.of(2021, 03, 04, 21, 30)));

		mockPresentations.add(createPresentation(31, films.get(3), "Saal 2", LocalDateTime.of(2021, 03, 04, 15, 30)));
		mockPresentations.add(createPresentation(32, films.get(4), "Saal 2", LocalDateTime.of(2021, 03, 04, 18, 30)));
		mockPresentations.add(createPresentation(33, films.get(5), "Saal 2", LocalDateTime.of(2021, 03, 04, 22, 30)));

		mockPresentations.add(createPresentation(34, films.get(6), "Saal 3", LocalDateTime.of(2021, 03, 04, 15, 30)));
		mockPresentations.add(createPresentation(35, films.get(7), "Saal 3", LocalDateTime.of(2021, 03, 04, 20, 30)));
		mockPresentations.add(createPresentation(36, films.get(8), "Saal 3", LocalDateTime.of(2021, 03, 04, 23, 30)));

		mockPresentations.add(createPresentation(37, films.get(0), "Saal 1", LocalDateTime.of(2021, 03, 05, 15, 00)));
		mockPresentations.add(createPresentation(38, films.get(1), "Saal 1", LocalDateTime.of(2021, 03, 05, 17, 00)));
		mockPresentations.add(createPresentation(39, films.get(2), "Saal 1", LocalDateTime.of(2021, 03, 05, 21, 30)));

		mockPresentations.add(createPresentation(40, films.get(3), "Saal 2", LocalDateTime.of(2021, 03, 05, 15, 30)));
		mockPresentations.add(createPresentation(41, films.get(4), "Saal 2", LocalDateTime.of(2021, 03, 05, 18, 30)));
		mockPresentations.add(createPresentation(42, films.get(5), "Saal 2", LocalDateTime.of(2021, 03, 05, 22, 30)));

		mockPresentations.add(createPresentation(43, films.get(6), "Saal 3", LocalDateTime.of(2021, 03, 05, 15, 30)));
		mockPresentations.add(createPresentation(44, films.get(7), "Saal 3", LocalDateTime.of(2021, 03, 05, 20, 30)));
		mockPresentations.add(createPresentation(45, films.get(8), "Saal 3", LocalDateTime.of(2021, 03, 05, 23, 30)));
		return mockPresentations;
	}

	private Presentation createPresentation(int id, Film film, String room, LocalDateTime localDateTime)
	{
		Presentation presentation = new Presentation();
		presentation.setId(id);
		presentation.setFilm(film);
		presentation.setStartTime(getDate(localDateTime));
		presentation.setRoom(new Room(room));
		return presentation;
	}

	private Film createMockFilm(int id, String title)
	{
		Film film = new Film();
		film.setId(id);
		film.setTitle(title);
		return film;
	}

	private Date getDate(LocalDateTime localDateTime)
	{
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(localDateTime.atZone(defaultZoneId).toInstant());
	}

	private void performAssertions(Set<FilmPresentationsMatrixView> filmPresentationsMatrixViewSet)
	{
		assertNotNull(filmPresentationsMatrixViewSet, "Film presentation matrix view was null.");
		assertTrue(filmPresentationsMatrixViewSet.size() == 9);
		assertCorrectFilmOrder(filmPresentationsMatrixViewSet);
	}

	private void assertCorrectFilmOrder(Set<FilmPresentationsMatrixView> filmPresentationsMatrixViewSet)
	{
		List<FilmPresentationsMatrixView> filmPresentationsMatrixViewList = new ArrayList<>(filmPresentationsMatrixViewSet);
		for(int i=1; i <= filmPresentationsMatrixViewList.size(); i++)
		{
			FilmPresentationsMatrixView filmPresentationsMatrixView = filmPresentationsMatrixViewList.get(i-1);
			FilmView filmView = filmPresentationsMatrixView.getFilmView();
			assertTrue(i == filmView.getId(), "Film was in incorrect position: " + filmView.getId());
		}
	}

	private void assertCorrectPresentationsPerFilm(Set<FilmPresentationsMatrixView> filmPresentationsMatrixViewSet)
	{
		List<FilmPresentationsMatrixView> filmPresentationsMatrixViewList = new ArrayList<>(filmPresentationsMatrixViewSet);

	}

	private void validate1stFilmPresentationsMatrixView(FilmPresentationsMatrixView filmPresentationsMatrixView)
	{

	}

}
