package com.shadowhawk;

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

import java.util.Calendar;

import org.lwjgl.util.ReadableColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class WorldClockAMPMIndicator extends WorldClockBase{
	private int renderMode = 0;
	private final ReadableColor baseColor = ReadableColor.DKGREY;
	private ReadableColor activeColor;
	private ReadableColor color = baseColor;
	private ReadableColor inverseColor;
	private boolean isDay;
	private boolean isMinecraft;
	private Calendar calendar = null;
	private int size = 64;
	
	WorldClockAMPMIndicator(int x, int y, ReadableColor activeColor)
	{
		this.xPos = x;
		this.yPos = y;
		this.activeColor = activeColor;
		this.isMinecraft = true;
	}

	WorldClockAMPMIndicator(int x, int y, ReadableColor activeColor, Calendar calendar)
	{
		this.xPos = x;
		this.yPos = y;
		this.activeColor = activeColor;
		this.isMinecraft = false;
		this.calendar = calendar;
		this.isDay = isDay(calendar);
	}
	
	public void setPos(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public void render()
	{
		if(isMinecraft)
		{
			this.isDay = isDay(Minecraft.getMinecraft());
		} else {
			this.isDay = isDay(calendar);
		}
		float transparency = 1.0f;
		float inverseTransparency = 0.2f;
		if(this.isDay)
		{
			this.color = this.baseColor;
			this.inverseColor = this.activeColor;
			transparency = 0.2f;
			inverseTransparency = 1.0f;
		} else
		{
			this.color = this.activeColor;
			this.inverseColor = this.baseColor;
			transparency = 1.0f;
			inverseTransparency = 0.2f;
		}
		drawFilledCircle(xPos, yPos, 5f / 64 * size, color, transparency);
		if(renderMode == 0)
		{
			drawFilledCircle(getNextCoords()[0], getNextCoords()[1], 5f/ 64 * size, inverseColor, inverseTransparency);
		} else if(renderMode == 1)
		{
			drawFilledCircle(xPos + 10, yPos+5, 5f / 64 * size, inverseColor, inverseTransparency);
			drawFilledCircle(xPos - 10, yPos+10, 5f / 64 * size, inverseColor, inverseTransparency);
		}
	}
	
	public float[] getNextCoords()
	{
		float[] returnValue = {xPos, yPos + (15f/ 64 * size)};
		return returnValue;
	}
	
	public float[] getNextCoords(float xPos, float yPos)
	{
		float[] returnValue = {xPos, yPos + (15f/ 64 * size)};
		return returnValue;
	}
	
	/*
	 * Function that handles the drawing of a circle using the triangle fan
	 * method. This will create a filled circle.
	 *
	 * Params:
	 *	x (GLFloat) - the x position of the center point of the circle
	 *	y (GLFloat) - the y position of the center point of the circle
	 *	radius (GLFloat) - the radius that the painted circle will have
	 */
	public void drawFilledCircle(float x, float y, float radius, ReadableColor color, float transparency){
		int triangleAmount = 20; //# of triangles used to draw circle
		float processedTransparency = Math.min(1.0f, Math.max(0.0f, transparency));
		glDisableBlend();
        glDisableTexture2D();
        glDisableCulling();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), processedTransparency);
		
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
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
}
