package com.streaming.pump;

import static com.streaming.pump.jooq.tables.Temperature.TEMPERATURE;
import static java.lang.String.format;
import static org.jooq.SQLDialect.MYSQL;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streaming.pump.jooq.Tables;

/**
 * The task which retrieves the data from Openhab and save it to the storage.
 */
public class RetrieveOpenhabDataTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveOpenhabDataTask.class);
	private static final String TEMPERATURE_LIVING_THERMOMETER = "rest/items/ThermometerLiving_temperatureliving";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final PumpConfiguration cfg;

	public RetrieveOpenhabDataTask(PumpConfiguration cfg) {
		this.cfg = cfg;
	}

	@Override
	public void run() {

		var client = HttpClient.newHttpClient();
		var uri = URI.create(cfg.getOpenHabUrl() + TEMPERATURE_LIVING_THERMOMETER);

		LOGGER.info("Request to Openhab {}", uri);
		var request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();

		try {

			var resp = client.send(request, HttpResponse.BodyHandlers.ofString());
			var item = OBJECT_MAPPER.readValue(resp.body(), ThermometerItem.class);
			saveTemperatureToStorage(item);

		} catch (IOException | InterruptedException e) {
			LOGGER.error("Error calling Openhab api", e);
		} catch (SQLException e) {
			LOGGER.error("Error saving the data to the database", e);
		}
	}

	private void saveTemperatureToStorage(ThermometerItem item) throws SQLException {

		var value = parseTemperature(item);

		// TODO marius: Make this code independent of the database implementation
		try (var conn = DriverManager.getConnection(cfg.getDatabaseUrl(), cfg.getDatabaseUser(), cfg.getDatabasePassword())) {

			DSL.using(conn, MYSQL)
					.insertInto(Tables.TEMPERATURE, TEMPERATURE.NAME, TEMPERATURE.TYPE, TEMPERATURE.CATEGORY, TEMPERATURE.STATE)
					.values(item.name(), item.type(), item.category(), BigDecimal.valueOf(value))
					.execute();
		}
	}

	private double parseTemperature(ThermometerItem item) {
		var matcher = Pattern.compile("^([0-9.]+)\\s+°C$").matcher(item.state());

		if (!matcher.matches() || matcher.groupCount() != 1) {

			throw new RuntimeException(format("Unexpected state format %s for item %s", item.state(), item.name()));
		}

		return Double.parseDouble(matcher.group(1));
	}
}
