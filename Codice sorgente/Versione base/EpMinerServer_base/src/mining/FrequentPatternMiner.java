package mining;

import data.*;
import utility.*;
import utility.Queue;
import java.io.*;
import java.util.*;

/**
 * Classe che include i metodi per la scoperta di pattern frequenti con Algoritmo APRIORI.
 */
public class FrequentPatternMiner implements Iterable<FrequentPattern>, Serializable {
	private List<FrequentPattern> outputFP = new LinkedList<FrequentPattern>();

	/**
	 * Metodo che restituisce la lista dei FrequentPattern.
	 * @return Lista di FrequentPattern.
	 */
	List<FrequentPattern> getOutputFP() {
		return outputFP;
	}

	/**
	 * Costruttore che genera tutti i pattern k=1 frequenti e per ognuno di questi genera quelli con k>1
	 * richiamando expandFrequentPatterns(). I pattern sono memorizzati nel membro OutputFP.
	 * @param data Insieme delle transizioni.
	 * @param minSup Minimo supporto.
	 * @throws EmptySetException Eccezione pattern vuoto.
	 */
	public FrequentPatternMiner(Data data, float minSup) throws EmptySetException {
		if (data == null || data.getNumberOfExamples() == 0) {
			throw new EmptySetException();
		}

		Queue<FrequentPattern> fpQueue = new Queue<FrequentPattern>();
		for (Attribute genericAttribute : data.getAttributeSet()) {
			if (genericAttribute instanceof DiscreteAttribute) {
				DiscreteAttribute currentAttribute = (DiscreteAttribute) genericAttribute;
				for (String s : currentAttribute.getValues()) {
					DiscreteItem item = new DiscreteItem(currentAttribute, s);
					FrequentPattern fp = new FrequentPattern();
					fp.addItem(item);
					fp.setSupport(fp.computeSupport(data));
					if (fp.getSupport() >= minSup) { // 1-FP CANDIDATE
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}

				}
			} else if (genericAttribute instanceof ContinuousAttribute) {
				ContinuousAttribute currentAttribute = (ContinuousAttribute) genericAttribute;
				Iterator<Float> iter = currentAttribute.iterator();
				if (iter.hasNext()) {
					float a = iter.next(); //limite inferiore dell'intervallo
					while (iter.hasNext()) {
						float b = iter.next(); //limite superiore dell'intervallo
						if (b == currentAttribute.getMax()){
							b = currentAttribute.getMax() + 0.0001f; //per includere l'ultimo intervallo
						}
						ContinuousItem item = new ContinuousItem(currentAttribute, new Interval(a, b));
						FrequentPattern fp = new FrequentPattern();
						fp.addItem(item);
						fp.setSupport(fp.computeSupport(data));
						if (fp.getSupport() >= minSup) { // 1-FP CANDIDATE
							fpQueue.enqueue(fp);
							outputFP.add(fp);
						}
						a = b; //il vecchio superiore � il nuovo inferiore
					}
				}
			}
		}
		try {
			outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
			sort();
		} catch (EmptyQueueException e) {
			//System.out.println("Nessun pattern frequente rilevato.");
		}
	}

	/**
	 * Metodo che espande la lista di pattern frequenti.
	 * Finché fpQueue contiene elementi, si estrae un elemento dalla coda fpQueue, si generano i raffinamenti per questo
	 * (aggiungendo un nuovo item non incluso). Per ogni raffinamento si verifica se è frequente e, in caso affermativo,
	 * lo si aggiunge sia ad fpQueue sia ad outputFP.
	 * @param data Insieme delle transazioni.
	 * @param minSup Minimo supporto.
	 * @param fpQueue Coda contente i pattern da valutare.
	 * @param outputFP Lista dei pattern frequenti già estratti.
	 * @return Lista popolata con pattern frequenti a k>1.
	 * @throws EmptyQueueException Eccezione coda vuota.
	 */
	 private List<FrequentPattern> expandFrequentPatterns(
	 		Data data, float minSup, Queue<FrequentPattern> fpQueue, List<FrequentPattern> outputFP)
			 throws EmptyQueueException {

		if(fpQueue.isEmpty()) {
			throw new EmptyQueueException();
		}

		while (!fpQueue.isEmpty()) {
			FrequentPattern fp = fpQueue.first(); //fp to be refined
			fpQueue.dequeue();
			for (Attribute genericAttribute : data.getAttributeSet()) {
				boolean found = false;
			//the new item should involve an attribute different form attributes already involved into the items of fp
				for (Item item : fp.getFp()) {
					if (item.getAttribute().equals(genericAttribute)) {
						found = true;
						break;
					}
				}
				if (!found) { //data.getAttribute(i) is not involve into an item of fp
					if (genericAttribute instanceof DiscreteAttribute){
						DiscreteAttribute currentAttribute = (DiscreteAttribute) genericAttribute;
						for (String s : currentAttribute.getValues()) {
							DiscreteItem item = new DiscreteItem(currentAttribute, s);
							FrequentPattern newFP = refineFrequentPattern(fp, item); //generate refinement
							newFP.setSupport(newFP.computeSupport(data));
							if (newFP.getSupport() >= minSup) {
								fpQueue.enqueue(newFP);
								outputFP.add(newFP);
							}
						}
					} else if (genericAttribute instanceof ContinuousAttribute) {
						ContinuousAttribute currentAttribute = (ContinuousAttribute) genericAttribute;
						Iterator<Float> iter = currentAttribute.iterator();
						if (iter.hasNext()) {
							float a = iter.next();
							while (iter.hasNext()) {
								float b = iter.next();
								if (b == currentAttribute.getMax()) {
									b = currentAttribute.getMax() + 0.0001f; //per includere l'ultimo intervallo
								}
								ContinuousItem item = new ContinuousItem(currentAttribute, new Interval(a, b));
								FrequentPattern newFP = refineFrequentPattern(fp, item); //generate refinement
								newFP.setSupport(newFP.computeSupport(data));
								if (newFP.getSupport() >= minSup) {
									fpQueue.enqueue(newFP);
									outputFP.add(newFP);
								}
								a = b;
							}
						}
					}

				}

			}
		}

		return outputFP;
	}

	/**
	 * Metodo che crea un nuovo pattern a cui aggiunge tutti gli item di FP e il parametro item.
	 * @param FP Pattern da raffinare.
	 * @param item Item da aggiungere ad FP.
	 * @return Nuovo pattern ottenuto per effetto del raffinamento.
	 */
	private FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item) {
		FrequentPattern refinedFrequentPattern = new FrequentPattern(FP);
		refinedFrequentPattern.addItem(item);
		return refinedFrequentPattern;
	}

	/**
	 * Metodo che ordina la lista outputFP.
	 */
	private void sort() {
		Collections.sort(outputFP);
	}

	/**
	 * Metodo che scandisce OutputFp al fine di concatenare in un'unica stringa i pattern frequenti letti.
	 * @return Stringa rappresentante il valore di OutputFP.
	 */
	@Override
	public String toString() {
		String output = "\nFrequent patterns:\n";
		int i = 1;
		for (FrequentPattern fp : this) {
			output += i + ". " + fp + "\n";
			i++;
		}
		return output;
	}

	/**
	 * Metodo che restituisce un iteratore per la lista di FrequentPattern.
	 * @return Iteratore.
	 */
	@Override
	public Iterator<FrequentPattern> iterator() {
		return outputFP.iterator();
	}

	/**
	 * Metodo che permette di salvare su file i pattern frequenti trovati.
	 * @param nomeFile Stringa rappresentante il nome del file.
	 * @throws FileNotFoundException Eccezione file non trovato.
	 * @throws IOException Eccezione I/O.
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		FileOutputStream outFile = new FileOutputStream(nomeFile);
		ObjectOutputStream outStream = new ObjectOutputStream(outFile);
		outStream.writeObject(this);
		outFile.close();
		outStream.close();
	}

	/**
	 * Metodo che permette di caricare da file i pattern frequenti.
	 * @param nomeFile Stringa rappresentante il nome del file.
	 * @return FrequentPatternMiner caricato da un file.
	 * @throws FileNotFoundException Eccezione file non trovato.
	 * @throws IOException Eccezione I/O.
	 * @throws ClassNotFoundException Eccezione classe non trovata.
	 */
	public static FrequentPatternMiner carica(String nomeFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream inFile = new FileInputStream(nomeFile);
		ObjectInputStream inStream = new ObjectInputStream(inFile);
		FrequentPatternMiner fpm = (FrequentPatternMiner) inStream.readObject();
		inFile.close();
		inStream.close();
		return fpm;
	}

}
