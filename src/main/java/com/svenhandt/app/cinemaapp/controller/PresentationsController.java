package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.service.BookingService;
import com.svenhandt.app.cinemaapp.service.PresentationDetailsService;
import com.svenhandt.app.cinemaapp.service.PresentationListService;
import com.svenhandt.app.cinemaapp.view.BookingView;
import com.svenhandt.app.cinemaapp.view.FilmPresentationsMatrixView;
import com.svenhandt.app.cinemaapp.view.PresentationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@CrossOrigin("${crossorigin.angular.http}")
@Controller
public class PresentationsController
{

	private PresentationListService presentationListService;

	private PresentationDetailsService presentationDetailsService;

	private BookingService bookingService;

	@GetMapping("/presentations")
	public ResponseEntity<Set<FilmPresentationsMatrixView>> getFilmsAndPresentationsPage()
	{
		Set<FilmPresentationsMatrixView> filmsWithPresentations = presentationListService.createFilmsAndPresentationsView();
		return ResponseEntity.ok(filmsWithPresentations);
	}

	@GetMapping("/presentationDetails")
	public ResponseEntity<BookingView> getPresentationDetailsPage(HttpServletRequest httpServletRequest)
	{
		BookingView presentationBookingView = null;
		String presentationIdStr = httpServletRequest.getParameter("id");
		if(presentationIdStr != null && presentationIdStr.matches("\\d+"))
		{
			int presentationId = Integer.parseInt(presentationIdStr);
			PresentationView presentationDetails = presentationDetailsService.getPresentationDetails(presentationId, PresentationDetailsOption.FULL);
			presentationBookingView = getBookingViewFromSession(presentationId, httpServletRequest);
			presentationBookingView.setPresentationView(presentationDetails);
		}
		return ResponseEntity.ok(presentationBookingView);
	}

	private BookingView getBookingViewFromSession(int presentationId, HttpServletRequest httpServletRequest)
	{
		BookingView bookingView = null;
		HttpSession currentSession = httpServletRequest.getSession();
		Object bookingViewObj = currentSession.getAttribute(ApplicationConstants.BOOKING_PRESENTATION_PREFIX + presentationId);
		if(bookingViewObj == null)
		{
			bookingView = bookingService.createInitialSessionBookingView(presentationId);
			currentSession.setAttribute(ApplicationConstants.BOOKING_PRESENTATION_PREFIX + presentationId, bookingView);
		}
		else if(bookingViewObj instanceof BookingView)
		{
			bookingView = (BookingView)bookingViewObj;
		}
		return bookingView;
	}

	@Autowired
	public void setPresentationListService(PresentationListService presentationListService)
	{
		this.presentationListService = presentationListService;
	}

	@Autowired
	public void setPresentationDetailsService(PresentationDetailsService presentationDetailsService)
	{
		this.presentationDetailsService = presentationDetailsService;
	}

	@Autowired
	public void setBookingService(BookingService bookingService)
	{
		this.bookingService = bookingService;
	}
}
