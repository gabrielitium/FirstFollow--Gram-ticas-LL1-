/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import javax.swing.text.html.*;

/**
 *
 * @author Derecha
 */
public class Factorizacion {
    private Gramatica original;
    private Gramatica modificada;
    private Sintaxis stx;
    public jdFactorizacion jf;
    public fraIndex fIndex;


    public Factorizacion(fraIndex fIndex,Gramatica original){
        this.fIndex=fIndex;
        jf=new jdFactorizacion(fIndex,true,this);
        this.original=original;        
        jf.jepOriginal.setContentType( "text/html" );
        jf.jepModificada.setContentType( "text/html" );
        jf.jepProcedimiento.setContentType( "text/html" );
    }
    public void load()
    {      
           if(original.ambiguedad.isFactorizable()==true)
               show();
           else
               JOptionPane.showMessageDialog(fIndex, " ¡Gramática actual no necesita factorización!. ");
    }
    public void show()
    {
        try{
        Mensaje("Gramática Original ");
        String Texto=original.getDoc().toString();
            jf.jepOriginal.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jf.jepOriginal,jf.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jf.jepOriginal.setCaretPosition(0);
        //Eliminar Recursividad---------------------------------
        Mensaje("<br> Factorizando gramática  <br>");
        modificada=original.ambiguedad.Factorizar(jf.jepProcedimiento);
        Mensaje("Gramática Modificada ");
        Texto=modificada.getDoc().toString();
            jf.jepModificada.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jf.jepModificada,jf.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jf.jepModificada.setCaretPosition(0);
            Mensaje("Listo!!!");
           // System.out.println("Existe rec: "+original.ambiguedad.existeRecursividad());
           // System.out.println("Existen Ciclos:"+original.ambiguedad.existenCiclos(jf.jepProcedimiento));
        }
        catch(Exception e){ e.printStackTrace();    }
        //System.out.println("Texto:"+Texto);
           jf.show();
    }
     public void Mensaje(String msg)
      {
        HTMLDocument doc=(HTMLDocument) jf.jepProcedimiento.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) jf.jepProcedimiento.getEditorKit();
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
