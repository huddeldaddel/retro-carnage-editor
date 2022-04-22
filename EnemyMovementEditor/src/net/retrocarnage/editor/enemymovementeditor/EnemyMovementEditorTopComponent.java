package net.retrocarnage.editor.enemymovementeditor;

import java.beans.PropertyChangeEvent;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
        /*
        switch (pce.getPropertyName()) {
            case EnemyMovementEditorController.PROPERTY_ENABLED:
                btnAddSection.setEnabled(Objects.equals(Boolean.TRUE, pce.getNewValue()));
                btnRemoveSection.setEnabled(
                        Objects.equals(Boolean.TRUE, pce.getNewValue())
                        && (null != controller.getSelectedSection())
                );
                break;
            case EnemyMovementEditorController.PROPERTY_SELECTION:
                btnRemoveSection.setEnabled(null != pce.getNewValue());
                break;
            case EnemyMovementEditorController.PROPERTY_SECTIONS:
                ((SectionMapLabel) lblMap).setSections((List<Section>) pce.getNewValue());
                break;
            default:
            // ignore this
        }
         */
    }
}
