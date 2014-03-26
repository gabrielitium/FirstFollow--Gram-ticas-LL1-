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
public class Primeros {
    private Vector Producciones;
    private Vector Primeros;

    public Primeros()
    {
        Producciones=new Vector();
        Primeros=new Vector();
    }
    public boolean addPrimeros(String Prod, Vector Prim)
    {
        boolean exito=false;
        if(existenPrimeros(Prod)!=true)
        {
            Producciones.add(Prod);
            Primeros.add(Prim);
        }
        else
            System.out.println("Ya existen primeros para:"+Prod);
        return exito;
    }
    public Vector getPrimeros(String Prod)
    {
        Vector prim=null;
        int i=0;
            while(i<Producciones.size())
            {
                if(Prod.compareTo(Producciones.get(i).toString())==0 ){
                    prim=(Vector)Primeros.get(i);
                    break;
                }
                i++;
            }
        return prim;
    }
    public boolean existenPrimeros(String Prod)
    {
        boolean exito=false;
        int i=0;
            while(i<Producciones.size())
            {
                if(Prod.compareTo(Producciones.get(i).toString())==0 ){
                    exito=true;
                    break;
                }
                i++;
            }
        return exito;
    }
}
