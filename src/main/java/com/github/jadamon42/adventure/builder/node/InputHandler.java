package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;

import java.util.function.Function;

public class InputHandler extends BasicNode {
    private PlayerDeltaBiFunction<Player, Object> handler;

    public InputHandler() {
        NodeHeader header = new NodeHeader("Handle Input", "Input Handler");
        setHeader(header);
        SubTypeSelector selector = new SubTypeSelector(
                "Set Name",
                event -> handler = (player, obj) -> player.setName(obj.toString())
        );
        handler = (player, obj) -> player.setName(obj.toString());
        setSubTypeSelector(selector);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.HANDLER);
        setFooter(footer);
    }

    public PlayerDeltaBiFunction<Player, Object> getHandler() {
        return handler;
    }
}
