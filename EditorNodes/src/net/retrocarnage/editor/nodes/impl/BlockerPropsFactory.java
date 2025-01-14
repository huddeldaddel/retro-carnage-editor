package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Blocker;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;

/**
 * A factory that builds properties for Blockers.
 *
 * @author Thomas Werner
 */
public final class BlockerPropsFactory {

    private BlockerPropsFactory() {
        // Intentionally empty.
    }

    public static Sheet.Set buildBlockerSheet(final Blocker blocker, final boolean readonly) {
        final Sheet.Set blockerSet = Sheet.createPropertiesSet();
        blockerSet.setName("Blocker");
        blockerSet.setDisplayName("Blocker");

        final Node.Property<Boolean> obstacleProp = BlockerPropsFactory.buildObstacleProperty(blocker, readonly);
        obstacleProp.setName(Blocker.PROPERTY_OBSTACLE);
        blockerSet.put(obstacleProp);

        final Node.Property<Boolean> bulletsProp = BlockerPropsFactory.buildStoppingBulletsProperty(blocker, readonly);
        bulletsProp.setName(Blocker.PROPERTY_BULLETSTOPPER);
        blockerSet.put(bulletsProp);

        final Node.Property<Boolean> explosivesProp = BlockerPropsFactory.buildStoppingExplosivesProperty(blocker, readonly);
        explosivesProp.setName(Blocker.PROPERTY_EXPLOSIVESTOPPER);
        blockerSet.put(explosivesProp);

        return blockerSet;
    }

    private static Node.Property<Boolean> buildStoppingBulletsProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isBulletStopper();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setBulletStopper(t);
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Node.Property<Boolean> buildStoppingExplosivesProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isExplosiveStopper();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setExplosiveStopper(t);
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Node.Property<Boolean> buildObstacleProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isObstacle();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setObstacle(t);
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }

            @Override
            public String getShortDescription() {
                return "Prevents the user from accessing this area (when checked)";
            }
        };
    }

}
