package testes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Connections {

    ConnectionsPanel connectionsPanel;
 
    public Connections()
    {
        connectionsPanel = new ConnectionsPanel();
        JFrame f = new JFrame("Connection");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(getUIPanel(), "North");
        f.getContentPane().add(connectionsPanel);
        f.setSize(400,300);
        f.setLocation(200,200);
        f.setVisible(true);
    }
 
    private JPanel getUIPanel()
    {
        JButton connect = new JButton("make connection");
        connect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                connectionsPanel.selectingLabelsForConnection = true;
            }
        });
        JPanel panel = new JPanel();
        panel.add(connect);
        return panel;
    }
 
    public static void main(String[] args)
    {
        new Connections();
    }
}
 
class ConnectionsPanel extends JPanel
{
    JLabel label1, label2, label3, label4;
    JLabel[] labels;
    JLabel selectedLabel;
    int offsetX, offsetY;
    boolean dragging, selectingLabelsForConnection;
    List<Component[]> connections;
 
    public ConnectionsPanel()
    {
        dragging = false;
        selectingLabelsForConnection = false;
        connections = new ArrayList<Component[]>();
        setOpaque(false);
        setLayout(null);
        addLabels();
        addMouseListener(new LabelSelector());
        addMouseMotionListener(new LabelMover());
    }
 
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.blue);
        for(int j = 0; j < connections.size(); j++)
        {
            Component[] pair = connections.get(j);
            Point p1 = getCenter(pair[0]);
            Point p2 = getCenter(pair[1]);
            g2.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
        }
    }
 
    private Point getCenter(Component c)
    {
        Rectangle r = c.getBounds();
        return new Point((int)r.getCenterX(), (int)r.getCenterY());
    }
 
    private class LabelSelector extends MouseAdapter
    {
        Component[] components;
        boolean haveSelection;
 
        public LabelSelector()
        {
            haveSelection = false;
        }
 
        public void mousePressed(MouseEvent e)
        {
            Point p = e.getPoint();
            for(int j = 0; j < labels.length; j++)
            {
                Rectangle r = labels[j].getBounds();
                if(r.contains(p))
                {
                    if(selectingLabelsForConnection)
                    {
                        if(!haveSelection)
                        {
                            components = new Component[2];
                            components[0] = labels[j];
                        }
                        else
                        {
                            components[1] = labels[j];
                            if(components[0] != components[1])
                                connections.add(components);
                            selectingLabelsForConnection = false;
                            repaint();
                        }
                        haveSelection = !haveSelection;
                    }
                    else    // selecting for dragging
                    {
                        selectedLabel = labels[j];
                        offsetX = p.x - r.x;
                        offsetY = p.y - r.y;
                        dragging = true;
                        break;
                    }
                }
            }
        }
 
        public void mouseReleased(MouseEvent e)
        {
            dragging = false;
        }
    }
 
    private class LabelMover extends MouseMotionAdapter
    {
        public void mouseDragged(MouseEvent e)
        {
            if(dragging)
            {
                int x = e.getX() - offsetX;
                int y = e.getY() - offsetY;
                Rectangle r = selectedLabel.getBounds();
                selectedLabel.setBounds(x, y, r.width, r.height);
                repaint();
            }
        }
    }
 
    private void addLabels()
    {
        int w = 125;
        int h = 25;
        Border border = BorderFactory.createEtchedBorder();
        label1 = new JLabel("Label 1", JLabel.CENTER);
        label2 = new JLabel("Label 2", JLabel.CENTER);
        label3 = new JLabel("Label 3", JLabel.CENTER);
        label4 = new JLabel("Label 4", JLabel.CENTER);
        labels = new JLabel[4];
        labels[0] = label1;
        labels[1] = label2;
        labels[2] = label3;
        labels[3] = label4;
        for(int j = 0; j < labels.length; j++)
        {
            labels[j].setBorder(border);
            labels[j].setOpaque(true);
            labels[j].setBackground(Color.white);
            add(labels[j]);
        }
        label1.setBounds(40, 40, w, h);
        label2.setBounds(215, 40, w, h);
        label3.setBounds(40, 200, w, h);
        label4.setBounds(215, 200, w, h);
    }
}
