package com.streaming.pump;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class TemperatureRegexShould {

	@Test
	public void extractValue() {

		String str = "21.86 °C";
		Pattern pattern = Pattern.compile("^([0-9.]+)\\s+°C$");
		Matcher matcher = pattern.matcher(str);

		assertTrue(matcher.matches());
		assertEquals(1, matcher.groupCount());
		assertEquals("21.86", matcher.group(1));
	}
}
