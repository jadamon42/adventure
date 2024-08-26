package com.github.jadamon42.adventure.builder.element;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class AttachmentManager {
    private static AttachmentManager instance;
    private AttachmentLink selectedLink;
    private AttachmentLine currentLine;
    private final List<AttachmentLine> attachmentLines = new ArrayList<>();
    private Pane commonParent;

    private AttachmentManager() {}

    public static AttachmentManager getInstance() {
        if (instance == null) {
            instance = new AttachmentManager();
        }
        return instance;
    }

    public void setCommonParent(Pane commonParent) {
        this.commonParent = commonParent;
    }

    public Pane getCommonParent() {
        return commonParent;
    }

    public void handleAttachmentClick(AttachmentLink link) {
        if (selectedLink == null) {
            selectedLink = link;
            currentLine = new AttachmentLine(link, null);
            attachmentLines.add(currentLine);
            commonParent.getChildren().add(currentLine);
            selectedLink.startFollowingCursor(currentLine);
        } else {
            if (canAttach(selectedLink, link)) {
                attachLinks(selectedLink, link);
            }
        }
    }

    private boolean canAttach(AttachmentLink link1, AttachmentLink link2) {
        return link1.getObjectAttachmentType() == link2.getObjectAttachmentType() &&
               link1.getGenderAttachmentType() != link2.getGenderAttachmentType();
    }

    private void attachLinks(AttachmentLink link1, AttachmentLink link2) {
        if (link1.getGenderAttachmentType() == AttachmentLink.GenderAttachmentType.MALE) {
            currentLine.setFemaleLink(link2);
        } else {
            currentLine.setMaleLink(link2);
        }
        currentLine.update();
        link1.setIsConnected(true);
        link2.setIsConnected(true);
    }

    public void cancelCurrentLine() {
        if (selectedLink != null) {
            selectedLink.stopFollowingCursor();
            commonParent.getChildren().remove(currentLine);
            attachmentLines.remove(currentLine);
            selectedLink = null;
            currentLine = null;
        }
    }

    public void updateLines() {
        for (AttachmentLine line : attachmentLines) {
            line.update();
        }
    }
}
