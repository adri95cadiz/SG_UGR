package Model;

import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

public class Anillo extends BranchGroup {
    
    private float radioInterno;
    private float radioExterno;
    private long velRotacion;
    private Texture texture;
    private TextureAttributes textureAttributes;
    private Material material;
    private Appearance ap;
    private RotationInterpolator rotator;
    private TransformGroup rotation;
    
    public Anillo(float radioInterno, float radioExterno, long velRotacion, String textura, Material material) {
        this.setPickable(false);
        
        this.radioInterno = radioInterno;
        this.radioExterno = radioExterno;
        this.velRotacion = velRotacion;
        this.material = material;
        
        // APARIENCIA
        texture = new TextureLoader (textura, null).getTexture();
        textureAttributes = new TextureAttributes(); 
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);
        ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(textureAttributes);
        ap.setMaterial(this.material);
        
        // TRANSFORMACIONES
        rotation = rotar(); 
        BranchGroup figure = new BranchGroup (); 
        figure.addChild(new Disco(radioInterno,radioExterno,64,2,ap)); // El objeto anillo es de la clase Disco
        rotation.addChild(figure);
        
        this.addChild(rotation); 
    }
    
    private TransformGroup rotar() {
        TransformGroup t = new TransformGroup (); 
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 
        Transform3D t3d = new Transform3D ();
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velRotacion, 0, 0, 0, 0, 0);
        rotator = new RotationInterpolator (value, t, t3d, 0.0f, (float) Math.PI*2.0f);
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));
        rotator.setEnable(true); 
        t.addChild(rotator);
        
        return t;
    }
    
}