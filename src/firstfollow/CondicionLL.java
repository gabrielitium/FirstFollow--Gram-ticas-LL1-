/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Derecha
 */
public class CondicionLL {
    jdCondicionLL jc;
    private Gramatica g;
    Tools tools;
    Vector SegReglas;
    boolean Condicion=false;

    public CondicionLL(Gramatica g)
    {
        this.g=g;
        tools=new Tools();
    }
    public void guardarReporteCondicionLL()
    {
        g.reporte.imprimirCondicionLL();
    }
    public boolean VerificarCondicionLL(int opc) //Si opc es 0 se muestra el formulario, si es 1 no.
    {
        int i=0,j=0;
        boolean exito=true;
        jc=new jdCondicionLL(null,true,this);
        if(g.predictivo.CalcularPredictivo(false)==true)
        {
           i=0;
           while(i<g.sizeProduccion())
           {
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#8A0808\">PRIMEROS de "+Prod+" = "+g.cPrediccion.primeros.getPrimeros(Prod)+"</font>",jc.jepPrimeros);
                i++;
           }
            i=0;
           while(i<g.sizeProduccion())
           {
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#045FB4\">SIGUIENTES de "+Prod+" = "+g.cPrediccion.siguientes.getSiguientes(Prod)+"</font>",jc.jepSiguientes);
                i++;
           }
           i=0;
           Condicion=true;
           while(i<g.sizeProduccion())
           {
                SegReglas=g.segmentar();
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#610B38\"> ********************** Revisando condición LL(1) para - ( "+Prod+") - ****************************</font>",jc.jepCondicion);
                Vector pred=RevisarCondicion(Prod);
                System.out.println("Condicion-->"+Condicion);
             //}
                i++;
           }
           if(Condicion==true)
               tools.Mensaje("<font color=\"#0B3861\">  ---- La condición LL(1) se cumple!, se puede implementar un Analizador Sintactico Predictivo con ésta gramática.  ----</font>",jc.jepCondicion);
           else
               tools.Mensaje("<font color=\"#0B3861\">  ---- La condición LL(1) NO se cumple!  ----</font>",jc.jepCondicion);
            if(opc==0)
                jc.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error!, no se pudo calcular conjuntos PREDICTIVOS.");
        }
        return Condicion;
    }
    public Vector RevisarCondicion(String Prod)
    {
        Vector interseccion=new Vector();
        StringBuilder builder=new StringBuilder();
          int pos=g.existeProduccion(Prod);
          int i,j;
          i=0;
          try{
          if(pos!=-1){
            Vector segmento=(Vector)SegReglas.get(pos); //separa por segmentos en una regla, segmentos marcados por |
                //tools.Mensaje("<font color=\"#B45F04\"> Calculando PREDICTIVOS de  "+Prod+"</font>",jpred.jepProcedimiento);
            Vector predictivos=(Vector)g.predictivo.Predictivos.get(pos);
            if(predictivos.size()==2){
                //interseccion=tools.UnirVector(interseccion, (Vector)predictivos.get(i));
                System.out.println(" ---- Contenido de intersección: ---- "+interseccion);
                //while(i<segmento.size()){
                    //Vector reg=(Vector)segmento.get(i); //separa en tokens
                    //Vector predict=(Vector)predictivos.get(i);
                    Vector predict1=(Vector)predictivos.get(0);
                    Vector predict2=(Vector)predictivos.get(1);

                    tools.Mensaje("<font color=\"#0B3B24\">PREDICTIVO("+Prod+"-->"+(Vector)segmento.get(0)+") = "+predict1+"</font>",jc.jepPredictivos);
                    tools.Mensaje("<font color=\"#0B3B24\">PREDICTIVO("+Prod+"-->"+(Vector)segmento.get(1)+") = "+predict2+"</font>",jc.jepPredictivos);
                    builder.append(predict1).append(" ∩ ").append(predict2);
                    interseccion=tools.InterseccionVector(predict1, predict2);
                  //  if(i>0)
                   //     builder.append(" ∩ ").append(predict);
                   // else
                    //    builder.append(predict);
                   // i++;
                //}
            }
            else if(predictivos.size()>2){               
                for(i=0; i<segmento.size();i++){
                    Vector reg=(Vector)segmento.get(i); //separa en tokens
                    Vector predict=(Vector)predictivos.get(i);
                    Vector predictivos2=(Vector)g.predictivo.Predictivos.get(pos);
                    tools.Mensaje("<font color=\"#0B3B24\">PREDICTIVO("+Prod+"-->"+reg+") = "+predict+"</font>",jc.jepPredictivos);
                    for(j=i; j<segmento.size();j++){
                        if(i!=j)
                        {
                            Vector predict2=(Vector)predictivos2.get(j);
                            Vector temp=tools.InterseccionVector(predict, predict2);
                            interseccion=tools.UnirVector(interseccion, temp);
                            builder.append(" { ").append(predict).append(" ∩ ").append(predict2).append("=").append(temp).append(" } ");
                            //interseccion=tools.InterseccionVector(segmento, segmento)
                            //Vector reg=(Vector)segmento.get(i); //separa en tokens
                            //Vector predict=(Vector)predictivos.get(i);
                        }
                    }
                }
                System.out.println(" ---- Contenido de intersección: ---- "+interseccion);
            }
            else{
                Vector reg=(Vector)segmento.get(i); //separa en tokens
                Vector predict=(Vector)predictivos.get(0);
                tools.Mensaje("<font color=\"#0B3B24\">PREDICTIVO("+Prod+"-->"+reg+") = "+predict.get(0)+"</font>",jc.jepPredictivos);
                builder.append(predict);
            }

            if(interseccion.isEmpty()!=true)
             {
                Condicion=false;
             }
             tools.Mensaje("<font color=\"#4B088A\">"+builder+"="+interseccion+"</font>",jc.jepCondicion);
          }
          else{
               System.out.println("No existe producción solicitada.");
          }

          }catch(StackOverflowError e){  //atrapando desbordamiento de pila
           e.printStackTrace(); //System.exit(-1);
           return null;
          }
      return interseccion;
    }
}
