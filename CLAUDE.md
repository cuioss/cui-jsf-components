# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Full build (all modules)
./mvnw clean install

# Run all tests
./mvnw test

# Run a single test class or method
./mvnw test -Dtest=ClassName#methodName

# Build a specific module (from repo root)
./mvnw clean install -pl modules/cui-jsf-api

# Javadoc cleanup (fix all reported errors/warnings)
./mvnw clean install -Pjavadoc-mm-reporting

# Code cleanup with OpenRewrite, then verify
./mvnw -Prewrite rewrite:run
./mvnw clean install

# Generate site documentation
./mvnw site
```

Web modules (cui-javascript, cui-jsf-bootstrap-css) use frontend-maven-plugin which automatically runs Node.js/Grunt during `mvnw install` -- no separate npm commands needed for normal builds.

## Project Architecture

This is a multi-module Maven project providing a JSF (Jakarta Faces 4.0) component library built on MyFaces 4.x, Jakarta EE 10, Bootstrap 5, and jQuery 3.7.

### Module Hierarchy

```
cui-jsf-components (root POM)
├── bom/                          # Bill of Materials - defines all consumable artifacts
├── modules/                      # Aggregator for Java JSF modules
│   ├── cui-jsf-api/              # Foundation: base classes, partial providers, renderers, CSS utilities
│   ├── cui-jsf-core-components/  # Non-Bootstrap components: validators, converters, basic HTML
│   ├── cui-jsf-bootstrap/        # Bootstrap-styled JSF components (buttons, icons, menus, etc.)
│   ├── cui-jsf-jqplot/           # JQPlot chart components
│   ├── cui-jsf-dev/              # Developer documentation components (source code display, metadata)
│   └── cui-jsf-test/             # Testing infrastructure: mock producers, @EnableJSFCDIEnvironment
└── web-modules/                  # Aggregator for frontend resource modules
    ├── cui-javascript/           # TypeScript→Grunt→minified JS (jQuery integration)
    └── cui-jsf-bootstrap-css/    # SCSS→Grunt→compiled CSS (Bootstrap 5 + CUI styles)
```

### Module Dependencies (bottom-up)

```
cui-jsf-api  ←  cui-jsf-core-components  ←  cui-jsf-bootstrap
                                          ←  cui-jsf-dev
             ←  cui-jsf-jqplot
             ←  cui-jsf-test (test scope)
```

### Key Architectural Pattern: Partial Providers

The central design pattern is **Partial Providers** for composing component behavior:

- **`ComponentBridge`** interface connects partial implementations to the owning `UIComponent`, providing access to `StateHelper`, `FacesContext`, and facets
- **Provider interfaces** define contracts: `TitleProvider`, `IconProvider`, `StyleClassProvider`, `ContextStateProvider`, etc. (30+ providers in `de.cuioss.jsf.api.components.partial`)
- **Provider implementations** are stateless; they access component state exclusively through `ComponentBridge`
- **Components compose providers** using Lombok `@Delegate` to gain behavior without deep inheritance

### Component Base Classes

Located in `de.cuioss.jsf.api.components.base`:

- `CuiComponentBase` → base for all CUI components, implements `ComponentBridge`
- `AbstractBaseCuiComponent` → adds `ComponentStyleClassProvider` + `StyleAttributeProvider`
- `BaseCuiNamingContainer`, `BaseCuiCommandButton`, `BaseCuiInputComponent`, `BaseCuiPanel`, `BaseCuiOutputText` → specialized bases

Component families: `"de.cuioss.jsf.api.html.family"` (API) and `BootstrapFamily` (Bootstrap module).

### Renderer Framework

Located in `de.cuioss.jsf.api.components.renderer`:

- `BaseDecoratorRenderer` → base class using `DecoratingResponseWriter` for simplified HTML generation
- Components register via `META-INF/faces-config.xml` in each module

### Testing Infrastructure

- `@EnableJSFCDIEnvironment` meta-annotation combines JSF + CDI (Weld JUnit5) test setup
- `CoreJsfTestConfiguration` provides base test configuration with converters and mock renderers
- Mock producers: `MessageProducerMock`, `LocaleProducerMock`, `ProjectStageProducerMock`
- Testing libraries: JUnit 5, EasyMock, `cui-test-value-objects`, `cui-test-juli-logger`, `cui-test-generator`

### Web Resource Build Pipeline

Both web modules use `frontend-maven-plugin` with Node.js v20.11.1:
- **cui-javascript**: TypeScript → Grunt (ts-plugin + uglify) → minified JS in `META-INF/resources/`
- **cui-jsf-bootstrap-css**: SCSS → Grunt (grunt-sass) → CSS in `META-INF/resources/de.cuioss.portal.css/`

## Code Standards

- **Lombok**: `@Builder`, `@Value`, `@NonNull`, `@Delegate`, `@UtilityClass` -- config enables `@Generated` annotation for Sonar exclusion
- **Logging**: `CuiLogger` (private static final LOGGER) with LogRecord API; use `'%s'` for substitutions, exception as first parameter
- **Null safety**: Use `@NonNull` from Lombok for required parameters, `Optional` for nullable return values
- **Package naming**: `de.cuioss.jsf.*`
- **Formatting**: 4-space indentation, UTF-8, LF line endings (see `.editorconfig`)

## External Standards Reference

CUI organization standards: https://github.com/cuioss/cui-llm-rules/tree/main/standards/README.adoc
