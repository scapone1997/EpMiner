package mining;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe comparatore per EmergingPattern.
 */
class ComparatorGrowRate implements Comparator<EmergingPattern>, Serializable {
    /**
     * Metodo che confronta il GrowRate do o1 e o2.
     * @param o1 EmergingPattern di cui confrontare il GrowRate.
     * @param o2 EmergingPattern di cui confrontare il GrowRate.
     * @return intero: 1 se GrowRate di o1 maggiore, -1 se GrowRate di o2 maggiore, 0 altrimenti.
     */
    @Override
    public int compare(EmergingPattern o1, EmergingPattern o2) {
        if (o1.getGrowRate() > o2.getGrowRate()) {
            return 1;
        } else if (o1.getGrowRate() < o2.getGrowRate()) {
            return -1;
        } else {
            return 0;
        }
    }

}
