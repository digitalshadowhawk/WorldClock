package com.shadowhawk.clock.digital;

import org.lwjgl.util.ReadableColor;

import com.shadowhawk.clock.ClockData;

import static com.mumfrey.liteloader.gl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.mumfrey.liteloader.gl.GL.GL_SRC_ALPHA;
import static com.mumfrey.liteloader.gl.GL.GL_TRIANGLE_FAN;
import static com.mumfrey.liteloader.gl.GL.glBlendFunc;
import static com.mumfrey.liteloader.gl.GL.glColor4f;
import static com.mumfrey.liteloader.gl.GL.glDisableBlend;
import static com.mumfrey.liteloader.gl.GL.glDisableCulling;
import static com.mumfrey.liteloader.gl.GL.glDisableTexture2D;
import static com.mumfrey.liteloader.gl.GL.glEnableCulling;
import static com.mumfrey.liteloader.gl.GL.glEnableTexture2D;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class DigitalClockPanel extends ClockData{
	/**
	 * the array of Digits representing the time
	 */
	private Digit[] time;
	
	/**
	 * 
	 * @param minecraft
	 * @param xPos X-coordinate for the top left corner of the clock
	 * @param yPos Y-coordinate for the top left corner of the clock
	 * @param scale
	 * @param color Color of the entire clock
	 * @param isMinecraft Determines whether to check minecraft world time or system clock
	 */
	public DigitalClockPanel(Minecraft minecraft, float xPos, float yPos, float scale, ReadableColor color, boolean isMinecraft)
	{
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.scale = scale;
		this.minecraft = minecraft;
		this.isMinecraft = isMinecraft;
		
		time = new Digit[] {
				new Digit(0, 2, xPos, yPos, scale, color, true),
				new Digit(0, 10, xPos + 0.18F * scale, yPos, scale, color),
				new Digit(0, 6, xPos + 0.42F * scale, yPos, scale, color),
				new Digit(0, 10, xPos + 0.60F * scale, yPos, scale, color)};
		
		setScale(this.scale);
	}
	
	/**
	 * Updates the clock then renders the entire clock panel
	 */
	public void render()
	{		
		if (isMinecraft)
		{
			updateTimes(minecraft);
		}
		else
		{
			updateTimes();
		}
		
		for(Digit digit : time)
		{
			digit.render();
		}
		drawSemi();
	}
	
	public void drawSemi()
	{
		drawFilledCircle(xPos + 0.38F * scale, yPos + 0.07F * scale, 0.02F * scale, color, 1.0F);
		drawFilledCircle(xPos + 0.38F * scale, yPos + 0.21F * scale, 0.02F * scale, color, 1.0F);
	}
	
	public void drawFilledCircle(float x, float y, float radius, ReadableColor color, float transparency){
		int triangleAmount = 20; //# of triangles used to draw circle
		float processedTransparency = Math.min(1.0f, Math.max(0.0f, transparency));
		glDisableBlend();
        glDisableTexture2D();
        glDisableCulling();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), processedTransparency);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexBuffer = tessellator.getBuffer();
		float twicePi = (float) (2.0f * Math.PI);
		
		vertexBuffer.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
			for(int i = 0; i <= triangleAmount;i++) { 
				float xBuffer = (float) (x + (radius * Math.cos(i *  twicePi / triangleAmount)));
				float yBuffer = (float) (y + (radius * Math.sin(i * twicePi / triangleAmount)));
				vertexBuffer.pos(xBuffer, yBuffer, 0).endVertex();//.tex(u  * texMapScale, v  * texMapScale).endVertex();
			}
		tessellator.draw();
		
		glEnableCulling();
        glEnableTexture2D();
	}

	/**
	 * Updates the clock with the minecraft world time
	 * @param minecraft
	 */
	public void updateTimes(Minecraft minecraft)
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
	
	/**
	 * Updates the clock with the system time
	 */
	public void updateTimes() {
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
	
	/**
	 * Sets the overall size of the clock
	 * @param scale Size of the clock
	 */
	public void setScale(float scale)
	{
		this.time[0] = new Digit(0, 2, xPos, yPos, scale, color, true);
		this.time[1] = new Digit(0, 10, xPos + 0.18F * scale, yPos, scale, color);
		this.time[2] = new Digit(0, 6, xPos + 0.42F * scale, yPos, scale, color);
		this.time[3] = new Digit(0, 10, xPos + 0.60F * scale, yPos, scale, color);
	}
}
