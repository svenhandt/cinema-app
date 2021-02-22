package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.BookingRepository;
import com.svenhandt.app.cinemaapp.dao.FilmRepository;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.dao.RoomRepository;
import com.svenhandt.app.cinemaapp.service.InitDataBaseService;
import com.svenhandt.app.cinemaapp.service.InitPresentationsService;
import com.svenhandt.app.cinemaapp.service.InitRoomsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InitDataBaseServiceImpl implements InitDataBaseService
{

	private static final Logger LOG = LogManager.getLogger(InitDataBaseServiceImpl.class);

	private BookingRepository bookingRepository;

	private FilmRepository filmRepository;

	private PresentationRepository presentationRepository;

	private RoomRepository roomRepository;

	private InitRoomsService initRoomsService;

	private InitPresentationsService initPresentationsService;

	@Override
	public void initialize()
	{
		LOG.info("begin initialization");
		bookingRepository.deleteAll();
		presentationRepository.deleteAll();
		filmRepository.deleteAll();
		roomRepository.deleteAll();
		initRoomsService.initialize();
		initPresentationsService.initialize();
		LOG.info("end initialization");
	}

	@Autowired
	public void setBookingRepository(BookingRepository bookingRepository)
	{
		this.bookingRepository = bookingRepository;
	}

	@Autowired
	public void setFilmRepository(FilmRepository filmRepository)
	{
		this.filmRepository = filmRepository;
	}

	@Autowired
	public void setPresentationRepository(PresentationRepository presentationRepository)
	{
		this.presentationRepository = presentationRepository;
	}

	@Autowired
	public void setRoomRepository(RoomRepository roomRepository)
	{
		this.roomRepository = roomRepository;
	}

	@Autowired
	public void setInitRoomsService(InitRoomsService initRoomsService)
	{
		this.initRoomsService = initRoomsService;
	}

	@Autowired
	public void setInitPresentationsService(InitPresentationsService initPresentationsService)
	{
		this.initPresentationsService = initPresentationsService;
	}
}
