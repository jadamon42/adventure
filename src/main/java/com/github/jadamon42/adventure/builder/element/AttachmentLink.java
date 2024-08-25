package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

    public AttachmentLink(ObjectAttachmentType objectAttachmentType, GenderAttachmentType genderAttachmentType) {
        super(FontAwesomeIcon.CIRCLE_THIN);
        this.objectAttachmentType = objectAttachmentType;
        this.genderAttachmentType = genderAttachmentType;
        setFill(Paint.valueOf("lightgrey"));
        setGlyphSize(12);
    }

    public ObjectAttachmentType getObjectAttachmentType() {
        return objectAttachmentType;
    }

    public GenderAttachmentType getGenderAttachmentType() {
        return genderAttachmentType;
    }
}
