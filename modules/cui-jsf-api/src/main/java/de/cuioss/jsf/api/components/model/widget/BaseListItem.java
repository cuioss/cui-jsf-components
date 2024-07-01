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
package de.cuioss.jsf.api.components.model.widget;

import java.io.Serial;
import java.time.LocalDate;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Data;

/**
 * Default implementation for {@link ListItem}
 */
@Data
public class BaseListItem implements ListItem {

    @Serial
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
