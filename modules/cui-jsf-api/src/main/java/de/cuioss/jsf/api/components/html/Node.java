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
package de.cuioss.jsf.api.components.html;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * To be used in conjunction with {@link HtmlTreeBuilder}. Each constant
 * represents a html node.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Node {

    /**
     * Html <b>a</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_a.asp">HTML &lt;a&gt; Tag</a>
     */
    A("a"),

    /**
     * Html <b>button</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_button.asp">HTML
     *      &lt;button&gt; Tag</a>
     */
    BUTTON("button"),

    /**
     * Html <b>code</b> tag.<br>
     */
    CODE("code"),

    /**
     * Html <b>div</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_div.asp">HTML &lt;div&gt;
     *      Tag</a>
     */
    DIV("div"),

    /**
     * Html <b>fieldset</b> tag.<br>
     */
    FIELDSET("fieldset"),

    /**
     * Html <b>form</b> tag.<br>
     */
    FORM("form"),

    /**
     * Html <b>h1</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to
     *      &lt;h6&gt; Tags</a>
     */
    H1("h1"),

    /**
     * Html <b>h2</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to
     *      &lt;h6&gt; Tags</a>
     */
    H2("h2"),

    /**
     * Html <b>h3</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to
     *      &lt;h6&gt; Tags</a>
     */
    H3("h3"),

    /**
     * Html <b>h4</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to
     *      &lt;h6&gt; Tags</a>
     */
    H4("h4"),

    /**
     * Html <b>input</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_input.asp">HTML &lt;input&gt;
     *      Tag</a>
     */
    INPUT("input"),

    /**
     * Html <b>i</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_i.asp">HTML &lt;i&gt; Tag</a>
     */
    ITALIC("i"),

    /**
     * Html <b>label</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_label.asp">HTML &lt;label&gt;
     *      Tag</a>
     */
    LABEL("label"),

    /**
     * Html <b>legend</b> tag.<br>
     */
    LEGEND("legend"),

    /**
     * Html <b>li</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_li.asp">HTML &lt;li&gt;
     *      Tag</a>
     */
    LI("li"),

    /**
     * Html <b>nav</b> tag.<br>
     *
     * @see <a href="https://www.w3schools.com/tags/tag_nav.asp">HTML &lt;nav&gt;
     *      Tag</a>
     */
    NAV("nav"),

    /**
     * Html <b>ol</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_ol.asp">HTML &lt;ol&gt;
     *      Tag</a>
     */
    OL("ol"),

    /**
     * Html <b>option</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_option.asp">HTML
     *      &lt;option&gt; Tag</a>
     */
    OPTION("option"),

    /**
     * Html <b>p</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_p.asp">HTML &lt;p&gt; Tag</a>
     */
    P("p"),

    /**
     * Html <b>pre</b> tag.<br>
     */
    PRE("pre"),

    /**
     * Html <b>script</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_script.asp">HTML
     *      &lt;script&gt; Tag</a>
     */
    SCRIPT("script"),

    /**
     * Html <b>select</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_select.asp">HTML
     *      &lt;select&gt; Tag</a>
     */
    SELECT("select"),

    /**
     * Html <b>span</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_span.asp">HTML &lt;span&gt;
     *      Tag</a>
     */
    SPAN("span"),

    /**
     * Html <b>textarea</b> tag.<br>
     */
    TEXT_AREA("textarea"),

    /**
     * Html <b>ul</b> tag.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_ul.asp">HTML &lt;ul&gt;
     *      Tag</a>
     */
    UL("ul");

    @Getter
    private final String content;
}
