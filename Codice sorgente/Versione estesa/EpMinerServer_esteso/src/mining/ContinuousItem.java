package mining;

import data.ContinuousAttribute;
import java.io.Serializable;

/**
 * Classe che estende la classe astratta Item e modella la coppia: Attributo continuo - Intervallo di valori;
 * es: (Temperature in [10;30.31[).
 */
public class ContinuousItem extends Item implements Serializable {
    /**
     * Costruttore di ContinuousItem.
     * @param attribute Attributo di tipo ContinuousAttribute.
     * @param value Intervallo rappresentante il dominio di ContinuousAttribute.
     */
    ContinuousItem(ContinuousAttribute attribute, Interval value) {
        super(attribute, value);
    }

    /**
     * Metodo che verifica che il parametro value rappresenti un numero reale incluso tra gli estremi dell’intervallo
     * associato allo item in oggetto.
     * @param value Valore di cui verificare l'appartenenza al dominio.
     * @return booleano: true se appartiene, false altrimenti.
     */
    boolean checkItemCondition(Object value) { //al run time sarà di tipo Float
        Interval i = (Interval) this.getValue();
        return i.checkValueInclusion((float) value);
    }

    /**
     * Metodo che restituisce una stringa rappresentante lo stato dell' oggetto nella forma:
     * (nome attributo) in [inf, sup[
     * @return Stringa in formato: (nome attributo) in [inf, sup[
     */
    @Override
    public String toString() {
        Interval i = (Interval) this.getValue();
        return this.getAttribute().toString() + " in " + i.toString();
    }
}
