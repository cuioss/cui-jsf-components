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
package de.cuioss.jsf.bootstrap.common.logging;

import de.cuioss.tools.logging.LogRecord;
import de.cuioss.tools.logging.LogRecordModel;

/**
 * Defines the log messages for the JSF Bootstrap module.
 * <p>
 * The module prefix is "JSF_BOOTSTRAP".
 *
 * <p>
 * The identifiers are structured as follows:
 * <ul>
 * <li>001-099: INFO level</li>
 * <li>100-199: WARN level</li>
 * <li>200-299: ERROR level</li>
 * <li>300-399: FATAL level</li>
 * </ul>
 *
 * @since 1.0
 */
public final class BootstrapLogMessages {

    private BootstrapLogMessages() {
        throw new UnsupportedOperationException("This utility class must not be instantiated");
    }

    /**
     * The prefix for all log messages of this module.
     */
    public static final String PREFIX = "JSF_BOOTSTRAP";

    /**
     * Container for INFO level log messages (identifiers 001-099).
     *
     * @since 1.0
     */
    public static final class INFO {

        private INFO() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Log message when no children or content is defined for help text display.
         */
        public static final LogRecord NO_HELP_TEXT_CONTENT = LogRecordModel.builder()
                .template("Neither children or a content is defined to be displayed as help text.")
                .prefix(PREFIX)
                .identifier(1)
                .build();

        /**
         * Log message when a LabeledContainer does not contain an input component.
         */
        public static final LogRecord NO_INPUT_COMPONENT = LogRecordModel.builder()
                .template("LabeledContainer '%s' does not contain an input component with id '%s' and is not configured for complex output. Please check if you want to render an input element and did not adapt the id of this element. If you want to use it for output text, you can ignore this message")
                .prefix(PREFIX)
                .identifier(2)
                .build();
    }

    /**
     * Container for WARN level log messages (identifiers 100-199).
     *
     * @since 1.0
     */
    public static final class WARN {

        private WARN() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Log message for facelet include failures in dashboard.
         */
        public static final LogRecord FACELET_INCLUDE_FAILED = LogRecordModel.builder()
                .template("include facet failed: %s")
                .prefix(PREFIX)
                .identifier(100)
                .build();

        /**
         * Log message for invalid client behavior configuration on tag component.
         */
        public static final LogRecord INVALID_CLIENT_BEHAVIOR_CONFIG = LogRecordModel.builder()
                .template("Invalid configuration: In order to use a client-behavior you need to set disposable=true, clientid='%s'")
                .prefix(PREFIX)
                .identifier(101)
                .build();
    }

    /**
     * Container for ERROR level log messages (identifiers 200-299).
     *
     * @since 1.0
     */
    public static final class ERROR {

        private ERROR() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Log message when a component requires a form parent but is not within one.
         */
        public static final LogRecord COMPONENT_REQUIRES_FORM = LogRecordModel.builder()
                .template("The UI component '%s' needs to have a form tag in its ancestry.")
                .prefix(PREFIX)
                .identifier(200)
                .build();

        /**
         * Log message for invalid facet name resolution.
         */
        public static final LogRecord INVALID_FACET_NAME = LogRecordModel.builder()
                .template("Invalid name given, expected one of '%s' (Case Insensitive)")
                .prefix(PREFIX)
                .identifier(201)
                .build();

        /**
         * Log message for invalid component type in converter.
         */
        public static final LogRecord INVALID_COMPONENT_TYPE = LogRecordModel.builder()
                .template("Component must be of type %s")
                .prefix(PREFIX)
                .identifier(202)
                .build();

        /**
         * Log message for hex decoding failures.
         */
        public static final LogRecord HEX_DECODE_ERROR = LogRecordModel.builder()
                .template("Could not decode: %s")
                .prefix(PREFIX)
                .identifier(203)
                .build();

        /**
         * Log message when unable to match an element by name.
         */
        public static final LogRecord UNABLE_TO_MATCH_ELEMENT = LogRecordModel.builder()
                .template("Unable to match element with name: %s")
                .prefix(PREFIX)
                .identifier(204)
                .build();

        /**
         * Log message for invalid input values.
         */
        public static final LogRecord INVALID_INPUT_VALUE = LogRecordModel.builder()
                .template("Invalid input value found: %s")
                .prefix(PREFIX)
                .identifier(205)
                .build();
    }
}
