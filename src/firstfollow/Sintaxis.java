/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;
import java.io.*;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.*;
import javax.swing.text.BadLocationException;
/**
 *
 * @author Derecha
 */
public class Sintaxis {
   private String Texto;
   private JEditorPane zonaTexto;
   private Lexer lexer;
   private JEditorPane mensajes;
   private Gramatica gramatica;
   private HTMLDocument docText;
   private HTMLEditorKit EdKit;

    public Sintaxis(String Texto,JEditorPane zonaTexto,JEditorPane mensajes)
    {                
      this.Texto=Texto;
      this.zonaTexto=zonaTexto;
      this.mensajes=mensajes;
      this.gramatica=new Gramatica();
      Mensaje(" --- Análisis de Gramática. --- ");
      Mensaje("---");
      lexer=new Lexer(Texto,mensajes,gramatica);
      docText=(HTMLDocument)zonaTexto.getDocument();
      EdKit=(HTMLEditorKit) zonaTexto.getEditorKit();
    }
    public boolean Sintaxis()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
      //  System.out.println("valor: "+token.getValor());
         if(token.getValor().compareTo("G")==0){
             Texto=Texto.replace("G", "<font color=\"#04B4AE\">G</font>");
             token=lexer.NextToken();
             if(token.getValor().compareTo("{")==0){
                if(Bloque()==true){
                    if(gramatica.existeProduccion(gramatica.getInicial())>-1){
                       exito=true;
                    }
                    else { Mensaje("Error :"," El símbolo inicial no pertenece a ninguna producción. "); }
                    //sustituye                    
                }
             }
             else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba { en G "); }
             }
         else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba G  "); }
        return exito;
    }
    public boolean Bloque()
    {
        boolean exito=false;
        Token token;
        if(Cons()==true && Var()==true && Ini()==true && Reglas()==true)
        {
            Mensaje("Gramática Revisada… \n");
            token=lexer.NextToken();
          //  System.out.println("Reglas de producción:");
          //gramatica.imprimirReglas();
         //   Mensaje("valor: "+token.getValor());
            if(token.getValor().compareTo("}")==0 ){
                exito=true;
            }
            else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba } en G \n"); }
        }
        else
          Mensaje("Error en linea "+ lexer.numLinea() +" :"," Errores revisión Terminada \n");
        return exito;
    }
    public boolean Cons()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo("constantes")==0){                        
            Texto=Texto.replace("constantes", "<font color=\"#0101DF\">constantes</font>");
             token=lexer.NextToken();
             if(token.getValor().compareTo("{")==0){               
                token=lexer.NextToken();               
                if(token.getTipo().compareTo("Term")==0 && gramatica.addTerminal(token.getValor())==true){                    
                    if(T()==true)
                        exito=true;
                }
                else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba Terminal ó está repetido en constantes \n"); }
             }
             else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba { en constantes \n"); }
        }
        else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," Falta definición de constantes \n"); }
        return exito;
    }
    public boolean Var()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo("variables")==0){
            Texto=Texto.replace("variables", "<font color=\"#FF8000\">variables</font>");
             token=lexer.NextToken();
             if(token.getValor().compareTo("{")==0){
                token=lexer.NextToken();
                 //Mensaje("valor: "+token.getValor());
                if(token.getTipo().compareTo("NTerm")==0 && gramatica.addNoTerminal(token.getValor())==true){
                    if(NT()==true)
                        exito=true;
                }
                else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba No Terminal ó está repetido en variables\n"); }
             }
             else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba { en variables \n"); }
        }
        else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," Falta definición de variables \n"); }
        return exito;
    }
    public boolean Ini()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo("inicial")==0){
            Texto=Texto.replace("inicial", "<font color=\"#DF0101\">inicial</font>");
            token=lexer.NextToken();
            if(token.getValor().compareTo("{")==0){
                token=lexer.NextToken();
                if(token.getTipo().compareTo("NTerm")==0 && gramatica.addInicial(token.getValor().toString())){
                    token=lexer.NextToken();
                    if(token.getValor().compareTo("}")==0){
                        exito=true;
                    }
                    else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba } en Inicial \n"); }
                }
                else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba No Terminal ó no Existe en inicial \n"); }
             }
             else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba { \n"); }

        }
        else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," Falta definición de símbolo inicial \n"); }
        return exito;
    }
    public boolean Reglas()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo("reglas")==0){
            Texto=Texto.replace("reglas", "<font color=\"#088A08\">reglas</font>");
            token=lexer.NextToken();
            if(token.getValor().compareTo("{")==0){
                if(Prod()==true)                    
                        exito=true;
            }
            else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba { en reglas \n"); }
        }        
       // exito=true;
        return exito;
    }
    public boolean T()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo(",")==0){
            token=lexer.NextToken();            
            if(token.getTipo().compareTo("Term")==0 && gramatica.addTerminal(token.getValor())==true){
                if(T()==true)
                    exito=true;
            }
            else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba terminal ó está repetido en constantes \n");
                   Mensaje("Token >>> "+token.getValor()+"  Tipo >>> "+token.getTipo()+"\n");
            }
        }
        else if(token.getValor().compareTo("}")==0){
            exito=true;
        }
        else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba ',' ó '}' en constantes \n"); }
        return exito;
    }
    //---- Mensaje("Valor: "+token.getValor()+" tipo: "+token.getTipo());
    public boolean NT()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
        if(token.getValor().compareTo(",")==0){
            token=lexer.NextToken();
            if(token.getTipo().compareTo("NTerm")==0 && gramatica.addNoTerminal(token.getValor())==true){
                if(NT()==true)
                    exito=true;
            }
            else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba no terminal ó está repetido en variables \n");
                   Mensaje("Token >>> "+token.getValor()+"  Tipo >>> "+token.getTipo()+"\n");
            }
        }
        else if(token.getValor().compareTo("}")==0){
            exito=true;
        }
        else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba ',' ó '}' en variables \n"); }
        return exito;
    }
    public boolean TP()
    {
        boolean exito=false;
        //Token token=lexer.NextToken();
        char caracter=lexer.getCaracter();
       // System.out.println(" caracter: "+caracter);
            if(caracter=='}'){
                exito=true;
            }
            else {
            //System.out.println("Toquen antes: "+token.getValor());
              if(lexer.Retroceso()==true)  {
                  //System.out.println("Toquen despues: "+token.getValor());
                    if(Prod()==true)
                        exito=true;
              }
            }
        return exito;
    }
    public boolean Prod()
    {
        boolean exito=false;
        Token token=lexer.NextToken();
      //  System.out.println("Encabezado:"+token.getValor()+" tipo: "+token.getTipo());
        if(token.getTipo().compareTo("NTerm")==0 && gramatica.existeNoTerminal(token.getValor())){
            if(gramatica.existeProduccion(token.getValor())==-1){ //Provar que no se repita la producción
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<3;i++)
                    sb.append(lexer.NextToken().getValor());                    
                if(sb.toString().compareTo("-->")==0){
                    StringBuilder Reglas=new StringBuilder();
                    Reglas.append(" ");
                    if(lexer.getCaracter()=='€'){
                        Reglas.append("€ ");
                        if(Vacio(token.getValor(),Reglas)==true)
                            exito=true;
                    }
                    else{
                        lexer.Retroceso();
                        if(CT(token.getValor(),Reglas)==true)
                            exito=true;
                    }
                }
                else{ Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba --> en reglas \n"); }
             }
            else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," Producción ya existe! \n");
                   Mensaje("Token -[ "+token.getValor()+" ]-  Tipo -[ "+token.getTipo()+" ]-\n");
            }
         }
         else{ Mensaje("Error en linea "+ lexer.numLinea() +" :"," se esperaba encabezado no terminal ó no está definido \n");
               Mensaje("Token -[ "+token.getValor()+" ]-  Tipo -[ "+token.getTipo()+" ]-\n");
         }
                
        return exito;
    }
    public boolean Cuerpo(String Produccion, StringBuilder Reglas)
    {
        boolean exito=false;
       if(lexer.isVacio()!=true){
        Token token=lexer.NextRegla();
       // System.out.println("valor token: "+ token.getValor()+" tipo: "+token.getTipo());

        if(token.getValor().compareTo("|")==0){
            Reglas.append(" | ");
            if(CT(Produccion,Reglas)==true)
            { exito=true;                }
        }
        else if(token.getValor().compareTo(";")==0){          
            if(gramatica.addReglas(Produccion, Reglas)==true)
            {            
                if(TP()==true){
                    exito=true;
                }
            }
            else{ Mensaje("Error en linea "+ lexer.numLinea() +" :"," Palabra vacía € no debe repetirse más de una vez por producción. \n"); }

     /*   else if(token.getValor().compareTo("€")==0){
            Reglas.append(" € ");
            System.out.println("cadena vacia");
            if(Vacio(Produccion,Reglas)==true)
            { exito=true;                }
        }*/
        }
        else{            
            //token=lexer.NextRegla();
            //System.out.println("Antes de entrar a reglas: "+token.getValor());
                                
                if(token.getTipo().compareTo("Regla")==0){
                    //Mensaje(" Regla: "+token.getValor()+" \n");
                    Reglas.append(" ");
                    Reglas.append(token.getValor());
                   if(Cuerpo(Produccion,Reglas)==true){
                        exito=true;
                    }
                }
                else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," regla no valida ó desconocida \n");
                       Mensaje("Token -[ "+token.getValor()+" ]-  Tipo -[ "+token.getTipo()+" ]-\n");
                }
            }
       }
       else
            Mensaje("Gramática vacia \n");
        return exito;
    }
    public boolean CT(String Produccion, StringBuilder Reglas)
    {
       boolean exito=false;
       if(lexer.isVacio()!=true){
             Token token=lexer.NextRegla();
               if(token.getTipo().compareTo("Regla")==0){
                   // Mensaje(" Regla: "+token.getValor()+" \n");
                    Reglas.append(" ");
                    Reglas.append(token.getValor());
                   if(Cuerpo(Produccion,Reglas)==true){
                        exito=true;
                    }
                }
                else if(token.getValor().compareTo("€")==0){
                    Reglas.append(" € ");
                    if(Vacio(Produccion,Reglas)==true)
                    { exito=true;                }
                }
                else { Mensaje("Error en linea "+ lexer.numLinea() +" :"," Se esperaba terminal, no terminal ó no está declarado\n");
                    Mensaje("Token -[ "+token.getValor()+" ]-  Tipo -[ "+token.getTipo()+" ]-\n");
                    Mensaje("Texto validado-[ "+Reglas+" ]  Texto Restante -[ "+lexer.getRestoDeTexto()+" ]- ");
                }
       }
        return exito;
    }
    public boolean Vacio(String Produccion, StringBuilder Reglas)
    {
       boolean exito=false;
       if(lexer.isVacio()!=true){
        Token token=lexer.NextRegla();
       // System.out.println("Dentro de Vacio");
        if(token.getValor().compareTo("|")==0){
            Reglas.append(" | ");
            if(CT(Produccion,Reglas)==true)
            { exito=true;                }
            
        }
        else if(token.getValor().compareTo(";")==0){
            if(gramatica.addReglas(Produccion, Reglas)==true)
            {
                if(TP()==true){
                    exito=true;
                }
            }
            else{ Mensaje("Error en linea "+ lexer.numLinea() +" :"," Palabra vacía € no debe repetirse más de una vez por producción. \n"); }
        }
        else{
           Mensaje("Error en linea "+ lexer.numLinea() +" :"," Se esperaba ; ó | después de la cadena vacía \n");
        }
       }
          return exito;
    }
    public String getTextoHTML()
    {
        return Texto;
    }
    public void Mensaje(String msg)
    {
        HTMLDocument doc=(HTMLDocument) mensajes.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) mensajes.getEditorKit();
        try{
            //doc.insertString(doc.getLength(), msg, null);
            kit.insertHTML(doc,doc.getLength(),"<b>"+msg+"<br></b>", 0, 0, HTML.Tag.B);
            //doc.insertString(doc.getLength(), "\n", null);
        }
        catch(Exception be){
            be.printStackTrace();
        }
    }
    public void Mensaje(String titulo, String Error)
    {
        HTMLDocument doc=(HTMLDocument) mensajes.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) mensajes.getEditorKit();
        try{
            //doc.insertString(doc.getLength(), msg, null);
            kit.insertHTML(doc,doc.getLength(),"<b>"+titulo+"</b><font color=\"red\">"+Error+"</font><br>", 0, 0, HTML.Tag.B);
            //doc.insertString(doc.getLength(), "\n", null);
        }
        catch(Exception be){
            be.printStackTrace();
        }
    }
    public void coloreaHTML()
    {
      synchronized(this){
        try{            
            zonaTexto.setText("");
            EdKit.insertHTML(docText,0,"<b><pre><strong>"+Texto+"</strong></pre></b>", 0, 0, HTML.Tag.B);            
        }
        catch(Exception be){
            be.printStackTrace();
        }
      }
    }
    public Gramatica getGramatica()
    {
        return gramatica;
    }
}

