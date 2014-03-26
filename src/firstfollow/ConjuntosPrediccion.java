/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
import javax.swing.text.html.*;
import javax.swing.*;
import java.lang.StackOverflowError;
/**
 *
 * @author Derecha
 */
public class ConjuntosPrediccion {
    Primeros primeros;
    Siguientes siguientes;
    public jdPrimeros jp; //ventana de primeros
    public jdSiguientes js;
    private Gramatica g;
    private Vector SegReglas; //Guarda reglas dela forma Producción-->"[[alfa],[beta]]"
    Vector FFF;  // FirstForFollow
    int contaF;  //si pasa de 15 repeticiones se ha ciclado el calculo de primeros llamado por siguientes


    public ConjuntosPrediccion(Gramatica g){
        primeros=new Primeros();
        siguientes=new Siguientes();
        this.g=g;
        SegReglas=new Vector();
        FFF=new Vector();
        contaF=0;
    }
    public void guardarReportePrimeros()
    {
        g.reporte.imprimirPrimeros();
    }
    public void guardarReporteSiguientes()
    {
        g.reporte.imprimirSiguientes();
    }
    public boolean calcularPrimeros(Boolean MuestraVentana)
    {
        boolean exito=false;
        primeros=new Primeros();
        jp=new jdPrimeros(null,true,this);
        Vector segReglas=g.segmentar();
        Sintaxis stx;
        jdAvisos ja=new jdAvisos(null,true);
        int i=0;

       if(g.ambiguedad.revisionGeneral(ja.jepMensajes)==true)
       {
           try{
            Mensaje("Gramática revisada. ",jp.jepProcedimiento);
            String Texto=g.getDoc().toString();
            jp.jepGramatica.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jp.jepGramatica,jp.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jp.jepGramatica.setCaretPosition(0);
           while(i<g.sizeProduccion())
            {
                SegReglas=g.segmentar();
                String Prod=g.getProduccion(i);
                //if(primeros.existenPrimeros(Prod)!=true)
             //{ //<--Si no existen primeros de la producción, se calculan.
                 //System.out.println();                
                 Mensaje("<font color=\"#B45F04\"> ********************** - PRIMEROS( "+Prod+" ) - ****************************</font>",jp.jepProcedimiento);
                 Vector prim=First(Prod);
                 Mensaje("<font color=\"#4B088A\">Primeros de "+Prod+" = "+prim+"</font>",jp.jepPrimeros);
                 Mensaje("<font color=\"#088A85\">  - PRIMEROS( "+Prod+" )= "+prim+" - </font>",jp.jepProcedimiento);
                 primeros.addPrimeros(Prod, prim);
             //}
                i++;
            }
            exito= true;
           }
           catch(Exception e){ e.printStackTrace(); }
           if(MuestraVentana==true)
                jp.show();
       }
       else
       {
        //ja.Mensaje("<b><font color=\"#B4045F\"><br>¡Gramática es recursiva!.<br></b> ");
            ja.show();
       }
        return exito;
    }
     public void Mensaje(String msg, JEditorPane panel)
      {
        HTMLDocument doc=(HTMLDocument) panel.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) panel.getEditorKit();
        try{
            //doc.insertString(doc.getLength(), msg+" <br> ", null);
            kit.insertHTML(doc,doc.getLength(),"<b>"+msg+" <br></b> ", 0, 0, HTML.Tag.B);
        }
        catch(Exception be){
            be.printStackTrace();
        }
      }
    //Recibe cadenas heches tokens y elige los primeros
    public Vector First(String Prod)
    {        
        Vector prim=new Vector();
        int i=0;
        int pos=g.existeProduccion(Prod);
      try{
      if(pos!=-1){
        Vector segmento=(Vector)SegReglas.get(pos); //separa por segmentos en una regla, segmentos marcados por |
        Mensaje("<font color=\"#4B088A\"> Calculando PRIMEROS de  "+Prod+"</font>",jp.jepProcedimiento);
        while(i<segmento.size()){
            Vector reg=(Vector)segmento.get(i); //separa en tokens
            String p=reg.get(0).toString(); //revisamos primer token            
 
            if(p.compareTo("€")==0){
                if(prim.contains(p)==false){
                    prim.add(p);
                    Mensaje("<font color=\"#0B3861\"> ---Aplicando Regla 1 –> Token = €  –> Añadimos { € } a PRIMEROS( "+Prod+" ).</font>",jp.jepProcedimiento);
                }
            }
            else if(g.existeTerminal(" "+p+" ")==true)
            {
                if(prim.contains(p)==false){
                    prim.add(p);
                    Mensaje("<font color=\"#38610B\"> ---Aplicando Regla 2 –> Token es un terminal  –> Añadimos { "+p+" } a PRIMEROS( "+Prod+" ).</font>",jp.jepProcedimiento);
                }
            }
            else if(g.existeNoTerminal(" "+p+" ")==true)
            {
                int j=0, vacios=0;
                boolean soloVacios=true;
                Vector primReg=new Vector(); //<---- Primeros unicamente de este segmento de regla
                Mensaje("<font color=\"#0B3861\"> ---Aplicando Regla 3 –> Token es un No Terminal... Calcular PRIMEROS("+reg+")</font>",jp.jepProcedimiento);
                while(j<reg.size())
                {  //Revisar no terminales concatenados PRIM(ABC)= PRIM (A)U PRIM(B) U PRIM(C)
                   String sp=reg.get(j).toString();
                   if(g.existeTerminal(" "+sp+" ")==true)
                   {
                        prim.add(sp);
                        Mensaje("<font color=\"#0B3861\"> Terminal {"+sp+"} se adjuntan a PRIMEROS( "+Prod+")  ........</font>",jp.jepProcedimiento);
                        System.out.println("Break! se encontro terminal -->"+sp);
                        soloVacios=false;
                        break;
                   }
                   else if(g.existeNoTerminal(" "+sp+" ")==true)
                   { //
                      Vector prs=First(sp);
                       primReg=UnirVector(primReg,prs);

                        System.out.println("Se adjuntan primeros de "+sp+ " |--|> "+prs);
                        Mensaje("<font color=\"#0B3861\"> PRIMEROS("+sp+")={"+prs+"} se adjuntan a PRIMEROS( "+Prod+")  ........</font>",jp.jepProcedimiento);
                        if(existeVacio(prs)==true)
                        {
                          primReg.remove("€");
                          vacios++;
                          //System.out.println("Antes de remover-->"+prs);
                          //System.out.println("Despues  de remover-->"+prs);
                          System.out.println("Existen vacios -->"+prs);
                        }
                        else{
                            soloVacios=false;
                            break;
                        }
                   }
                   else
                   {   System.out.println("No está definido en gramatica : "+sp);     }
                   j++;
                }
                System.out.println("Producción: "+Prod+"  Número de terminales-->"+j+" Número de vacios-->"+vacios);
                System.out.println("Existe Vacio: "+existeVacio(primReg));
                if(soloVacios==true && existeVacio(primReg)==false) //si no se encontro vacio en todos los terminales, se elimina vacio
                    primReg.add("€");

                //Agragamos primeros de este segmento a primeros globales de la producción en curso
                //System.out.println("Antes de Unir-->"+prim);
                prim=UnirVector(prim,primReg);
                //System.out.println("Después de Unir-->"+prim);
            }
            i++; //<--Siguiente regla
        }
      }
      else
          System.out.println("No existe producción solicitada.");
      }
      catch(StackOverflowError e){  //atrapando desbordamiento de pila
           e.printStackTrace(); //System.exit(-1);
           return null;
      }
        return prim;
    }
    public boolean existeVacio(Vector prim)
    {
        boolean exito=false;
        int i=0;
            while(i<prim.size())
            {
               if(prim.get(i).toString().compareTo("€")==0) 
               {
                exito=true;
                break;
               }
               i++;
            }
        return exito;
    }
    public boolean calcularSiguientes(boolean esVisible)
    {
        boolean exito=false;
        siguientes=new Siguientes();
        js=new jdSiguientes(null,true,this);
        Vector segReglas=g.segmentar();
        Sintaxis stx;
        jdAvisos ja=new jdAvisos(null,true);
        int i=0;

       if(g.ambiguedad.revisionGeneral(ja.jepMensajes)==true)
       {
           try{
            Mensaje("Gramática revisada. ",js.jepProcedimiento);
            String Texto=g.getDoc().toString();
            js.jepGramatica.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,js.jepGramatica,js.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            js.jepGramatica.setCaretPosition(0);
            SegReglas=g.segmentar();
            if(calcularPrimeros(false)==false){
                JOptionPane.showMessageDialog(null, "¡No se pudo calcular PRIMEROS!");
                return false;
            }

            siguientes=new Siguientes();
            i=0;
           while(i<g.sizeProduccion())
           {
                String Prod=g.getProduccion(i);
                if(Prod.compareTo(g.getInicial())==0){ //sólo si es símbolo inicial
                    Vector v=new Vector();
                    v.add("$");
                    siguientes.addSiguientes(Prod, v);
                     Mensaje("<font color=\"#4B088A\">Se agrega $ a Siguientes("+Prod+") Símbolo inicial por Regla 1.</font>",js.jepProcedimiento);
                }
                else
                {
                    siguientes.addSiguientes(Prod, new Vector());
                }
                Mensaje("<font color=\"#4B088A\">Primeros de "+Prod+" = "+primeros.getPrimeros(Prod)+"</font>",js.jepSiguientes);
                i++;
           }
           i=0;
           System.out.println("Calcula Siguientes*----------------------------");
           while(i<g.sizeProduccion())
            {
               calcularPrimeros(false);
                 //SegReglas=g.segmentar();
                 String Prod=g.getProduccion(i);
                 Mensaje("<font color=\"#4B088A\"> ********************** - SIGUIENTES( "+Prod+" ) - ****************************</font>",js.jepProcedimiento);
                 Vector sig=Follow(Prod);
                 siguientes.concatenaSiguientes(Prod, sig);
                 Mensaje("<font color=\"#8A084B\">Siguientes de "+Prod+" = "+siguientes.getSiguientes(Prod).toString()+"</font>",js.jepSiguientes);
                 Mensaje("<font color=\"#585858\">Siguientes de "+Prod+" = "+siguientes.getSiguientes(Prod).toString()+"</font>",js.jepProcedimiento);
                 //siguientes.addSiguientes(Prod, sig);
                 i++;
            }
            exito=true;
           }
           catch(Exception e){ e.printStackTrace(); }
           if(esVisible==true)
                js.show();
       }
       else
       {
        //ja.Mensaje("<b><font color=\"#B4045F\"><br>¡Gramática es recursiva!.<br></b> ");
            ja.show();
       }
        return exito;
    }
    public Vector Follow(String SProd)
    {
        Vector sig=new Vector();
        int i=0,j=0,k=0;
        int pos=g.existeProduccion(SProd);
      try{
      if(pos!=-1){
          Mensaje("<font color=\"#B45F04\">Calculando SIGUIENTES de  "+SProd+"</font>",js.jepProcedimiento);

          while(k<g.numProducciones())
          {
            Vector segmento=(Vector)SegReglas.get(k); //obtener una regla
            String Prod=g.getProduccion(k);
           // System.out.println("Segmento: "+segmento);            
            i=0;
            while(i<segmento.size()){  // segmentos marcados por |
                Vector reg=(Vector)segmento.get(i); //separa en tokens                
                j=0;
                while(j<reg.size())
                {
                    String p=reg.get(j).toString(); //revisamos que el token sea igual a la producción
                    Boolean agregaSig=false;
                    if(p.compareTo(SProd)==0){ //<--- Igual a la regla de producción. Procedemos a hallar el siguiente
                       Mensaje("<font color=\"#5858FA\"> ++ Producción: "+Prod+"-->"+reg+"</font>",js.jepProcedimiento);
                        if ((j+1)<reg.size())//no esta vacío y hay avance de lado derecho
                        {
                            String p2=reg.get(j+1).toString(); //revisamos que el token sea igual a la producción
                          //  System.out.println(">>>>  SIGUIENTE->    "+p2);
                            List lista=reg.subList(j+1, reg.size());
                            Vector subvec=new Vector(lista);
                            //System.out.println("  -->Obtener primeros de :"+subvec);
                            Vector prim=firstForFollow(subvec);
                                if(prim.equals(null))
                                    return null;
                            Mensaje("<font color=\"#8A084B\">Aplicando regla 2. </font>",js.jepProcedimiento);
                            Mensaje("<font color=\"#8A084B\">  Primeros("+subvec+")={"+prim+"}",js.jepProcedimiento);                            
                            if(existeVacio(prim)==true)
                            {
                                Mensaje("<font color=\"#8A084B\">  Primeros("+subvec+") contiene €. Aplicar regla 3. </font>",js.jepProcedimiento);
                                Mensaje("<font color=\"#8A084B\">  Se elimina € de Primeros("+subvec+") </font>",js.jepProcedimiento);
                                prim.remove("€");                              
                                agregaSig=true;
                            }                            
                            sig=UnirVector(sig,prim);
                        }
                        else{  //esta vacío  -----------Primera Regla
                           Mensaje("<font color=\"#8A084B\">Siguientes("+SProd+") en "+reg+"  está vacío. Aplicar regla 3. </font>",js.jepProcedimiento);
                           agregaSig=true;
                         //   System.out.println("Siguientes("+SProd+") en "+reg+"  esta vacio. Aplicar regla 3.");                            
                        }

                       if(agregaSig==true)
                       {
                          Mensaje("<font color=\"#0B610B\">Se agregan SIGUIENTES("+Prod+")="+siguientes.getSiguientes(Prod)+" en  SIGUIENTES("+SProd+")  </font>",js.jepProcedimiento);
                          System.out.println("   ### Agregar Siguientes de : "+SProd+"  EN  Prod -: "+Prod);
                           if(SProd.equals(Prod)==true)
                            {
                                String ProdGen=buscarProdGenerador(Prod);
                                if(ProdGen!=null){
                                    System.out.println("   Get SIGUIENTES ---- ;) "+siguientes.getSiguientes(ProdGen));
                                    sig=UnirVector(sig,siguientes.getSiguientes(ProdGen));
                                }
                            }
                            else
                            {
                                System.out.println("   Get SIGUIENTES ----2 ;) "+siguientes.getSiguientes(Prod).toString());
                                sig=UnirVector(sig,siguientes.getSiguientes(Prod));
                            }
                       }
                    }
                    j++;
                 }
                i++; //<--Siguiente regla
            }
            k++;
        }
      }
      else
          Mensaje("<font color=red>No existe producción solicitada.->"+SProd+"</font>",js.jepProcedimiento );
      }
      catch(StackOverflowError e){  //capturamos la excepción de OVerflow          
           e.printStackTrace(); //System.exit(-1);
           return null;
     }

        return sig;
    }
    public String buscarProdGenerador(String Prod)
    {
       int i=0,j=0;
       String cad=null;
       boolean exito=false;
           while(i<g.numProducciones())
           {
              Vector segmento=(Vector)SegReglas.get(i);
              j=0;
              while(j<segmento.size())
              {
                Vector regla=(Vector)segmento.get(j);
                int pos=0;
                if(regla.size()>1)  //buscamos el ultimo token de cada regla
                    pos=regla.size()-1;
              //  System.out.println("Ultimo de "+regla+" -"+regla.get(pos).toString()+" pos="+pos);
                 if(regla.get(pos).toString().compareTo(Prod)==0)
                 {
                    String tmp=g.getProduccion(i);
                    if(tmp.compareTo(Prod)!=0){
                        cad=tmp;
                        exito=true;
                        break;
                    }
                 }
                j++;
              }
              if(exito==true) break;
              i++;
           }
       return cad;
    }
    public Vector firstForFollow(Vector vec)
    {
       Vector prim=new Vector();
       String tok=vec.get(0).toString();
       System.out.println("Calcular FirstForFollow de -->"+vec);
       if(vec.equals(FFF))
       {
            contaF++;
            if(contaF>15){
                System.out.println("Existe error en ciclo al calcular primeros");
                return null;
            }
       }
       else
       {
            contaF=0;
            FFF=new Vector(vec);
       }       

       try{
        if(tok.compareTo("€")==0)
        {
            prim.add("€");
        }
        else if(g.existeTerminal(" "+tok+" ")==true) // --------------Segunda Regla
        {
            prim.add(tok);
        }
        else if(g.existeNoTerminal(" "+tok+" ")==true)
        {
            int j=0, vacios=0;
                boolean soloVacios=true;
                Vector primReg=new Vector(); //<---- Primeros unicamente de este segmento de regla
                //Mensaje("<font color=\"#0B3861\"> ---Aplicando Regla 3 –> Token es un No Terminal... Calcular PRIMEROS("+reg+")</font>",jp.jepProcedimiento);
                while(j<vec.size())
                {  //Revisar no terminales concatenados PRIM(ABC)= PRIM (A)U PRIM(B) U PRIM(C)
                   String sp=vec.get(j).toString();
                   if(g.existeTerminal(" "+sp+" ")==true)
                   {
                        prim.add(sp);
                        //Mensaje("<font color=\"#0B3861\"> Terminal {"+sp+"} se adjuntan a PRIMEROS( "+Prod+")  ........</font>",jp.jepProcedimiento);
                        //System.out.println("Break! se encontro terminal -->"+sp);
                        soloVacios=false;
                        break;
                   }
                   else if(g.existeNoTerminal(" "+sp+" ")==true)
                   { //
                      Vector prs=First(sp);
                       primReg=UnirVector(primReg,prs);

                       // System.out.println("Se adjuntan primeros de "+sp+ " |--|> "+prs);
                        //Mensaje("<font color=\"#0B3861\"> PRIMEROS("+sp+")={"+prs+"} se adjuntan a PRIMEROS( "+Prod+")  ........</font>",jp.jepProcedimiento);
                        if(existeVacio(prs)==true)
                        {
                          primReg.remove("€");
                          vacios++;
                          //System.out.println("Antes de remover-->"+prs);
                          //System.out.println("Despues  de remover-->"+prs);
                         // System.out.println("Existen vacios -->"+prs);
                        }
                        else{
                            soloVacios=false;
                            break;
                        }
                   }
                   else
                   {   System.out.println("No está definido en gramatica : "+sp);     }
                   j++;
                }
               // System.out.println("Producción: "+Prod+"  Número de terminales-->"+j+" Número de vacios-->"+vacios);
                //System.out.println("Existe Vacio: "+existeVacio(primReg));
                if(soloVacios==true && existeVacio(primReg)==false) //si no se encontro vacio en todos los terminales, se elimina vacio
                    primReg.add("€");

                //Agragamos primeros de este segmento a primeros globales de la producción en curso
                //System.out.println("Antes de Unir-->"+prim);
                prim=UnirVector(prim,primReg);
                //System.out.println("Después de Unir-->"+prim);
        }
        else{ System.out.println("No se que hacer con token: "+ tok);  }
      }
      catch(StackOverflowError e){           
           e.printStackTrace(); //System.exit(-1);
           return null;
      }

      return prim;
    }
    public static void endless()
    {
        endless();
    }
    public Vector UnirVector(Vector A,Vector B)
    {
        Vector C=new Vector();
        C.addAll(A);
        for(int i=0;i<B.size();i++)
        {
            Object obj=B.get(i);
            if(C.contains(obj)!=true )
            {
                C.add(obj);
            }
        }
        return C;
    }
}
