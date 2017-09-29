package com.shadowhawk.clock;

import net.minecraft.client.Minecraft;

public class Clock {

	public Clock(float x, float y)
	{
		this.setPosition(xPos, yPos);
		this.setSize(64);
	}
	
	protected float xPos, yPos;

	/**
	 * Size and position for the clock
	 */
	protected float size;
	
	protected Clock nextClock = null;
	public float[] nextClockCoords = {10, 10};
	
	/**
	 * Whether the clock is currently visible
	 */
	protected boolean visible = true;
	
	/**
	 * Get the current size
	 */
	public float getSize()
	{
		return this.size;
	}
	
	/**
	 * Get whether the clock is currently visible
	 */
	public boolean isVisible()
	{
		return this.visible;
	}
	
	/**
	 * Set the location of the top left corner of the clock
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public void setPosition(float xPos, float yPos)
	{
		this.xPos = Math.max(10,  xPos);
		this.yPos = Math.max(10,  yPos);
	}
	
	/**
	 * Set the size of the clock
	 * 
	 * @param size new size (min is 32)
	 */
	public void setSize(float size)
	{
		this.size  = Math.max(32, size);
	}
	
	/**
	 * Set whether the clock should be visible
	 * @param visible new visibility setting
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/**
	 * Placeholder method, must overwrite
	 * @param minecraft
	 */
	public void render(Minecraft minecraft)
	{
		
	}
	
	public void updateNextClockCoords()
	{
		if(nextClock != null)
		{
			nextClock.xPos = nextClockCoords[0];
			nextClock.yPos = nextClockCoords[1];
		}
	}
}
