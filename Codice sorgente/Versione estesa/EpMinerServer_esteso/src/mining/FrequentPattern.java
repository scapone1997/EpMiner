package mining;

import data.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che modella un pattern frequente.
 */
class FrequentPattern implements Iterable<Item>, Comparable<FrequentPattern>, Serializable {
	private List<Item> fp;
	private float support;

	/**
	 * Metodo che restituisce la lista di Item del pattern.
	 * @return Lista di Item del pattern frequente.
	 */
	public List<Item> getFp() {
		return fp;
	}

	/**
	 * Costruttore di classe FrequentPattern.
	 */
	FrequentPattern(){
		fp = new LinkedList<Item>();
	}

	/**
	 * Costruttore che alloca fp e support come copia del frequent pattern FP passato.
	 * @param FP FrequentPattern col quale istanziare per copia l'oggetto corrente.
	 */
	FrequentPattern(FrequentPattern FP) {
		fp = new LinkedList<Item>();
		for (Item it : FP) {
			fp.add(it);
		}
		support = FP.getSupport();
	}

	/**
	 * Metodo che aggiunge un nuovo Item al pattern.
	 * @param item Item da aggiungere al pattern.
	 */
	void addItem(Item item) {
		fp.add(item);
	}

	/**
	 * Metodo che restituisce un Item in posizione index della lista fp.
	 * @param index Indice indicante la posizione dell'item nella lista.
	 * @return Item nella lista in posizione index.
	 */
	Item getItem(int index) {
		return fp.get(index);
	}

	/**
	 * Metodo che restituisce il support di FrequentPattern.
	 * @return Support.
	 */
	 float getSupport() {
		return support;
	 }

	/**
	 * Metodo che restituisce la lunghezza del pattern.
	 * @return lunghezza del pattern.
	 */
	int getPatternLength() {
		return fp.size();
	 }

	/**
	 * Metodo che imposta il support del pattern.
	 * @param support Valore a cui impostare il support del pattern.
	 */
	 void setSupport(float support) {
		this.support = support;
	 }

	/**
	 * Metodo che scandisce fp al fine di concatenare in una stringa la rappresentazione degli item;
	 * alla fine si concatena il supporto.
	 * @return Stringa ripresentante lo item set e il suo supporto.
	 */
	@Override
	public String toString() {
		String value = "";
		boolean primo = true; //solo il primo elemento non ha AND all'inizio
		for (Item it : this) {
			if (primo) {
				value += it;
				primo = false;
			} else {
				value += " AND " + it;
			}
		}
		if (fp.size() > 0) value += "[" + support + "]";
		
		return value;
	}

	/**
	 * Calcola il supporto del pattern rappresentato dall'oggetto this rispetto al dataset data passato come argomento.
	 * @param data Valore di supporto del pattern nel dataset data
	 * @return Support del pattern rispetto a data.
	 */
	float computeSupport(Data data) {
		int suppCount = 0;
		// indice esempio
		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			//indice item
			boolean isSupporting = true;
			for (Item it : this) {
				//mining.DiscreteItem
				if (it instanceof DiscreteItem) {
					DiscreteItem item = (DiscreteItem) it;
					DiscreteAttribute attribute = (DiscreteAttribute) item.getAttribute();
					//Extract the example value
					Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
					if (!item.checkItemCondition(valueInExample)) {
						isSupporting = false;
						break; //the ith example does not satisfy fp
					}
				//mining.ContinuousItem
				} else if (it instanceof ContinuousItem) {
					ContinuousItem item = (ContinuousItem) it;
					ContinuousAttribute attribute = (ContinuousAttribute) item.getAttribute();
					//Extract the example value
					Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
					if (!item.checkItemCondition(valueInExample)) {
						isSupporting = false;
						break; //the ith example does not satisfy fp
					}
				}

			}
			if (isSupporting) {
				suppCount++;
			}
		}
		return((float) suppCount) / (data.getNumberOfExamples());
	}

	/**
	 * Metodo che restituisce un iteratore per FrequentPattern.
	 * @return Iteratore.
	 */
	@Override
	public Iterator<Item> iterator() {
		return fp.iterator();
	}

	/**
	 * Metodo che confronta il supporto del FrequentPattern corrente con quello passato come parametro.
	 * @param o FrequentPattern del quale confrontare il support con this.support
	 * @return intero: 1 se this.support maggiore di o.support, -1 se this.support minore di o.support, 0 altrimenti.
	 */
	@Override
	public int compareTo(FrequentPattern o) {
		if (this.support > o.support) {
			return 1;
		} else if (this.support < o.support){
			return -1;
		} else {
			return 0;
		}
	}

}
