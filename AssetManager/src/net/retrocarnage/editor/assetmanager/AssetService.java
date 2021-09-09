package net.retrocarnage.editor.assetmanager;

import java.io.InputStream;
import java.util.Collection;
import net.retrocarnage.editor.assetmanager.impl.AssetServiceImpl;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;
import net.retrocarnage.editor.assetmanager.model.SpriteCategory;
import org.openide.util.Lookup;

/**
 * Manages the assets used to create a level.
 *
 * @author Thomas Werner
 */
public abstract class AssetService {

    /**
     * Searches for music assets that match the given criteria.
     *
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of Music assets
     */
    public abstract Collection<Music> findMusic(String tagFilter);

    /**
     * Gets the Music for the given Id.
     *
     * @param id unique identifier of a music asset
     * @return the music asset for the given Id or NULL
     */
    public abstract Music getMusic(String id);

    public abstract void addMusic(Music music, InputStream in);

    public abstract void updateMusicInfo(Music music);

    public abstract void updateMusicAsset(InputStream in);

    public abstract void removeMusic(String id);

    /**
     * Searches for sprite assets that match the given criteria.
     *
     * @param category a SpriteCategory or NULL
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of sprite assets
     */
    public abstract Collection<Sprite> findSprites(SpriteCategory category, String tagFilter);

    /**
     * Gets the Sprite for the given Id.
     *
     * @param id unique identifier of a sprite asset
     * @return the sprite asset for the given Id or NULL
     */
    public abstract Sprite getSprite(String id);

    public abstract void addSprite(Sprite sprite, InputStream in);

    public abstract void updateSpriteInfo(Sprite sprite);

    public abstract void updateSpriteAsset(InputStream in);

    public abstract void removeSprite(String id);

    /**
     * @return an instance of this service
     */
    public static AssetService getDefault() {
        AssetService assetService = Lookup.getDefault().lookup(AssetService.class);
        if (null == assetService) {
            assetService = new AssetServiceImpl();
        }
        return assetService;
    }

}
