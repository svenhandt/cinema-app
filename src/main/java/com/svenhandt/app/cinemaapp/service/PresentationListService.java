package com.svenhandt.app.cinemaapp.service;

import com.svenhandt.app.cinemaapp.view.FilmPresentationsMatrixView;

import java.util.Set;


public interface PresentationListService
{

	Set<FilmPresentationsMatrixView> createFilmsAndPresentationsView();

}
