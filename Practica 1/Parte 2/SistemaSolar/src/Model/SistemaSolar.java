package Model;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

public class SistemaSolar {
    
    public static void main(String[] args) {
        
        Canvas3D canvasPlaneta = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        Canvas3D canvasVariable = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvasPlaneta.setSize(300, 300);
        canvasVariable.setSize(900, 600);

        Universo universe = new Universo(canvasPlaneta, canvasVariable);
        
        PanelControl panelControl = new PanelControl(canvasPlaneta, canvasVariable, universe);
        
        panelControl.showWindow ();
    }
  
}