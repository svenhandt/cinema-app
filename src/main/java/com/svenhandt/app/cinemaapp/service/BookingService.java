package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.view.BookingView;


public interface BookingService
{

	BookingView createInitialSessionBookingView(int presentationId);

	void addSeatAndCalculate(BookingView bookingView, int seatId);

	void removeSeatAndCalculate(BookingView bookingView, int seatId);

	int saveBooking(BookingView bookingView);

	BookingView getForBookingId(int bookingId);

}
