/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.ERROR;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author moraj0721
 */
public class project extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    int screen = 0;
    // player
    Rectangle player = new Rectangle(400, 550, 50, 50);
    int speed = 2;
    int RandomX = 0;
    int RandomY = 0;
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    int gravity = 3;
    int frameCount = 0;
    int startOfScreen = 100;
    int endOfScreen = 700;
    //keyboard variables
    boolean right = false;
    boolean left = false;
    int x = 50;
    // block
    ArrayList<Rectangle> carrots = new ArrayList<Rectangle>();
    //load in game border
    BufferedImage border = loadImage("border.png");
    //Load in title game title
    BufferedImage carrotMania = loadImage("carrot mania.png");
    //start screen enter
    BufferedImage enter = loadImage("enter.png");
    //Start screen press
    BufferedImage press = loadImage("press1.png");
    //Bunny rabbit right
    BufferedImage bunnyRight = loadImage("bunnyrabbitRIGHT.png");
    //Bunny rabbit left
    BufferedImage bunnyLeft = loadImage("bunnyrabbitLEFT.png");
    //load in title border
    BufferedImage carrot = loadImage("pixelcarrot.png");
    //load in bunny forward
    BufferedImage bunnyForward = loadImage("bunyforward.png");
    private boolean buttonPressed;
    private int randNum;

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 


        //START SCREEN//////////
        if (screen == 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);


            g.drawImage(carrot, 128, 105, 50, 50, null);
            g.drawImage(carrot, 128, 145, 50, 50, null);
            g.drawImage(carrot, 128, 185, 50, 50, null);

            g.drawImage(carrot, 610, 105, 50, 50, null);
            g.drawImage(carrot, 610, 145, 50, 50, null);
            g.drawImage(carrot, 610, 185, 50, 50, null);

            g.drawImage(carrotMania, 200, 103, 380, 150, null);

            g.drawImage(enter, 230, 400, 300, 100, null);

            g.drawImage(press, 230, 300, 300, 100, null);

        }

        ///LEVEL 1\\\
        if (screen == 1) {

            //background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);

            //bottom border //top border
            g.drawImage(border, 0, 550, 50, 50, null);
            for (int x = 0; x < 800; x = x + 50) {
                g.drawImage(border, x, 550, 50, 50, null);
                g.drawImage(border, x, 0, 50, 50, null);
            }

            //Right side border //Left side border

            for (int sideY = 500; sideY >= 0; sideY = sideY - 50) {
                g.drawImage(border, 750, sideY, 50, 50, null);
                g.drawImage(border, 0, sideY, 50, 50, null);
            }


            g.drawImage(border, 750, 0, 50, 50, null);

            if (right) {
                g.drawImage(bunnyRight, player.x, player.y, player.width, player.height, null);
            } else if (left) {
                g.drawImage(bunnyLeft, player.x, player.y, player.width, player.height, null);
            } else {
                g.drawImage(bunnyForward, player.x, player.y, player.width, player.height, null);
            }

            
                g.drawImage(carrot, randNum, 50, 50, 50, null);

            
        }



        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            if (player.x == 50) {
                player.x = player.x + 10;

            }

            if (player.x == 700) {
                player.x = player.x - 10;

            }



            //Carrots
            // block


            if (screen == 1) {
                if (left) {
                    player.x = player.x - 2;
                } else if (right) {
                    player.x = player.x + 2;
                } else {
                    player.x = player.x + 0;
                }






                // if feet of player become lower than the ground   
                if (player.y + player.height > 550) {
                    // stops the falling
                    player.y = 550 - player.height;
                    moveY = 0;
                    inAir = false;
                }



                if (screen == 1) {


                    randNum = (int) (Math.random() * (endOfScreen - startOfScreen + 1)) + startOfScreen;
                

                }

                // GAME LOGIC ENDS HERE 

                // update the drawing (calls paintComponent)
                repaint();



                // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
                // USING SOME SIMPLE MATH
                deltaTime = System.currentTimeMillis() - startTime;
                try {
                    if (deltaTime > desiredTime) {
                        //took too much time, don't wait
                        Thread.sleep(1);
                    } else {

                        Thread.sleep(desiredTime - deltaTime);

                    }
                } catch (Exception e) {
                };



            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        project game = new project();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (screen == 0) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                screen = 1;

            }
        } else {


            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                left = true;

            } else if (key == KeyEvent.VK_RIGHT) {
                right = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;

        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }
}
