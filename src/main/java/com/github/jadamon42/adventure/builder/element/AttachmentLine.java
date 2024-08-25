package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AttachmentLine extends Line {
    private AttachmentLink maleLink;
    private AttachmentLink femaleLink;

    public AttachmentLine(AttachmentLink maleLink, AttachmentLink femaleLink) {
        this.maleLink = maleLink;
        this.femaleLink = femaleLink;
        setStrokeWidth(3.0);
        setStroke(Color.BLUE);
        setMouseTransparent(true);
        update();
    }

    public void setMaleLink(AttachmentLink maleLink) {
        this.maleLink = maleLink;
    }

    public void setFemaleLink(AttachmentLink femaleLink) {
        this.femaleLink = femaleLink;
    }

    public void update() {
        Pane mainBoard = AttachmentManager.getInstance().getCommonParent();
        if (maleLink != null) {
            Bounds maleBoundsInScene = maleLink.localToScene(maleLink.getBoundsInLocal());
            Bounds maleBoundsInMainBoard = mainBoard.sceneToLocal(maleBoundsInScene);
            setStartX(maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2);
            setStartY(maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2);
        }
        if (femaleLink != null) {
            Bounds femaleBoundsInScene = femaleLink.localToScene(femaleLink.getBoundsInLocal());
            Bounds femaleBoundsInMainBoard = mainBoard.sceneToLocal(femaleBoundsInScene);
            setEndX(femaleBoundsInMainBoard.getMinX() + femaleBoundsInMainBoard.getWidth() / 2);
            setEndY(femaleBoundsInMainBoard.getMinY() + femaleBoundsInMainBoard.getHeight() / 2);
        }
    }

    public void updateEnd(double x, double y) {
        setEndX(x);
        setEndY(y);
    }
}
