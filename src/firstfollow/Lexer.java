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
 * @author usuario
 */
class Lexer
{
    private int ini;
    private int fin;
    private int nlinea;
    private char caracter;
    private String entrada;
    private StringBuilder builder;
    private char [] texto;
    private JEditorPane mensajes;
    private Gramatica gramatica;


    public Lexer(String entrada,JEditorPane mensajes, Gramatica gramatica)
      {
        builder=new StringBuilder();
        nlinea=0;
        this.entrada=entrada;
        this.mensajes=mensajes;
        this.gramatica=gramatica;
        CargarTexto();
        nlinea=1; //>Reinicia conteo lineas, útil para el manejo de errores
        texto=builder.toString().toCharArray();
        ini=0;
        fin=entrada.length();
        //Mensaje("  Compilando  ");
      }
    public void  CargarTexto()
      {
      char cadena [];
      char c;
      try{
        entrada=quitaEspacios(entrada);
        entrada=quitarComentarios(entrada);
        //System.out.println("Entrada: "+entrada);
        cadena=entrada.toCharArray();
        fin=entrada.length();

        while(ini<fin)
        {
            c=cadena[ini];
            if(c!='\n')
            {
              builder.append(cadena[ini]);
              ini++;
            }
            else
            {
              builder.append(" ");
              ini++;
            }
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
        /*  try{
         FileReader fr=new FileReader(archivo);
         BufferedReader br=new BufferedReader(fr);
          String linea = br.readLine();
           while (linea != null)
           {
              //txtMensajes.append(" While mayor ");
              linea=quitaEspacios(linea);
              ini=0;
              fin=linea.length();
              cadena=linea.toCharArray();

              while(ini<fin)
              {
                //System.out.print("'"+cadena[ini]+"'");
                builder.append(cadena[ini]);
                ini++;
              }
              linea = br.readLine();
            // txtMensajes.append(" "+(++nlinea)+"  \n");
             builder.append(" ");
            }
        }
        catch(IOException e)
        {}*/
      }
      public void quitaLineas()
      {
        while(Avance()==true && caracter==' ')
        {
           // txtMensajes.append("Num linea: "+nlinea+" \n");
            nlinea++;
        }
        Retroceso();
      }
      public String quitarComentarios(String entrada)
      {
          char [] txt=entrada.toCharArray();
          int atras=0,ultimo=0;
          StringBuilder build=new StringBuilder();
          boolean bd=false;
          char c;
            atras=0; ultimo=entrada.length();
            while(atras < ultimo)
            {
                c=txt[atras];
                if(c=='/')
                {
                    if(atras+1 < ultimo)
                    {
                      c=txt[atras];
                      if(c=='/'){
                          bd=true;
                          //atras--;
                      }
                    }
                }
                if(c!='\n')
                {
                    if(bd==false)
                    build.append(txt[atras]);
                    atras++;
                }
                else
                {
                    build.append(" ");
                    bd=false;
                    atras++;
                }
            }
            return build.toString();
      }
      public Token NextToken()
      {
        Token token=new Token("","");
        StringBuilder sb=new StringBuilder();

        quitaLineas();

        if(Avance()==true)
        {
           // System.out.print(caracter);
            if(Character.isUpperCase(caracter)==true){
                sb.append(caracter);
                String cad=Palabra().toString();
                if(cad.compareTo("")!=0)
                {
                    sb.append(cad);
                }
                token=new Token(sb.toString(),"NTerm");
            }
            else if(Character.isLowerCase(caracter)==true){
                sb.append(caracter);
                String cad=Palabra().toString();
                if(cad.compareTo("")!=0)
                {
                    sb.append(cad);
                }
                token=new Token(sb.toString(),"Term");
            }
            else if(caracter=='"'){
                sb.append(caracter);
                String cad=Palabra().toString();
                if(cad.compareTo("")!=0)
                {
                    sb.append(cad);
                }
                quitaLineas();
                if(Avance()==true && caracter=='"')
                    token=new Token(sb.append(caracter).toString(),"Term");
                else
                    Mensaje("Error en linea "+ nlinea +" :"," se esperaba comillas ");
            }
            else if(caracter=='<'){
                sb.append(caracter);
                String cad=Palabra().toString();
                if(cad.compareTo("")!=0)
                {
                    sb.append(cad);
                }
                quitaLineas();
                if(Avance()==true && caracter=='>')
                    token=new Token(sb.append(caracter).toString(),"Term");
                else
                    Mensaje("Error en linea "+ nlinea +" :"," se esperaba paréntesis angular ");
            }
            else if(Character.isDigit(caracter)==true){
                 token=new Token(Character.toString(caracter),"Term");
            }
            else{
                 token=new Token(Character.toString(caracter),"Term");
            }
        }
        else{
            token=new Token("No hay caracteres!","FIN");
            Mensaje("Fin de gramática ");
        }
        return token;
      }

      public Token NextRegla()
      {
        Token token=new Token("","");
        StringBuilder sb=new StringBuilder();

        quitaLineas();

     //   System.out.println("Terminales "+gramatica.getTerminales());
     //   System.out.println("No Terminales "+gramatica.getNoTerminales());
        sb.append(" ");
        //if(gramatica.existeNoTerminal(sb.toString())||gramatica.existeTerminal(sb.toString())){
          if(Avance()==true)
          {
                if(Character.isLetter(caracter)){
                 //algun terminal o no terminal sin numero al inicio
                    String cad;
                    do{
                        if(caracter!=' '){
                            cad=sb.toString()+caracter;
                            if(gramatica.existeNoTerminal(cad)||gramatica.existeTerminal(cad)){
                            sb.append(caracter);

                            }
                            else { 
                                   //System.out.println(" cadena : "+sb.toString());                                
                                break;
                            }
                        }
                        else { nlinea++; }

                    }while(Avance()==true);
                    Retroceso();
                //    System.out.println(" cadena : "+sb.toString());

                    sb.append(" ");
                    if(sb.toString().compareTo("  ")!=0)
                        if(gramatica.existeNoTerminal(sb.toString())||gramatica.existeTerminal(sb.toString())){
                            token=new Token(sb.toString(),"Regla");
                            System.out.println(" regla:"+sb.toString()+"-");
                        }
                        else
                            Mensaje("Cadena desconocida -[ "+sb+" ]- !!! ");
                }
                else if(Character.isDigit(caracter)){
                    if(gramatica.existeNoTerminal(" "+caracter+" ")||gramatica.existeTerminal(" "+caracter+" ")){
                    token=new Token(Character.toString(caracter),"Regla");
                    }
                }
                else if(caracter==';'||caracter=='|'||caracter=='€'){
                    token=new Token(Character.toString(caracter),"Term");
                }
                else{
                  if(gramatica.existeNoTerminal(" "+caracter+" ")||gramatica.existeTerminal(" "+caracter+" ")){
                    token=new Token(Character.toString(caracter),"Regla");
                  }
                  else
                    token=new Token(Character.toString(caracter),"No valido");
                }
          }
       return token;
      }
      public StringBuilder Palabra()
      {
         StringBuilder palabra=new StringBuilder();
            while(Avance()==true && Character.isLetterOrDigit(caracter))
            {   palabra.append(caracter);   }
            Retroceso();
         return palabra;
      }
      public int getCaret()
      {
        return ini;
      }
      public char getCaracter()
      {
       char c=' ';
       quitaLineas();
        if(Avance()==true)
        {
            c=caracter;
        }
       return c;
      }
      public boolean Avance()
      {
        boolean exito=false;
         if(ini<fin)
         {
           try{
              caracter=texto[ini++];
              exito=true;
           }catch(Exception e){ Mensaje("No se puede avanzar. - Ex"); exito=false; } finally{  }
         }
         else
         {
            Mensaje("No se puede avanzar. ini>"+ini+" fin>"+fin+" ");
         }
        return exito;
      }
      public boolean Retroceso()
      {
        boolean exito=false;
         if(ini>0)
         {
            try{
                caracter=texto[--ini];
                exito=true;
            }catch(Exception e){  Mensaje("No se puede retroceder - Ex."); exito=false;  } 
         }
         else
         {
            Mensaje("No se puede retroceder. ");
         }
        return exito;
      }
      public boolean isVacio()
      {
        boolean exito=false;
         if(ini==(fin-1))
         {
            exito=true;
         }
        return exito;
      }
      public int numLinea()
      {
        return nlinea;
      }
      public String quitaEspacios(String texto)
      {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto," \t");
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(tokens.nextToken());
        }
        return buff.toString();
      }
      public void Mensaje(String msg)
      {
        HTMLDocument doc=(HTMLDocument) mensajes.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) mensajes.getEditorKit();
        try{
            //doc.insertString(doc.getLength(), msg+" <br> ", null);
            kit.insertHTML(doc,doc.getLength(),"<b>"+msg+" <br></b> ", 0, 0, HTML.Tag.B);
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
      public String getRestoDeTexto()
      {
         // System.out.println(" ini -->"+ini+" fin-->"+fin);
          String txt;
          if ((ini+10)<fin)
          {
            txt=builder.toString(). substring(ini+10);
          }
          else{
            txt=builder.toString(). substring(ini,fin);
          }
          
          return txt+"...";
      }
  }
