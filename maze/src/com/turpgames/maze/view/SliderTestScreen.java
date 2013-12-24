package com.turpgames.maze.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.component.info.FadingGameInfo;
import com.turpgames.framework.v0.impl.AttachedText;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.display.SlideBrowser;
import com.turpgames.maze.display.SlideBrowserObject;

public class SliderTestScreen extends MazeScreen {	
	@Override
	public void init() {
		super.init();
		IResourceManager resourceManager = Game.getResourceManager();
		
		while(resourceManager.isLoading()) {}
		
		SlideBrowser slideBrowser = new SlideBrowser();
		slideBrowser.registerObject(new SliderTestObject(""+1));
		slideBrowser.registerObject(new SliderTestObject(""+2));
		slideBrowser.registerObject(new SliderTestObject(""+3));
		slideBrowser.registerObject(new SliderTestObject(""+4));
		slideBrowser.registerObject(new SliderTestObject(""+5));
		registerDrawable(slideBrowser, Game.LAYER_GAME);
	}
	
	class SliderTestObject extends SlideBrowserObject {
		private static final float width = 300;
		private static final float height = 300;
		
		private Text indexText;
		private FadingGameInfo clickedInfo;
		public SliderTestObject(String text) {
			setWidth(width);
			setHeight(height);
			getLocation().set((Game.getVirtualWidth()-width)/2, (Game.getVirtualHeight()-height)/2);
			getColor().set(Color.red());
			indexText = new AttachedText(this);
			indexText.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
			indexText.getColor().set(Color.white());
			indexText.setText(text);
			
			clickedInfo = new FadingGameInfo();
			clickedInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
			clickedInfo.getColor().set(Color.white());
			clickedInfo.getColor().a = 0;
			clickedInfo.setText("clicked " + text);
		}
		
		@Override
		protected boolean onTouchDown() {
			clickedInfo.show();
			return super.onTouchDown();
		}
		
		@Override
		public void draw() {
			ShapeDrawer.drawRect(this, true);
			indexText.draw();
			clickedInfo.draw();
		}
	}
}
