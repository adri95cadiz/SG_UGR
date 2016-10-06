package Model;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Universo {
    
    private Fondo background;
    private Escena scene;
    private VirtualUniverse universe;
    private Locale locale;
    private Camara camaraPerspectiva;
    private Camara camaraPlanta;    
    private Camara camaraLuna;
    private Camara camaraNave;
    private Select select;

  
    public Universo (Canvas3D canvas, Canvas3D canvasVariable) {
        
        // UNIVERSO
        universe = new VirtualUniverse();
        locale = new Locale(universe);
        
        // CAMARAS
        camaraPlanta = new Camara(false, false, canvas, new Point3d (0,200,0), new Point3d (0,0,0), new Vector3d (0,0,-1), 0.0032f, 0.3f, 100.0f);
        camaraPerspectiva = new Camara(true, true, canvasVariable, new Point3d (80,80,80), new Point3d (0,0,0), new Vector3d (0,1,0), 45.0f, 0.3f, 100.0f);
        camaraNave = new Camara(true, false, canvasVariable, new Point3d (0,0.5,-0.25), new Point3d (0,0,1), new Vector3d (0,1,0), 45.0f, 0.1f, 30.0f);
        camaraLuna = new Camara(true, false, canvasVariable, new Point3d (0,0.5,0), new Point3d (-1,-0.25,0), new Vector3d (1,1,0), 100.0f, 0.05f, 15.0f);
        
        camaraPerspectiva.compile();
        camaraPlanta.compile();
        
        locale.addBranchGraph(camaraPerspectiva);
        locale.addBranchGraph(camaraPlanta);
        
        camaraPerspectiva.activar();
        camaraPlanta.activar();
        
        // FONDO
        background = new Fondo("images/back.jpg"); 
        
        // ESCENA
        scene = new Escena(camaraLuna, camaraNave); 
        
        // SELECCION
        select = new Select(canvasVariable);
        select.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));
        scene.addChild(select);
        
        background.compile();
        scene.compile();
        
        locale.addBranchGraph(background);
        locale.addBranchGraph(scene);
        
        select.initSearch(scene); 
    }
    
    public void activarCamaraPerspectiva() {
        camaraLuna.desactivar();
        camaraNave.desactivar();
        camaraPerspectiva.activar();
    }
    
    public void activarCamaraNave() {
        camaraLuna.desactivar();
        camaraPerspectiva.desactivar();
        camaraNave.activar();
    }
    
    public void activarCamaraLuna() {
        camaraNave.desactivar();
        camaraPerspectiva.desactivar();
        camaraLuna.activar();
    }
    
    public void closeApp (int code) {
        System.exit (code);
    }
    
}