package net.retrocarnage.editor.enemymovementeditor;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import net.retrocarnage.editor.core.gui.SpinnerCellEditor;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.enemymovementeditor//EnemyMovementEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EnemyMovementEditorTopComponent",
        iconBase = "net/retrocarnage/editor/enemymovementeditor/icon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "rightSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "net.retrocarnage.editor.enemymovementeditor.EnemyMovementEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EnemyMovementEditorAction",
        preferredID = "EnemyMovementEditorTopComponent"
)
@Messages({
    "CTL_EnemyMovementEditorAction=Enemy movements",
    "CTL_EnemyMovementEditorTopComponent=Enemy movements",
    "HINT_EnemyMovementEditorTopComponent=Movements of the selected enemy"
})
public final class EnemyMovementEditorTopComponent extends TopComponent {

    private final EnemyMovementEditorController controller;

    public EnemyMovementEditorTopComponent() {
        controller = new EnemyMovementEditorController();
        controller.addPropertyChangeListener((PropertyChangeEvent pce) -> handleControllerPropertyChanged(pce));

        initComponents();
        setName(Bundle.CTL_EnemyMovementEditorTopComponent());
        setToolTipText(Bundle.HINT_EnemyMovementEditorTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDetail = new javax.swing.JPanel();
        scrMovements = new javax.swing.JScrollPane();
        tblMovements = new javax.swing.JTable();
        pnlMovementActions = new javax.swing.JPanel();
        btnAddMovement = new javax.swing.JButton();
        btnRemoveMovement = new javax.swing.JButton();
        pnlLibrary = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        pnlDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.pnlDetail.border.title"))); // NOI18N
        pnlDetail.setLayout(new java.awt.BorderLayout());

        tblMovements.setModel(controller.getTableModel());
        tblMovements.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMovements.getSelectionModel().addListSelectionListener(controller.getTableSelectionListener(tblMovements));
        tblMovements.getColumnModel().getColumn(0).setCellEditor(new SpinnerCellEditor());
        tblMovements.getColumnModel().getColumn(1).setCellEditor(new SpinnerCellEditor());
        scrMovements.setViewportView(tblMovements);

        pnlDetail.add(scrMovements, java.awt.BorderLayout.CENTER);

        pnlMovementActions.setLayout(new java.awt.GridLayout(1, 0));

        org.openide.awt.Mnemonics.setLocalizedText(btnAddMovement, org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.btnAddMovement.text")); // NOI18N
        btnAddMovement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovementActionPerformed(evt);
            }
        });
        pnlMovementActions.add(btnAddMovement);

        org.openide.awt.Mnemonics.setLocalizedText(btnRemoveMovement, org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.btnRemoveMovement.text")); // NOI18N
        btnRemoveMovement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMovementActionPerformed(evt);
            }
        });
        pnlMovementActions.add(btnRemoveMovement);

        pnlDetail.add(pnlMovementActions, java.awt.BorderLayout.SOUTH);

        add(pnlDetail, java.awt.BorderLayout.CENTER);

        pnlLibrary.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.pnlLibrary.border.title"))); // NOI18N

        javax.swing.GroupLayout pnlLibraryLayout = new javax.swing.GroupLayout(pnlLibrary);
        pnlLibrary.setLayout(pnlLibraryLayout);
        pnlLibraryLayout.setHorizontalGroup(
            pnlLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );
        pnlLibraryLayout.setVerticalGroup(
            pnlLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        add(pnlLibrary, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddMovementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMovementActionPerformed
        controller.addMovement();        
    }//GEN-LAST:event_btnAddMovementActionPerformed

    private void btnRemoveMovementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMovementActionPerformed
        controller.deleteMovement();
    }//GEN-LAST:event_btnRemoveMovementActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMovement;
    private javax.swing.JButton btnRemoveMovement;
    private javax.swing.JPanel pnlDetail;
    private javax.swing.JPanel pnlLibrary;
    private javax.swing.JPanel pnlMovementActions;
    private javax.swing.JScrollPane scrMovements;
    private javax.swing.JTable tblMovements;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        controller.close();
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

    private void handleControllerPropertyChanged(final PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case EnemyMovementEditorController.PROPERTY_ENABLED:
                btnAddMovement.setEnabled(Objects.equals(Boolean.TRUE, pce.getNewValue()));
                btnRemoveMovement.setEnabled(
                        Objects.equals(Boolean.TRUE, pce.getNewValue())
                        && (null != controller.getSelectedMovement())
                );
                break;
            case EnemyMovementEditorController.PROPERTY_SELECTION:
                btnRemoveMovement.setEnabled(null != pce.getNewValue());
                break;
            case EnemyMovementEditorController.PROPERTY_MOVEMENTS:
                // TODO
                break;
            default:
            // ignore this
        }
    }
}
