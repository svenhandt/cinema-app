package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.dao.SeatRepository;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import com.svenhandt.app.cinemaapp.view.SeatView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class BookingServiceImpl implements BookingService
{

	private PresentationRepository presentationRepository;

	private SeatRepository seatRepository;

	private DataTypeConversionService dataTypeConversionService;

	private PresentationDetailsService presentationDetailsService;

	@Override
	public BookingView createInitialBookingView(int presentationId)
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
		if(seatOpt.isPresent())
		{
			Seat seat = seatOpt.get();
			SeatView seatView = new SeatView(seatId, seat.getSeatRow(), seat.getNumberInSeatRow());
			bookingView.getSeatsMap().put(seatId, seatView);
		}
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
}
