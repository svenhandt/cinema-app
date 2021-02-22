package com.svenhandt.app.cinemaapp.view;

import java.util.Map;


public class RoomView
{
	private int roomId;
	private String roomName;
	private Map<Integer, SeatRowView> numberSeatRowMapping;

	public int getRoomId()
	{
		return roomId;
	}

	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}

	public String getRoomName()
	{
		return roomName;
	}

	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}

	public Map<Integer, SeatRowView> getNumberSeatRowMapping()
	{
		return numberSeatRowMapping;
	}

	public void setNumberSeatRowMapping(Map<Integer, SeatRowView> numberSeatRowMapping)
	{
		this.numberSeatRowMapping = numberSeatRowMapping;
	}
}
