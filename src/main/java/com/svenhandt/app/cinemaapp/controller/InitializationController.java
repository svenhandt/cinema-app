package com.svenhandt.app.cinemaapp.controller;

import com.svenhandt.app.cinemaapp.service.InitDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/init")
public class InitializationController
{

	@Autowired
	private InitDataBaseService initDataBaseService;

	@PostMapping
	public String initData()
	{
		initDataBaseService.initialize();
		return "System initialized";
	}

}
