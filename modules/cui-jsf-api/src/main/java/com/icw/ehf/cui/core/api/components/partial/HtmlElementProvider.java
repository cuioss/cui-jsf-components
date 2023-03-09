package com.icw.ehf.cui.core.api.components.partial;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.StateHelper;

import org.omnifaces.util.State;

import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.tools.string.Joiner;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of an HTML element to be used in
 * component context
 * </p>
 * <p>
 * The implementation relies on the correct use of attribute names,
 * saying they must exactly match the accessor methods.
 * </p>
 * <h2>htmlElement</h2>
 * <p>
 * The html tag to be used in the renderer
 * </p>
 *
 * @author Oliver Wolff
 */
public class HtmlElementProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "htmlElement";

    private final Node defaultNode;

    private final State state;

    /**
     * @param componentBridge
     * @param defaultNode
     */
    public HtmlElementProvider(@NonNull ComponentBridge componentBridge, @NonNull Node defaultNode) {
        state = new State(componentBridge.stateHelper());
        this.defaultNode = defaultNode;
    }

    /**
     * @return the htmlElement
     */
    public String getHtmlElement() {
        return state.get(KEY, defaultNode.getContent());
    }

    /**
     * @param htmlElement the state to set
     */
    public void setHtmlElement(String htmlElement) {
        state.put(KEY, htmlElement);
    }

    /**
     * Access configured or default htmlElement as {@link Node}
     *
     * @return the {@link Node} representing the htmlElement
     */
    public Node resolveHtmlElement() {
        var name = getHtmlElement();
        for (Node node : Node.values()) {
            if (name.equalsIgnoreCase(node.getContent())) {
                return node;
            }
        }
        List<String> allowedList = new ArrayList<>();
        for (Node node : Node.values()) {
            allowedList.add(node.getContent());
        }
        var message =
            "Unexpected Html-element found: '" + name + "' expected one of " + Joiner.on(",").join(allowedList);
        throw new IllegalArgumentException(message);
    }

}
