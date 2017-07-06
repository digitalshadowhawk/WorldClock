package com.shadowhawk.clock.digital;

import static com.mumfrey.liteloader.gl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.mumfrey.liteloader.gl.GL.GL_POLYGON;
import static com.mumfrey.liteloader.gl.GL.GL_SRC_ALPHA;
import static com.mumfrey.liteloader.gl.GL.glBlendFunc;
import static com.mumfrey.liteloader.gl.GL.glColor4f;
import static com.mumfrey.liteloader.gl.GL.glDisableBlend;
import static com.mumfrey.liteloader.gl.GL.glDisableCulling;
import static com.mumfrey.liteloader.gl.GL.glDisableTexture2D;
import static com.mumfrey.liteloader.gl.GL.glEnableCulling;
import static com.mumfrey.liteloader.gl.GL.glEnableTexture2D;
import static com.mumfrey.liteloader.gl.GL.glPopMatrix;
import static com.mumfrey.liteloader.gl.GL.glPushMatrix;
import static com.mumfrey.liteloader.gl.GL.glTranslatef;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION;

import org.lwjgl.util.ReadableColor;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;

public class Digit {
	private int number;
	private int max = 6;
	private float xPos;
	private float yPos;
	private int scale;
	private ReadableColor color;
	private boolean hideZero = false;
	
	public Digit(int digit, int max, float x, float y, int scale, ReadableColor color)
	{
		number = digit;
		max = Math.min(10, max);
		xPos = x;
		yPos = y;
		this.scale = scale;
		this.color = color;
	}
	
	public Digit(int digit, int max, float x, float y, int scale, ReadableColor color, boolean hideZero)
	{
		this(digit, max, x, y, scale, color);
		this.hideZero = hideZero;
	}
	
	public void updateDigit()
	{
		number = (number + 1) % max;
	}
	
	public void setDigit(int digit)
	{
		number = digit;
	}
	
	public void render()
	{
		if(!(hideZero && number == 0))
		{
			this.color = ReadableColor.RED;
			if(number != 1 && number != 4)
			{
				drawRect(xPos + 0.02F * scale, yPos, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
			this.color = ReadableColor.ORANGE;
			if(number != 1 && number != 2 && number != 3 && number != 7)
			{
				drawRect(xPos, yPos + 0.02F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			this.color = ReadableColor.YELLOW;
			if(number != 5 && number != 6)
			{
				drawRect(xPos + 0.08F * scale, yPos + 0.02F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			this.color = ReadableColor.GREEN;
			if(number != 0 && number != 1 && number != 7)
			{
				drawRect(xPos + 0.02F * scale, yPos + 0.08F * scale, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
			this.color = ReadableColor.CYAN;
			if(number == 0 || number == 2 || number == 6 || number == 8)
			{
				drawRect(xPos, yPos + 0.1F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			this.color = ReadableColor.BLUE;
			if(number != 2)
			{
				drawRect(xPos + 0.08F * scale, yPos + 0.1F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			this.color = ReadableColor.PURPLE;
			if(number != 1 && number != 4 && number != 7)
			{
				drawRect(xPos + 0.02F * scale, yPos + 0.16F * scale, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
		}
		
	}
	
	public void drawRect(float x, float y, float width, float length, ReadableColor colour, int orientation)
	{
		// Push the current transform onto the stack
			glPushMatrix();
			
			// Transform to the mid point of the clock
			glTranslatef(x, y, 0);
			
			// then draw the hand (straight up of course)
			//glDrawRect(width * -0.5F, length * 0.2F, width * 0.5F, -length, colour);
			glDrawRect(x, y, x + width, y + length, colour, orientation);
			
			// and finally restore the current transform
			glPopMatrix();
	}
	
	/**
	 * Draw an opaque rectangle
	 */
	private static void glDrawRect(float x1, float y1, float x2, float y2, ReadableColor colour, int orientation)
    {
		// Set GL modes
        glDisableBlend();
        glDisableTexture2D();
        glDisableCulling();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(colour.getRed(), colour.getGreen(), colour.getBlue(), 1.0F);
        
        // Draw the quad
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL_POLYGON, POSITION);
        worldRenderer.pos(x1, y2, 0).endVertex();
        worldRenderer.pos((x1+x2)/2, y2 + (orientation * Math.abs(x1-x2)), 0).endVertex();
        worldRenderer.pos(x2, y2, 0).endVertex();
        worldRenderer.pos(x2 + ((1 -orientation) * Math.abs(y1-y2)), (y1+y2)/2, 0).endVertex();
        worldRenderer.pos(x2, y1, 0).endVertex();
        worldRenderer.pos((x1+x2)/2, y1 - (orientation * Math.abs(x1-x2)), 0).endVertex();
        worldRenderer.pos(x1, y1, 0).endVertex();
        worldRenderer.pos(x1 - ((1 -orientation) * Math.abs(y1-y2)), (y1+y2)/2, 0).endVertex();
        tessellator.draw();
        
        // Restore GL modes
        glEnableCulling();
        glEnableTexture2D();
    }
}
