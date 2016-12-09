package app;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioContext;
import com.jme3.audio.Listener;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.JmeContext.Type;

import input.CustomFlyByCamera;

import com.jme3.system.JmeSystem;

public abstract class CustomSimpleApplication extends SimpleApplication  {
	private static final Logger logger = Logger.getLogger(Application.class.getName()); 
	private AppActionListener actionListener = new AppActionListener();

	private class AppActionListener implements ActionListener {

		public void onAction(String name, boolean value, float tpf) {
			if (!value) {
				return;
			}

			if (name.equals(INPUT_MAPPING_EXIT)) {
				stop();
			}else if (name.equals(INPUT_MAPPING_HIDE_STATS)){
				if (stateManager.getState(StatsAppState.class) != null) {
					stateManager.getState(StatsAppState.class).toggleStats();
				}
			}
		}
	}

	@Override
	public void initialize() {
		if (assetManager == null){
			initAssetManager();
		}

		initDisplay();
		initCamera();

		if (inputEnabled){
			initInput();
		}
		initAudio();

		// update timer so that the next delta is not too large
		//        timer.update();
		timer.reset();

		// user code here..

		// Several things rely on having this
		guiFont = loadGuiFont();

		guiNode.setQueueBucket(Bucket.Gui);
		guiNode.setCullHint(CullHint.Never);
		viewPort.attachScene(rootNode);
		guiViewPort.attachScene(guiNode);

		if (inputManager != null) {

			// We have to special-case the FlyCamAppState because too
			// many SimpleApplication subclasses expect it to exist in
			// simpleInit().  But at least it only gets initialized if
			// the app state is added.
			if (stateManager.getState(FlyCamAppState.class) != null) {
				flyCam = new CustomFlyByCamera(cam);
				flyCam.setMoveSpeed(1f); // odd to set this here but it did it before

				Field f=null;
				try {
					f = stateManager.getState(FlyCamAppState.class).getClass().getDeclaredField("flyCam");
					f.setAccessible(true);
					f.set(stateManager.getState(FlyCamAppState.class), flyCam);
					
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
	
				//stateManager.getState(FlyCamAppState.class).setCamera( flyCam );
			}

			if (context.getType() == Type.Display) {
				inputManager.addMapping(INPUT_MAPPING_EXIT, new KeyTrigger(KeyInput.KEY_ESCAPE));
			}

			if (stateManager.getState(StatsAppState.class) != null) {
				inputManager.addMapping(INPUT_MAPPING_HIDE_STATS, new KeyTrigger(KeyInput.KEY_F5));
				inputManager.addListener(actionListener, INPUT_MAPPING_HIDE_STATS);
			}

			inputManager.addListener(actionListener, INPUT_MAPPING_EXIT);
		}

		if (stateManager.getState(StatsAppState.class) != null) {
			// Some of the tests rely on having access to fpsText
			// for quick display.  Maybe a different way would be better.

			Field f1=null;
			Field f2=null;
			try {
				f1 = stateManager.getState(StatsAppState.class).getClass().getDeclaredField("guiFont");
				f1.setAccessible(true);
				f1.set(stateManager.getState(StatsAppState.class), guiFont);
				
				f2 = stateManager.getState(StatsAppState.class).getClass().getDeclaredField("fpsText");
				f2.setAccessible(true);
				f2.set(stateManager.getState(StatsAppState.class), new BitmapText(guiFont, false));
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			
			//stateManager.getState(StatsAppState.class).setFont(guiFont);
			fpsText = stateManager.getState(StatsAppState.class).getFpsText();
		}

		// call user code
		simpleInitApp();
	}

	private void initAssetManager(){
		if (settings != null){
			String assetCfg = settings.getString("AssetConfigURL");
			if (assetCfg != null){
				URL url = null;
				try {
					url = new URL(assetCfg);
				} catch (MalformedURLException ex) {
				}
				if (url == null) {
					url = Application.class.getClassLoader().getResource(assetCfg);
					if (url == null) {
						logger.log(Level.SEVERE, "Unable to access AssetConfigURL in asset config:{0}", assetCfg);
						return;
					}
				}
				assetManager = JmeSystem.newAssetManager(url);
			}
		}
		if (assetManager == null){
			assetManager = JmeSystem.newAssetManager(
					Thread.currentThread().getContextClassLoader()
					.getResource("com/jme3/asset/Desktop.cfg"));
		}
	}
	private void initDisplay(){
		// aquire important objects
		// from the context
		settings = context.getSettings();

		// Only reset the timer if a user has not already provided one
		if (timer == null) {
			timer = context.getTimer();
		}

		renderer = context.getRenderer();
	}
	private void initCamera(){
		cam = new Camera(settings.getWidth(), settings.getHeight());

		cam.setFrustumPerspective(45f, (float)cam.getWidth() / cam.getHeight(), 1f, 1000f);
		cam.setLocation(new Vector3f(0f, 0f, 10f));
		cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);

		renderManager = new RenderManager(renderer);
		//Remy - 09/14/2010 setted the timer in the renderManager
		renderManager.setTimer(timer);
		viewPort = renderManager.createMainView("Default", cam);
		viewPort.setClearFlags(true, true, true);

		// Create a new cam for the gui
		Camera guiCam = new Camera(settings.getWidth(), settings.getHeight());
		guiViewPort = renderManager.createPostView("Gui Default", guiCam);
		guiViewPort.setClearFlags(false, false, false);
	}
	private void initInput(){
		mouseInput = context.getMouseInput();
		if (mouseInput != null)
			mouseInput.initialize();

		keyInput = context.getKeyInput();
		if (keyInput != null)
			keyInput.initialize();

		touchInput = context.getTouchInput();
		if (touchInput != null)
			touchInput.initialize();

		if (!settings.getBoolean("DisableJoysticks")){
			joyInput = context.getJoyInput();
			if (joyInput != null)
				joyInput.initialize();
		}

		inputManager = new InputManager(mouseInput, keyInput, joyInput, touchInput);
	}
	private void initAudio(){
		if (settings.getAudioRenderer() != null && context.getType() != Type.Headless){
			audioRenderer = JmeSystem.newAudioRenderer(settings);
			audioRenderer.initialize();
			AudioContext.setAudioRenderer(audioRenderer);

			listener = new Listener();
			audioRenderer.setListener(listener);
		}
	}
}
