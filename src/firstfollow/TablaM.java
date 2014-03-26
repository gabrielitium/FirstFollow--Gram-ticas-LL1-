/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;
import java.awt.Font;

/**
 *
 * @author Derecha
 */
public class TablaM {
    private Gramatica g;
    public jdTablaM jdTabla;
    public JTable tablaM;
    private Tools tools;
    private Vector SegReglas;
    private Vector Matriz[][];
    Vector Variables;
    Vector Constantes;
    Vector Predictivos;

    public TablaM(Gramatica g){
        this.g=g;
        tools=new Tools();
    }
    public void iniciarTabla()
    {
        Variables=g.getVariables();
        Constantes=g.getConstantes();
        DefaultTableModel Modelo=new DefaultTableModel();        
        //tabla=new JTable(2,2);
        tablaM=new JTable(Variables.size(),Constantes.size());        
        //Llenamos Valores
        Modelo.addColumn("     ");
        for(int i=0;i<Constantes.size();i++){
            Modelo.addColumn(Constantes.get(i));
        }
        Modelo.addColumn("$");
        for(int i=0;i<Variables.size();i++){
            //Modelo.setValueAt(Variables.get(0), 1, 1);
            Modelo.addRow(new Vector());
            Modelo.setValueAt(Variables.get(i), i, 0);
        }
        tablaM.setFont(new Font("Monospaced", Font.BOLD, 14));
        tablaM.setModel(Modelo);
        Matriz=new Vector[Modelo.getRowCount()][Modelo.getColumnCount()];

        TableColumnModel ModCol=tablaM.getColumnModel();

        //Establecemos ancho de las celdas
        for(int i=0;i<Modelo.getColumnCount();i++){
             TableColumn columna=ModCol.getColumn(i);
             columna.setPreferredWidth(150);
        }
        ///-------------------
        tablaM.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //---Para que aparezcan los Scrolls
        ///-------------------
    }
    public boolean crearTabla(boolean isVisible)
    {
        jdTabla=new jdTablaM(null,true,this);
        Sintaxis stx;
        try{
            tools.Mensaje("Gramática revisada. ",jdTabla.jepProcedimiento);
            String Texto=g.getDoc().toString();
            jdTabla.jepGramatica.getDocument().insertString(0, Texto, null);
            stx=new Sintaxis(Texto,jdTabla.jepGramatica,jdTabla.jepProcedimiento);
            stx.Sintaxis();
            stx.coloreaHTML();
            jdTabla.jepGramatica.setCaretPosition(0);
            System.out.println("INICIA creaciòn de TABLA M  _______________________________");
        iniciarTabla();
        if(llenarTabla()==false)
            return false;
        jdTabla.insertarTabla(tablaM);
        if(isVisible==true)
           jdTabla.show();
        }
        catch(Exception e){
            System.out.println("Exception al crear Tabla: "+e);
            return false;
        }
        return true;
    }
   public boolean llenarTabla()
    {
        boolean exito=false;
        int i=0;
        try{
        if(g.predictivo.CalcularPredictivo(false)==true)
        {
            while(i<g.sizeProduccion())
            {
                SegReglas=g.segmentar();
                String Prod=g.getProduccion(i);
                tools.Mensaje("<font color=\"#0B3861\"> ********************** - AGREGANDO Producciones de( "+Prod+") - ****************************</font>",jdTabla.jepProcedimiento);
                //Vector pred=Predict(Prod);
                calcularCeldas(Prod);
                i++;
            }
        exito= true;
        }
        else
            JOptionPane.showMessageDialog(null, "Error al llenar tabla. Conjuntos de predicción incorrectos.");

        }
        catch(Exception e){ e.printStackTrace(); }
        return exito;
    }
    public void calcularCeldas(String Prod)
    {
        //Vector pred=new Vector();  //El ORDEN a guardar sera A--> [predict(alfa1),predict(alfa2),...,predict(alfaN)]
          int pos=g.existeProduccion(Prod);
          int i,j,k;
          i=0;
          try{
          if(pos!=-1){
             Vector segmento=(Vector)SegReglas.get(pos); //separa por segmentos en una regla, segmentos marcados por |
             int posRen=g.existeVariable(Prod);

                tools.Mensaje("<font color=\"#B45F04\"> Revisando Producción  "+Prod+"</font>",jdTabla.jepProcedimiento);
                i=0;
                while(i<segmento.size()){                    
                    Vector reg=(Vector)segmento.get(i); //separa en tokens
                    System.out.println("Calcular primeros de :"+reg);
                    Vector prim=g.cPrediccion.firstForFollow(reg);
                    tools.Mensaje("<font color=\"#B45F04\"> Primeros de  "+reg+"="+prim+"</font>",jdTabla.jepProcedimiento);
                    if(prim!=null){
                        System.out.println("Primeros de :"+reg+"=="+prim);
                        if(g.cPrediccion.existeVacio(prim)==true) //existe vacio en primeros
                        {
                            //tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+tem+"</font>",jpred.jepPredictivo);
                            //tools.Mensaje("<font color=\"#045FB4\">Calculamos PRIMEROS("+reg+") = "+prim+", como contienen '€', se adjunta con SIGUIENTES("+Prod+")="+follow+"</font>",jpred.jepProcedimiento);
                             tools.Mensaje("<font color=\"#B45F04\"> Existe € en Primeros, calcular Siguientes("+Prod+") </font>",jdTabla.jepProcedimiento);
                            prim.remove("€");
                            Vector follow=g.cPrediccion.Follow(Prod);
                            k=0;
                            while(k<follow.size())
                            {
                                String tok=follow.get(k).toString();
                                StringBuilder builder=new StringBuilder();
                                int posCol=0;
                                if(tok.equals("$")!=true){
                                    posCol=g.existeConstante(tok)+1;
                                    System.out.println("  Siguientes de "+Prod+" -- "+follow.get(k).toString());
                                    builder.append(Prod).append("-->").append(reg);
                                }
                                else{
                                    posCol=Constantes.size()+1;
                                    System.out.println("  Siguientes de "+Prod+" -- "+follow.get(k).toString());
                                    builder.append(Prod).append("-->").append("[€]");
                                }
                                    System.out.println("  == "+builder+ "  posición: ren="+posRen+" col="+posCol);
                                    tablaM.setValueAt(builder, posRen, posCol);
                                    Matriz[posRen][posCol]=reg;
                                    tools.Mensaje("<font color=\"#B45F04\">  Se agrega "+builder+" en ["+(posRen+1)+","+posCol+"]</font>",jdTabla.jepProcedimiento);
                                //Modelo.setValueAt(Variables.get(i), i, posCon);
                                k++;
                            }
                        }
                        else   //no existe vacio en primeros
                        {

                            //tools.Mensaje("<font color=\"#4B088A\">PREDICTIVO("+Prod+"-->"+reg+") = "+prim+"</font>",jpred.jepPredictivo);
                            //tools.Mensaje("<font color=\"#088A85\">Calculamos PRIMEROS("+reg+") = "+prim+" y se añaden a PREDICTIVO.",jpred.jepProcedimiento);
                        }
                        //Revisando primeros
                        k=0;
                        while(k<prim.size())
                        {
                             StringBuilder builder=new StringBuilder();
                             int posCol=g.existeConstante(prim.get(k).toString())+1;
                             System.out.println("  Primero de "+Prod+" -- "+prim.get(k).toString());                             
                             builder.append(Prod).append("-->").append(reg);
                             System.out.println("  == "+builder+ "  posición: ren="+posRen+" col="+posCol);
                             tablaM.setValueAt(builder, posRen, posCol);
                             Matriz[posRen][posCol]=reg;
                             tools.Mensaje("<font color=\"#B45F04\">  Se agrega "+builder+" en ["+(posRen+1)+","+posCol+"]</font>",jdTabla.jepProcedimiento);
                             //Modelo.setValueAt(Variables.get(i), i, posCon);
                             k++;
                        }

                    }
                    else
                    { // Captura de ERROR en caso de un ciclo
                        System.out.println("   XTX Primeros de "+Prod+" retornó null");
                        return;
                    }

                    i++;
                }
          }
          else{
               System.out.println("No existe producción solicitada.");
          }

          }catch(StackOverflowError e){  //atrapando desbordamiento de pila
           e.printStackTrace(); //System.exit(-1);
           return;
          }
    }
    public Vector[][] getMatriz()
    {
        return Matriz;
    }
}
