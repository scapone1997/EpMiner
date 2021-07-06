package mining;

import data.Attribute;
import java.io.Serializable;

/**
 * Classe astratta che modella un generico item (coppia attributo-valore).
 */
abstract class Item implements Serializable {
    private Attribute attribute; //attributo coinvolto nell'item
    private Object value; //valore assegnato all'attributo

    /**
     * Costruttore di classe Item.
     * @param attribute Attributo.
     * @param value Valore dell'attributo.
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Metodo che restituisce l'attributo di Item.
     * @return Attributo.
     */
    Attribute getAttribute() {
        return attribute;
    }

    /**
     * Metodo che restituisce il valore di Item.
     * @return Valore.
     */
    Object getValue() {
        return value;
    }

    /**
     * Metodo astratto da implementare nelle specializzazioni della classe.
     * Verifica l'appartenenza di value al dominio di Item.
     * @param value Valore di cui verificare l'appartenenza al dominio.
     * @return booleano: true se appartiene, false altrimenti.
     */
    abstract boolean checkItemCondition(Object value);

    /**
     * Metodo che restituisce lo stato dell'oggetto sotto forma di stringa.
     * @return Stringa rappresentante lo stato dell'oggetto.
     */
    public String toString() {
        String coppia = attribute.toString() + "=" + (String) value;
        return coppia;
    }

}
