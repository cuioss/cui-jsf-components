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
package de.cuioss.jsf.api.common.logging;

import de.cuioss.tools.logging.LogRecord;
import de.cuioss.tools.logging.LogRecordModel;

/**
 * Defines the log messages for the JSF API module.
 * <p>
 * The module prefix is "JSF_API".
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
 * <p>
 * This class is designed as a utility class with static constants and cannot be instantiated.
 * It is thread-safe as it contains only static final fields and methods.
 *
 * @since 1.0
 */
public final class JsfApiLogMessages {

    /**
     * Common message text used for log messages indicating that an error occurred but was handled silently.
     * This constant is used by multiple log records across different log levels.
     */
    public static final String ERROR_OCCURRED_BUT_WAS_HANDLED_SILENT = "Error occurred but was handled silent.";

    /**
     * Private constructor to prevent instantiation of this utility class.
     * This class is designed to provide static constants only and should not be instantiated.
     *
     * @throws UnsupportedOperationException if called
     */
    private JsfApiLogMessages() {
        throw new UnsupportedOperationException("This utility class must not be instantiated");
    }

    /**
     * The prefix for all log messages of this module.
     */
    public static final String PREFIX = "JSF_API";

    /**
     * Container for INFO level log messages (identifiers 001-099).
     * <p>
     * This class provides constants for all INFO level log messages used in the JSF API module.
     * Each constant is a {@link LogRecord} that can be used with the {@link de.cuioss.tools.logging.CuiLogger}.
     *
     * @since 1.0
     */
    public static final class INFO {

        /**
         * Private constructor to prevent instantiation of this utility class.
         *
         * @throws UnsupportedOperationException if called
         */
        private INFO() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Silent error message for INFO level.
         */
        public static final LogRecord SILENT_ERROR = LogRecordModel.builder()
                .template("Silent error occurred")
                .prefix(PREFIX)
                .identifier(1)
                .build();

        /**
         * Log message for CUI version information.
         */
        public static final LogRecord VERSION_INFO = LogRecordModel.builder()
                .template("Running on %s ( Version : %s )")
                .prefix(PREFIX)
                .identifier(2)
                .build();

        /**
         * Log message for when an error occurred but was handled silently.
         */
        public static final LogRecord ERROR_HANDLED_SILENT = LogRecordModel.builder()
                .template(ERROR_OCCURRED_BUT_WAS_HANDLED_SILENT)
                .prefix(PREFIX)
                .identifier(3)
                .build();
    }


    /**
     * Container for WARN level log messages (identifiers 100-199).
     * <p>
     * This class provides constants for all WARN level log messages used in the JSF API module.
     * Each constant is a {@link LogRecord} that can be used with the {@link de.cuioss.tools.logging.CuiLogger}.
     *
     * @since 1.0
     */
    public static final class WARN {

        /**
         * Private constructor to prevent instantiation of this utility class.
         *
         * @throws UnsupportedOperationException if called
         */
        private WARN() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Silent error message for WARN level.
         */
        public static final LogRecord SILENT_ERROR = LogRecordModel.builder()
                .template("Silent warning occurred")
                .prefix(PREFIX)
                .identifier(100)
                .build();

        /**
         * Log message for when response is already committed and redirect cannot be performed.
         */
        public static final LogRecord RESPONSE_ALREADY_COMMITTED = LogRecordModel.builder()
                .template("Unable to redirect, response already committed.")
                .prefix(PREFIX)
                .identifier(101)
                .build();

        /**
         * Log message for unexpected environment where request is not of type HttpServletRequest.
         */
        public static final LogRecord UNEXPECTED_ENVIRONMENT = LogRecordModel.builder()
                .template("Unexpected environment. %s is not of type javax.servlet.http.HttpServletRequest. This call therefore returns null")
                .prefix(PREFIX)
                .identifier(102)
                .build();

        /**
         * Log message for when an error occurred but was handled silently.
         */
        public static final LogRecord ERROR_HANDLED_SILENT = LogRecordModel.builder()
                .template(ERROR_OCCURRED_BUT_WAS_HANDLED_SILENT)
                .prefix(PREFIX)
                .identifier(103)
                .build();

        /**
         * Log message for ActiveIndexManager evaluation failures.
         */
        public static final LogRecord ACTIVE_INDEX_MANAGER_ERROR = LogRecordModel.builder()
                .template("Could not evaluate accordions active indexes from ActiveIndexManager. Returning list with entry {0}.")
                .prefix(PREFIX)
                .identifier(104)
                .build();

        /**
         * Log message for text not correctly escaped or sanitized.
         */
        public static final LogRecord TEXT_NOT_SANITIZED = LogRecordModel.builder()
                .template("Text not correct escaped or sanitized: '%s' in %s (component id %s)")
                .prefix(PREFIX)
                .identifier(105)
                .build();
    }

    /**
     * Container for ERROR level log messages (identifiers 200-299).
     * <p>
     * This class provides constants for all ERROR level log messages used in the JSF API module.
     * Each constant is a {@link LogRecord} that can be used with the {@link de.cuioss.tools.logging.CuiLogger}.
     *
     * @since 1.0
     */
    public static final class ERROR {

        /**
         * Private constructor to prevent instantiation of this utility class.
         *
         * @throws UnsupportedOperationException if called
         */
        private ERROR() {
            throw new UnsupportedOperationException("This utility class must not be instantiated");
        }

        /**
         * Silent error message for ERROR level.
         */
        public static final LogRecord SILENT_ERROR = LogRecordModel.builder()
                .template("Silent error occurred")
                .prefix(PREFIX)
                .identifier(200)
                .build();

        /**
         * Log message for when an error occurred but was handled silently.
         */
        public static final LogRecord ERROR_HANDLED_SILENT = LogRecordModel.builder()
                .template(ERROR_OCCURRED_BUT_WAS_HANDLED_SILENT)
                .prefix(PREFIX)
                .identifier(201)
                .build();

        /**
         * Log message for converter instantiation failures.
         */
        public static final LogRecord CONVERTER_INSTANTIATION_FAILED = LogRecordModel.builder()
                .template("Unable to instantiate converter for %s, due to %s")
                .prefix(PREFIX)
                .identifier(202)
                .build();
    }
}
