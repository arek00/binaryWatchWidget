package com.example.binarywatchwidget;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider
{
	Bitmap bitmap;
	MyView myView;
	Canvas canvas;
	
	//CurrentDateProvider date;
	final int WIDGET_REFRESH_TIME = 1000;
	final String LOG_TAG = "TEST: ";
	final public String UPDATE_ACTION_NAME = "binaryclockwidget.update";
	
	//Paint paint;
	//CurrentDateProvider date;
	public void onUpdate(Context context, AppWidgetManager widgetManager, int[] appWidgetIds)
	{
		final int WIDGETS_NUMBER = appWidgetIds.length;
		Log.e(LOG_TAG,"onUpdate()");
		
		for(int i=0; i<WIDGETS_NUMBER;i++)
		{
				updateWidget(context, widgetManager, appWidgetIds[i]);
		}
	}
	
	
	
	@Override
	public void onEnabled(Context context)
	{
		super.onEnabled(context);
		//long delta;

		
		
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.SECOND, 1);
		//delta = c.getTimeInMillis()+10;
		
		Log.e(LOG_TAG,"onEnabled()");
		Log.e(LOG_TAG,"System curr milis: "+Long.toString(System.currentTimeMillis()));
		Log.e(LOG_TAG,"Calendar milis: "+Long.toString(c.MILLISECOND));
		
		alarm.setRepeating(AlarmManager.RTC, c.getTimeInMillis(), WIDGET_REFRESH_TIME, tick(context));
		
	}
	
	public void onDisabled(Context context)
	{		
		super.onDisabled(context);
		
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(tick(context));
		Log.e(LOG_TAG,"onDisabled()");
	}
	
	
	public PendingIntent tick(Context context)
	{
		
		Intent tmpInt = new Intent(UPDATE_ACTION_NAME);
		PendingIntent intent = PendingIntent.getBroadcast(context, 0, tmpInt, 0);
		
		return intent;
	}
	
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		
		Log.e(LOG_TAG,"onReceive()");
		
		if(UPDATE_ACTION_NAME.equals(intent.getAction()))
		{
			
			ComponentName component = new ComponentName(context.getPackageName(),getClass().getName());
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			
			int ids[] = appWidgetManager.getAppWidgetIds(component);
			
			for(int i=0;i<ids.length;i++)
				updateWidget(context, appWidgetManager,ids[i]);
		}
		
	}

	
	public void updateWidget(Context context, AppWidgetManager widgetManager, int id)
	{
		
		myView  = new MyView(context);

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();


		Point size = new Point();
		size.set(display.getHeight(), display.getWidth());

		bitmap = Bitmap.createBitmap(size.y, 150, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);	
			//myView  = new MyView(context);
			//bitmap = myView.getBitmap();
			//bitmap = Bitmap.createBitmap(300, 150, Bitmap.Config.ARGB_8888);
			//canvas = new Canvas(bitmap);
			//canvas = new Canvas(bitmap);
			//myView.date.refreshDate();
			myView.drawCanvas(canvas);
			
			
			//paint = new Paint();
			//paint.setColor(Color.YELLOW);
			//myView.drawCanvas(canvas);
			//myView.paint.setColor(Color.RED);
			//canvas.drawCircle(10, 10, 5, paint);
			//date = new CurrentDateProvider();
			//date.refreshDate();
			
			
			RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
			view.setImageViewBitmap(R.id.bitmap, bitmap);
			//view.setTextColor(R.id.txt, Color.RED);
			//view.setTextViewText(R.id.txt, date.dateToString("Dzi� jest: /d//M/y /h:/m"));
			
			widgetManager.updateAppWidget(id, view);
	
		}
		
	
	
}
