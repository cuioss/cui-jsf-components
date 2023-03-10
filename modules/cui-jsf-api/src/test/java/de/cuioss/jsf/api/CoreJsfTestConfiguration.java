package de.cuioss.jsf.api;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.convert.NumberConverter;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundle;
import de.cuioss.jsf.api.application.bundle.ResourceBundleWrapperImpl;
import de.cuioss.jsf.api.application.locale.LocaleProducer;
import de.cuioss.jsf.api.application.locale.LocaleProducerImpl;
import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.jsf.api.application.message.MessageProducerImpl;
import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;

/**
 * Defines a base setup for testing. Implicitly uses {@link BasicApplicationConfiguration}. In
 * addition, it:
 * <ul>
 * <li>Registers {@link StringIdentConverter} {@link ObjectToStringConverter} and
 * {@link NumberConverter}</li>
 * <li>Registers {@link CuiResourceBundle} and {@link MessageProducer}</li>
 * <li>Register a minimal {@link ResourceBundle}: {@link #TEST_BUNDLE_NAME}</li>
 * <li>Register the cui component messages</li>
 * <li>Registers a number of default components and {@link Renderer} derived from
 * {@link JsfHtmlComponent}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
public class CoreJsfTestConfiguration extends BasicApplicationConfiguration
        implements BeanConfigurator, ComponentConfigurator {

    /** Test resource bundle path */
    public static final String TEST_BUNDLE_BASE_PATH = "de.cuioss.jsf.components.bundle.";

    /** Test resource bundle name */
    public static final String TEST_BUNDLE_NAME = "testBundle";

    /** cui-messages bundle path */
    public static final String CUI_BUNDLE_BASE_PATH = "de.cuioss.jsf.api.core.l18n.";

    /** cui-messages resource bundle name */
    public static final String CUI_BUNDLE_NAME = "messages";

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        var bundleWrapper = new ResourceBundleWrapperImpl();
        bundleWrapper.setResourceBundleNames(immutableList(TEST_BUNDLE_NAME, CUI_BUNDLE_NAME));

        var cuiResourceBundle = new CuiResourceBundle();
        cuiResourceBundle.setResourceBundleWrapper(bundleWrapper);
        decorator.register(cuiResourceBundle, CuiResourceBundle.BEAN_NAME);

        var messageProducer = new MessageProducerImpl();
        messageProducer.setResourceBundle(cuiResourceBundle);
        decorator.register(messageProducer, MessageProducerImpl.BEAN_NAME);

        LocaleProducer localeProducer = () -> Locale.ENGLISH;
        decorator.register(localeProducer, LocaleProducerImpl.BEAN_NAME);
    }

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        super.configureApplication(decorator);
        decorator.registerResourceBundle(TEST_BUNDLE_NAME,
                TEST_BUNDLE_BASE_PATH + TEST_BUNDLE_NAME);
        decorator.registerResourceBundle(CUI_BUNDLE_NAME, CUI_BUNDLE_BASE_PATH + CUI_BUNDLE_NAME);
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerConverter(StringIdentConverter.class);
        decorator.registerConverter(NumberConverter.class, NumberConverter.CONVERTER_ID);
        decorator.registerConverter(ObjectToStringConverter.class);
        for (JsfHtmlComponent<?> component : JsfHtmlComponent.VALUES) {
            decorator.registerUIComponent(component.getComponentType(), component.getComponentClass())
                    .registerRenderer(component.getFamily(), component.getRendererType(),
                            new CuiMockRenderer(component.getDefaultHtmlElement().getContent()));
        }
    }

}
