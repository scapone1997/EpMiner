package utility;

/**
 * Classe che modella una coda generica personalizzata.
 * @param <T> Tipo di coda.
 */
public class Queue<T> {

		private Record begin = null;

		private Record end = null;
		
		private class Record {

	 		 T elem;

	 		 Record next;

			 Record(T e) {
				this.elem = e; 
				this.next = null;
			}
		}


	/**
	 * Metodo che verifica se una coda sia vuota o meno.
	 * @return booleano: vero se coda vuota, false altrimenti.
	 */
	public boolean isEmpty() {
			return this.begin == null;
		}

	/**
	 * Metodo che inserisce un elemento nella coda.
	 * @param e elemento da inserire nella coda.
	 */
		 public void enqueue(T e) {
			if (this.isEmpty()) {
				this.begin = this.end = new Record(e);
			} else {
				this.end.next = new Record(e);
				this.end = this.end.next;
			}
		}


	/**
	 * Metodo che legge l'elemento in testa alla coda.
	 * @return elemento letto dalla coda.
	 * @throws EmptyQueueException Eccezione coda vuota.
	 */
		 public T first() throws EmptyQueueException {
			 if (this.begin == null) {
				 throw new EmptyQueueException();
			 } else {
			 	return this.begin.elem;
			 }
		}

	/**
	 * Metodo che elimina un elemento dalla coda.
	 * @throws EmptyQueueException Eccezione coda vuota.
	 */
		 public void dequeue() throws EmptyQueueException {
			if (this.begin == this.end) {
				if (this.begin == null) {
					throw new EmptyQueueException();
				} else {
					this.begin = this.end = null;
				}
			} else {
				begin = begin.next;
			}
			
		}

	}
