package soketsuport;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SoketSupport {

	private As _typeConnection;

	private static Socket _ns;

	private DataOutputStream _writer;

	private DataInputStream _reader;

	private ServerSocket _server;
	private Socket _client;
	private Socket _socket;

	private String _ip;
	private int _port;

	/**
	 * m�todo que configura o socket como cliente ou servidor passando o tipo da
	 * conex�o, ip e porta.
	 * 
	 * @param typeConnection
	 *            tipo da conex�o, definida client para cliente e server para
	 *            servidor
	 * @param ip
	 *            endere�o que ser� usado para configurar o servidor e/ou
	 *            cliente
	 * @param porta
	 *            que ser� usada para configurar o servidor e/ou cliente
	 * 
	 * @throws IOException
	 *             � lan�ada a exce��o quando existe algum problema com a
	 *             entrada ou a sa�da dos dados
	 */
	public void configure(As typeConnection, String ip, String port)
			throws IOException {
		_ip = ip;
		_port = Integer.parseInt(port);

		_typeConnection = typeConnection;

		switch (typeConnection) {
		case server:
			InetAddress address = InetAddress.getByName(ip);
			if (_server == null)
				_server = new ServerSocket(_port, 0, address);

			break;

		case client:

			_client = new Socket();
			break;
		}

	}

	/**
	 * M�todo respons�vel pelo envio de uma mens�gem a outro socket
	 * 
	 * @param data
	 *            mens�gem a ser repassada
	 * @throws IOException
	 *             � lan�ada a exce��o quando existe algum problema com a
	 *             entrada ou a sa�da dos dados
	 */
	public void sendData(String data) throws IOException {
		switch (_typeConnection) {
		case server:

			_writer.writeBytes(data);
			_writer.flush();
			_socket.close();

			break;
		case client:

			_client = new Socket(_ip, _port);
			_ns = _client;

			_writer = new DataOutputStream(_ns.getOutputStream());

			if (_client.isConnected())

				_writer.writeBytes(data);
			_writer.flush();

			break;
		}

	}

	/**
	 * m�todo respons�vel por receber uma mens�gem de um socket
	 * 
	 * @return mens�gem recebida
	 * @throws IOException
	 *             � lan�ada a exce��o quando existe algum problema com a
	 *             entrada ou a sa�da dos dados
	 */
	public String receiveData() throws IOException {
		String data = null;

		switch (_typeConnection) {
		case server:
			_socket = _server.accept();

			_ns = _socket;

			_writer = new DataOutputStream(_ns.getOutputStream());
			_reader = new DataInputStream(_ns.getInputStream());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					_reader));
			return reader.readLine();

		case client:

			BufferedReader clientreader = new BufferedReader(
					new InputStreamReader(_reader));
			data = clientreader.readLine();
			_client.close();
			break;
		}
		return data;
	}

	/**
	 * M�todo que obtem o tipo da conex�o, se � um servidor ou cliente
	 * 
	 * @return tipo da conex�o
	 */
	public As getTypeConnection() {
		return _typeConnection;
	}

	/**
	 * M�todo que obtem uma lista com os ips de cada dispositivo (f�sico ou
	 * virtual)
	 * 
	 * @return array de ips como strings
	 * @throws UnknownHostException
	 *             � lan�ada a exce��o quando o host n�o � alcan�ado ou �
	 *             desconhecido
	 * @throws IOException
	 *             � lan�ada a exce��o quando existe algum problema com a
	 *             entrada ou a sa�da dos dados
	 */
	public String[] getLocalIp() throws UnknownHostException, IOException {
		String nome = InetAddress.getLocalHost().getHostName();

		InetAddress[] ip = Inet4Address.getAllByName(nome);
		String[] ips = new String[ip.length + 1];
		ips[0] = "127.0.0.1";
		for (int i = 0; i < ip.length; i++) {
			ips[i + 1] = ip[i].getHostAddress();
		}
		return (ips);
	}

	/**
	 * M�todo que fecha a conex�o do socket para encerrar o tr�fego de
	 * informa��es
	 * 
	 * @throws IOException
	 *             � lan�ada a exce��o quando existe algum problema com a
	 *             entrada ou a sa�da dos dados
	 */
	public void ForceClose() throws IOException {
		switch (_typeConnection) {
		case server:
			if(_socket!=null)
			{
			_socket.close();
			}
			break;
		case client:
			if(_client!=null)
			{
			_client.close();
			}
			break;
		}
	}

}