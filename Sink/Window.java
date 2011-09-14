import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Window extends JFrame {
    private DrawPanel dp;
    
    public Window(){
        setTitle("DM827");
        setLocation(100, 50);
        
        dp = new DrawPanel(100, 100);

        Container contents = getContentPane();
        contents.add(dp, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void drawMotes(ArrayList l) {
        dp.drawMotes(l);
        dp.repaint();
    }

   
    
    private class DrawPanel extends JPanel {
        private int gridWidth, gridHeight;
        Dimension size;
        private Graphics g;
        private Image fieldImage;
        ArrayList motes;

        public DrawPanel(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
            motes = new ArrayList();
        }

        public void drawMotes(ArrayList l) {
            motes = l;
            repaint();
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(100, 100);
        }
        /*
        public void preparePaint() {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = this.createImage(size.width, size.height);
                g = fieldImage.getGraphics();
            }
            g.setColor(Color.blue);
            g.fillRect(0,0,10,10);//size.width, size.height);
            g.setColor(Color.red);
            g.fillRect(0, 0, 110, 110);
        }
        */
        
        private void paintMote(Mote m, Graphics g) {
            g.setColor(Color.blue);
            g.fillRect(m.x, m.y, 10, 10);
            g.drawOval(m.x - m.dist /2, m.y - m.dist/2, m.dist, m.dist);
        }

        private void paintMotes(Graphics g) {
            Dimension size = getSize();
            g.setColor(Color.black);
            g.fillRect(0, 0, size.width, size.height);

            for (int i = 0; i < motes.size(); i++){
                paintMote((Mote)motes.get(i),g);
            }
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            Dimension size = getSize();
            Image i = createImage(size.width, size.height);
            Graphics ig = i.getGraphics();
            paintMotes(ig);
            g.drawImage(i,0,0,null);
            /*
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                } else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
            */
        }
    }
    /*
    public static void main(String[] args) {
        Window w = new Window(100,100);
        w.drawMotes();
    }
    */
}
