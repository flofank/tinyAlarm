import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8215973777232473220L;
	private Control control;
	private JLabel lblTimeLeft, lblTimeEnd, lblTimeNow;
	private TrayIcon ticon;
	
	
	public MainWindow(Control control) {
		super();
		setTitle("tinyAlarm");
		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("icon.gif")));
		} catch (Exception e) {}
		this.control = control;
		setResizable(false);
		getContentPane().setBackground(new Color(0, 128, 0));
		initWindow();
		try {
			initSysTray();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void setTimes(String tLeft, String tNow, String tEnd) {
		lblTimeLeft.setText(tLeft);
		lblTimeNow.setText(tNow);
		lblTimeEnd.setText(tEnd);
		ticon.setToolTip(tLeft);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initWindow() {
		setBounds(100, 100, 400, 190);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setLayout(null);
		JButton btnClose = new JButton(new ImageIcon(getClass().getResource("icon_close.gif")));
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		btnClose.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("icon_close_selected.gif")));
		btnClose.setBackground(null);
		btnClose.setBounds(386, 2, 12, 12);
		getContentPane().add(btnClose);
		
		JButton btnMini = new JButton(new ImageIcon(getClass().getResource("icon_minimize.gif")));
		btnMini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnMini.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("icon_minimize_selected.gif")));
		btnMini.setBackground(null);
		btnMini.setBounds(370, 2, 12, 12);
		getContentPane().add(btnMini);
		
		//Countdown
		lblTimeLeft = new JLabel("00:00:00");
		lblTimeLeft.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		lblTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeLeft.setForeground(Color.WHITE);
		lblTimeLeft.setFont(new Font("Cooper Black", Font.PLAIN, 50));
		lblTimeLeft.setBounds(40, 25, 320, 80);
		getContentPane().add(lblTimeLeft);
		
		//Actual Time
		lblTimeNow = new JLabel("00:00:00");
		lblTimeNow.setBorder(new LineBorder(new Color(255, 255, 255)));
		lblTimeNow.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeNow.setForeground(Color.WHITE);
		lblTimeNow.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblTimeNow.setBounds(95, 115, 100, 25);
		getContentPane().add(lblTimeNow);
		
		JLabel lblNow = new JLabel("Now");
		lblNow.setHorizontalAlignment(SwingConstants.CENTER);
		lblNow.setForeground(Color.WHITE);
		lblNow.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblNow.setBounds(40, 115, 50, 25);
		getContentPane().add(lblNow);
		
		//Finish Time
		lblTimeEnd = new JLabel("00:00:00");
		lblTimeEnd.setBorder(new LineBorder(new Color(255, 255, 255)));
		lblTimeEnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeEnd.setForeground(Color.WHITE);
		lblTimeEnd.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblTimeEnd.setBounds(255, 115, 100, 25);
		getContentPane().add(lblTimeEnd);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnd.setForeground(Color.WHITE);
		lblEnd.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblEnd.setBounds(200, 115, 50, 25);
		getContentPane().add(lblEnd);
		
		JButton btnNewAlarm = new JButton("New Alarm!");
		btnNewAlarm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.newAlarm();
			}
		});
		btnNewAlarm.setForeground(Color.WHITE);
		btnNewAlarm.setFocusPainted(false);
		btnNewAlarm.setBackground(null);
		btnNewAlarm.setBounds(255, 156, 105, 23);
		getContentPane().add(btnNewAlarm);
		
		JLabel lblTinyalarm = new JLabel("tinyAlarm 0.1");
		lblTinyalarm.setHorizontalAlignment(SwingConstants.LEFT);
		lblTinyalarm.setForeground(new Color(175, 238, 238));
		lblTinyalarm.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblTinyalarm.setBounds(10, 178, 95, 12);
		getContentPane().add(lblTinyalarm);
	}

	
	private void initSysTray() throws AWTException, IOException, URISyntaxException {
		if (!SystemTray.isSupported()) {
			System.out.println("System Tray is not supported");
			return;
		}
		PopupMenu pup = new PopupMenu();
		ticon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("icon.gif")));
		SystemTray tray = SystemTray.getSystemTray();
				
		// Create a pop-up menu components
		MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
			}
		});
        pup.add(exitItem);
        
        ticon.setPopupMenu(pup);
        ticon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) {
					setVisible(true);
				}
			}
		});
        ticon.setToolTip("no Alarm set");
        tray.add(ticon);   
	}
}
