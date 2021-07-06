package mining;

import java.io.Serializable;

/**
 * Classe raprresentante un pattern emergente.
 */
public class EmergingPattern extends FrequentPattern implements Serializable {
    private float growrate;

    /**
     * Costruttore di classe EmergingPattern.
     * @param fp FrequentPattern che potrebbe essere emergente.
     * @param growrate Growrate del pattern.
     */
    EmergingPattern(FrequentPattern fp, float growrate) {
        super(fp);
        this.growrate = growrate;
    }

    /**
     * Metodo che restituisce il growrate di EmergingPattern.
     * @return Growrate del pattern.
     */
    float getGrowRate() {
        return growrate;
    }

    /**
     * Metodo che crea e restituisce la stringa che rappresenta il pattern,il suo supporto e il suo growrate.
     * @return Stringa rappresentante lo stato di EmergingPattern.
     */
    @Override
    public String toString() {
        return super.toString() + "[" + growrate + "]";
    }


}
