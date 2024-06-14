package de.cuioss.jsf.dev.metadata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static de.cuioss.jsf.dev.metadata.TagLib.JSF_2_FACELET_TAGLIB_NAMESPACE;
import static de.cuioss.jsf.dev.metadata.TagLib.JSF_4_0_FACELET_TAGLIB_NAMESPACE;

/**
 * Isolates concrete {@link TagLib}s to be loaded.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum LibraryTagLib {

    CUI_CORE("/META-INF/cui-core.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    CUI_BOOTSTRAP("/META-INF/cui-bootstrap.taglib.xml", JSF_4_0_FACELET_TAGLIB_NAMESPACE),
    PRIME_FACES("/META-INF/primefaces-p.taglib.xml", JSF_2_FACELET_TAGLIB_NAMESPACE),
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
