package com.example.binarywatchwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
//import android.widget.SlidingDrawer.OnDrawerCloseListener;

class MyView extends View {

	public String str;
	//private Canvas canvas = new Canvas();
	Paint paint = new Paint();
	Typeface visitor2 = Typeface.createFromAsset(getContext().getAssets(),"visitor2.ttf");
	
	
	Bitmap tile = BitmapFactory.decodeResource(getResources(),R.drawable.lights);
	Bitmap lightOff = Bitmap.createBitmap(tile, 2, 1, 36, 8);
	Bitmap lightOn = Bitmap.createBitmap(tile,2,11,36,8);
	CurrentDateProvider date = new CurrentDateProvider();
	int [] dateArray = date.getDateArray();
	
	
	public MyView(Context context)
	{
		super(context);		

	}
	
	
 public void drawCanvas(Canvas canvas)
 {
	//Paint paint = new Paint();
	 //invalidate();
	//canvas.setBitmap(outPutBitmap); 
	
	//paint.setColor(Color.BLACK);	
	//canvas.drawText(str, 50, 50, paint);
	date.refreshDate(); 
	paint.setTypeface(visitor2);
	str = date.dateToString("/d//M//y  /h-/m");
	
	//canvas.drawColor(Color.BLACK);
	/*	
	for(int i=0;i<dateArray.length;i++)
	{
		if(i==2) continue;
		
		drawWatch(canvas,10, 10+25*i,i);
	}*/
	
	
	
	drawWatch(canvas,10,10,CurrentDateProvider.HOUR);
	drawWatch(canvas,10,30,CurrentDateProvider.MINUTE);
	drawWatch(canvas,10,50,CurrentDateProvider.SECOND);
	
	/*
	//canvas.drawColor(Color.WHITE);
	paint.setColor(Color.WHITE);
	canvas.drawText(str, 10, 150, paint);
	str = "Display resolution: ";
	str += Integer.toString(this.getWidth());
	str+="x";
	str+=Integer.toString(this.getHeight());
	
	canvas.drawText(str, 10 ,175, paint);
	*/

 }
	

public void drawWatch(Canvas canvas, float x, float y, int it)
{
	invalidate();
	paint.setColor(Color.WHITE);
	//paint.set
	int dateTemp = dateArray[it];
	String temp = Integer.toBinaryString(dateTemp);
	int n = 1;
	int power;
	
	for(int i = date.binaryLength(dateTemp) - date.binaryLength(date.MAX_DATA_VALUES[it]); i<date.binaryLength(dateTemp); i++)
	{
		if(i<0) 
			canvas.drawBitmap(lightOff, x,y, paint);
		else
		{
		
		//x += lightOn.getWidth();
		if(temp.charAt(i) == '1')
			canvas.drawBitmap(lightOn, x, y, paint);
		else
			canvas.drawBitmap(lightOff, x,y, paint);
		
		}
		
		power = (int)Math.pow(2,date.binaryLength(date.MAX_DATA_VALUES[it]) - n);
		++n;
		canvas.drawText(Integer.toString(power), x+2, y+lightOn.getHeight()+5, paint);
		
			x += lightOn.getWidth();
	}
	
}




}
