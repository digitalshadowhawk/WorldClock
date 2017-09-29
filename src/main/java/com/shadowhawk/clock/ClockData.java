package com.shadowhawk.clock;

import java.util.Calendar;

import org.lwjgl.util.ReadableColor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class ClockData {
	protected float xPos;

	protected float scale;

	protected float yPos;
	
	protected boolean isMinecraft;
	protected Minecraft minecraft;
	protected ReadableColor color;
	
	protected int hour, minute, second;
	
	/**
	 * Common method to update times for system based clocks
	 */
	protected void updateTimes()
	{
		Calendar calendar = Calendar.getInstance();
    	hour   = calendar.get(Calendar.HOUR);
    	minute = calendar.get(Calendar.MINUTE);
    	second = calendar.get(Calendar.SECOND);
	}
	
	/**
	 * Common method to update times for in-game clocks
	 * @param minecraft
	 */
	protected void updateTimes(Minecraft minecraft)
	{
		long ticks = (minecraft.world.getWorldTime() + 6000) % 12000;
	    
    	hour   = MathHelper.floor(ticks * 0.001F);
    	minute = MathHelper.floor(ticks * 0.06F) % 60;
		second = MathHelper.floor(ticks * 3.6F) % 60;
	}
	
	/**
	 * Method to determine whether it's afternoon or not (system)
	 * @param calendar
	 * @return False if afternoon, true otherwise
	 */
	protected boolean isDay(Calendar calendar)
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
	
	/**
	 * Method to determine whether it's afternoon or not (in-game)
	 * @param minecraft
	 * @return False if afternoon, true otherwise
	 */
	protected boolean isDay(Minecraft minecraft)
	{
		long ticks = (minecraft.world.getWorldTime() + 6000) % 24000;
		if(MathHelper.floor(ticks * 0.001F)>= 12)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
