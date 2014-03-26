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
import java.awt.event.*;
import javax.swing.text.html.HTMLDocument;
import java.io.*;


/**
 *
 * @author Derecha
 */
class EventosTexto implements KeyListener,MouseListener
{
    JEditorPane zonaTexto;
    JLabel jlContador;    
    int contador;
    int pos;

    public EventosTexto(JEditorPane zonaTexto, JLabel jlContador)
    {
        this.zonaTexto=zonaTexto;
        this.jlContador=jlContador;
        contador=1;
        //renglon=1;
        zonaTexto.addKeyListener(this);
        zonaTexto.addMouseListener(this);
        posicionador();        
    }
    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e) {
    
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==38)
        {
            int offset=zonaTexto.getCaretPosition();
            int npos=pos;
            try{
                HTMLDocument doc  = (HTMLDocument) zonaTexto.getDocument();
                JTextArea ta=new JTextArea();
                ta.setText(doc.getText(0, doc.getLength()));
                //System.out.println( );
                npos=ta.getLineOfOffset(offset)+1;
                while(npos==pos && offset>0){
                    npos=ta.getLineOfOffset(offset)+1;
                    offset--;
                }
                if(offset>0)
                    zonaTexto.setCaretPosition(offset);
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        else if(e.getKeyCode()==40)
        {
            System.out.println("Abajo");
            int offset=zonaTexto.getCaretPosition();
            int npos=pos;
            try{
                HTMLDocument doc  = (HTMLDocument) zonaTexto.getDocument();
                JTextArea ta=new JTextArea();
                ta.setText(doc.getText(0, doc.getLength()));
                //System.out.println( );
                npos=ta.getLineOfOffset(offset)+1;
                while(npos==pos && offset<doc.getLength()){
                    npos=ta.getLineOfOffset(offset)+1;
                    offset++;
                }
                if(offset>0)
                    zonaTexto.setCaretPosition(offset);
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        posicionador();
    }

    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        posicionador();
    }

    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        posicionador();
    }

    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    public void posicionador() //throws BadLocationException
    {        
        try{
            pos=0;
            int offset  = zonaTexto.getCaretPosition();
            HTMLDocument doc  = (HTMLDocument) zonaTexto.getDocument();
            pos = zonaTexto.getDocument().getDefaultRootElement().getElementIndex(offset);
            jlContador.setText("Renglon: " + pos);
            JTextArea ta=new JTextArea();
            ta.setText(doc.getText(0, doc.getLength()));
            //System.out.println( );
            pos=ta.getLineOfOffset(offset)+1;
            jlContador.setText("Renglon: " + pos);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

