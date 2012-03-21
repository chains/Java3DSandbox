package purpledude;

/*
 * ShowLand.java
 *
 * Created on 31 March 2003, 23:25
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
public class ShowLand extends java.applet.Applet {
    

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

        String texturePath="file://localhost/"+System.getProperty("user.dir")+"/stone.jpg";
        
        SimpleLand s = new SimpleLand(10, 10, texturePath);
        
        
        Color3f alColour = new Color3f(0.4f, 0.4f, 0.4f);
	AmbientLight aLgt = new AmbientLight(alColour);
        aLgt.setInfluencingBounds(bounds);
        
        Color3f DirColour = new Color3f(0.35f, 0.35f, 0.3f);
	Vector3f DirVec  = new Vector3f(0.5f, -0.2f, 0.6f);

	DirectionalLight DirLig = new DirectionalLight(DirColour, DirVec);
	DirLig.setInfluencingBounds(bounds);

        objRoot.addChild(DirLig);
        objRoot.addChild(aLgt);
        objRoot.addChild(s);

        objRoot.compile();
        return objRoot;
    }
    
        
//    public void destroy() {
//	u.removeAllLocales();
//    }

    public static void main(String[] args) 
    {
	new MainFrame(new ShowLand(), 700, 700);
    }
}
