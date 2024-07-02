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

import java.util.ArrayList;
import java.util.List;

import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.visit.VisitCallback;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Implementation for the visiting pattern of JSF, inspired by
 * <a href="http://ovaraksin.blogspot.de/2011/12/efficient-component-tree-traversal-in.html">...</a>
 *
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode
public class EditableValueHoldersVisitCallback implements VisitCallback {

    @Getter
    private final List<EditableValueHolder> editableValueHolders = new ArrayList<>();

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
