package net.retrocarnage.editor.model;

import java.util.List;

/**
 * Layers are used to organized graphical assets.
 *
 * This makes it easer to work on different aspects of the mission one at a time.
 *
 * @author Thomas Werner
 */
public class Layer {

    public static final String DEFAULT_LAYER_NAME = "Default";

    private boolean locked;
    private String name;
    private boolean visible;
    private final ObservableList<VisualAsset> visualAssets;

    public Layer() {
        visualAssets = new ObservableList<>();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public ObservableList<VisualAsset> getVisualAssets() {
        return visualAssets;
    }

    public void setVisualAssets(final List<VisualAsset> visualAssets) {
        this.visualAssets.clear();
        this.visualAssets.addAll(visualAssets);
    }

}
