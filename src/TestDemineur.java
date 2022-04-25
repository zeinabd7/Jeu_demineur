
//public class Main {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub
//Frame frame =new Frame ("Demineur");
//new Demineur (12,16,20) {
	
//}
//	}
//import com.incors.plaf.kunststoff.*; //look kunststoff si vous avez
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
    new Demineur(16,30,99,3);//Yalla na nieuw waaaay
  }
}


