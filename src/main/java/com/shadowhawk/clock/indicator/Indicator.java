package com.shadowhawk.clock.indicator;

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

import com.shadowhawk.clock.ClockData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.*;

public class Indicator extends ClockData{

	public Indicator(Minecraft minecraft, float xPos, float yPos, float scale, ReadableColor color, boolean isMinecraft)
	{
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.scale = scale;
		this.minecraft = minecraft;
		this.isMinecraft = isMinecraft;
	}
	
	public void render()
	{
		if((isMinecraft && isDay(Minecraft.getMinecraft()))|| (!isMinecraft && isDay(Calendar.getInstance())))
		{
			drawFilledCircle(xPos + 0.1F * scale, yPos + 0.07F * scale, 0.03F * scale, color, 1.0F);
		}
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
}
