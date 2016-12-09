package terrainTest;

import dataStructures.tuple.Tuple2;
import env.terrain.TerrainTools;

public class GenerateAndSaveMap {
	
	public static void main(String[] args) {
		Tuple2<Integer, float[]> t = TerrainTools.getPerlinAlgoMap(64);
		String fileName="result";
		TerrainTools.saveHeightMap(fileName, t);
	}
}
