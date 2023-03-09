package com.icw.ehf.cui.components.bootstrap.icon.support;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Provides the css-classes for mimetype-icon
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssMimeTypeIcon implements StyleClassProvider {

    /** Base class for mime-type icons: "cui-mime-type" */
    CUI_MIME_TYPE("cui-mime-type"),
    /** The folder base class for mime-type-icon: "cui-mime-type-folder" */
    CUI_MIME_TYPE_FOLDER("cui-mime-type-folder"),
    /**
     * The placeholder base class for mime-type-icon:
     * "cui-mime-type-placeholder"
     */
    CUI_MIME_TYPE_PLACEHOLDER("cui-mime-type-placeholder"),
    /** The default mime-type icon class: "cui-mime-type-default" */
    CUI_MIME_TYPE_DEFAULT("cui-mime-type-default"),
    /** Base class for Stacked Icon: "cui-icon-stack" */
    CUI_STACKED_ICON("cui-icon-stack"),
    /** 1x variant of CUI_STACKED_ICON: "cui-icon-stack-1x" */
    CUI_STACKED_ICON_1("cui-icon-stack-1x"),
    /** Starting point for the layers: "cui-mime-type cui-icon-stack-1x" */
    CUI_STACKED_BASE_STRING("cui-mime-type cui-icon-stack-1x"),
    /**
     * The default decorator is the the no-decorator:
     * "cui-mime-type-no-decorator".
     */
    CUI_STACKED_ICON_NO_DECORATOR("cui-mime-type-no-decorator");

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }
}
