package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.view.SeatRowView;
import com.svenhandt.app.cinemaapp.view.SeatView;

import java.util.List;
import java.util.Map;


public interface SeatsService
{

	List<Seat> findAllByRoomOrderByRowsAndNumbers(Room room);

	Seat findById(int seatId);

	Map<Integer, SeatRowView> createNumberSeatRowMapping(Map<Integer, Seat> occuppiedSeats, List<Seat> seatsForRoom);

	List<Seat> getSeats(Map<Integer, SeatView> seatViewMap);

	void removeSeatInView(Map<Integer, SeatView> seatViewMap, int seatId);

}
