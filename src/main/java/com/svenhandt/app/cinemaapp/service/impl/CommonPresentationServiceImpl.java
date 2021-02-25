package com.svenhandt.app.cinemaapp.service.impl;

import com.svenhandt.app.cinemaapp.constants.ApplicationConstants;
import com.svenhandt.app.cinemaapp.dao.PresentationRepository;
import com.svenhandt.app.cinemaapp.entity.Presentation;
import com.svenhandt.app.cinemaapp.service.CommonPresentationService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CommonPresentationServiceImpl implements CommonPresentationService
{

	private PresentationRepository presentationRepository;

	@Override
	public Presentation getPresentation(int id)
	{
		Optional<Presentation> presentationOpt = presentationRepository.findById(id);
		Validate.isTrue(presentationOpt.isPresent(), ApplicationConstants.NO_PRESENTATION_FOR_ID + id);
		return presentationOpt.get();
	}

	@Autowired
	public void setPresentationRepository(PresentationRepository presentationRepository)
	{
		this.presentationRepository = presentationRepository;
	}
}
