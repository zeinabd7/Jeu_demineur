
import java.util.*;
import javax.swing.*;


public class TestDemineur {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new Demineur(16,16,40,2);
  }
}


