package mining;

import java.io.Serializable;

/**
 * Classe che modellaun intervallo numerico continuo.
 */
public class Interval implements Serializable {
    private float inf;
    private float sup;

    /**
     * Costruttore di classe Interval.
     * @param inf Estremo inferiore dell'intervallo.
     * @param sup Estremo superiore dell'intervallo.
     */
    Interval(float inf, float sup) {
        this.inf = inf;
        this.sup = sup;
    }

    /**
     * Metodo che avvalora inf con il parametro passato.
     * @param inf Valore numerico.
     */
    void setInf(float inf) {
        this.inf = inf;
    }

    /**
     * Metodo che avvalora sup con il parametro passato.
     * @param sup Valore numerico.
     */
    void setSup(float sup) {
        this.sup = sup;
    }

    /**
     * Metodo che restituisce l'estremo inferiore dell'intervallo.
     * @return estremo inferiore dell'intervallo.
     */
    float getInf() {
        return inf;
    }

    /**
     * Metodo che restituisce l'estremo superiore dell'intervallo.
     * @return estremo superiore dell'intervallo.
     */
    float getSup() {
        return sup;
    }

    /**
     * Metodo che restituisce vero se il parametro è maggiore uguale di inf e minore di sup, false altrimenti.
     * @param value valore assunto da una attributo continuo per il quale verificare la appartenenza all’intervallo.
     * @return booleano.
     */
    boolean checkValueInclusion(float value) {
        return value >= inf && value < sup;
    }

    /**
     * Metodo che rappresenta in una stringa gli estremi dell’intervallo e restituisce tale stringa.
     * @return Stringa rappresentante l'intervallo.
     */
    @Override
    public String toString() {
        return "[" + inf + "," + sup + "[";
    }

}
