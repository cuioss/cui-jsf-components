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
package de.cuioss.jsf.bootstrap;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.bootstrap.accordion.AccordionComponent;
import de.cuioss.jsf.bootstrap.badge.BadgeComponent;
import de.cuioss.jsf.bootstrap.button.Button;
import de.cuioss.jsf.bootstrap.button.CloseCommandButton;
import de.cuioss.jsf.bootstrap.button.CommandButton;
import de.cuioss.jsf.bootstrap.icon.GenderIconComponent;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.bootstrap.icon.MimeTypeIconComponent;
import de.cuioss.jsf.bootstrap.layout.AbstractLayoutComponent;
import de.cuioss.jsf.bootstrap.layout.BootstrapPanelComponent;
import de.cuioss.jsf.bootstrap.layout.ColumnComponent;
import de.cuioss.jsf.bootstrap.layout.ControlGroupComponent;
import de.cuioss.jsf.bootstrap.layout.FormGroupComponent;
import de.cuioss.jsf.bootstrap.layout.InputGroupComponent;
import de.cuioss.jsf.bootstrap.layout.OutputLabelComponent;
import de.cuioss.jsf.bootstrap.layout.QuickControlGroupComponent;
import de.cuioss.jsf.bootstrap.layout.RowComponent;
import de.cuioss.jsf.bootstrap.layout.ToolbarComponent;
import de.cuioss.jsf.bootstrap.layout.input.HelpTextComponent;
import de.cuioss.jsf.bootstrap.layout.input.InputGuardComponent;
import de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessagesComponent;
import de.cuioss.jsf.bootstrap.lazyloading.LazyLoadingComponent;
import de.cuioss.jsf.bootstrap.link.OutputLinkButton;
import de.cuioss.jsf.bootstrap.menu.NavigationMenuComponent;
import de.cuioss.jsf.bootstrap.modal.ModalDialogComponent;
import de.cuioss.jsf.bootstrap.notification.NotificationBoxComponent;
import de.cuioss.jsf.bootstrap.tab.TabPanelComponent;
import de.cuioss.jsf.bootstrap.tag.TagComponent;
import de.cuioss.jsf.bootstrap.taginput.TagInputComponent;
import de.cuioss.jsf.bootstrap.taginput.TagInputRenderer;
import de.cuioss.jsf.bootstrap.taglist.TagListComponent;
import de.cuioss.jsf.bootstrap.tooltip.TooltipComponent;
import de.cuioss.jsf.bootstrap.waitingindicator.WaitingIndicatorComponent;
import lombok.experimental.UtilityClass;

/**
 * Constants container for Bootstrap component and renderer identifiers.
 * Used for component registration and lookup in the JSF framework.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@UtilityClass
public final class BootstrapFamily {

    /**
     * The component for {@link AccordionComponent}
     */
    public static final String ACCORDION_COMPONENT = "de.cuioss.bootstrap.accordion";

    /**
     * Default Renderer for {@link AccordionComponent}
     */
    public static final String ACCORDION_RENDERER = "de.cuioss.bootstrap.accordion_renderer";

    /**
     * The component id for {@link BadgeComponent}
     */
    public static final String BADGE_COMPONENT = "de.cuioss.bootstrap.badge";

    // Button
    /**
     * The component id for {@link Button}
     */
    public static final String BUTTON_COMPONENT = "de.cuioss.bootstrap.button";

    /**
     * Standard renderer for {@link Button}
     */
    public static final String BUTTON_RENDERER = "de.cuioss.bootstrap.button_renderer";

    // CommandButton
    /**
     * The component id for {@link CommandButton}
     */
    public static final String COMMAND_BUTTON_COMPONENT = "de.cuioss.bootstrap.commandbutton";

    /**
     * Standard renderer for {@link CommandButton}
     */
    public static final String COMMAND_BUTTON_RENDERER = "de.cuioss.bootstrap.commandbutton_renderer";
    /**
     * The component id for {@link CloseCommandButton}
     */
    public static final String CLOSE_COMMAND_BUTTON_COMPONENT = "de.cuioss.bootstrap.close_commandbutton";

    /**
     * Standard renderer for {@link CloseCommandButton}
     */
    public static final String CLOSE_COMMAND_BUTTON_RENDERER = "de.cuioss.bootstrap.close_commandbutton_renderer";

    /**
     * "de.cuioss.cui.components.bootstrap.family"
     */
    public static final String COMPONENT_FAMILY = "de.cuioss.bootstrap.family";

    /**
     * The component id for {@link CuiMessageComponent}
     */
    public static final String CUI_MESSAGE_COMPONENT = "de.cuioss.bootstrap.cuimessage";

    /**
     * Standard renderer for {@link CuiMessageComponent}
     */
    public static final String CUI_MESSAGE_COMPONENT_RENDERER = "de.cuioss.bootstrap.cuimessage_renderer";

    /**
     * The component id for {@link CuiMessagesComponent}
     */
    public static final String CUI_MESSAGES_COMPONENT = "de.cuioss.bootstrap.cuimessages";

    /**
     * The component for {@link GenderIconComponent}
     */
    public static final String GENDER_ICON_COMPONENT = "de.cuioss.bootstrap.genderIcon";

    // Icon
    /**
     * The component for {@link IconComponent}
     */
    public static final String ICON_COMPONENT = "de.cuioss.bootstrap.icon";

    /**
     * Default Renderer for {@link IconComponent}
     */
    public static final String ICON_COMPONENT_RENDERER = "de.cuioss.bootstrap.icon_renderer";

    /**
     * The component id for {@link LabeledContainerComponent}
     */
    public static final String LABELED_CONTAINER_COMPONENT = "de.cuioss.bootstrap.labeledContainer";

    /**
     * Standard renderer for {@link LabeledContainerComponent}
     */
    public static final String LABELED_CONTAINER_COMPONENT_RENDERER = "de.cuioss.bootstrap.labeledContainer_renderer";

    /**
     * The component id for {@link InputGuardComponent}
     */
    public static final String GUARDED_INPUT_COMPONENT = "de.cuioss.bootstrap.guarded_input";

    // labeledIcon
    /**
     * The component for {@link MimeTypeIconComponent}
     */
    public static final String LABELED_ICON_COMPONENT = "de.cuioss.bootstrap.icon.labeled";

    /**
     * Default Renderer for {@link MimeTypeIconComponent}
     */
    public static final String LABELED_ICON_COMPONENT_RENDERER = "de.cuioss.bootstrap.icon.labeled_renderer";

    /**
     * The component id for {@link ColumnComponent}
     */
    public static final String LAYOUT_COLUMN_COMPONENT = "de.cuioss.bootstrap.column";

    /**
     * The component id for {@link ControlGroupComponent}
     */
    public static final String LAYOUT_CONTROL_GROUP_COMPONENT = "de.cuioss.bootstrap.controlgroup";

    /**
     * Default Renderer for {@link ControlGroupComponent}
     */
    public static final String LAYOUT_CONTROL_GROUP_RENDERER = "de.cuioss.bootstrap.controlgroup_renderer";

    /**
     * The component id for {@link FormGroupComponent}
     */
    public static final String LAYOUT_FORMGROUP_COMPONENT = "de.cuioss.bootstrap.form_group";

    /**
     * The component id for {@link InputGroupComponent}
     */
    public static final String LAYOUT_INPUT_GROUP_COMPONENT = "de.cuioss.bootstrap.input_group";

    /**
     * Standard renderer for all variants of {@link AbstractLayoutComponent}
     */
    public static final String LAYOUT_RENDERER = "de.cuioss.bootstrap.layout_renderer";

    /**
     * The component id for {@link RowComponent}
     */
    public static final String LAYOUT_ROW_COMPONENT = "de.cuioss.bootstrap.row";

    /**
     * The component for {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_COMPONENT = "de.cuioss.bootstrap.modal_dialog";

    /**
     * Default Renderer for {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_COMPONENT_RENDERER = "de.cuioss.bootstrap.modal_dialog_renderer";

    /**
     * {@link AbstractParentDecorator} for controlling {@link ModalDialogComponent}
     */
    public static final String MODAL_DIALOG_CONTROL = "de.cuioss.bootstrap.modal_dialog_control";

    // MimeTypeIcon
    /**
     * The component for {@link MimeTypeIconComponent}
     */
    public static final String MIME_TYPE_ICON_COMPONENT = "de.cuioss.bootstrap.icon.mime_type";

    /**
     * Default Renderer for {@link MimeTypeIconComponent}
     */
    public static final String MIME_TYPE_ICON_COMPONENT_RENDERER = "de.cuioss.bootstrap.icon.mime_type_renderer";

    // NavigationMenu
    /**
     * The component for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_COMPONENT = "de.cuioss.bootstrap.navigationmenu";

    /**
     * Default Renderer for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_COMPONENT_RENDERER = "de.cuioss.bootstrap.navigationmenu_renderer";

    // NavigationMenu
    /**
     * The component for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_CONTAINER_COMPONENT = "de.cuioss.bootstrap.navigationmenu_container";

    /**
     * Default Renderer for {@link NavigationMenuComponent}
     */
    public static final String NAVIGATION_MENU_CONTAINER_COMPONENT_RENDERER = "de.cuioss.bootstrap.navigationmenu_container_renderer";

    /**
     * The component id for {@link NotificationBoxComponent}
     */
    public static final String NOTIFICATION_BOX_COMPONENT = "de.cuioss.bootstrap.notificationbox";

    /**
     * Standard renderer for {@link NotificationBoxComponent}
     */
    public static final String NOTIFICATION_BOX_RENDERER = "de.cuioss.bootstrap.notificationbox_renderer";

    /**
     * The component id for {@link OutputLabelComponent}
     */
    public static final String OUTPUT_LABEL_COMPONENT = "de.cuioss.bootstrap.outputlabel";

    /**
     * The component for {@link BootstrapPanelComponent}
     */
    public static final String PANEL_COMPONENT = "de.cuioss.bootstrap.panel";

    /**
     * Default Renderer for {@link BootstrapPanelComponent}
     */
    public static final String PANEL_RENDERER = "de.cuioss.bootstrap.panel_renderer";

    /**
     * The component for {@link QuickControlGroupComponent}
     */
    public static final String QUICK_CONTROL_GROUP_COMPONENT = "de.cuioss.bootstrap.quickControlGroup";

    /**
     * The component for {@link TabPanelComponent}
     */
    public static final String TAB_PANEL_COMPONENT = "de.cuioss.bootstrap.tab_panel";

    /**
     * Default Renderer for {@link TabPanelComponent}
     */
    public static final String TAB_PANEL_RENDERER = "de.cuioss.bootstrap.tab_panel_renderer";

    // Tag
    /**
     * The component for {@link TagComponent}
     */
    public static final String TAG_COMPONENT = "de.cuioss.bootstrap.tag";

    /**
     * Default Renderer for {@link TagComponent}
     */
    public static final String TAG_COMPONENT_RENDERER = "de.cuioss.bootstrap.tag_renderer";

    // TagInput
    /**
     * The component for {@link TagInputComponent}
     */
    public static final String TAG_INPUT_COMPONENT = "de.cuioss.bootstrap.taginput";

    /**
     * Default Renderer for {@link TagInputRenderer}
     */
    public static final String TAG_INPUT_COMPONENT_RENDERER = "de.cuioss.bootstrap.taginput_renderer";

    // TagList
    /**
     * The component for {@link TagListComponent}
     */
    public static final String TAG_LIST_COMPONENT = "de.cuioss.bootstrap.taglist";

    /**
     * Default Renderer for {@link TagListComponent}
     */
    public static final String TAG_LIST_COMPONENT_RENDERER = "de.cuioss.bootstrap.taglist_renderer";

    /**
     * The component for {@link ToolbarComponent}
     */
    public static final String TOOLBAR_COMPONENT = "de.cuioss.bootstrap.toolbar";

    /**
     * The component for {@link TooltipComponent}
     */
    public static final String TOOLTIP_COMPONENT = "de.cuioss.bootstrap.tooltip";

    /**
     * The component id for {@link CommandButton}
     */
    public static final String SWITCH_COMPONENT = "de.cuioss.bootstrap.switch";

    /**
     * Standard renderer for {@link CommandButton}
     */
    public static final String SWITCH_RENDERER = "de.cuioss.bootstrap.switch_renderer";

    /**
     * The component id for {@link OutputLinkButton}
     */
    public static final String OUTPUT_LINK_BUTTON_COMPONENT = "de.cuioss.bootstrap.outputLinkButton";

    /**
     * Standard renderer for {@link OutputLinkButton}
     */
    public static final String OUTPUT_LINK_BUTTON_RENDERER = "de.cuioss.bootstrap.outputLinkButton_renderer";

    /**
     * The component id for {@link LazyLoadingComponent}
     */
    public static final String LAZYLOADING_COMPONENT = "de.cuioss.bootstrap.lazyLoading";

    /**
     * Standard renderer for {@link LazyLoadingComponent}
     */
    public static final String LAZYLOADING_RENDERER = "de.cuioss.bootstrap.lazyLoading_renderer";

    /**
     * The component id for {@link WaitingIndicatorComponent}
     */
    public static final String WAITING_INDICATOR_COMPONENT = "de.cuioss.bootstrap.waitingindicator";

    /**
     * Standard renderer for {@link WaitingIndicatorComponent}
     */
    public static final String WAITING_INDICATOR_RENDERER = "de.cuioss.bootstrap.waitingindicator_renderer";

    /**
     * The component id for {@link HelpTextComponent}
     */
    public static final String HELP_TEXT_COMPONENT = "de.cuioss.bootstrap.helptext";

}
