/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.icon.support;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
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
     * The placeholder base class for mime-type-icon: "cui-mime-type-placeholder"
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
     * The default decorator is the the no-decorator: "cui-mime-type-no-decorator".
     */
    CUI_STACKED_ICON_NO_DECORATOR("cui-mime-type-no-decorator");

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }
}
