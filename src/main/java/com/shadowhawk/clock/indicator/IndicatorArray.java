package com.shadowhawk.clock.indicator;

import org.lwjgl.util.ReadableColor;

import com.shadowhawk.clock.Clock;

import net.minecraft.client.Minecraft;

public class IndicatorArray extends Clock{
	
	Indicator mcInd, sysInd;
	
	public IndicatorArray(float xPos, float yPos)
	{
		super(xPos, yPos);
		mcInd = new Indicator(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.GREY, true);
		sysInd = new Indicator(Minecraft.getMinecraft(), xPos, yPos + 0.32F * size, size, ReadableColor.PURPLE, false);
	}
	
	public void render(Minecraft minecraft)
	{
		mcInd.render();
		sysInd.render();
	}
	
	public void setSize(float scale)
	{
		super.setSize(scale);
		mcInd = new Indicator(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.GREY, true);
		sysInd = new Indicator(Minecraft.getMinecraft(), xPos, yPos + 0.32F * size, size, ReadableColor.PURPLE, false);
	}
}
