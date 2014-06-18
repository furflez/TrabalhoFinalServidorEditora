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

public class DialogStartServer extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text textIp;
	private Text textPort;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DialogStartServer(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(219, 107);
		shell.setText(getText());
		
		Label lblIp = new Label(shell, SWT.NONE);
		lblIp.setBounds(10, 10, 36, 13);
		lblIp.setText("IP:");
		
		Label lblPorta = new Label(shell, SWT.NONE);
		lblPorta.setBounds(10, 29, 36, 13);
		lblPorta.setText("Porta:");
		
		textIp = new Text(shell, SWT.BORDER);
		textIp.setText("127.0.0.1");
		textIp.setBounds(52, 4, 153, 19);
		
		textPort = new Text(shell, SWT.BORDER);
		textPort.setText("40000");
		textPort.setBounds(52, 29, 153, 19);
		
		Button btnStartServer = new Button(shell, SWT.NONE);
		btnStartServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Main.ip = textIp.getText();
				Main.port = textPort.getText();

				Main.update();
				shell.close();
				
			}
		});
		
		btnStartServer.setBounds(10, 54, 93, 23);
		btnStartServer.setText("Iniciar servidor");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(112, 54, 93, 23);
		btnCancel.setText("Cancelar");

	}
}
