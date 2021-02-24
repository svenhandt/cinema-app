package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.BookingRepository;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.dao.SeatRepository;
import com.svenhandt.app.cinemaapp.entity.Booking;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import com.svenhandt.app.cinemaapp.view.SeatView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class BookingServiceImpl implements BookingService
{

	private PresentationRepository presentationRepository;

	private SeatRepository seatRepository;

	private BookingRepository bookingRepository;

	private DataTypeConversionService dataTypeConversionService;

	private PresentationDetailsService presentationDetailsService;

	@Override
	public BookingView createInitialSessionBookingView(int presentationId)
	{
		BookingView bookingView = new BookingView();
		PresentationView presentationView = presentationDetailsService.getPresentationDetails(presentationId, PresentationDetailsOption.BASIC);
		bookingView.setPresentationView(presentationView);
		bookingView.setId(presentationId);
		bookingView.setTotalPrice(new BigDecimal(0));
		bookingView.setSeatsMap(new HashMap<>());
		return bookingView;
	}

	@Override
	public void addSeatAndCalculate(BookingView bookingView, int seatId)
	{
		Optional<Seat> seatOpt = seatRepository.findById(seatId);
		Validate.isTrue(seatOpt.isPresent(), ApplicationConstants.NO_SEAT_FOR_ID + seatId);
		Seat seat = seatOpt.get();
		SeatView seatView = new SeatView(seatId, seat.getSeatRow(), seat.getNumberInSeatRow());
		bookingView.getSeatsMap().put(seatId, seatView);
		calculate(bookingView);
	}

	@Override
	public void removeSeatAndCalculate(BookingView bookingView, int seatId)
	{
		Map<Integer, SeatView> seatViewsMap = bookingView.getSeatsMap();
		if(seatViewsMap.containsKey(seatId))
		{
			seatViewsMap.remove(seatId);
		}
		calculate(bookingView);
	}

	@Override
	public int saveBooking(BookingView bookingView)
	{
		Booking booking = new Booking();
		booking.setName(bookingView.getName());
		booking.setCreditCardNo(bookingView.getCreditCardNo());
		Presentation presentation = getPresentation(bookingView);
		List<Seat> seats = getSeats(bookingView);
		booking.setPresentation(presentation);
		booking.setSeats(seats);
		Validate.notNull(presentation.getPrice(), ApplicationConstants.PRESENTATION_MUST_HAVE_PRICE);
		booking.setTotalPrice(presentation.getPrice().multiply(new BigDecimal(seats.size())));
		bookingRepository.save(booking);
		return booking.getId();
	}

	@Override
	public BookingView getForBookingId(int bookingId)
	{
		Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
		Validate.isTrue(bookingOpt.isPresent(), ApplicationConstants.NO_BOOKING_FOR_ID + bookingId);
		Booking booking = bookingOpt.get();
		BookingView bookingView = new BookingView();
		bookingView.setId(bookingId);
		bookingView.setPresentationView(
				presentationDetailsService.getPresentationDetails(booking.getPresentation().getId(), PresentationDetailsOption.BASIC));

		return null;
	}



	private Presentation getPresentation(BookingView bookingView)
	{
		Optional<Presentation> presentationOpt = presentationRepository.findById(bookingView.getId());
		if(presentationOpt.isPresent())
		{
			return presentationOpt.get();
		}
		else
		{
			throw new IllegalStateException(ApplicationConstants.NO_PRESENTATION_FOR_ID + bookingView.getId());
		}
	}

	private List<Seat> getSeats(BookingView bookingView)
	{
		List<Seat> seats = new ArrayList<>();
		for(int seatId : bookingView.getSeatsMap().keySet())
		{
			Optional<Seat> seatOptional = seatRepository.findById(seatId);
			Validate.isTrue(seatOptional.isPresent(), ApplicationConstants.NO_SEAT_FOR_ID + seatId);
			seats.add(seatOptional.get());
		}
		return seats;
	}

	private void calculate(BookingView bookingView)
	{
		PresentationView presentationView = bookingView.getPresentationView();
		Optional<Presentation> presentationForBookingOpt = presentationRepository.findById(presentationView.getId());
		if(presentationForBookingOpt.isPresent())
		{
			Presentation presentationForBooking = presentationForBookingOpt.get();
			BigDecimal newBookingPrice = presentationForBooking.getPrice();
			newBookingPrice = newBookingPrice.multiply(new BigDecimal(bookingView.getSeatsMap().size()));
			bookingView.setTotalPrice(newBookingPrice);
			if(bookingView.getSeatsMap().size() > 0)
			{
				bookingView.setTotalPriceFormatted(dataTypeConversionService.getFormattedPrice(newBookingPrice));
			}
			else
			{
				bookingView.setTotalPriceFormatted(null);
			}
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
	public void setPresentationDetailsService(PresentationDetailsService presentationDetailsService)
	{
		this.presentationDetailsService = presentationDetailsService;
	}

	@Autowired
	public void setBookingRepository(BookingRepository bookingRepository)
	{
		this.bookingRepository = bookingRepository;
	}
}
