package princ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jme3.system.AppSettings;

import dataStructures.tuple.Tuple2;
import env.jme.Environment;
import env.terrain.TerrainTools;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import sma.agents.BasicAgent;
import sma.agents.ChaseAgent;
import sma.agents.SmartAgent;

public class Principal {

	private static String hostname = "127.0.0.1"; 
	private static HashMap<String, ContainerController> containerList=new HashMap<String, ContainerController>();// container's name - container's ref
	public static List<AgentController> agentList;// agents's ref
	private static Environment env;// static ref of the real environment

	public static Lock lockUpdate;
	public static final int nbSimulationMax = 5;
	public static final int nbSimulationByMap = 5;
	public static int nbWin =0;
	public static Tuple2<Integer, float[]> map;
	public static int nbMatchNull =0;
	public static boolean printDebug = true;

	public static void main(String[] args){

		lockUpdate = new ReentrantLock();	
		env = new Environment();

		emptyPlatform(containerList);

		for(int nbSim =0 ; nbSim<Principal.nbSimulationMax;nbSim++){
			synchronized(env){
				try {				
					
					agentList=createAgents(containerList);
					//env.launchNormalMap(createMap("circleMap2"));
					env.launchPerlinMap(createMap(64));

					System.out.println("-- Wait JMonkey ending loading !! ");
					env.wait();
					System.out.println("-- startAgents --");
					startAgents(agentList);

					System.out.println("---->nb Simulation "+ (nbSim+1));
					System.out.println("---->nb Win  "+ Principal.nbWin);
					System.out.println("---Wait for finish--");
					env.wait(); // wait for finish

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			env.stop(true);
			env = new Environment();
			System.gc();
		}

	}

	public static Tuple2<Integer, float[]> createMap(String fileName){
		if(Principal.map==null){
			System.out.println("###Create initial Map");
			System.out.println("###Create initial Map");
			Principal.map =  TerrainTools.getHeightMapFromTxt(fileName);
			}
		return Principal.map;
	}
	public static Tuple2<Integer, float[]> createMap(int size){
		if(Principal.map==null){
			System.out.println("###Create initial Map");
			Principal.map =  TerrainTools.getPerlinAlgoMap(size);
		}else{
			if(nbWin%nbSimulationByMap==0){
				System.out.println("###Create new map");
				nbWin=0;
				Principal.map =  TerrainTools.getPerlinAlgoMap(size);
			}
		}
		
		return Principal.map;

	}
	
	/**********************************************
	 * 
	 * Methods used to create an empty platform
	 * 
	 **********************************************/

	/**
	 * Create an empty platform composed of 1 main container and 3 containers.
	 * 
	 * @return a ref to the platform and update the containerList
	 */
	private static Runtime emptyPlatform(HashMap<String, ContainerController> containerList){

		Runtime rt = Runtime.instance();

		// 1) create a platform (main container+DF+AMS)
		Profile pMain = new ProfileImpl(hostname, 8888, null);
		System.out.println("Launching a main-container..."+pMain);
		AgentContainer mainContainerRef = rt.createMainContainer(pMain); //DF and AMS are include

		// 2) create the containers
		containerList.putAll(createContainers(rt));

		// 3) create monitoring agents : rma agent, used to debug and monitor the platform; sniffer agent, to monitor communications; 
		createMonitoringAgents(mainContainerRef);

		System.out.println("Plaform ok");
		return rt;

	}

	/**
	 * Create the containers used to hold the agents 
	 * @param rt The reference to the main container
	 * @return an Hmap associating the name of a container and its object reference.
	 * 
	 * note: there is a smarter way to find a container with its name, but we go fast to the goal here. Cf jade's doc.
	 */
	private static HashMap<String,ContainerController> createContainers(Runtime rt) {
		String containerName;
		ProfileImpl pContainer;
		ContainerController containerRef;
		HashMap<String, ContainerController> containerList=new HashMap<String, ContainerController>();//bad to do it here.


		System.out.println("Launching containers ...");

		//create the container0	
		containerName="container0";
		pContainer = new ProfileImpl(null, 8888, null);
		System.out.println("Launching container "+pContainer);
		containerRef = rt.createAgentContainer(pContainer); //ContainerController replace AgentContainer in the new versions of Jade.
		containerList.put(containerName, containerRef);

		//		//create the container1	
		//		containerName="container1";
		//		pContainer = new ProfileImpl(null, 8888, null);
		//		System.out.println("Launching container "+pContainer);
		//		containerRef = rt.createAgentContainer(pContainer); //ContainerController replace AgentContainer in the new versions of Jade.
		//		containerList.put(containerName, containerRef);
		//
		//		//create the container2	
		//		containerName="container2";
		//		pContainer = new ProfileImpl(null, 8888, null);
		//		System.out.println("Launching container "+pContainer);
		//		containerRef = rt.createAgentContainer(pContainer); //ContainerController replace AgentContainer in the new versions of Jade.
		//		containerList.put(containerName, containerRef);

		System.out.println("Launching containers done");
		return containerList;
	}


	/**
	 * create the monitoring agents (rma+sniffer) on the main-container given in parameter and launch them.
	 *  - RMA agent's is used to debug and monitor the platform;
	 *  - Sniffer agent is used to monitor communications
	 * @param mc the main-container's reference
	 * @return a ref to the sniffeur agent
	 */
	private static void createMonitoringAgents(ContainerController mc) {

		System.out.println("Launching the rma agent on the main container ...");
		AgentController rma;

		try {
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
			rma.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			System.out.println("Launching of rma agent failed");
		}

		System.out.println("Launching  Sniffer agent on the main container...");
		AgentController snif=null;

		try {
			snif= mc.createNewAgent("sniffeur", "jade.tools.sniffer.Sniffer",new Object[0]);
			snif.start();

		} catch (StaleProxyException e) {
			e.printStackTrace();
			System.out.println("launching of sniffer agent failed");

		}		


	}

	/**********************************************
	 * 
	 * Methods used to create the agents and to start them
	 * 
	 **********************************************/


	/**
	 *  Creates the agents and add them to the agentList.  agents are NOT started.
	 *@param containerList :Name and container's ref
	 *@param sniff : a ref to the sniffeur agent
	 *@return the agentList
	 */
	private static List<AgentController> createAgents(HashMap<String, ContainerController> containerList) {
		System.out.println("Launching agents...");
		ContainerController c;
		String agentName;
		List<AgentController> agentList=new ArrayList<AgentController>();	
		c = containerList.get("container0");
		agentName="Player1";
		try {


			Object[] objtab=new Object[]{env, true};//used to give informations to the agent
			AgentController	ag=c.createNewAgent(agentName,SmartAgent.class.getName(),objtab);
			agentList.add(ag);
			System.out.println(agentName+" launched");
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		c = containerList.get("container0");

		agentName="Player2";
		try {


			Object[] objtab=new Object[]{env, false};//used to give informations to the agent
			AgentController	ag=c.createNewAgent(agentName,ChaseAgent.class.getName(),objtab);
			agentList.add(ag);
			System.out.println(agentName+" launched");
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		System.out.println("Agents launched...");
		return agentList;
	}

	/**
	 * Start the agents
	 * @param agentList
	 */
	private static void startAgents(List<AgentController> agentList){

		System.out.println("Starting agents...");


		for(final AgentController ac: agentList){
			try {
				ac.start();
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Agents started...");
	}

}
