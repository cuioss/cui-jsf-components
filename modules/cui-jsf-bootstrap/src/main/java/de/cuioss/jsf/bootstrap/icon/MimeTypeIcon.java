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
package de.cuioss.jsf.bootstrap.icon;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.bootstrap.icon.strategy.IStrategyProvider;
import de.cuioss.jsf.bootstrap.icon.strategy.Rule;
import de.cuioss.jsf.bootstrap.icon.strategy.StrategyProviderImpl;
import de.cuioss.tools.io.StructuredFilename;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * <p>
 * Comprehensive enumeration of MIME type icons supported by the CUI icon library.
 * Each constant represents a specific file or document type with associated CSS classes
 * and HTML metadata. This enum serves as the central registry for all supported MIME
 * type icons in the system.
 * </p>
 * 
 * <h2>Implementation Features</h2>
 * <ul>
 *   <li>Defines CSS class names for each MIME type icon</li>
 *   <li>Maps standard MIME type identifiers to appropriate icon representations</li>
 *   <li>Provides file extension mappings for common file types</li>
 *   <li>Supports healthcare-specific document formats (CDA, CCD, CCDA, etc.)</li>
 *   <li>Includes placeholder versions of each icon type</li>
 *   <li>Offers intelligent resolution strategies to determine appropriate icon</li>
 * </ul>
 * 
 * <h2>Resolution Strategies</h2>
 * <p>
 * The enum provides several methods to determine the appropriate icon:
 * </p>
 * <ol>
 *   <li><b>Direct enum access</b> - Using the enum constant directly when the type is known</li>
 *   <li><b>MIME type resolution</b> - Using the {@link #valueOfIdentifier} method with a MIME type string</li>
 *   <li><b>File extension resolution</b> - Using the {@link #determineForFilenameSuffix} method with a filename</li>
 *   <li><b>HL7 format resolution</b> - Special handling for healthcare document formats</li>
 * </ol>
 * 
 * <h2>Icon Categories</h2>
 * <p>
 * The icons can be broadly categorized into:
 * </p>
 * <ul>
 *   <li><b>Document types</b> - PDF, DOC, DOCX, RTF, TXT, etc.</li>
 *   <li><b>Image types</b> - JPEG, PNG, GIF, TIFF, etc.</li>
 *   <li><b>Audio types</b> - MP3, audio/basic, etc.</li>
 *   <li><b>Video types</b> - MPEG, AVI, etc.</li>
 *   <li><b>Healthcare document types</b> - CDA, CCD, CCDA, etc.</li>
 *   <li><b>Other types</b> - HTML, XML, etc.</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p><b>Direct enum usage:</b></p>
 * <pre>
 * MimeTypeIcon icon = MimeTypeIcon.PDF;
 * String cssClass = icon.getIconClass();  // Returns "cui-mime-type-pdf"
 * </pre>
 * 
 * <p><b>Resolving from a MIME type string:</b></p>
 * <pre>
 * MimeTypeIcon icon = MimeTypeIcon.valueOfIdentifier("application/pdf", null);
 * // Returns MimeTypeIcon.PDF
 * </pre>
 * 
 * <p><b>Resolving from a filename:</b></p>
 * <pre>
 * MimeTypeIcon icon = MimeTypeIcon.determineForFilenameSuffix("document.docx");
 * // Returns MimeTypeIcon.DOCX
 * </pre>
 * 
 * <p><b>Healthcare format handling:</b></p>
 * <pre>
 * MimeTypeIcon icon = MimeTypeIcon.valueOfIdentifier("text/xml", "CCD");
 * // Returns MimeTypeIcon.CCD regardless of the MIME type
 * </pre>
 *
 * @author Oliver Wolff
 * @author Eugen Fischer
 * @see MimeTypeIconComponent
 * @see MimeTypeIconRenderer
 */
public enum MimeTypeIcon {

    /** General audio file. */
    AUDIO_BASIC("audio/basic"),

    /** Audio -> mp3. */
    AUDIO_MPEG("audio/mpeg", "mp3"),

    /** Avi Video-Container. */
    AVI("video/x-avi", "avi"),

    /** CCD document. */
    CCD("CCD"),

    /** CCDA document. */
    CCDA("CCDA"),

    /** CCR document. */
    CCR("CCR"),

    /** CDA document. */
    CDA("multipart/x-hl7-cda-level1"),

    /** Dicom document. */
    DCM("application/dicom"),

    /** Legacy word. */
    DOC("application/msword", "doc"),

    /** Word current. */
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),

    /** Still in use?. */
    G3FAX("image/g3fax"),

    /** Gif image . */
    GIF("image/gif", "gif"),

    /** Html document. */
    HTML("text/html", "html"),

    /** A Jpg image. */
    JPEG("image/jpeg", "jpg", "jpeg"),

    /** Some legacy audio format. */
    K32ADPCM("audio/k32adpcm"),

    /** Octet Stream /v arbitrary. */
    OCTET_STREAM("application/octet-stream"),

    /** PDF document. */
    PDF("application/pdf", "pdf"),

    /** png image. */
    PNG("image/png", "png"),

    /** Rich Text Format. */
    RTF("text/rtf", "rtf"),

    /** SGML. */
    SGML("text/sgml"),

    /** ShockWave / Flash. */
    SHOCKWAVE_FLASH("application/x-shockwave-flash"),

    /** TIFF-Image. */
    TIF("image/tiff", "tif", "tiff"),

    /** Standard text-files. */
    TXT("text/plain", "txt", "text"),

    /** Default / Undefined value. */
    UNDEFINED("undefined"),

    /** Mpg generic video. */
    VIDEO_MPEG("video/mpeg", "mpg"),

    /** Standard xml-files. */
    XML("text/xml", "xml"),

    /** Default for Hl7 based xml that can not be determined. */
    XXX("unknown/hl7");

    /**
     * Strategy for hl7 types which will be resolved by format code
     */
    private static final IStrategyProvider<String, MimeTypeIcon> HL7_TYPES = new StrategyProviderImpl.Builder<String, MimeTypeIcon>()
            .add(Rule.create("CCD", CCD)).add(Rule.create("CCDA", CCDA)).add(Rule.create("CCR", CCR))
            .add(Rule.create("CDA", CDA)).defineDefaultRule(Rule.createDefaultRule(XXX)).build();

    /**
     * Strategy for all known mime types with default
     */
    private static final IStrategyProvider<String, MimeTypeIcon> MIME_TYPES = new StrategyProviderImpl.Builder<String, MimeTypeIcon>()

            .add(DCM.getRule()).add(DOC.getRule()).add(DOCX.getRule()).add(PDF.getRule()).add(OCTET_STREAM.getRule())
            .add(SHOCKWAVE_FLASH.getRule())

            .add(AUDIO_BASIC.getRule()).add(K32ADPCM.getRule()).add(AUDIO_MPEG.getRule())

            .add(G3FAX.getRule()).add(GIF.getRule()).add(JPEG.getRule()).add(PNG.getRule()).add(TIF.getRule())

            .add(HTML.getRule()).add(TXT.getRule()).add(RTF.getRule()).add(SGML.getRule()).add(XML.getRule())

            .add(VIDEO_MPEG.getRule()).add(AVI.getRule())

            .add(Rule.create("text/x-hl7-ft", CDA)).add(CDA.getRule())

            .defineDefaultRule(Rule.createDefaultRule(UNDEFINED)).build();

    private static final String PLACEHOLDER_SUFFIX = "-placeholder";

    private static final String PREFIX = "cui-mime-type-";

    private static String saveToUpperCase(final String value) {
        if (null != value) {
            return value.toUpperCase();
        }
        return null;
    }

    /**
     * Factory method returning a concrete {@link MimeTypeIcon}
     *
     * @param mimeTypeIdentifier if it is null or empty {@link #UNDEFINED} will be
     *                           chosen.
     * @param formatCode         if it is not null and within #XML_FORMAT_CODES it
     *                           will be directly used, identifier will be ignored
     * @return the found identifier, always defaulting to {@link #UNDEFINED}
     */
    public static MimeTypeIcon valueOfIdentifier(final String mimeTypeIdentifier, final String formatCode) {

        final var uppFormatCode = saveToUpperCase(formatCode);
        var result = HL7_TYPES.actOnCondition(uppFormatCode);
        if (XXX.equals(result)) {
            result = MIME_TYPES.actOnCondition(mimeTypeIdentifier);
        }
        return result;
    }

    /**
     * Resolves a {@link MimeTypeIcon} for a given filename
     *
     * @param fileName to be checked
     * @return {@link MimeTypeIcon#UNDEFINED} in case the given filename is null or
     *         empty or does not map to {@link MimeTypeIcon}, the found
     *         {@link MimeTypeIcon} otherwise.
     */
    public static MimeTypeIcon determineForFilenameSuffix(String fileName) {
        if (isEmpty(fileName)) {
            return UNDEFINED;
        }
        var structured = new StructuredFilename(fileName);
        if (isEmpty(structured.getSuffix())) {
            return UNDEFINED;
        }
        var suffix = structured.getSuffix().toLowerCase();
        for (MimeTypeIcon icon : values()) {
            if (icon.fileSuffixes.contains(suffix)) {
                return icon;
            }
        }
        return UNDEFINED;
    }

    @Getter
    private final List<String> fileSuffixes;

    @Getter
    private final String htmlIdentifier;

    @Getter
    private final String iconClass;

    @Getter
    private final String placeholder;

    @Getter(AccessLevel.PRIVATE)
    private final Rule<String, MimeTypeIcon> rule;

    MimeTypeIcon(String htmlIdentifier, String... fileSuffix) {
        this.htmlIdentifier = htmlIdentifier;
        if (null == fileSuffix || 0 == fileSuffix.length) {
            fileSuffixes = Collections.emptyList();
        } else {
            fileSuffixes = immutableList(fileSuffix);
        }
        final var lowerCaseName = name().toLowerCase();
        iconClass = PREFIX + lowerCaseName;
        placeholder = PREFIX + lowerCaseName + PLACEHOLDER_SUFFIX;
        rule = Rule.create(htmlIdentifier, this);
    }

    /**
     * @return boolean indicating whether the concrete enum is an image, saying one
     *         of GIF, JPEG, PNG, TIF
     */
    public boolean isImage() {
        return IMAGES.contains(this);
    }

    /**
     * @return boolean indicating whether the concrete enum is an hl7-format, saying
     *         one of CCD, CDA, CCDA, CCR, 'XXX'
     */
    public boolean isHL7Format() {
        return HL7_FORMATS.contains(this);
    }

    private static final EnumSet<MimeTypeIcon> IMAGES = EnumSet.of(GIF, JPEG, PNG, TIF);

    private static final EnumSet<MimeTypeIcon> HL7_FORMATS = EnumSet.of(CCD, CDA, CCDA, CCR, XXX);

}
