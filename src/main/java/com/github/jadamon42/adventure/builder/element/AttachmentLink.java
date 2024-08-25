package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class AttachmentLink extends FontAwesomeIconView {
    public enum ObjectAttachmentType {
        ITEM, EFFECT, CONDITION, HANDLER
    }

    public enum GenderAttachmentType {
        MALE, FEMALE
    }

    private final ObjectAttachmentType objectAttachmentType;
    private final GenderAttachmentType genderAttachmentType;
    private boolean isConnected;

    public AttachmentLink(ObjectAttachmentType objectAttachmentType, GenderAttachmentType genderAttachmentType) {
        super(FontAwesomeIcon.CIRCLE_THIN);
        this.objectAttachmentType = objectAttachmentType;
        this.genderAttachmentType = genderAttachmentType;
        setFill(Paint.valueOf("lightgrey"));
        setGlyphSize(12);
        setCursor(Cursor.HAND);
        setOnMouseClicked(this::handleClick);
    }

    public ObjectAttachmentType getObjectAttachmentType() {
        return objectAttachmentType;
    }

    public GenderAttachmentType getGenderAttachmentType() {
        return genderAttachmentType;
    }

    public void setIsConnected(boolean isConnected) {
        if (genderAttachmentType == GenderAttachmentType.FEMALE) {
            setCursor(Cursor.DEFAULT);
        }
        this.isConnected = isConnected;
    }

    private void handleClick(MouseEvent event) {
        event.consume();
        if (genderAttachmentType == GenderAttachmentType.FEMALE && isConnected) {
            return;
        }
        AttachmentManager.getInstance().handleAttachmentClick(this);
    }

    public void startFollowingCursor(AttachmentLine line) {
        Pane mainBoard = AttachmentManager.getInstance().getCommonParent();
        Bounds maleBoundsInScene = localToScene(getBoundsInLocal());
        Bounds maleBoundsInMainBoard = mainBoard.sceneToLocal(maleBoundsInScene);
        double startX = maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2;
        double startY = maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2;
        line.updateEnd(startX, startY);

        getScene().setOnMouseMoved(event -> {
            double xInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double yInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            setLayoutX(xInMainBoard);
            setLayoutY(yInMainBoard);
            line.updateEnd(xInMainBoard, yInMainBoard);
        });

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                AttachmentManager.getInstance().cancelCurrentLine();
            }
        });
    }

    public void stopFollowingCursor() {
        if (getScene() != null) {
            getScene().setOnMouseMoved(null);
            getScene().setOnKeyPressed(null);
        }
    }
}
