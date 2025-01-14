package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.retrocarnage.editor.model.Direction;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemySkin;
import net.retrocarnage.editor.model.EnemySpeed;
import net.retrocarnage.editor.model.EnemyType;
import net.retrocarnage.editor.model.Position;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates Nodes for each EnemyType
 *
 * @author Thomas Werner
 */
public class EnemyNodeFactory extends ChildFactory<EnemyType> {

    @Override
    protected boolean createKeys(final List<EnemyType> toPopulate) {
        toPopulate.addAll(Arrays.asList(EnemyType.values()));
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final EnemyType key) {
        switch (key) {
            case PERSON:
                return new EnemyNode[]{new EnemyNode(buildPersonTemplate())};
            case LANDMINE:
                return new EnemyNode[]{new EnemyNode(buildLandmineTemplate())};
            default:
                return new EnemyNode[]{};
        }
    }

    private static Enemy buildPersonTemplate() {
        final Enemy result = new Enemy();
        result.setActions(Collections.emptyList());
        result.setDirection(Direction.DOWN.getValue());
        result.setMovements(Collections.emptyList());
        result.setPosition(new Position(0, 0, Enemy.PERSON_WIDTH, Enemy.PERSON_HEIGHT));
        result.setSpeed(EnemySpeed.NORMAL.getSpeed());
        result.setSkin(EnemySkin.GREY_JUMPER_WITH_RIFLE.getName());
        result.setType(EnemyType.PERSON.getValue());
        return result;
    }

    private static Enemy buildLandmineTemplate() {
        final Enemy result = new Enemy();
        result.setActions(Collections.emptyList());
        result.setDirection(Direction.DOWN.getValue());
        result.setMovements(Collections.emptyList());
        result.setPosition(new Position(0, 0, Enemy.LANDMINE_WIDTH, Enemy.LANDMINE_HEIGHT));
        result.setSpeed(EnemySpeed.NONE.getSpeed());
        result.setSkin("");
        result.setType(EnemyType.LANDMINE.getValue());
        return result;
    }

}
