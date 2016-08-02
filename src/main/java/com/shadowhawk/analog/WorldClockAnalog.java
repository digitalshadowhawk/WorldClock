package com.shadowhawk.analog;

import static com.mumfrey.liteloader.gl.GL.GL_GREATER;
import static com.mumfrey.liteloader.gl.GL.GL_QUADS;
import static com.mumfrey.liteloader.gl.GL.glAlphaFunc;
import static com.mumfrey.liteloader.gl.GL.glColor4f;
import static com.mumfrey.liteloader.gl.GL.glDisableBlend;
import static com.mumfrey.liteloader.gl.GL.glDisableLighting;
import static com.mumfrey.liteloader.gl.GL.glEnableTexture2D;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX;

import org.lwjgl.util.ReadableColor;

import com.shadowhawk.WorldClockBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.MathHelper;


/**
 * Analog clock to show the time in minecraft or IRL based on the example mod by Adam Mummery-Smith
 *
 * @author Shadow_Hawk
 */
public class WorldClockAnalog extends WorldClockBase
{
	/**
	 * This is the clock face resource, you need to create a resource location for any assets that you
	 * wish to use. It is best to make these static to avoid new instances being created for every instance
	 * of the referencing object, this also means they will only be garbage collected when the class is
	 * garbage collected or when no instances of the class are left.
	 * 
	 * The first parameter for the resource location is the "domain" and this should normally be your mod's
	 * name. The domain MUST be lower case! The second is the resource "path" and represents the path to the
	 * resource within the domain. It is convention that the path always start with the resource type, such
	 * as "textures" in this case.
	 * 
	 * Resources are always stored in a path of the form "assets/{domain}/{path}" which makes the appropriate
	 * path to the CLOCKFACE resource: "/assets/example/textures/clock/face.png"
	 */
	private static final ResourceLocation CLOCKFACE = new ResourceLocation("worldclock", "textures/clock/face.png");
	
	/**
     * Draw a rectangle using the currently bound texture
     */
    static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
    {
		// Set the appropriate OpenGL modes
		glDisableLighting();
        glDisableBlend();
		glAlphaFunc(GL_GREATER, 0.01F);
		glEnableTexture2D();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		float texMapScale = 0.001953125F; // 512px
        
        // We use the tessellator rather than drawing individual quads because it uses vertex arrays to
        // draw the quads more efficiently.
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL_QUADS, POSITION_TEX);
        worldRenderer.pos(x + 0,     y + height, 0).tex(u  * texMapScale, v2 * texMapScale).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(u2 * texMapScale, v2 * texMapScale).endVertex();
        worldRenderer.pos(x + width, y + 0,      0).tex(u2 * texMapScale, v  * texMapScale).endVertex();
        worldRenderer.pos(x + 0,     y + 0,      0).tex(u  * texMapScale, v  * texMapScale).endVertex();
        tessellator.draw();
    }
	
	
	public WorldClockAnalogHands mcHands, sysHands;
	
	/**
	 * @param xPos X position for the clock
	 * @param yPos Y position for the clock
	 */
	public WorldClockAnalog(int xPos, int yPos)
	{
		this.setPosition(xPos, yPos);
		this.setSize(64);
		
		this.mcHands = new WorldClockAnalogHands(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.GREY, ReadableColor.WHITE, true);
		this.sysHands = new WorldClockAnalogHands(Minecraft.getMinecraft(), xPos, yPos, size, ReadableColor.PURPLE, ReadableColor.PURPLE, ReadableColor.PURPLE, false);
	}
	
	/**
	 * Render the clock at its current position, unless hidden
	 * 
	 * @param minecraft Minecraft game instance
	 */
	public void render(Minecraft minecraft, boolean systemClock, boolean worldClock)
	{		
		if (this.isVisible())
		{
			// First, update the hand angles
			sysHands.calculateAngles();
			mcHands.calculateAngles(Minecraft.getMinecraft());
			
			// Then render the actual clock
			if(systemClock || worldClock)
			{
				this.renderClock(minecraft, systemClock, worldClock);
			}
		}
	}

	/**
	 * Renders the clock
	 * 
	 * @param minecraft Minecraft game instance
	 */
	private void renderClock(Minecraft minecraft, boolean systemClock, boolean worldClock)
	{
		// Render the face
		this.renderClockFace(minecraft);
		
		// Render each of the hands
		if(systemClock)
		{
			sysHands.render();
		}
		if(worldClock)
		{
			mcHands.render();
		}
	}

	/**
	 * Renders the clock face texture using the texture resource
	 * 
	 * @param minecraft Minecraft game instance
	 */
	private void renderClockFace(Minecraft minecraft)
	{
		// Bind the texture resource
		minecraft.getTextureManager().bindTexture(WorldClockAnalog.CLOCKFACE);
		
		// Draw a rectangle using the currently bound texture
		glDrawTexturedRect(this.xPos, this.yPos, this.size, this.size, 1, 1, 511, 511);
	}

	
    @Override
	public void setSize(int size)
	{
		this.size  = Math.max(32, size);
		this.mcHands = new WorldClockAnalogHands(Minecraft.getMinecraft(), xPos, yPos, this.size, ReadableColor.GREY, ReadableColor.WHITE, true);
		this.sysHands = new WorldClockAnalogHands(Minecraft.getMinecraft(), xPos, yPos, this.size, ReadableColor.PURPLE, ReadableColor.PURPLE, ReadableColor.PURPLE, false);
	}
}
