package com.shadowhawk;

import java.util.Calendar;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class ClockData {
	protected float xPos;

	int size;

	protected float yPos;
	
	protected boolean isMinecraft;
	protected Minecraft minecraft;
	
	protected int hour, minute, second;
	
	protected void updateTimes()
	{
		Calendar calendar = Calendar.getInstance();
    	hour   = calendar.get(Calendar.HOUR);
    	minute = calendar.get(Calendar.MINUTE);
    	second = calendar.get(Calendar.SECOND);
	}
	
	protected void updateTimes(Minecraft minecraft)
	{
		long ticks = (minecraft.world.getWorldTime() + 6000) % 12000;
	    
    	hour   = MathHelper.floor(ticks * 0.001F);
    	minute = MathHelper.floor(ticks * 0.06F) % 60;
		second = MathHelper.floor(ticks * 3.6F) % 60;
	}
	
	@SuppressWarnings("unused")
	private boolean isDay(Calendar calendar)
	{
		if(calendar.get(Calendar.HOUR)>= 12)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@SuppressWarnings("unused")
	private boolean isDay(Minecraft minecraft)
	{
		long ticks = (minecraft.world.getWorldTime() + 6000) % 24000;
		if(MathHelper.floor(ticks * 0.001F)>= 12)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
