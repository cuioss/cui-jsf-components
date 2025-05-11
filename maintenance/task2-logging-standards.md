# Task 2: Update Production Code to Logging Standards

This document provides detailed guidance for implementing Task 2 of the CUI JSF Components maintenance plan: updating production code to comply with CUI logging standards.

## Overview

The goal of this task is to ensure all logging in production code across the CUI JSF Components project follows the established CUI logging standards. This includes updating logger implementations, log message formats, and logging levels to align with best practices.

## Important Note on Code Changes

When adapting code to these logging standards:

* Only modify logging-related code (imports, log statements, assertions)
* DO NOT change any unrelated production or test code
* DO NOT refactor or "improve" other aspects while implementing logging changes
* Keep changes focused solely on logging standard compliance

## Pre-conditions

- Logging implementation may not follow CUI logging standards
- Logger instances may not use the recommended CuiLogger
- Log messages may not use the recommended format
- Log levels may be inconsistent
- Exception handling in logging may not follow best practices

## Post-conditions

- All logging follows CUI logging standards
- CuiLogger is used consistently throughout the modules
- Log messages follow the recommended format
- Log levels are used appropriately
- Exception handling in logging follows best practices
- No other aspects of the production code have been modified

## Logging Standards

The following standards should be applied to all logging in production code:

### Logger Configuration

#### Required Setup

* Use `de.cuioss.tools.logging.CuiLogger` with constant name 'LOGGER'
* Logger must be private static final
* Module/artifact: cui-java-tools

```java
private static final CuiLogger LOGGER = new CuiLogger(YourClass.class);
```

#### Prohibited Practices

* No log4j or slf4j usage
* No System.out or System.err - use appropriate logger level
* No direct logger instantiation

### Logging Standards

#### Method Requirements

* Exception parameter always comes first
* Use '%s' for string substitutions (not '{}')
* Use `de.cuioss.tools.logging.LogRecord` for template logging

```java
// With exception:
LOGGER.error(exception, ERROR.DATABASE_CONNECTION.format(url));

// With parameters:
LOGGER.info(INFO.USER_LOGIN.format(username));
```

### LogRecord Usage

#### Core Requirements

* Use LogRecord API for structured logging
* Use LogRecord#format for parameterized messages
* Required for INFO/WARN/ERROR/FATAL in production
* Use LogRecord#resolveIdentifierString() for testing

#### Module Organization

* Aggregate LogRecords in module-specific 'LogMessages'
* Create unique module prefix (e.g., "Portal", "Authentication")
* Store prefix as constant in LogMessages

#### Message Identifiers

* 001-99: INFO level
* 100-199: WARN level
* 200-299: ERROR level
* 300-399: FATAL level
* 500-599: DEBUG level (optional)
* 600-699: TRACE level (optional)

### LogMessages Implementation

#### Class Structure and Organization

* Follow the DSL-Style Constants Pattern
* Import category level constant, NOT its members
* Create final utility class
* Name pattern: [Module][Component]LogMessages
* Place in module's root package
* Define module-specific prefix constant

```java
@UtilityClass
public final class PortalCoreLogMessages {
    public static final String PREFIX = "PORTAL_CORE";

    @UtilityClass
    public static final class INFO {
        public static final LogRecord USER_LOGIN = LogRecordModel.builder()
            .template("User %s logged in successfully")
            .prefix(PREFIX)
            .identifier(1)
            .build();
    }

    @UtilityClass
    public static final class WARN {
        public static final LogRecord USER_NOT_LOGGED_IN = LogRecordModel.builder()
            .template("User not logged in for protected resource")
            .prefix(PREFIX)
            .identifier(100)
            .build();
    }

    @UtilityClass
    public static final class ERROR {
        public static final LogRecord REQUEST_PROCESSING_ERROR = LogRecordModel.builder()
            .template("Error processing request: %s")
            .prefix(PREFIX)
            .identifier(200)
            .build();
    }
}
```

#### Import and Usage Pattern

```java
// CORRECT:
import static de.cuioss.portal.core.PortalCoreLogMessages.INFO;

// Then use:
INFO.USER_LOGIN
WARN.USER_NOT_LOGGED_IN

// INCORRECT - DO NOT:
import static de.cuioss.portal.core.PortalCoreLogMessages.INFO.*;
import static de.cuioss.portal.core.PortalCoreLogMessages.*;
```

#### LogRecord Usage Patterns

##### Mandatory LogRecord Usage (INFO/WARN/ERROR/FATAL)

LogRecord MUST be used for INFO/WARN/ERROR/FATAL levels in production code. Direct logging is NOT allowed for these levels:

```java
// CORRECT:
LOGGER.info(INFO.USER_LOGIN.format(username));
LOGGER.error(e, ERROR.DATABASE_CONNECTION.format(url));

// INCORRECT - Never use direct logging for INFO/WARN/ERROR/FATAL:
logger.info("User %s logged in", username);
logger.error(e, "Database connection failed: %s", url);
```

##### Forbidden LogRecord Usage (DEBUG/TRACE)

LogRecord MUST NOT be used for DEBUG/TRACE levels. These levels MUST use direct logging:

```java
// CORRECT:
LOGGER.debug("Processing file %s", filename);
LOGGER.trace(e, "Detailed error info: %s", e.getMessage());

// INCORRECT - Never use LogRecord for DEBUG/TRACE:
LOGGER.debug(DEBUG.SOME_DEBUG_MESSAGE.format());
LOGGER.trace(TRACE.SOME_TRACE_MESSAGE.format());
```

##### Parameter Handling

* For LogRecords (INFO/WARN/ERROR/FATAL): Use format method
```java
LOGGER.info(INFO.SOME_MESSAGE.format(param1, param2));
```

* For Direct Logging (DEBUG/TRACE): Use '%s' for parameter substitution
```java
LOGGER.debug("Processing file %s with size %s", filename, size);
```

##### Exception Handling

* For LogRecords (INFO/WARN/ERROR/FATAL):
```java
LOGGER.error(e, ERROR.CANNOT_GENERATE_CODE_CHALLENGE.format());
LOGGER.error(e, ERROR.SOME_ERROR.format(param1));
```

* For Direct Logging (DEBUG/TRACE):
```java
LOGGER.debug(e, "Detailed error info: %s", e.getMessage());
```

##### Parameter-Free Calls

For LogRecords without parameters, use method reference syntax:

```java
// CORRECT:
LOGGER.info(INFO.STARTUP_COMPLETE::format);

// INCORRECT:
LOGGER.info(INFO.STARTUP_COMPLETE.format());
```

### Documentation Requirements

#### doc/LogMessages.adoc Format

The documentation must be maintained in `doc/LogMessages.adoc` for each module and must follow this format:

```asciidoc
= Log Messages for [Module Name]
:toc: left
:toclevels: 2

== Overview

All messages follow the format: [Module-Prefix]-[identifier]: [message]

== INFO Level (001-099)

[cols="1,1,2,2", options="header"]
|===
|ID |Component |Message |Description
|PortalAuth-001 |AUTH |User '%s' successfully logged in |Logged when a user successfully authenticates
|PortalAuth-002 |AUTH |User '%s' logged out |Logged when a user logs out of the system
|===

== WARN Level (100-199)

[cols="1,1,2,2", options="header"]
|===
|ID |Component |Message |Description
|PortalAuth-100 |AUTH |Login failed for user '%s' |Logged when a login attempt fails
|===

== ERROR Level (200-299)

[cols="1,1,2,2", options="header"]
|===
|ID |Component |Message |Description
|PortalAuth-200 |AUTH |Authentication error occurred: %s |Logged when a system error occurs
|===
```

#### Documentation Rules

1. Every LogMessages class must have a corresponding documentation file at `doc/LogMessages.adoc`
2. Documentation must be updated whenever log messages are modified
3. Documentation must exactly match the implementation - this is a success criterion
4. Messages must be organized in separate tables by log level, with level ranges in headers:
   * INFO Level (001-099)
   * WARN Level (100-199)
   * ERROR Level (200-299)
   * FATAL Level (300-399)
5. Include all metadata:
   * Full identifier with module prefix
   * Module/component name
   * Exact message template
   * Clear description of when the message is used
6. Other Level like debug or trace are not to be documented that way

## Best Practices

### Message Organization

* Group related messages under meaningful component names
* Use consistent naming across the module
* Keep hierarchy depth at exactly 4 levels
* Follow the DSL-Style Constants Pattern

### Message Templates

* Use clear, consistent language
* Include all necessary context
* Use '%s' for all parameter placeholders
* Keep messages concise but informative

### Identifier Management

* Assign IDs sequentially within ranges
* Document all IDs in LogMessage.adoc
* Verify no duplicate IDs within module
* Follow level-specific ranges

## Implementation Process

For implementing this task, follow the refactoring process:

### 1. Analysis Phase

- Review current state of the module's logging
- Identify gaps between current implementation and standards
- Document specific issues to be addressed

### 2. Planning Phase

- Define specific changes needed for each class
- Prioritize classes based on importance and complexity
- Estimate effort required

### 3. Implementation Phase

- Make the necessary changes to logging implementation
- Follow the pre and post conditions strictly
- Keep changes focused on the specific task
- Do not modify other aspects of the production code

### 4. Verification Phase

- Run tests to ensure functionality is preserved
- Verify that standards are now being followed
- Document any remaining issues

## Success Criteria

### Logger Configuration

* Only CuiLogger is used
* Logger is private static final
* No prohibited logging frameworks

### Implementation

* All log messages use LogRecord
* Message identifiers follow level ranges
* DSL-Style pattern is followed
* Imports are correct
* On validating you must ensure, that there is no "dangling" LogRecords, saying each type must be used. If it is not used, analyze the codebase, whether it has somewhere to be used. Remove it otherwise. Remove it from doc/LogMessages.adoc as well

### Documentation

* doc/LogMessages.adoc exists for each module
* All messages are documented
* Format matches specification
* IDs and messages match implementation

### Testing

* All INFO/WARN/ERROR/FATAL messages have tests
* Tests use cui-test-juli-logger
* Assertions follow standard patterns

## Progress Tracking Approach

### Module-Level Status

For each module, overall progress should be tracked in the module-specific maintenance document:

- **Not Started**: Task has not been initiated
- **In Progress**: Task is currently being worked on
- **Completed**: Task has been completed and verified
- **Blocked**: Task cannot proceed due to dependencies or issues

### Detailed Java File Tracking

Each module should maintain a `maintenance/code.md` file listing all Java files from the main source directory with their current status for various maintenance tasks, including logging standards:

- **Analyzed**: The code has been reviewed but not yet updated
- **logging-standards**: The code has been updated to comply with logging standards
- **javadoc**: The code has been updated to comply with Javadoc standards
- **refactoring**: The code has been analyzed for refactoring opportunities

This granular tracking allows for better progress monitoring and ensures no files are overlooked during the implementation process.

## References

- [CUI Logging Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging)
- [Logging Core Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging/core-standards.adoc)
- [Logging Implementation Guide](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging/implementation-guide.adoc)
