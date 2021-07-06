package mining;

import data.DiscreteAttribute;
import java.io.Serializable;

/**
 * Classe che estende la classe astratta Item e modella la coppia: Attributo discreto - valore discreto.
 * es: (Outlook="Sunny")
 */
public class DiscreteItem extends Item implements Serializable {
    /**
     * Costruttore di DiscreteItem.
     * @param attribute Attributo di tipo DiscreteItem.
     * @param value Stringa rappresentante un valore del dominio di DiscreteItem.
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Metodo che verifica che il membro value sia uguale (nello stato) all’argomento passato come parametro.
     *
     * @param value Valore di cui verificare l'appartenenza al dominio.
     * @return booleano: true se appartiene, false altrimenti.
     */
    boolean checkItemCondition(Object value) { //al run time di tipo String
        return this.getValue().equals(value);
    }

}
