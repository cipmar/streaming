package com.streaming.pump;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ThermometerItem(
		@JsonProperty String name,
		@JsonProperty String type,
		@JsonProperty String state,
		@JsonProperty String category) {
}
