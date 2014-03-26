/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package firstfollow;

/**
 *
 * @author Derecha
 */
public class Token {
    private String valor;
    private String tipo;
        public Token(String valor, String tipo)
        {
            this.valor=valor;
            this.tipo=tipo;
        }
        public String getValor()
        {
            return valor;
        }
        public String getTipo()
        {
            return tipo;
        }
}
