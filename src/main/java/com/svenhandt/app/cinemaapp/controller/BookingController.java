package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.entity.Booking;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin("${crossorigin.angular.http}")
@Controller
public class BookingController
{

	private BookingService bookingService;

	private enum Action
	{
		ADD, REMOVE
	}

	@PostMapping("/booking/save")
	public String saveBooking(@Valid CreditCardForm creditCardForm, BindingResult bindingResult,
			HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes)
	{
		String targetPage = "";
		if(bindingResult.hasErrors())
		{
			prepareBookingFormPage(httpServletRequest, model);
			targetPage = "booking-data-form";
		}
		else
		{
			int newBookingId = saveBooking(httpServletRequest, creditCardForm);
			redirectAttributes.addAttribute("bookingId", newBookingId);
			targetPage = "redirect:/booking/confirmation";
		}
		return targetPage;
	}

	@GetMapping("/booking/confirmation")
	public String bookingConfirmation(@RequestParam("bookingId") int bookingId, Model model)
	{
		BookingView bookingView = bookingService.getForBookingId(bookingId);
		model.addAttribute("booking", bookingView);
		return "booking-confirmation";
	}

	private void prepareBookingFormPage(HttpServletRequest httpServletRequest, Model model)
	{
		BookingView bookingView = getBookingViewFromRequestParam(httpServletRequest);
		model.addAttribute("currentBooking", bookingView);
	}

	private int saveBooking(HttpServletRequest httpServletRequest, CreditCardForm creditCardForm)
	{
		BookingView bookingView = getBookingViewFromRequestParam(httpServletRequest);
		bookingView.setName(creditCardForm.getCardName());
		bookingView.setCreditCardNo(StringUtils.substring(
				creditCardForm.getCardNumber(), 0, 4) + "************");
		int newBookingId = bookingService.saveBooking(bookingView);
		httpServletRequest.getSession().removeAttribute(ApplicationConstants.BOOKING_PRESENTATION_PREFIX + bookingView.getId());
		return newBookingId;
	}

	private BookingView getBookingViewFromRequestParam(HttpServletRequest httpServletRequest)
	{
		String bookingId = httpServletRequest.getParameter("bookingId");
		Validate.notNull(bookingId, "Booking id must not be null");
		HttpSession session = httpServletRequest.getSession();
		Object bookingViewObject = session.getAttribute(ApplicationConstants.BOOKING_PRESENTATION_PREFIX + bookingId);
		Validate.isTrue(bookingViewObject instanceof BookingView, ApplicationConstants.NO_BOOKING_IN_SESSION);
		return (BookingView)bookingViewObject;
	}

	@Autowired
	public void setBookingService(BookingService bookingService)
	{
		this.bookingService = bookingService;
	}

}
