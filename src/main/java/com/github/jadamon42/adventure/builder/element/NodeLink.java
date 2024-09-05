package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionPoint;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.common.util.StringUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.List;

public class NodeLink extends ConnectionPoint {
    public NodeLink(ConnectionGender gender) {
        super(gender);
        FontAwesomeIconView nodeLinkIcon = new FontAwesomeIconView(FontAwesomeIcon.CARET_SQUARE_ALT_RIGHT);
        nodeLinkIcon.setFill(Paint.valueOf("lightgrey"));
        nodeLinkIcon.setGlyphSize(25);
        getChildren().add(nodeLinkIcon);

        String description;
        description = StringUtils.softWrap(
                "Link to next node in the story",
                50);
        Tooltip tooltip = new Tooltip(description);
        tooltip.setShowDuration(Duration.INDEFINITE);
        Tooltip.install(this, tooltip);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.NODE;
    }

    public Node getLinkedNode() {
        Node linked = null;
        List<Node> linkedNodes = getConnectedNodes();
        if (!linkedNodes.isEmpty()) {
            linked = linkedNodes.getFirst();
        }
        return linked;
    }
}
