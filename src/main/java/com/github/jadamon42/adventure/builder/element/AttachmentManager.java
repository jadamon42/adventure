package com.github.jadamon42.adventure.builder.element;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class AttachmentManager {
    private static AttachmentManager instance;
    private AttachmentLink selectedLink;
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
            selectedLink.startFollowingCursor();
        } else {
            if (canAttach(selectedLink, link)) {
                attachLinks(selectedLink, link);
            }
            selectedLink.stopFollowingCursor();
            selectedLink = null;
        }
    }

    private boolean canAttach(AttachmentLink link1, AttachmentLink link2) {
        return link1.getObjectAttachmentType() == link2.getObjectAttachmentType() &&
               link1.getGenderAttachmentType() != link2.getGenderAttachmentType();
    }

    private void attachLinks(AttachmentLink link1, AttachmentLink link2) {
        if (link1.getGenderAttachmentType() == AttachmentLink.GenderAttachmentType.MALE) {
            createAttachmentLine(link1, link2);
        } else {
            createAttachmentLine(link2, link1);
        }
    }

    private void createAttachmentLine(AttachmentLink maleLink, AttachmentLink femaleLink) {
        AttachmentLine line = new AttachmentLine(maleLink, femaleLink);
        attachmentLines.add(line);
        commonParent.getChildren().add(line);
    }

    public void updateLines() {
        for (AttachmentLine line : attachmentLines) {
            line.update();
        }
    }
}
