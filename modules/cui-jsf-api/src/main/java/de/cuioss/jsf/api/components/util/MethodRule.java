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
package de.cuioss.jsf.api.components.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.el.MethodExpression;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.MetaRule;
import jakarta.faces.view.facelets.Metadata;
import jakarta.faces.view.facelets.MetadataTarget;
import jakarta.faces.view.facelets.TagAttribute;
import jakarta.faces.view.facelets.TagAttributeException;

import lombok.RequiredArgsConstructor;

/**
 * Inspired from: com.sun.faces.facelets.tag.MethodRule.
 */
@SuppressWarnings({ "rawtypes" })
@RequiredArgsConstructor
public final class MethodRule extends MetaRule {

    private final String methodName;
    private final Class<?> returnTypeClass;
    private final Class[] params;

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

    private static class ExpressionMetadata extends Metadata {

        private final Method method;
        private final TagAttribute attribute;
        private final Class[] paramList;
        private final Class<?> returnType;

        public ExpressionMetadata(final Method method, final TagAttribute attribute, final Class<?> returnType,
                final Class[] paramList) {
            this.method = method;
            this.attribute = attribute;
            this.paramList = paramList;
            this.returnType = returnType;
        }

        @Override
        public void applyMetadata(final FaceletContext ctx, final Object instance) {
            var expr = attribute.getMethodExpression(ctx, returnType, paramList);
            try {
                method.invoke(instance, expr);
            } catch (InvocationTargetException e) {
                throw new TagAttributeException(attribute, e.getCause());
            } catch (Exception e) {
                throw new TagAttributeException(attribute, e);
            }
        }
    }
}
