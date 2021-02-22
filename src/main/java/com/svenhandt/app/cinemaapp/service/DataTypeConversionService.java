package com.svenhandt.app.cinemaapp.service;

import java.math.BigDecimal;
import java.util.Date;


public interface DataTypeConversionService
{

	String getDayOfWeekAbbrev(Date date);

	String getTimeOfDayFormatted(Date date);

	int getDayOfWeek(String dayOfWeekStr);

	int getHourOfDay(String hourOfDayStr);

	int getMinute(String minuteStr);

	BigDecimal getPrice(String priceStr);

	String getFormattedPrice(BigDecimal price);

}
