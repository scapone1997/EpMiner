package data;

import java.util.Iterator;

/**
 * Classe che modella un iteratore per ContinuousAttribute.
 */
public class ContinuousAttributeIterator implements Iterator<Float> {
    private float min;
    private float max;
    private int j = 0;
    private int numValues;

    /**
     * Costruttore di classe per ContinuousAttributeIterator.
     * @param min Estremo inferiore di ContinuousAttribute.
     * @param max Estremo superiore di ContinuousAttribute.
     * @param numValues Numero di segmenti uguali in cui suddividere l'intervallo ContinuousAttribute.
     */
    ContinuousAttributeIterator(float min, float max, int numValues) {
        this.min = min;
        this.max = max;
        this.numValues = numValues;
    }

    /**
     * Metodo che verifica la presenza di un successivo segmento nell'intervallo.
     * @return booleano: vero se ha successivo segmento, falso altrimenti.
     */
    @Override
    public boolean hasNext() {
        return (j <= numValues);
    }

    /**
     * Metodo che restituisce il primo valore del segmento successivo dell'intervallo.
     * @return Numero indicante l'inizio del segmento successivo.
     */
    @Override
    public Float next() {
        j++;
        return min + ((max - min) / numValues) * (j-1);
    }

}
