package com.svenhandt.app.cinemaapp.entity;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.List;


@Entity
@Table(name = "seat")
public class Seat
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "seat_row")
	private int seatRow;

	@Column(name = "number_in_seat_row")
	private int numberInSeatRow;

	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinTable(
			name = "booking_seat",
			joinColumns = @JoinColumn(name = "seat_id"),
			inverseJoinColumns = @JoinColumn(name = "booking_id")
	)
	private List<Booking> bookings;

	public Seat(int seatRow, int numberInSeatRow, Room room)
	{
		this.seatRow = seatRow;
		this.numberInSeatRow = numberInSeatRow;
		this.room = room;
	}

	public Seat()
	{

	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getSeatRow()
	{
		return seatRow;
	}

	public void setSeatRow(int seatRow)
	{
		this.seatRow = seatRow;
	}

	public int getNumberInSeatRow()
	{
		return numberInSeatRow;
	}

	public void setNumberInSeatRow(int numberInSeatRow)
	{
		this.numberInSeatRow = numberInSeatRow;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}
}
