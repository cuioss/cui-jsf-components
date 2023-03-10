package de.cuioss.jsf.api.components.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.el.MethodExpression;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

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
    public Metadata applyRule(final String name, final TagAttribute attribute,
            final MetadataTarget meta) {
        if (!name.equals(methodName)) {
            return null;
        }
        if (MethodExpression.class.equals(meta.getPropertyType(name))) {
            var method = meta.getWriteMethod(name);
            if (method != null) {
                return new ExpressionMetadata(method, attribute,
                        returnTypeClass, params);
            }
        }
        return null;
    }

    private static class ExpressionMetadata extends Metadata {

        private final Method method;
        private final TagAttribute attribute;
        private final Class[] paramList;
        private final Class<?> returnType;

        public ExpressionMetadata(final Method method, final TagAttribute attribute,
                final Class<?> returnType, final Class[] paramList) {
            this.method = method;
            this.attribute = attribute;
            this.paramList = paramList;
            this.returnType = returnType;
        }

        @Override
        public void applyMetadata(final FaceletContext ctx, final Object instance) {
            var expr = attribute.getMethodExpression(ctx,
                    returnType, paramList);
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
