package database;

/**
 * Classe che modella un'eccezione di tipo DatabaseConnectionException.
 */
public class DatabaseConnectionException extends Exception {
	DatabaseConnectionException(String msg) {
		super(msg);
	}
}
