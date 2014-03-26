/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.util.*;
/**
 *
 * @author Franco
 */
public class Predictivo {
    private Gramatica g;
    private Vector SegReglas;
    Vector Predictivos;
    public jdPredictivo jpred;
    Tools tools;
    boolean esVisible=false;

    public Predictivo(Gramatica g)
    {
        this.g=g;
        tools=new Tools();
    }
    public void guardarReportePredictivos()
    {
        g.reporte.imprimirPredictivos();
    }
    public boolean CalcularPredictivo(boolean esVisible)
    {
        if(g.cPrediccion.calcularPrimeros(false)==true)
        {
            if(g.cPrediccion.calcularSiguientes(false)==true)
            {                 
                if(Predictivos()==true){
                    if(esVisible==true)
                        jpred.show();
                    return true;
                }
            }
        }
        return false;
    }
    public boolean Predictivos()
    {
        boolean exito=false;
        Sintaxis stx;
        jpred=new jdPredictivo(null,true,this);
        Predictivos=new Vector();
        int i;        
            try{
            tools.Mensaje("Gramática revisada. ",jpred.jepProcedimiento);
            String Texto=g.getDoc().toString();
            jpred.jepGramatica.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jpred.jepGramatica,jpred.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jpred.jepGramatica.setCaretPosition(0);
            System.out.println("INICIA calculo de PREDICTIVOS_______________________________");
           i=0;
           while(i<g.sizeProduccion())
           {
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#0B3861\">PRIMEROS de "+Prod+" = "+g.cPrediccion.primeros.getPrimeros(Prod)+"</font>",jpred.jepPredictivo);
                i++;
           }
           i=0;
           while(i<g.sizeProduccion())
           {
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#0B610B\">SIGUIENTES de "+Prod+" = "+g.cPrediccion.siguientes.getSiguientes(Prod)+"</font>",jpred.jepPredictivo);
                i++;
           }
           i=0;
           while(i<g.sizeProduccion())
            {
                SegReglas=g.segmentar();
                String Prod=g.getProduccion(i);
                //if(primeros.existenPrimeros(Prod)!=true)
             //{ //<--Si no existen primeros de la producción, se calculan.
                 //System.out.println();
                tools.Mensaje("<font color=\"#0B3861\"> ********************** - PREDICTIVO( "+Prod+") - ****************************</font>",jpred.jepProcedimiento);
                Vector pred=Predict(Prod);                                  
                 Predictivos.add(pred);
             //}
                i++;
            }
            exito= true;            
           }
           catch(Exception e){ e.printStackTrace(); }
        return exito;
    }
    public Vector Predict(String Prod)
    {
        Vector pred=new Vector();  //El ORDEN a guardar sera A--> [predict(alfa1),predict(alfa2),...,predict(alfaN)]
          int pos=g.existeProduccion(Prod);
          int i,j;
          i=0;
          try{
          if(pos!=-1){
             Vector segmento=(Vector)SegReglas.get(pos); //separa por segmentos en una regla, segmentos marcados por |
                tools.Mensaje("<font color=\"#B45F04\"> Calculando PREDICTIVOS de  "+Prod+"</font>",jpred.jepProcedimiento);
                while(i<segmento.size()){
                    Vector reg=(Vector)segmento.get(i); //separa en tokens
                    Vector prim=g.cPrediccion.firstForFollow(reg);
                    if(prim!=null){
                        if(g.cPrediccion.existeVacio(prim)==true) //existe vacio en primeros
                        {
                            prim.remove("€");
                            Vector follow=g.cPrediccion.Follow(Prod);
                            Vector tem=g.cPrediccion.UnirVector(prim, follow);
                            pred.add(tem);
                            tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+tem+"</font>",jpred.jepPredictivo);                            
                            tools.Mensaje("<font color=\"#045FB4\">Calculamos PRIMEROS("+reg+") = "+prim+", como contienen '€', se adjunta con SIGUIENTES("+Prod+")="+follow+"</font>",jpred.jepProcedimiento);
                            tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+tem+"</font>",jpred.jepProcedimiento);

                        }
                        else   //no existe vacio en primeros
                        {
                            pred.add(prim);
                            tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+prim+"</font>",jpred.jepPredictivo);                            
                            tools.Mensaje("<font color=\"#088A85\">Calculamos PRIMEROS("+reg+") = "+prim+" y se añaden a PREDICTIVO.",jpred.jepProcedimiento);
                            tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+prim+"</font>",jpred.jepProcedimiento);
                        }
                    }
                    else
                    { // Captura de ERROR en caso de un ciclo
                        System.out.println("   XTX Primeros de "+Prod+" retornó null");
                        return new Vector();
                    }

                    i++;
                }
          }
          else{
               System.out.println("No existe producción solicitada.");
          }

          }catch(StackOverflowError e){  //atrapando desbordamiento de pila
           e.printStackTrace(); //System.exit(-1);
           return null;
          }
      return pred;
    }
}
