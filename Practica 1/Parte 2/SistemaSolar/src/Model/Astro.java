package Model;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Astro extends BranchGroup {
    
    private boolean movimiento, movimientoOrbita;
    protected float diametro;
    private long velTraslacion;
    private long velRotacion;
    private float distancia;
    private Texture texture;
    private TextureAttributes textureAttributes;
    private Material material;
    protected Appearance ap;
    private RotationInterpolator rotator;
    private RotationInterpolator rotatorAround;
    private TransformGroup rotationAround;
    protected TransformGroup rotation;
    private TransformGroup translation;
    
    public Astro(float diametro, long velTraslacion, long velRotacion, float distancia, String textura, Material material) {
        this.setPickable(true);
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        
        this.movimiento = true;
        this.movimientoOrbita = true;
        this.diametro = diametro;
        this.velTraslacion = velTraslacion;
        this.velRotacion = velRotacion;
        this.distancia = distancia;
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
        rotationAround = rotarAlrededor(); 
        translation = trasladar(); 
        rotation = rotar(); 
        BranchGroup figure = new BranchGroup(); 
        
        figure.addChild(new Sphere (this.diametro/2, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 64, ap)); // se crea la figura y se cuelga del nodo figura 
        rotation.addChild(figure); // la figura se cuelga de la rotación
        translation.addChild(rotation); // la rotación se cuelga de la traslación
        rotationAround.addChild(translation); // la traslación se cuelga de la rotación alrededor
        this.addChild(rotationAround); // la rotación alrededor se cuelga del BranchGroup del planeta
    }
    
    // Este constructor será llamado desde la subclase Sol
    public Astro(float diametro, long velRotacion, String texturePath) {
        this.setPickable(true);
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        
        this.diametro = diametro;
        this.velTraslacion = 0l;
        this.velRotacion = velRotacion;
        this.distancia = 0.0f;
        this.material = new Material(
            new Color3f (1f, 1f, 1f), 
            new Color3f (1f, 1f, 1f), 
            new Color3f (1f, 1f, 1f), 
            new Color3f (1f, 1f, 1f), 
            100f
        );
        
        // creación de la apariencia: textura + material
        texture = new TextureLoader (texturePath, null).getTexture();
        textureAttributes = new TextureAttributes(); 
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);
        ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(textureAttributes);
        ap.setMaterial(this.material);
    }
    
    public void addSatelite(Astro astro) {
        translation.addChild(astro);
    }
    
    public void addAnillo(Anillo anillo) {
        translation.addChild(anillo);
    }
    
    public void addCamara(Camara camara) {
        rotation.addChild(camara);
    }
    
    public void setRotationOnOff() {
        movimiento = !movimiento; 
        rotator.setEnable(movimiento);
    }
    
    public void setOrbitaOnOff() {
        movimientoOrbita = !movimientoOrbita; 
        rotatorAround.setEnable(movimientoOrbita);
    }
    
    public boolean isSol(){
        return false;
    }
    
    
    protected final TransformGroup rotar() {
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
    
    private TransformGroup rotarAlrededor() {
        TransformGroup t = new TransformGroup (); 
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 
        Transform3D t3d = new Transform3D (); 
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velTraslacion, 0, 0, 0, 0, 0); 
        rotatorAround = new RotationInterpolator (value, t, t3d, 0.0f, (float) Math.PI*2.0f); 
        rotatorAround.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0)); 
        rotatorAround.setEnable(true); 
        t.addChild(rotatorAround); 
        
        return t;
    }

    private TransformGroup trasladar() {
        TransformGroup t = new TransformGroup (); 
        Transform3D t3d = new Transform3D (); 
        t3d.setTranslation(new Vector3f(distancia,0,0) ); 
        t.setTransform(t3d); 
        
        return t;
    }
}