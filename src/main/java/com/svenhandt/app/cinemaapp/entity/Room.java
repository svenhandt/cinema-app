package com.svenhandt.app.cinemaapp.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "room")
public class Room
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "room",
			cascade = CascadeType.ALL)
	private List<Seat> seats;

	public Room()
	{

	}

	public Room(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Seat> getSeats()
	{
		return seats;
	}

	public void setSeats(List<Seat> seats)
	{
		this.seats = seats;
	}
}
