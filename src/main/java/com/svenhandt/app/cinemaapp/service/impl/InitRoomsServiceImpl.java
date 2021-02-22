package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.dao.RoomRepository;
import com.svenhandt.app.cinemaapp.dao.SeatRepository;
import com.svenhandt.app.cinemaapp.entity.Room;
import com.svenhandt.app.cinemaapp.entity.Seat;
import com.svenhandt.app.cinemaapp.service.InitRoomsService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class InitRoomsServiceImpl implements InitRoomsService
{

	private static final Logger LOG = LogManager.getLogger(InitRoomsServiceImpl.class);

	private RoomRepository roomRepository;

	private SeatRepository seatRepository;

	@Value("${initfiles.rooms.dir.path}")
	private String initRoomsFilesDirPath;

	@Value("${initfiles.rooms.seat.symbol}")
	private String initFilesSeatSymbol;

	@Override
	public void initialize()
	{
		Set<String> fileNames = listFilesUsingFileWalk();
		if(fileNames != null)
		{
			for (String fileName : fileNames)
			{
				initRoom(fileName);
			}
		}
	}

	private Set<String> listFilesUsingFileWalk()
	{
		Set<String> fileNames;
		Stream<Path> stream = null;
		try
		{
			URL fileUrl = getClass().getClassLoader().getResource(initRoomsFilesDirPath);
			URI fileUri = fileUrl.toURI();
			stream = Files.walk(Paths.get(fileUri));
			fileNames = stream
					.filter(file -> !Files.isDirectory(file))
					.map(Path::getFileName)
					.map(Path::toString)
					.collect(Collectors.toSet());
		}
		catch (URISyntaxException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			if(stream != null)
			{
				stream.close();
			}
		}
		return fileNames;
	}

	private void initRoom(String fileName)
	{
		Room room = createRoom(fileName);
		createSeats(room, fileName);
	}

	private Room createRoom(String fileName)
	{
		String roomName = StringUtils.capitalize(
				fileName.replaceAll(".txt", StringUtils.EMPTY).replaceAll("_", " "));
		Room room = new Room(roomName);
		LOG.info("create room " + roomName);
		roomRepository.save(room);
		return room;
	}

	private void createSeats(Room room, String fileName)
	{
		BufferedReader reader = null;
		try
		{
			URL fileUrl = getClass().getClassLoader().getResource(initRoomsFilesDirPath + "/" + fileName);
			URI fileUri = fileUrl.toURI();
			Path path = Paths.get(fileUri);
			reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
			String currentLine;
			int currentSeatRow = 1;
			while ((currentLine = reader.readLine()) != null)
			{
				createSeatRow(room, currentSeatRow, currentLine);
				currentSeatRow++;
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

	private void createSeatRow(Room room, int seatRow, String seatLineStr)
	{
		for(int i=0; i < seatLineStr.length(); i++)
		{
			if(seatLineStr.charAt(i) == initFilesSeatSymbol.charAt(0))
			{
				LOG.info("Create Seat " + seatRow + "_" + (i+1) + " in room " + room.getName());
				Seat seat = new Seat(seatRow, i + 1, room);
				seatRepository.save(seat);
			}
		}
	}

	@Autowired
	public void setRoomRepository(RoomRepository roomRepository)
	{
		this.roomRepository = roomRepository;
	}

	@Autowired
	public void setSeatRepository(SeatRepository seatRepository)
	{
		this.seatRepository = seatRepository;
	}

}
