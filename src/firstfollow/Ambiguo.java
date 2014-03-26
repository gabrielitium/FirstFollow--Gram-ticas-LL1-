/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Esta clase utiliza una aplicación externa encontrada en internet
//que intenta detectar si una gramática es ambigua.

package firstfollow;
import java.io.*;
import java.util.*;
import java.nio.charset.*;
//----------------Clasese externas
import dk.brics.grammar.main.*;
import dk.brics.grammar.*;
import dk.brics.grammar.ambiguity.*;
import dk.brics.grammar.parser.*;
//-----------------------------------
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.*;
/**
 *
 * @author usuario
 */
public class Ambiguo {
   private String str2;
   private String str1;
   private AmbiguityAnalyzer localAmbiguityAnalyzer;
   private ByteArrayOutputStream localByteArrayOutputStream;
   private Grammar localGrammar1;
   private PrintWriter localPrintWriter;   
   Gramatica g;

    public Ambiguo(Gramatica g)
    {
        this.g=g;        
        localAmbiguityAnalyzer = null;
    }
    //Analiza la gramática con  librería dk  y retorna análisis 
    public Boolean AnalizarGramaticaConDk(JEditorPane Mensajes, Boolean Errores)
    {
        Boolean exito=false;
        String str1 = str2 = Charset.defaultCharset().name();
        localGrammar1 = null;
        localByteArrayOutputStream = new ByteArrayOutputStream();
        localPrintWriter = new PrintWriter(localByteArrayOutputStream, true);


        try{
                localGrammar1 = new String2Grammar().convert(transformaSintaxis(), str1, localPrintWriter);
                localPrintWriter.close();
                System.out.print(localByteArrayOutputStream);
                if(Errores==true){
                    String stemp=localByteArrayOutputStream.toString();
                    imprimirDetalles(Mensajes,stemp);
                }
            }
            catch(Exception e){ e.printStackTrace(); }
        if (localByteArrayOutputStream.size() == 0)
            exito=true;
        return exito;
    }
    public void imprimirDetalles(JEditorPane Mensajes, String cadena)
    {
        HTMLDocument doc=(HTMLDocument) Mensajes.getDocument();
        HTMLEditorKit kit= (HTMLEditorKit) Mensajes.getEditorKit();
        StringBuilder builder=new StringBuilder();
                    int i=0;
                    builder.append("<br> ");
                    while(i<cadena.length())
                    {
                        switch(cadena.charAt(i))
                        {
                            case '\n':
                                //kit.insertHTML(doc,doc.getLength(),"<b><br></b>", 0, 0, HTML.Tag.B);
                                builder.append("<br>");
                            case ' ':
                                //kit.insertHTML(doc,doc.getLength(),"<b>_</b>", 0, 0, HTML.Tag.B);
                                builder.append(" ");
                            default:
                                //kit.insertHTML(doc,doc.getLength(),"<b><font color=\"red\">"+stemp.charAt(i)+"</font></b>", 0, 0, HTML.Tag.B);
                                builder.append(cadena.charAt(i));
                        }
                        i++;
                    }
                    try{
                    kit.insertHTML(doc,doc.getLength(),"<b><font color=\"#084B8A\">"+builder+"</font></b>", 0, 0, HTML.Tag.B);
                    }
                    catch(Exception e){ e.printStackTrace(); }
    }
    public void AnalizadorDeAmbiguedad()
    {                        
        localAmbiguityAnalyzer = null;
        jdAvisos ja=new jdAvisos(null,true);
        System.out.println(transformaSintaxis());
        AnalizarGramaticaConDk(null,false);  //<--mandamos a análizar
            try{
                localGrammar1 = new String2Grammar().convert(transformaSintaxis(), str1, localPrintWriter);
                localPrintWriter.close();
                System.out.print(localByteArrayOutputStream);
                if (localByteArrayOutputStream.size() == 0){
                int i=0;
                ByteArrayOutputStream os=new ByteArrayOutputStream();
                PrintWriter pwAnalizer=new PrintWriter(os, true);

                    System.out.println("No se encontraron errores en la gramática!");
                    localAmbiguityAnalyzer = new AmbiguityAnalyzer(pwAnalizer, true);
                    localAmbiguityAnalyzer.analyze(localGrammar1);

                    int j = localAmbiguityAnalyzer.getNumberOfPotentialVerticalAmbiguities();
                    int k = localAmbiguityAnalyzer.getNumberOfPotentialHorizontalAmbiguities();
                    int l = localAmbiguityAnalyzer.getNumberOfCertainVerticalAmbiguities();
                    int i1 = localAmbiguityAnalyzer.getNumberOfCertainHorizontalAmbiguities();
                    int i2 = localAmbiguityAnalyzer.getNumberOfOutOfMemoryErrors();
                   if (j + k + l + i1 + i2 == 0)
                    {
                      System.out.println("La gramática no es ambigua!");
                      imprimirDetalles(ja.jepMensajes,os.toString());
                      ja.Mensaje("La gramática no parece ser ambigua!");
                    }
                    else
                    {
                    if (l + i1 > 0){
                      imprimirDetalles(ja.jepMensajes,os.toString());
                       ja.Mensaje("La gramática es ambigua!");
                       System.out.println("La gramática es ambigua!");
                    }
                    else{
                       imprimirDetalles(ja.jepMensajes,os.toString());
                       ja.Mensaje("La gramática probablemente sea ambigua ó produzca ambigüedades....");
                       System.out.println("La gramática probablemente sea ambigua ó produzca ambigüedades....");
                    }
                    i = 1;
                     }
                }
                else{
                    System.out.println("Errores en la gramática!");
                    ja.Mensaje("Errores en la gramática!");
                }

                ja.show();
            }catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    public String transformaSintaxis()
    {
       StringBuilder texto=new StringBuilder();
       Vector reglasSeg=g.segmentar();  //getSegmentosReglas();
       int i=0,j=0,k=0,nc;

       while(i<reglasSeg.size()) //desglosando por reglas
       {
            String prod=g.getProduccion(i);
            String tok;
            texto.append(" ").append(prod).append(" : ");
            Vector reg=(Vector)reglasSeg.get(i);
            j=0;
            while(j<reg.size()) //desglosando por segmentos
            {
               if(j>0) texto.append(" | ");
               k=0;
               Vector seg=(Vector)reg.get(j);               
               while(k<seg.size()){ // desglosando por tokens                   
                 tok= (String)seg.get(k);
                  //System.out.println("Token -:"+tok);
                 try{
                 if(g.existeNoTerminal(" "+tok+" ")==true)
                  {
                   // System.out.println("Existe Terminal "+" "+tok+" ");
                    texto.append(" ").append(tok).append(" ");
                  }
                  else if(g.existeTerminal(" "+tok+" ")==true)
                  {
                    texto.append(" \"").append(tok).append("\" ");
                  }
                  else if(tok.compareTo("€")==0){ //<--- producciones vacias--------------------
                     texto.append(" vacio ");
                  } 

                 }catch(Exception e){ e.printStackTrace(); }
                 k++;
                }
                j++;
            }
            texto.append("\n");
            i++;
        }
        return texto.toString();
    }   
}
