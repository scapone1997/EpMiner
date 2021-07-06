package mining;

import data.Data;
import data.EmptySetException;
import java.io.*;
import java.util.*;

/**
 * Classe che modella la scoperta di emerging pattern a partire dalla lista di frequent pattern.
 */
public class EmergingPatternMiner implements Iterable<EmergingPattern>, Serializable {
    //lista che contiene riferimenti a oggetti istanza della classe mining.EmergingPattern che definiscono il pattern
    private List<EmergingPattern> epList = new LinkedList<EmergingPattern>();

    /**
     * Costruttore di classe EmergingPatternMiner.
     * Si scandiscono tutti i frequent pattern in fpList , per ognuno di essi si calcola il grow rate usando
     * dataBackground e se tale valore è maggiore uguale di minG allora il pattern è aggiunto ad epList.
     * @param dataBackground Dataset di background su cui calcolare il growrate di tutti i pattern presenti in fpList.
     * @param fpList FrequentPatternMiner contenente lista di pattern frequenti.
     * @param minG Minimo growrate.
     * @throws EmptySetException Eccezione pattern vuoto.
     */
    public EmergingPatternMiner(Data dataBackground, FrequentPatternMiner fpList, float minG) throws EmptySetException {
        if (dataBackground == null || dataBackground.getNumberOfExamples() == 0) {
            throw new EmptySetException();
        }

        EmergingPattern ep = null;
        for (FrequentPattern fp : fpList) {
            try {
                ep = computeEmergingPattern(dataBackground, fp, minG);
                epList.add(ep);
            } catch (EmergingPatternException e) {
                //System.out.println("EmergingPattern analizzato ha growrate inferiore alla soglia minima.");
            }
        }
        sort();
    }

    /**
     * Metodo che ottiene da fp il suo supporto relativo al dataset target:
     * Si calcola il supporto di fp relativo al dataset di background.
     * Si calcola il grow rate come rapporto dei due supporti.
     * @param dataBackground Insieme delle transazioni di background.
     * @param fp Pattern frequente di cui calcolare il growrate.
     * @return growrate di fp.
     */
    private float computeGrowRate(Data dataBackground, FrequentPattern fp) {
        float targetSupport = fp.getSupport();
        float backgroundSupport = fp.computeSupport(dataBackground);
        return targetSupport / backgroundSupport;
    }

    /**
     * Metodo che verifica che il gorw rate di fp sia maggiore di minGR.
     * In caso affermativo crea un oggetto EmemrgingPattern da fp.
     * @param dataBackground Insieme delle transazioni di background.
     * @param fp Pattern frequente.
     * @param minGR minimo growrate.
     * @return restituisce emerging pattern creato da fp se la condizione sul growrate è soddisfatta, null altrimenti.
     * @throws EmergingPatternException Eccezione lanciata quando la condizione del growrate non è soddisfatta.
     */
    private EmergingPattern computeEmergingPattern(Data dataBackground , FrequentPattern fp, float minGR)
            throws EmergingPatternException {
        float growrate = computeGrowRate(dataBackground, fp);
        if (growrate >= minGR) {
            return new EmergingPattern(fp, growrate);
        } else {
            throw new EmergingPatternException();
        }
    }

    /**
     * Metodo che ordina epList.
     */
    private void sort() {
        Collections.sort(epList, new ComparatorGrowRate());
    }

    /**
     * Metodo che scandisce epList al fine di concatenare in un'unica stringa le stringhe rappresentati i pattern letti.
     * @return Stringa rappresentante il valore di epList.
     */
    @Override
    public String toString() {
        String output = "\nEmerging patterns:\n";
        int i = 1;
            for (EmergingPattern ep : this) {
                output += i + ". " + ep + "\n";
                i++;
            }
        return output;
    }

    /**
     * Metodo che restituisce un iteratore per la lista di EmergingPattern.
     * @return Iteratore.
     */
    @Override
    public Iterator<EmergingPattern> iterator() {
        return epList.iterator();
    }

    /**
     * Metodo che permette di salvare su file i pattern emergenti trovati.
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
     * Metodo che permette di caricare da file i pattern emergenti.
     * @param nomeFile Stringa rappresentante il nome del file.
     * @return EmergingPatternMiner caricato da un file.
     * @throws FileNotFoundException Eccezione file non trovato.
     * @throws IOException Eccezione I/O.
     * @throws ClassNotFoundException Eccezione classe non trovata.
     */
    public static EmergingPatternMiner carica(String nomeFile)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream inFile = new FileInputStream(nomeFile);
        ObjectInputStream inStream = new ObjectInputStream(inFile);
        EmergingPatternMiner epm = (EmergingPatternMiner) inStream.readObject();
        inFile.close();
        inStream.close();
        return epm;
    }

}
