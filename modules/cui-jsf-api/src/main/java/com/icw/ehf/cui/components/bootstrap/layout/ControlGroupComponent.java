package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.common.partial.ColumnProvider;
import com.icw.ehf.cui.core.api.components.base.BaseCuiPanel;

import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'form-group' and an embedded column. It is
 * used to place buttons within a form context.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <ul>
 * <li>controlGoups must be within container with 'form-horizontal'.</li>
 * <li>They act similar to formGroups or labeledContainer.</li>
 * <li>The size and offset of a controlGoup is always defined in 1/12 steps. A row is limited to 12.
 * If the columnSize (and offsets) are more than 12 the surplus columns will be rendered in the next
 * line.</li>
 * <li>The offset is used for the inner spacing of the column</li>
 * <li>The size definitions of the components are always related to sm. In case you want to change
 * the behavior you need to add additional styleClasses, see example. The default size is '8'</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "http://ehf-ui-trunk.ci.dev.icw.int:8080/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'form-group' and 'col-sm-' + size attribute</li>
 * <li>The offset css class is 'col-sm-offset-' + offsetSize attribute</li>
 * </ul>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link BaseCuiPanel}</li>
 * <li>{@link ColumnProvider}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.LAYOUT_CONTROL_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ControlGroupComponent extends BasicBootstrapPanelComponent {

    private static final Integer DEFAULT_COLUMN_SIZE = 8;

    @Delegate
    private final ColumnProvider columnProvider;

    /**
     *
     */
    public ControlGroupComponent() {
        super();
        columnProvider = new ColumnProvider(this, DEFAULT_COLUMN_SIZE);
        super.setRendererType(BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER);
    }

}
