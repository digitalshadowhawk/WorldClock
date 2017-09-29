package com.shadowhawk.clock.digital;

import org.lwjgl.util.ReadableColor;

import com.shadowhawk.clock.Clock;
import com.shadowhawk.clock.LiteModWorldClock;
import com.shadowhawk.clock.indicator.IndicatorArray;

import net.minecraft.client.Minecraft;

public class DigitalClock extends Clock{
	private DigitalClockPanel mcPanel, sysPanel;
	
	/**
	 * An object containing up to two digital clocks
	 * @param xPos X-coordinate for the top left of the group of clocks
	 * @param yPos Y-coordinate for the top left of the group of clocks
	 */
	public DigitalClock(int xPos, int yPos)
	{
		super(xPos, yPos);
		this.setSize(64);
		
		this.mcPanel = new DigitalClockPanel(Minecraft.getMinecraft(), this.xPos, this.yPos + 10, size, ReadableColor.GREY, true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getMinecraft(), this.xPos, this.yPos + 10 + 0.32F * size, size, ReadableColor.PURPLE, true);
		this.nextClock = new IndicatorArray(nextClockCoords[0], nextClockCoords[1]);
		System.out.println("Digital Clock Instantiation finished: (" + xPos + ", " + yPos + ") ");
	}
	
	/**
	 * Update the clocks and check if they're visible, and if so, attempt to render them
	 * @param minecraft
	 */
	public void render(Minecraft minecraft)
	{
		if(this.isVisible())
		{
			System.out.print("Render the digital cock (ln 38): (" + xPos + ", " + yPos + ") ");
			sysPanel.updateTimes();
			//mcPanel.updateTimes(Minecraft.getMinecraft());
			mcPanel.updateTimes(minecraft); //TODO
			if(LiteModWorldClock.instance.systemClock || LiteModWorldClock.instance.worldClock)
			{
				this.renderClock(minecraft);
				if(nextClock != null)
				{
					nextClock.render(minecraft);
				}
			}
		}
	}
	
	/**
	 * Renders each clock if possible
	 * @param minecraft
	 */
	private void renderClock(Minecraft minecraft)
	{
		if(LiteModWorldClock.instance.systemClock)
		{
			sysPanel.render();
		}
		if(LiteModWorldClock.instance.worldClock)
		{
			mcPanel.render();
		}
	}
	
	/**
	 * Resizes the clocks
	 * @param scale 
	 */
	@Override
	public void setSize(float scale)
	{
		super.setSize(scale);
		this.nextClockCoords[0] = xPos + 0.78F * size;
		this.nextClockCoords[1] = yPos;
		updateNextClockCoords();
		if(nextClock != null)
		{
			nextClock.setSize(scale);
		}
		this.mcPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos, this.size, ReadableColor.GREY, true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos + 0.32F * size, this.size, ReadableColor.PURPLE, false);
	}
}
