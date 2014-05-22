package com.epam.xslt.util;

import java.util.Random;

public final class Tokenizer {
	private static Random random = new Random();

	private Tokenizer() {
	}

	public static String generateToken() {
		StringBuilder sb = new StringBuilder();
		while (sb.length() < 15) {
			sb.append(random.nextInt());
		}
		return new String(sb);
	}

}
