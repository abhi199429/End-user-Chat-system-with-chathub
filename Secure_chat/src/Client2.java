import java.net.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Client2  {


	private ObjectInputStream socIn;
	private ObjectOutputStream socOut;
	private Socket socket;
        ConversationDemo c;
	

	private String server, username;
	private int port;

	Client2(String server, int port, String username) {
		
            this.server = server;
            this.port = port;
            this.username = username;
		
	}

	public boolean start() {
		
		try {
			socket = new Socket(server, port);
		} 
		
		catch(Exception ec) {
	
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
	
		try
		{
			socIn  = new ObjectInputStream(socket.getInputStream());
			socOut = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
	
			return false;
		}

		
		new ServerListen().start();
		try
		{
			socOut.writeObject(username);
		}
		catch (IOException eIO) {
		
			disconnect();
			return false;
		}
		
		return true;
	}
        

	void sendMessage(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		try {
                   
                    c = new ConversationDemo();
			socOut.writeObject(c.encryptString(msg));
		}
		catch(IOException e) {
		
		}
	}

	private void disconnect() {
		try { 
			if(socIn != null) socIn.close();
		}
		catch(Exception e) {}
		try {
			if(socOut != null) socOut.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
		
			
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		int p_Num = 1500;
		String s_Add = "localhost";
		String name = "Anonymous1";
                
                if(args.length == 3){
                    
                    		s_Add = args[2];
                }
                else if(args.length ==2){
                    		try {
					p_Num = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
					return;
				}
                }
                else if(args.length == 1){
                    name = args[0];
                }
                else if(args.length == 0){
                    
                }
                else
                {
                    		System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
			return;
                }
		Client2 client = new Client2(s_Add, p_Num, name);

		if(!client.start())
			return;
		
		
		Scanner scan = new Scanner(System.in);
		
		for(;;) {
			System.out.print("> ");
		
			String msg = scan.nextLine();
		
			if(msg.equalsIgnoreCase("LOGOUT")) {
		
                                client.sendMessage(msg);
		
				break;
			}
			
			else {	
			
                                client.sendMessage(msg);
			}
		}
		
		client.disconnect();	
	}

	
	class ServerListen extends Thread {

		public void run() {
                    c = new ConversationDemo();
			for(;;) {
				try {
                                    
					String msg = (String) socIn.readObject();
                                            
						System.out.println("Message from server" + c.decryptString(msg));
						System.out.print("> ");
	
				}
				catch(IOException e) {
	
					break;
				}
	
				catch(ClassNotFoundException e2) {
				} catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPaddingException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadPaddingException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidAlgorithmParameterException ex) {
                            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
                        }
			}
		}
	}
}
