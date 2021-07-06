package data;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Classe concreta che estende la classe Attribute e modella un attributo continuo.
 */
public class ContinuousAttribute extends Attribute implements Iterable<Float>, Serializable {
    //rappresentano gli estremi di un intervallo
    private float max;
    private float min;

    /**
     * Costruttore della classe che inizializza gli attributi della classe.
     * @param name Nome dell'attributo.
     * @param index Indice dell'attributo.
     * @param min Valore minimo del dominio dell'attributo.
     * @param max Valore massimo del dominio dell'attributo.
     */
    ContinuousAttribute(String name, int index, float min, float max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Metodo che restituisce l'attributo min.
     * @return Estremo inferiore dell'intervallo.
     */
    public float getMin() {
        return min;
    }

    /**
     * Metodo che restituisce l'attributo max.
     * @return Estremo superiore dell'intervallo.
     */
    public float getMax() {
        return max;
    }

    /**
     * Metodo che restituisce un iteratore float per ContinuousAttribute.
     * @return Iteratore
     */
    public Iterator<Float> iterator() {
        return new ContinuousAttributeIterator(min, max, 5);
    }

}
