package data;

import java.io.Serializable;

/**
 * Classe concreta che estende la classe Attribute e rappresenta un attributo discreto.
 */
public class DiscreteAttribute extends Attribute implements Serializable {
    //array di stringhe, una per ciascun valore discreto , che rappresenta il domino dell’attributo
    private String values[];

    /**
     * Costruttore di classe che inizializza un attributo discreto.
     * @param name Nome dell'attributo.
     * @param index Indice dell'attributo.
     * @param values Valore dell'attributo.
     */
    DiscreteAttribute(String name, int index, String values[]) {
        super(name, index);
        this.values = values;
    }

    /**
     * Metodo che restituisce il valore in posizione index.
     * @param index Indice del valore.
     * @return Valore in posizione index.
     */
    public String getValue(int index) {
        return values[index];
    }

    /**
     * Metodo che restituisce il numero di valori discreti nel dominio dell'attributo.
     * @return Numero di valori discreti nel dominio dell'attributo.
     */
    public int getNumberOfDistinctValues() {
        return values.length;
    }

    /**
     * Metodo che restituisce l'insieme dei valori del dominio dell'attributo.
     * @return Valori discreti nel dominio dell'attributo.
     */
    public String[] getValues() {
        return values;
    }

}
