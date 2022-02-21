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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.shared.release.versions.VersionParseException;

/**
 * Example version: 1.10.0-2.17.12-SNAPSHOT
 */
class VersionWithSuffixVersion {

  private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+(\\.\\d+){0,4})-(\\d+(\\.\\d+){0,4})(-SNAPSHOT)?$");

  private final String mainVersion;
  private final String suffixVersion;
  private final boolean snapshot;

  VersionWithSuffixVersion(String mainVersion, String suffixVersion, boolean snapshot) {
    this.mainVersion = mainVersion;
    this.suffixVersion = suffixVersion;
    this.snapshot = snapshot;
  }

  public String getMainVersion() {
    return this.mainVersion;
  }

  public String getSuffixVersion() {
    return this.suffixVersion;
  }

  public boolean isSnapshot() {
    return this.snapshot;
  }

  @Override
  public String toString() {
    return mainVersion + "-" + suffixVersion + (snapshot ? "-SNAPSHOT" : "");
  }

  static VersionWithSuffixVersion parse(String version) throws VersionParseException {
    Matcher matcher = VERSION_PATTERN.matcher(version);
    if (!matcher.matches()) {
      throw new VersionParseException("Version with version suffix '" + version + "' does not match pattern: " + VERSION_PATTERN.toString());
    }
    return new VersionWithSuffixVersion(matcher.group(1), matcher.group(3), matcher.group(5) != null);
  }

}
