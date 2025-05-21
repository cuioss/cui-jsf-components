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
 * <p>
 * Provides predefined constants for commonly used JSF tag libraries. Each enum constant
 * represents a specific tag library with its corresponding path and namespace.
 * </p>
 * 
 * <p>
 * This enum simplifies the process of loading tag libraries by encapsulating the path
 * and namespace configuration required by {@link TagLib}. The {@link #load()} method
 * provides a convenient way to instantiate a {@link TagLib} for the specified library.
 * </p>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Load the CUI Bootstrap taglib
 * TagLib bootstrapTagLib = LibraryTagLib.CUI_BOOTSTRAP.load();
 * 
 * // Access components from the taglib
 * List&lt;UIComponentMetadata&gt; components = bootstrapTagLib.getComponentMetadata().getCollected();
 * </pre>
 * 
 * <p>
 * As an enum, this class is inherently thread-safe.
 * </p>
 * 
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum LibraryTagLib {

    /**
     * <p>
     * The CUI Core tag library, containing base components and utilities.
     * </p>
     * <p>
     * Path: "/META-INF/cui-core.taglib.xml"
     * </p>
     * <p>
     * Uses Jakarta EE 4.0 namespace.
     * </p>
     */
    CUI_CORE("/META-INF/cui-core.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),

    /**
     * <p>
     * The CUI Bootstrap tag library, containing Bootstrap-based UI components.
     * </p>
     * <p>
     * Path: "/META-INF/cui-bootstrap.taglib.xml"
     * </p>
     * <p>
     * Uses Jakarta EE 4.0 namespace.
     * </p>
     */
    CUI_BOOTSTRAP("/META-INF/cui-bootstrap.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),

    /**
     * <p>
     * The PrimeFaces tag library, containing the third-party PrimeFaces components.
     * </p>
     * <p>
     * Path: "/META-INF/primefaces.taglib.xml"
     * </p>
     * <p>
     * Uses JavaEE 8 namespace (JSF 2.2).
     * </p>
     */
    PRIME_FACES("/META-INF/primefaces.taglib.xml", JSF_2_2_FACELET_TAGLIB_NAMESPACE),

    /**
     * <p>
     * The OmniFaces tag library, containing the third-party OmniFaces UI components.
     * </p>
     * <p>
     * Path: "/META-INF/omnifaces-ui.taglib.xml"
     * </p>
     * <p>
     * Uses Jakarta EE 4.0 namespace.
     * </p>
     */
    OMNI_FACES("/META-INF/omnifaces-ui.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),

    /**
     * <p>
     * The Jakarta Faces core tag library, containing the standard faces core components.
     * </p>
     * <p>
     * Path: "/META-INF/resources/mojarra-4.0.7-tlds/faces.core_taglib.xml"
     * </p>
     * <p>
     * Uses Jakarta EE 4.0 namespace.
     * </p>
     */
    FACES_CORE("/META-INF/resources/mojarra-4.0.7-tlds/faces.core_taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),

    /**
     * <p>
     * The Jakarta Faces HTML tag library, containing the standard HTML components.
     * </p>
     * <p>
     * Path: "/META-INF/resources/mojarra-4.0.7-tlds/faces.html_taglib.xml"
     * </p>
     * <p>
     * Uses Jakarta EE 4.0 namespace.
     * </p>
     */
    FACES_HTML("/META-INF/resources/mojarra-4.0.7-tlds/faces.html_taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE);

    /**
     * The path to the taglib XML file. This is used to locate the taglib definition in the classpath.
     */
    @Getter
    private final String path;

    /**
     * The XML namespace for the taglib. This defines which JSF specification version
     * the taglib conforms to (JSF 2.0, 2.2, or Jakarta 4.0).
     */
    @Getter
    private final String namespace;

    /**
     * <p>
     * Creates and returns a new {@link TagLib} instance for this library.
     * </p>
     * 
     * <p>
     * This method simplifies the creation of TagLib instances by automatically providing
     * the correct path and namespace for the specified library.
     * </p>
     * 
     * <p>
     * Example usage:
     * </p>
     * <pre>
     * // Load the CUI Bootstrap taglib
     * TagLib bootstrapTagLib = LibraryTagLib.CUI_BOOTSTRAP.load();
     * </pre>
     *
     * @return A new {@link TagLib} instance for this library.
     * @throws IllegalStateException If the taglib file cannot be found at the specified path.
     * @throws IllegalArgumentException If there is an error parsing the taglib XML file.
     * @see TagLib#TagLib(String, String)
     */
    public TagLib load() {
        return new TagLib(this.path, this.namespace);
    }

}
