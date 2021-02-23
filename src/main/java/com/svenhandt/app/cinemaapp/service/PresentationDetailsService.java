package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.enums.PresentationDetailsOption;
import com.svenhandt.app.cinemaapp.view.PresentationView;


public interface PresentationDetailsService
{

	PresentationView getPresentationDetails(int presentationId, PresentationDetailsOption option);

}
