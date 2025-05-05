# Task 2: Update Production Code to Logging Standards

This document provides detailed guidance for implementing Task 2 of the CUI JSF Components maintenance plan: updating production code to comply with CUI logging standards.

## Overview

The goal of this task is to ensure all logging in production code across the CUI JSF Components project follows the established CUI logging standards. This includes updating logger implementations, log message formats, and logging levels to align with best practices.

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

### Logger Implementation

- Use `de.cuioss.tools.logging.CuiLogger` (private static final LOGGER)
- Use LogRecord API for structured logging with dedicated message constants
- Exception parameter always comes first in logging methods
- Use '%s' for string substitutions (not '{}')

### Log Levels and Message Structure

- Follow logging level ranges:
  - INFO (001-99): Informational messages about normal application flow
  - WARN (100-199): Warning messages about potential issues
  - ERROR (200-299): Error messages about recoverable errors
  - FATAL (300-399): Fatal messages about unrecoverable errors
- Use CuiLogger.error(exception, ERROR.CONSTANT.format(param)) pattern
- All log messages must be documented in doc/LogMessages.md

### Message Constants

- Use DSL-style nested constants for logging messages
- Organize constants by log level
- Include message code in constant name
- Provide descriptive constant names

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

## Progress Tracking

For each module, progress should be tracked in the module-specific maintenance document:

- **Not Started**: Task has not been initiated
- **In Progress**: Task is currently being worked on
- **Completed**: Task has been completed and verified
- **Blocked**: Task cannot proceed due to dependencies or issues

## References

- [CUI Logging Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging)
- [Logging Core Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging/core-standards.adoc)
- [Logging Implementation Guide](https://github.com/cuioss/cui-llm-rules/tree/main/standards/logging/implementation-guide.adoc)