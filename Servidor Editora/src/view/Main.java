package view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import vo.Book;

public class Main {
	public static Table table;
	private static Button btnRemoveBook;
	public static ArrayList<Book> bookList;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(530, 370);
		shell.setText("SWT Application");
		bookList = new ArrayList<Book>();
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setSelection(0);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 0, 504, 304);

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
		btnNewBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DialogAddNewBook add = new DialogAddNewBook(shell, SWT.NONE);
				add.open();

			}
		});
		btnNewBook.setBounds(10, 310, 122, 28);
		btnNewBook.setText("Novo Livro");

		btnRemoveBook = new Button(shell, SWT.NONE);
		btnRemoveBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = table.getSelectionIndex();
				if (table.getSelectionCount() != 0) {
					table.remove(i);
					if (table.getItemCount() != 0) {
						if (!(i < 0)) {
							table.setSelection(i - 1);
						}
					}
				}

			}
		});

		btnRemoveBook.setBounds(392, 310, 122, 28);
		btnRemoveBook.setText("Remover Livro");

		Button btnEditBook = new Button(shell, SWT.NONE);
		btnEditBook.setBounds(138, 310, 122, 28);
		btnEditBook.setText("Editar Livro");
		
		Button btnIniciarServidor = new Button(shell, SWT.NONE);
		btnIniciarServidor.setBounds(266, 310, 120, 28);
		btnIniciarServidor.setText("Iniciar Servidor");

		update();

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void update() {

		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setMaximumFractionDigits(2);
		table.removeAll();
		for (Book book : bookList) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { book.getISBN() + "", book.getName(),
					book.getAuthor(), book.getDailyProd() + "",
					"R$" + df.format(book.getPrice()) });
		}

	}
}