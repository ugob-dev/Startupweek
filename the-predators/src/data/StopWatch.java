package data;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatch {
	
	static int interval;
	static Timer timer;
	
	public StopWatch()
	{
		init();
	}
	
	public void init()
	{
		 int delay = 1000;
	    int period = 1000;
	    timer = new Timer();
	    interval =10000;
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	         System.out.println(setInterval());
      }

    private final int setInterval(){
	    if( interval== 1) timer.cancel();
	      return --interval;
	  }
	    }, delay, period);
	}

}
