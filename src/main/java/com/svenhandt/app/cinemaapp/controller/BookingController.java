package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.controller.Constants.ControllerConstants;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class BookingController
{

	private BookingService bookingService;

	private enum Action
	{
		ADD, REMOVE
	}

	@PostMapping("/booking/addSeat")
	public ResponseEntity<BookingView> addSeatToSessionBooking(@RequestBody String presentationSeatId,  HttpServletRequest httpServletRequest)
	{
		return addOrRemoveSeatAtSessionBooking(presentationSeatId, httpServletRequest, Action.ADD);
	}

	@PostMapping("/booking/removeSeat")
	public ResponseEntity<BookingView> removeSeatFromSessionBooking(@RequestBody String presentationSeatId,  HttpServletRequest httpServletRequest)
	{
		return addOrRemoveSeatAtSessionBooking(presentationSeatId, httpServletRequest, Action.REMOVE);
	}

	private ResponseEntity<BookingView> addOrRemoveSeatAtSessionBooking(String presentationSeatId,  HttpServletRequest httpServletRequest, Action action)
	{
		String[] presentationSeatIdArr = StringUtils.split(presentationSeatId, "_");

		// <presentationId>_<seatId>
		Validate.isTrue(presentationSeatIdArr.length == 2);

		int seatId = Integer.parseInt(presentationSeatIdArr[1]);
		HttpSession currentSession = httpServletRequest.getSession();
		Object bookingViewObj = currentSession.getAttribute(ControllerConstants.BOOKING_PRESENTATION_PREFIX + presentationSeatIdArr[0]);
		return addOrRemoveSeatAtSessionBooking(bookingViewObj, seatId, action);
	}


	private ResponseEntity<BookingView> addOrRemoveSeatAtSessionBooking(Object bookingViewObj, int seatId, Action action)
	{
		ResponseEntity<BookingView> result;
		if(bookingViewObj instanceof BookingView)
		{
			BookingView bookingView = (BookingView)bookingViewObj;
			if(action == Action.ADD)
			{
				bookingService.addSeatAndCalculate(bookingView, seatId);
			}
			else if(action == Action.REMOVE)
			{
				bookingService.removeSeatAndCalculate(bookingView, seatId);
			}
			result = ResponseEntity.ok(bookingView);
		}
		else
		{
			throw new IllegalStateException("No booking in session - invalid state");
		}
		return result;
	}

	@Autowired
	public void setBookingService(BookingService bookingService)
	{
		this.bookingService = bookingService;
	}
}
