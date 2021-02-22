package com.svenhandt.app.cinemaapp.dao;

import com.svenhandt.app.cinemaapp.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRepository extends JpaRepository<Film, Integer>
{

}
