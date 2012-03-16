import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CreateAlarmDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Control control;
	private JTextField txtIn;
	private JTextField txtAt;
	private JLabel lblInfo;
	
	
	public CreateAlarmDialog(Control control, MainWindow window) {
		super(window);
		this.control = control;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(window.getX() + 10, window.getY() + 10, 156, 155);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblInfo = new JLabel("send me an alarm ...");
			lblInfo.setBounds(10, 11, 123, 14);
			contentPanel.add(lblInfo);
		}
		{
			JLabel lblIn = new JLabel("in:");
			lblIn.setBounds(10, 36, 46, 14);
			contentPanel.add(lblIn);
		}
		{
			JLabel lblAt = new JLabel("at:");
			lblAt.setBounds(10, 61, 46, 14);
			contentPanel.add(lblAt);
		}
		
		txtIn = new JTextField();
		txtIn.setBounds(37, 33, 96, 20);
		contentPanel.add(txtIn);
		txtIn.setColumns(10);
		
		txtAt = new JTextField();
		txtAt.setColumns(10);
		txtAt.setBounds(37, 58, 96, 20);
		contentPanel.add(txtAt);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ok();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}
	
	private void cancel() {
		this.dispose();
	}
	
	private void ok() {
		if (control.newTimeLeft(txtIn.getText()) || control.newTimeEnd(txtAt.getText())) {
			this.dispose();
		} else {
			lblInfo.setText("invalid input");
			lblInfo.setForeground(Color.RED);
		}
	}
}
