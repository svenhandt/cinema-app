package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.BookingRepository;
import com.svenhandt.app.cinemaapp.entity.Booking;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.*;
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

	private BookingRepository bookingRepository;

	private DataTypeConversionService dataTypeConversionService;

	private PresentationDetailsService presentationDetailsService;

	private CommonPresentationService commonPresentationService;

	private SeatsService seatsService;

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
		SeatView seatView = seatsService.getSeatViewForId(seatId);
		bookingView.getSeatsMap().put(seatId, seatView);
		calculate(bookingView);
	}

	@Override
	public void removeSeatAndCalculate(BookingView bookingView, int seatId)
	{
		Map<Integer, SeatView> seatViewsMap = bookingView.getSeatsMap();
		seatsService.removeSeatInView(seatViewsMap, seatId);
		calculate(bookingView);
	}

	@Override
	public int saveBooking(BookingView bookingView)
	{
		Booking booking = new Booking();
		booking.setName(bookingView.getName());
		booking.setCreditCardNo(bookingView.getCreditCardNo());
		Presentation presentation = commonPresentationService.getPresentation(bookingView.getId());
		List<Seat> seats = seatsService.getSeats(bookingView.getSeatsMap());
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
		bookingView.setSeatsMap(seatsService.getSeatViewsMap(booking.getSeats()));
		bookingView.setTotalPrice(booking.getTotalPrice());
		bookingView.setTotalPriceFormatted(dataTypeConversionService.getFormattedPrice(booking.getTotalPrice()));
		bookingView.setCreditCardNo(booking.getCreditCardNo());
		bookingView.setName(booking.getName());
		return bookingView;
	}

	private void calculate(BookingView bookingView)
	{
		PresentationView presentationView = bookingView.getPresentationView();
		Presentation presentationForBooking = commonPresentationService.getPresentation(presentationView.getId());
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

	@Autowired
	public void setCommonPresentationService(CommonPresentationService commonPresentationService)
	{
		this.commonPresentationService = commonPresentationService;
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

	@Autowired
	public void setSeatsService(SeatsService seatsService)
	{
		this.seatsService = seatsService;
	}
}
