/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;
import javax.swing.text.html.*;

/**
 *
 * @author DERECHA
 */
public class HacerReportes {
    private Gramatica g;
    private File archivo;
    JFileChooser fileChooser;
    public HacerReportes(Gramatica g)
    {
        this.g=g;
    }

    public void imprimirPrimeros()
    {
        boolean flag=false;
       while(flag==false)
       {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new htmlFilter());
        int seleccion=fileChooser.showSaveDialog(g.cPrediccion.jp);
         if(seleccion==JFileChooser.APPROVE_OPTION)
            {
              try{
                archivo=fileChooser.getSelectedFile();
                System.out.println(extension(archivo).getAbsolutePath());
                archivo=extension(archivo);
                if(archivo.exists()!=true){
                    PrintWriter pw = new PrintWriter(archivo);
                    pw.println("FIRST FOLLOW --- PRIMEROS y SIGUIENTES");
                    pw.append("Gramática original:");
                    pw.append(g.cPrediccion.jp.jepGramatica.getText());
                    pw.append("");
                    pw.append("Procedimiento PRIMEROS:");
                    pw.append(g.cPrediccion.jp.jepProcedimiento.getText());
                    pw.append("Primeros:");
                    pw.append(g.cPrediccion.jp.jepPrimeros.getText());
                    //pw.print(ht.getText(0, ht.getLength()));
                    pw.close();
                    flag=true;
                }
                else
                {
                    JOptionPane.showMessageDialog(g.cPrediccion.jp, "¡Fichero existente!");
                    flag=false;
                }
               }
              catch(Exception e)
               {   e.printStackTrace();  }
            }
         else
         {
             flag=true;
         }
       }
    }
    public void imprimirSiguientes()
    {
        boolean flag=false;
       while(flag==false)
       {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new htmlFilter());
        int seleccion=fileChooser.showSaveDialog(g.cPrediccion.jp);
         if(seleccion==JFileChooser.APPROVE_OPTION)
            {
              try{
                archivo=fileChooser.getSelectedFile();
                System.out.println(extension(archivo).getAbsolutePath());
                archivo=extension(archivo);
                if(archivo.exists()!=true){
                    PrintWriter pw = new PrintWriter(archivo);
                    pw.println("FIRST FOLLOW --- PRIMEROS y SIGUIENTES");
                    pw.append("Gramática original:");
                    pw.append(g.cPrediccion.jp.jepGramatica.getText());
                    pw.append("");
                    pw.append("Procedimiento PRIMEROS:");
                    pw.append(g.cPrediccion.jp.jepProcedimiento.getText());
                    //pw.append("Primeros:");
                    //pw.append(g.cPrediccion.jp.jepPrimeros.getText());
                    pw.append("Procedimiento SIGUIENTES:");
                    pw.append(g.cPrediccion.js.jepProcedimiento.getText());
                    pw.append("Conjuntos PRIMEROS y SIGUIENTES:");
                    pw.append(g.cPrediccion.js.jepSiguientes.getText());
                    //pw.print(ht.getText(0, ht.getLength()));
                    pw.close();
                    flag=true;
                }
                else
                {
                    JOptionPane.showMessageDialog(g.cPrediccion.jp, "¡Fichero existente!");
                    flag=false;
                }
               }
              catch(Exception e)
               {   e.printStackTrace();  }
            }
         else
         {
             flag=true;
         }
       }
    }
    public void imprimirPredictivos()
    {
       boolean flag=false;
       while(flag==false)
       {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new htmlFilter());
        int seleccion=fileChooser.showSaveDialog(g.cPrediccion.jp);
         if(seleccion==JFileChooser.APPROVE_OPTION)
            {
              try{
                archivo=fileChooser.getSelectedFile();
                System.out.println(extension(archivo).getAbsolutePath());
                archivo=extension(archivo);
                if(archivo.exists()!=true){
                    PrintWriter pw = new PrintWriter(archivo);
                    pw.println("FIRST FOLLOW --- PRIMEROS, SIGUIENTES y PREDICTIVOS");
                    pw.append("Gramática original:");
                    pw.append(g.cPrediccion.jp.jepGramatica.getText());
                    pw.append("");
                    pw.append("Procedimiento PRIMEROS:");
                    pw.append(g.cPrediccion.jp.jepProcedimiento.getText());
                    //pw.append("Primeros:");
                    //pw.append(g.cPrediccion.jp.jepPrimeros.getText());
                    pw.append("Procedimiento SIGUIENTES:");
                    pw.append(g.cPrediccion.js.jepProcedimiento.getText());
                    pw.append("Procedimiento PREDICTIVOS:");
                    pw.append(g.predictivo.jpred.jepProcedimiento.getText());
                    pw.append("Conjuntos PRIMEROS, SIGUIENTES y PREDICTIVOS:");
                    pw.append(g.predictivo.jpred.jepPredictivo.getText());
                    //pw.print(ht.getText(0, ht.getLength()));
                    pw.close();
                    flag=true;
                }
                else
                {
                    JOptionPane.showMessageDialog(g.cPrediccion.jp, "¡Fichero existente!");
                    flag=false;
                }
               }
              catch(Exception e)
               {   e.printStackTrace();  }
            }
         else
         {
             flag=true;
         }
       }
    }
    public void imprimirCondicionLL()
    {
       boolean flag=false;
       while(flag==false)
       {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new htmlFilter());
        int seleccion=fileChooser.showSaveDialog(g.cPrediccion.jp);
         if(seleccion==JFileChooser.APPROVE_OPTION)
            {
              try{
                archivo=fileChooser.getSelectedFile();
                System.out.println(extension(archivo).getAbsolutePath());
                archivo=extension(archivo);
                if(archivo.exists()!=true){
                    PrintWriter pw = new PrintWriter(archivo);
                    pw.println("CONDICIÓN LL(1)");
                    pw.append("Gramática original:");
                    pw.append(g.cPrediccion.jp.jepGramatica.getText());
                    pw.append("");
                    pw.append("Conjuntos PRIMEROS, SIGUIENTES y PREDICTIVOS:");
                    pw.append(g.predictivo.jpred.jepPredictivo.getText());
                    pw.println("CONDICIÓN LL(1)");
                    pw.println(g.cLL.jc.jepCondicion.getText());
                    //pw.print(ht.getText(0, ht.getLength()));
                    pw.close();
                    flag=true;
                }
                else
                {
                    JOptionPane.showMessageDialog(g.cPrediccion.jp, "¡Fichero existente!");
                    flag=false;
                }
               }
              catch(Exception e)
               {   e.printStackTrace();  }
            }
         else
         {
             flag=true;
         }
       }

    }
    public File extension(File archivo)
    {
       String ext;
       String nombre=archivo.getName();
       File rename=null;
       boolean flag=false;
        if(nombre.indexOf(".html")!=-1)
        {
            ext=nombre.substring(nombre.length()-4, nombre.length());
            System.out.println("primer paso "+ext);
            if(ext.compareTo(".html")==0)
            {
                flag=true;
                rename=archivo;
            }
        }
       if(flag==false)
       {
         rename=new File(archivo.getAbsolutePath()+".html");
       }
       return rename;
    }
}
