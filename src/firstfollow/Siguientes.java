/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
/**
 *
 * @author usuario
 */
public class Siguientes {
    private Vector Producciones;
    private Vector Siguientes;

    public Siguientes()
    {
        Producciones=new Vector();
        Siguientes=new Vector();
    }
    public boolean addSiguientes(String Prod, Vector Sig)
    {
        boolean exito=false;
        if(existenSiguientes(Prod)!=true)
        {
            Producciones.add(Prod);
            Siguientes.add(Sig);
        }
        else
            System.out.println("Ya existen siguientes para:"+Prod);
        return exito;
    }
    public Vector getSiguientes(String Prod)
    {
        Vector sig=new Vector();
        int i=0;
            while(i<Producciones.size())
            {
                //System.out.println("   COMPARACION A BUSCAR:-"+Prod+" == "+Producciones.get(i));
                if(Prod.equals(Producciones.get(i))==true ){
                     
                    return (Vector)Siguientes.get(i);
                }
                i++;
            }
        return sig;
    }
    public void concatenaSiguientes(String Prod, Vector Sig)
    {
        int i=0;
            while(i<Producciones.size())
            {
                if(Prod.compareTo(Producciones.get(i).toString())==0 ){
                    //Siguientes.addAll(i, Sig);
                    Vector s=(Vector)Siguientes.get(i);
                    s=UnirVector(s,Sig);
                    Siguientes.set(i, s);
                    break;
                }
                i++;
            }
    }
    public boolean existenSiguientes(String Prod)
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
