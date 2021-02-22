package com.svenhandt.app.cinemaapp.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "booking")
public class Booking
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "credit_card_no")
	private String creditCardNo;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name = "presentation_id")
	private Presentation presentation;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinTable(
			name = "booking_seat",
			joinColumns = @JoinColumn(name = "booking_id"),
			inverseJoinColumns = @JoinColumn(name = "seat_id")
	)
	private List<Seat> seats;

	public Booking()
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreditCardNo()
	{
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo)
	{
		this.creditCardNo = creditCardNo;
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public Presentation getPresentation()
	{
		return presentation;
	}

	public void setPresentation(Presentation presentation)
	{
		this.presentation = presentation;
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
