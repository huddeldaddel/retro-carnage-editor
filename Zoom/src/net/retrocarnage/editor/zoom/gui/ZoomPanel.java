package net.retrocarnage.editor.zoom.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.zoom.ZoomService;
import net.retrocarnage.editor.zoom.impl.ZoomServiceImpl;

/**
 * Component that gets shown in the toolbar. Used to modify the zoom level of the game play editors.
 *
 * @author Thomas Werner
 */
public class ZoomPanel extends javax.swing.JPanel implements PropertyChangeListener {

    /**
     * Creates new form ZoomPanel
     */
    public ZoomPanel() {
        ZoomService.getDefault().addPropertyChangeListener(this);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblZoomOut = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        lblZoomIn = new javax.swing.JLabel();
        lblZoomLevel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(lblZoomOut, org.openide.util.NbBundle.getMessage(ZoomPanel.class, "ZoomPanel.lblZoomOut.text_1")); // NOI18N
        lblZoomOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblZoomOutMouseClicked(evt);
            }
        });

        sldZoom.setMajorTickSpacing(50);
        sldZoom.setMaximum(300);
        sldZoom.setMinimum(10);
        sldZoom.setMinorTickSpacing(10);
        sldZoom.setPaintTicks(true);
        sldZoom.setSnapToTicks(true);
        sldZoom.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 3, 1));
        sldZoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldZoomStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(lblZoomIn, org.openide.util.NbBundle.getMessage(ZoomPanel.class, "ZoomPanel.lblZoomIn.text_1")); // NOI18N
        lblZoomIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblZoomInMouseClicked(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(lblZoomLevel, org.openide.util.NbBundle.getMessage(ZoomPanel.class, "ZoomPanel.lblZoomLevel.text_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblZoomOut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldZoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblZoomIn)
                .addGap(18, 18, 18)
                .addComponent(lblZoomLevel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblZoomIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sldZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblZoomLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblZoomOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZoomOutMouseClicked
        final ZoomServiceImpl service = (ZoomServiceImpl) ZoomService.getDefault();
        service.setZoomLevel(service.getZoomLevel() - 10);
    }//GEN-LAST:event_lblZoomOutMouseClicked

    private void lblZoomInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZoomInMouseClicked
        final ZoomServiceImpl service = (ZoomServiceImpl) ZoomService.getDefault();
        service.setZoomLevel(service.getZoomLevel() + 10);
    }//GEN-LAST:event_lblZoomInMouseClicked

    private void sldZoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldZoomStateChanged
        if (!sldZoom.getValueIsAdjusting()) {
            final ZoomServiceImpl service = (ZoomServiceImpl) ZoomService.getDefault();
            service.setZoomLevel(sldZoom.getValue());
        }
    }//GEN-LAST:event_sldZoomStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblZoomIn;
    private javax.swing.JLabel lblZoomLevel;
    private javax.swing.JLabel lblZoomOut;
    private javax.swing.JSlider sldZoom;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (ZoomService.PROPERTY_ZOOM.equals(pce.getPropertyName())) {
            sldZoom.setValue((int) pce.getNewValue());
            lblZoomLevel.setText(Integer.toString((int) pce.getNewValue()) + " %");
        }
    }
}
