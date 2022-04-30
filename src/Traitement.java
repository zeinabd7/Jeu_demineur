import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Traitement extends JPanel implements MouseListener {

  private int etat = 0;
  private boolean mine = false;
  private boolean selected = false;
  private boolean blocked = false;
  private int chiffre = 0;

  private Design gr = null;

  public Traitement() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(Design.dessus);
    this.addMouseListener(this);
    this.setPreferredSize(new Dimension(16, 16));
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    if (e.getModifiers() == 16 && etat != 1 && etat != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseReleased(MouseEvent e) {
    selected = false;
    repaint();
  }

  public void mouseEntered(MouseEvent e) {
    if (e.getModifiers() == 16 && etat != 1 && etat != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseExited(MouseEvent e) {
    selected = false;
    repaint();
  }

  public boolean isMine() {
    return mine;
  }

  public int getEtat() {
    return etat;
  }

  public void setEtat(int etat) {
    this.etat = etat;
  }

  public void setMine(boolean mine) {
    this.mine = mine;
  }

  public int getChiffre() {
    return chiffre;
  }

  public void setChiffre(int chiffre) {
    this.chiffre = chiffre;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    this.paintComponent(this.getGraphics());
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gr != null) {
      if (!selected) {
        if (etat == 0) {
          g.setColor(Color.white);
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
        else if (etat == 1) g.drawImage(gr.chiffre[chiffre], 0, 0, null);
        else if (etat == 2) g.drawImage(gr.drapeau, 0, 0, null);
        else if (etat == 6) g.drawImage(gr.erreur, 0, 0, null);
        else if (etat == 3) g.drawImage(gr.question, 0, 0, null);
        else if (etat == 4) g.drawImage(gr.boum, 0, 0, null);
        else if (etat == 5) g.drawImage(gr.mine, 0, 0, null);
      }
      else {
        if (etat == 3) g.drawImage(gr.questionSel, 0, 0, null);
        else if (etat != 1) {
          g.setColor(Color.gray);
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
      }
    }

    g.dispose();
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setGraphisme(Design gr) {
    this.gr = gr;
  }

  public void reset() {
    this.etat = 0;
    this.selected = false;
    setMine(false);
    setBlocked(false);

  }
}
