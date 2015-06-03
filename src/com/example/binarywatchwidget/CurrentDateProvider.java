package com.example.binarywatchwidget;

import java.util.Calendar;

import android.graphics.BitmapFactory;

public class CurrentDateProvider {
	
	public final int HOUR_MAX = 23, MINUTE_MAX = 59, DAY_MAX = 31, MONTH_MAX = 12, YEAR_MAX = 2047, SECOND_MAX = 59; 
	
	Calendar calendar; 
	public static final int DAY = 0, MONTH = 1, YEAR = 2, HOUR = 3, MINUTE = 4, SECOND = 5;
	public final int[] MAX_DATA_VALUES = {DAY_MAX, MONTH_MAX, YEAR_MAX, HOUR_MAX, MINUTE_MAX, SECOND_MAX}; 
	private int[] dateConsts = {calendar.DAY_OF_MONTH, calendar.MONTH, calendar.YEAR, calendar.HOUR_OF_DAY, calendar.MINUTE, calendar.SECOND};
	private int[] dateArray = new int[6]; //day, month, year, hour, minute
	
			
			
	public CurrentDateProvider()
	{
		
		refreshDate();
		//godzina : minuta - dzien - miesiac - rok
		
 //miesi¹ce s¹ liczone od 0, styczen = 0, luty = 1 etc..
		
		/*
		this.minute = calendar.get(calendar.MINUTE);
		this.hour = calendar.get(calendar.HOUR_OF_DAY);
		this.day = calendar.get(calendar.DAY_OF_MONTH);
		this.month = calendar.get(calendar.MONTH)+1;
		this.year = calendar.get(calendar.YEAR);
		*/
	}
	
	
	//example of date's format
	//d-m-y h:m
	// /d - day, /m - month, /y - year, /h- hour, /n - minute
	
	
	public void refreshDate()
	{
		calendar = Calendar.getInstance();
		
		for(int i=0;i<dateArray.length;i++)
			dateArray[i] = calendar.get(dateConsts[i]);
		++dateArray[1];
		
	}
	
	public String dateToString(String format)
	{
		String dateString ="";
		
		for(int i=0;i<format.length();i++)
		{
		
			//Aktualna data: /d-/M-/y /h:/m
			
			switch(format.charAt(i))
			{
				case '/': 
							switch(format.charAt(i+1))
							{
								case 'd' : dateString += Integer.toString(dateArray[0]);break; 
								case 'M' : dateString += Integer.toString(dateArray[1]);break;
								case 'y' : dateString += Integer.toString(dateArray[2]);break;
								case 'h' : dateString += Integer.toString(dateArray[3]);break;
								case 'm' : dateString += Integer.toString(dateArray[4]);break;
								
								default: dateString += format.charAt(i); --i; break;
							}
							++i;
				break;			
							
				default: dateString += format.charAt(i);break;
			
			}
		}
		return dateString;
	}
	
	public int[] getDateArray()
	{
		
		return dateArray;
	}
	
	public int binaryLength(int a)
	{	
		int lenght = (int)Math.floor(Math.log10(a)/Math.log10(2)) + 1;
		if(a==0) lenght = 1;
		return lenght;
	}
}
