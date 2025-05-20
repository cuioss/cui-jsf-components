# Task 1: Update Unit Tests to Comply with Standards

This document provides comprehensive guidance for implementing Task 1 of the CUI JSF Components maintenance plan: updating unit tests to comply with CUI testing standards. It references the official CUI testing standards and provides specific implementation guidelines for this task.

## Overview

The goal of this task is to ensure all unit tests across the CUI JSF Components project follow the established CUI testing standards. This includes:

- Updating test structure and organization
- Implementing proper JUnit 5 annotations and features
- Ensuring consistent assertion patterns
- Improving test documentation and naming
- Achieving adequate test coverage

## Pre-conditions and Post-conditions

### Pre-conditions

- Existing unit tests may not fully comply with testing standards
- Test coverage may be insufficient (below 80%)
- Test naming conventions may be inconsistent
- Tests may not use the recommended JUnit 5 features
- Mocking and stubbing approaches may vary across the codebase

### Post-conditions

- All unit tests follow the CUI testing standards
- Tests use JUnit 5 features consistently
- Test coverage meets or exceeds 80%
- Tests are well-organized and follow consistent patterns
- No production code has been modified during this task

## Progress Tracking Approach

### Detailed Test Class Tracking

Each module should maintain a `maintenance/unit-tests.md` file listing all test classes with their current status:

- **Analyzed**: The test has been reviewed but not yet updated
- **Completed**: The test has been updated to comply with standards
- **NoChanges**: The test already complies with standards or no changes are needed

This granular tracking allows for better progress monitoring and ensures no tests are overlooked during the implementation process.

## CUI Testing Standards Reference

This section summarizes the key testing standards that must be applied when updating unit tests. For complete details, refer to the official CUI testing standards documents.

### Standards Documents

- [Testing Standards README](../maintenance/standards/testing/README.adoc) - Overview of testing standards
- [Core Standards](../maintenance/standards/testing/core-standards.adoc) - Fundamental testing standards and requirements
- [Quality Standards](../maintenance/standards/testing/quality-standards.adoc) - Standards for test quality and coverage
- [Logging Testing Guide](../maintenance/standards/logging/testing-guide.adoc) - Standards for testing logging implementations
- [Using Generators](../maintenance/standards/testing/Using%20Generators.adoc) - Guide for test data generation

### Key Testing Principles

The four foundational principles that should guide all test implementations:

1. **Comprehensive Coverage**: All code should have appropriate test coverage
2. **Maintainability**: Tests should be easy to understand and maintain
3. **Reliability**: Tests should be deterministic and not flaky
4. **Efficiency**: Tests should run quickly and use resources efficiently

### Essential Testing Requirements

#### Test Structure and Organization

- **Arrange-Act-Assert Pattern**: All tests must follow this structure
  - Arrange: Set up test data and conditions
  - Act: Execute the code being tested
  - Assert: Verify the expected outcomes
- **Nested Test Classes**: Use `@Nested` to organize related tests
- **Descriptive Naming**: Use clear, descriptive names for test methods and classes
- **Test Independence**: Tests must not depend on each other or execution order

#### Test Coverage Requirements

- All public methods must have unit tests
- Edge cases and error conditions must be tested
- Test coverage should meet or exceed 80% line coverage
- Critical paths must have 100% coverage

#### JUnit 5 Features

- Use `@DisplayName` for human-readable test descriptions
- Implement `@Nested` classes for logical test organization
- Apply appropriate lifecycle annotations (`@BeforeEach`, `@AfterEach`, etc.)
- Use parameterized tests for testing multiple scenarios efficiently

### Parameterized Tests Implementation Guide

Parameterized tests are a powerful feature of JUnit 5 that allow testing multiple scenarios with a single test method. This section provides guidelines for implementing effective parameterized tests.

#### General Best Practices

- **Minimum Test Cases**: Use parameterized tests only when you have at least 3 test cases
  - For fewer cases, use separate test methods instead
  - Convert existing parameterized tests with fewer than 3 cases to direct methods

- **Test Case Naming**:
  - Use the `name` parameter in `@ParameterizedTest` for descriptive test names
  - Include parameter values in the name template where appropriate
  - Example: `@ParameterizedTest(name = "Testing {0} with value {1}")`

#### Test Data Generation Approaches

##### 1. Using Generators Framework (Recommended)

The CUI test-generator framework provides robust and reproducible test data generation:

```java
// Enable reproducible test data generation
@EnableGeneratorController
class MyGeneratorTest {

    // Basic string generator with size parameters
    @ParameterizedTest
    @GeneratorsSource(
        generator = GeneratorType.STRINGS,
        minSize = 3,
        maxSize = 10,
        count = 5
    )
    void testWithStringGenerator(String value) {
        assertNotNull(value);
        assertTrue(value.length() >= 3 && value.length() <= 10);
    }

    // Number generator with range
    @ParameterizedTest
    @GeneratorsSource(
        generator = GeneratorType.INTEGERS,
        low = "1",
        high = "100",
        count = 5
    )
    void testWithIntegerGenerator(Integer value) {
        assertNotNull(value);
        assertTrue(value >= 1 && value <= 100);
    }

    // Using with fixed seed for reproducible tests
    @ParameterizedTest
    @GeneratorSeed(42L)
    @GeneratorsSource(
        generator = GeneratorType.STRINGS,
        count = 3
    )
    void testWithSpecificSeed(String value) {
        // This test will always generate the same values
        assertNotNull(value);
    }
}
```

##### 2. Using TypeGenerator Directly

For more control over test data generation:

```java
@EnableGeneratorController
class TypeGeneratorTest {

    // Using generator class directly
    @ParameterizedTest
    @TypeGeneratorSource(NonBlankStringGenerator.class)
    void testWithGeneratedStrings(String value) {
        assertNotNull(value);
        assertFalse(value.isBlank());
    }

    // Using Generators directly as factory (main use-case)
    @ParameterizedTest
    @TypeGeneratorMethodSource("createRangeGenerator")
    void testWithRangeGenerator(Integer value) {
        assertNotNull(value);
        assertTrue(value >= 1 && value <= 100);
    }

    // Generator method that directly uses Generators
    static TypedGenerator<Integer> createRangeGenerator() {
        return Generators.integers(1, 100); // Integers between 1-100
    }

    // Composite generators for multiple parameters
    @ParameterizedTest
    @CompositeTypeGeneratorSource(
        generators = {
            GeneratorType.NON_EMPTY_STRINGS,
            GeneratorType.INTEGERS
        },
        count = 3
    )
    void testWithMultipleGenerators(String text, Integer number) {
        assertNotNull(text);
        assertNotNull(number);
    }
}
```

##### 3. Method Source (Use as Last Resort)

When generators aren't suitable, use method sources with these guidelines:

- Method Source should be the last resort for test data generation
- Keep method sources simple and focused on providing test data only
- Use descriptive method names for test case providers
- Document each test case's purpose in the provider method
- Return `Stream<Arguments>` with clear parameter descriptions

```java
@ParameterizedTest
@MethodSource("validEmailProvider")
void shouldValidateEmails(String email, boolean expected) {
    // Test implementation
}

// Well-documented method source
static Stream<Arguments> validEmailProvider() {
    return Stream.of(
        // Standard valid email
        Arguments.of("user@example.com", true),
        // Email with subdomain
        Arguments.of("user@sub.example.com", true),
        // Invalid email (missing domain)
        Arguments.of("user@", false)
    );
}
```

For more detailed information on test data generation, refer to the [Using Generators](../maintenance/standards/testing/Using%20Generators.adoc) guide.

## Implementation Process

This section outlines the systematic approach for implementing Task 1. Follow this structured process to ensure consistent and thorough updates to all test classes.

### 0. Prerequisites

Before starting the refactoring of JUnit tests, you must first migrate the cui-jsf-test-basic tests according to the [migration guide](cui-jsf-test-basic/migration.adoc). This migration involves:

- Moving from the legacy `JsfEnvironmentConsumer` approach to the new parameter resolution approach
- Updating test classes that implement `JsfEnvironmentConsumer` or extend `JsfEnabledTestEnvironment`
- Replacing configurator interfaces with parameter resolution
- Updating navigation assertions to use the new `NavigationAsserts` parameter

This prerequisite step is essential as it ensures all JSF tests use the modern JUnit 5 parameter resolution capabilities, making them more concise, readable, and maintainable before proceeding with other test refactoring tasks.

### 1. Analysis Phase

- Review current state of the module's tests
- Identify gaps between current implementation and standards
- Create or update the module's `maintenance/unit-tests.md` file with a complete list of test classes
- Mark each test class as **Analyzed** after reviewing it
- Document specific issues to be addressed for each test class
- Assess test coverage and identify areas needing improvement

#### Analysis Checklist

- [ ] JUnit 5 annotations usage (`@Test`, `@DisplayName`, `@Nested`, etc.)
- [ ] Test organization (nested classes, logical grouping)
- [ ] Naming conventions (descriptive method names)
- [ ] Assertion patterns (specific assertions, error messages)
- [ ] Test data generation approaches
- [ ] Test coverage (line coverage, branch coverage)
- [ ] Documentation quality

### 2. Planning Phase

- Define specific changes needed for each test class based on the analysis
- Prioritize tests based on importance and complexity
- Identify test classes that already comply with standards and mark them as **NoChanges** in the `maintenance/unit-tests.md` file
- Group similar test classes that require the same type of changes
- Estimate effort required for each group of test classes
- Create a timeline for implementation with clear milestones

#### Planning Strategies

- Start with simpler tests to establish patterns
- Group similar tests that need the same type of changes
- Identify common utilities or helpers that can be reused
- Plan for incremental improvements that can be verified at each step

### 3. Implementation Phase

- Make the necessary changes to test classes according to the plan
- Update the status in `maintenance/unit-tests.md` to reflect work in progress
- Follow the pre and post conditions strictly
- Keep changes focused on the specific task
- Do not modify production code
- Update test documentation as you go
- Mark each test class as **Completed** in the `maintenance/unit-tests.md` file after successfully updating it

#### Common Implementation Tasks

- Convert to JUnit 5 annotations
- Implement nested test classes for better organization
- Improve test method names for clarity
- Add `@DisplayName` annotations
- Refactor test data generation to use Generators
- Ensure Arrange-Act-Assert pattern is followed
- Add comments for complex test scenarios

### 4. Verification Phase

- Run tests to ensure functionality is preserved for each updated test class
- Verify that standards are now being followed for all test classes
- Review the `maintenance/unit-tests.md` file to ensure all test classes are properly categorized as **Analyzed**, **Completed**, or **NoChanges**
- Verify that no test classes have been overlooked
- Document any remaining issues or edge cases that couldn't be addressed
- Ensure test coverage meets requirements
- Update the overall task status in the module-specific maintenance document

#### Verification Checklist

- [ ] All tests pass successfully
- [ ] No regressions introduced
- [ ] Test coverage meets or exceeds 80%
- [ ] All test classes follow the standards
- [ ] Documentation is complete and accurate
- [ ] Status tracking is up-to-date

## Task Status Tracking

### Module-Level Status

Track the overall task status in the module-specific maintenance document:

- **Not Started**: Task has not been initiated
- **In Progress**: Task is currently being worked on
- **Completed**: Task has been completed and verified
- **Blocked**: Task cannot proceed due to dependencies or issues

### Test Class Status

For each test class in the `maintenance/unit-tests.md` file, use these status indicators:

- **Analyzed**: The test has been reviewed but not yet updated
- **Completed**: The test has been updated to comply with standards
- **NoChanges**: The test already complies with standards or no changes are needed

## Example Test Class Updates

### Before Update

```java
public class UserServiceTest {
    @Test
    public void testCreateUser() {
        UserService service = new UserService();
        User user = service.createUser("john", "password");
        Assert.assertNotNull(user);
        Assert.assertEquals("john", user.getUsername());
    }
}
```

### After Update

```java
@DisplayName("Tests for UserService")
class UserServiceTest {

    @Nested
    @DisplayName("User Creation Tests")
    class UserCreationTests {

        @Test
        @DisplayName("Should create user with valid credentials")
        void shouldCreateUserWithValidCredentials() {
            // Arrange
            UserService service = new UserService();

            // Act
            User user = service.createUser("john", "password");

            // Assert
            assertNotNull(user, "Created user should not be null");
            assertEquals("john", user.getUsername(), "Username should match input");
        }
    }
}
```

## References

The standards in this document are based on the following CUI testing standards:

- [Testing Standards README](../maintenance/standards/testing/README.adoc)
- [Core Standards](../maintenance/standards/testing/core-standards.adoc)
- [Quality Standards](../maintenance/standards/testing/quality-standards.adoc)
- [Logging Testing Guide](../maintenance/standards/logging/testing-guide.adoc)
- [Using Generators](../maintenance/standards/testing/Using%20Generators.adoc)
