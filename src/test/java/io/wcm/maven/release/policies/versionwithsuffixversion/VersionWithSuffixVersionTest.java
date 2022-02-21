/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2022 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.maven.release.policies.versionwithsuffixversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.maven.shared.release.versions.VersionParseException;
import org.junit.jupiter.api.Test;

class VersionWithSuffixVersionTest {

  @Test
  void testInvalid() {
    assertThrows(VersionParseException.class, () -> {
      VersionWithSuffixVersion.parse("1.10.0");
    });
  }

  @Test
  void testValid() throws VersionParseException {
    VersionWithSuffixVersion underTest = VersionWithSuffixVersion.parse("1.10.0-2.17.12");
    assertEquals("1.10.0", underTest.getMainVersion());
    assertEquals("2.17.12", underTest.getSuffixVersion());
    assertFalse(underTest.isSnapshot());
    assertEquals("1.10.0-2.17.12", underTest.toString());
  }

  @Test
  void testValidSnapshot() throws VersionParseException {
    VersionWithSuffixVersion underTest = VersionWithSuffixVersion.parse("1.10.0-2.17.12-SNAPSHOT");
    assertEquals("1.10.0", underTest.getMainVersion());
    assertEquals("2.17.12", underTest.getSuffixVersion());
    assertTrue(underTest.isSnapshot());
    assertEquals("1.10.0-2.17.12-SNAPSHOT", underTest.toString());
  }

}
