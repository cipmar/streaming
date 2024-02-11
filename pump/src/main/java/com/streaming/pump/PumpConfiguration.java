package com.streaming.pump;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotEmpty;

/**
 * The configuration of the service.
 */
public class PumpConfiguration extends Configuration {

	@NotEmpty
	private String openHabUrl = "http://192.168.100.45:8080/";

	@NotEmpty
	private String databaseUrl = "jdbc:mysql://localhost:3307/streaming";

	@NotEmpty
	private String databaseUser = "user";

	@NotEmpty
	private String databasePassword = "password";

	@JsonProperty
	public String getOpenHabUrl() {
		return openHabUrl;
	}

	@JsonProperty
	public void setOpenHabUrl(String openHabUrl) {
		this.openHabUrl = openHabUrl;
	}

	@JsonProperty
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	@JsonProperty
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	@JsonProperty
	public String getDatabaseUser() {
		return databaseUser;
	}

	@JsonProperty
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	@JsonProperty
	public String getDatabasePassword() {
		return databasePassword;
	}

	@JsonProperty
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
}
