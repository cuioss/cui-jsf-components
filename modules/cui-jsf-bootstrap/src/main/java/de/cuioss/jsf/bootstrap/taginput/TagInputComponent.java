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
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.selectize.Selectize.*;
import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSortedSet;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.components.base.BaseCuiHtmlInputComponent;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassResolver;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.DisabledComponentProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.tools.codec.Hex;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstants;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * Component that renders an interactive tag input field with autocomplete capabilities.
 * Users can select from existing tags or create new ones on-the-fly.
 * 
 * <h2>Features</h2>
 * <ul>
 * <li>Select from predefined tags</li>
 * <li>Create new tags on-the-fly (optional)</li>
 * <li>Multi-selection with configurable limits</li>
 * <li>Typeahead/autocomplete functionality</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ComponentStyleClassProvider} - CSS styling</li>
 * <li>{@link DisabledComponentProvider} - Disabled state</li>
 * <li><b>sourceSet</b>: Available {@link ConceptKeyType} objects for selection</li>
 * <li><b>clientCreated</b>: Tags created by user input (internal use)</li>
 * <li><b>letUserCreateTags</b>: Allow creating new tags (default: true)</li>
 * <li><b>maxItems</b>: Maximum selectable tags (default: 10)</li>
 * <li><b>delimiter</b>: Identifier separator (default: {@link JavaScriptOptions#OPTION_VALUE_DELIMITER})</li>
 * <li><b>displayRemoveButton</b>: Show remove buttons for tags</li>
 * <li><b>itemConverterId</b>: Optional converter ID for custom conversion</li>
 * </ul>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;boot:tagInput value="#{bean.selectedTags}" sourceSet="#{bean.availableTags}" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.TAG_INPUT_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.selectize.js", target = "head")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TagInputComponent extends BaseCuiHtmlInputComponent implements StyleClassResolver {

    private static final CuiLogger LOGGER = new CuiLogger(TagInputComponent.class);

    private static final Integer DEFAULT_MAX_ITEMS = 10;

    /** Partial elements. */
    @Delegate
    private final DisabledComponentProvider disabledProvider;

    /** Component specific Keys. */
    private static final String SOURCE_SET_KEY = "sourceSet";
    private static final String CLIENT_CREATED_KEY = "clientCreated";
    private static final String USER_CREATE_TAGS_KEY = "letUserCreateTags";
    private static final String MAX_ITEMS_KEY = "maxItems";
    private static final String DELIMITER_KEY = "delimiter";
    private static final String ITEM_CONVERTER_ID_KEY = "itemConverterId";
    private static final String REMOVE_BUTTON_KEY = "displayRemoveButton";

    /**
     * Style class for the Selectize wrapper to indicate that it is allowed to
     * create new tags
     */
    static final String CSS_CLASS_CAN_CREATE = "selectize-cancreate";

    /**
     * Style class for the Selectize wrapper to indicate that it is not allowed to
     * create new tags
     */
    static final String CSS_CLASS_CANNOT_CREATE = "selectize-cannotcreate";

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    private final CuiState state;

    /**
     * Constructs a new TagInputComponent with default settings.
     * Initializes the component with the appropriate renderer type and
     * a default ConceptKeyStringConverter.
     */
    public TagInputComponent() {
        disabledProvider = new DisabledComponentProvider(this);
        setRendererType(BootstrapFamily.TAG_INPUT_COMPONENT_RENDERER);
        super.setConverter(new ConceptKeyStringConverter());
        state = new CuiState(getStateHelper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Returns the source set of available tags for selection.
     * 
     * @return the Set of ConceptKeyType objects representing available tags,
     *         or an empty set if none are configured
     */
    public Set<ConceptKeyType> getSourceSet() {
        return state.get(SOURCE_SET_KEY, Collections.<ConceptKeyType>emptySet());
    }

    /**
     * Sets the source set of available tags for selection.
     * 
     * @param sourceList the Set of ConceptKeyType objects representing available tags
     */
    public void setSourceSet(final Set<ConceptKeyType> sourceList) {
        state.put(SOURCE_SET_KEY, sourceList);
    }

    /**
     * Returns the set of tags created by the client.
     * 
     * @return the Set of ConceptKeyType objects created by the client,
     *         or an empty set if none exist
     */
    public Set<ConceptKeyType> getClientCreated() {
        return state.get(CLIENT_CREATED_KEY, Collections.<ConceptKeyType>emptySet());
    }

    /**
     * Sets whether remove buttons should be displayed on selected tags.
     * 
     * @param displayRemoveButton true to display remove buttons, false otherwise
     */
    public void setDisplayRemoveButton(final boolean displayRemoveButton) {
        state.put(REMOVE_BUTTON_KEY, displayRemoveButton);
    }

    /**
     * Determines whether remove buttons should be displayed on selected tags.
     * 
     * @return true if remove buttons should be displayed, false otherwise (defaults to true)
     */
    public boolean isDisplayRemoveButton() {
        return state.getBoolean(REMOVE_BUTTON_KEY, true);
    }

    /**
     * Sets the client-created tags.
     * 
     * @param clientCreated the Set of ConceptKeyType objects created by the client
     */
    public void setClientCreated(final Set<ConceptKeyType> clientCreated) {
        state.put(CLIENT_CREATED_KEY, clientCreated);
    }

    /**
     * Determines whether users are allowed to create new tags on-the-fly.
     * 
     * @return true if users can create new tags, false otherwise (defaults to true)
     */
    public boolean isLetUserCreateTags() {
        return state.getBoolean(USER_CREATE_TAGS_KEY, true);
    }

    /**
     * Sets whether users are allowed to create new tags on-the-fly.
     * 
     * @param letUserCreateTags true to allow users to create new tags, false otherwise
     */
    public void setLetUserCreateTags(final boolean letUserCreateTags) {
        state.put(USER_CREATE_TAGS_KEY, letUserCreateTags);
    }

    /**
     * Returns the maximum number of tags that can be selected.
     * 
     * @return the maximum number of selectable items (defaults to 10)
     */
    public int getMaxItems() {
        return state.getInt(MAX_ITEMS_KEY, DEFAULT_MAX_ITEMS);
    }

    /**
     * Sets the maximum number of tags that can be selected.
     * 
     * @param maxItems the maximum number of selectable items
     */
    public void setMaxItems(final int maxItems) {
        state.put(MAX_ITEMS_KEY, maxItems);
    }

    /**
     * Returns the delimiter used to separate identifiers in the input value.
     * 
     * @return the delimiter string (defaults to {@link JavaScriptOptions#OPTION_VALUE_DELIMITER})
     */
    public String getDelimiter() {
        return state.get(DELIMITER_KEY, JavaScriptOptions.OPTION_VALUE_DELIMITER);
    }

    /**
     * Sets the delimiter used to separate identifiers in the input value.
     * 
     * @param delimiter the delimiter string
     */
    public void setDelimiter(final String delimiter) {
        state.put(DELIMITER_KEY, delimiter);
    }

    /**
     * This component uses a fixed converter for ConceptKeyType handling.
     * Attempting to change the converter will result in an exception.
     * 
     * @param converter the converter to set (not supported)
     * @throws UnsupportedOperationException always thrown since the converter cannot be changed
     */
    @Override
    public void setConverter(final Converter converter) {
        throw new UnsupportedOperationException("Component converter already set.");
    }

    /**
     * Sets the ID of a converter to use for item conversion.
     * 
     * @param converterId the ID of the converter to use for items
     */
    public void setItemConverterId(final String converterId) {
        state.put(ITEM_CONVERTER_ID_KEY, converterId);
    }

    /**
     * Returns the ID of the converter used for item conversion.
     * 
     * @return the converter ID, or null if none is set
     */
    public String getItemConverterId() {
        return state.get(ITEM_CONVERTER_ID_KEY);
    }

    /**
     * Returns the resolved item converter instance if one has been specified.
     * 
     * @return an Optional containing the converter if specified and available, or empty otherwise
     */
    public Optional<Converter> getItemConverter() {
        final var converterId = getItemConverterId();
        if (!MoreStrings.isEmpty(converterId)) {
            LOGGER.debug("Creating item converter with id: %s", converterId);
            return Optional.ofNullable(facesContext().getApplication().createConverter(converterId));
        }
        return Optional.empty();
    }

    /**
     * Returns the set of ConceptKeyType values that are undefined and not part of the sourceSet.
     * These represent values that exist in the component's value but are not defined
     * in the available tags list.
     * 
     * @return a Set of ConceptKeyType objects that are undefined, or an empty set if none
     */
    Set<ConceptKeyType> getUndefinedValues() {
        if (null == getValue()) {
            return Collections.emptySet();
        }
        return getValue().stream().filter(AugmentationKeyConstants::isUndefinedValue).collect(Collectors.toSet());
    }

    /**
     * Returns the component's value as a SortedSet of ConceptKeyType objects.
     * 
     * @return a SortedSet of selected ConceptKeyType objects, or null if no value is set
     */
    @Override
    public SortedSet<ConceptKeyType> getValue() {
        return null != super.getValue() ? mutableSortedSet((Iterable) super.getValue()) : null;
    }

    /**
     * Sets the component's value. The value must be a Set of ConceptKeyType objects.
     * 
     * @param value the Set of ConceptKeyType objects to set as the component's value
     * @throws IllegalArgumentException if the value is not a Set of ConceptKeyType objects
     */
    @Override
    public void setValue(final Object value) {
        checkValue(value);
        super.setValue(value);
    }

    /**
     * Validates that the provided value is a Set of ConceptKeyType objects.
     *
     * @param value the object to validate
     * @throws IllegalArgumentException if value is not null and not a Set of ConceptKeyType objects
     */
    private void checkValue(final Object value) {
        if (null == value) {
            return;
        }

        final var errorMessage = "value must be a java.util.Set of ConceptKeyType, but is: "
                + value.getClass().getName();

        checkArgument(value instanceof Set, errorMessage);

        final Set<?> collection = (Set<?>) value;
        for (Object o : collection) {
            checkArgument(o instanceof ConceptKeyType, errorMessage);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Converts the submitted string value to a Set of ConceptKeyType objects
     * using the ConceptKeyStringConverter.
     */
    @Override
    protected Object getConvertedValue(final FacesContext context, final Object submittedValue)
            throws ConverterException {
        return new ConceptKeyStringConverter().getAsObject(context, this, (String) submittedValue);
    }

    /**
     * Resolves the CSS classes for this component based on configuration.
     * Adds special classes to indicate whether tag creation is allowed.
     *
     * @return a StyleClassBuilder with appropriate CSS classes
     */
    @Override
    public StyleClassBuilder resolveStyleClass() {
        return getStyleClassBuilder().append(isLetUserCreateTags() ? CSS_CLASS_CAN_CREATE : CSS_CLASS_CANNOT_CREATE);
    }

    /**
     * {@inheritDoc}
     * 
     * Adds selectize-specific passthrough attributes needed for client-side initialization.
     */
    @Override
    public Map<String, Object> getPassThroughAttributes() {
        final var attributes = super.getPassThroughAttributes(true);
        attributes.put(PassthroughAttributes.SELECTIZE, !isDisabled()); // trigger for JS
        attributes.put(PassthroughAttributes.CAN_CREATE, isLetUserCreateTags());
        attributes.put(PassthroughAttributes.MAX_ITEMS, getMaxItems());
        attributes.put(PassthroughAttributes.DELIMITER, getDelimiter());
        attributes.put(PassthroughAttributes.WRAPPER_CLASS,
                resolveStyleClass().append(OPTION_VALUE_DEFAULT_WRAPPER).getStyleClass());
        attributes.put(PassthroughAttributes.REMOVE_BUTTON, isDisplayRemoveButton());
        attributes.put(PassthroughAttributes.PERSIST, Boolean.FALSE);
        attributes.put(PassthroughAttributes.OPTIONS, getOptions());
        return attributes;
    }

    /**
     * Builds a JSON string representation of the available tag options.
     * 
     * @return a JSON string containing all available tag options
     */
    private String getOptions() {
        final SortedSet<ConceptKeyType> source = mutableSortedSet(getSourceSet());
        source.addAll(getClientCreated());
        source.addAll(getUndefinedValues());
        final var options = buildOptionElements(source);
        return JavaScriptOptions.SQUARE_BRACKETS_WRAPPER.formatted(options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getPassThroughAttributes(final boolean create) {
        return getPassThroughAttributes();
    }

    /**
     * Builds the JSON representation of option elements for the given ConceptKeyType set.
     * 
     * @param codeTypes the set of ConceptKeyType objects to convert to option elements
     * @return a String containing the JSON representation of the option elements
     */
    private String buildOptionElements(final SortedSet<ConceptKeyType> codeTypes) {
        final List<String> optionElements = new ArrayList<>();
        final var locale = localeAccessor.getValue();
        for (final ConceptKeyType codeType : codeTypes) {
            final String label;
            if (getItemConverter().isPresent()) {
                // getItemConverter().get().getAsString(facesContext(), this, codeType);
                label = codeType.getResolved(locale);
            } else {
                label = CuiSanitizer.PLAIN_TEXT.apply(codeType.getResolved(locale));
            }
            final var identifier = Hex.encodeHexString(codeType.getIdentifier().getBytes());
            optionElements.add(buildOptionElement(label, identifier));
        }
        return Joiner.on(JavaScriptOptions.OPTION_VALUE_DELIMITER).join(optionElements);
    }

    /**
     * Builds a single option element JSON object with the given label and identifier.
     * 
     * @param label the display label for the option
     * @param identifier the value identifier for the option
     * @return a JSON string representing the option
     */
    private static String buildOptionElement(final String label, final String identifier) {
        return "{\"" + OPTION_VALUE_LABEL_KEY + "\":\"" + label +
                "\",\"" + OPTION_VALUE_VALUE_KEY + "\":\"" + identifier + "\"}";
    }
}
