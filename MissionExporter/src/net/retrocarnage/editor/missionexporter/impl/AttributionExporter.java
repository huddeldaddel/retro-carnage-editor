package net.retrocarnage.editor.missionexporter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.AssetServiceFactory;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.missionmanager.MissionServiceFactory;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Creates the attribution document for an exported mission. This file contains attributions for all assets used in the
 * mission.
 *
 * @author Thomas Werner
 */
public class AttributionExporter {

    private static final Logger logger = Logger.getLogger(AttributionExporter.class.getName());
    private static final String TEMPLATE = "/net/retrocarnage/editor/missionexporter/templates/attribution-template.md";

    private final AssetService assetService;
    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;
    private final MissionService missionService;
    private String imageAttributions = null;
    private String musicAttributions = null;

    /**
     * Creates a new instance of AttributionExporter. This constructor is designed to be used at runtime.
     *
     * @param mission the mission to export the attributions for
     * @param exportFolderStructure definition of the export folder structure
     */
    public AttributionExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.assetService = AssetServiceFactory.buildAssetService();
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
        this.missionService = MissionServiceFactory.buildMissionService();
    }

    /**
     * Creates a new instance of AttributionExporter. This constructor is designed to be used in unit tests.
     *
     * @param assetService service used to load asset data
     * @param mission the mission to export the attributions for
     * @param exportFolderStructure definition of the export folder structure
     * @param missionService service used to load mission data
     */
    public AttributionExporter(final AssetService assetService, final Mission mission,
                               final ExportFolderStructure exportFolderStructure, MissionService missionService) {
        this.assetService = assetService;
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
        this.missionService = missionService;
    }

    /**
     * Exports the attribution information as Markdown formatted file.
     */
    public void export() {
        final File mdFile = exportFolderStructure.getMissionAttributionFile();
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile, StandardCharsets.UTF_8))) {
            for (String line : readTemplate()) {
                writer.write(replacePlaceholders(line));
                writer.write("\n");
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to write attribution file for mission " + mission.getName(), ex);
        }
    }

    /**
     * Reads the attribution template.
     *
     * @return the lines of the attribution template
     * @throws IOException when template cannot be read
     */
    private List<String> readTemplate() throws IOException {
        final List<String> result = new ArrayList<>();
        final InputStream templateStream = AttributionExporter.class.getResourceAsStream(TEMPLATE);
        try (final var reader = new BufferedReader(new InputStreamReader(templateStream, StandardCharsets.UTF_8))) {
            String line;
            while (null != (line = reader.readLine())) {
                result.add(line);
            }
        }
        return result;
    }

    /**
     * Replaces all placeholders in a given line of the Markdown template.
     *
     * @param inputLine the line that possibly contains placeholders
     * @return line with placeholders replaced by formatted content
     */
    private String replacePlaceholders(final String inputLine) throws IOException {
        String result = inputLine;
        if(result.contains("<MISSION>")) {
            result = result.replace("<MISSION>", mission.getName());
        }
        if(result.contains("<IMAGES>")) {
            result = result.replace("<IMAGES>", buildImageAttributions());
        }
        if(result.contains("<MUSIC>")) {
            result = result.replace("<MUSIC>", buildMusicAttributions());
        }
        return result;
    }

    /**
     * Builds the attribution text for all the sprites used in a mission.
     *
     * @return Markdown formatted attributions
     */
    private String buildImageAttributions() throws IOException {
        if (null == imageAttributions) {
            final StringBuilder sbuilder = new StringBuilder();
            for(Sprite sprite: getSprites()) {
                final AttributionData attribution = sprite.getAttributionData();
                final String mdString = buildAttribution(sprite.getName(), attribution);
                sbuilder.append(mdString)
                        .append("\n");
            }
            imageAttributions = sbuilder.toString();
        }
        return imageAttributions;
    }

    /**
     * Gets the sprites used in a mission ordered by name.
     *
     * @return list of sprites.
     */
    private List<Sprite> getSprites() throws IOException {
        final Map<String, Sprite> sprites = new HashMap<>();
        final GamePlay gamePlay = missionService.loadGamePlay(mission.getId());
        for(Layer layer: gamePlay.getLayers()) {
            for(VisualAsset asset: layer.getVisualAssets()) {
                sprites.put(asset.getAssetId(), assetService.getSprite(asset.getAssetId()));
            }
        }
        final List<Sprite> result = new ArrayList<>(sprites.values());
        Collections.sort(result, (t1, t2) -> t1.getName().compareTo(t2.getName()));
        return result;
    }

    /**
     * Builds the attribution text for the music used in a mission.
     *
     * @return Markdown formatted attribution
     */
    private String buildMusicAttributions() {
        if (null == musicAttributions && mission.getSong() != null && !mission.getSong().isEmpty()) {
            final Music music = assetService.getMusic(mission.getSong());
            final AttributionData attribution = music.getAttributionData();
            musicAttributions = buildAttribution(music.getName(), attribution) + "\n";
        }
        return musicAttributions;
    }

    /**
     * Creates a Markdown formatted list element for a singe attribution
     *
     * @param name the element created by another author
     * @param attribution AttributionData object containing the data to be exported
     * @return markdown formatted list element
     */
    private String buildAttribution(final String name, final AttributionData attribution) {
        if (null == name || name.isBlank() || null == attribution.getAuthor() || attribution.getAuthor().isBlank())
            return "";

        String result = String.format("- %s by %s", name, attribution.getAuthor());

        String links = "";
        boolean linkPresent = false;
        if(attribution.getWebsite() != null && !attribution.getWebsite().isBlank()) {
            links = String.format("[Link](%s)", attribution.getWebsite());
            linkPresent = true;
        }

        if(attribution.getLicenseLink() != null && !attribution.getLicenseLink().isBlank()) {
            if(linkPresent) {
                links = String.format("%s, [License](%s)", links, attribution.getLicenseLink());
            } else {
                links = String.format("[License](%s)", attribution.getLicenseLink());
            }
        }

        if(attribution.getLicenseText() != null && !attribution.getLicenseText().isBlank()) {
            final String licenseText = attribution.getLicenseText().lines().reduce("",(t, u) -> t + "  " + u + "  \n");
            return (links.isEmpty()) 
                    ? String.format("%s  %n  License:  %n%s", result, licenseText) 
                    : String.format("%s (%s)  %n  License:  %n%s", result, links, licenseText);
        }
        
        return (links.isEmpty()) 
                ? result 
                : String.format("%s (%s)", result, links);
    }

}
