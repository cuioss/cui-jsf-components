package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.selectize.Selectize.OPTION_VALUE_DEFAULT_WRAPPER;
import static de.cuioss.jsf.bootstrap.selectize.Selectize.OPTION_VALUE_LABEL_KEY;
import static de.cuioss.jsf.bootstrap.selectize.Selectize.OPTION_VALUE_VALUE_KEY;
import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSortedSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.components.base.BaseCuiHtmlInputComponent;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassResolver;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.DisabledComponentProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.tools.codec.Hex;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an TagInput similar to a JIRA Tag. See {@link TagInputRenderer} for
 * rendering details
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>sourceSet: The initially available tags. The Set must contain objects of
 * the type {@link ConceptKeyType}</li>
 * <li>clientCreated: The tags that are created by the client. Internal use only
 * </li>
 * <li>letUserCreateTags: Indicates whether the user is allowed to dynamically
 * create own Tags on the fly. User Created tags, e.g. with the label 'MyTag'
 * will be translated into a {@link ConceptKeyType} with the
 * {@link ConceptKeyType#getResolved(java.util.Locale)} resulting in 'MyTag'
 * {@link ConceptKeyType#getIdentifier()} resulting in '_client_created_MyTag'. The
 * suffix '_client_created_' is useful to differentiate between the original
 * tags and the ones create on the fly</li>
 * <li>maxItems: Defines the maximum number of items to be selected, defaults to
 * 10.</li>
 * <li>delimiter: Defines the delimiter to separate the identifier in the input value, default to
 * {@link JavaScriptOptions#OPTION_VALUE_DELIMITER}</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:tagInput value="someValueBinding" sourceSet="set of concept keys" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@FacesComponent(BootstrapFamily.TAG_INPUT_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.selectize.js", target = "head")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TagInputComponent extends BaseCuiHtmlInputComponent
        implements StyleClassResolver {

    private static final CuiLogger log = new CuiLogger(TagInputComponent.class);

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

    /** Style class for the Selectize wrapper to indicate that it is allowed to create new tags */
    static final String CSS_CLASS_CAN_CREATE = "selectize-cancreate";

    /**
     * Style class for the Selectize wrapper to indicate that it is not allowed to create new tags
     */
    static final String CSS_CLASS_CANNOT_CREATE = "selectize-cannotcreate";

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    private final CuiState state;

    /**
     *
     */
    public TagInputComponent() {
        super();
        disabledProvider = new DisabledComponentProvider(this);
        setRendererType(BootstrapFamily.TAG_INPUT_COMPONENT_RENDERER);
        super.setConverter(new ConceptKeyStringConverter());
        state = new CuiState(getStateHelper());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * @return the sourceList
     */
    public Set<ConceptKeyType> getSourceSet() {
        return state.get(SOURCE_SET_KEY, Collections.<ConceptKeyType> emptySet());
    }

    /**
     * @param sourceList the sourceList to set
     */
    public void setSourceSet(final Set<ConceptKeyType> sourceList) {
        state.put(SOURCE_SET_KEY, sourceList);
    }

    /**
     * @return the clientCreated
     */
    public Set<ConceptKeyType> getClientCreated() {
        return state.get(CLIENT_CREATED_KEY, Collections.<ConceptKeyType> emptySet());
    }

    /**
     * @param displayRemoveButton the list of client created tags to set
     */
    public void setDisplayRemoveButton(final boolean displayRemoveButton) {
        state.put(REMOVE_BUTTON_KEY, displayRemoveButton);
    }

    /**
     * @return true, if the remove button should be displayed on each tag
     */
    public boolean isDisplayRemoveButton() {
        return state.getBoolean(REMOVE_BUTTON_KEY, true);
    }

    /**
     * @param clientCreated the list of client created tags to set
     */
    public void setClientCreated(final Set<ConceptKeyType> clientCreated) {
        state.put(CLIENT_CREATED_KEY, clientCreated);
    }

    /**
     * @return the letUserCreateTags
     */
    public boolean isLetUserCreateTags() {
        return state.getBoolean(USER_CREATE_TAGS_KEY, true);
    }

    /**
     * @param letUserCreateTags the letUserCreateTags to set
     */
    public void setLetUserCreateTags(final boolean letUserCreateTags) {
        state.put(USER_CREATE_TAGS_KEY, letUserCreateTags);
    }

    /**
     * @return the maxItems
     */
    public int getMaxItems() {
        return state.getInt(MAX_ITEMS_KEY, DEFAULT_MAX_ITEMS);
    }

    /**
     * @param maxItems the maxItems to set
     */
    public void setMaxItems(final int maxItems) {
        state.put(MAX_ITEMS_KEY, maxItems);
    }

    /**
     * @return the delimiter
     */
    public String getDelimiter() {
        return state.get(DELIMITER_KEY, JavaScriptOptions.OPTION_VALUE_DELIMITER);
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(final String delimiter) {
        state.put(DELIMITER_KEY, delimiter);
    }

    @Override
    public void setConverter(final Converter converter) {
        throw new UnsupportedOperationException("Component converter already set.");
    }

    /**
     * @param converterId
     */
    public void setItemConverterId(final String converterId) {
        state.put(ITEM_CONVERTER_ID_KEY, converterId);
    }

    /**
     * @return converterId
     */
    public String getItemConverterId() {
        return state.get(ITEM_CONVERTER_ID_KEY);
    }

    /**
     * @return the resolved itemConverter
     */
    public Optional<Converter> getItemConverter() {
        final var converterId = getItemConverterId();
        if (!MoreStrings.isEmpty(converterId)) {
            log.debug("Creating item converter with id: {}", converterId);
            return Optional.ofNullable(facesContext().getApplication().createConverter(converterId));
        }
        return Optional.empty();
    }

    /**
     * @return the {@link Set} of ConceptKeyType of {@link TagHelper#getValueAsSet(Object...)}
     *         marked as undefined and
     *         not part of {@link #getSourceSet()}.
     */
    Set<ConceptKeyType> getUndefinedValues() {
        if (null == getValue()) {
            return Collections.emptySet();
        }
        return getValue().stream()
                .filter(AugmentationKeyConstans::isUndefinedValue)
                .collect(Collectors.toSet());
    }

    /**
     * @return {@link java.util.SortedSet} of {@link ConceptKeyType}
     */
    @Override
    public SortedSet<ConceptKeyType> getValue() {
        return null != super.getValue() ? mutableSortedSet((Iterable) super.getValue()) : null;
    }

    /**
     * @param value {@link java.util.Set} of {@link ConceptKeyType}
     * @throws IllegalArgumentException if value is not a {@link java.util.Set} of
     *             {@link ConceptKeyType}
     */
    @Override
    public void setValue(final Object value) {
        checkValue(value);
        super.setValue(value);
    }

    /**
     * Ensure that <code>value</code> is a {@link java.util.Set} of {@link ConceptKeyType}.
     *
     * @param value to be checked
     * @throws IllegalArgumentException if value is not a {@link java.util.Set} of
     *             {@link ConceptKeyType}
     */
    private void checkValue(final Object value) {
        if (null == value) {
            return;
        }

        final var errorMessage =
            "value must be a java.util.Set of ConceptKeyType, but is: " + value.getClass().getName();

        checkArgument(value instanceof Set, errorMessage);

        final Set<?> collection = (Set<?>) value;
        for (Object o : collection) {
            checkArgument(o instanceof ConceptKeyType, errorMessage);
        }
    }

    @Override
    protected Object getConvertedValue(final FacesContext context, final Object submittedValue)
        throws ConverterException {
        return new ConceptKeyStringConverter().getAsObject(context, this, (String) submittedValue);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return getStyleClassBuilder()
                .append(isLetUserCreateTags() ? CSS_CLASS_CAN_CREATE : CSS_CLASS_CANNOT_CREATE);
    }

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

    private String getOptions() {
        final SortedSet<ConceptKeyType> source = mutableSortedSet(getSourceSet());
        source.addAll(getClientCreated());
        source.addAll(getUndefinedValues());
        final var options = buildOptionElements(source);
        return String.format(JavaScriptOptions.SQUARE_BRACKETS_WRAPPER, options);
    }

    @Override
    public Map<String, Object> getPassThroughAttributes(final boolean create) {
        return getPassThroughAttributes();
    }

    private String buildOptionElements(final SortedSet<ConceptKeyType> codeTypes) {
        final List<String> optionElements = new ArrayList<>();
        final var locale = localeProducerAccessor.getValue().getLocale();
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

    private static String buildOptionElement(final String label, final String identifier) {
        return new StringBuilder()
                .append("{\"")
                .append(OPTION_VALUE_LABEL_KEY)
                .append("\":\"")
                .append(label)
                .append("\",\"")
                .append(OPTION_VALUE_VALUE_KEY)
                .append("\":\"")
                .append(identifier)
                .append("\"}")
                .toString();
    }
}
