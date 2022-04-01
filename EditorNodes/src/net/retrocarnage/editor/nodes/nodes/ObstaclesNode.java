package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node used to group the Obstacles of a Layer.
 *
 * @author Thomas Werner
 */
public class ObstaclesNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/obstacles.png";
    private static final String OPEN_ICON_PATH = "/net/retrocarnage/editor/nodes/icons/folder_open.png";
    private static final Image ICON = IconUtil.loadIcon(ObstaclesNode.class.getResourceAsStream(ICON_PATH));
    private static final Image OPEN_ICON = IconUtil.loadIcon(ObstaclesNode.class.getResourceAsStream(OPEN_ICON_PATH));

    public ObstaclesNode(final Layer layer) {
        super(new ObstacleChildren(layer), Lookups.singleton(layer));
        setDisplayName("Obstacles");
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return OPEN_ICON;
    }

}
