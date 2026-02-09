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
package de.cuioss.jsf.dev.common.logging;

import de.cuioss.tools.logging.LogRecord;
import de.cuioss.tools.logging.LogRecordModel;

/**
 * Defines the log messages for the JSF Dev module.
 * <p>
 * The module prefix is "JSF_DEV".
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
public final class DevLogMessages {

    private DevLogMessages() {
        throw new UnsupportedOperationException("This utility class must not be instantiated");
    }

    /**
     * The prefix for all log messages of this module.
     */
    public static final String PREFIX = "JSF_DEV";

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
         * Log message when a resource path resolves to an unexpected value.
         */
        public static final LogRecord RESOURCE_PATH_RESOLVED = LogRecordModel.builder()
                .template("Resource path resolved to: %s")
                .prefix(PREFIX)
                .identifier(100)
                .build();

        /**
         * Log message when no relative path is found for a source code reference.
         */
        public static final LogRecord NO_RELATIVE_PATH = LogRecordModel.builder()
                .template("No relative path found for '%s'")
                .prefix(PREFIX)
                .identifier(101)
                .build();

        /**
         * Log message when multiple elements are found for an ID.
         */
        public static final LogRecord MULTIPLE_ELEMENTS_FOUND = LogRecordModel.builder()
                .template("More than one element found on view='%s' with id='%s' found, choosing the first one")
                .prefix(PREFIX)
                .identifier(102)
                .build();

        /**
         * Log message when no view resource is found.
         */
        public static final LogRecord NO_VIEW_FOUND = LogRecordModel.builder()
                .template("No view found, candidates='%s")
                .prefix(PREFIX)
                .identifier(103)
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
         * Log message for IO errors when reading sample source.
         */
        public static final LogRecord SAMPLE_SOURCE_IO_ERROR = LogRecordModel.builder()
                .template("Sample source not found in %s")
                .prefix(PREFIX)
                .identifier(200)
                .build();

        /**
         * Log message for parser configuration errors.
         */
        public static final LogRecord PARSER_CONFIG_ERROR = LogRecordModel.builder()
                .template("Parser configuration exception in sample source of component %s")
                .prefix(PREFIX)
                .identifier(201)
                .build();

        /**
         * Log message for XML parser exceptions.
         */
        public static final LogRecord PARSER_EXCEPTION = LogRecordModel.builder()
                .template("Parser exception in %s")
                .prefix(PREFIX)
                .identifier(202)
                .build();
    }
}
