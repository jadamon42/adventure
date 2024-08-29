package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;

import java.util.List;

public class Effect extends BasicNode implements VisitableNode {
    private com.github.jadamon42.adventure.model.Effect effect;
    private final AttachmentLink effectLink;

    public Effect() {
        NodeHeader header = new NodeHeader("New Effect", "Effect");
        setHeader(header);
        header.setChildOnKeyTyped(event -> effect = new com.github.jadamon42.adventure.model.Effect(getTitle()));
        NodeFooter footer = new NodeFooter();
        effectLink = footer.addAttacher(ConnectionType.EFFECT);
        setFooter(footer);
        effect = new com.github.jadamon42.adventure.model.Effect(getTitle());
    }

    public com.github.jadamon42.adventure.model.Effect getEffectModel() {
        return effect;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public List<String> getEffectConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void addEffectConnection(ConnectionLine connectionLine) {
        effectLink.addConnection(connectionLine);
    }
}
