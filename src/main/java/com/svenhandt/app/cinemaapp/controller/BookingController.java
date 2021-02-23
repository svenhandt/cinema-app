package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.controller.Constants.ControllerConstants;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class BookingController
{

	private BookingService bookingService;

	private PresentationDetailsService presentationDetailsService;

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

	@PostMapping("/booking/prepare")
	public String prepareBookingDataForm(HttpServletRequest httpServletRequest, Model model)
	{
		BookingView bookingView = getBookingViewFromRequestParam(httpServletRequest);
		PresentationView presentationView = presentationDetailsService.getPresentationDetails(bookingView.getPresentationId(), PresentationDetailsOption.BASIC);
		return "";
	}

	private BookingView getBookingViewFromRequestParam(HttpServletRequest httpServletRequest)
	{
		BookingView bookingView;
		String bookingId = httpServletRequest.getParameter("bookingId");
		Validate.notNull(bookingId, "Booking id must not be null");
		HttpSession session = httpServletRequest.getSession();
		Object bookingViewObject = session.getAttribute(bookingId);
		if(bookingViewObject instanceof BookingView)
		{
			bookingView = (BookingView)bookingViewObject;
		}
		else
		{
			throw new IllegalStateException("No booking in session - invalid state");
		}
		return bookingView;
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

	@Autowired
	public void setPresentationDetailsService(PresentationDetailsService presentationDetailsService)
	{
		this.presentationDetailsService = presentationDetailsService;
	}
}
