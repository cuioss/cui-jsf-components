# Task 4: Update Javadoc to Comply with Standards

This document provides detailed guidance for implementing Task 4 of the CUI JSF Components maintenance plan: updating Javadoc to comply with CUI documentation standards.

## Overview

The goal of this task is to ensure all Javadoc across the CUI JSF Components project follows the established CUI documentation standards. This includes updating class, method, and field documentation to provide clear, consistent, and comprehensive information for developers.

## Pre-conditions

- Existing Javadoc may not follow CUI documentation standards
- Documentation may be incomplete or outdated
- Javadoc formatting may be inconsistent
- Important details may be missing from documentation

## Post-conditions

- All Javadoc follows CUI documentation standards
- Documentation is complete for all public classes, methods, and interfaces
- Javadoc provides clear and useful information
- No functional code changes have been made during this task

## Javadoc Standards

The following standards should be applied to all Javadoc:

### Package Documentation

- Every package must have package-info.java
- Package documentation must describe the package purpose
- Include cross-references to related packages
- List key components and interfaces
- Provide usage examples where appropriate

### Class/Interface Documentation

- Every public and protected class/interface must be documented
- Include clear purpose statement
- Document inheritance relationships
- Specify thread-safety guarantees
- Include @since tag with version information
- Document serialization if applicable
- Include usage examples for complex classes

### Method Documentation

- Every public and protected method must be documented
- Document parameters with @param tags
- Document return values with @return tags
- Document exceptions with @throws tags
- Include usage examples for complex methods
- Document thread-safety considerations
- Use @see for related methods
- Use @deprecated with migration path if applicable

### Field Documentation

- Document all public and protected fields
- Include validation rules and constraints
- Document default values
- Use @deprecated with migration path if applicable

### Annotation Documentation

- Document purpose and applicability
- Document all elements with @param tags
- Specify default values
- Include usage examples
- Document processing requirements

### Enum Documentation

- Document the enum purpose
- Document each enum constant
- Document methods specific to the enum
- Include usage examples

### Non-public Methods and Fields

- Document only if necessary
- Use @implSpec tag for implementation details
- Include usage examples if complex
- Do not comment on trivial methods/fields
- Do not comment standard fields like serialVersionUID, LOGGER, etc.

### Avoiding "Stating the Obvious"

- Javadoc must not state the obvious or merely repeat what is evident from the method/class name
- Avoid comments like "Gets the name" for a getName() method
- Avoid trivial documentation that adds no value beyond what the signature already conveys
- Focus on documenting "why" and "how" rather than just "what"
- Include information that is not immediately apparent from the code itself
- Document constraints, side effects, and edge cases rather than restating the method name

#### Examples of "Stating the Obvious" to Avoid:

```java
/**
 * Returns the name.
 * @return the name
 */
public String getName() { return name; }
```

```java
/**
 * Sets the value.
 * @param value the value to set
 */
public void setValue(String value) { this.value = value; }
```

#### Better Alternatives:

```java
/**
 * Returns the display name of this component.
 * @return the localized display name, never null
 */
public String getName() { return name; }
```

```java
/**
 * Sets the configuration value. This triggers a refresh of dependent components.
 * @param value the configuration value, must not be null
 * @throws IllegalArgumentException if value is null
 */
public void setValue(String value) { this.value = value; }
```

### Avoiding Excessive Verbosity

When documenting classes and methods, strive for conciseness while maintaining clarity. Common issues to avoid include:

1. **Documenting Component Hierarchy**: Do not document the entire inheritance tree or component hierarchy unless it provides critical information. This can be determined by looking at the class declaration.

2. **Excessive Method Documentation**: Documentation should focus on intent, edge cases, and non-obvious behavior rather than step-by-step processes. For example:

   Too verbose:
   ```java
   /**
    * Converts a string to an object by:
    * 1. Validating the input parameters
    * 2. Checking if the string is empty
    * 3. Splitting the string by delimiter
    * 4. Converting each part to an object
    * 5. Returning the collection of objects
    */
   ```

   More appropriate:
   ```java
   /**
    * Converts a delimited string to a collection of objects.
    * Handles empty inputs by returning an empty collection.
    * @throws IllegalArgumentException if the delimiter is invalid
    */
   ```

3. **Documenting @Override Methods**: Methods that override parent class functionality generally don't need documentation unless they:
   - Change the contract of the parent method
   - Add additional restrictions or guarantees
   - Have side effects not present in the parent method
   - Fix issues or provide specific implementations for abstract methods

4. **Detailed Procedural Documentation**: Avoid documenting algorithms step-by-step in JavaDoc. Focus on the intent, constraints, and guarantees rather than implementation details.

5. **Section Headers**: Use section headers sparingly. They're useful for grouping related information in large classes, but can add unnecessary verbosity to simple components.

### Special Documentation Cases

#### Builder Classes

- Document the builder purpose
- Document each builder method
- Include complete builder usage example
- Document validation rules

#### Factory Methods

- Document factory purpose
- Document creation conditions
- Document returned instance guarantees
- Include usage examples

#### Fluent APIs

- Document method chaining pattern
- Document terminal operations
- Include complete fluent API example
- Document state changes

#### Generic Types

- Document type parameters with @param tags
- Explain type constraints
- Document wildcard usage
- Include examples with different type arguments

### Javadoc Formatting

#### HTML Formatting

- Use HTML tags sparingly
- Ensure all HTML tags are properly closed
- Use `<p>` for paragraph breaks
- Use `<code>` for inline code
- Use `<pre>` for code blocks
- Use `<ul>` and `<li>` for lists

#### Code Examples

- Use `<pre>` and `<code>` tags for code examples
- Include complete, compilable examples
- Show proper error handling
- Follow project coding standards

#### Links and References

- Use `{@link}` for references to classes, methods, and fields
- Use `{@linkplain}` for plain text links
- Use `{@code}` for inline code that is not a link
- Use `{@literal}` for text with special characters
- Verify all references exist

#### Tag Order

- @param (in parameter order)
- @return
- @throws (in alphabetical order)
- @see
- @since
- @deprecated
- @author (if applicable)
- @version (if applicable)

## Javadoc Maintenance

### Critical Constraints

- Fix ONLY Javadoc errors and warnings from build
- Do NOT alter or improve documentation content
- Do NOT modify any code
- Make minimal modifications necessary
- Focus only on formatting, references, and tags

### Common Fixes

- Fix invalid `{@link}` references
- Fix malformed HTML tags
- Fix missing/incorrect parameter documentation
- Fix missing/incorrect return value documentation
- Fix missing/incorrect exception documentation

### Out of Scope

- Documentation improvements
- Code changes
- Content rewrites
- Style changes beyond error fixes

### Common Javadoc Issues and Fixes

#### Missing Parameter Documentation

- Add @param tags for all undocumented parameters
- Use parameter name exactly as in method signature
- Add minimal description based on parameter name
- Do not modify existing parameter documentation

#### Invalid References

- Fix `{@link}` references to non-existent classes/methods
- Update references to renamed elements
- Remove references to deleted elements
- Replace with appropriate alternative references

#### HTML Formatting Issues

- Close unclosed HTML tags
- Fix malformed HTML elements
- Correct improper nesting of HTML tags
- Ensure proper escaping of special characters

#### Missing Return Documentation

- Add @return tags for undocumented return values
- Provide minimal description based on method name
- Do not modify existing return documentation
- For void methods, no @return tag is needed

#### Missing Exception Documentation

- Add @throws tags for undocumented exceptions
- Document conditions that trigger exceptions
- Do not modify existing exception documentation
- Ensure exceptions in @throws tags match method signature

## Implementation Process

For implementing this task, follow the refactoring process:

### 1. Analysis Phase

- Review current state of the module's Javadoc
- Identify gaps between current implementation and standards
- Document specific issues to be addressed

### 2. Planning Phase

- Define specific changes needed for each class
- Prioritize classes based on importance and complexity
- Estimate effort required

### 3. Implementation Phase

- Make the necessary changes to Javadoc
- Follow the pre and post conditions strictly
- Keep changes focused on the specific task
- Do not modify functional code

### 4. Verification Phase

- Run Javadoc generation to ensure no errors
- Verify that standards are now being followed
- Document any remaining issues

### Build Verification

- Identify and categorize issues:
  - Missing tags
  - Invalid references
  - Formatting problems
- Note error locations for resolution

### Documentation Analysis

- For each error:
  - Identify issue details
  - Analyze context
  - Plan minimal fix
  - Determine appropriate resolution
- Complete analysis before proceeding

### Fix Application

- For each identified issue:
  - Apply minimal fix
  - Verify no content loss
  - Run local javadoc check
  - Ensure changes address the issue
- Review all applied fixes

### Final Verification

- Run appropriate javadoc command
- If issues remain:
  - Note remaining issues
  - Return to Fix Application
- On success:
  - Verify all issues are resolved
  - Commit changes with descriptive message

## Progress Tracking Approach

### Module-Level Status

For each module, overall progress should be tracked in the module-specific maintenance document:

- **Not Started**: Task has not been initiated
- **In Progress**: Task is currently being worked on
- **Completed**: Task has been completed and verified
- **Blocked**: Task cannot proceed due to dependencies or issues

### Detailed Java File Tracking

Each module should maintain a `maintenance/code.md` file listing all Java files from the main source directory with their current status for various maintenance tasks, including Javadoc standards:

- **Analyzed**: The code has been reviewed but not yet updated
- **logging-standards**: The code has been updated to comply with logging standards
- **javadoc**: The code has been updated to comply with Javadoc standards
- **refactoring**: The code has been analyzed for refactoring opportunities

This granular tracking allows for better progress monitoring and ensures no files are overlooked during the implementation process.

## References

- [CUI Javadoc Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/documentation/javadoc-standards.adoc)
- [Javadoc Maintenance](https://github.com/cuioss/cui-llm-rules/tree/main/standards/documentation/javadoc-maintenance.adoc)
- [General Documentation Standards](https://github.com/cuioss/cui-llm-rules/tree/main/standards/documentation/general-standard.adoc)
