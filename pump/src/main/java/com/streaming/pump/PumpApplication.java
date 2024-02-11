package com.streaming.pump;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

/**
 * The entry point of the service.
 */
public class PumpApplication extends Application<PumpConfiguration> {

	public static void main(String[] args) throws Exception {

		new PumpApplication().run(args);
	}

	@Override
	public void run(PumpConfiguration pumpConfiguration, Environment environment) {

		var retrieveOpenhabDataTask = new RetrieveOpenhabDataTask(pumpConfiguration);

		var executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(retrieveOpenhabDataTask, 1, 30, SECONDS);
	}
}
