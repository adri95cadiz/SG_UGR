package Model;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
import javax.vecmath.Point3d;

class Fondo extends BranchGroup {
    
    private String textura;
    
    public Fondo (String textura) {
        this.setPickable(false);
        this.textura = textura;
    
        Background background = new Background ();
        background.setApplicationBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 200.0));
           
        Appearance app = new Appearance ();
        Texture texture = new TextureLoader (this.textura, null).getTexture();
        app.setTexture (texture);
    
        Primitive sphere = new Sphere (1.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, app);
    
        BranchGroup bgGeometry = new BranchGroup ();        
        bgGeometry.addChild (sphere);
        background.setGeometry (bgGeometry);
    
        this.addChild (background);  
    }
    
}