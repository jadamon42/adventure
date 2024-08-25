package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AttachmentLine extends Line {
    private final AttachmentLink maleLink;
    private final AttachmentLink femaleLink;

    public AttachmentLine(AttachmentLink maleLink, AttachmentLink femaleLink) {
        this.maleLink = maleLink;
        this.femaleLink = femaleLink;
        setStrokeWidth(3.0);
        setStroke(Color.BLUE);
        update();
    }

    public void update() {
        Pane mainBoard = AttachmentManager.getInstance().getCommonParent();
        Bounds maleBoundsInScene = maleLink.localToScene(maleLink.getBoundsInLocal());
        Bounds femaleBoundsInScene = femaleLink.localToScene(femaleLink.getBoundsInLocal());
        Bounds maleBoundsInMainBoard = mainBoard.sceneToLocal(maleBoundsInScene);
        Bounds femaleBoundsInMainBoard = mainBoard.sceneToLocal(femaleBoundsInScene);

        setStartX(maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2);
        setStartY(maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2);
        setEndX(femaleBoundsInMainBoard.getMinX() + femaleBoundsInMainBoard.getWidth() / 2);
        setEndY(femaleBoundsInMainBoard.getMinY() + femaleBoundsInMainBoard.getHeight() / 2);
    }
}
