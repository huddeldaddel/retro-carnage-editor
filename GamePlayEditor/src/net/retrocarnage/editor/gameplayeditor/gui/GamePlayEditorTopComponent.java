package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.TransferHandler;
import net.retrocarnage.editor.gameplayeditor.impl.GamePlayEditorRepository;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditor;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.nodes.GamePlayNode;
import net.retrocarnage.editor.nodes.nodes.ObstacleNode;
import net.retrocarnage.editor.nodes.nodes.VisualAssetNode;
import net.retrocarnage.editor.playermodeloverlay.PlayerModelOverlayService;
import net.retrocarnage.editor.zoom.ZoomService;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the GamePlay of a Mission.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.gameplayeditor//GamePlayEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "GamePlayEditorTopComponent",
        iconBase = "net/retrocarnage/editor/gameplayeditor/images/accessories-text-editor.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_GamePlayEditorAction=GamePlayEditor",
    "CTL_GamePlayEditorTopComponent=GamePlayEditor Window",
    "HINT_GamePlayEditorTopComponent=This is a GamePlayEditor window"
})
public final class GamePlayEditorTopComponent
        extends TopComponent
        implements ExplorerManager.Provider, GamePlayEditor, PropertyChangeListener {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final GamePlayEditorController controller;
    private final TransferHandler transferHandler;

    public GamePlayEditorTopComponent() {
        this(null);
    }

    public GamePlayEditorTopComponent(final Mission mission) {
        controller = new GamePlayEditorController(mission);
        controller.addPropertyChangeListener(this);
        transferHandler = new DragAndDropTransferHandler(controller);
        PlayerModelOverlayService.getDefault().addPropertyChangeListener(this);
        ZoomService.getDefault().addPropertyChangeListener(this);

        final ActionMap map = getActionMap();
        associateLookup(new ProxyLookup(
                ExplorerUtils.createLookup(explorerManager, map),
                new AbstractLookup(controller.getLookupContent())
        ));

        getLookup().lookup(SelectionController.class).addPropertyChangeListener(this);
        final GamePlayNode rootNode = new GamePlayNode(
                controller.getGamePlay(),
                getLookup().lookup(LayerController.class)
        );
        explorerManager.setRootContext(rootNode);
        try {
            explorerManager.setSelectedNodes(new Node[]{rootNode});
        } catch (PropertyVetoException ex) {
            Exceptions.printStackTrace(ex);
        }

        initComponents();

        setName(Bundle.CTL_GamePlayEditorTopComponent());
        setToolTipText(Bundle.HINT_GamePlayEditorTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code.
     *
     * The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrPane = new javax.swing.JScrollPane();
        scrPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        pnlDisplay = new GamePlayDisplay(scrPane);
        ((GamePlayDisplay) pnlDisplay).updateDisplay(controller.getGamePlay(), null);

        setLayout(new java.awt.BorderLayout());

        pnlDisplay.setTransferHandler(transferHandler);
        pnlDisplay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlDisplayMouseDragged(evt);
            }
        });
        pnlDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDisplayMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDisplayMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlDisplayMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlDisplayMouseReleased(evt);
            }
        });
        pnlDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pnlDisplayKeyTyped(evt);
            }
        });
        scrPane.setViewportView(pnlDisplay);

        add(scrPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void pnlDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDisplayMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Point location = evt.getPoint();
            location.translate(-GamePlayDisplay.BORDER_WIDTH, -GamePlayDisplay.BORDER_WIDTH);
            controller.handleMouseClick(location);
            pnlDisplay.requestFocus();
        } else if (evt.getButton() == MouseEvent.BUTTON3) {
            final Node[] selectedNodes = getExplorerManager().getSelectedNodes();
            if (null != selectedNodes && 1 == selectedNodes.length && !(selectedNodes[0] instanceof GamePlayNode)) {
                final JPopupMenu popup = new JPopupMenu();
                for (Action action : selectedNodes[0].getActions(true)) {
                    popup.add(action);
                }
                popup.show(pnlDisplay, evt.getPoint().x, evt.getPoint().y);
            }
            pnlDisplay.requestFocus();
        }
    }//GEN-LAST:event_pnlDisplayMouseClicked

    private void pnlDisplayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDisplayMousePressed
        final Point location = evt.getPoint();
        location.translate(-GamePlayDisplay.BORDER_WIDTH, -GamePlayDisplay.BORDER_WIDTH);
        controller.handleMousePressed(location);
    }//GEN-LAST:event_pnlDisplayMousePressed

    private void pnlDisplayMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDisplayMouseReleased
        final Point location = evt.getPoint();
        location.translate(-GamePlayDisplay.BORDER_WIDTH, -GamePlayDisplay.BORDER_WIDTH);
        controller.handleMouseReleased(location);
    }//GEN-LAST:event_pnlDisplayMouseReleased

    private void pnlDisplayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDisplayMouseExited
        controller.handleMouseExited();
    }//GEN-LAST:event_pnlDisplayMouseExited

    private void pnlDisplayMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDisplayMouseDragged
        final Point location = evt.getPoint();
        location.translate(-GamePlayDisplay.BORDER_WIDTH, -GamePlayDisplay.BORDER_WIDTH);
        controller.handleMouseDragged(location);
    }//GEN-LAST:event_pnlDisplayMouseDragged

    private void pnlDisplayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlDisplayKeyTyped
        if (KeyEvent.VK_DELETE == evt.getKeyChar()) {
            controller.removeSelectedElement();
        }
    }//GEN-LAST:event_pnlDisplayKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlDisplay;
    private javax.swing.JScrollPane scrPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    protected void componentActivated() {
        ExplorerUtils.activateActions(explorerManager, true);
    }

    @Override
    protected void componentDeactivated() {
        ExplorerUtils.activateActions(explorerManager, false);
    }

    @Override
    public void componentOpened() {
        final Mission mission = controller.getMission();
        if (null != mission) {
            GamePlayEditorRepository.INSTANCE.register(mission.getId(), this);
            this.setDisplayName(mission.getName());
        }
    }

    @Override
    public void componentClosed() {
        final Mission mission = controller.getMission();
        if (null != mission) {
            GamePlayEditorRepository.INSTANCE.unregister(mission.getId(), this);
        }
        PlayerModelOverlayService.getDefault().removePropertyChangeListener(this);
        ZoomService.getDefault().removePropertyChangeListener(this);
        controller.removePropertyChangeListener(this);
        controller.close();
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        if (GamePlayEditorController.PROPERTY_GAMEPLAY.equals(pce.getPropertyName())
                || ZoomService.PROPERTY_ZOOM.equals(pce.getPropertyName())
                || PlayerModelOverlayService.PROPERTY_VISIBILITY.equals(pce.getPropertyName())) {
            final GamePlay gamePlay = controller.getGamePlay();
            final Selectable selection = getLookup().lookup(SelectionController.class).getSelection();
            ((GamePlayDisplay) pnlDisplay).updateDisplay(gamePlay, selection);
        } else if (SelectionController.PROPERTY_SELECTION.equals(pce.getPropertyName())) {
            try {
                if (null == pce.getNewValue()) {
                    explorerManager.setSelectedNodes(new Node[]{explorerManager.getRootContext()});
                } else {
                    Node nodeToSelect = getNodeForSelection(explorerManager.getRootContext(), (Selectable) pce.getNewValue());
                    if (null != nodeToSelect) {
                        explorerManager.setSelectedNodes(new Node[]{nodeToSelect});
                    } else {
                        explorerManager.setSelectedNodes(new Node[]{explorerManager.getRootContext()});
                    }
                }
            } catch (PropertyVetoException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private Node getNodeForSelection(final Node parent, final Selectable selection) {
        if (parent instanceof VisualAssetNode) {
            if (((VisualAssetNode) parent).getVisualAsset() == selection) {
                return parent;
            }
        } else if (parent instanceof ObstacleNode) {
            if (((ObstacleNode) parent).getObstacle() == selection) {
                return parent;
            }
        } else {
            for (Node child : parent.getChildren().getNodes()) {
                final Node match = getNodeForSelection(child, selection);
                if (null != match) {
                    return match;
                }
            }
        }
        return null;
    }

}
