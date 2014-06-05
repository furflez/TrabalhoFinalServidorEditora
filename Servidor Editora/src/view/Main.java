package view;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class Main {
	private static Table table;
	private static Button btnRemoveBook;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(524, 370);
		shell.setText("SWT Application");
		
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
		btnNewBook.setBounds(10, 310, 122, 28);
		btnNewBook.setText("Novo Livro");
		
		btnRemoveBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = table.getSelectionIndex();
				table.remove(i);
				if(!(i-1 < 0))
				table.setSelection(i-1);
				else{
					btnRemoveBook.setEnabled(false);
				}
				
			}
		});
		btnRemoveBook.setBounds(392, 310, 122, 28);
		btnRemoveBook.setText("Remover Livro");
		
		Button btnEditBook = new Button(shell, SWT.NONE);
		btnEditBook.setBounds(138, 310, 122, 28);
		btnEditBook.setText("Editar Livro");

		 for (int i = 0; i < 12; i++) {
		      TableItem item = new TableItem(table, SWT.NONE);
		      item.setText(new String[] {(int)(Math.random() * (99999 - 10000)) + 10000+"", "furflez"});
		    }
		 
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	static class clientStart implements Runnable {

		@Override
		public void run() {
			if(table.getItemCount() > 0){
				btnRemoveBook.setEnabled(true);
			}
			
		}
		
	}
}
