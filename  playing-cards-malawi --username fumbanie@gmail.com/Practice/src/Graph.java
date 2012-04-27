import java.awt.*;
import javax.swing.*;

public class Graph extends JFrame {
 private JPanel frame;
 private JPanel panel;


 public Graph() {
  super();
  initializeComponent();
  this.setVisible(true);
 }

 private void initializeComponent() {
  frame = (JPanel)this.getContentPane();
  panel = new JPanel();
  
  frame.setBackground(Color.white);
  
  addComponent(frame, panel, 0,0,600,400);
  panel.setBackground(Color.black);
  panel.add( new GamePanel() );
  
  this.setTitle("Android FUMBA Emulator");
  this.setLocation(new Point(0, 0));
  this.setSize(new Dimension(600, 400));
  this.setResizable(false);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }

 private void addComponent(Container container,Component c,int x,int y,int width,int height) {
  c.setBounds(x,y,width,height);
  container.add(c);
 }


 
 public static void main(String[] args) {
  new Graph();
 }
 
}
