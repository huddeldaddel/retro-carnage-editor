package net.retrocarnage.editor.renderer.editor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.retrocarnage.editor.model.Blocker;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.renderer.common.TextureFactory;

/**
 * Paints the Obstacles that are contained in a stack of Layers.
 *
 * @author Thomas Werner
 */
class ObstaclePainter {

    private static final Map<String, BufferedImage> textureCache = new HashMap<>();

    private final List<Layer> layers;
    private final Graphics2D g2d;
    private final float scalingFactor;

    public ObstaclePainter(final List<Layer> layers, final Graphics2D g2d, final float scalingFactor) {
        this.layers = layers;
        this.g2d = g2d;
        this.scalingFactor = scalingFactor;
    }

    public void paintObstacles() {
        layers.stream()
                .filter(l -> (l.isVisible()))
                .map(Layer::getObstacles)
                .forEachOrdered(obstacles -> {
                    for (int idx = obstacles.size() - 1; idx >= 0; idx--) {
                        paintObstacle(obstacles.get(idx));
                    }
                });
        layers.stream()
                .filter(l -> (l.isVisible()))
                .map(Layer::getVisualAssets)
                .forEachOrdered(visualAssets -> {
                    for (int idx = visualAssets.size() - 1; idx >= 0; idx--) {
                        final VisualAsset vAsset = visualAssets.get(idx);
                        if (vAsset.isObstacle()) {
                            paintObstacle(vAsset);
                        }
                    }
                });
    }

    /**
     * Paints a single obstacle. This is done by using a texture to fill the obstacle area.
     *
     * @param obstacle the obstacle to be painted
     */
    private void paintObstacle(final Blocker obstacle) {
        final Position scaledPosition = obstacle.getPosition().scale(scalingFactor);
        final int sideLength = (int) (10 * scalingFactor);
        final BufferedImage texture = getObstacleTexture(
                sideLength,
                obstacle.isBulletStopper(),
                obstacle.isExplosiveStopper()
        );
        final Rectangle anchor = new Rectangle(0, 0, sideLength, sideLength);
        g2d.setPaint(new TexturePaint(texture, anchor));
        g2d.fill(scaledPosition.toRectangle());
    }

    /**
     * Gets the texture to be used to paint an obstacle with the given parameters.
     *
     * @param sideLength specifies the width / height of the texture image. Used for zooming.
     * @param bullets the texture will have an additional line of it stops bullets.
     * @param explosives the texture will have a diamond pattern of it stops explosives, too.
     * @return the texture
     */
    private static BufferedImage getObstacleTexture(
            final int sideLength,
            final boolean bullets,
            final boolean explosives
    ) {
        final String imageKey = String.format("%d%b%b", sideLength, bullets, explosives);
        if (textureCache.containsKey(imageKey)) {
            return textureCache.get(imageKey);
        }

        final BufferedImage result = TextureFactory.buildObstacleTexture(sideLength, bullets, explosives);
        textureCache.put(imageKey, result);
        return result;
    }

}
