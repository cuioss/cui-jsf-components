/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application.message;

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Helper class for accessing instances of {@link MessageProducer} within
 * objects that are not under control of the MangedBeanFacility, e.g. Converter,
 * validators, components. Managed Beans should use the managed property
 * facility.
 *
 * @author Oliver Wolff
 */
public class MessageProducerAccessor extends ManagedBeanAccessor<MessageProducer> {

    private static final long serialVersionUID = -8226816546645652258L;

    /**
     * Constructor.
     */
    public MessageProducerAccessor() {
        super(MessageProducerImpl.BEAN_NAME, MessageProducer.class, true);
    }

}
