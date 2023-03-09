package com.icw.ehf.cui.core.api.components.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Implementation for the visiting pattern of JSF, inspired by
 * http://ovaraksin.blogspot.de/2011/12/efficient-component-tree-traversal-in.html
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
        if (target instanceof EditableValueHolder) {
            editableValueHolders.add((EditableValueHolder) target);
        }
        return VisitResult.ACCEPT;
    }

}
