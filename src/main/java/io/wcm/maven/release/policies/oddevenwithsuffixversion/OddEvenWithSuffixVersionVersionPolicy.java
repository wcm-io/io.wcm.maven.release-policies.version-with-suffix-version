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
package io.wcm.maven.release.policies.oddevenwithsuffixversion;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.release.policy.PolicyException;
import org.apache.maven.shared.release.policy.oddeven.OddEvenVersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Manages release versionining of versions with a suffix version, e.g. <code>1.10.0-2.17.12</code>.
 * The policy manages only the first part, <code>1.10.0</code> in this example, and keeps the suffixed version
 * untouched.
 */
@Component(
    role = VersionPolicy.class,
    hint = "VersionWithSuffixVersionVersionPolicy",
    description = "Manages versions with a suffixed version")
public class OddEvenWithSuffixVersionVersionPolicy implements VersionPolicy {

  private final VersionPolicy oddEvenVersionPolicy = new OddEvenVersionPolicy();

  @Override
  public VersionPolicyResult getReleaseVersion(VersionPolicyRequest request) throws PolicyException, VersionParseException {
    VersionWithSuffixVersion version = VersionWithSuffixVersion.parse(request.getVersion());

    // increment only main version
    String nextMainVersion = oddEvenVersionPolicy.getReleaseVersion(new VersionPolicyRequest()
        .setVersion(version.getMainVersion() + "-SNAPSHOT")).getVersion();
    return new VersionPolicyResult().setVersion(
        new VersionWithSuffixVersion(nextMainVersion, version.getSuffixVersion(), false).toString());
  }

  @Override
  public VersionPolicyResult getDevelopmentVersion(VersionPolicyRequest request) throws PolicyException, VersionParseException {
    VersionWithSuffixVersion version = VersionWithSuffixVersion.parse(request.getVersion());

    // return next main version with snapshot
    String nextMainVersion = StringUtils.removeEnd(oddEvenVersionPolicy.getDevelopmentVersion(new VersionPolicyRequest()
        .setVersion(version.getMainVersion())).getVersion(), "-SNAPSHOT");
    return new VersionPolicyResult().setVersion(
        new VersionWithSuffixVersion(nextMainVersion, version.getSuffixVersion(), true).toString());
  }

}
