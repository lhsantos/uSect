package org.unbiquitous.games.uSect;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.unbiquitous.uImpala.engine.core.GameRenderers;
import org.unbiquitous.uImpala.jse.util.shapes.Circle;

class Nutrient extends EnvironmentObject{
	Point center;
	Set<Sect> targetOf;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Map<Sect, Integer> absortionTable = new HashMap();
	private int radius = 10;
	
	boolean hasBeenConsumed = false;
	
	public Nutrient(Point center) {
		this.center = center;
		targetOf = new HashSet<Sect>();
	}

	public void inContactWith(Sect s) {
		absortionTable.put(s, 1+absortionTable.get(s));
		if(absortionTable.get(s) >= 5){
			notifyAbsortionToAll();
			hasBeenConsumed = true;
		}
	}

	private void notifyAbsortionToAll() {
		for(Sect s1: targetOf){
			s1.leftSight(new Something(id, center, Something.Type.NUTRIENT));
		}
	}

	public void insightOf(Sect s) {
		if(! targetOf.contains(s)){
			s.enteredSight(new Something(id, center, Something.Type.NUTRIENT)); 
			targetOf.add(s);
			absortionTable.put(s, 0);
		};
	}

	protected void render(GameRenderers renderers) {
		new Circle(center, Color.GREEN.darker(), radius).render();
	}
}