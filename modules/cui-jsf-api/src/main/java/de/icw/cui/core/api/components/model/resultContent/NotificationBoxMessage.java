package de.icw.cui.core.api.components.model.resultContent;

import java.io.Serializable;

import com.icw.ehf.cui.core.api.components.css.ContextState;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Value;

@Value
public class NotificationBoxMessage implements Serializable {

    private static final long serialVersionUID = 170134856997455422L;

    private IDisplayNameProvider notificationBoxValue;

    private ContextState notificationBoxState;
}
