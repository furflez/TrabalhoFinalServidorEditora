package view;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import vo.Book;

public class DialogAddNewBook extends Dialog {

	protected Object result;
	protected Shell shlAdicionarNovoLivro;
	private Text textIsbn;
	private Text textName;
	private Text textDailyProd;
	private Text textAuthor;
	private Text textPrize;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DialogAddNewBook(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAdicionarNovoLivro.open();
		shlAdicionarNovoLivro.layout();
		Display display = getParent().getDisplay();
		while (!shlAdicionarNovoLivro.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAdicionarNovoLivro = new Shell(getParent(), getStyle());
		shlAdicionarNovoLivro.setSize(219, 180);
		shlAdicionarNovoLivro.setText("Adicionar Novo livro");

		Label lblISBN = new Label(shlAdicionarNovoLivro, SWT.NONE);
		lblISBN.setBounds(10, 6, 32, 14);
		lblISBN.setText("ISBN:");

		textIsbn = new Text(shlAdicionarNovoLivro, SWT.BORDER);
		textIsbn.setBounds(48, 3, 151, 19);

		Label lblNome = new Label(shlAdicionarNovoLivro, SWT.NONE);
		lblNome.setBounds(10, 33, 39, 14);
		lblNome.setText("Nome:");

		textName = new Text(shlAdicionarNovoLivro, SWT.BORDER);
		textName.setBounds(48, 28, 151, 19);

		textDailyProd = new Text(shlAdicionarNovoLivro, SWT.BORDER);
		textDailyProd.setBounds(108, 53, 91, 19);

		textAuthor = new Text(shlAdicionarNovoLivro, SWT.BORDER);
		textAuthor.setBounds(48, 78, 151, 19);

		textPrize = new Text(shlAdicionarNovoLivro, SWT.BORDER);
		textPrize.setBounds(48, 103, 151, 19);

		Label lblNewLabel_2 = new Label(shlAdicionarNovoLivro, SWT.NONE);
		lblNewLabel_2.setBounds(10, 58, 92, 14);
		lblNewLabel_2.setText("Produção Diária:");

		Label lblAuthor = new Label(shlAdicionarNovoLivro, SWT.NONE);
		lblAuthor.setBounds(10, 85, 39, 14);
		lblAuthor.setText("Autor:");

		Label lblPrice = new Label(shlAdicionarNovoLivro, SWT.NONE);
		lblPrice.setBounds(10, 108, 39, 14);
		lblPrice.setText("Preço:");

		Button btnAdd = new Button(shlAdicionarNovoLivro, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Book book = new Book(Integer.parseInt(textIsbn.getText()),
						textName.getText().toString(), Integer
								.parseInt(textDailyProd.getText()), textAuthor
								.getText().toString(), Double
								.parseDouble(textPrize.getText()));
				Main.bookList.add(book);
				Main.update();
				shlAdicionarNovoLivro.close();
			}
		});
		btnAdd.setBounds(8, 128, 94, 28);
		btnAdd.setText("Adicionar");

		Button btnCancel = new Button(shlAdicionarNovoLivro, SWT.NONE);
		btnCancel.setBounds(105, 128, 94, 28);
		btnCancel.setText("Cancelar");

	}
}
