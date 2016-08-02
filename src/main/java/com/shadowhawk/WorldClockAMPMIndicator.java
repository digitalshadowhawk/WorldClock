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
	private final ReadableColor baseColor = ReadableColor.LTGREY;
	private ReadableColor activeColor;
	private ReadableColor color = baseColor;
	private Object clockType;
	
	WorldClockAMPMIndicator(int x, int y, ReadableColor activeColor, Object clockType)
	{
		this.xPos = x;
		this.yPos = y;
		this.activeColor = activeColor;
		this.clockType = clockType;
	}
	
	public void render()
	{
		if((clockType instanceof Minecraft && isDay((Minecraft)clockType) || (clockType instanceof Calendar && isDay((Calendar)clockType))))
		{
			this.color = this.baseColor;
		} else
		{
			this.color = this.activeColor;
		}
		drawFilledCircle(xPos, yPos, 5f);
		if(renderMode == 0)
		{
			drawFilledCircle(xPos, yPos + 30, 5f);
		} else if(renderMode == 1)
		{
			drawFilledCircle(xPos + 10, yPos+5, 0.8f);
			drawFilledCircle(xPos - 10, yPos+10, 0.8f);
		}
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
	public void drawFilledCircle(float x, float y, float radius){
		int triangleAmount = 20; //# of triangles used to draw circle
		glDisableBlend();
        glDisableTexture2D();
        glDisableCulling();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), 1.0F);
		
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
