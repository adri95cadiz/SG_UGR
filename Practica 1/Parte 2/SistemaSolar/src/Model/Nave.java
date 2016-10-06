package Model;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.io.FileNotFoundException;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

public class Nave extends BranchGroup {
    
    private long velocidad;
    private float[] knots;          // ALPHAS
    private Point3f[] posiciones;   // POSICIONES
    private Quat4f[] angulos;       // ANGULOS
    private RotPosPathInterpolator interpolator;
    private TransformGroup transform;
    
    public Nave(String obj, long velocidad, Point3f[] posiciones, AxisAngle4f[] angulos, float[] knots) {
        this.setPickable(false);
        this.velocidad = velocidad;
        this.posiciones = new Point3f[posiciones.length];
        this.angulos = new Quat4f[posiciones.length];
        this.knots = new float[posiciones.length];
        for(int i=0; i<posiciones.length; i++) {
            this.posiciones[i] = posiciones[i];
            this.angulos[i] = new Quat4f();
            this.angulos[i].set(angulos[i]);
            this.knots[i] = knots[i];
        }
        
        transform = mover(); 
        
        // ESCALAMOS LA NAVE, ES DEMASIADO PEQUEÑA
        TransformGroup scale = new TransformGroup();
        Transform3D t3d = new Transform3D (); 
        t3d.setScale(1.5); 
        scale.setTransform(t3d); 
        
        BranchGroup figure = new BranchGroup (); 
        figure.addChild(importarModelo(obj));
        
        scale.addChild(figure); 
        transform.addChild(scale); 
        this.addChild(transform); 
        
    }
    
    public void addCamara(Camara camara) {
        transform.addChild(camara);
    }
    
    private TransformGroup mover() {        
        TransformGroup t = new TransformGroup ();
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 
        Transform3D t3d = new Transform3D ();
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velocidad, 0, 0, 0, 0, 0); 
        interpolator = new RotPosPathInterpolator (value, t, t3d, knots, angulos, posiciones); 
        interpolator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));
        interpolator.setEnable(true); 
        t.addChild(interpolator); 
        
        return t;
    }
    
    private BranchGroup importarModelo(String obj) {        
        BranchGroup bg = new BranchGroup();
        Scene modelo = null; 
        ObjectFile archivo = new ObjectFile (ObjectFile.RESIZE | ObjectFile.STRIPIFY | ObjectFile.TRIANGULATE );
        try {
            modelo = archivo.load (obj);
        } catch (FileNotFoundException e) {
            System.err.println (e);
            System.exit(1);
        } catch (ParsingErrorException e) {
            System.err.println (e);
            System.exit(1);
        } catch (IncorrectFormatException e) {
            System.err.println (e);
            System.exit(1);
        }
        bg.addChild ( modelo.getSceneGroup() );   
        
        return bg;
    }
    
}