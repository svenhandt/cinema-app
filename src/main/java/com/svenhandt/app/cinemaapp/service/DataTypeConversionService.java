package com.svenhandt.app.cinemaapp.service;

import java.math.BigDecimal;
import java.util.Date;


public interface DataTypeConversionService
{

	String getDayOfWeekAbbrev(Date date);

	String getTimeOfDayFormatted(Date date);

	Date getFromWeekAndDay(String dayOfWeekStr, String startTimeStr);

	BigDecimal getPrice(String priceStr);

}
