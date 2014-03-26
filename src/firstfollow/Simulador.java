/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.Font;
/**
 *
 * @author Derecha
 */
public class Simulador {
    public jdSimulador js;
    private Gramatica g;
    private JTable tablaAn;
    private Vector Matriz[][];
    private DefaultTableModel Modelo=new DefaultTableModel();
    private Stack pila;
    private Tools tools;

    public Simulador(Gramatica g)
    {
        tools=new Tools();
        this.g=g;
    }
    public void iniciarTabla()
    {
        
        Modelo.addColumn("COINCIDENCIA");
        Modelo.addColumn("PILA");
        Modelo.addColumn("ENTRADA");
        Modelo.addColumn("ACCIÓN");
        //tabla=new JTable(2,2);
        tablaAn=new JTable();
        tablaAn=new JTable(Modelo);

        //Llenamos Valores
        tablaAn.setFont(new Font("Monospaced", Font.BOLD, 14));
        tablaAn.setModel(Modelo);

        TableColumnModel ModCol=tablaAn.getColumnModel();

        //Establecemos ancho de las celdas
        for(int i=0;i<Modelo.getColumnCount();i++){
             TableColumn columna=ModCol.getColumn(i);
             columna.setPreferredWidth(150);
        }
        ///-------------------
        tablaAn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //---Para que aparezcan los Scrolls
        ///-------------------
    }
    public void Calcular()
    {
        StringBuilder builder=new StringBuilder(js.textCadena.getText());
        StringBuilder coincidencia=new StringBuilder();
        StringBuilder accion=new StringBuilder();
        Vector Fila;
        String tok;
        System.out.println("ANALIZANDO CADENA.......................");
        //tablaAn.setModel(new DefaultTableModel());
        //iniciarTabla();
        //js.insertarTabla(tablaAn);
        Fila=new Vector();        
         for(int i=0;i<3;i++)
            Modelo.addRow(Fila);

        if(builder.toString()!=null)
        {
            pila=new Stack();
            builder.append("$");
            tools.Mensaje("<font color=\"#0B3861\">Cadena de Entrada:"+builder+"</font>",js.jepMensajes);
            pila.push("$");
            pila.push(g.getInicial());
            Lexer lexico=new Lexer(builder.toString(),js.jepMensajes,g);
            Matriz=g.tablaM.getMatriz();                               
            Fila=new Vector();
            Fila.add(coincidencia.toString()); Fila.add(pila.toString()); Fila.add(builder.toString());
            Modelo.addRow(Fila);

            while(pila.peek().equals("$")==false)
            {
                String NP=pila.peek().toString();
                String rw=returnConstante(builder.toString());
                tools.Mensaje("<font color=\"#0B3861\"> Token Entrada-->  "+rw+"</font>",js.jepMensajes);
                System.out.println("Siguiente en la Pila->"+NP);
                tools.Mensaje("<font color=\"#0B3861\"> Siguiente en la Pila->"+NP+"</font>",js.jepMensajes);
                accion=new StringBuilder();
                if(rw!=null)
                {
                   if(NP.compareTo(rw)==0)
                   {
                        //String nbuild=builder.toString().replaceFirst(rw, "");
                        String nbuild=builder.substring(rw.length());
                        builder=new StringBuilder(nbuild);
                        coincidencia.append(pila.pop());
                        accion.append("relacionar ").append(rw);
                        tools.Mensaje("<font color=\"#0080FF\"> Relacionar->"+rw+"</font>",js.jepMensajes);
                        tools.Mensaje("<font color=\"#0B6138\"> Liberamos de la pila->"+NP+"</font>",js.jepMensajes);
                        System.out.println("Liberamos de la pila->"+NP);
                   }
                   else 
                   {
                    int posC=0;
                    int posR=g.existeVariable(NP);
                        if(rw.equals("$")!=true)
                            posC=g.existeConstante(rw)+1;
                        else
                            posC=g.getConstantes().size()+1;
                    System.out.println("Constante: "+ rw +" Fila: "+posR+" Columna: "+posC);
                    System.out.println("Contenido-->"+Matriz[posR][posC]);
                    accion.append("emitir ").append(NP).append("-->").append(Matriz[posR][posC]);
                    tools.Mensaje("<font color=\"#0080FF\">"+accion+"</font>",js.jepMensajes);
                    Vector reg=Matriz[posR][posC];
                    if(reg!=null){
                        String pilapop=pila.pop().toString();
                        System.out.println("Liberamos de la pila->"+pilapop);
                        System.out.println("Insertar en pila->"+reg);
                        tools.Mensaje("<font color=\"#0B6138\"> Liberamos de la pila->"+pilapop+"</font>",js.jepMensajes);
                        tools.Mensaje("<font color=\"#0B6138\"> Insertamos en pila->"+reg+"</font>",js.jepMensajes);
                        int i=reg.size()-1;
                        while(i>-1)
                        {
                           if(reg.get(i).equals("€")==false)
                                pila.push(reg.get(i));
                            i--;
                        }
                    }
                    else
                    {
                        //error--regla invalida
                        System.out.println("Error!, regla no valida!");
                        tools.Mensaje("<font color=\"#B40404\"> Error!, regla no valida!</font>",js.jepMensajes);
                        return;
                    }
                   }
                    Fila=new Vector();
                    Fila.add(coincidencia.toString()); Fila.add(pila.toString()); Fila.add(builder.toString()); Fila.add(accion.toString());
                    Modelo.addRow(Fila);
                }
                else{
                //error constante no definida
                       System.out.println("Error!.- "+builder.toString()+" <-Token desconocido.");
                       tools.Mensaje("<font color=\"#B40404\"> Error!.- "+builder.toString()+" <-Token desconocido.</font>",js.jepMensajes);
                        return;
                }
            }
            String rw=returnConstante(builder.toString());
             if(pila.peek().equals("$") && rw.compareTo("$")==0)
             {
                 System.out.println("Análisis Correcto!!!");
                 tools.Mensaje("<font color=\"#610B38\"> Análisis Correcto!!!</font>",js.jepMensajes);
             }
             else
             {
                 System.out.println("Error! Caracteres no se pudieron reconocer.!!! -->"+builder);
                 tools.Mensaje("<font color=\"#610B38\"> Error! Caracteres no se pudieron reconocer.!!! -->"+builder+"</font>",js.jepMensajes);
             }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "¡No hay cadena para analizar!");
        }
    }
    public String returnConstante(String Entrada)
    {   int cur=0;
        String exito=null;
        try{
        System.out.println("Entrada:"+Entrada);        
        if(Entrada.equals("$"))
        {
            return "$";
        }
        StringBuilder bd=new StringBuilder();
        while(cur<Entrada.length())
        {
            bd.append(Entrada.charAt(cur));
            if(g.existeConstante(bd.toString())>-1){
                System.out.println("terminal:"+bd.toString());                
                return bd.toString();            
            }
            cur++;
        }
        }
        catch(Exception e){ e.printStackTrace(); }
        return exito;
    }
    public void Analizar()
    {
        js=new jdSimulador(null,true,this);
        if(g.tablaM.crearTabla(false)==true){
            iniciarTabla();
            js.insertarTabla(tablaAn);
            js.setLocationRelativeTo(null);
            js.show();
        }
        else{
            JOptionPane.showMessageDialog(null, "No se pudo realizar la simulación.");
        }
    }
}
