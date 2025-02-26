package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;

import java.util.List;

public class InputHandler extends BasicNode implements VisitableNode {
    private PlayerDeltaBiFunction<Object> handler;
    private final AttachmentLink inputHandlerLink;

    public InputHandler() {
        NodeHeader header = new NodeHeader("Handle Input", "Input Handler");
        setHeader(header);
        SubtypeSelector selector = new SubtypeSelector(
                "Set Name",
                event -> {
                    removeGameMessageInput();
                    handler = (player, obj) -> player.setName(obj.toString());
                }
        );
        selector.addOption(
                "Set Custom Attribute",
                event -> {
                    setGameMessageInput("Custom Attribute Name");
                    setOnTextChange(event1 -> {
                        String attributeName = getText();
                        handler = (player, obj) ->
                                player.addCustomAttribute(attributeName, obj.toString());
                    });
                }
        );
        setSubtypeSelector(selector);
        initializeHandler();
        NodeFooter footer = new NodeFooter();
        inputHandlerLink = footer.addAttacher(ConnectionType.HANDLER);
        setFooter(footer);
    }

    public static String getDescription() {
        return "Handle input provided by the player.";
    }

    public PlayerDeltaBiFunction<Object> getHandler() {
        return handler;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public void setSubtype(String subtype) {
        getSubtypeSelector().setSelectedOption(subtype);
    }

    public String getSubtype() {
        return getSubtypeSelector().getSelectedOption();
    }

    public List<String> getInputHandlerConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void addInputHandlerConnection(ConnectionLine connectionLine) {
        inputHandlerLink.addConnection(connectionLine);
    }

    public void initializeHandler() {
        String subtype = getSubtype();
        if (subtype.equals("Set Custom Attribute")) {
            setGameMessageInput("Custom Attribute Name");
            String attributeName = getText();
            handler = (player, obj) ->
                    player.addCustomAttribute(attributeName, obj.toString());
        } else {
            handler = (player, obj) -> player.setName(obj.toString());
        }
    }
}
