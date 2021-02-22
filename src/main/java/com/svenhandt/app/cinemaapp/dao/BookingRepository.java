package com.svenhandt.app.cinemaapp.dao;

import com.svenhandt.app.cinemaapp.entity.Booking;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Integer>
{

	List<Booking> findAllByPresentation(Presentation presentation);

}
