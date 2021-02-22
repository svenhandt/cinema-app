package com.svenhandt.app.cinemaapp.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "presentation")
public class Presentation
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name = "film_id")
	private Film film;

	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name = "room_id")
	private Room room;

	public Presentation()
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

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Film getFilm()
	{
		return film;
	}

	public void setFilm(Film film)
	{
		this.film = film;
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
