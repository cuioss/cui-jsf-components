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

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CSS classes for mime-type icons.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssMimeTypeIcon implements StyleClassProvider {

    /** Base class for mime-type icons */
    CUI_MIME_TYPE("cui-mime-type"),

    /** Folder mime-type icon */
    CUI_MIME_TYPE_FOLDER("cui-mime-type-folder"),

    /** Placeholder mime-type icon */
    CUI_MIME_TYPE_PLACEHOLDER("cui-mime-type-placeholder"),

    /** Default mime-type icon */
    CUI_MIME_TYPE_DEFAULT("cui-mime-type-default"),

    /** Base class for stacked icons */
    CUI_STACKED_ICON("cui-icon-stack"),

    /** 1x variant of stacked icon */
    CUI_STACKED_ICON_1("cui-icon-stack-1x"),

    /** Base layer for stacked mime-type icons */
    CUI_STACKED_BASE_STRING("cui-mime-type cui-icon-stack-1x"),

    /** No-decorator variant */
    CUI_STACKED_ICON_NO_DECORATOR("cui-mime-type-no-decorator");

    @Getter
    private final String styleClass;
}
