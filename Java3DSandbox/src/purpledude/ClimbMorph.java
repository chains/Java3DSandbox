
package purpledude;

/*
 * ClimbMorph.java
 *
 * Created on 21 April 2003, 18:35
 */
import java.util.Enumeration;
import javax.media.j3d.*;
import com.sun.j3d.utils.timer.J3DTimer;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import com.sun.j3d.utils.picking.*;
/**
 *
 * @author  Ben Moxon
 */
public class ClimbMorph extends javax.media.j3d.Behavior {


    
    private boolean walking;
    private boolean fwDown;
    private boolean leftDown;
    private boolean rightDown;
    
    private int upSum;
    
    private double weights[];
    private double step = 0.0002;
    
    private long oldTime;
    
    private Alpha alpha;
    private Morph morph;
    
    private TransformGroup ourTG;
    
    private PickTool picker;
    
    private WakeupOnElapsedFrames framewake = null;
    private WakeupOnAWTEvent    starter = null;
    private WakeupOnAWTEvent    stopper = null;
    private WakeupCondition     wakeUpCondition = null;
    private WakeupCondition     keepUpCondition = null;
    private WakeupCriterion[]     wakeupArray;
    private WakeupCriterion[]   continueArray;
   
    /** Creates a new MoveMorph with the Alpha, Morph and TransformGroup objects it has been given.
     *@param a The alpha to base the behaviour on.
     *@param m The morph object we are animating.
     */  
    public ClimbMorph(Alpha a, Morph m, TransformGroup moveMe, PickTool px) 
    {
        picker = px;
	alpha = a;
	morph = m;
        ourTG = moveMe;
	weights = morph.getWeights();
        starter = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
        stopper = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
        framewake = new WakeupOnElapsedFrames(0);
        continueArray = new WakeupCriterion[2];
        continueArray[0]=stopper;
        continueArray[1]=framewake;
        keepUpCondition = new WakeupOr(continueArray);
    }
    
    /** initializes the behaviour. Our intial starting condition is a keypress.
     */
    public void initialize()
    {
        wakeupOn(starter);
    }


    /** Overrides behaviour's processStimulus method. Triggered by a keypress or release
     *or on every frame, depending on whether the animation is running or not.
     */
    public void processStimulus(Enumeration criteria) {

        WakeupCriterion ster;
        
        while (criteria.hasMoreElements())
        {
            ster=(WakeupCriterion) criteria.nextElement();
            if (ster instanceof WakeupOnAWTEvent)
                processAWTEvent(( (WakeupOnAWTEvent) ster).getAWTEvent());
        }

        if (walking)
        {
            double val = alpha.value();
            if (val < 0.5) {
                double a = val * 2.0;
                weights[0] = 1.0 - a;
                weights[1] = a;
                weights[2] = 0.0;
            }
            else {
                double a = (val - 0.5) * 2.0;
                weights[0] = 0.0;
                weights[1] = 1.0f - a;
                weights[2] = a;
            }
            morph.setWeights(weights);
           
        }
        if (upSum>0) moveStep();
        else wakeupOn(starter);
    }

    private void moveStep()    
        {
            if (rightDown || leftDown || fwDown )
            {

                upSum=0;               
                double stepper = getStep(); 
                Transform3D tempTrans = new Transform3D();
                Transform3D ourTrans = new Transform3D();
                ourTG.getTransform(ourTrans);
                
                if (rightDown || leftDown)
                {
                    if (rightDown)
                    {    
                        tempTrans.rotY(0-stepper);
                        upSum++;

                    }    
                    else
                    {
                        tempTrans.rotY(stepper);
                        upSum++;
                    }
                }
                 if (fwDown)
                {
 
                      upSum++;

                      Point3d pickStart=new Point3d();
                      Vector3d down = new Vector3d(0.0, -1.0, 0.0);
                      Transform3D newT3d = new Transform3D();
                      newT3d.setTranslation(new Vector3d(0.0, 1.0, stepper));
                      newT3d.mul(ourTrans);
                      newT3d.transform(pickStart);
                      
                      picker.setShapeRay(pickStart, down);

                      PickResult picked = picker.pickClosest();
                      PickIntersection intersect = picked.getIntersection(0);
                      Point3d nextpoint = intersect.getPointCoordinates();
                      double pickY = (pickStart.y);
                      
                      pickY = pickY-1.0;

                        if (nextpoint != null)
                        {
                            if (nextpoint.y != pickY)
                            {
                                Vector3d translate = new Vector3d();
                                ourTrans.get(translate);
                                translate.y=nextpoint.y;
                                ourTrans.setTranslation(translate);
                                //ourTrans.setTranslation(new Vector3d(pickStart.x, nextpoint.y, pickStart.z));
                            }    
                        }
                      
                       tempTrans.setTranslation(new Vector3d(0.0d, 0.0d, stepper));
                       System.err.println("      Stepper is "+stepper);
                      
                }
               //checkTransform("    about to  translate ", ourTrans);  debugging code - left here as an example
               ourTrans.mul(tempTrans);
               //checkTransform("    after translation ", ourTrans); debugging code - left here as an example
               ourTG.setTransform(ourTrans);
               
               wakeupOn(keepUpCondition);
            }
    }
    
    /*
    A method to transform a point from a transform3D and send the resulting x y and z values to 
    System.err. This is left here, commented out, as an example of the kind of thing you may find 
    it useful to do when you're having problems with your code. Clear and complete traces like this
    can be a real asset. As can a proper debugging tool, but lots of system.err calls are easier...
    
    private void checkTransform(String note, Transform3D oatBlanket)
    {
        Point3d checkme = new Point3d();
        oatBlanket.transform(checkme);
        System.err.println(note+" > x = "+checkme.x+" y = "+checkme.y+" z = "+checkme.z);
    }
     */
    
    /** Returns the size of the step for the current frame based on the time of the previous step.
    *@return The size of the step;
    */
    public double getStep()
    {
        long echo = J3DTimer.getValue();
        double wrongun= ((echo-oldTime)/1000);
        wrongun = wrongun*step;
        oldTime=echo;
        return wrongun;
    }
    
    private void processAWTEvent(AWTEvent[] events)
    {
        for (int n=0;n<events.length;n++)
        {
            if (events[n] instanceof KeyEvent)
            {
                KeyEvent ek = (KeyEvent) events[n];                

                if (ek.getID() == KeyEvent.KEY_PRESSED)
                {
                    System.err.println("key event "+ek.getKeyChar());
                    if (ek.getKeyChar() == 'w')
                    {
                        alpha.setStartTime(System.currentTimeMillis());
                        walking=true;
                        fwDown= true;
                        getStep();
                        upSum++;
                    }
                    else if (ek.getKeyChar() == 'a')
                    {
                        leftDown=true;
                        upSum++;
                    }
                    else if (ek.getKeyChar() == 'd')
                    {
                        rightDown=true;
                        upSum++;
                    }
                }
                else if (ek.getID() == KeyEvent.KEY_RELEASED)
                {
                    if (ek.getKeyChar() == 'w')
                    {
                        walking=false;
                        fwDown=false;
                        upSum--;

                    }
                    else if (ek.getKeyChar() == 'a')
                    {
                        leftDown=false;
                        upSum--;
                    }
                    else if (ek.getKeyChar() == 'd')
                    {
                        rightDown=false;
                        upSum--;
                    }                  
                }
            }
        }
    }
    

}
