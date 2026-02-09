/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.tag.support;

import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;

import java.io.Serial;

/**
 * A concept category for representing tags that are not found or missing 
 * from the originally defined set. Used to handle cases where a tag reference 
 * exists but the actual tag definition is not available.
 *
 * @author Sven Haag
 * @since 1.0
 */
public class MissingTagConceptKeyCategory extends BaseConceptCategory {

    @Serial
    private static final long serialVersionUID = -3651465449271115447L;
}
