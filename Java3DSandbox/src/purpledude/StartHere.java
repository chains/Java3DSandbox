
package purpledude;

/*
 * StartHere.java
 *
 * Created on 25 March 2003, 23:48
 */
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.applet.MainFrame;
/**
 *
 * @author  Ben Moxon
 */
public class StartHere extends java.applet.Applet {
    

    private SimpleUniverse u;
    private BoundingSphere bounds;
    /** Initialization method that will be called after the applet is loaded
     *  into the browser.
     */
    public void init() {
        OrbitBehavior B;
        ViewingPlatform ourView;  
        bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        setLayout(new BorderLayout());
         GraphicsConfiguration config =    SimpleUniverse.getPreferredConfiguration();

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
        OurShape2D os2 = new OurShape2D();
        Appearance app = new Appearance();
	Color3f objColour = new Color3f(0.7f, 0.2f, 0.8f);
	Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	app.setMaterial(new Material(objColour, black, objColour, black, 80.0f));
        os2.setAppearance(app);
        
        Color3f alColour = new Color3f(0.4f, 0.4f, 0.4f);
	AmbientLight aLgt = new AmbientLight(alColour);
        aLgt.setInfluencingBounds(bounds);
        
        objRoot.addChild(aLgt);
        objRoot.addChild(os2);

        objRoot.compile();
        return objRoot;
    }
    
        
//    public void destroy() {
//	u.removeAllLocales();
//    }

    public static void main(String[] args) 
    {
	new MainFrame(new StartHere(), 700, 700);
    }
}
