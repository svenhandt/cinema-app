package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.commands.booking.CreateBookingCommand;
import com.svenhandt.app.cinemaapp.enums.CreateBookingStatus;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import com.svenhandt.app.cinemaapp.view.CreateBookingResultView;

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

	@PostMapping("/booking/save")
	public ResponseEntity<CreateBookingResultView> saveBooking(@RequestBody @Valid CreateBookingCommand createBookingCommand, BindingResult bindingResult)
	{
		CreateBookingResultView createBookingResultView = new CreateBookingResultView();
		if(bindingResult.hasErrors())
		{
			createBookingResultView.setCreateBookingStatus(CreateBookingStatus.FORM_ERROR);
		}
		else
		{
			createBookingResultView.setCreateBookingStatus(CreateBookingStatus.OK);
			int bookingId = bookingService.saveBooking(createBookingCommand);
			createBookingResultView.setBookingId(bookingId);
		}
		return ResponseEntity.ok(createBookingResultView);
	}

	@GetMapping("/booking/confirmation")
	public ResponseEntity<BookingView> bookingConfirmation(@RequestParam("bookingId") int bookingId)
	{
		BookingView bookingView = bookingService.getForBookingId(bookingId);
		return ResponseEntity.ok(bookingView);
	}

	@Autowired
	public void setBookingService(BookingService bookingService)
	{
		this.bookingService = bookingService;
	}

}
