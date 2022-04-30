import javax.swing.*;
public class Temps implements Runnable {
  Thread thread;
  JLabel aff;
  boolean marche=true;
  boolean threadSuspended=false;

  public Temps(JLabel compteur) {
    aff=compteur;
  }
  public void run() {
    while (marche) {
      try {
        thread.sleep(1000);
        if (threadSuspended) {
          synchronized(this) {
            while (threadSuspended)
              wait();
          }
        }
      }
      catch(java.lang.InterruptedException e) {}
      int time = Integer.valueOf(aff.getText());
      if (marche && time<999) {
        aff.setText(String.valueOf(time+1));
      }

    }
  }
  public void start() {
    if (thread==null) thread = new Thread(this);
    thread.setPriority(thread.MAX_PRIORITY);
    thread.start();
  }
  public void stop() {
    if (thread!=null) thread = null;
  }
  public void cancel() {
    marche=false;
  }
  public void suspend() {
    threadSuspended=true;
  }
  public synchronized void resume() {
    threadSuspended=false;
    notify();
  }
}
