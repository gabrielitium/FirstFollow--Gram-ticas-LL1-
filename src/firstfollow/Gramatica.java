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
public class Gramatica {
    private String gramatica;
    private String inicial;
    private StringBuilder Terminales;
    private StringBuilder NoTerminales;
    private Vector Constantes; // Terminales y constantes es lo mismo, una manejada en String y otra en vector
    private Vector Variables; // NoTerminales y variables es lo mismo, una manejada en String y otra en vector
    private Vector Produccion;
    private Vector Reglas;
    private Vector SegReglas;
    public Ambiguedades ambiguedad;
    public ConjuntosPrediccion cPrediccion;
    public Predictivo predictivo;
    public CondicionLL cLL;
    public TablaM tablaM;
    public Simulador simulador;
    public HacerReportes reporte;

    public Gramatica()
    {
        gramatica="";
        Terminales=new StringBuilder();
        NoTerminales=new StringBuilder();
        Produccion=new Vector();
        Reglas=new Vector();
        Constantes=new Vector();
        Variables=new Vector();
        SegReglas=new Vector();
        inicial="";
        ambiguedad=new Ambiguedades(this);
        cPrediccion=new ConjuntosPrediccion(this);
        predictivo=new Predictivo(this);
        cLL=new CondicionLL(this);
        tablaM=new TablaM(this);
        simulador=new Simulador(this);
        reporte=new HacerReportes(this);
    }
    public boolean addTerminal(String Terminal)
    {
        boolean exito=false;
        String cad=" "+Terminal+" ";
        if(existeTerminal(cad)!=true) //no existe
        {
            Terminales.append(cad);
            Constantes.add(Terminal);
            exito=true;
        }
        //System.out.println("cadena:"+cad+" no terminales: "+Terminales);
        return exito;
    }
    public boolean addNoTerminal(String NoTerminal)
    {
        boolean exito=false;
        String cad=" "+NoTerminal+" ";
        if(existeNoTerminal(cad)!=true) //no existe
        {
            NoTerminales.append(cad);
            Variables.add(NoTerminal);
            exito=true;
        }
        //System.out.println("cadena:"+NoTerminal+" no terminales: "+NoTerminales);
        return exito;
    }
    public boolean addInicial(String inicial)
    {
        boolean exito=false;
        String cad=" "+inicial+" ";
        if(existeNoTerminal(cad)==true) //no existe
        {
            this.inicial=inicial;
            exito=true;
        }
        return exito;
    }
    public String getInicial()
    {
        return inicial;
    }
    public String getVariable(int i)
    {
       String variable=null;          
        if(i>=0 && i<=Variables.size())
            return Variables.get(i).toString();
       return variable;
    }
    public Vector getVariables()
    {
        return Variables;
    }
    public String getConstante(int i)
    {
       String constante=null;
        if(i>=0 && i<=Constantes.size())
            return Constantes.get(i).toString();
       return constante;
    }
    public Vector getConstantes()
    {
        return Constantes;
    }
    public void setGramatica(String gramatica)
    {
        this.gramatica=gramatica;
    }
    public String getGramatica()
    {
      return gramatica;
    }
    public void setTerminales(String Term)
    {
        Terminales=new StringBuilder(Term);
    }
    public void setNoTerminales(String NoTerm)
    {
        NoTerminales=new StringBuilder(NoTerm);
    }
    public boolean existeTerminal(String Terminal)
    {
        boolean exito=true;
   //System.out.println("Terminales "+getTerminales());
        if(Terminales.toString().indexOf(Terminal)==-1) //existe
            exito=false;
        return exito;
    }
    public boolean existeNoTerminal(String NoTerminal)
    {
        boolean exito=true;
        //System.out.println("No Terminale "+NoTerminal);
        //System.out.println("No Terminales "+getNoTerminales());
        if(NoTerminales.toString().indexOf(NoTerminal)==-1) //existe
        {
            exito=false;
        }
        return exito;
    }
    public String getTerminales()
    {
        return Terminales.toString();
    }
    public String getNoTerminales()
    {
        return NoTerminales.toString();
    }
    public int getNumOcurrencias(String cadena, String buscar)
    {
        int contador=0;
            while (cadena.indexOf(buscar) > -1) {
            cadena = cadena.substring(cadena.indexOf(buscar)+buscar.length(),cadena.length());
            contador++;
            }
        //   System.out.println("numero de ocurrencias:"+contador);
        return contador;
    }
    public boolean addReglas(String P,StringBuilder R)
    {
       boolean exito=false;
        if(R.toString().indexOf(" € ")>-1){ //no se debe repetir cadena vacia en una regla
            int oc=getNumOcurrencias(R.toString()," € ");
            if(oc==1)
                exito=true;
            //System.out.println("Éxito----:"+exito);
        }
        else
            exito=true;
       
       if(exito==true)
       {
        Produccion.add(P);
        Reglas.add(R);        
       }       
        return exito;
    }
    public Vector segmentar()
    {  int i=0;
       SegReglas=new Vector();
       while(i<Produccion.size())
        {
            segmentarReglas((StringBuilder)Reglas.get(i++));
        }
       return SegReglas;
    }
    public void segmentarReglas(StringBuilder R)
    {
        StringTokenizer st=new StringTokenizer(R.toString(), "|");
        Vector segmentos=new Vector();
           while(st.hasMoreTokens())
           {
               String tok=st.nextToken();
               StringTokenizer seg=new StringTokenizer(tok, " ");
               Vector tokens=new Vector();
               while(seg.hasMoreTokens())
               {
                   String nt=seg.nextToken();
                   tokens.add(nt);
               }
               segmentos.add(tokens);
           }
        //System.out.println("Segmentar:>"+R+" Segmentado:-->"+segmentos);
        SegReglas.add(segmentos);
    }
    //Verifica si existe producción y retorna su posición
    public int existeProduccion(String Nombre)
    {
          int index=-1;
          ListIterator it;
          it=Produccion.listIterator();
            while(it.hasNext())
            {
                String temp=(String)it.next();
                if(temp.compareTo(Nombre)==0)
                {
                    index=(it.nextIndex())-1;
                    break;
                }
            }
            return index;
    }
    public int existeConstante(String Nombre)
    {
          int index=-1;
          ListIterator it;
          it=Constantes.listIterator();
            while(it.hasNext())
            {
                String temp=(String)it.next();
                if(temp.compareTo(Nombre)==0)
                {
                    index=(it.nextIndex())-1;
                    break;
                }
            }
         return index;
    }
    public int existeVariable(String Nombre)
    {
          int index=-1;
          ListIterator it;
          it=Variables.listIterator();
            while(it.hasNext())
            {
                String temp=(String)it.next();
                if(temp.compareTo(Nombre)==0)
                {
                    index=(it.nextIndex())-1;
                    break;
                }
            }
         return index;
    }
    public String getProduccion(int index)
    {
        return Produccion.get(index).toString();
    }
    //Retorna regla en una cadena
    public String getReglas(int index)
    {
        return Reglas.get(index).toString();
    }

    public Gramatica getClon()
    {
        return this;
    }
    public int numProducciones()
    {
        return Produccion.size();
    }

    public StringBuilder getReglas()
    {
        StringBuilder builder=new StringBuilder();
        builder.append(" ");
        for(int i=0;i<Produccion.size();i++)
        {
            builder.append("\t");
            builder.append(Produccion.get(i).toString());
            builder.append("-->");
            builder.append(Reglas.get(i).toString());
            builder.append("; \n");
        }
        return builder;
    }
    public Gramatica clonarVarCons()
    {
        Gramatica gram=new Gramatica();
            gram.setTerminales(Terminales.toString());
            gram.setNoTerminales(NoTerminales.toString());
            gram.addInicial(inicial);
        return gram;
    }
    public StringBuilder getReglasEnHtml()
    {
        StringBuilder builder=new StringBuilder();
        builder.append(" ");
        for(int i=0;i<Produccion.size();i++)
        {
            builder.append(Produccion.get(i).toString());
            builder.append("-->  ");
            builder.append(Reglas.get(i).toString());
            builder.append(" <br> ");
        }
        return builder;
    }
    public StringBuilder getDoc()
    {
        StringBuilder doc=new StringBuilder();
            doc.append("G {\n");
            doc.append("constantes{\n").append(getDocTerminales().toString()).append("\n}\n");
            doc.append("variables{\n").append(getDocNoTerminales().toString()).append("\n}\n");
            doc.append("inicial{\n\t").append(getInicial()).append("\n}\n");
            doc.append("reglas{\n").append(getReglas()).append("}\n}");
        return doc;
    }
    public StringBuilder getDocTerminales()
    {
       StringBuilder build=new StringBuilder();
       StringTokenizer st=new StringTokenizer(Terminales.toString()," ");
       int i=0;
            while(st.hasMoreTokens()==true)
            {
                String tk=st.nextToken();
                if(i!=0){
                    if((i%8)==0) build.append("\n\t");
                    build.append(", ").append(tk);
                }
                else{                    
                    build.append("\t").append(tk);
                }
                i++;
            }
        return build;
    }
    public StringBuilder getDocNoTerminales()
    {
       StringBuilder build=new StringBuilder();
       StringTokenizer st=new StringTokenizer(NoTerminales.toString()," ");
       int i=0;
            while(st.hasMoreTokens()==true)
            {
                String tk=st.nextToken();
                if(i!=0){
                    if((i%8)==0) build.append("\n\t");
                    build.append(", ").append(tk);
                }
                else{
                    build.append("\t").append(tk);
                }
                i++;
            }
        return build;
    }
    public int sizeProduccion()
    {
        return Produccion.size();
    }

}
