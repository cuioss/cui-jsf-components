package com.icw.ehf.cui.core.api.components.util.styleclass;

import com.icw.ehf.cui.core.api.components.util.ComponentModifier;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Uses a given {@link ComponentModifier} to implement styleClass contract
 *
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class DelegateStyleClassModifier extends AbstractStyleClassModifier implements CombinedComponentModifier {

    @Delegate
    private final ComponentModifier componentModifier;

}
