package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modella lo schema di una tabella generica in un database.
 */
public class TableSchema {
	private Connection connection;

	/**
	 * Metodo che gestisce il mapping tra i tipi SQL e quelli Java.
	 * @param connection Gestore accesso al database.
	 * @param tableName Nome della tabella.
	 * @throws SQLException Eccezione SQL.
	 */
	public TableSchema(String tableName, Connection connection) throws SQLException {
		this.connection = connection;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");
		
		 DatabaseMetaData meta = connection.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
		   
	     while (res.next()) {
	         
	         if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
				 tableSchema.add(new Column(
						 res.getString("COLUMN_NAME"),
						 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
				 );


			 }
	     }
	     res.close();
	}


	/**
	 * Classe che modella una colonna della tabella.
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * Costruttore di classe Column che inizializza gli attributi.
		 * @param name Nome della colonna.
		 * @param type Tipologia del contenuto della colonna.
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Metodo che restituisce il nome della colonna.
		 * @return Nome della colonna.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Metodo che verifica se la colonna contiene valori numerici o meno.
		 * @return booleano: true se numero, false altrimenti.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Metodo che restituisce una stringa che descrive lo stato di una colonna.
		 */
		@Override
		public String toString() {
			return name + ":" + type;
		}
	}
	List<Column> tableSchema = new ArrayList<Column>();


	/**
	 * Metodo che restituisce il numero di colonne di una tabella.
	 * @return Numero di colonne della tabella.
	 */
	public int getNumberOfAttributes() {
			return tableSchema.size();
	}

	/**
	 * Metodo che restituisce la colonna della tabella indicata dall'indice.
	 * @param index Indice colonna.
	 * @return Colonna della tabella indicata dall'indice.
	 */
	public Column getColumn(int index) {
			return tableSchema.get(index);
	}

}
