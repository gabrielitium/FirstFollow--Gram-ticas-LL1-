/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.*;
import javax.swing.text.BadLocationException;
/**
 *
 * @author Derecha
 */
public class Recursividad {
    private Gramatica original;
    private Gramatica modificada;
    private Sintaxis stx;
    public jdRecursividad jr;
    public fraIndex fIndex;


    public Recursividad(fraIndex fIndex,Gramatica original){
        this.fIndex=fIndex;
        jr=new jdRecursividad(fIndex,true,this);
        this.original=original;        
        jr.jepOriginal.setContentType( "text/html" );
        jr.jepModificada.setContentType( "text/html" );
        jr.jepProcedimiento.setContentType( "text/html" );
    }
    public void load()
    {
       jdAvisos jd=new jdAvisos(fIndex,true);
       jd.Mensaje("Existen Ciclos en la gramática, debe eliminarlos primero. Pueden causar ambigüedad.");       
       if(original.ambiguedad.isCiclico(jd.jepMensajes)!=true)
       {           
           if(original.ambiguedad.isRecursivo()==true)
               show();
           else
               JOptionPane.showMessageDialog(fIndex, " No se detecto recursividad en la Gramática actual. ");
       }
       else
       {           
           jd.show();           
       }
    }
    public void show()
    {                
        try{
            Mensaje("Gramática Original ");
        String Texto=original.getDoc().toString();
            jr.jepOriginal.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jr.jepOriginal,jr.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jr.jepOriginal.setCaretPosition(0);
        //Eliminar Recursividad---------------------------------
        Mensaje("<br> Eliminando recursividad: <br>");
        modificada=original.ambiguedad.EliminarRecursividad(jr.jepProcedimiento);
        Mensaje("Gramática Modificada ");
        Texto=modificada.getDoc().toString();
            jr.jepModificada.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jr.jepModificada,jr.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jr.jepModificada.setCaretPosition(0);
            Mensaje("Listo!!!");
           // System.out.println("Existe rec: "+original.ambiguedad.existeRecursividad());
           // System.out.println("Existen Ciclos:"+original.ambiguedad.existenCiclos(jr.jepProcedimiento));
        }
        catch(Exception e){ e.printStackTrace();    }        
        //System.out.println("Texto:"+Texto);
           jr.show();
    }
     public void Mensaje(String msg)
      {
        HTMLDocument doc=(HTMLDocument) jr.jepProcedimiento.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) jr.jepProcedimiento.getEditorKit();
        try{
            //doc.insertString(doc.getLength(), msg+" <br> ", null);
            kit.insertHTML(doc,doc.getLength(),"<b>"+msg+" <br></b> ", 0, 0, HTML.Tag.B);
        }
        catch(Exception be){
            be.printStackTrace();
        }
      }
     public void Aplicar()
     {
        //Manda a llamar editor.aplicar(Gramatica) para aplicar los cambios realizados
        fIndex.AplicarCambios(modificada);
     }
}

/*Funcion de recorrido, reglas por segmentos y mas interno reglas por tokens
    public boolean existeRecursividad(Vector reglasSeg)
    {
       boolean exito=false;
       int i=0,j=0,k=0;
        while(i<reglasSeg.size()) //desglosando por reglas
        {
            Vector reg=(Vector)reglasSeg.get(i);
            j=0;
            while(j<reg.size()) //desglosando por segmentos
            {  k=0;
               Vector seg=(Vector)reg.get(j);
               System.out.println("-------:");
                while(k<seg.size()) //desglosado por tokens
                {
                    System.out.println("          Token:"+seg.get(k));
                    k++;
                }
                j++;
            }
            i++;
        }
       return exito;
    }
 *
 *
 */