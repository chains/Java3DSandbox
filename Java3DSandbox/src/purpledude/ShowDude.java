package purpledude;

/*
 * ShowDude.java
 *
 * Created on 02 April 2003, 23:18
 */

import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.loaders.Scene;
import com.glyphein.j3d.loaders.milkshape.*;

/**
 * 
 * @author Ben Moxon
 */
public class ShowDude extends java.applet.Applet {

	private SimpleUniverse u;
	private BoundingSphere bounds;

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		OrbitBehavior B;
		ViewingPlatform ourView;
		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D c = new Canvas3D(config);
		add("Center", c);
		SimpleUniverse u = new SimpleUniverse(c);
		BranchGroup scene = createSceneGraph();
		u.addBranchGraph(scene);

		ourView = u.getViewingPlatform();
		ourView.setNominalViewingTransform();

		B = new OrbitBehavior(c);
		B.setSchedulingBounds(bounds);
		ourView.setViewPlatformBehavior(B);

		System.out.println("Should be visible now.");

	}

	private BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();

		String filePath = "file://localhost/" + System.getProperty("user.dir")
				+ "/";

		MS3DLoader loader = new MS3DLoader();

		Scene ourScene;

		try {
			java.net.URL loadLocate = new java.net.URL(filePath
					+ "models/msFigureStanding.ms3d");
			try {
				ourScene = loader.load(loadLocate);
				BranchGroup b = ourScene.getSceneGroup();
				Shape3D newShape = new Shape3D();
				// newShape.addGeometry(((Shape3D)
				// b.getChild(0)).getGeometry());
				newShape = (Shape3D) b.getChild(0);
				b.removeChild(0);
				Appearance app = new Appearance();
				Color3f objColor = new Color3f(0.7f, 0.2f, 0.8f);
				Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
				app.setMaterial(new Material(objColor, black, objColor, black,
						80.0f));

				newShape.setAppearance(app);

				objRoot.addChild(newShape);

			} catch (Exception e) {
				System.err.println("could not load file! " + filePath);
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println("ShowDude.createSceneGraph error: " + filePath
					+ " not a URL");
		}

		Color3f alColour = new Color3f(0.4f, 0.4f, 0.4f);
		AmbientLight aLgt = new AmbientLight(alColour);
		aLgt.setInfluencingBounds(bounds);

		Color3f DirColour = new Color3f(0.35f, 0.35f, 0.3f);
		Vector3f DirVec = new Vector3f(0.5f, -0.2f, 0.6f);

		DirectionalLight DirLig = new DirectionalLight(DirColour, DirVec);
		DirLig.setInfluencingBounds(bounds);

		objRoot.addChild(DirLig);
		objRoot.addChild(aLgt);

		objRoot.compile();
		return objRoot;
	}

	// public void destroy() {
	// u.removeAllLocales();
	// }

	public static void main(String[] args) {
		new MainFrame(new ShowDude(), 700, 700);
	}

}
