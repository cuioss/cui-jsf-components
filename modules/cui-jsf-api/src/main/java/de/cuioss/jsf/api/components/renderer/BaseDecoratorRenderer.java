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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.component.behavior.ClientBehavior;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.render.Renderer;

import java.io.IOException;

/**
 * Base class for renderer that use {@link DecoratingResponseWriter} in order to
 * simplify usage. The actual implementor must implement at least one of
 * doEncodeBegin or doEncodeEnd
 *
 * @param <T> identifying the concrete component to be rendered, at least
 *            {@link UIComponent}
 * @author Oliver Wolff
 */
public class BaseDecoratorRenderer<T extends UIComponent> extends Renderer {

    private static final String JAVAX_FACES_SOURCE = "jakarta.faces.source";
    private static final String JAVAX_FACES_BEHAVIOR_EVENT = "jakarta.faces.behavior.event";
    private static final CuiLogger LOGGER = new CuiLogger(BaseDecoratorRenderer.class);
    private final boolean renderChildren;

    /**
     * Constructor.
     *
     * @param renderChildren indicating whether the concrete renderer is rendering
     *                       its children. Usually this is the cases if the
     *                       {@link Renderer} renders a leaf.
     */
    public BaseDecoratorRenderer(final boolean renderChildren) {
        this.renderChildren = renderChildren;
    }

    /**
     * @param behaviorSourceId  value could be missing
     * @param componentClientId value which should be checked
     * @return true, if the ID given in <code>behaviorSourceId</code> is supposed to
     * come from the component with <code>componentClientId</code>
     */
    protected static boolean isFromBehaviorSource(final String behaviorSourceId, final String componentClientId) {
        return !MoreStrings.isEmpty(behaviorSourceId) && behaviorSourceId.equals(componentClientId);
    }

    /**
     * Simple helper method that strips on prefixes from possible dom-event-names
     *
     * @param domEventName maybe null or empty
     * @return in case of the given event starts with "on", e.g. "onclick" it
     * returns the token after on "on" in the example, this would be "click"
     */
    public static String fixDomeEventName(final String domEventName) {
        if (isEmpty(domEventName)) {
            return domEventName;
        }
        if (domEventName.startsWith("on")) {
            return domEventName.substring(2);
        }
        return domEventName;
    }

    /**
     * This component always take care on rendering its children
     */
    @Override
    public boolean getRendersChildren() {
        return renderChildren;
    }

    @Override
    public void encodeBegin(final FacesContext context, final UIComponent component) throws IOException {
        // Checks the contract
        super.encodeBegin(context, component);
        // Stop here if the component will not be rendered
        if (!component.isRendered()) {
            return;
        }
        @SuppressWarnings("unchecked") // owolff Should not be a problem because
        // the cast is safe because of the
        // typing
        final var typedComponent = (T) component;
        doEncodeBegin(context, new DecoratingResponseWriter<>(context, typedComponent), typedComponent);
    }

    @Override
    public void encodeChildren(final FacesContext context, final UIComponent component) throws IOException {
        // Checks the contract
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        // Stop here if the component will not be rendered
        if (!component.isRendered()) {
            return;
        }
        @SuppressWarnings("unchecked") // owolff Should not be a problem because
        // the cast is safe because of the
        // typing
        final var typedComponent = (T) component;
        doEncodeChildren(context, new DecoratingResponseWriter<>(context, typedComponent), typedComponent);
    }

    @Override
    public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
        // Checks the contract
        super.encodeEnd(context, component);
        // Stop here if the component will not be rendered
        if (!component.isRendered()) {
            return;
        }
        @SuppressWarnings("unchecked") // owolff Should not be a problem because
        // the cast is safe because of the
        // typing
        final var typedComponent = (T) component;
        doEncodeEnd(context, new DecoratingResponseWriter<>(context, typedComponent), typedComponent);
    }

    @Override
    @SuppressWarnings("unchecked") // owolff Should not be a problem because
    // the cast is safe because of the
    // typing
    public void decode(final FacesContext context, final UIComponent component) {
        // Checks the contract
        super.decode(context, component);
        // Stop here if the component will not be rendered
        if (!component.isRendered()) {
            return;
        }
        final var componentWrapper = new ComponentWrapper<>((T) component);
        decodeClientBehavior(context, componentWrapper);
        doDecode(context, componentWrapper);
    }

    @Override
    public Object getConvertedValue(final FacesContext context, final UIComponent component,
            final Object submittedValue) {
        final var valueExpression = component.getValueExpression("value");
        Converter<?> converter = null;
        if (component instanceof ValueHolder holder) {
            converter = holder.getConverter();
        }
        if (null == converter && null != valueExpression) {
            final Class<?> converterType = valueExpression.getType(context.getELContext());
            if (null == converterType || Object.class == converterType || converterType == String.class
                    && null != context.getApplication().createConverter(String.class)) {
                return submittedValue;
            }
            try {
                final var application = context.getApplication();
                converter = application.createConverter(converterType);
                // cui-rewrite:disable InvalidExceptionUsageRecipe
            } catch (final Exception e) {
                LOGGER.error(e, JsfApiLogMessages.ERROR.CONVERTER_INSTANTIATION_FAILED, converterType, e.getMessage());
                return null;
            }
        } else if (null == converter) {
            return submittedValue;
        }

        if (converter != null) {
            return converter.getAsObject(context, component, submittedValue.toString());
        }
        throw new ConverterException("No converter found");
    }

    /**
     * Tries to decode the set client behavior. Will be called from
     * {@link #decode(FacesContext, UIComponent)} before
     * {@link #doDecode(FacesContext, ComponentWrapper)} is called.
     *
     * @param context          {@link FacesContext} must not be null
     * @param componentWrapper {@link ComponentWrapper} must not be null
     */
    protected void decodeClientBehavior(final FacesContext context, final ComponentWrapper<T> componentWrapper) {
        if (!componentWrapper.isClientBehaviorHolder() || componentWrapper.getClientBehaviors().isEmpty()) {
            return;
        }
        final var external = context.getExternalContext();
        final var params = external.getRequestParameterMap();
        final var behaviorEvent = params.get(JAVAX_FACES_BEHAVIOR_EVENT);

        if (null != behaviorEvent) {
            final var behaviorsForEvent = componentWrapper.getClientBehaviors().get(behaviorEvent);

            if (null != behaviorsForEvent && !behaviorsForEvent.isEmpty()) {
                final var behaviorSource = params.get(JAVAX_FACES_SOURCE);
                if (isFromBehaviorSource(behaviorSource, componentWrapper.getClientId())) {
                    for (final ClientBehavior behavior : behaviorsForEvent) {
                        behavior.decode(context, componentWrapper.getWrapped());
                    }
                }
            }
        }
    }

    /**
     * The actual method of the renderer to be implemented as replacement for
     * {@link Renderer#encodeBegin(FacesContext, UIComponent)}. The default
     * implementation is NOOP. The calling method
     * {@link BaseDecoratorRenderer#encodeBegin(FacesContext, UIComponent)} takes
     * care about checking the initial parameter and whether the component will be
     * rendered at all.
     *
     * @param context   FacesContext for the request we are processing
     * @param writer    decorated writer to be used
     * @param component to be rendered
     * @throws IOException occurs on interrupted I/O operations
     */
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<T> writer,
            final T component) throws IOException {
        // NOOP if not overridden by subclass
    }

    /**
     * The actual method of the renderer to be implemented as replacement for
     * {@link Renderer#encodeChildren(FacesContext, UIComponent)}. The calling
     * method
     * {@link BaseDecoratorRenderer#encodeChildren(FacesContext, UIComponent)} takes
     * care about checking the initial parameter and whether the component will be
     * rendered at all.
     *
     * @param context   FacesContext for the request we are processing
     * @param writer    decorated writer to be used
     * @param component to be rendered
     * @throws IOException occurs on interrupted I/O operations
     */
    protected void doEncodeChildren(final FacesContext context, final DecoratingResponseWriter<T> writer,
            final T component) throws IOException {
        for (final UIComponent child : component.getChildren()) {
            if (child.isRendered()) {
                child.encodeAll(context);
            }
        }
    }

    /**
     * The actual method of the renderer to be implemented as replacement for
     * {@link Renderer#encodeEnd(FacesContext, UIComponent)}. The default
     * implementation is NOOP. The calling method
     * {@link BaseDecoratorRenderer#encodeEnd(FacesContext, UIComponent)} takes care
     * about checking the initial parameter and whether the component will be
     * rendered at all.
     *
     * @param context   FacesContext for the request we are processing
     * @param writer    decorated writer to be used
     * @param component to be rendered
     * @throws IOException occurs on interrupted I/O operations
     */
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<T> writer, final T component)
            throws IOException {
        // NOOP if not overridden by subclass
    }

    /**
     * The actual method of the renderer to be implemented as replacement for
     * {@link Renderer#decode(FacesContext, UIComponent)}. The default
     * implementation is NOOP. The calling method
     * {@link BaseDecoratorRenderer#decode(FacesContext, UIComponent)} takes care
     * about checking the initial parameter and whether the component will be
     * decoded at all.
     *
     * @param context          FacesContext for the request we are processing
     * @param componentWrapper to be decoded
     */
    protected void doDecode(final FacesContext context, final ComponentWrapper<T> componentWrapper) {
        // NOOP if not overridden by subclass
    }
}
