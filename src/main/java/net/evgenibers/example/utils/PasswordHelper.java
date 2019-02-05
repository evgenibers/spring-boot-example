package net.evgenibers.example.utils;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PasswordHelper {
	private static final String POSSIBLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=+";
	private static final Integer PASSWORD_LENGTH = 15;
	
	private PasswordHelper() {
	}
	
	public static String generatePassword() {
		log.debug("generatePassword: ");
		return generateRandomPassword(POSSIBLE_CHARS, PASSWORD_LENGTH);
	} 
	
	private static String generateRandomPassword(String possibleChars, Integer passwordLength) {
		log.debug("generateRandomPassword: possibleChars = {}, passwordLength = {}", possibleChars, passwordLength);
		char[] possibleCharacters = (new String(possibleChars)).toCharArray();
		return RandomStringUtils.random(passwordLength, 0, possibleCharacters.length - 1, false, false,
				possibleCharacters, new SecureRandom());
	}
}
