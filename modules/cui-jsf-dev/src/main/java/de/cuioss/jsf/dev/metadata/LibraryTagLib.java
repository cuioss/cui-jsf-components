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
package de.cuioss.jsf.dev.metadata;

import static de.cuioss.jsf.dev.metadata.TagLib.JSF_2_2_FACELET_TAGLIB_NAMESPACE;
import static de.cuioss.jsf.dev.metadata.TagLib.JSF_4_0_FACELET_TAGLIB_NAMESPACE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Isolates concrete {@link TagLib}s to be loaded.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum LibraryTagLib {

    CUI_CORE("/META-INF/cui-core.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    CUI_BOOTSTRAP("/META-INF/cui-bootstrap.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    PRIME_FACES("/META-INF/primefaces.taglib.xml", JSF_2_2_FACELET_TAGLIB_NAMESPACE),
    OMNI_FACES("/META-INF/omnifaces-ui.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    FACES_CORE("/META-INF/resources/mojarra-4.0.7-tlds/faces.core_taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    FACES_HTML("/META-INF/resources/mojarra-4.0.7-tlds/faces.html_taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE);

    @Getter
    private final String path;

    @Getter
    private final String namespace;

    public TagLib load() {
        return new TagLib(this.path, this.namespace);
    }

}
