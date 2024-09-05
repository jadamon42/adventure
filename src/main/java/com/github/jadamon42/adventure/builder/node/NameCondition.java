package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;
import com.github.jadamon42.adventure.common.util.Range;
import com.github.jadamon42.adventure.common.util.SerializableFunction;
import com.github.jadamon42.adventure.common.util.StringUtils;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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
                    clearValidity();
                    conditionCreator = string -> (player -> StringUtils.equalsIgnoreCase(player.getName(), string));
                }
        );
        selector.addOption(
                "Name Equals Any",
                event -> {
                    setGameMessageInput("Player Names (comma separated)");
                    handleValidity(
                        StringUtils::isCorrectlyDelimited,
                        "Invalid name list format. Use a comma separated list of names."
                    );
                    conditionCreator = string -> (player -> StringUtils.equalsAnyIgnoreCase(player.getName(), string));
                }
        );
        selector.addOption(
                "Name Contains",
                event -> {
                    setGameMessageInput("Substring");
                    clearValidity();
                    conditionCreator = string -> (player -> StringUtils.containsIgnoreCase(player.getName(), string));
                }
        );
        selector.addOption(
                "Name Contains Any",
                event -> {
                    setGameMessageInput("Substrings (comma separated)");
                    handleValidity(
                        StringUtils::isCorrectlyDelimited,
                        "Invalid name list format. Use a comma separated list of names (or parts of names)."
                    );
                    conditionCreator = string -> (player -> StringUtils.containsAnyIgnoreCase(player.getName(), string));
                }
        );
        selector.addOption(
                "Name Has Length",
                event -> {
                    setGameMessageInput("Length (number or range)");
                    handleValidity(
                        Range::canParse,
                        "Invalid name length format. Use a number or range (e.g. 3-5)."
                    );
                    conditionCreator = string -> (player -> Range.parse(string).contains(player.getName().length()));
                }
        );
        setSubtypeSelector(selector);
        conditionCreator = string -> (player -> Objects.equals(player.getName(), string));

        NodeFooter footer = new NodeFooter();
        conditionLink = footer.addAttacher(ConnectionType.CONDITION);
        setFooter(footer);
    }

    public static String getDescription() {
        return "Check if the player has a name that meets one of the specified conditions.";
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

    private void handleValidity(
            Predicate<String> validityCheck,
            String tooltipText
    ) {
        setInputValidityCheck(validityCheck);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.setShowDelay(Duration.millis(100));
        setInputInvalidTooltip(tooltip);
        setText("");
        checkValidityOnInput();
    }

    private void clearValidity() {
        setInputValidityCheck(null);
        setInputInvalidTooltip(null);
        checkValidityOnInput();
    }
}
