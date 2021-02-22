package com.svenhandt.app.cinemaapp.dao;

import com.svenhandt.app.cinemaapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Integer>
{

	Room findByName(String name);

}
