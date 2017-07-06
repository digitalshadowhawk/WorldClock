package com.shadowhawk;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

/**
 * In-game configuration panel with buttons for independently enabling and disabling functionality
 * and changing settings.
 */
public class WorldClockConfigPanel extends Gui implements ConfigPanel {

  /** Line spacing, in points. */
  private final static int SPACING = 24;

  /** Instance references. */
  private Minecraft minecraft;
  private GuiButton setVisible;
  private GuiButton worldClock;
  private GuiButton systemClock;
  private GuiButton clockType;
  private GuiButton activeButton;

  /** Draw the configuration panel's elements every refresh. */
  @Override
  public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
    setVisible.drawButton(minecraft, mouseX, mouseY);
    worldClock.drawButton(minecraft, mouseX, mouseY);
    systemClock.drawButton(minecraft, mouseX, mouseY);
    clockType.drawButton(minecraft, mouseX, mouseY);

  }

  /** Get the height of the panel in points. */
  @Override
  public int getContentHeight() {
    return SPACING * 3;
  }

  /** Get the title to display for the panel. */
  @Override
  public String getPanelTitle() {
    return I18n.format("worldclock.configpanel.title", new Object[] {LiteModWorldClock.MOD_NAME});
  }

  /** On key-presses, nothing needs to be done. */
  @Override
  public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {
    if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RETURN) {
      host.close();
    }
  }

  /** On mouse movement, nothing needs to be done. */
  @Override
  public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}

  /** On click, activate button under cursor if one exists. */
  @Override
  public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
	  if (setVisible.mousePressed(minecraft, mouseX, mouseY))
	  {
		  activeButton = setVisible;
		  LiteModWorldClock.instance.toggleVisibility();
		  setVisible.displayString = (I18n.format("worldclock.configpanel.button.visibletoggle."+LiteModWorldClock.instance.isVisible()+".text", new Object[0]));
		  setVisible.playPressSound(minecraft.getSoundHandler());
	  }
	  else if (worldClock.mousePressed(minecraft, mouseX, mouseY))
	  {
		  activeButton = worldClock;
		  LiteModWorldClock.instance.worldClock = !LiteModWorldClock.instance.worldClock;
		  worldClock.displayString = I18n.format("worldclock.configpanel.button.worldtoggle."+Boolean.toString(LiteModWorldClock.instance.worldClock)+".text", new Object[0]);
	      worldClock.playPressSound(minecraft.getSoundHandler());
	  } 
	  else if (systemClock.mousePressed(minecraft, mouseX, mouseY))
	  {
		  activeButton = systemClock;
		  LiteModWorldClock.instance.systemClock = !LiteModWorldClock.instance.systemClock;
		  systemClock.displayString = I18n.format("worldclock.configpanel.button.systemtoggle."+Boolean.toString(LiteModWorldClock.instance.systemClock)+".text", new Object[0]);
		  systemClock.playPressSound(minecraft.getSoundHandler());
	  }
	  else if (clockType.mousePressed(minecraft, mouseX, mouseY))
	  {
		  activeButton = clockType;
		  LiteModWorldClock.instance.digitalMode = !LiteModWorldClock.instance.digitalMode;
		  clockType.displayString = I18n.format("worldclock.configpanel.button.clocktype."+Boolean.toString(LiteModWorldClock.instance.digitalMode)+".text", new Object[0]);
		  clockType.playPressSound(minecraft.getSoundHandler());
	  }
  }

  /** On release of click, deactivate the selected button (if any). */
  @Override
  public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
    if (activeButton != null) {
      activeButton.mouseReleased(mouseX, mouseY);
      activeButton = null;
    }
  }

  /** On closing of panel, save the configuration to disk. */
  @Override
  public void onPanelHidden() {
    LiteLoader.getInstance().writeConfig(LiteModWorldClock.instance);
  }

  /** On resizing of panel, nothing needs to be done. */
  @Override
  public void onPanelResize(ConfigPanelHost host) {}

  /** On opening of panel, instantiate the user interface components. */
  @Override
  public void onPanelShown(ConfigPanelHost host) {
    minecraft = Minecraft.getMinecraft();
    int id = 0;
    int line = 0;
    setVisible = 
        	new GuiButton(id++, 10, SPACING * line++, I18n.format(
        		"worldclock.configpanel.button.visibletoggle."+LiteModWorldClock.instance.isVisible()+".text", new Object[0]));
    worldClock =
        new GuiButton(id++, 10, SPACING * line++, I18n.format(
        		"worldclock.configpanel.button.worldtoggle."+LiteModWorldClock.instance.worldClock+".text", new Object[0]));
    systemClock = 
        	new GuiButton(id++, 10, SPACING * line++, I18n.format(
        		"worldclock.configpanel.button.systemtoggle."+Boolean.toString(LiteModWorldClock.instance.systemClock)+".text", new Object[0]));
    clockType = 
        	new GuiButton(id++, 10, SPACING * line++, I18n.format(
        		"worldclock.configpanel.button.clocktype."+Boolean.toString(LiteModWorldClock.instance.digitalMode)+".text", new Object[0]));
  }

  /** On each tick, nothing needs to be done. */
  @Override
  public void onTick(ConfigPanelHost host) {}

}
