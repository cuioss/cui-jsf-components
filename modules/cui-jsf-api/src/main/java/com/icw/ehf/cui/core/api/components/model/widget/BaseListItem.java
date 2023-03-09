package com.icw.ehf.cui.core.api.components.model.widget;

import java.time.LocalDate;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Data;

/**
 * Default implementation for {@link ListItem}
 */
@Data
public class BaseListItem implements ListItem {

    private static final long serialVersionUID = -3847701857160268822L;

    private IDisplayNameProvider<?> title;

    private String titleType;

    private String text;

    private String textType;

    private String iconClass;

    private String previewImage;

    private String previewImageLibrary;

    private String clickLink;

    private String clickLinkTarget;

    private LocalDate timestamp;

    private String timestampValue;
}
