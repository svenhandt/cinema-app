package com.svenhandt.app.cinemaapp.dao;

import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.entity.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, Integer>
{

	List<Seat> findAllByRoom(Room room, Sort sort);

}
