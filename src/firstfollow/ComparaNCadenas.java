/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
/**
 *
 * @author Derecha
 */
///Esta clase compara los primeros caracteres e identifica el patron,
// p/ clase ambiguedades y factorizacion
public class ComparaNCadenas {
    public Vector Existe;
    public Vector NoExiste;
    public Vector Cadenas;
    public Vector Patron;

    public ComparaNCadenas(Vector Cadenas)
    {
        Existe=new Vector();
        NoExiste=new Vector();
        Patron=new Vector();
        this.Cadenas=Cadenas;
    }
    //cadenas que se parezcan y con concidencia más larga.
    public ComparaNCadenas comparar()
    {
        int cont=0;
        int i=0;        
        while(i<Cadenas.size())
        {
            cont=0;
            Vector tk=(Vector)Cadenas.get(i);
            System.out.println("Tok:"+tk);

            for(int j=i+1; j<Cadenas.size();j++)
            {                
                Vector cm=(Vector)Cadenas.get(j);
                System.out.println("Compara tk:"+tk+" temp:"+cm.firstElement().toString());
                if(tk.get(i).toString().compareTo(cm.firstElement().toString())==0)
                {                    
                    Existe.add(Cadenas.get(j));
                    Cadenas.remove(j); //si existe retiralo
                    cont++;
                }
            }
            if(cont>0){
                Existe.add(Cadenas.get(i));
            }
            else{
                NoExiste.add(Cadenas.get(i));                
            }
            Cadenas.removeElementAt(i);            
        }
        if(Existe.isEmpty()!=true)
            buscarPatron();

        return this;
    }
    public void buscarPatron()
    {
        boolean bd;
        bd=true;
        int i=0;        
        String Token="";

          while( bd==true)
            {
                i=0;
                Vector ex =(Vector)Existe.get(i);
                if(ex.isEmpty()!=true)
                {
                    Token =ex.firstElement().toString();
                    i++;
                    while(i<Existe.size()){
                        Vector comp =(Vector)Existe.get(i);
                        if(comp.isEmpty()!=true && Token.compareTo(comp.firstElement().toString())==0){

                        }
                        else
                        {
                                bd=false;
                                break;
                        }
                        i++;
                    }

                }
                else
                    bd=false;
                if(bd==true){
                    i=0;
                    Patron.add(Token);
                    while(i<Existe.size()){
                        Vector temp=(Vector)Existe.get(i);
                        System.out.println("Eliminar:"+temp.toString());
                        temp.removeElementAt(0);
                        Existe.set(i, temp);
                        i++;
                    }
                }
             }
     }
}
