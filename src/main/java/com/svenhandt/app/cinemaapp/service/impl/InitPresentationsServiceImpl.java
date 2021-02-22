package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.FilmRepository;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.dao.RoomRepository;
import com.svenhandt.app.cinemaapp.entity.Film;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.service.DataTypeConversionService;
import com.svenhandt.app.cinemaapp.service.InitPresentationsService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class InitPresentationsServiceImpl implements InitPresentationsService
{

	private static final Logger LOG = LogManager.getLogger(InitPresentationsServiceImpl.class);

	private static final String SEMICOLON = ";";
	private static final String SLASH = "/";
	private static final String DATA_LINE_INCORRECT_FORMAT = "data line has not the correct format: ";
	private static final String COMMA = ",";
	private static final int PRESENTATION_DATA_ARR_LENGTH = 4;

	private RoomRepository roomRepository;

	private FilmRepository filmRepository;

	private PresentationRepository presentationRepository;

	private DataTypeConversionService dataTypeConversionService;

	@Value("${initfiles.presentations.file.path}")
	private String initPresentationsFilePath;

	@Override
	public void initialize()
	{
		BufferedReader reader = null;
		try
		{
			URL fileUrl = getClass().getClassLoader().getResource(initPresentationsFilePath);
			URI fileUri = fileUrl.toURI();
			Path path = Paths.get(fileUri);
			reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
			String currentLine;
			while ((currentLine = reader.readLine()) != null)
			{
				createFilmAndItsPresentations(currentLine);
			}
		}
		catch (IOException | URISyntaxException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			IOUtils.closeQuietly(reader);
		}
	}

	private void createFilmAndItsPresentations(String dataLine)
	{
		try
		{
			Validate.isTrue(StringUtils.contains(dataLine, SEMICOLON), DATA_LINE_INCORRECT_FORMAT);
			int semicolonIndex = StringUtils.indexOf(dataLine, SEMICOLON);
			Validate.isTrue(semicolonIndex < (StringUtils.length(dataLine) - 1));
			String filmName = StringUtils.substring(dataLine, 0, semicolonIndex);
			Film film = createFilm(filmName);
			String presentationsDataLine = StringUtils.substring(dataLine, semicolonIndex + 1, StringUtils.length(dataLine));
			List<Presentation> presentations = createPresentations(presentationsDataLine);

			// now as the data line was correct and entities are created, they can be persisted
			save(film, presentations);
		}
		catch(IllegalArgumentException ex)
		{
			LOG.error("Data line " + dataLine + " could not be imported: " + dataLine, ex);
		}
	}

	private void save(Film film, List<Presentation> presentations)
	{
		LOG.info("Creating film: " + film.getTitle());
		filmRepository.save(film);
		for(Presentation presentation : presentations)
		{
			LOG.info("Create presentation for film: " + film.getTitle());
			presentation.setFilm(film);
			presentationRepository.save(presentation);
		}
	}

	private Film createFilm(String filmName)
	{
		Validate.notBlank(filmName, "Film name must not be empty.");
		Film film = new Film(filmName);
		return film;
	}

	private List<Presentation> createPresentations(String presentationsDataLine)
	{
		List<Presentation> presentations = new ArrayList<>();
		String[] presentationsDataArr = StringUtils.split(presentationsDataLine, COMMA);
		for(String lineForOnePresentation : presentationsDataArr)
		{
			String[] presentationDataArr = StringUtils.split(lineForOnePresentation, SLASH);
			Validate.isTrue(presentationDataArr.length == PRESENTATION_DATA_ARR_LENGTH, DATA_LINE_INCORRECT_FORMAT);
			String dayOfWeekStr = presentationDataArr[0];
			String startTimeStr = presentationDataArr[1];
			String roomName = presentationDataArr[2];
			String priceStr = presentationDataArr[3];
			Presentation presentation = createPresentation(dayOfWeekStr, startTimeStr, roomName, priceStr);
			presentations.add(presentation);
		}
		return presentations;
	}

	private Presentation createPresentation(String dayOfWeekStr, String startTimeStr, String roomName, String priceStr)
	{
		Presentation presentation = new Presentation();
		Room room = roomRepository.findByName(roomName);
		presentation.setRoom(room);
		presentation.setStartTime(getPresentationStartTime(dayOfWeekStr, startTimeStr));
		presentation.setPrice(dataTypeConversionService.getPrice(priceStr));
		return presentation;
	}

	private Date getPresentationStartTime(String dayOfWeekStr, String startTimeStr)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, dataTypeConversionService.getDayOfWeek(dayOfWeekStr));
		String[] startTimeArr = StringUtils.split(startTimeStr, ":");
		Validate.isTrue(startTimeArr.length == 2, "Start time format invalid: " + startTimeStr);
		calendar.set(Calendar.HOUR_OF_DAY, dataTypeConversionService.getHourOfDay(startTimeArr[0]));
		calendar.set(Calendar.MINUTE, dataTypeConversionService.getMinute(startTimeArr[1]));
		return calendar.getTime();
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
	public void setDataTypeConversionService(DataTypeConversionService dataTypeConversionService)
	{
		this.dataTypeConversionService = dataTypeConversionService;
	}
}
