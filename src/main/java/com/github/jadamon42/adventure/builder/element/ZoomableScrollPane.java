package com.github.jadamon42.adventure.builder.element;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.VBox;

public class ZoomableScrollPane extends ScrollPane {
    private final DoubleProperty scaleValue = new SimpleDoubleProperty(1.0);
    private Node target;
    private Node zoomNode;

    public ZoomableScrollPane() {
        super();
        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true);
        setFitToWidth(true);
    }

    public void setTarget(Node target) {
        this.target = target;
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));
        updateScale();
        centerContent();
    }

    public DoubleProperty scaleValueProperty() {
        return scaleValue;
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnZoom(this::onZoom);
        return outerNode;
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        target.setScaleX(scaleValue.get());
        target.setScaleY(scaleValue.get());
//        AppState.getInstance().setScaleFactor(scaleValue.get());
    }

    private void onZoom(ZoomEvent event) {
        event.consume();
        double zoomFactor = event.getZoomFactor();

        // Calculate new scale value
        double newScaleValue = scaleValue.get() * zoomFactor;

        // Determine the minimum scale based on target node's bounds
        Bounds targetBounds = target.getLayoutBounds();
        double minScale = Math.min(
                getViewportBounds().getWidth() / targetBounds.getWidth(),
                getViewportBounds().getHeight() / targetBounds.getHeight()
        );

        // Clamp the scale value
        if (newScaleValue < minScale) {
            newScaleValue = minScale;
        }

        scaleValue.set(newScaleValue);
        updateScale();
        this.layout();

        // Adjust the scroll position
        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(new Point2D(event.getX(), event.getY())));
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }

    private void centerContent() {
        Bounds viewportBounds = getViewportBounds();
        Bounds contentBounds = zoomNode.getLayoutBounds();

        double centerX = (contentBounds.getWidth() - viewportBounds.getWidth()) / 2;
        double centerY = (contentBounds.getHeight() - viewportBounds.getHeight()) / 2;

        setHvalue(centerX / (contentBounds.getWidth() - viewportBounds.getWidth()));
        setVvalue(centerY / (contentBounds.getHeight() - viewportBounds.getHeight()));
    }
}
