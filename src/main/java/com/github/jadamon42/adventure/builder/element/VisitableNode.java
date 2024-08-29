package com.github.jadamon42.adventure.builder.element;

public interface VisitableNode {
    void accept(NodeVisitor visitor);
}
