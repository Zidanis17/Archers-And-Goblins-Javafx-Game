package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.Battle;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;

public class Model {
	public Battle battle;
	public int initialTitanSpawnDistance;
	
	public Model(int initialTitanSpawnDistance ,int initialNumOfLanes, int initialResourcesPerLane) throws IOException {
		battle = new Battle(1, 0, initialTitanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);
		this.initialTitanSpawnDistance = initialTitanSpawnDistance;
	}
	
	public Battle getBattle() {
		return battle;
	}
	
	public Lane getLaneByNumber(int num) {
		return battle.getOriginalLanes().get(num-1);
	}
	
	public ArrayList<ArrayList<Titan>> getTitansInLaneFormByNumber(int num){
		ArrayList<ArrayList<Titan>> titansInLaneForm = new ArrayList<>();
		
		Lane laneOfTitans = getLaneByNumber(num);
		PriorityQueue<Titan> titansPQ = laneOfTitans.getTitans();
		for(int i = 0; i < initialTitanSpawnDistance + 1; i++) {
			ArrayList<Titan> titansInIndex = new ArrayList<>();
			for(Titan titan : titansPQ) {

				if(titan.getDistance() == i) {
					titansInIndex.add(titan);
				}
				
			}
			titansInLaneForm.add(titansInIndex);
		}
		return titansInLaneForm;
	}
	
	
}
