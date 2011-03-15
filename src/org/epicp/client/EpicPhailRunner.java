package org.epicp.client;

import java.io.IOException;

public class EpicPhailRunner {

	public static void main(String[] args) {

		try {
			Client client = SimpleClient.createSimpleClient();

			while (client.update()) {
				;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
