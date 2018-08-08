package br.com.fiap.service.util;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class UpdateTweetUtil {

	public static void enviarTweetsProfessor(Twitter twitter, String retorno) {

		try {
			twitter.updateStatus(retorno + " @Maderal");

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}


	public static void enviarTweetsProfessorComArray(Twitter twitter, List<String> listaResponseSend) {

		try {
			
			for (String response : listaResponseSend) {
				twitter.updateStatus(response + " @Maderal");
			}
			

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
}
