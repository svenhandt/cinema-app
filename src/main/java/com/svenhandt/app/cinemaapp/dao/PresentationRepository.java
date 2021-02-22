package com.svenhandt.app.cinemaapp.dao;

import com.svenhandt.app.cinemaapp.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationRepository extends JpaRepository<Presentation, Integer>
{

}
