/*
 * Orchestrator
 * Copyright (C) 2011-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.sonar.orchestrator.locator;

import static com.sonar.orchestrator.util.OrchestratorUtils.checkArgument;
import static com.sonar.orchestrator.util.OrchestratorUtils.isEmpty;

public class PluginLocation extends MavenLocation {
  private final String key;

  public PluginLocation(Builder builder) {
    super(builder);
    this.key = builder.key;
  }

  public String key() {
    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PluginLocation that = (PluginLocation) o;
    return key.equals(that.key) && version().equals(that.version());
  }

  @Override
  public int hashCode() {
    int result = key.hashCode();
    result = 31 * result + version().hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("[%s:%s:%s:%s]", key, version(), getGroupId(), getArtifactId());
  }

  public static PluginLocation create(String key, String version, String groupId, String artifactId) {
    return builder().setKey(key).setVersion(version).setGroupId(groupId).setArtifactId(artifactId).build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder extends MavenLocation.Builder<Builder> {
    private String key;

    private Builder() {
    }

    public Builder setKey(String key) {
      this.key = key;
      return this;
    }

    @Override
    public PluginLocation build() {
      // Just to reuse preconditions
      super.build();

      checkArgument(!isEmpty(key), "key must be set");
      return new PluginLocation(this);
    }
  }
}
