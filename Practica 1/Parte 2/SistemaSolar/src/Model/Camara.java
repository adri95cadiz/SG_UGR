package Model;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Camara extends BranchGroup {
    
    private boolean activa;
    private TransformGroup tg;
    private ViewPlatform vp;
    private View view;
    private Canvas3D canvas;
    private Point3d posicion;   // CAMERA POSITION
    private Point3d interes;    // CAMERA LOOKAT
    private Vector3d vUp;       // CAMERA VUP
    private double anguloOEscala;
    private double planoDelantero;
    private double planoTrasero;
    
    public Camara(boolean perspectiva, boolean movimiento, Canvas3D canvas, Point3d posicion, Point3d interes, Vector3d vUp, double anguloOEscala, double planoDelantero, double planoTrasero) {
        this.setPickable(false);
        
        this.activa = false;
        this.posicion = posicion;
        this.interes = interes;
        this.vUp = vUp;
        this.anguloOEscala = anguloOEscala;
        this.planoDelantero = planoDelantero;
        this.planoTrasero = planoTrasero;
        this.canvas = canvas;
        
        Transform3D transform = new Transform3D(); 
        transform.lookAt(this.posicion, this.interes, this.vUp); 
        transform.invert();
        
        tg = new TransformGroup(transform); 
        vp = new ViewPlatform(); 
        
        if(movimiento) {
            tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            
            MouseRotate myMouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
            myMouseRotate.setFactor(0.005);
            myMouseRotate.setTransformGroup(tg);
            myMouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));

            MouseTranslate myMouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
            myMouseTranslate.setFactor(0.1);
            myMouseTranslate.setTransformGroup(tg);
            myMouseTranslate.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));

            MouseWheelZoom myMouseZoom = new MouseWheelZoom(MouseBehavior.INVERT_INPUT);
            myMouseZoom.setFactor(-2.0);
            myMouseZoom.setTransformGroup(tg);
            myMouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));
            
            tg.addChild(myMouseRotate);
            tg.addChild(myMouseTranslate);
            tg.addChild(myMouseZoom);
        }
       
        tg.addChild(vp); 
        
        view = new View();
        view.setPhysicalBody(new PhysicalBody());
        view.setPhysicalEnvironment(new PhysicalEnvironment());
        
        if(perspectiva) {
            view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
            view.setFieldOfView(Math.toRadians(this.anguloOEscala));
            view.setFrontClipDistance(this.planoDelantero);
            view.setBackClipDistance(this.planoTrasero);
        } else {
            view.setProjectionPolicy(View.PARALLEL_PROJECTION);
            view.setScreenScalePolicy(View.SCALE_EXPLICIT);
            view.setScreenScale(this.anguloOEscala);
            view.setFrontClipDistance(this.planoDelantero);
            view.setBackClipDistance(this.planoTrasero);
        }
        
        view.attachViewPlatform(vp); 
        this.addChild(tg); 
    }
    
    public void activar() {
        if(!activa) {
            view.addCanvas3D(this.canvas); 
            activa = true;
        }
    }
    
    public void desactivar() {
        if(activa) {
            view.removeCanvas3D(this.canvas); 
            activa = false;
        }
    }
    
}