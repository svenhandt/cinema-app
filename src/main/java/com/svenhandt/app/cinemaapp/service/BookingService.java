package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.commands.booking.CreateBookingCommand;
import com.svenhandt.app.cinemaapp.view.BookingView;


public interface BookingService
{

	BookingView createInitialSessionBookingView(int presentationId);

	int saveBooking(CreateBookingCommand createBookingCommand);

	BookingView getForBookingId(int bookingId);

}
