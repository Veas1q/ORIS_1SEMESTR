package ru.itis.dis403.lab_08.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GComponent extends JComponent{

    int x, y, bounds = 64;
    boolean move_up, move_left;
    int speed = 3;
    int scoreServer = 0;
    int scoreClient = 0;

    int bYS = 300, bYC = 300;

    final int bXS = 10;
    int bXC;

    private Socket clientSocket;

    Image image;

    public GComponent(Socket clientSocket) {

        this.clientSocket = clientSocket;

        image = new ImageIcon("ball.png").getImage();

        setDoubleBuffered(true);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                bYS = e.getY();
            }
        });


        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bXC = getWidth() - 20;
                Rectangle leftRectangle = new Rectangle(bXS, bYS, 10, 200);
                Rectangle rightRectangle = new Rectangle(bXC, bYC, 10, 200);
                Rectangle ball = new Rectangle(x,y, bounds, bounds);

               if (!move_left && rightRectangle.intersects(ball)) {
                   move_left = true;
               }
               if (move_left && leftRectangle.intersects(ball)) {
                   move_left = false;
               }

                if (x < 10) {
                    move_left = false;
                    scoreClient++;
                }

                if (x > getWidth() - bounds) {
                    move_left = true;
                    scoreServer++;
                }
                if (y < 0 ) {
                    move_up = false;
                }
                if (y > getHeight() - bounds) {
                    move_up = true;
                }

                if (move_left) {
                    x -= speed;
                } else {
                    x += speed;
                }

                if (move_up) {
                    y -= speed;
                } else {
                    y += speed;
                }

                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    dataOutputStream.writeInt(bYS);
                    dataOutputStream.writeInt(x);
                    dataOutputStream.writeInt(y);
                    dataOutputStream.writeInt(scoreServer);
                    dataOutputStream.writeInt(scoreClient);
                    DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    bYC = dataInputStream.readInt();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                repaint();
            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image,x,y,null);
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