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

import net.minecraft.client.renderer.*;

public class Digit {
	private int number;
	private int max = 6;
	private float xPos;
	private float yPos;
	private float scale;
	private ReadableColor color;
	private boolean hideZero = false;
	
	/**
	 * Makes up numbers out of horizontal and vertical segments
	 * 
	 * @param digit The number the digit is supposed to represent
	 * @param max The maximum numeral the digit can represent; useful for non-base 10 counting systems or clocks
	 * numerals higher than 9 not currently supported (ie. hexadecimal)
	 * @param x X-coordinate at the top left of the digit
	 * @param y Y-coordinate at the top left of the digit
	 * @param scale Scaling factor of the digit
	 * @param color Color of the digit
	 */
	public Digit(int digit, int max, float x, float y, float scale, ReadableColor color)
	{
		number = digit;
		max = Math.min(10, max);
		xPos = x;
		yPos = y;
		this.scale = scale;
		this.color = color;
	}
	
	/**
	 * Makes up numbers out of horizontal and vertical segments
	 * 
	 * @param digit The number the digit is supposed to represent
	 * @param max The maximum numeral the digit can represent; useful for non-base 10 counting systems or clocks
	 * numerals higher than 9 not currently supported (ie. hexadecimal)
	 * @param x X-coordinate at the top left of the digit
	 * @param y Y-coordinate at the top left of the digit
	 * @param scale Scaling factor of the digit
	 * @param color Color of the digit
	 * @param hideZero Whether the numeral should be hidden when it is equal to zero, such as leading zeroes
	 */
	public Digit(int digit, int max, float x, float y, float scale, ReadableColor color, boolean hideZero)
	{
		this(digit, max, x, y, scale, color);
		this.hideZero = hideZero;
	}
	
	/**
	 * Increments the digit by one, wrapping to 0 when it reaches its maximum
	 */
	public void updateDigit()
	{
		number = (number + 1) % max;
	}
	
	/**
	 * Sets the digit to the entered value
	 * @param digit New value for number
	 */
	public void setDigit(int digit)
	{
		number = digit;
	}
	
	/**
	 * Displays the digit at its specified coordinates on screen
	 */
	public void render()
	{
		if(!(hideZero && number == 0))
		{
			//this.color = ReadableColor.RED;
			if(number != 1 && number != 4)
			{
				//drawRect(xPos + 0.02F * scale, yPos, 0.1F * scale, 0.02F * scale, this.color, 0);
				drawRect(xPos + 0.03F * scale, yPos, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
			//this.color = ReadableColor.ORANGE;
			if(number != 1 && number != 2 && number != 3 && number != 7)
			{
				drawRect(xPos, yPos + 0.03F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			//this.color = ReadableColor.YELLOW;
			if(number != 5 && number != 6)
			{
				drawRect(xPos + 0.14F * scale, yPos + 0.03F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			//this.color = ReadableColor.GREEN;
			if(number != 0 && number != 1 && number != 7)
			{
				drawRect(xPos + 0.03F * scale, yPos + 0.14F * scale, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
			//this.color = ReadableColor.CYAN;
			if(number == 0 || number == 2 || number == 6 || number == 8)
			{
				drawRect(xPos, yPos + 0.17F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			//this.color = ReadableColor.BLUE;
			if(number != 2)
			{
				drawRect(xPos + 0.14F * scale, yPos + 0.17F * scale, 0.02F * scale, 0.1F * scale, this.color, 1);
			}
			//this.color = ReadableColor.PURPLE;
			if(number != 1 && number != 4 && number != 7)
			{
				drawRect(xPos + 0.03F * scale, yPos + 0.28F * scale, 0.1F * scale, 0.02F * scale, this.color, 0);
			}
		}
		
	}
	
	/**
	 * 
	 * @param x X-coordinate of the segment
	 * @param y Y-coordinate of the segment
	 * @param width Width of the segment
	 * @param length Length of the segment
	 * @param color Color of the segment
	 * @param orientation Determines which sides of the segment get pointed tips; 
	 *     0 = horizontal
	 *     1 = vertical
	 */
	public void drawRect(float x, float y, float width, float length, ReadableColor color, int orientation)
	{
		// Push the current transform onto the stack
			glPushMatrix();
			
			// Transform to the location of the segment
			//glTranslatef(x, y, 0);
			
			// draw the segment
			glDrawSeg(x, y, x + width, y + length, color, orientation);
			
			// and finally restore the current transform
			glPopMatrix();
	}
	
	/**
	 * Draw an opaque segment with pointed tips
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 * @param orientation Determines which sides of the segment get pointed tips; 
	 *     0 = horizontal
	 *     1 = vertical
	 */
	private static void glDrawSeg(float x1, float y1, float x2, float y2, ReadableColor color, int orientation)
    {
		// Set GL modes
        glDisableBlend();
        glDisableTexture2D();
        glDisableCulling();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), 1.0F);
        
        // Draw the polygon
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
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
