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

import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.visit.VisitCallback;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class provides an implementation of the {@link VisitCallback} interface that collects
 * all {@link EditableValueHolder} components in a JSF component tree during traversal.
 * It's designed to be used with the JSF component visiting mechanism.</p>
 * 
 * <p>The visitor pattern implementation efficiently traverses the component tree to find
 * all rendered input components. When a component implementing {@link EditableValueHolder}
 * is found, it's added to an internal list that can be accessed after traversal.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * EditableValueHoldersVisitCallback visitCallback = new EditableValueHoldersVisitCallback();
 * VisitContext visitContext = VisitContext.createVisitContext(facesContext);
 * form.visitTree(visitContext, visitCallback);
 * 
 * // Access the collected components
 * List&lt;EditableValueHolder&gt; inputs = visitCallback.getEditableValueHolders();
 * for (EditableValueHolder input : inputs) {
 *     // Process each input component
 *     input.resetValue();
 * }
 * </pre>
 * 
 * <p>This implementation is inspired by the approach described in
 * <a href="http://ovaraksin.blogspot.de/2011/12/efficient-component-tree-traversal-in.html">
 * Oleg Varaksin's blog post about efficient component tree traversal</a>.</p>
 *
 * @author Oliver Wolff
 * @see VisitCallback
 * @see EditableValueHolder
 * @see ComponentUtility
 */
@ToString
@EqualsAndHashCode
public class EditableValueHoldersVisitCallback implements VisitCallback {

    /**
     * <p>The list of all visited and rendered {@link EditableValueHolder} components
     * that were found during the tree traversal.</p>
     * 
     * <p>This collection is populated during the component tree visit process and
     * can be accessed after the traversal is complete.</p>
     */
    @Getter
    private final List<EditableValueHolder> editableValueHolders = new ArrayList<>();

    /**
     * <p>Implements the visit method from {@link VisitCallback} to collect all
     * {@link EditableValueHolder} components during tree traversal.</p>
     * 
     * <p>This method filters out non-rendered components with {@link VisitResult#REJECT}
     * and collects all component instances that implement {@link EditableValueHolder}
     * into the internal list. The traversal will continue to all child components
     * by returning {@link VisitResult#ACCEPT}.</p>
     * 
     * @param context The context for the visit, provides information about the
     *                current state of the visit, must not be null
     * @param target  The current component being visited, must not be null
     * @return {@link VisitResult#REJECT} if the component is not rendered,
     *         {@link VisitResult#ACCEPT} otherwise to continue tree traversal
     */
    @Override
    public VisitResult visit(final VisitContext context, final UIComponent target) {
        if (!target.isRendered()) {
            return VisitResult.REJECT;
        }
        if (target instanceof EditableValueHolder holder) {
            editableValueHolders.add(holder);
        }
        return VisitResult.ACCEPT;
    }

}
