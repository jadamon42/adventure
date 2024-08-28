package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

import java.util.Objects;
import java.util.function.Function;

public class NameCondition extends BasicNode implements ConditionTranslator {
    private Function<String, BooleanFunction<Player>> conditionCreator;

    public NameCondition() {
        NodeHeader header = new NodeHeader("Has Name", "Name Condition");
        setHeader(header);

        setGameMessageInput("");

        SubTypeSelector selector = new SubTypeSelector(
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
        setSubTypeSelector(selector);
        conditionCreator = string -> (player -> Objects.equals(player.getName(), string));

        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        return player -> conditionCreator.apply(getText()).apply(player);
    }
}
