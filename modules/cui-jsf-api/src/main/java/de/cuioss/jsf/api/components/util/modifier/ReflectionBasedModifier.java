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
package de.cuioss.jsf.api.components.util.modifier;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;

import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.ForIdentifierProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.tools.property.PropertyReadWrite;
import de.cuioss.tools.property.PropertyUtil;
import lombok.Getter;
import lombok.ToString;

/**
 * Implementation of {@link ComponentModifier} that uses reflection in order to
 * determine the corresponding methods.
 *
 * @author Oliver Wolff
 */
@ToString(of = "component")
public class ReflectionBasedModifier implements ComponentModifier {

    private static final String LABEL = "label";

    private static final String REQUIRED = "required";

    private static final String VALID = "valid";

    private static final String TITLE = "title";

    private static final String ROLE = "role";

    private static final String DISABLED = "disabled";

    private static final String RENDERED = "rendered";

    @Getter
    private final UIComponent component;

    private final Map<String, PropertyReadWrite> description;

    @Getter
    private final boolean editableValueHolder;

    /**
     * @param component
     */
    public ReflectionBasedModifier(final UIComponent component) {
        this.component = requireNonNull(component);
        editableValueHolder = component instanceof EditableValueHolder;
        description = new HashMap<>();
    }

    private PropertyReadWrite supportsAttribute(final String attributeName) {
        if (!description.containsKey(attributeName)) {
            description.put(attributeName, PropertyReadWrite.resolveForBean(getComponent().getClass(), attributeName));
        }
        return description.get(attributeName);
    }

    private Object readAttribute(final String attributeName) {
        if (!supportsAttribute(attributeName).isReadable()) {
            throw new UnsupportedOperationException("Attribute not supported " + attributeName);
        }
        return PropertyUtil.readProperty(getComponent(), attributeName);
    }

    private boolean readBooleanAttribute(final String attributeName) {
        var read = readAttribute(attributeName);
        if (null == read) {
            return false;
        }
        return (Boolean) read;
    }

    private void writeAttribute(final String attributeName, final Object value) {
        if (!supportsAttribute(attributeName).isWriteable()) {
            throw new UnsupportedOperationException("Attribute not supported " + attributeName);
        }
        PropertyUtil.writeProperty(getComponent(), attributeName, value);
    }

    @Override
    public boolean isSupportsStyleClass() {
        return supportsAttribute(ComponentStyleClassProviderImpl.KEY).isReadable();
    }

    @Override
    public void setStyleClass(final String styleClass) {
        writeAttribute(ComponentStyleClassProviderImpl.KEY, styleClass);
    }

    @Override
    public String getStyleClass() {
        return (String) readAttribute(ComponentStyleClassProviderImpl.KEY);
    }

    @Override
    public boolean isSupportsStyle() {
        return supportsAttribute(StyleAttributeProviderImpl.KEY).isReadable();
    }

    @Override
    public void setStyle(final String style) {
        writeAttribute(StyleAttributeProviderImpl.KEY, style);

    }

    @Override
    public String getStyle() {
        return (String) readAttribute(StyleAttributeProviderImpl.KEY);
    }

    @Override
    public boolean isSupportsDisabled() {
        return supportsAttribute(DISABLED).isReadable();
    }

    @Override
    public boolean isDisabled() {
        return readBooleanAttribute(DISABLED);
    }

    @Override
    public void setDisabled(final boolean disabled) {
        writeAttribute(DISABLED, disabled);

    }

    @Override
    public boolean isSupportsRole() {
        return supportsAttribute(ROLE).isReadable();
    }

    @Override
    public void setRole(final String role) {
        writeAttribute(ROLE, role);

    }

    @Override
    public String getRole() {
        return (String) readAttribute(ROLE);
    }

    @Override
    public boolean isSupportsTitle() {
        return supportsAttribute(TITLE).isReadable();
    }

    @Override
    public void setTitle(final String title) {
        writeAttribute(TITLE, title);
    }

    @Override
    public String getTitle() {
        return (String) readAttribute(TITLE);
    }

    @Override
    public boolean isValid() {
        return readBooleanAttribute(VALID);
    }

    @Override
    public boolean isRequired() {
        return readBooleanAttribute(REQUIRED);
    }

    @Override
    public boolean isSupportsLabel() {
        return supportsAttribute(LABEL).isReadable();
    }

    @Override
    public void setLabel(final String label) {
        writeAttribute(LABEL, label);

    }

    @Override
    public String getLabel() {
        return (String) readAttribute(LABEL);
    }

    @Override
    public boolean wrapsComponentClass(final Class<? extends UIComponent> klazz) {
        return getComponent().getClass().equals(klazz);
    }

    @Override
    public boolean isCompositeInput() {
        return supportsAttribute(ForIdentifierProvider.KEY).isReadable();
    }

    @Override
    public String getForIndentifiers() {
        return (String) readAttribute(ForIdentifierProvider.KEY);
    }

    @Override
    public void resetValue() {
        if (!editableValueHolder) {
            throw new UnsupportedOperationException("ResetValue only supported on EditableValueHolder");
        }
        ((EditableValueHolder) getComponent()).resetValue();

    }

    @Override
    public boolean isRendered() {
        return readBooleanAttribute(RENDERED);
    }

}
