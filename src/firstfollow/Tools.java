/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import javax.swing.text.html.*;
import java.util.*;
/**
 *
 * @author Franco
 */
public class Tools {

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
    public Vector InterseccionVector(Vector A,Vector B)
    {
        Vector C=new Vector();
        Vector Mayor;
        Vector Menor;

            if(A.size()>B.size())
            {  Mayor=A; Menor=B;  }
            else
            {  Mayor=B; Menor=A; }
        //C.addAll(Mayor);
        for(int i=0;i<Menor.size();i++)
        {
            Object obj=Menor.get(i);
            if(Mayor.contains(obj)==true && Menor.contains(obj)==true)
            {
                C.add(obj);
            }
        }
        return C;
    }
}
