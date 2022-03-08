package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.BookingRepository;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.entity.*;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.service.SeatsService;
import com.svenhandt.app.cinemaapp.view.FilmView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import com.svenhandt.app.cinemaapp.view.RoomView;
import com.svenhandt.app.cinemaapp.view.SeatRowView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class PresentationDetailsServiceImpl implements PresentationDetailsService
{

	private PresentationRepository presentationRepository;

	private BookingRepository bookingRepository;

	private SeatsService seatsService;

	private DataTypeConversionService dataTypeConversionService;

	@Override
	public PresentationView getPresentationDetails(int presentationId, PresentationDetailsOption option)
	{
		PresentationView presentationView;
		Optional<Presentation> presentationOptional = presentationRepository.findById(presentationId);
		Validate.isTrue(presentationOptional.isPresent(), ApplicationConstants.NO_PRESENTATION_FOR_ID + presentationId);
		Presentation presentation = presentationOptional.get();
		presentationView = createPresentationDetails(presentation, option);
		return presentationView;
	}

	private PresentationView createPresentationDetails(Presentation presentation, PresentationDetailsOption option)
	{
		Validate.notNull(presentation.getRoom(), ApplicationConstants.PRESENTATION_MUST_HAVE_ROOM);
		Validate.notNull(presentation.getFilm(), ApplicationConstants.PRESENTATION_MUST_HAVE_FILM);
		Validate.notNull(presentation.getPrice(), ApplicationConstants.PRESENTATION_MUST_HAVE_PRICE);
		PresentationView presentationView = new PresentationView();
		presentationView.setId(presentation.getId());
		presentationView.setDayOfWeek(dataTypeConversionService.getDayOfWeekAbbrev(presentation.getStartTime()));
		presentationView.setStartTime(dataTypeConversionService.getTimeOfDayFormatted(presentation.getStartTime()));
		presentationView.setPrice(presentation.getPrice());;
		setFilmView(presentation, presentationView);
		presentationView.setRoomView(createRoomView(presentation, option));
		return presentationView;
	}

	private void setFilmView(Presentation presentation, PresentationView presentationView)
	{
		Film film = presentation.getFilm();
		FilmView filmView = new FilmView(film.getId(), film.getTitle());
		presentationView.setFilmView(filmView);
	}

	private RoomView createRoomView(Presentation presentation, PresentationDetailsOption option)
	{
		Room room = presentation.getRoom();
		RoomView roomView = new RoomView(room.getId(), room.getName());
		if(PresentationDetailsOption.FULL.equals(option))
		{
			List<Seat> seatsForRoom = seatsService.findAllByRoomOrderByRowsAndNumbers(room);
			Map<Integer, Seat> presentationOccuppiedSeats = getOccuppiedSeatsMap(presentation);
			Map<Integer, SeatRowView> numberSeatRowMapping = seatsService.createNumberSeatRowMapping(presentationOccuppiedSeats, seatsForRoom);
			roomView.setNumberSeatRowMapping(numberSeatRowMapping);
		}
		return roomView;
	}

	Map<Integer, Seat> getOccuppiedSeatsMap(Presentation presentation)
	{
		List<Booking> bookings4Presentation = bookingRepository.findAllByPresentation(presentation);
		return seatsService.getBookingsOccuppiedSeats(bookings4Presentation);
	}

	@Autowired
	public void setPresentationRepository(PresentationRepository presentationRepository)
	{
		this.presentationRepository = presentationRepository;
	}

	@Autowired
	public void setSeatsService(SeatsService seatsService)
	{
		this.seatsService = seatsService;
	}

	@Autowired
	public void setDataTypeConversionService(DataTypeConversionService dataTypeConversionService)
	{
		this.dataTypeConversionService = dataTypeConversionService;
	}

	@Autowired
	public void setBookingRepository(BookingRepository bookingRepository)
	{
		this.bookingRepository = bookingRepository;
	}
}
