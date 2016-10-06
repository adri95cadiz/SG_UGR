package Model;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

class Escena extends BranchGroup {  
    
    public Escena(Camara camaraLuna, Camara camaraNave) {
        // Creación nave
        Nave nave = new Nave(
            "models/E-TIE-I/E-TIE-I.obj"/*"models/FA-22_Raptor/FA-22_Raptor.obj"*//*"models/naveEspacial/naveEspacial.obj"*/,     // MODELO
            20000,                          // TIEMPO ANIMACION
            new Point3f[] {                 // RECORRIDO PUNTOS
                new Point3f(10f,10f,-10f),   new Point3f(10f,15f,-5f),
                new Point3f(10f,20f,0f),     new Point3f(10f,15f,5f),
                new Point3f(10f,10f,10f),    new Point3f(-10f,10f,10f),
                new Point3f(-10f,10f,-10f),  new Point3f(10f,10f,-10f)
            },
            new AxisAngle4f[] {             // RECORRIDO ANGULOS
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(0)),
                new AxisAngle4f(1.0f, 0.0f, 0.0f, (float) Math.toRadians(315)),
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(0)),
                new AxisAngle4f(1.0f, 0.0f, 0.0f, (float) Math.toRadians(45)),
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(270)),
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(180)),
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(90)),
                new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(360))
            },
            new float[] {                   // RECORRIDO ALPHAS
                0f, 0.14f, 0.28f, 0.42f, 0.56f, 0.7f, 0.84f, 1f
            }
        );
        
        // MATERIALES
        Material materialAstros = new Material (
            new Color3f (0.6f, 0.6f, 0.6f), // AMBIENTAL
            new Color3f (0.6f, 0.6f, 0.6f), // DIFUSA
            new Color3f (0.6f, 0.6f, 0.6f), // EMISIVA
            new Color3f (0.2f, 0.2f, 0.2f), // ESPECULAR
            10f                             // BRILLO
        );
        Material materialAnillos = new Material (
            new Color3f (0.8f, 0.8f, 0.8f), // AMBIENTAL
            new Color3f (0.7f, 0.7f, 0.7f), // DIFUSA
            new Color3f (0.6f, 0.6f, 0.6f), // EMISIVA
            new Color3f (0.2f, 0.2f, 0.2f), // ESPECULAR
            10f                             // BRILLO
        );
        
        // ASTROS
        Sol sol = new Sol(
                10f,                            // DIAMETRO
                5000l,                          // ROTACION
                "images/sol.jpg"                  // TEXTURA
        );
        Astro mercurio =  new Astro(
                0.49f,                          // DIAMETRO
                18000l,                         // TRANSLACION
                10000l,                         // ROTACION
                7f,                             // DISTANCIA
                "images/mercurio.jpg",            // TEXTURA
                materialAstros                  // MATERIAL
                
        );
        Astro venus =  new Astro(
                1.21f, 
                25000l, 
                -100000l, 
                9.5f, 
                "images/venus.jpg", 
                materialAstros
        );
        Astro tierra = new Astro(
                1.27f, 
                60000l, 
                3000l, 
                12.5f, 
                "images/tierra.jpg", 
                materialAstros
        );
        Astro luna = new Astro(
                0.34f, 
                9000l, 
                0l, 
                1f, 
                "images/luna.jpg", 
                materialAstros
        );
        Astro marte = new Astro(
                0.68f, 
                75000l, 
                5000l, 
                15f, 
                "images/marte.jpg", 
                materialAstros
        );
        Astro fobos = new Astro(
                0.11f, 
                3100l, 
                3100l, 
                0.5f, 
                "images/fobos.jpg", 
                materialAstros
        );
        Astro deimos = new Astro(
                0.1f, 
                3200l, 
                3200l, 
                0.7f, 
                "images/deimos.jpg", 
                materialAstros
        );
        Astro jupiter = new Astro(
                5f, 
                90000l, 
                2900l, 
                23f, 
                "images/jupiter.jpg", 
                materialAstros
        );
        Astro io = new Astro(
                0.36f, 
                3300l, 
                3300l, 
                2.8f, 
                "images/io.jpg", 
                materialAstros
        );
        Astro europa = new Astro(
                0.31f, 
                3500l, 
                3500l, 
                3.3f, 
                "images/europa.jpg", 
                materialAstros
        );
        Astro calisto = new Astro(
                0.48f, 
                6000l, 
                6000l, 
                4.6f, 
                "images/calisto.jpg", 
                materialAstros
        );
        Astro saturno = new Astro(
                4f, 
                120000l, 
                2900l, 
                35f, 
                "images/saturno.jpg", 
                materialAstros
        );
        Astro urano = new Astro(
                2.6f, 
                150000l, 
                2900l, 
                45f, 
                "images/urano.jpg", 
                materialAstros
        );
        Astro miranda = new Astro(
                0.12f, 
                3250l, 
                3250l, 
                1.4f, 
                "images/miranda.jpg", 
                materialAstros
        );
        Astro ariel = new Astro(
                0.13f, 
                3400l, 
                3400l, 
                1.6f, 
                "images/ariel.jpg",  
                materialAstros
        );
        Astro titania = new Astro(
                0.16f, 
                4000l,  
                4000l, 
                2.3f, 
                "images/titania.jpg", 
                materialAstros
        );
        Astro neptuno = new Astro(
                2.5f, 
                200000l, 
                2900l, 
                51f, 
                "images/neptuno.jpg", 
                materialAstros
        );
        Astro triton = new Astro(
                0.27f, 
                -36000l, 
                -36000l, 
                1.6f, 
                "images/triton.jpg", 
                materialAstros
        );
        
        // ANILLOS
        Anillo a = new Anillo(
                3.76f,                          // RADIO INTERNO
                0.24f,                          // RADIO EXTERNO
                50000l,                         // ROTACION
                "images/anilloa.jpg",             // TEXTURA
                materialAnillos                 // MATERIAL
        );
        Anillo b = new Anillo(
                3.11f, 
                0.39f, 
                50000l, 
                "images/anillob.jpg", 
                materialAnillos
        );
        Anillo c = new Anillo(
                2.375f, 
                0.325f, 
                50000l, 
                "images/anilloc.jpg", 
                materialAnillos
        );
        
        // ASTROS
        tierra.addSatelite(luna);
        marte.addSatelite(fobos); 
        marte.addSatelite(deimos);
        jupiter.addSatelite(io); 
        jupiter.addSatelite(europa); 
        jupiter.addSatelite(calisto);
        urano.addSatelite(miranda); 
        urano.addSatelite(ariel); 
        urano.addSatelite(titania); 
        neptuno.addSatelite(triton);
        sol.addSatelite(mercurio); 
        sol.addSatelite(venus); 
        sol.addSatelite(tierra); 
        sol.addSatelite(marte); 
        sol.addSatelite(jupiter); 
        sol.addSatelite(saturno); 
        sol.addSatelite(urano); 
        sol.addSatelite(neptuno);
        
        // ANILLOS
        saturno.addAnillo(a); 
        saturno.addAnillo(b); 
        saturno.addAnillo(c);
        
        // SOL Y NAVE
        this.addChild(sol);
        this.addChild(nave);
        
        // CAMARAS
        luna.addCamara(camaraLuna);
        nave.addCamara(camaraNave);
    }
    
}