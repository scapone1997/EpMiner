package clientmain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import keyboardinput.Keyboard;

/**
 * Classe che modella l'esecuzione di un client.
 */
public class MainTest {

	/**
	 * Metodo main del client.
	 * @param args Stringa rappresentante indirizzo ip e port del server a cui si desidera connettersi.
	 *             Se args è vuoto o non valido, verrà utilizzato un ip e port predefinito.
	 */
	public static void main(String[] args) throws IOException {
		//se args non vuoto prendo indirizzo dato, altrimenti uso quello del server locale con porta 8080
		Socket socket;
		if(args.length != 0) {
			InetAddress addr = InetAddress.getByName(args[0]);
			System.out.println("addr = " + addr + "\nport=" + args[1] );
			socket = new Socket(addr, new Integer(args[1]));
		}else{
			InetAddress addr = InetAddress.getByName("127.0.0.1"); //indirizzo server locale
			int port = 8080; //porta usata nel server locale
			System.out.println("addr = " + addr + "\nport=" + port );
			socket = new Socket(addr, port);
		}
		System.out.println(socket);

		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
		
		
		char risp='s';
		do{
			System.out.println("Scegli una opzione:");
			int opzione;
			do{
				System.out.println("1: Nuova scoperta");
				System.out.println("2: Risultati in archivio");
				opzione=Keyboard.readInt();
			}while(opzione!=1 && opzione!=2);
			
			float minsup=0f;float minGr=0f;
			do{
				System.out.println("Inserire valore minimo supporto (minsup>0 e minsup<=1):");
				minsup=Keyboard.readFloat();
			}while(minsup<=0 || minsup>1 || Float.isNaN(minsup));
			
			do{
				System.out.println("Inserire valore minimo grow rate (minGr>0):");
				minGr=Keyboard.readFloat();
			}while(minGr<=0 || Float.isNaN(minGr));
				
			System.out.println("Tabella target:");
			String targetName=Keyboard.readString();
			System.out.println("Tabella background:");
			String backgroundName=Keyboard.readString();
			String nameFile =targetName+"_"+backgroundName;
			try{
				//qui invio richiesta al server
				out.writeObject(opzione);
				out.writeObject(minsup);
				out.writeObject(minGr);
				out.writeObject(targetName);
				out.writeObject(backgroundName);
				out.writeObject(nameFile);

				//da qui ricevo risultati elaborazione dal server
				String fpMiner=(String)(in.readObject());
				System.out.println(fpMiner);
				String epMiner=(String)(in.readObject());
				System.out.println(epMiner);
			} catch (SocketException e) { //se il socket non è più raggiungibile, il server è stato spento.
				System.out.println("Server irrangiungibile. Chiusura client...");
				break;
			} catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
			
			System.out.println("Vuoi ripetere?(s/n)");
			risp=Keyboard.readChar();
		}while(risp!='n');
		
		
		
	}

}
