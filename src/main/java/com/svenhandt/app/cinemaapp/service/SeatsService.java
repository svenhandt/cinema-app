package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.view.SeatView;

import java.util.List;
import java.util.Map;


public interface SeatsService
{

	List<Seat> getSeats(Map<Integer, SeatView> seatViewMap);

}
