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
package de.cuioss.jsf.test;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.weld.junit5.auto.ActivateScopes;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import lombok.Getter;

@EnableAutoWeld
@ActivateScopes({ RequestScoped.class })
class MirrorCuiRessourcBundleTest implements ShouldHandleObjectContracts<MirrorCuiRessourcBundle> {

    @Inject
    @Getter
    MirrorCuiRessourcBundle underTest;

    @Test
    void resourceBundleContainsKey() {
        assertNotNull(underTest.getKeys());
    }

    @Test
    void shouldMirrorKey() {
        var any = letterStrings(1, 20).next();
        assertEquals(any, underTest.getString(any));
    }
}
