import java.util.Date;


public class Time {
	private int hours, minutes, seconds;
	
	@SuppressWarnings("deprecation")
	public Time() {
		//long millis = System.currentTimeMillis();
		Date d = new Date();
		seconds = d.getSeconds();
		minutes = d.getMinutes();
		hours = d.getHours();
// Leider geht bei dieser Variante die Sommer-Winterzeit verloren
//		seconds = (int) ((millis / 1000) % 60);
//		minutes = (int) ((millis / 60000) % 60);
//		hours = (int) ((millis / 3600000) % 24);
	}
	
	/**
	 * Takes a Time String in Format HH:MM:SS
	 * @param time
	 */
	public Time(String time) {
		hours = Integer.parseInt(time.split(":")[0]);
		minutes = Integer.parseInt(time.split(":")[1]);
		seconds = Integer.parseInt(time.split(":")[2]);
	}
	
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void addTime(Time t) {
		addHours(t.getHours());
		addMinutes(t.getMinutes());
		addSeconds(t.getSeconds());
	}
	
	public void subtractTime(Time t) {
		subtractHours(t.getHours());
		subtractMinutes(t.getMinutes());
		subtractSeconds(t.getSeconds());	
	}
	
	public void addSeconds(int s) {
		int b = seconds + s;
		setSeconds(b%60);
		addMinutes(b/60);
		addHours(b/3600);
	}
	
	public void subtractSeconds(int s) {
		seconds -= s;
		int b = 0;
		while (seconds < 0) {
			seconds += 60;
			b++;
		}
		subtractMinutes(b);
	}
	
	public void addMinutes(int m) {
		int b = minutes + m;
		setMinutes(b%60);
		addHours(b/60);
	}
	
	public void subtractMinutes(int m) {
		minutes -= m;
		int b = 0;
		while (minutes < 0) {
			minutes += 60;
			b++;
		}
		subtractHours(b);
	}
	
	public void addHours(int h) {
		int b = hours + h;
		setHours(b%24);
	}
	
	public void subtractHours(int h) {
		hours -= h;
		if (hours < 0) {
			hours += 24;
		}
	}
	
	public Time getTimeFrom(Time t) {
		Time d = new Time(this.toString());
		d.subtractTime(t);
		return d;
	}
	
	public boolean isNoTime() {
		if (minutes == 0 && hours == 0 && seconds == 0) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String out = "";
		if (hours < 10) {
			out += "0";
		}
		out += hours + ":";
		if (minutes < 10) {
			out += "0";
		}
		out += minutes + ":";
		if (seconds < 10) {
			out += "0";
		}
		out += seconds;
		return out;
	}
}
