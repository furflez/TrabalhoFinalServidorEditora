package view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import soketsuport.As;
import soketsuport.SoketSupport;
import vo.Book;

public class Main {
	public static Table table;
	private static Button btnRemoveBook;
	private static Button btnIniciarServidor;
	private static Label lblServer;
	public static ArrayList<Book> bookList;
	public static SoketSupport soketSupport;

	public static String ip = "";
	public static String port = "";

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// criacão da janela principal
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(530, 397);
		shell.setText("Servidor editora do mau");
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setSelection(0);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 504, 307);
		TableColumn tblclmnIsbn = new TableColumn(table, SWT.NONE);
		tblclmnIsbn.setWidth(100);
		tblclmnIsbn.setText("ISBN");
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("NOME");
		TableColumn tblclmnAutor = new TableColumn(table, SWT.NONE);
		tblclmnAutor.setWidth(100);
		tblclmnAutor.setText("AUTOR");
		TableColumn tblclmnQtd = new TableColumn(table, SWT.NONE);
		tblclmnQtd.setWidth(100);
		tblclmnQtd.setText("QTD");
		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(100);
		tblclmnPrice.setText("VALOR");
		Button btnNewBook = new Button(shell, SWT.NONE);
		btnNewBook.setBounds(10, 323, 122, 28);
		btnNewBook.setText("Novo Livro");
		btnRemoveBook = new Button(shell, SWT.NONE);
		btnRemoveBook.setBounds(392, 323, 122, 28);
		btnRemoveBook.setText("Remover Livro");
		btnIniciarServidor = new Button(shell, SWT.NONE);
		btnIniciarServidor.setBounds(266, 323, 120, 28);
		btnIniciarServidor.setText("Iniciar Servidor");
		lblServer = new Label(shell, SWT.CENTER);
		lblServer.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblServer.setBounds(10, 357, 502, 13);
		lblServer.setText("server off");
		//fim da criação da tela
		
		bookList = new ArrayList<Book>();//instanciado uma nova lista vazia de livros
		
		deserializeBooks();//chamado o método que vai pegar os livros de um arquivo txt e colocar na lista instanciada
		
		//função de clique no botão novo livro
		btnNewBook.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
		
				DialogAddNewBook add = new DialogAddNewBook(shell, 0);//inicia o diálogo de cadastro de novo livro
				add.open();
				
				
				serializeBooks();//método que adiciona os livros da lista no txt

			}
		});

		//função de clique no botão remover livro
		btnRemoveBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				int i = table.getSelectionIndex(); //obtem qual linha foi selecionada
				if (table.getSelectionCount() != 0) { //verifica se foi selecionada alguma linha
					bookList.remove(i); //remove a linha da tabela
					table.remove(i); // remove o livro da lista
					if (table.getItemCount() != 0) { //verifica se ainda tem livros na lista
						if (!(i < 0)) {
							table.setSelection(i - 1); //se tiver ao menos 1 item, vai selecionar a linha anterior a excluida
						}
					}
				}

				
				serializeBooks();//método que adiciona os livros da lista no txt
			}
		});

		//função de clique no botão iniciar servidor
		btnIniciarServidor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DialogStartServer start = new DialogStartServer(shell, SWT.NONE);//inicia o diálogo de configuração do servidor
				start.open();
			}
		});

		update(); //método que vai atualizar os dados da tabela com os dados da lista

		//as coisas abaixos são geradas automáticamente para iniciar a tela
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// o update atualiza a tabela com os dados da lista, é publico pois é chamado nas classes de dialogo
	public static void update() {

		DecimalFormat df = new DecimalFormat("#,##0.00"); //formatador para que um tipo float aparça com 2 zeros ao final
		df.setMaximumFractionDigits(2);//configuração do formatador para que ele tenha 2 digitos após a virgula
		table.removeAll();//é removido todos os dados da tabela para não duplicar os que ja tem
		for (Book book : bookList) { //esse for pega cada livro dentro da lista de livros e adiciona na tabela
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { book.getISBN() + "", book.getName(),
					book.getAuthor(), book.getDailyProd() + "",
					"R$" + df.format(book.getPrice()) });
		}
		if (ip != "") { //quando o servidor for iniciado, a thread dentro desse if será iniciada, o background e o texto da label serão alterados também
			Runnable stask = new serverStart();  //instanciada a thread serverStart como um Runnable
			Thread server = new Thread(stask); //instanciada uma nova thread passando o runnable no construtor
			server.start();//inicia a thread
			lblServer.setBackground(SWTResourceManager
					.getColor(SWT.COLOR_GREEN)); //altera a cor do fundo da label em vermelho para verde
			lblServer.setText("server on"); //altera o texto da label
			btnIniciarServidor.setText("Parar Servidor"); //altera a cor do botão

		}

	}

	//thread que inicia o servidor e fica no aguardo de requisições
	private static class serverStart implements Runnable {
		// server recebe requisi‹o e responde
		@Override
		public void run() {
			try {
				soketSupport = new SoketSupport(); //instancia a classe soketSupport;
				soketSupport.configure(As.server, ip, port); //configura o soket como server passando o ip e a porta do servidor
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}

			while (true) {
				try {
					// server aguardando requisião
					String texto = soketSupport.receiveData();
					System.out.println(texto);
					getMethod(texto); //assim que recebe um texto, entra no metodo de verificação de metodo

					soketSupport.ForceClose();
					
				} catch (Exception e) {
					System.out.println("EXCEPTION: " + e.getMessage());
				}
			}
		}
	}

	private static void getMethod(String string) { //esse metodo vai verificar se o texto passado é um metodo válido

		if (string.contains("consultarIsbn")) { //verifica se contem consultarIsbn na string para fazer a busca do livro através da isbn
			consultarIsbn(string.substring(string.indexOf(":") + 1)); //o consultarIsbn ta recebendo uma string cortada a partir do : 
		} else {
			try {
				soketSupport.sendData("COMANDO INVÁLIDO! ANEMAL!");//caso não receba a string com um método válido, é retornada essa menságem para o cliente
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//a consulta da isbn recebe uma string com o numero da isbn a ser consultada
	private static void consultarIsbn(String string) {
		int isbn = Integer.parseInt(string); //aqui a string é convertida para inteiro
		boolean foundAtLeastOne = false; //variavel controladora informando se ao menos um registro foi encontrado, inicia como falsa
		Book book = new Book(); //instanciado um novo livro
		for (Book bookx : bookList) { //for para fazer a verificação em toda a lista de livros
			if (bookx.getISBN() == isbn) { //se a isbn do livro for igual a informada
				book = bookx; // o livro recebe o livro encontrado
				foundAtLeastOne = true; //a variavel controladora recebe true
				break;//quebra o for para não ser feita mais nenhuma verificação
			}
		}
		if (foundAtLeastOne) {//se um livro foi encontrado, as informações dele serão repassadas para o cliente
			try {
				DecimalFormat df = new DecimalFormat("#,##0.00");
				df.setMaximumFractionDigits(2);
				soketSupport.sendData("$Nome: " + book.getName() + " |Autor: "
						+ book.getAuthor() + "|Preco: R$"
						+ df.format(book.getPrice()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { //se não encontrar informa que nada foi encontrado
			try {
				soketSupport.sendData("Nada encontrado!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//metodo que salva a lista inteira num txt de forma serializada
	private static void serializeBooks() {
		try {
			FileOutputStream fos = new FileOutputStream("arquivo");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(bookList);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	//metodo que obtem todos os dados dos livros do txt serializado
	private static void deserializeBooks() {
		try {
			FileInputStream fis = new FileInputStream("arquivo");
			ObjectInputStream ois = new ObjectInputStream(fis);
			bookList = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
	}
}