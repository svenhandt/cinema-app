package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.SeatRepository;
import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.enums.SeatType;
import com.svenhandt.app.cinemaapp.service.SeatsService;
import com.svenhandt.app.cinemaapp.view.SeatRowView;
import com.svenhandt.app.cinemaapp.view.SeatView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SeatsServiceImpl implements SeatsService
{

	private SeatRepository seatRepository;



	@Override
	public List<Seat> findAllByRoomOrderByRowsAndNumbers(Room room)
	{
		return seatRepository.findAllByRoom(room, Sort.by(Sort.Direction.ASC, "seatRow", "numberInSeatRow"));
	}

	@Override
	public Seat findById(int seatId)
	{
		Optional<Seat> seatOpt = seatRepository.findById(seatId);
		Validate.isTrue(seatOpt.isPresent(), ApplicationConstants.NO_SEAT_FOR_ID + seatId);
		return seatOpt.get();
	}

	@Override
	public Map<Integer, SeatRowView> createNumberSeatRowMapping(Map<Integer, Seat> occuppiedSeats, List<Seat> seatsForRoom)
	{
		Map<Integer, SeatRowView> numberSeatRowMapping = new LinkedHashMap<>();
		Optional<Integer> maxNumberInSeatRowOpt = seatsForRoom.stream().map(Seat::getNumberInSeatRow).max(Integer::compareTo);
		if(maxNumberInSeatRowOpt.isPresent())
		{
			for(Seat seat : seatsForRoom)
			{
				actualizeNumberSeatRowMapping(numberSeatRowMapping, seat, occuppiedSeats);
			}
		}
		return numberSeatRowMapping;
	}

	@Override
	public List<Seat> getSeats(Map<Integer, SeatView> seatViewMap)
	{
		List<Seat> seats = new ArrayList<>();
		for(int seatId : seatViewMap.keySet())
		{
			Optional<Seat> seatOptional = seatRepository.findById(seatId);
			Validate.isTrue(seatOptional.isPresent(), ApplicationConstants.NO_SEAT_FOR_ID + seatId);
			seats.add(seatOptional.get());
		}
		return seats;
	}

	@Override
	public void removeSeatInView(Map<Integer, SeatView> seatViewsMap, int seatId)
	{
		if(seatViewsMap.containsKey(seatId))
		{
			seatViewsMap.remove(seatId);
		}
	}

	private void actualizeNumberSeatRowMapping(Map<Integer, SeatRowView> numberSeatRowMapping, Seat seat, Map<Integer, Seat> occuppiedSeats)
	{
		int currentSeatRow = seat.getSeatRow();
		boolean newRow = addNewSeatRowIfNew(numberSeatRowMapping, currentSeatRow);
		SeatRowView seatRowView = numberSeatRowMapping.get(currentSeatRow);
		if(newRow)
		{
			fillSeatRowWithNoneSeats(seatRowView, currentSeatRow, 1, seat.getNumberInSeatRow());
		}
		SeatView seatView = createSeatView(seat, occuppiedSeats);
		seatRowView.getSeatViews().add(seatView);
	}

	private boolean addNewSeatRowIfNew(Map<Integer, SeatRowView> numberSeatRowMapping, int currentSeatRow)
	{
		boolean newRow = false;
		if(!numberSeatRowMapping.containsKey(currentSeatRow))
		{
			SeatRowView seatRowView = new SeatRowView(new ArrayList<>());
			numberSeatRowMapping.put(currentSeatRow, seatRowView);
			newRow = true;
		}
		return newRow;
	}

	private void fillSeatRowWithNoneSeats(SeatRowView seatRowView, int currentSeatRow, int startIndex, int endIndex)
	{
		for(int i = startIndex; i < endIndex; i++)
		{
			SeatView seatView = new SeatView();
			seatView.setSeatRow(currentSeatRow);
			seatView.setNumberInSeatRow(i);
			seatView.setSeatType(SeatType.NONE);
			seatRowView.getSeatViews().add(seatView);
		}
	}

	private SeatView createSeatView(Seat seat, Map<Integer, Seat> occuppiedSeats)
	{
		SeatView seatView = new SeatView();
		seatView.setId(seat.getId());
		seatView.setSeatRow(seat.getSeatRow());
		seatView.setNumberInSeatRow(seat.getNumberInSeatRow());
		seatView.setSeatType(getSeatType(seat, occuppiedSeats));
		return seatView;
	}

	private SeatType getSeatType(Seat seat, Map<Integer, Seat> occuppiedSeats)
	{
		if(occuppiedSeats.containsKey(seat.getId()))
		{
			return SeatType.OCCUPPIED;
		}
		else
		{
			return SeatType.AVAILABLE;
		}
	}

	@Autowired
	public void setSeatRepository(SeatRepository seatRepository)
	{
		this.seatRepository = seatRepository;
	}
}
