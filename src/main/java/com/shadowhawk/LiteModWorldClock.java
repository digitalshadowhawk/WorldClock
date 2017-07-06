package com.shadowhawk;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.PreRenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.KeyBinding;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Zachary Cook
 */
@ExposableOptions(strategy = ConfigStrategy.Versioned, filename="worldclock.json")
public class LiteModWorldClock implements Tickable, PreRenderListener, Configurable
{
	public static LiteModWorldClock instance;
	
	public static final String MOD_NAME = "World Clock";
	public static final String MOD_VERSION = "1.1.1";
	
	  /**
	 * This is a keybinding that we will register with the game and use to toggle the clock
	 * 
	 * Notice that we specify the key name as an *unlocalised* string. The localisation is provided from the included resource file
	 */
	private static KeyBinding clockKeyBinding = new KeyBinding("key.clock.toggle", Keyboard.KEY_F12, "key.categories.litemods");

	  /** World Clock. */
	  @Expose
	  @SerializedName("world_clock")
	  protected boolean worldClock = true;
	  

	  /** System Clock. */
	  @Expose
	  @SerializedName("system_clock")
	  protected boolean systemClock = false;
	  
	  /** Digital Mode */
	  @Expose
	  @SerializedName("digital_mode")
	  protected boolean digitalMode = false;
	  
	/**
	 * This is our instance of Clock which we will draw every tick
	 */
	private AnalogClock clock = new AnalogClock(10, 10);
	private DigitalClock clock2 = new DigitalClock(10, 10);
	
	@Expose
	@SerializedName("clock_size")
	private int clockSize = 64;
	
	@Expose
	@SerializedName("clock_visible")
	private boolean clockVisible = true;
	
	/**
	 * Default constructor. All LiteMods must have a default constructor. In general you should do very little
	 * in the mod constructor EXCEPT for initialising any non-game-interfacing components or performing
	 * sanity checking prior to initialisation
	 */
	public LiteModWorldClock()
	{
		if (instance != null) {
		      LiteLoaderLogger.severe("Attempted to instantiate " + MOD_NAME + " twice.");
		      throw new RuntimeException("Double instantiation of " + MOD_NAME);
		    } else {
		      instance = this;
		    }
	}
	
	/** Get class responsible for the configuration panel. */
	  @Override
	  public Class<? extends ConfigPanel> getConfigPanelClass() {
	    return WorldClockConfigPanel.class;
	  }
	
	/**
	 * getName() should be used to return the display name of your mod and MUST NOT return null
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#getName()
	 */
	@Override
	public String getName()
	{
		return MOD_NAME;
	}
	
	/**
	 * getVersion() should return the same version string present in the mod metadata, although this is
	 * not a strict requirement.
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#getVersion()
	 */
	@Override
	public String getVersion()
	{
		return MOD_VERSION;
	}
	
	/**
	 * init() is called very early in the initialisation cycle, before the game is fully initialised, this
	 * means that it is important that your mod does not interact with the game in any way at this point.
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#init(java.io.File)
	 */
	@Override
	public void init(File configPath)
	{
		// The key binding declared above won't do anything unless we register it, ModUtilties provides 
		// a convenience method for this
		LiteLoader.getInput().registerKeyBinding(LiteModWorldClock.clockKeyBinding);
		
		this.clock.setSize(this.clockSize);
		this.clock2.setSize(this.clockSize);
		this.clock.setVisible(this.clockVisible);
		this.clock2.setVisible(this.clockVisible);
	}
	
	public boolean isVisible()
	{
		return clockVisible;
	}
	
	@Override
	public void onRenderClouds(float partialTicks, int pass, RenderGlobal renderGlobal)
	{
//		System.err.printf(">> onRenderClouds %s, %d!\n", partialTicks, pass);
	}
	
	@Override
	public void onRenderSky(float partialTicks, int pass)
	{
//		System.err.printf(">> onRenderSky %s, %d!\n", partialTicks, pass);
	}

	@Override
	public void onRenderTerrain(float partialTicks, int pass)
	{
//		System.err.printf(">> onRenderTerrain %s, %d!\n", partialTicks, pass);
	}
	
	@Override
	public void onRenderWorld(float partialTicks)
	{
//		System.err.printf(">> onRenderWorld!\n");
	}

	@Override
	public void onSetupCameraTransform(float partialTicks, int pass, long timeSlice)
	{
//		System.err.printf(">> onSetupCameraTransform %s, %d, %d!\n", partialTicks, pass, timeSlice);
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// The three checks here are critical to ensure that we only draw the clock as part of the "HUD"
		// and don't draw it over active GUI's or other elements
		if (inGame && minecraft.currentScreen == null && Minecraft.isGuiEnabled())
		{
			if (LiteModWorldClock.clockKeyBinding.isPressed())
			{
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
				{
					this.clockSize = (this.clockSize << 1) & 0x1FF;
					this.clock.setSize(this.clockSize);
					this.clock2.setSize(this.clockSize);
					this.clockSize = this.clock.getSize();
				}
				else
				{
					toggleVisibility();
				}
				
				// Our @Expose annotations control what properties get saved, this tells liteloader to
				// actually write the properties to disk
				LiteLoader.getInstance().writeConfig(this);
			}
			
			// Render the clock			
			if(!digitalMode)
			{
				this.clock.render(minecraft);
			} else {
				this.clock2.render(minecraft);
			}
		}
	}

	public void toggleVisibility()
	{
		this.clock.setVisible(!this.clock.isVisible());
		this.clock2.setVisible(!this.clock2.isVisible());
		this.clockVisible = this.clock.isVisible();
	}

	/**
	 * upgradeSettings is used to notify a mod that its version-specific settings are being migrated
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#upgradeSettings(java.lang.String, java.io.File, java.io.File)
	 */
	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath)
	{
	}
}
