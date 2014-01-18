package com.turpgames.maze.display;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.GameUtils;

public class SlideBrowser extends GameObject {

	private static float switchThreshold = Game.getVirtualWidth() * 2 / 5;
	private static float boundaryLimit = Game.getVirtualWidth() * 1 / 5;
	private static float slideLimit = 50f;
	private List<SlideBrowserObject> items;
	
	private int currentIndex;
	private float elapsedReturn;
	private static float durationReturn = 1f;
	
	private boolean isSliding;
	
	public SlideBrowser() {
		items = new ArrayList<SlideBrowserObject>();
		listenInput(true);
		currentIndex = 0;
		elapsedReturn = durationReturn + 100;
	}
	
	public void registerObject(SlideBrowserObject obj) {
		float x = obj.getLocation().x;
		float y = obj.getLocation().y;
		obj.getLocation().set(x + (- currentIndex + items.size()) * Game.getVirtualWidth(), y + 0.0f);
		items.add(obj);
		if (items.size() == 1)
			items.get(currentIndex).listenInput(isListeningInput());
	}
	
	public void unregisterObject(SlideBrowserObject obj) {
		items.remove(obj);
	}
	
	private boolean isSwitching() {
		return elapsedReturn < durationReturn;
	}
	
	float p;
	@Override
	public void draw() {
		if (items.size() == 0)
			return;		
		
		if(isSliding && isSwitching()) {
			elapsedReturn += Game.getDeltaTime();
			float p = (1 - Math.max((durationReturn - elapsedReturn) / (durationReturn ), 60/100)) * totalDx;
			
			totalDx *= Math.max((durationReturn - elapsedReturn) / (durationReturn ), 60/100);

			if (Math.abs(totalDx) < 0.005) {
				p = totalDx;
				totalDx = 0;
				elapsedReturn = durationReturn + 100;
			}
			for (int i = 0; i < items.size(); i++) {
//				Game.pushRenderingShift((-dx - currentIndex + i)* Game.getVirtualWidth(), 0, false);
//				items.get(i).getLocation().set((-dx - currentIndex + i)* Game.getVirtualWidth(), items.get(i).getLocation().y);
				items.get(i).getLocation().add(p, 0f);
//				Game.popRenderingShift();
			}
		}

		for (int i = 0; i < items.size(); i++) {
//			Game.pushRenderingShift((-dx - currentIndex + i)* Game.getVirtualWidth(), 0, false);
			items.get(i).draw();
//			Game.popRenderingShift();
		}
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Game.LAYER_SCREEN);
	}
	
	
	private float startX;
	private boolean startedSliding;
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (isInputInCurrentItem(x, y)) {
			return false;
		}
		isSliding = false;
		startedSliding = true;
		items.get(currentIndex).listenInput(false);
		startX = x;
		slideDx = 0;
		return false;
	}
	
	private float totalDx;
	private float slideDx;
	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		if (!startedSliding)
			return false;
		float f = ((startX - x) > 0 ? Math.min(startX - x, slideLimit) : Math.max(startX - x, -slideLimit));
		f *= 1.5f;
		//boundaryCheck
		if (slideDx + f + totalDx > boundaryLimit + (items.size() -1 -currentIndex) * Game.getVirtualWidth())
			f = boundaryLimit + (items.size() -1 -currentIndex) * Game.getVirtualWidth() - slideDx - totalDx;
		else if (slideDx + f + totalDx < -boundaryLimit + (-currentIndex) * Game.getVirtualWidth())
			f = -boundaryLimit + (-currentIndex) * Game.getVirtualWidth() - slideDx - totalDx;
				
		slideDx += f;
		startX = x;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).getLocation().add(- f, 0.0f);
		}
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		if(!startedSliding)
			return false;
		lifted();
		return false;
	}

	private boolean isInputInCurrentItem(float x, float y) {
		if (GameUtils.isIn(x, y, items.get(currentIndex)))
			return true;
		return false;
	}
	
	private void lifted() {
		elapsedReturn = 0;
		if(slideDx > switchThreshold) { // go forward
			slideDx -= switchThreshold;
			int tmp = (int) (slideDx / Game.getVirtualWidth()) + 1;
			currentIndex += tmp;
			slideDx += - tmp * Game.getVirtualWidth() + switchThreshold;
		}
		else if (slideDx < -switchThreshold) { // go backward
			slideDx += switchThreshold;
			int tmp = (int) (slideDx / Game.getVirtualWidth()) - 1;
			currentIndex += tmp;
			slideDx += - tmp * Game.getVirtualWidth() - switchThreshold;
		}
		items.get(currentIndex).listenInput(true);
		totalDx += slideDx;
		isSliding = true;
		startedSliding = false;
	}
}
