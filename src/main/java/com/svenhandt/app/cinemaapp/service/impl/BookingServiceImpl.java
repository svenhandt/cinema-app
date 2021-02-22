package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService
{

	private PresentationRepository presentationRepository;

	private DataTypeConversionService dataTypeConversionService;

	@Override
	public BookingView createInitialBookingView(int presentationId)
	{
		BookingView bookingView = new BookingView();
		bookingView.setPresentationId(presentationId);
		bookingView.setTotalPrice(new BigDecimal(0));
		bookingView.setSeatIds(new ArrayList<>());
		return bookingView;
	}

	@Override
	public void addSeatAndCalculate(BookingView bookingView, int seatId)
	{
		bookingView.getSeatIds().add(seatId);
		calculate(bookingView);
	}

	@Override
	public void removeSeatAndCalculate(BookingView bookingView, int seatId)
	{
		List<Integer> seatIds = bookingView.getSeatIds();
		for(int i=0; i < seatIds.size(); i++)
		{
			if(seatIds.get(i) == seatId)
			{
				seatIds.remove(i);
			}
		}
		calculate(bookingView);
	}

	private void calculate(BookingView bookingView)
	{
		Optional<Presentation> presentationForBookingOpt = presentationRepository.findById(bookingView.getPresentationId());
		if(presentationForBookingOpt.isPresent())
		{
			Presentation presentationForBooking = presentationForBookingOpt.get();
			BigDecimal newBookingPrice = presentationForBooking.getPrice();
			newBookingPrice = newBookingPrice.multiply(new BigDecimal(bookingView.getSeatIds().size()));
			bookingView.setTotalPrice(newBookingPrice);
			if(bookingView.getSeatIds().size() > 0)
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
	public void setDataTypeConversionService(DataTypeConversionService dataTypeConversionService)
	{
		this.dataTypeConversionService = dataTypeConversionService;
	}
}
