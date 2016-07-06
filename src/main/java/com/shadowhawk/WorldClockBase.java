package com.shadowhawk;

public class WorldClockBase {

	/**
	 * Size and position for the clock
	 */
	protected int xPos, yPos, size;
	
	/**
	 * Whether the clock is currently visible
	 */
	protected boolean visible = true;
	
	/**
	 * Get the current size
	 */
	public int getSize()
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
	 * @param xPos
	 * @param yPos
	 */
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = Math.max(0,  xPos);
		this.yPos = Math.max(0,  yPos);
	}
	
	/**
	 * Set the size of the clock
	 * 
	 * @param size new size (min is 32)
	 */
	public void setSize(int size)
	{
		this.size  = Math.max(32, size);
	}
	
	/**
	 * Set whether the clock should be visible
	 * 
	 * @param visible new visibility setting
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
}
