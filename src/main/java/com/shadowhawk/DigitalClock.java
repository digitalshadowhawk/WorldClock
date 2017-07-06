package com.shadowhawk;

import org.lwjgl.util.ReadableColor;

import net.minecraft.client.Minecraft;

public class DigitalClock extends Clock{
	private DigitalClockPanel mcPanel, sysPanel;
	
	public DigitalClock(int xPos, int yPos)
	{
		this.setPosition(xPos, yPos);
		this.setSize(64);
		
		this.mcPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.GREY, true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos + 25, size, ReadableColor.PURPLE, true);
	}
	
	public void render(Minecraft minecraft)
	{
		if(this.isVisible())
		{
			sysPanel.updateClock();
			mcPanel.updateClock(Minecraft.getMinecraft());
			
			if(LiteModWorldClock.instance.systemClock || LiteModWorldClock.instance.worldClock)
			{
				this.renderClock(minecraft);
			}
		}
	}
	
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
	
	@Override
	public void setSize(int size)
	{
		super.setSize(size);
		this.mcPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.GREY, true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getMinecraft(), xPos, yPos + 0.25F * size, this.size, ReadableColor.PURPLE, false);
	}
}
