/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.metadata.composite.util;

import de.cuioss.jsf.dev.common.logging.DevLogMessages;
import de.cuioss.tools.logging.CuiLogger;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * <p>
 * Utility class responsible for finding and extracting sample source code from XHTML files
 * that demonstrate the usage of JSF components in the CUI component library. This class
 * specifically searches for facets with the name "sample" within component metadata sections.
 * </p>
 * 
 * <p>
 * The class uses XML parsing and XPath expressions to locate and extract the sample source code
 * from the specified component's facet. It handles namespace issues and entity escaping to ensure
 * proper XML parsing.
 * </p>
 * 
 * <p>
 * This is primarily used in developer tools and documentation to:
 * </p>
 * <ul>
 *   <li>Extract live code examples from demonstration pages</li>
 *   <li>Display the source code alongside working examples</li>
 *   <li>Allow developers to see how components should be implemented</li>
 * </ul>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>{@code
 * File xhtmlFile = new File("/path/to/demo-page.xhtml");
 * String componentId = "myButtonDemo";
 * SampleSourceFinder finder = new SampleSourceFinder(xhtmlFile, componentId);
 * String sourceCode = finder.getSampleSource();
 * 
 * // The sourceCode can then be displayed in documentation or development tools
 * }</pre>
 *
 * @since 1.0
 */
public class SampleSourceFinder {

    /**
     * <p>
     * Logger instance for this class.
     * </p>
     * <p>
     * Used for logging errors during source code extraction and debugging information.
     * </p>
     */
    private static final CuiLogger LOGGER = new CuiLogger(SampleSourceFinder.class);

    /**
     * <p>
     * String constant representing the opening facet tag for samples.
     * </p>
     * <p>
     * This is used to identify and replace the opening tag when extracting the sample source.
     * </p>
     */
    private static final String F_FACET_NAME_SAMPLE = "<f:facet name=\"sample\">";

    /**
     * <p>
     * Constant representing an empty string.
     * </p>
     * <p>
     * Used in replacement operations and as a default return value.
     * </p>
     */
    private static final String EMPTY = "";

    /**
     * <p>
     * Parameter name for configuring pretty printing in XML serialization.
     * </p>
     */
    private static final String FORMAT_PRETTY_PRINT_PARAM = "format-pretty-print";

    /**
     * <p>
     * Parameter name for controlling XML declaration output in serialization.
     * </p>
     */
    private static final String XML_DECLARATION_PARAM = "xml-declaration";

    /**
     * <p>
     * XPath fragment used to locate the sample facet by component ID.
     * </p>
     * <p>
     * Contains "Q_Q" as a placeholder for colons to avoid namespace issues during parsing.
     * </p>
     */
    private static final String F_Q_QFACET_NAME_SAMPLE = "']/fQ_Qfacet[@name='sample']";

    /**
     * <p>
     * XPath fragment used to locate the development metadata section by component ID.
     * </p>
     * <p>
     * Contains "Q_Q" as a placeholder for colons to avoid namespace issues during parsing.
     * </p>
     */
    private static final String DEVELOPMENT_Q_QPRINT_METADATA_ID = "//developmentQ_QprintMetadata[@id='";

    /**
     * <p>
     * String constant for the namespace delimiter (colon).
     * </p>
     * <p>
     * Used in string replacement operations for handling XML namespace issues.
     * </p>
     */
    private static final String NAMESPACE_DELIM = ":";

    /**
     * <p>
     * String constant for the ampersand character.
     * </p>
     * <p>
     * Used in string replacement operations to handle XML entity issues.
     * </p>
     */
    private static final String AND = "&";

    /**
     * <p>
     * Closing tag for the fake root element used during parsing.
     * </p>
     */
    private static final String FAKE_ROOT_END = "</Q_Q>";

    /**
     * <p>
     * Opening tag for the fake root element used during parsing.
     * </p>
     * <p>
     * A fake root element is added to ensure the XML fragment is well-formed for parsing.
     * </p>
     */
    private static final String FAKE_ROOT = "<Q_Q>";

    /**
     * <p>
     * Placeholder used to replace colons in namespace prefixes during parsing.
     * </p>
     * <p>
     * This avoids namespace resolution issues during XML processing.
     * </p>
     */
    private static final String REPLACER = "Q_Q";

    /**
     * <p>
     * Placeholder used to replace ampersands during parsing.
     * </p>
     * <p>
     * This avoids entity resolution issues during XML processing.
     * </p>
     */
    private static final String AND_REPLACER = "QandQ";

    /**
     * <p>
     * String constant representing the closing UI define tag.
     * </p>
     * <p>
     * Used to extract content section from XHTML files.
     * </p>
     */
    private static final String UI_DEFINE = "</ui:define>";

    /**
     * <p>
     * String constant representing the opening UI define tag for content.
     * </p>
     * <p>
     * Used to extract content section from XHTML files.
     * </p>
     */
    private static final String UI_DEFINE_NAME_CONTENT = "<ui:define name=\"content\">";

    /**
     * <p>
     * The XHTML file to extract sample source code from.
     * </p>
     */
    private File file = null;

    /**
     * <p>
     * The ID of the component whose sample code is to be extracted.
     * </p>
     */
    private String id = null;

    /**
     * <p>
     * Private default constructor to prevent instantiation without parameters.
     * </p>
     * <p>
     * This class requires a file and component ID to function properly.
     * </p>
     */
    @SuppressWarnings("unused")
    private SampleSourceFinder() {
        // Private constructor to prevent instantiation without parameters
    }

    /**
     * <p>
     * Creates a new SampleSourceFinder with the specified XHTML file and component ID.
     * </p>
     * <p>
     * This constructor initializes the finder with the necessary information to locate
     * and extract the sample source code for a specific component.
     * </p>
     *
     * @param file        The XHTML file containing the component sample, must not be null
     * @param componentId The ID of the component whose sample code is to be extracted, must not be null
     */
    public SampleSourceFinder(final File file, final String componentId) {
        this.file = file;
        id = componentId;
    }

    /**
     * <p>
     * Searches for the sample facet in the XHTML file and extracts its content.
     * </p>
     * 
     * <p>
     * This method performs the following steps:
     * </p>
     * <ol>
     *   <li>Reads the XHTML file into a string</li>
     *   <li>Extracts only the content section (between ui:define tags)</li>
     *   <li>Escapes namespace colons and ampersands to avoid parsing issues</li>
     *   <li>Adds a fake root element to ensure well-formed XML</li>
     *   <li>Parses the XML and uses XPath to locate the sample facet</li>
     *   <li>Serializes the facet content with proper formatting</li>
     *   <li>Restores the original syntax by removing the escaping</li>
     * </ol>
     * 
     * <p>
     * If any exception occurs during processing, an empty string is returned and the error
     * is logged.
     * </p>
     *
     * @return The extracted sample source code as a string, or an empty string if the sample
     *         cannot be found or an error occurs during extraction
     */
    public String getSampleSource() {
        var result = EMPTY;
        final var buffer = new byte[(int) file.length()];
        try (var bis = new BufferedInputStream(new FileInputStream(file))) {
            // read xhtml into string
            var read = bis.read(buffer);
            LOGGER.debug("Read bytes '%s'", read);
            var src = new String(buffer);
            // reduce it with content only
            src = Pattern.compile(UI_DEFINE_NAME_CONTENT).split(src)[1];
            src = Pattern.compile(UI_DEFINE).split(src)[0];
            // escape ":" symbol to avoid namespace problems
            src = Pattern.compile(NAMESPACE_DELIM).matcher(src).replaceAll(REPLACER);
            // escape "&" symbol to avoid SAX parser exceptions for non-defined elements
            src = Pattern.compile(AND).matcher(src).replaceAll(AND_REPLACER);
            // add fake root element
            src = FAKE_ROOT.concat(src).concat(FAKE_ROOT_END);
            // parse
            final var factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            final var builder = factory.newDocumentBuilder();
            final var doc = builder.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));
            // search for sample facet
            final var xfactory = XPathFactory.newInstance();
            final var xpath = xfactory.newXPath();
            final var expr = xpath.compile(DEVELOPMENT_Q_QPRINT_METADATA_ID.concat(id).concat(F_Q_QFACET_NAME_SAMPLE));
            final var facet = (Node) expr.evaluate(doc, XPathConstants.NODE);
            // get the content of facet sample
            if (null != facet) {
                final var document = facet.getOwnerDocument();
                final var domImplLS = (DOMImplementationLS) document.getImplementation();
                final var serializer = domImplLS.createLSSerializer();
                serializer.getDomConfig().setParameter(XML_DECLARATION_PARAM, Boolean.FALSE);
                serializer.getDomConfig().setParameter(FORMAT_PRETTY_PRINT_PARAM, Boolean.TRUE);
                // write result to string and delete all fakes
                result = serializer.writeToString(facet).replace(FAKE_ROOT, EMPTY).replace(FAKE_ROOT_END, EMPTY)
                        .replace(REPLACER, NAMESPACE_DELIM).replace(AND_REPLACER, AND)
                        .replaceFirst(F_FACET_NAME_SAMPLE, EMPTY);
                // remove last </f:facet>
                result = result.substring(0, result.length() - 11);
            }
        } catch (final IOException e) {
            LOGGER.error(e, DevLogMessages.ERROR.SAMPLE_SOURCE_IO_ERROR.format(file.getPath()));
            result = EMPTY;
        } catch (final ParserConfigurationException e) {
            LOGGER.error(e, DevLogMessages.ERROR.PARSER_CONFIG_ERROR.format(id));
            result = EMPTY;
        } catch (final SAXException | XPathExpressionException e) {
            LOGGER.error(e, DevLogMessages.ERROR.PARSER_EXCEPTION.format(file.getPath()));
            result = EMPTY;
        }
        return result.trim();
    }
}
