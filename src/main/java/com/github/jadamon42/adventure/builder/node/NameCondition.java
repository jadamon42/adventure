package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;
import com.github.jadamon42.adventure.util.SerializableFunction;

import java.util.List;
import java.util.Objects;

public class NameCondition extends BasicNode implements ConditionTranslator, VisitableNode {
    private SerializableFunction<String, BooleanFunction<Player>> conditionCreator;
    private final AttachmentLink conditionLink;

    public NameCondition() {
        NodeHeader header = new NodeHeader("Has Name", "Name Condition");
        setHeader(header);

        setGameMessageInput("");

        SubtypeSelector selector = new SubtypeSelector(
                "Name Equals",
                event -> {
                    setGameMessageInput("Player Name");
                    conditionCreator = string -> (player -> player.getName().equalsIgnoreCase(string));
                }
        );
        selector.addOption(
                "Name Contains",
                event -> {
                    setGameMessageInput("Substring");
                    conditionCreator = string -> (player -> player.getName().toLowerCase().contains(string.toLowerCase()));
                }
        );
        selector.addOption(
                "Name Has Length",
                event -> {
                    setGameMessageInput("Length");
                    conditionCreator = string -> (player -> player.getName().length() == string.length());
                }
        );
        setSubtypeSelector(selector);
        conditionCreator = string -> (player -> Objects.equals(player.getName(), string));

        NodeFooter footer = new NodeFooter();
        conditionLink = footer.addAttacher(ConnectionType.CONDITION);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        String text = getText();
        SerializableFunction<String, BooleanFunction<Player>> creator = conditionCreator;
        return player -> creator.apply(text).apply(player);
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

    public List<String> getConditionConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void addConditionConnection(ConnectionLine connectionLine) {
        conditionLink.addConnection(connectionLine);
    }
}
