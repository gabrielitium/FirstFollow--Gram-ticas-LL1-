/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;

import java.util.*;
import javax.swing.*;
import java.io.*;
/**
 *
 * @author usuario
 */
public class ListaArchivos {
    private Vector Archivos;
    private Vector Paneles;
    private Vector AreasTexto;
    private Vector EditorPanel;
    private Vector Gramaticas;
    private Vector Mensajes;

    private ListIterator it;
        public ListaArchivos()
        {
            Archivos=new Vector();
            Paneles=new Vector();
            AreasTexto =new Vector();
            EditorPanel=new Vector();
            Gramaticas=new Vector();
            Mensajes=new Vector();
        }
        public void add(File archivo, JPanel panel, JEditorPane areaTexto, JEditorPane mensajes)
        {
            Archivos.add(archivo);
            Paneles.add(panel);
            AreasTexto.add(areaTexto);
            Mensajes.add(mensajes);
        }
        public void add(JEditorPane editorPane,Gramatica gramatica)
        {
            EditorPanel.add(editorPane);
            Gramaticas.add(gramatica);
        }
        public void replaceGramatica(int index,Gramatica gramatica)
        {
            Gramaticas.setElementAt(gramatica, index);
        }
        public File getArchivo(int index)
        {
            File temp=null;
            it=Archivos.listIterator();
            while(it.hasNext())
            {
                File archivo=(File)it.next();
                if(index==it.nextIndex())
                {
                    temp=archivo;
                    break;
                }
            }
            return temp;
        }
        public Gramatica getGramatica(int index)
        {
            Gramatica temp=null;
            it=Gramaticas.listIterator();
            while(it.hasNext())
            {
                Gramatica gramatica=(Gramatica)it.next();
                if(index==it.nextIndex())
                {
                    temp=gramatica;
                    break;
                }
            }
            return temp;
        }
        public JEditorPane getEditorPanel(int index)
        {
            JEditorPane temp=null;
            it=EditorPanel.listIterator();
            while(it.hasNext())
            {
                JEditorPane editorPanel=(JEditorPane)it.next();
                if(index==it.nextIndex())
                {
                    temp=editorPanel;
                    break;
                }
            }
            return temp;
        }
        public JPanel getPanel(int index)
        {
            JPanel temp=null;
            it=Paneles.listIterator();
            while(it.hasNext())
            {
                JPanel panel=(JPanel)it.next();
                if(index==it.nextIndex())
                {
                    temp=panel;
                    break;
                }
            }
            return temp;
        }
        public JEditorPane getTextArea(int index)
        {
            JEditorPane temp=null;
            it=AreasTexto.listIterator();
            while(it.hasNext())
            {
                JEditorPane areaTexto=(JEditorPane)it.next();
                if(index==it.nextIndex())
                {
                    temp=areaTexto;
                    break;
                }
            }
            return temp;
        }
        public JEditorPane getMensajes(int index)
        {
            JEditorPane temp=null;
            it=Mensajes.listIterator();
            while(it.hasNext())
            {
                JEditorPane panel=(JEditorPane)it.next();
                if(index==it.nextIndex())
                {
                    temp=panel;
                    break;
                }
            }
            return temp;
        }
        public int existeArchivo(File archivo)
        {
          int index=-1;
          it=Archivos.listIterator();
            while(it.hasNext())
            {
                File temp=(File)it.next();
                if(temp.getName().compareTo(archivo.getName())==0)
                {
                    index=(it.nextIndex())-1;
                    break;
                }
            }
            return index;
        }
        public int existeEditorPanel(String Nombre)
        {
          int index=-1;
          it=EditorPanel.listIterator();
            while(it.hasNext())
            {
                JEditorPane temp=(JEditorPane)it.next();
                if(temp.getName().compareTo(Nombre)==0)
                {
                    System.out.println("nombres:"+temp.getName());
                    index=(it.nextIndex())-1;
                    break;
                }
            }
            return index;
        }
        public void Close(int index)
        {
           try{
            Archivos.remove(index);
            Paneles.remove(index);
            AreasTexto.remove(index);
            Mensajes.remove(index);
           }
           catch(Exception e)
           {
              e.printStackTrace();
              System.out.println("No se pudo cerrar el archivo");
           }
        }
        public void ClosePanelGramatica(int index)
        {
           try{
            EditorPanel.remove(index);
            Gramaticas.remove(index);
           }
           catch(Exception e)
           {
              e.printStackTrace();
              System.out.println("No se pudo cerrar esa gramática!");
           }
        }
}
