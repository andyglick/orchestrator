/*
 * Orchestrator
 * Copyright (C) 2011-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
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
package com.sonar.orchestrator.container;

import com.google.common.base.Preconditions;
import com.sonar.orchestrator.locator.Location;
import com.sonar.orchestrator.version.Version;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.annotation.Nullable;

public final class SonarDistribution {

  private Version version;
  private int port;
  private String context = "/sonar";
  private List<Location> pluginLocations = new ArrayList<>();
  private List<Location> profileBackups = new ArrayList<>();
  private Properties serverProperties = new Properties();
  private List<String> licensedPluginKeys = new ArrayList<>();
  private boolean removeDistributedPlugins = true;

  public SonarDistribution() {
    // A distribution without a version yet
  }

  public SonarDistribution(Version version) {
    this.version = version;
  }

  public SonarDistribution setVersion(Version s) {
    this.version = s;
    return this;
  }

  public SonarDistribution addPluginLocation(Location plugin) {
    pluginLocations.add(plugin);
    return this;
  }

  public Version version() {
    return version;
  }

  public List<Location> getPluginLocations() {
    return Collections.unmodifiableList(pluginLocations);
  }

  public SonarDistribution restoreProfileAtStartup(Location backup) {
    Preconditions.checkNotNull(backup);
    this.profileBackups.add(backup);
    return this;
  }

  public List<Location> getProfileBackups() {
    return profileBackups;
  }

  public Properties getServerProperties() {
    return serverProperties;
  }

  public String getServerProperty(String key) {
    return serverProperties.getProperty(key);
  }

  public SonarDistribution setServerProperty(String key, @Nullable String value) {
    if (value == null) {
      serverProperties.remove(key);
    } else {
      serverProperties.setProperty(key, value);
    }
    return this;
  }

  public SonarDistribution removeServerProperty(String key) {
    serverProperties.remove(key);
    return this;
  }

  public SonarDistribution addServerProperties(Properties props) {
    serverProperties.putAll(props);
    return this;
  }

  public boolean isRelease() {
    return version.isRelease();
  }

  public SonarDistribution activateLicense(String pluginKey) {
    licensedPluginKeys.add(pluginKey);
    return this;
  }

  public List<String> getLicensedPluginKeys() {
    return licensedPluginKeys;
  }

  public int getPort() {
    return port;
  }

  public SonarDistribution setPort(int port) {
    this.port = port;
    return this;
  }

  public String getContext() {
    return context;
  }

  public SonarDistribution setContext(String context) {
    this.context = context;
    return this;
  }

  public boolean removeDistributedPlugins() {
    return removeDistributedPlugins;
  }

  public SonarDistribution setRemoveDistributedPlugins(boolean remove) {
    this.removeDistributedPlugins = remove;
    return this;
  }
}
