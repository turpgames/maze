package com.turpgames.maze.display;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class SlideBrowser extends GameObject {

	private static float switchThreshold = 0.7f;
	private static float boundaryLimit = 0.2f;
	private List<GameObject> items;
	
	private int currentIndex;
	private float elapsedReturn;
	private static float durationReturn = 1f;
	
	public SlideBrowser() {
		items = new ArrayList<GameObject>();
		listenInput(true);
		currentIndex = 1;
		elapsedReturn = durationReturn + 100;
	}
	
	public void registerObject(GameObject obj) {
		items.add(obj);
	}
	
	public void unregisterObject(GameObject obj) {
		items.remove(obj);
	}
	
	private boolean isReturning() {
		return elapsedReturn < durationReturn;
	}
	
	@Override
	public void draw() {
		if (items.size() == 0)
			return;		
		
		if(isReturning()) {
			elapsedReturn += Game.getDeltaTime();
			dx *= (durationReturn - elapsedReturn) / durationReturn;
			if (durationReturn - elapsedReturn < 0.02) {
				dx = 0;
				elapsedReturn = durationReturn + 100;
			}
		}

		for (int i = 0; i < items.size(); i++) {
			Game.pushRenderingShift((-dx - currentIndex + i)* Game.getVirtualWidth(), 0, false);
			items.get(i).draw();
			Game.popRenderingShift();
		}
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_SCREEN);
	}
	
	private float dx;
	private float startX;
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		elapsedReturn = durationReturn + 100;
		startX = x;
		return false;
	}
	
	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		dx += ((startX - x) * 2 / Game.getVirtualWidth());
		if (dx > 0) {
			dx = Math.min(dx, boundaryLimit + (items.size() -1 - currentIndex));
		}
		else if (dx < 0) {
			dx = Math.max(dx, - boundaryLimit - currentIndex);
		}
		startX = x;
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		lifted();
		return false;
	}

	private void lifted() {
		// can pass more than one item on one swipe
		elapsedReturn = 0;
		if(dx > switchThreshold) { // go forward
			dx -= switchThreshold;
			int tmp = (int) dx + 1;
			currentIndex += tmp;
			dx = dx - tmp + switchThreshold;
		}
		else if (dx < -switchThreshold) { // go backward
			dx += switchThreshold;
			int tmp = (int) dx - 1;
			currentIndex += tmp;
			dx = dx - tmp - switchThreshold;
		}
	}
}
