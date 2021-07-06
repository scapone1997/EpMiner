package data;

import java.io.Serializable;

/**
 * Classe astratta che modella l'entità Attribute.
 */
public abstract class Attribute implements Serializable {
    //nome simbolico dell'attributo
    private String name;
    //identificativo numerico dell'attributo (indica la posizione della colonna (0,1,2,…)
    //che rappresenta l’attributo nella tabella di dati)
    private int index;

    /**
     * Costruttore di classe avvalorato dal nome e dall'identificativo numerico.
     * @param name Nome dell'attributo.
     * @param index Identificativo numerico dell'attributo.
     */
    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Metodo che restituisce il nome dell'attributo.
     * @return Nome dell'attributo.
     */
    String getName() {
        return name;
    }

    /**
     * Metodo che restituisce l'identificativo numerico dell'attributo.
     * @return Identificativo numerico dell'attributo.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Metodo che restituisce una stringa rappresentante lo stato dell'oggetto.
     * @return Stringa contenente il nome dell'attributo.
     */
    @Override
    public String toString() {
        return name;
    }

}
