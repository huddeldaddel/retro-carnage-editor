package net.retrocarnage.editor.gameplayeditor.interfaces.impl;

import java.beans.PropertyChangeEvent;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditor;
import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import static org.openide.windows.TopComponent.Registry.PROP_ACTIVATED;
import static org.openide.windows.TopComponent.Registry.PROP_TC_CLOSED;

/**
 * Implementation of the abstract GamePlayEditorProxy class.
 *
 * @author Thomas Werner
 */
public class GamePlayEditorProxy implements Lookup.Provider {

    private final ProxyLookup.Controller controller = new ProxyLookup.Controller();
    private final ProxyLookup proxy = new ProxyLookup(controller);

    public GamePlayEditorProxy() {
        TopComponent
                .getRegistry()
                .addPropertyChangeListener((final PropertyChangeEvent pce) -> handleTopComponentRegistryChange(pce));
    }

    @Override
    public Lookup getLookup() {
        return proxy;
    }

    private void handleTopComponentRegistryChange(final PropertyChangeEvent pce) {
        if (PROP_ACTIVATED.equals(pce.getPropertyName()) && (pce.getNewValue() instanceof GamePlayEditor)) {
            final TopComponent tc = (TopComponent) pce.getNewValue();
            controller.setLookups(tc.getLookup());
        } else if (PROP_TC_CLOSED.equals(pce.getPropertyName())) {
            final long numberOfGamePlayEditors = TopComponent
                    .getRegistry()
                    .getOpened()
                    .stream()
                    .filter(GamePlayEditor.class::isInstance)
                    .count();
            if (0 == numberOfGamePlayEditors) {
                controller.setLookups();
            }
        }
    }

}
