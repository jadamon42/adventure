package com.github.jadamon42.adventure.builder.element;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
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
    }

    public DoubleProperty scaleValueProperty() {
        return scaleValue;
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnZoom(this::onZoom);
        outerNode.setOnScroll(this::onScroll);
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
    }

    private void onZoom(ZoomEvent event) {
        event.consume();
        double zoomFactor = event.getZoomFactor();

        zoomAtPoint(zoomFactor, event.getX(), event.getY());
    }

    private void onScroll(ScrollEvent event) {
        if (event.isControlDown()) {
            event.consume();
            double zoomFactor = 1.0;
            if (event.getDeltaY() > 0) {
                zoomFactor = 1.1;
            } else if (event.getDeltaY() < 0) {
                zoomFactor = 0.9;
            }
            zoomAtPoint(zoomFactor, event.getX(), event.getY());
        }
    }

    private void zoomAtPoint(double zoomFactor, double x, double y) {
        double newScaleValue = scaleValue.get() * zoomFactor;
        double maxScale = 4.5;
        double minScale = 0.05;

        if (newScaleValue <= maxScale && newScaleValue >= minScale) {
            scaleValue.set(newScaleValue);
            updateScale();
            this.layout();

            Bounds innerBounds = zoomNode.getLayoutBounds();
            Bounds viewportBounds = getViewportBounds();

            double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
            double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

            Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(new Point2D(x, y)));
            Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

            Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
            this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
            this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
        }
    }
}
