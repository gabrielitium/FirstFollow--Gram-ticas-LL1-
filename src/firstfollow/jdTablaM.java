/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jdTablaM.java
 *
 * Created on 17/08/2010, 04:37:33 PM
 */

package firstfollow;
import javax.swing.*;

/**
 *
 * @author Derecha
 */
public class jdTablaM extends javax.swing.JDialog {
    public JTable tablaM;
    public JEditorPane jepGramatica;
    public JEditorPane jepProcedimiento;
    TablaM tablam;

    /** Creates new form jdTablaM */
    public jdTablaM(java.awt.Frame parent, boolean modal,TablaM tablam) {
        super(parent, modal);
        initComponents();
        jepGramatica=jepGramatica_;
        jepProcedimiento=jepProcedimiento_;        
        this.setLocationRelativeTo(null);
        this.tablam=tablam;
        jepGramatica.setContentType( "text/html" );
        jepProcedimiento.setContentType( "text/html" );
    }
    public void insertarTabla(JTable tabla)
    {
        jspTabla.setViewportView(tabla);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jepGramatica_ = new javax.swing.JEditorPane();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jepProcedimiento_ = new javax.swing.JEditorPane();
        PROCEDIMIENTO = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jspTabla = new javax.swing.JScrollPane();
        jbAceptar = new javax.swing.JButton();
        jbAyuda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setDividerLocation(450);
        jSplitPane1.setDividerSize(10);

        jepGramatica_.setEditable(false);
        jScrollPane3.setViewportView(jepGramatica_);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabel3.setForeground(new java.awt.Color(95, 162, 203));
        jLabel3.setText("GRAMÁTICA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel3)
                .addContainerGap(206, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jepProcedimiento_.setEditable(false);
        jScrollPane4.setViewportView(jepProcedimiento_);

        PROCEDIMIENTO.setFont(new java.awt.Font("Tahoma", 0, 18));
        PROCEDIMIENTO.setForeground(new java.awt.Color(95, 162, 203));
        PROCEDIMIENTO.setText("PROCEDIMIENTO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(PROCEDIMIENTO)
                .addContainerGap(133, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PROCEDIMIENTO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        jSplitPane2.setLeftComponent(jSplitPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel3);

        jbAceptar.setFont(new java.awt.Font("Tahoma", 1, 12));
        jbAceptar.setText("Aceptar");
        jbAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAceptarMouseClicked(evt);
            }
        });

        jbAyuda.setFont(new java.awt.Font("Tahoma", 1, 12));
        jbAyuda.setText("Ayuda");
        jbAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAyudaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jbAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 554, Short.MAX_VALUE)
                        .addComponent(jbAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jbAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAceptarMouseClicked
        this.hide();
}//GEN-LAST:event_jbAceptarMouseClicked

    private void jbAyudaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAyudaMouseClicked
        jdAyuda ja=new jdAyuda(null,true);
        ja.setTitle("Algoritmo para Construir tabla M. ");
        ja.lblMensajes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/firstfollow/Images/tabla.JPG")));
        //ja.setIconImage(new IconImage());
        ja.setLocationRelativeTo(null);
        ja.show();
}//GEN-LAST:event_jbAyudaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PROCEDIMIENTO;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbAyuda;
    private javax.swing.JEditorPane jepGramatica_;
    private javax.swing.JEditorPane jepProcedimiento_;
    private javax.swing.JScrollPane jspTabla;
    // End of variables declaration//GEN-END:variables

}
