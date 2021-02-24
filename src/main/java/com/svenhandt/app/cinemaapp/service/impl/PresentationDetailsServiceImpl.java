package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.BookingRepository;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.dao.SeatRepository;
import com.svenhandt.app.cinemaapp.entity.*;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.enums.SeatType;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.view.*;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PresentationDetailsServiceImpl implements PresentationDetailsService
{

	private PresentationRepository presentationRepository;

	private SeatRepository seatRepository;

	private BookingRepository bookingRepository;

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
		RoomView roomView = new RoomView();
		roomView.setRoomId(room.getId());
		roomView.setRoomName(room.getName());
		if(PresentationDetailsOption.FULL.equals(option))
		{
			List<Seat> seatsForRoom = seatRepository.findAllByRoom(room, Sort.by(Sort.Direction.ASC, "seatRow", "numberInSeatRow"));
			Map<Integer, Seat> presentationOccuppiedSeats = getOccuppiedSeatsMap(presentation);
			Map<Integer, SeatRowView> numberSeatRowMapping = createNumberSeatRowMapping(presentationOccuppiedSeats, seatsForRoom);
			roomView.setNumberSeatRowMapping(numberSeatRowMapping);
		}
		return roomView;
	}

	Map<Integer, Seat> getOccuppiedSeatsMap(Presentation presentation)
	{
		Map<Integer, Seat> occupiedSeatsMap = new HashMap<>();
		List<Booking> bookings4Presentation = bookingRepository.findAllByPresentation(presentation);
		for(Booking booking : bookings4Presentation)
		{
			for(Seat seat : booking.getSeats())
			{
				occupiedSeatsMap.put(seat.getId(), seat);
			}
		}
		return occupiedSeatsMap;
	}

	private Map<Integer, SeatRowView> createNumberSeatRowMapping(Map<Integer, Seat> presentationOccuppiedSeats, List<Seat> seatsForRoom)
	{
		Map<Integer, SeatRowView> numberSeatRowMapping = new LinkedHashMap<>();
		Optional<Integer> maxNumberInSeatRowOpt = seatsForRoom.stream().map(Seat::getNumberInSeatRow).max(Integer::compareTo);
		if(maxNumberInSeatRowOpt.isPresent())
		{
			for(Seat seat : seatsForRoom)
			{
				actualizeNumberSeatRowMapping(numberSeatRowMapping, seat, presentationOccuppiedSeats);
			}
		}
		return numberSeatRowMapping;
	}

	private void actualizeNumberSeatRowMapping(Map<Integer, SeatRowView> numberSeatRowMapping, Seat seat, Map<Integer, Seat> presentationOccuppiedSeats)
	{
		int currentSeatRow = seat.getSeatRow();
		boolean newRow = addNewSeatRowIfNew(numberSeatRowMapping, currentSeatRow);
		SeatRowView seatRowView = numberSeatRowMapping.get(currentSeatRow);
		if(newRow)
		{
			fillSeatRowWithNoneSeats(seatRowView, currentSeatRow, 1, seat.getNumberInSeatRow());
		}
		SeatView seatView = createSeatView(seat, presentationOccuppiedSeats);
		seatRowView.getSeatViews().add(seatView);
	}

	private boolean addNewSeatRowIfNew(Map<Integer, SeatRowView> numberSeatRowMapping, int currentSeatRow)
	{
		boolean newRow = false;
		if(!numberSeatRowMapping.containsKey(currentSeatRow))
		{
			SeatRowView seatRowView = new SeatRowView(new ArrayList<>());
			numberSeatRowMapping.put(currentSeatRow, seatRowView);
			newRow = true;
		}
		return newRow;
	}

	private void fillSeatRowWithNoneSeats(SeatRowView seatRowView, int currentSeatRow, int startIndex, int endIndex)
	{
		for(int i = startIndex; i < endIndex; i++)
		{
			SeatView seatView = new SeatView();
			seatView.setSeatRow(currentSeatRow);
			seatView.setNumberInSeatRow(i);
			seatView.setSeatType(SeatType.NONE);
			seatRowView.getSeatViews().add(seatView);
		}
	}

	private SeatView createSeatView(Seat seat, Map<Integer, Seat> presentationOccuppiedSeats)
	{
		SeatView seatView = new SeatView();
		seatView.setId(seat.getId());
		seatView.setSeatRow(seat.getSeatRow());
		seatView.setNumberInSeatRow(seat.getNumberInSeatRow());
		seatView.setSeatType(getSeatType(seat, presentationOccuppiedSeats));
		return seatView;
	}

	private SeatType getSeatType(Seat seat, Map<Integer, Seat> presentationOccuppiedSeats)
	{
		if(presentationOccuppiedSeats.containsKey(seat.getId()))
		{
			return SeatType.OCCUPPIED;
		}
		else
		{
			return SeatType.AVAILABLE;
		}
	}

	@Autowired
	public void setPresentationRepository(PresentationRepository presentationRepository)
	{
		this.presentationRepository = presentationRepository;
	}

	@Autowired
	public void setSeatRepository(SeatRepository seatRepository)
	{
		this.seatRepository = seatRepository;
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
