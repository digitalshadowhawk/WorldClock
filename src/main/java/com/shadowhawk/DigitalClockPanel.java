package com.shadowhawk;

import org.lwjgl.util.ReadableColor;

import net.minecraft.client.Minecraft;

public class DigitalClockPanel extends ClockData{
	private Digit[] time;
	private ReadableColor color;
	private boolean isMinecraft;
	private Minecraft minecraft;
	
	public DigitalClockPanel(Minecraft minecraft, float xPos, float yPos, int size, ReadableColor color, boolean isMinecraft)
	{
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = size;
		this.minecraft = minecraft;
		this.isMinecraft = isMinecraft;
		
		time = new Digit[] {
				new Digit(0, 2, xPos, yPos, size, color, true),
				new Digit(0, 10, xPos + 0.15F * size, yPos, size, color),
				new Digit(0, 6, xPos + 0.35F * size, yPos, size, color),
				new Digit(0, 10, xPos + 0.5F * size, yPos, size, color)};
		
		setSize();
	}
	
	public void render()
	{		
		if (isMinecraft)
		{
			updateClock(minecraft);
		}
		else
		{
			updateClock();
		}
		
		for(Digit digit : time)
		{
			digit.render();
		}
	}

	public void updateClock(Minecraft minecraft)
	{
		super.updateTimes(minecraft);
    	if(hour == 0)
    	{
    		hour = 12;
    	}
    	
    	time[0].setDigit((int)(hour/10));
    	time[1].setDigit((int)(hour%10));
    	time[2].setDigit((int)(minute/10));
    	time[3].setDigit((int)(minute%10));
	}
	
	public void updateClock() {
		super.updateTimes();
		if(hour == 0)
		{
    		hour = 12;
    	}
    	
    	//DEBUG DIGIT
    	//time[0].setDigit(8);
    	time[0].setDigit((int)(hour/10));
    	time[1].setDigit((int)(hour%10));
    	time[2].setDigit((int)(minute/10));
    	time[3].setDigit((int)(minute%10));
	}
	
	public void setSize()
	{
		this.time[0] = new Digit(0, 2, xPos, yPos, size, color, true);
		this.time[1] = new Digit(0, 10, xPos + 0.15F * size, yPos, size, color);
		this.time[2] = new Digit(0, 6, xPos + 0.35F * size, yPos, size, color);
		this.time[3] = new Digit(0, 10, xPos + 0.5F * size, yPos, size, color);
	}
}
