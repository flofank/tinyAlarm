import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Control {
	private MainWindow window;
	private Time tEnd;
	private Clock clk;
	private SoundMachine sound;
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			Control c = new Control();
		} catch (URISyntaxException e) {}
	}
	
	public Control() throws URISyntaxException {
		window = new MainWindow(this);
		window.setVisible(true);
		clk = new Clock();
		//sound = new SoundMachine(getClass().getResource("alarm.wav").getPath());
		sound = new SoundMachine(getClass().getResourceAsStream("alarm.wav"));
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void newAlarm() {
		// TODO Auto-generated method stub
		try {
			clk.stop();
		} catch (Exception e) {}
		
		clk = new Clock();
		CreateAlarmDialog dl = new CreateAlarmDialog(this, window);
		clk.start();
	}
	
	public boolean newTimeEnd(String time) {
		try {
			String[] parts = time.split(":");
			tEnd = new Time("00:00:00");
			tEnd.setHours(Integer.parseInt(parts[0]));
			try {
				tEnd.setMinutes(Integer.parseInt(parts[1]));
				tEnd.setSeconds(Integer.parseInt(parts[2]));
			} catch (Exception e1) {
				//ignore
			}
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	public boolean newTimeLeft(String time) {
		tEnd = new Time();
		if (time.contains("s") || time.contains("sec") ||  time.contains("m") || time.contains("min") || time.contains("h")) {
			return parseInputString(time);	
		} else {
			try {
				String[] parts = time.split(":");
				tEnd.addSeconds(Integer.parseInt(parts[parts.length-1]));
				try {
					tEnd.addMinutes(Integer.parseInt(parts[parts.length-2]));
					tEnd.addHours(Integer.parseInt(parts[parts.length-3]));
				} catch (Exception e1) {
					//ignore
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	private boolean parseInputString(String raw) {
		boolean found = false;
		Pattern h = Pattern.compile("([\\d]*)[ ]{0,1}h");
		Pattern min = Pattern.compile("([\\d]*)[ ]{0,1}[min]{1,3}");
		Pattern sec = Pattern.compile("([\\d]*)[ ]{0,1}[seck]{1,3}");
		Matcher m = h.matcher(raw);
		m.find();
		try {
			tEnd.addHours(Integer.parseInt(m.group(1)));
			found = true;
		} catch (Exception e) {}
		m = min.matcher(raw);
		m.find();
		try {
			tEnd.addMinutes(Integer.parseInt(m.group(1)));
			found = true;
		} catch (Exception e) {}
		m = sec.matcher(raw);
		m.find();
		try {
			tEnd.addSeconds(Integer.parseInt(m.group(1)));
			found = true;
		} catch (Exception e) {}
		
		return found;
	}

	private void updateClocks () {
		Time tNow = new Time();
		Time tLeft = tEnd.getTimeFrom(tNow);
		window.setTimes(tLeft.toString(), tNow.toString(), tEnd.toString());
		window.repaint();
		if (tLeft.isNoTime()) {
			alarm();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void alarm() {
		window.setVisible(true);
		sound.start();
		clk.stop();
	}

	private class Clock extends Thread {
		private boolean running;
		private int errorCount;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			running = true;
			while(running) {
				updateClocks();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					errorCount++;
					if (errorCount > 500) {
						System.exit(0);
					}
				}
			}
		}		
	}
}
