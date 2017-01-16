package learning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import env.jme.Situation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class AttWriter {
	public static void writeArff(Situation situation) throws Exception 
	{

		System.out.println(situation.toString());
		FastVector attributes;
		Instances dataSet;
		double[] values;

		attributes = new FastVector();
		if(situation.fieldOfView==0 || situation.agentAltitude==null || situation.minAltitude==null 
				|| situation.maxAltitude==null || situation.avgAltitude ==0 || situation.fieldOfView==0  
				|| situation.maxDepth==0 || situation.consistency ==0){
			return; // Situation invalide !!
		}

		attributes.addElement(new Attribute("fieldOfViewLimit")); 		
		attributes.addElement(new Attribute("lastAction")); 


		attributes.addElement(new Attribute("agentAltitudeX"));
		attributes.addElement(new Attribute("agentAltitudeY"));
		attributes.addElement(new Attribute("agentAltitudeZ"));

		attributes.addElement(new Attribute("minAltitudeX"));
		attributes.addElement(new Attribute("minAltitudeY"));
		attributes.addElement(new Attribute("minAltitudeZ"));

		attributes.addElement(new Attribute("maxAltitudeX"));
		attributes.addElement(new Attribute("maxAltitudeY"));
		attributes.addElement(new Attribute("maxAltitudeZ"));

		attributes.addElement(new Attribute("avgAltitude")); 

		attributes.addElement(new Attribute("fieldOfView"));
		attributes.addElement(new Attribute("maxDepth")); 
		attributes.addElement(new Attribute("consistency")); 

		if (situation.agents!=null){
			for(int i =0 ;i < situation.agents.size();i++ ){
				attributes.addElement(new Attribute("ennemy"+i+"X"));
				attributes.addElement(new Attribute("ennemy"+i+"Y"));
				attributes.addElement(new Attribute("ennemy"+i+"Z"));
				attributes.addElement(new Attribute("ennemy"+i+"Name",(FastVector) null)); // String
			}
		}





		// 2. create Instances object
		dataSet = new Instances("Situation", attributes, 15);
		System.out.println("size() "+dataSet.numAttributes());

		// 3. fill with data
		values = new double[dataSet.numAttributes()];
		// - fieldOfViewLimit
		values[0] = situation.fieldOfView;
		// - lastAction
		if (situation.lastAction==null){
			values[1] = 0;
		}else{
			values[1] = situation.lastAction.id;
		}


		// - agentAltitude
		values[2] = situation.agentAltitude.getX();
		values[3] =situation.agentAltitude.getY();
		values[4] = situation.agentAltitude.getZ();
		
		// - minAltitudeX
		values[5] = situation.minAltitude.getX();
		values[6] =situation.minAltitude.getY();
		values[7] = situation.minAltitude.getZ();
		// - maxAltitudeX
		values[8] = situation.maxAltitude.getX();
		values[9] =situation.maxAltitude.getY();
		values[10] = situation.maxAltitude.getZ();
		// - avgAltitude
		values[11] = situation.avgAltitude;
		// - fieldOfView;
		values[12] = situation.fieldOfView;
		// - maxDepth;
		values[13] = situation.maxDepth;
		// - consistency;
		values[14] = situation.consistency;

		if (situation.agents!=null){
			for(int i =0 ;i < situation.agents.size();i++ ){
				values[15+(i*4)] = situation.agents.get(i).getFirst().getX();
				values[16+(i*4)] = situation.agents.get(i).getFirst().getY();
				values[17+(i*4)] = situation.agents.get(i).getFirst().getZ();
				values[18+(i*4)] = dataSet.attribute(18+(i*4)).addStringValue(situation.agents.get(i).getSecond());

			}	
		}

		dataSet.add(new Instance(1.0, values));

		//ArffSaver saver = new ArffSaver();
		//saver.setInstances(dataSet);
		//saver.setFile(new File("./test2.arff"));

		String fileName = "./resultats.arff";
		File f = new File(fileName);
		boolean fileExists = false;
		if(f.exists() && !f.isDirectory()) { 
			fileExists = true;
		}
		BufferedWriter writer =null;
		
		if(fileExists){
			writer = new BufferedWriter(new FileWriter(fileName,true));
			writer.write(stringWithoutHeader(dataSet)+"\n");
		}else{
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(dataSet.toString()+"\n");
		}
		writer.flush();
		writer.close();
		
	}
	private static String stringWithoutHeader(Instances data){
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < data.numInstances(); ++i) {
			text.append(data.instance(i));
			if (i < data.numInstances() - 1) {
				text.append('\n');
			}
		}
		return text.toString();
	}
}
