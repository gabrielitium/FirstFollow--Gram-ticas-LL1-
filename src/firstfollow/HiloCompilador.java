/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;

/**
 *
 * @author Derecha
 */
public class HiloCompilador extends Thread{
    private Editor editor;
    public HiloCompilador(Editor editor){
        this.editor=editor;
    }
    @Override
    public void run(){
        try{
          while(true){
            Thread.sleep(30000);
            editor.compilarGramatica();
          }
        }
        catch(InterruptedException e){}
    }
}
