
package Model;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class Disco extends Shape3D {
    
    public Disco(float rad1, float rad2, int res1, int res2, Appearance ap) {
        if(res1<3) res1 = 3;    
        if(res2<2) res2 = 2;   
        setGeometry(crearGeometria(rad1, rad2, res1, res2));   
        setAppearance(ap);  
    }

    private IndexedGeometryArray crearGeometria(float rad1, float rad2, int res1, int res2) {
        
        Point3f[] vertices = calcularVertices(rad1, rad2, res1, res2);  
        Vector3f[] normales = calcularNormales(res1, res2);            
        TexCoord2f[] texCoords = calcularTexCoords(res1, res2);        
        int[] indicesVertices = calcularIndicesVertices(res1, res2);    
        int[] indicesTexCoords = calcularIndicesTexCoords(res1, res2); 
        
        IndexedGeometryArray geometria = new IndexedTriangleArray(texCoords.length, GeometryArray.COORDINATES | GeometryArray.NORMALS |  GeometryArray.TEXTURE_COORDINATE_2, indicesVertices.length);
        geometria.setCoordinates(0, vertices);                              // VERTICES
        geometria.setCoordinateIndices(0, indicesVertices);                 // INDICES VERTICES
        geometria.setNormals(0, normales);                                  // NORMALES
        geometria.setNormalIndices(0, indicesVertices);                     // INDICES NORMALES
        geometria.setTextureCoordinates(0, 0, texCoords);                   // TEXTURAS
        geometria.setTextureCoordinateIndices(0, 0, indicesTexCoords);      // INDICES TEXTURAS
        
        return geometria;
    }

    private Point3f[] calcularVertices(float rad1, float rad2, int res1, int res2) {
        Point3f p;
        Transform3D t1, t2, t3;
        float alfa, beta;
        Point3f[] vertices = new Point3f[res1*res2];
        for(int i=0; i<res1; i++) { 
            alfa = (float)(i*2*Math.PI/res1);
            for(int j=0; j<res2; j++) { 
                beta = (float)(j*2*Math.PI/res2);
                p = new Point3f(rad2, 0f, 0f); 
                t1 = new Transform3D();
                t1.rotZ(beta);  
                t2 = new Transform3D();
                t2.setTranslation(new Vector3f(rad1, 0f, 0f));  
                t3 = new Transform3D();
                t3.rotY(alfa); 
                t1.transform(p);
                t2.transform(p);
                t3.transform(p);
                vertices[i*res2+j] = p; 
            }
        }
        
        return vertices;
    }

    private Vector3f[] calcularNormales(int res1, int res2) {
        Vector3f n;
        Transform3D t1, t3;
        float alfa, beta;
        Vector3f[] normales = new Vector3f[res1*res2];
        for(int i=0; i<res1; i++) { 
            alfa = (float)(i*2*Math.PI/res1);
                for(int j=0; j<res2; j++) {
                    beta = (float)(j*2*Math.PI/res2);
                    n = new Vector3f(1f, 0f, 0f); 
                    t1 = new Transform3D();
                    t1.rotZ(beta);  
                    t3 = new Transform3D();
                    t3.rotY(alfa);
                    t1.transform(n);
                    t3.transform(n);
                    n.normalize();  
                    normales[i*res2+j] = n;
            }
        }

        return normales;
    }
    
    private TexCoord2f[] calcularTexCoords(int res1, int res2) {
        
        TexCoord2f[] texCoords = new TexCoord2f[(res1+1)*(res2+1)];
        TexCoord2f texCoord;
        float s, t;
        for(int i=0; i<res1+1; i++) {
            s = (float)i/res1;  
            for(int j=0; j<res2+1; j++) {
                t = (float)j/res2;
                texCoord = new TexCoord2f(s,t);
                texCoords[i*(res2+1)+j] = texCoord; 
            }
        }
        
        return texCoords;
    }
    
    private int[] calcularIndicesVertices(int res1, int res2) {
        int[] indicesVertices = new int[res1*2*res2*3];
        int cnt = 0, supIzda, supDcha, infIzda, infDcha;
        for(int i=0; i<res1; i++) {
            for(int j=0; j<res2; j++) {
                supIzda = res2*i+j;
                supDcha = supIzda+res2;
                infIzda = supIzda+1;
                infDcha = infIzda+res2;
                if(i==res1-1) { 
                    supDcha = j;
                    infDcha = j+1;
                }
                if(j==res2-1) { 
                    infIzda -= res2;
                    infDcha -= res2;
                }
                indicesVertices[cnt] = (supIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infDcha)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (supIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (supDcha)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infDcha)%(res1*res2);
                cnt++;
            }
        }
        
        return indicesVertices;
    }
    
    private int[] calcularIndicesTexCoords(int res1, int res2) {
        int[] indicesTexCoords = new int[res1*2*res2*3];
        int cnt = 0, supIzda, supDcha, infIzda, infDcha;
        for(int i=0; i<res1; i++) {
            for(int j=0; j<res2; j++) {
                supIzda = i*(res2+1)+j;
                supDcha = supIzda+(res2+1);
                infIzda = supIzda+1;
                infDcha = infIzda+(res2+1);
                indicesTexCoords[cnt] = supIzda;
                cnt++;
                indicesTexCoords[cnt] = infDcha;
                cnt++;
                indicesTexCoords[cnt] = infIzda;
                cnt++;
                indicesTexCoords[cnt] = supIzda;
                cnt++;
                indicesTexCoords[cnt] = supDcha;
                cnt++;
                indicesTexCoords[cnt] = infDcha;
                cnt++;
            }
        }
        
        return indicesTexCoords;
    }
    
}