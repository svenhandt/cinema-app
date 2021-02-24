package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.controller.Constants.ControllerConstants;
import com.svenhandt.app.cinemaapp.forms.creditcardform.CreditCardForm;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


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

	@PostMapping("/booking/prepare")
	public String prepareBookingDataForm(HttpServletRequest httpServletRequest, Model model)
	{
		prepareBookingFormPage(httpServletRequest, model);
		model.addAttribute("creditCardForm", new CreditCardForm());
		return "booking-data-form";
	}

	@PostMapping("/booking/save")
	public String saveBooking(@Valid CreditCardForm creditCardForm, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model)
	{
		String targetPage = "";
		if(bindingResult.hasErrors())
		{
			prepareBookingFormPage(httpServletRequest, model);
			targetPage = "booking-data-form";
		}
		else
		{
			BookingView bookingView = getBookingViewFromRequestParam(httpServletRequest);
			bookingView.setName(creditCardForm.getCardName());
			bookingView.setCreditCardNo(bookingService.maskCreditCardNumber(creditCardForm.getCardNumber()));
		}
		return targetPage;
	}

	private void prepareBookingFormPage(HttpServletRequest httpServletRequest, Model model)
	{
		BookingView bookingView = getBookingViewFromRequestParam(httpServletRequest);
		model.addAttribute("currentBooking", bookingView);
	}

	private BookingView getBookingViewFromRequestParam(HttpServletRequest httpServletRequest)
	{
		BookingView bookingView;
		String bookingId = httpServletRequest.getParameter("bookingId");
		Validate.notNull(bookingId, "Booking id must not be null");
		HttpSession session = httpServletRequest.getSession();
		Object bookingViewObject = session.getAttribute(ControllerConstants.BOOKING_PRESENTATION_PREFIX + bookingId);
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

}
