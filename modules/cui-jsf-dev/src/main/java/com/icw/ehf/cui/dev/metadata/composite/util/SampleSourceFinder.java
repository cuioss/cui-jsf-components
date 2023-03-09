package com.icw.ehf.cui.dev.metadata.composite.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.cuioss.tools.logging.CuiLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 * Finds the source for the samples.
 *
 * @author e0623
 */
public class SampleSourceFinder {

    private static final CuiLogger log = new CuiLogger(SampleSourceFinder.class);

    private static final String F_FACET_NAME_SAMPLE = "<f:facet name=\"sample\">";

    private static final String EMPTY = "";

    private static final String FORMAT_PRETTY_PRINT_PARAM = "format-pretty-print";

    private static final String XML_DECLARATION_PARAM = "xml-declaration";

    private static final String F_Q_QFACET_NAME_SAMPLE = "']/fQ_Qfacet[@name='sample']";

    private static final String DEVELOPMENT_Q_QPRINT_METADATA_ID =
            "//developmentQ_QprintMetadata[@id='";

    private static final String NAMESPACE_DELIM = ":";

    private static final String AND = "&";

    private static final String FAKE_ROOT_END = "</Q_Q>";

    private static final String FAKE_ROOT = "<Q_Q>";

    private static final String REPLACER = "Q_Q";

    private static final String AND_REPLACER = "QandQ";

    private static final String UI_DEFINE = "</ui:define>";

    private static final String UI_DEFINE_NAME_CONTENT = "<ui:define name=\"content\">";

    private File file = null;

    private String id = null;

    @SuppressWarnings("unused")
    private SampleSourceFinder() {

    }

    /**
     * Constructor
     *
     * @param file xhtml file
     * @param componentId id of the component
     */
    public SampleSourceFinder(final File file, final String componentId) {
        this.file = file;
        id = componentId;
    }

    /**
     * Search for sample facet in xhtml file and return sample source
     *
     * @return String with sample source
     */
    public String getSampleSource() {
        String result = EMPTY;
        final byte[] buffer = new byte[(int) file.length()];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            // read xhtml into string
            int read = bis.read(buffer);
            log.debug("Read bytes '{}'", read);
            String src = new String(buffer);
            // reduce it with content only
            src = Pattern.compile(UI_DEFINE_NAME_CONTENT).split(src)[1];
            src = Pattern.compile(UI_DEFINE).split(src)[0];
            // escape ":" symbol to avoid namespace problems
            src = Pattern.compile(NAMESPACE_DELIM).matcher(src)
                    .replaceAll(REPLACER);
            // escape "&" symbol to avoid SAX parser exceptions for non-defined elements
            src = Pattern.compile(AND).matcher(src)
                    .replaceAll(AND_REPLACER);
            // add fake root element
            src = FAKE_ROOT.concat(src).concat(FAKE_ROOT_END);
            // parse
            final DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(new ByteArrayInputStream(src
                    .getBytes(StandardCharsets.UTF_8)));
            // search for sample facet
            final XPathFactory xfactory = XPathFactory.newInstance();
            final XPath xpath = xfactory.newXPath();
            final XPathExpression expr = xpath
                    .compile(DEVELOPMENT_Q_QPRINT_METADATA_ID.concat(id)
                            .concat(F_Q_QFACET_NAME_SAMPLE));
            final Node facet = (Node) expr.evaluate(doc, XPathConstants.NODE);
            // get the content of facet sample
            if (null != facet) {
                final Document document = facet.getOwnerDocument();
                final DOMImplementationLS domImplLS = (DOMImplementationLS) document
                        .getImplementation();
                final LSSerializer serializer = domImplLS.createLSSerializer();
                serializer.getDomConfig().setParameter(XML_DECLARATION_PARAM,
                        Boolean.FALSE);
                serializer.getDomConfig().setParameter(
                        FORMAT_PRETTY_PRINT_PARAM, Boolean.TRUE);
                // write result to string and delete all fakes
                result = serializer.writeToString(facet)
                        .replaceAll(FAKE_ROOT, EMPTY)
                        .replaceAll(FAKE_ROOT_END, EMPTY)
                        .replaceAll(REPLACER, NAMESPACE_DELIM)
                        .replaceAll(AND_REPLACER, AND)
                        .replaceFirst(F_FACET_NAME_SAMPLE, EMPTY);
                // remove last </f:facet>
                result = result.substring(0, result.length() - 11);
            }
        } catch (final IOException e) {
            log.error("Sample source not found in ".concat(file.getPath()), e);
            result = EMPTY;
        } catch (final ParserConfigurationException e) {
            log.error(
                    "Parser configuration exception in sample source of component "
                    .concat(id),
                    e);
            result = EMPTY;
        } catch (final SAXException | XPathExpressionException e) {
            log.error("Parser exception in ".concat(file.getPath()), e);
            result = EMPTY;
        }
        return result.trim();
    }
}
