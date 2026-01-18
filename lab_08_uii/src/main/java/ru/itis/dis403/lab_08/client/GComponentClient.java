package ru.itis.dis403.lab_08.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GComponentClient extends JComponent {

    int x, y, bounds = 64;
    boolean move_up, move_left;
    int speed = 3;
    int scoreServer;
    int scoreClient;
    final int bXS = 10;
    int bXC;
    int bYS = 300, bYC = 300;

    Image image;

    Socket socket;

    public GComponentClient() throws IOException {

        socket = new Socket("127.0.0.1", 50000);

        image = new ImageIcon("ball.png").getImage();

        setDoubleBuffered(true);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                bYC = e.getY();
            }
        });

        new Thread(() -> {
            while (true) {
                DataInputStream dis = null;
                try {
                    dis = new DataInputStream(socket.getInputStream());
                    bYS = dis.readInt();
                    x = dis.readInt();
                    y = dis.readInt();
                    scoreServer = dis.readInt();
                    scoreClient = dis.readInt();
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(bYC);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                repaint();
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println(bYS);
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image,x,y,null);
        bXC = getWidth() - 20;
        Rectangle leftRectangle = new Rectangle(bXS, bYS, 10, 200);
        Rectangle rightRectangle = new Rectangle(bXC, bYC, 10, 200);
        g2d.setColor(Color.BLACK);
        g2d.fill(leftRectangle);
        g2d.fill(rightRectangle);
        g2d.drawString(scoreServer+" : " + scoreClient, getHeight()/2, getWidth()/2);


        g2d.dispose();

        Toolkit.getDefaultToolkit().sync();
    }

}