package com.icw.ehf.cui.components.bootstrap;

import com.icw.ehf.cui.components.bootstrap.accordion.AccordionComponent;
import com.icw.ehf.cui.components.bootstrap.badge.BadgeComponent;
import com.icw.ehf.cui.components.bootstrap.button.Button;
import com.icw.ehf.cui.components.bootstrap.button.CloseCommandButton;
import com.icw.ehf.cui.components.bootstrap.button.CommandButton;
import com.icw.ehf.cui.components.bootstrap.icon.GenderIconComponent;
import com.icw.ehf.cui.components.bootstrap.icon.IconComponent;
import com.icw.ehf.cui.components.bootstrap.icon.MimeTypeIconComponent;
import com.icw.ehf.cui.components.bootstrap.layout.AbstractLayoutComponent;
import com.icw.ehf.cui.components.bootstrap.layout.BootstrapPanelComponent;
import com.icw.ehf.cui.components.bootstrap.layout.ColumnComponent;
import com.icw.ehf.cui.components.bootstrap.layout.ControlGroupComponent;
import com.icw.ehf.cui.components.bootstrap.layout.FormGroupComponent;
import com.icw.ehf.cui.components.bootstrap.layout.InputGroupComponent;
import com.icw.ehf.cui.components.bootstrap.layout.OutputLabelComponent;
import com.icw.ehf.cui.components.bootstrap.layout.QuickControlGroupComponent;
import com.icw.ehf.cui.components.bootstrap.layout.RowComponent;
import com.icw.ehf.cui.components.bootstrap.layout.ToolbarComponent;
import com.icw.ehf.cui.components.bootstrap.layout.input.HelpTextComponent;
import com.icw.ehf.cui.components.bootstrap.layout.input.InputGuardComponent;
import com.icw.ehf.cui.components.bootstrap.layout.input.LabeledContainerComponent;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessageComponent;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessagesComponent;
import com.icw.ehf.cui.components.bootstrap.link.OutputLinkButton;
import com.icw.ehf.cui.components.bootstrap.menu.NavigationMenuComponent;
import com.icw.ehf.cui.components.bootstrap.modal.ModalDialogComponent;
import com.icw.ehf.cui.components.bootstrap.notification.NotificationBoxComponent;
import com.icw.ehf.cui.components.bootstrap.tab.TabPanelComponent;
import com.icw.ehf.cui.components.bootstrap.tag.TagComponent;
import com.icw.ehf.cui.components.bootstrap.taginput.TagInputComponent;
import com.icw.ehf.cui.components.bootstrap.taginput.TagInputRenderer;
import com.icw.ehf.cui.components.bootstrap.taglist.TagListComponent;
import com.icw.ehf.cui.components.bootstrap.tooltip.TooltipComponent;
import com.icw.ehf.cui.core.api.components.decorator.AbstractParentDecorator;

import de.icw.cui.components.lazyloading.LazyLoadingComponent;
import de.icw.cui.components.waitingindicator.WaitingIndicatorComponent;
import lombok.experimental.UtilityClass;

/**
 * Simple Container for identifying bootstrap family
 *
 * @author Oliver Wolff
 */
@UtilityClass
public final class BootstrapFamily {

    /**
     * The component for {@link AccordionComponent}
     */
    public static final String ACCORDION_COMPONENT =
        "com.icw.cui.bootstrap.accordion";

    /**
     * Default Renderer for {@link AccordionComponent}
     */
    public static final String ACCORDION_RENDERER = "com.icw.cui.bootstrap.accordion_renderer";

    /**
     * The component id for {@link BadgeComponent}
     */
    public static final String BADGE_COMPONENT =
        "com.icw.cui.bootstrap.badge";

    // Button
    /**
     * The component id for {@link Button}
     */
    public static final String BUTTON_COMPONENT = "de.icw.cui.bootstrap.button";

    /**
     * Standard renderer for {@link Button}
     */
    public static final String BUTTON_RENDERER =
        "de.icw.cui.bootstrap.button_renderer";

    // CommandButton
    /**
     * The component id for {@link CommandButton}
     */
    public static final String COMMAND_BUTTON_COMPONENT = "de.icw.cui.bootstrap.commandbutton";

    /**
     * Standard renderer for {@link CommandButton}
     */
    public static final String COMMAND_BUTTON_RENDERER =
        "de.icw.cui.bootstrap.commandbutton_renderer";
    /**
     * The component id for {@link CloseCommandButton}
     */
    public static final String CLOSE_COMMAND_BUTTON_COMPONENT = "de.icw.cui.bootstrap.close_commandbutton";

    /**
     * Standard renderer for {@link CloseCommandButton}
     */
    public static final String CLOSE_COMMAND_BUTTON_RENDERER = "de.icw.cui.bootstrap.close_commandbutton_renderer";

    /**
     * "com.icw.cui.components.bootstrap.family"
     */
    public static final String COMPONENT_FAMILY = "com.icw.cui.bootstrap.family";

    /**
     * The component id for {@link CuiMessageComponent}
     */
    public static final String CUI_MESSAGE_COMPONENT = "com.icw.cui.bootstrap.cuimessage";

    /**
     * Standard renderer for {@link CuiMessageComponent}
     */
    public static final String CUI_MESSAGE_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.cuimessage_renderer";

    /**
     * The component id for {@link CuiMessagesComponent}
     */
    public static final String CUI_MESSAGES_COMPONENT = "com.icw.cui.bootstrap.cuimessages";

    /**
     * The component for {@link GenderIconComponent}
     */
    public static final String GENDER_ICON_COMPONENT = "com.icw.cui.bootstrap.genderIcon";

    // Icon
    /**
     * The component for {@link IconComponent}
     */
    public static final String ICON_COMPONENT = "com.icw.cui.bootstrap.icon";

    /**
     * Default Renderer for {@link IconComponent}
     */
    public static final String ICON_COMPONENT_RENDERER = "com.icw.cui.bootstrap.icon_renderer";

    /**
     * The component id for {@link LabeledContainerComponent}
     */
    public static final String LABELED_CONTAINER_COMPONENT =
        "com.icw.cui.bootstrap.labeledContainer";

    /**
     * Standard renderer for {@link LabeledContainerComponent}
     */
    public static final String LABELED_CONTAINER_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.labeledContainer_renderer";

    /**
     * The component id for {@link InputGuardComponent}
     */
    public static final String GUARDED_INPUT_COMPONENT =
        "com.icw.cui.bootstrap.guarded_input";

    // labeledIcon
    /**
     * The component for {@link MimeTypeIconComponent}
     */
    public static final String LABELED_ICON_COMPONENT = "com.icw.cui.bootstrap.icon.labeled";

    /**
     * Default Renderer for {@link MimeTypeIconComponent}
     */
    public static final String LABELED_ICON_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.icon.labeled_renderer";

    /**
     * The component id for {@link ColumnComponent}
     */
    public static final String LAYOUT_COLUMN_COMPONENT = "com.icw.cui.bootstrap.column";

    /**
     * The component id for {@link ControlGroupComponent}
     */
    public static final String LAYOUT_CONTROL_GROUP_COMPONENT =
        "com.icw.cui.bootstrap.controlgroup";

    /**
     * Default Renderer for {@link ControlGroupComponent}
     */
    public static final String LAYOUT_CONTROL_GROUP_RENDERER =
        "com.icw.cui.bootstrap.controlgroup_renderer";

    /**
     * The component id for {@link FormGroupComponent}
     */
    public static final String LAYOUT_FORMGROUP_COMPONENT = "com.icw.cui.bootstrap.form_group";

    /**
     * The component id for {@link InputGroupComponent}
     */
    public static final String LAYOUT_INPUT_GROUP_COMPONENT = "com.icw.cui.bootstrap.input_group";

    /**
     * Standard renderer for all variants of {@link AbstractLayoutComponent}
     */
    public static final String LAYOUT_RENDERER = "com.icw.cui.bootstrap.layout_renderer";

    /**
     * The component id for {@link RowComponent}
     */
    public static final String LAYOUT_ROW_COMPONENT = "com.icw.cui.bootstrap.row";

    /**
     * The component for {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_COMPONENT = "com.icw.cui.bootstrap.modal_dialog";

    /**
     * Default Renderer for {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.modal_dialog_renderer";

    /**
     * {@link AbstractParentDecorator} for controlling {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_CONTROL = "com.icw.cui.bootstrap.modal_dialog_control";

    // MimeTypeIcon
    /**
     * The component for {@link MimeTypeIconComponent}
     */
    public static final String MIME_TYPE_ICON_COMPONENT = "com.icw.cui.bootstrap.icon.mime_type";

    /**
     * Default Renderer for {@link MimeTypeIconComponent}
     */
    public static final String MIME_TYPE_ICON_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.icon.mime_type_renderer";

    // NavigationMenu
    /**
     * The component for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_COMPONENT = "com.icw.cui.bootstrap.navigationmenu";

    /**
     * Default Renderer for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.navigationmenu_renderer";

    // NavigationMenu
    /**
     * The component for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_CONTAINER_COMPONENT = "com.icw.cui.bootstrap.navigationmenu_container";

    /**
     * Default Renderer for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_CONTAINER_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.navigationmenu_container_renderer";

    /**
     * The component id for {@link NotificationBoxComponent}
     */
    public static final String NOTIFICATION_BOX_COMPONENT = "com.icw.cui.bootstrap.notificationbox";

    /**
     * Standard renderer for {@link NotificationBoxComponent}
     */
    public static final String NOTIFICATION_BOX_RENDERER =
        "com.icw.cui.bootstrap.notificationbox_renderer";

    /**
     * The component id for {@link OutputLabelComponent}
     */
    public static final String OUTPUT_LABEL_COMPONENT = "com.icw.cui.bootstrap.outputlabel";

    /**
     * The component for {@link BootstrapPanelComponent}
     */
    public static final String PANEL_COMPONENT = "com.icw.cui.bootstrap.panel";

    /**
     * Default Renderer for {@link BootstrapPanelComponent}
     */
    public static final String PANEL_RENDERER = "com.icw.cui.bootstrap.panel_renderer";

    /**
     * The component for {@link QuickControlGroupComponent}
     */
    public static final String QUICK_CONTROL_GROUP_COMPONENT = "com.icw.cui.bootstrap.quickControlGroup";

    /**
     * The component for {@link TabPanelComponent}
     */
    public static final String TAB_PANEL_COMPONENT =
        "com.icw.cui.bootstrap.tab_panel";

    /**
     * Default Renderer for {@link TabPanelComponent}
     */
    public static final String TAB_PANEL_RENDERER = "com.icw.cui.bootstrap.tab_panel_renderer";

    // Tag
    /**
     * The component for {@link TagComponent}
     */
    public static final String TAG_COMPONENT = "com.icw.cui.bootstrap.tag";

    /**
     * Default Renderer for {@link TagComponent}
     */
    public static final String TAG_COMPONENT_RENDERER = "com.icw.cui.bootstrap.tag_renderer";

    // TagInput
    /**
     * The component for {@link TagInputComponent}
     */
    public static final String TAG_INPUT_COMPONENT = "com.icw.cui.bootstrap.taginput";

    /**
     * Default Renderer for {@link TagInputRenderer}
     */
    public static final String TAG_INPUT_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.taginput_renderer";

    // TagList
    /**
     * The component for {@link TagListComponent}
     */
    public static final String TAG_LIST_COMPONENT = "com.icw.cui.bootstrap.taglist";

    /**
     * Default Renderer for {@link TagListComponent}
     */
    public static final String TAG_LIST_COMPONENT_RENDERER =
        "com.icw.cui.bootstrap.taglist_renderer";

    /**
     * The component for {@link ToolbarComponent}
     */
    public static final String TOOLBAR_COMPONENT = "com.icw.cui.bootstrap.toolbar";

    /**
     * The component for {@link TooltipComponent}
     */
    public static final String TOOLTIP_COMPONENT = "com.icw.cui.bootstrap.tooltip";

    /**
     * The component id for {@link CommandButton}
     */
    public static final String SWITCH_COMPONENT = "de.icw.cui.bootstrap.switch";

    /**
     * Standard renderer for {@link CommandButton}
     */
    public static final String SWITCH_RENDERER = "de.icw.cui.bootstrap.switch_renderer";

    /**
     * The component id for {@link OutputLinkButton}
     */
    public static final String OUTPUT_LINK_BUTTON_COMPONENT = "de.icw.cui.bootstrap.outputLinkButton";

    /**
     * Standard renderer for {@link OutputLinkButton}
     */
    public static final String OUTPUT_LINK_BUTTON_RENDERER = "de.icw.cui.bootstrap.outputLinkButton_renderer";

    /**
     * The component id for {@link LazyLoadingComponent}
     */
    public static final String LAZYLOADING_COMPONENT = "de.icw.cui.lazyLoading";

    /**
     * Standard renderer for {@link LazyLoadingComponent}
     */
    public static final String LAZYLOADING_RENDERER = "de.icw.cui.lazyLoading_renderer";

    /**
     * The component id for {@link WaitingIndicatorComponent}
     */
    public static final String WAITING_INDICATOR_COMPONENT = "de.icw.cui.waitingindicator";

    /**
     * Standard renderer for {@link WaitingIndicatorComponent}
     */
    public static final String WAITING_INDICATOR_RENDERER = "de.icw.cui.waitingindicator_renderer";

    /**
     * The component id for {@link HelpTextComponent}
     */
    public static final String HELP_TEXT_COMPONENT = "de.icw.cui.helptext";

}
