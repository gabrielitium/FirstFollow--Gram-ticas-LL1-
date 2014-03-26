/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
import javax.swing.*;
import javax.swing.text.html.*;
/**
 *
 * @author usuario
 */
public class Ambiguedades {
    Gramatica g;
    //Ambiguo ambiguo;
    public Ambiguedades(Gramatica g)
        {
            this.g=g;
      //      ambiguo=new Ambiguo(g);
        }
    public boolean revisionGeneral(JEditorPane Mensajes)
    {
       HTMLDocument doc=(HTMLDocument)Mensajes.getDocument();
       HTMLEditorKit kit= (HTMLEditorKit) Mensajes.getEditorKit();
        boolean exito=false; //<--Inicializamos exito
            boolean cic=isCiclico(Mensajes);
            boolean rec=isRecursivo();
            boolean fac=isFactorizable();

            if(rec==true){
                try{
                kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#B4045F\"><br>¡Gramática es recursiva!.<br></b> ", 0, 0, HTML.Tag.B);
                }catch(Exception e){ e.printStackTrace(); }
            }
            if(fac==true){
                try{
                kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#B4045F\"><br>¡Gramática necesita factorización!.<br></b> ", 0, 0, HTML.Tag.B);
                }catch(Exception e){ e.printStackTrace(); }
            }
            if(cic==false && rec==false && fac==false)
                exito=true;
        return exito;
    }   
    // Es recursivo
    public boolean isRecursivo()
    {
       boolean exito=false;
       Gramatica noRec=g.clonarVarCons();
       Vector reglasSeg=g.segmentar();  //getSegmentosReglas();
       int i=0,j=0,k=0,nc;

       while(i<reglasSeg.size()) //desglosando por reglas
       {
            String prod=g.getProduccion(i);
            String NProd=prod;
            nc=1;
            while(g.existeNoTerminal(" "+NProd+nc+" ")==true) {  /*System.out.println("Existe:"+NProd+nc); */ nc++; }
            NProd=NProd+nc;
            Vector reg=(Vector)reglasSeg.get(i);
            j=0;
            while(j<reg.size()) //desglosando por segmentos
            {  k=0;
               Vector seg=(Vector)reg.get(j);
                   // System.out.println("Tok:"+seg.get(k)+" size seg:"+seg.size());
                 try{
                  if("€".compareTo((String)seg.get(k))!=0) //<--- Descartamos producciones vacias--------------------
                    if(prod.compareTo((String)seg.get(k))==0 && seg.size()>1)
                    {   k++;
                        StringBuilder sb=new StringBuilder();
                        while(k<seg.size()){ sb.append(seg.get(k));  k++; }
                        exito=true;
                        break;
                    }
                 }
                 catch(Exception e){ e.printStackTrace(); }
                j++;
            }
            if (exito==true)
                break;
            i++;
        }
        return exito;
    }
    //Esisten ciclos
    public boolean isCiclico(JEditorPane Mensajes)
    {
       boolean exito=false;
       Vector reglasSeg=g.segmentar(); //getSegmentosReglas();
       int i=0,j=0,k=0;
       HTMLDocument doc=(HTMLDocument)Mensajes.getDocument();
       HTMLEditorKit kit= (HTMLEditorKit) Mensajes.getEditorKit();
        while(i<reglasSeg.size()) //desglosando por reglas
        {
            String prod=g.getProduccion(i);
            Vector reg=(Vector)reglasSeg.get(i);
            j=0;
            while(j<reg.size()) //desglosando por segmentos
            {  k=0;
               Vector seg=(Vector)reg.get(j);
                    //System.out.println("Tok:"+seg.get(k)+" size reg:"+reg.size());
                 try{
                    if(prod.compareTo((String)seg.get(k))==0 && seg.size()==1)
                    {                                    
                        kit.insertHTML(doc,doc.getLength(),"<b>Ciclo: </b><font color=\"red\"> "+prod+"-->"+seg.get(k)+"</font><br>", 0, 0, HTML.Tag.B);
                        exito=true;
                    }                     
                 }
                 catch(Exception e){ e.printStackTrace(); }
                j++;
            }
            i++;
        }
       return exito;
    }
    public Gramatica EliminarRecursividad(JEditorPane Mensajes)
    {
       Gramatica noRec=g.clonarVarCons();
       Vector reglasSeg=g.segmentar();  //getSegmentosReglas();
       int i=0,j=0,k=0,p,pp, nc,as,bs;
       HTMLDocument doc=(HTMLDocument)Mensajes.getDocument();
       HTMLEditorKit kit= (HTMLEditorKit) Mensajes.getEditorKit();
       boolean bd;

       while(i<reglasSeg.size()) //desglosando por reglas
       {
            String prod=g.getProduccion(i);
            String NProd=prod;
            StringBuilder P=new StringBuilder();
            StringBuilder PPrima=new StringBuilder();
            StringBuilder explicacion=new StringBuilder(); //Narra la explicación de la transformación
            StringBuilder sustituir=new StringBuilder(); // Guarda sustituciones de alfas y betas
            p=0; pp=0; nc=1; as=1; bs=1;
            bd=false;             
            while(g.existeNoTerminal(" "+NProd+nc+" ")==true) {  System.out.println("Existe:"+NProd+nc); nc++; }
            NProd=NProd+nc;
            Vector reg=(Vector)reglasSeg.get(i);
            j=0;
            while(j<reg.size()) //desglosando por segmentos
            {  k=0;               
               Vector seg=(Vector)reg.get(j);
                   // System.out.println("Tok:"+seg.get(k)+" size seg:"+seg.size());
                 try{
                  if("€".compareTo((String)seg.get(k))!=0) //<--- Descartamos producciones vacias--------------------
                    if(prod.compareTo((String)seg.get(k))==0 && seg.size()>1)
                    {   k++;
                        StringBuilder sb=new StringBuilder();
                        while(k<seg.size()){ sb.append(seg.get(k)); k++; }
                        bd=true;
                        if(pp>0) PPrima.append(" | "); else pp++;
                        //System.out.println("Contiene:"+tok.toString().replaceFirst(Prod, ""));
                        sustituir.append("α"+(as++)+" = "+sb+" ").append("<br>");
                        PPrima.append(sb);
                        PPrima.append(" ").append(NProd);
                        explicacion.append("<b>Recursividad:</b><font color=\"red\"> "+prod+"-->"+seg.get(0)+" "+sb+"</font><br>");
                        //kit.insertHTML(doc,doc.getLength(),"<b>Recursividad:</b><font color=\"red\"> "+prod+"-->"+sb+"</font><br>", 0, 0, HTML.Tag.B);
                    }
                    else
                    {
                        StringBuilder sb=new StringBuilder();
                        while(k<seg.size()){ sb.append(seg.get(k));  k++; }
                        //No Recursiva a la izquierda
                        sustituir.append("β"+(bs++)+" = "+sb+" ").append("<br>");
                        if(p>0) P.append(" | "); else p++;
                        //System.out.println("No contiene:"+Reglas.get(i));
                        P.append(" ").append(sb).append(" ").append(NProd);
                    }
                 }
                 catch(Exception e){ e.printStackTrace(); }
                j++;
            }
            if(bd==true){ //es recursivo
                    if(P.toString().isEmpty()==true)
                        P.append(" ").append(NProd).append(" ");
                    PPrima.append(" | € ");

                    noRec.addNoTerminal(NProd);
                    noRec.addReglas(prod, P);
                    noRec.addReglas(NProd, PPrima);
                    explicacion.append("<font color=\"blue\">");
                    explicacion.append(sustituir);
                    explicacion.append("No Terminal:"+prod).append("<br>").append("Auxiliar: "+NProd).append("</font><br>");
                    explicacion.append("Aplicando reglas, se sustituye por:").append("<br>");
                    explicacion.append(prod).append("-->").append(P).append("<br>");
                    explicacion.append(NProd).append("-->").append(PPrima).append("<br>");
                    
                    try{ kit.insertHTML(doc,doc.getLength(),"<b>"+explicacion+"</b><br>", 0, 0, HTML.Tag.B); }
                    catch(Exception e){ e.printStackTrace(); }
            }
            else{
                    noRec.addReglas(prod, new StringBuilder(g.getReglas(i)));
            }
            i++;
        }
        return noRec;
    }
    //análizar si la gramática requiere factorización
    public boolean isFactorizable()
    {
        boolean exito=false;
        StringBuilder build=new StringBuilder();
        StringBuilder br=new StringBuilder();
        Vector reglasSeg;
        int i=0,j=0,k=0,l=0,nc=1;

        Gramatica fac=g.getClon();
        try{
            Gramatica temp=fac.clonarVarCons(); //copiando variables y constantes en nueva gramatica factorizada
            reglasSeg=fac.segmentar(); //getSegmentosReglas(); //convetir reglas en segmentos de tokens
            i=0;
            while(i<reglasSeg.size()) //desglosando por reglas
            {
                String prod=fac.getProduccion(i);
                String regla=fac.getReglas(i);
                Vector reg=(Vector)reglasSeg.get(i);
                ComparaNCadenas cnc= new ComparaNCadenas((Vector)reg);
                cnc=cnc.comparar();

               if(cnc.Existe.isEmpty()!=true) //<--si esta vacio no hay repeticiones en las reglas
                {
                  exito=true; //<---- Gramática es factorizable
                  break;
                }
                i++; //<--Avance a siguiente regla
            }
        }catch(Exception e)
        {  e.printStackTrace();
           JOptionPane.showMessageDialog(null, "¡No se pudo analizar gramática a factorizar.!");
        }
       // System.out.println("Gramatica Factorizada: \n "+fac.getReglas());
       return exito;
    }
    //Factoriza las reglas que lo necesiten en la gramáctica correspondiente
    public Gramatica Factorizar(JEditorPane Mensajes)
    {
        boolean exito=false;
        boolean bd=true;
        StringBuilder build=new StringBuilder();
        StringBuilder br=new StringBuilder();
        Vector reglasSeg;
        int i=0,j=0,k=0,l=0,nc=1,bs=1;
        HTMLDocument doc=(HTMLDocument)Mensajes.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) Mensajes.getEditorKit();

        Gramatica fac=g.getClon();
        System.out.println("Gramatica a Factorizar: \n "+fac.getReglas());
        try{
            reglasSeg=fac.segmentar();  //getSegmentosReglas(); //convetir reglas en segmentos de tokens
            i=0;
            while(i<reglasSeg.size())
            {  System.out.println("Reglas --->"+fac.getReglas(i));
               System.out.println("Segmentos --->"+reglasSeg.get(i++));
            }
            

        while(bd==true)
        {
            bd=false;            
            Gramatica temp=fac.clonarVarCons(); //copiando variables y constantes en nueva gramatica factorizada
            reglasSeg=fac.segmentar();  //getSegmentosReglas(); //convetir reglas en segmentos de tokens
            

            i=0;
            while(i<reglasSeg.size()) //desglosando por reglas
            {
                bs=1;
                String prod=fac.getProduccion(i);                
                String regla=fac.getReglas(i);
                Vector reg=(Vector)reglasSeg.get(i);
                StringBuilder explicacion=new StringBuilder();
                System.out.println("Verificar:"+reg);
                ComparaNCadenas cnc= new ComparaNCadenas((Vector)reg);
                cnc=cnc.comparar();
                                
               if(cnc.Existe.isEmpty()!=true) //<--si esta vacio no hay repeticiones en las reglas
                {
                  bd=true; //<---- se deberá realizar otra revisión a toda la gramática
                   //Es factorizable, agregar nuevas reglas
                  String NProd=prod;
                  nc=1;
                  //busca nueva produccion enumerandola
                  while(temp.existeNoTerminal(" "+NProd+nc+" ")==true) { nc++; }
                    NProd=NProd+nc;
                  temp.addNoTerminal(NProd);
                  //-- busca el patron de equivalencia
                  build=new StringBuilder();
                  k=0;
                  while(k<cnc.Patron.size()) build.append(" ").append(cnc.Patron.get(k++));

                  String patron=build.toString(); //<--Cadena semejante entre las reglas
                  kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#4B8A08\">Factorizar:"+prod+"-->"+regla+" <br></b> ", 0, 0, HTML.Tag.B);
                  kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#4B8A08\">Factor  común:"+patron+" <br></b> ", 0, 0, HTML.Tag.B);

                  build=new StringBuilder();
                  k=0;  
                  //-----------Formando regla que no contiene patron de repetición
                   if(cnc.NoExiste.isEmpty()==false) { //Si existen reglas que no contengan patron de repetición
                     while(k<cnc.NoExiste.size()){
                      //convirtiendo de tokens a regla
                        Vector v=(Vector)cnc.NoExiste.get(k);
                        l=0;
                        br=new StringBuilder();
                        while(l<v.size()) { 
                            br.append(" ").append(v.get(l++)); //<-agrupando Tokens en una cadena
                        }                        
                        //concatenando reglas
                        if(k!=0) build.append(" | ").append(br);
                        else build.append(patron).append(" ").append(NProd).append(" | ").append(br);
                      k++;
                     }
                     explicacion.append("No Terminal:"+prod).append("<br>").append("Auxiliar: "+NProd).append("<br>");
                     explicacion.append("ϒ="+cnc.NoExiste.toString()+"<br>");
                   }
                   else{ // <<<solo se agrega el patrón de repetición.
                      build.append(patron).append(" ").append(NProd).append(" ");
                   }
                  kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#04B4AE\">"+prod+"-->"+build.toString()+"<br></b> ", 0, 0, HTML.Tag.B);
                  System.out.println(prod+"-->"+build.toString());
                  temp.addReglas(prod, build); //<--agregando nueva regla
           //       temp.addReglas(prod, build);
                  build=new StringBuilder();
                  //----------------------
                  k=0; int vacios=0; //<-Conteo de tokens vacios
                  while(k<cnc.Existe.size()){
                    Vector v=(Vector)cnc.Existe.get(k);
                    if(v.isEmpty()!=true){
                      //convirtiendo de tokens a regla
                        l=0;
                        br=new StringBuilder();
                        while(l<v.size()) br.append(" ").append(v.get(l++)); //<-agrupando Tokens en una cadena

                        explicacion.append("β"+(bs++)+" = "+(br.toString())+"  ");
                        //concatenando reglas
                        if(k!=0 && build.length()>0) build.append(" | ").append(br);
                        else build.append(" ").append(br);
                      }
                      else
                          vacios++;
                        k++;
                  }                  
                 if(vacios>0)
                    build.append(" | € ");
                  kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#04B4AE\">"+NProd+"-->"+build.toString()+"<br></b> ", 0, 0, HTML.Tag.B);
                  System.out.println(NProd+"-->"+build.toString());
                  temp.addReglas(NProd, build); //<--agregando nueva regla
                }
                else{ //agregar regla intacta.                   
                    temp.addReglas(prod, new StringBuilder(regla)); //<--regla que no necesita factorización
                }
                kit.insertHTML(doc,doc.getLength(),"<b>"+explicacion+"</b><br>", 0, 0, HTML.Tag.B); //imprime contenido de Betas
                //System.out.println("Cadena:"+cnc.Cadenas);             
                System.out.println("Existe:"+cnc.Existe);
                System.out.println("No existe:"+cnc.NoExiste);
                System.out.println("Patron:"+cnc.Patron);                        
                i++; //<--Avance a siguiente regla
            }
            System.out.println("Gramatica Temporal: \n "+temp.getReglas());
            kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#B4045F\"><br>Reglas obtenidas<br></b> ", 0, 0, HTML.Tag.B);
            kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#B4045F\">"+temp.getReglas().toString().replace("\n", "<br>")+"<br></b> ", 0, 0, HTML.Tag.B);
            fac=new Gramatica();
            fac=temp.getClon();
        }
        }catch(Exception e)
        {  e.printStackTrace();
           JOptionPane.showMessageDialog(Mensajes, "¡No se pudo factorizar ésta gramática.!");
        }
       // System.out.println("Gramatica Factorizada: \n "+fac.getReglas());
        return fac;
    }
}
