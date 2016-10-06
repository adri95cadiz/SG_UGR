package Model;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PointLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

public class Sol extends Astro {
    
    private PointLight luz;
            
    public Sol(float diametro, long velRotacion, String textura) {
        super(diametro, velRotacion, textura);
        
        rotation = rotar();
        BranchGroup figure = new BranchGroup();
        
        figure.addChild(new Sphere (this.diametro/2, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 64, ap));
        rotation.addChild(figure); 
        this.addChild(rotation);
        
        luz = new PointLight (
            new Color3f (0.9f, 0.9f, 0.9f), 
            new Point3f (0.0f, 0.0f, 0.0f),
            new Point3f (1.0f, 0.0f, 0.0f)
        );
        luz.setInfluencingBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 200.0));
        luz.setEnable (true);
        
        this.addChild(luz);
    }
    
    @Override
    public void addSatelite(Astro astro) {
        this.addChild(astro);
    }
    
    @Override
    public void addAnillo(Anillo anillo) {
        this.addChild(anillo);
    }
    
    @Override    
    public boolean isSol(){
        return true;
    }
    
}