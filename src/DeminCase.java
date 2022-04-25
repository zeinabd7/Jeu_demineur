import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeminCase
    extends JPanel
    implements MouseListener {

  private int etat = 0; //0 = rien; 1==cliquer; 2=drapeau; 3=?; 4=boum; 5=mine; 6=erreur de drapeau
  private boolean mine = false; //Si il y a une mine
  private boolean selected = false;
  private boolean blocked = false;
  private int chiffre = 0;

  private Graphisme gr = null; //l'objet qui contient les graphismes.

  public DeminCase() {
    try {
      //construction de la case
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(Graphisme.dessus);
    this.setMaximumSize(new Dimension(16, 16)); //On impose la taille
    this.setMinimumSize(new Dimension(16, 16));
    this.addMouseListener(this);
    this.setPreferredSize(new Dimension(16, 16));
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    //Selectionne la case si on clique dessus
    if (e.getModifiers() == 16 && etat != 1 && etat != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseReleased(MouseEvent e) {
    //D�selctionne la cases
    selected = false;
    repaint();
  }

  public void mouseEntered(MouseEvent e) {
    //Si la case est relev�e est que la souris passe dessus avec le clic gauche, on s�l�ctionne
    if (e.getModifiers() == 16 && etat != 1 && etat != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseExited(MouseEvent e) {
    //pas fin mais efficace
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

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    this.paintComponent(this.getGraphics());
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gr != null) {
      if (!selected) { //case non enfonc�e
        if (etat == 0) { //normal
          g.setColor(Color.white); //bordure haut et gauche blanche
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
        else if (etat == 1) g.drawImage(gr.chiffre[chiffre], 0, 0, null); //chiffre ou vide
        else if (etat == 2) g.drawImage(gr.drapeau, 0, 0, null); //drapeau
        else if (etat == 6) g.drawImage(gr.erreur, 0, 0, null); //erreur de drapeau
        else if (etat == 3) g.drawImage(gr.question, 0, 0, null); //?
        else if (etat == 4) g.drawImage(gr.boum, 0, 0, null); //mine sur fond rouge
        else if (etat == 5) g.drawImage(gr.mine, 0, 0, null); //mine
      }
      else { //case enfonc�e
        if (etat == 3) g.drawImage(gr.questionSel, 0, 0, null); //?
        else if (etat != 1) {
          g.setColor(Color.gray);
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
      }
    }

    g.setColor(Color.darkGray); //bordure bas et droite
    g.drawLine(0, 15, 15, 15);
    g.drawLine(15, 0, 15, 15);
    g.dispose();
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setGraphisme(Graphisme gr) {
    this.gr = gr;
  }

  public void reset() { //remise � zero des principaux param�tres
    this.etat = 0;
    this.selected = false;
    setMine(false);
    setBlocked(false);
    //repaint();
  }
}


