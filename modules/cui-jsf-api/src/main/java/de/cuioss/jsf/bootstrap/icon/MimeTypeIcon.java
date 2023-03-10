package de.cuioss.jsf.bootstrap.icon;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import de.cuioss.jsf.bootstrap.icon.strategy.IStrategyProvider;
import de.cuioss.jsf.bootstrap.icon.strategy.Rule;
import de.cuioss.jsf.bootstrap.icon.strategy.StrategyProviderImpl;
import de.cuioss.tools.io.StructuredFilename;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Each constant represent a mime type icon, defined within icons.css.<br/>
 * Currently supported mime types see https://jira.x-tention.com/browse/VMR-5009 <br/>
 *
 * @author Oliver Wolff
 * @author i000576 (Eugen Fischer)
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
    private static final IStrategyProvider<String, MimeTypeIcon> HL7_TYPES =
        new StrategyProviderImpl.Builder<String, MimeTypeIcon>()
                .add(Rule.create("CCD", CCD))
                .add(Rule.create("CCDA", CCDA))
                .add(Rule.create("CCR", CCR))
                .add(Rule.create("CDA", CDA))
                .defineDefaultRule(Rule.createDefaultRule(XXX))
                .build();

    /**
     * Strategy for all known mime types with default
     */
    private static final IStrategyProvider<String, MimeTypeIcon> MIME_TYPES =
        new StrategyProviderImpl.Builder<String, MimeTypeIcon>()

                .add(DCM.getRule())
                .add(DOC.getRule()).add(DOCX.getRule())
                .add(PDF.getRule())
                .add(OCTET_STREAM.getRule())
                .add(SHOCKWAVE_FLASH.getRule())

                .add(AUDIO_BASIC.getRule())
                .add(K32ADPCM.getRule())
                .add(AUDIO_MPEG.getRule())

                .add(G3FAX.getRule())
                .add(GIF.getRule())
                .add(JPEG.getRule())
                .add(PNG.getRule())
                .add(TIF.getRule())

                .add(HTML.getRule())
                .add(TXT.getRule())
                .add(RTF.getRule())
                .add(SGML.getRule())
                .add(XML.getRule())

                .add(VIDEO_MPEG.getRule())
                .add(AVI.getRule())

                .add(Rule.create("text/x-hl7-ft", CDA))
                .add(CDA.getRule())

                .defineDefaultRule(Rule.createDefaultRule(UNDEFINED))
                .build();

    private static final String PLACEHOLDER_SUFFIX = "-placeholder";

    private static final String PREFIX = "cui-mime-type-";

    private static String saveToUpperCase(final String value) {
        if (null != value) {
            return value.toUpperCase();
        }
        return value;
    }

    /**
     * Factory method returning a concrete {@link MimeTypeIcon}
     *
     * @param mimeTypeIdentifier if it is null or empty {@link #UNDEFINED} will be chosen.
     * @param formatCode if it is not null and within #XML_FORMAT_CODES it will be directly
     *            used, identifier will be ignored
     * @return the found identifier, always defaulting to {@link #UNDEFINED}
     */
    public static final MimeTypeIcon valueOfIdentifier(final String mimeTypeIdentifier,
            final String formatCode) {

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
     * @return {@link MimeTypeIcon#UNDEFINED} in case the given filename is null or empty or does
     *         not map to {@link MimeTypeIcon}, the found {@link MimeTypeIcon} otherwise.
     */
    public static final MimeTypeIcon determineForFilenameSuffix(String fileName) {
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
        iconClass = new StringBuilder(PREFIX).append(lowerCaseName).toString();
        placeholder =
            new StringBuilder(PREFIX).append(lowerCaseName).append(PLACEHOLDER_SUFFIX).toString();
        rule = Rule.create(htmlIdentifier, this);
    }

    /**
     * @return boolean indicating whether the concrete enum is an image, saying one of GIF, JPEG,
     *         PNG, TIF
     */
    public boolean isImage() {
        return IMAGES.contains(this);
    }

    /**
     * @return boolean indicating whether the concrete enum is an hl7-format, saying one of CCD,
     *         CDA, CCDA, CCR, 'XXX'
     */
    public boolean isHL7Format() {
        return HL7_FORMATS.contains(this);
    }

    private static final EnumSet<MimeTypeIcon> IMAGES = EnumSet.of(GIF, JPEG, PNG, TIF);

    private static final EnumSet<MimeTypeIcon> HL7_FORMATS = EnumSet.of(CCD, CDA, CCDA, CCR, XXX);

}
