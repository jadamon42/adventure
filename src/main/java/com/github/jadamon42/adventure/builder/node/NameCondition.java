package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class NameCondition extends BasicNode implements ConditionTranslator, VisitableNode {
    private Function<String, BooleanFunction<Player>> conditionCreator;
    private final AttachmentLink conditionLink;

    public NameCondition() {
        NodeHeader header = new NodeHeader("Has Name", "Name Condition");
        setHeader(header);

        setGameMessageInput("");

        SubtypeSelector selector = new SubtypeSelector(
                "Name Equals",
                event -> {
                    setGameMessageInput("Player Name");
                    conditionCreator = string -> (player -> Objects.equals(player.getName(), string));
                }
        );
        selector.addOption(
                "Name Contains",
                event -> {
                    setGameMessageInput("Substring");
                    conditionCreator = string -> (player -> player.getName().contains(string));
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
        return player -> conditionCreator.apply(getText()).apply(player);
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
