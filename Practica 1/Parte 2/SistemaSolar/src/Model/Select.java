package Model;

import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.WakeupOnAWTEvent;

public class Select extends Behavior {

    private WakeupOnAWTEvent condition;
    private PickCanvas pickCanvas;
    private Canvas3D canvas;

    public Select(Canvas3D aCanvas) {
        canvas = aCanvas;
        condition = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
    }

    public void initSearch(BranchGroup bg) {
        pickCanvas = new PickCanvas(canvas, bg);
        pickCanvas.setTolerance(0.0f); 
        pickCanvas.setMode(PickInfo.PICK_GEOMETRY); 
        pickCanvas.setFlags(PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
        setEnable(true); 
    }

    @Override
    public void initialize() {
        setEnable(false);
        wakeupOn(condition);
    }

    @Override
    public void processStimulus(Enumeration cond) {
        WakeupOnAWTEvent c = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent mouse = (MouseEvent) e[0];
        pickCanvas.setShapeLocation(mouse);
        PickInfo pi = pickCanvas.pickClosest();
        if(pi != null) { 
            SceneGraphPath sgp = pi.getSceneGraphPath();
            Astro a = (Astro)sgp.getNode(sgp.nodeCount()-2); 
            a.setRotationOnOff();
            if(!a.isSol()){
                a.setOrbitaOnOff();
            }
        }
        wakeupOn(condition);
    }
    
}