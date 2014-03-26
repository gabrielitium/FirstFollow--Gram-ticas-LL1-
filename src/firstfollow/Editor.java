/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.Document;
import javax.swing.text.html.*;
import javax.swing.text.BadLocationException;

/**
 *
 * @author FMK
 */
public class Editor{
  public fraIndex fIndex;
  private ListaArchivos listaArchivos;
  private File archivo;
 // private PrintReader pr;
  private PrintWriter pw;
  private FileReader fr;
  private BufferedReader br;
  public JFileChooser fileChooser;
  private HiloCompilador hc;

  public Editor(fraIndex fIndex)
    {
        this.fIndex=fIndex;
        listaArchivos=new ListaArchivos();
        hc=new HiloCompilador(this);
        hc.start();
    }
    public void nuevo()
    {
       boolean flag=false;
       while(flag==false)
       {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new TextFilter());
        int seleccion=fileChooser.showSaveDialog(fIndex);
         if(seleccion==JFileChooser.APPROVE_OPTION)
            {
              try{
                archivo=fileChooser.getSelectedFile();
                //System.out.println(extension(archivo).getAbsolutePath());
                archivo=extension(archivo);
                if(archivo.exists()!=true){
                    //archivo=fileChooser.getSelectedFile(archivo.getAbsolutePath());
                    //añadiendo a al lista p/ pestañas
                    JPanel panel=new JPanel();
                    JScrollPane JSPanel = new JScrollPane();
                    final JEditorPane zonaTexto=new JEditorPane();
                    JEditorPane mensajes=new JEditorPane();
                    JSplitPane jspPanel=new JSplitPane();

                    JLabel jlContador=new JLabel();
                    zonaTexto.setContentType( "text/html" );
                    mensajes.setContentType( "text/html" );
                    JSPanel.setViewportView(zonaTexto);

                    panel.setLayout(new BorderLayout());
                    panel.add(JSPanel,BorderLayout.CENTER);
                    panel.add(jlContador,BorderLayout.SOUTH);

                    jspPanel.setDividerLocation(400);
                    jspPanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
                    jspPanel.setTopComponent(panel);
                    jspPanel.setRightComponent(new JScrollPane(mensajes));

                    fIndex.jtpEditor.add(archivo.getName(),jspPanel);
                    fIndex.jtpEditor.setSelectedIndex(fIndex.jtpEditor.getTabCount()-1);
                    zonaTexto.grabFocus();
                    EventosTexto listener=new EventosTexto(zonaTexto,jlContador);

                    listaArchivos.add(archivo, panel, zonaTexto, mensajes);
                    pw = new PrintWriter(archivo);
                    pw.close();

                    flag=true;
                }
                else
                {
                    JOptionPane.showMessageDialog(fIndex.jtpEditor, "¡Fichero existente!");
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
    public void abrir()
    {
        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new TextFilter());
        int seleccion=fileChooser.showOpenDialog(fIndex);
         if(seleccion==JFileChooser.APPROVE_OPTION)
         {
            try{
              archivo=fileChooser.getSelectedFile();
              int existe=listaArchivos.existeArchivo(archivo);
              if(existe==-1)
              {
                //System.out.println(archivo.getAbsoluteFile());
                if(archivo.exists()==true){
                    fr=new FileReader(archivo);
                    br=new BufferedReader(fr);

                    JPanel panel=new JPanel();
                    JScrollPane JSPanel = new JScrollPane();
                    JEditorPane zonaTexto=new JEditorPane();
                    JEditorPane mensajes=new JEditorPane();
                    JSplitPane jspPanel=new JSplitPane();

                    JLabel jlContador=new JLabel();                    
                    zonaTexto.setContentType( "text/html" );
                    mensajes.setContentType( "text/html" );
                    JSPanel.setViewportView(zonaTexto);
                    panel.setLayout(new BorderLayout());
                    panel.add(JSPanel,BorderLayout.CENTER);
                    panel.add(jlContador,BorderLayout.SOUTH);

                    jspPanel.setDividerLocation(400);
                    jspPanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
                    jspPanel.setTopComponent(panel);
                    jspPanel.setRightComponent(new JScrollPane(mensajes));


                    fIndex.jtpEditor.add(archivo.getName(),jspPanel);
                    listaArchivos.add(archivo, panel, zonaTexto, mensajes);

                    StringBuilder builder=new StringBuilder();
                    String linea = br.readLine();
                    while (linea != null)
                        {
                        builder.append(linea);
                        builder.append("\n");
                        linea = br.readLine();
                       // System.out.println("posiciòn: "+zonaTexto.getCaretPosition());
                    }
                    //zonaTexto.setText(builder.toString());  
                    //Document doc=new Document(builder.toString());
                    zonaTexto.getDocument().insertString(0, builder.toString(), null);
                    
                    fIndex.jtpEditor.setSelectedIndex(fIndex.jtpEditor.getTabCount()-1);
                    zonaTexto.grabFocus();
                    EventosTexto listener=new EventosTexto(zonaTexto,jlContador);
                }
                else
                {
                      JOptionPane.showMessageDialog(fIndex.jtpEditor, "¡Fichero no existe!");
                }
               }
              else
              {
                  fIndex.jtpEditor.setSelectedIndex(existe);
                  listaArchivos.getTextArea(existe+1).grabFocus();
              }
            }
            catch(Exception e)
            {e.printStackTrace();}

         }
    }
    public void guardar()
    {
       int i=(fIndex.jtpEditor.getSelectedIndex())+1;

       if(i!=-1)
       {
            try{
                File archivo=listaArchivos.getArchivo(i);
                JEditorPane areaTexto=listaArchivos.getTextArea(i);
                //String txt=areaTexto.getDocument().getText(0, areaTexto.getDocument().getLength());
                HTMLDocument ht=(HTMLDocument) areaTexto.getDocument();
                if(archivo.exists()==true)
                {
                  try{
                    pw = new PrintWriter(archivo);
                    pw.print(ht.getText(0, ht.getLength()));
                    pw.close();
                    JOptionPane.showMessageDialog(fIndex, "La gramática ha sido guardada");
                    areaTexto.grabFocus();
                  }
                  catch(Exception e){ e.printStackTrace(); }
                }
                else
                {
                    JOptionPane.showMessageDialog(areaTexto, "Archivo no existe!");
                }
            }
            catch(Exception e)
            {

            }
       }
    }
    public void cerrar()
    {
        int index=fIndex.jtpEditor.getSelectedIndex();
        if(index!=-1)
        {
            int confirmado = JOptionPane.showConfirmDialog(fIndex,"¿Guardar cambios?");
            if (JOptionPane.OK_OPTION == confirmado)
            {
                guardar();
                System.out.println("index "+index);
                listaArchivos.Close(index);
                fIndex.jtpEditor.remove(index);
            }
            else if(JOptionPane.NO_OPTION==confirmado)
            {
                System.out.println("index "+index);
                listaArchivos.Close(index);
                fIndex.jtpEditor.remove(index);
            }
            else
            {
                System.out.println("index "+index);
                listaArchivos.getTextArea(index+1).grabFocus();
            }
        }
    }
    //Cargar gramática
    public Gramatica compilarGramatica()
    {
        synchronized(this){
        Gramatica gramatica=null;
        int i=fIndex.jtpEditor.getSelectedIndex();

        if(i!=-1)
        {
             // guardar();              
              JEditorPane zonaTexto=listaArchivos.getTextArea(i+1);
              JEditorPane Mensajes=listaArchivos.getMensajes(i+1);
              HTMLDocument doc=(HTMLDocument)zonaTexto.getDocument();
              try{
                int index=zonaTexto.getCaretPosition();
                String Texto= new String(doc.getText(0, doc.getLength()));
                Mensajes.setText(""); //<--------------Limpiando área de mensajes
                Sintaxis sint=new Sintaxis(Texto,zonaTexto,Mensajes);                
                if(sint.Sintaxis()==true){
                    //En este caso el análisis léxico fue correcto
                    gramatica=sint.getGramatica();                    
                    //procedemos análisis sintáctico
                    gramatica.ambiguedad.revisionGeneral(Mensajes);
//                    Boolean amb=gramatica.ambiguedad.ambiguo.AnalizarGramaticaConDk(Mensajes,true);
                }                
                sint.coloreaHTML();
                Mensajes.setCaretPosition(0); //<--------------Posicionando en inicio de mensajes
                if(index>0 && index < Texto.length())
                    zonaTexto.setCaretPosition(index);
                zonaTexto.grabFocus();                
              }
              catch(BadLocationException e){ e.printStackTrace(); }
              //gramatica.resetGramatica(); //purifica los arreglos de gramatica

        }
        else{
            System.out.println("No hay gramática");
        }
        return gramatica;
      }
    }
    //Insetamos símbolos del panel lateral.
    public void insertarSimbolo(int sim)
    {
      synchronized(this){
        int i=fIndex.jtpEditor.getSelectedIndex();
        if(i!=-1)
        {
          JEditorPane zonaTexto=listaArchivos.getTextArea(i+1);
          HTMLDocument doc=(HTMLDocument)zonaTexto.getDocument();
          HTMLEditorKit kit=(HTMLEditorKit) zonaTexto.getEditorKit();
              try{
               int index=zonaTexto.getCaretPosition();
                    switch(sim)
                    {
                        case 0:
                               kit.insertHTML(doc, index, "<b> € </b>", 0, 0, HTML.Tag.B);
                               zonaTexto.setCaretPosition(index+2);
                        break;
                        case 1:
                               kit.insertHTML(doc, index, "<b> | </b>", 0, 0, HTML.Tag.B);
                               zonaTexto.setCaretPosition(index+2);
                        break;
                        case 2:
                               kit.insertHTML(doc, index, "<b>--></b>", 0, 0, HTML.Tag.B);
                               zonaTexto.setCaretPosition(index+3);
                        break;
                        case 3:
                               kit.insertHTML(doc, index, "<b> ;</b>", 0, 0, HTML.Tag.B);
                               zonaTexto.setCaretPosition(index+2);
                        break;       
                    }
                    zonaTexto.grabFocus();
                }
              catch(Exception e){e.printStackTrace();}
        }
       }
    }
    //Aplicamos gramática modificada
    public void AplicarCambios(Gramatica ng)
    {
       synchronized(this){
        Gramatica gramatica=null;
        int i=fIndex.jtpEditor.getSelectedIndex();

        if(i!=-1)
        {
             // guardar();
              JEditorPane zonaTexto=listaArchivos.getTextArea(i+1);
              JEditorPane Mensajes=listaArchivos.getMensajes(i+1);
              HTMLDocument doc=(HTMLDocument)zonaTexto.getDocument();
              try{
                int index=zonaTexto.getCaretPosition();
                String Texto= new String(ng.getDoc());
                //String Texto= new String(doc.getText(0, doc.getLength()));
                Mensajes.setText(""); //<--------------Limpiando área de mensajes
                Sintaxis sint=new Sintaxis(Texto,zonaTexto,Mensajes);
                if(sint.Sintaxis()==true){
                    //En este caso el análisis léxico fue correcto
                    gramatica=sint.getGramatica();
                    //procedemos análisis sintáctico
                    gramatica.ambiguedad.revisionGeneral(Mensajes);
                }
                sint.coloreaHTML();
                if(index>0 && index < Texto.length())
                    zonaTexto.setCaretPosition(index);
                zonaTexto.grabFocus();
              }
              catch(Exception e){ e.printStackTrace(); }
              //gramatica.resetGramatica(); //purifica los arreglos de gramatica

        }
        else{
            System.out.println("No hay gramática");
            JOptionPane.showMessageDialog(null, "No existe gramática!");
        }
      }

    }
    //Rectifica que la extensión del archivo sea correcta
    public File extension(File archivo)
    {
       String ext;
       String nombre=archivo.getName();
       File rename=null;
       boolean flag=false;
        if(nombre.indexOf(".ll")!=-1)
        {
            ext=nombre.substring(nombre.length()-4, nombre.length());
            System.out.println("primer paso "+ext);
            if(ext.compareTo(".ll")==0)
            {
                flag=true;
                rename=archivo;
            }
        }
       if(flag==false)
       {
         rename=new File(archivo.getAbsolutePath()+".ll");
       }
       return rename;
    }
}
class TextFilter extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory())
      return true;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1)
      if (s.substring(i + 1).toLowerCase().equals("ll"))
        return true;

    return false;
  }
  public String getDescription() {
    return "Acepta .ll files only.";
  }
}
class htmlFilter extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory())
      return true;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1)
      if (s.substring(i + 1).toLowerCase().equals("html"))
        return true;

    return false;
  }
  public String getDescription() {
    return "Acepta .html files only.";
  }
}

