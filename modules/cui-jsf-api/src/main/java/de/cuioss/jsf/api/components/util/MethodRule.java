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
package de.cuioss.jsf.api.components.util;

import jakarta.el.MethodExpression;
import jakarta.faces.view.facelets.*;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>A specialized {@link MetaRule} implementation for handling JSF method expressions in facelet components.
 * This utility class simplifies the handling of method bindings in custom components and tags by providing a
 * mechanism to associate a method expression attribute with a corresponding setter on a component.</p>
 * 
 * <p>When creating custom components or tags, this rule can be used to properly bind method expressions
 * from tag attributes to component properties, handling the conversion between the expression syntax in
 * a page and the actual {@link MethodExpression} object needed by the component.</p>
 * 
 * <p>Usage example in a component's tag handler:</p>
 * <pre>
 * protected MetaRuleset createMetaRuleset(Class type) {
 *     MetaRuleset metaRuleset = super.createMetaRuleset(type);
 *     
 *     // Add rules for method expressions
 *     metaRuleset.addRule(new MethodRule("action", String.class, new Class[0]));
 *     metaRuleset.addRule(new MethodRule("valueChangeListener", void.class, 
 *                         new Class[] { ValueChangeEvent.class }));
 *     
 *     return metaRuleset;
 * }
 * </pre>
 * 
 * <p>This implementation is inspired by the original {@code com.sun.faces.facelets.tag.MethodRule}
 * class from the JSF reference implementation.</p>
 * 
 * @author Oliver Wolff
 */
@SuppressWarnings({"rawtypes"})
@RequiredArgsConstructor
public final class MethodRule extends MetaRule {

    /**
     * <p>The name of the method property to which this rule applies.</p>
     */
    private final String methodName;

    /**
     * <p>The expected return type of the method expression.</p>
     */
    private final Class<?> returnTypeClass;

    /**
     * <p>The expected parameter types of the method expression.</p>
     */
    private final Class[] params;

    /**
     * <p>Applies this rule to the specified attribute if the attribute name matches
     * the configured method name.</p>
     * 
     * <p>When a match occurs and the property is of type {@link MethodExpression},
     * this method creates and returns the appropriate metadata for binding the
     * method expression to the component property.</p>
     *
     * @param name The attribute name to check
     * @param attribute The tag attribute to be processed
     * @param meta The metadata target containing component property information
     * @return The metadata for binding the method expression if the name matches and
     *         the property type is {@link MethodExpression}, otherwise null
     */
    @Override
    public Metadata applyRule(final String name, final TagAttribute attribute, final MetadataTarget meta) {
        if (!name.equals(methodName)) {
            return null;
        }
        if (MethodExpression.class.equals(meta.getPropertyType(name))) {
            var method = meta.getWriteMethod(name);
            if (method != null) {
                return new ExpressionMetadata(method, attribute, returnTypeClass, params);
            }
        }
        return null;
    }

    /**
     * <p>Private implementation of {@link Metadata} that handles the creation and binding
     * of a {@link MethodExpression} to a component property.</p>
     * 
     * <p>This class is responsible for:</p>
     * <ul>
     *   <li>Creating the method expression from the tag attribute</li>
     *   <li>Setting the expression on the component via reflection</li>
     *   <li>Handling and wrapping any exceptions that occur during this process</li>
     * </ul>
     */
    private static class ExpressionMetadata extends Metadata {

        private final Method method;
        private final TagAttribute attribute;
        private final Class[] paramList;
        private final Class<?> returnType;

        /**
         * <p>Creates a new instance with the specified method, attribute, return type, and parameters.</p>
         * 
         * @param method The component's setter method for the method expression property
         * @param attribute The tag attribute containing the method expression
         * @param returnType The expected return type of the method expression
         * @param paramList The expected parameter types of the method expression
         */
        public ExpressionMetadata(final Method method, final TagAttribute attribute, final Class<?> returnType,
                final Class[] paramList) {
            this.method = method;
            this.attribute = attribute;
            this.paramList = paramList;
            this.returnType = returnType;
        }

        /**
         * <p>Applies the metadata to bind the method expression to the component.</p>
         * 
         * <p>This method creates a {@link MethodExpression} from the tag attribute and
         * invokes the component's setter method to establish the binding.</p>
         *
         * @param ctx The current facelet context
         * @param instance The component instance to which the metadata should be applied
         * @throws TagAttributeException If an error occurs during the expression creation
         *         or method invocation
         */
        @Override
        public void applyMetadata(final FaceletContext ctx, final Object instance) {
            var expr = attribute.getMethodExpression(ctx, returnType, paramList);
            try {
                method.invoke(instance, expr);
            } catch (InvocationTargetException e) {
                throw new TagAttributeException(attribute, e.getCause());
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new TagAttributeException(attribute, e);
            }
        }
    }
}
