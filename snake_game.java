import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class snake_game extends JPanel 
{
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int DOT_SIZE = 20;
    private static final int GAME_UNITS = WIDTH * HEIGHT;
    private static final int DELAY = 100;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int snakeSize = 3;
    private int foodEaten;
    private int foodX;
    private int foodY;
    private char direction = 'R';
    private boolean isRunning;
    private Timer timer;
    public snake_game() 
    {
        setPreferredSize(new Dimension(WIDTH * DOT_SIZE, HEIGHT * DOT_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() 
        {
            public void keyPressed(KeyEvent e) 
            {
                moveSnake(e.getKeyCode());
            }
        });

        startGame();
    }
    private void startGame() 
    {
        isRunning = true;
        newFood();
        timer = new Timer(DELAY, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                if (isRunning) 
                {
                    move();
                    checkFood();
                    checkCollision();
                    repaint();
                }
            }
        });
        timer.start();
    }

    private void moveSnake(int keyCode) 
    {
        switch (keyCode) 
        {
            case KeyEvent.VK_LEFT:
                if (direction != 'R')
                    direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L')
                    direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D')
                    direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U')
                    direction = 'D';
                break;
        }
    }

    private void move() 
    {
        for (int i = snakeSize; i > 0; i--) 
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) 
        {
            case 'U':
                y[0] -= DOT_SIZE;
                break;
            case 'D':
                y[0] += DOT_SIZE;
                break;
            case 'L':
                x[0] -= DOT_SIZE;
                break;
            case 'R':
                x[0] += DOT_SIZE;
                break;
        }
    }

    private void checkFood() 
    {
        if (x[0] == foodX && y[0] == foodY) 
        {
            snakeSize++;
            foodEaten++;
            newFood();
        }
    }

    private void checkCollision() 
    {
        // Check if snake collides with itself
        for (int i = snakeSize; i > 0; i--) 
        {
            if (x[0] == x[i] && y[0] == y[i]) 
            {
                isRunning = false;
                break;
            }
        }

        // Check if snake collides with boundaries
        if (x[0] < 0 || x[0] >= WIDTH * DOT_SIZE || y[0] < 0 || y[0] >= HEIGHT * DOT_SIZE) 
        {
            isRunning = false;
        }
    }

    private void newFood() 
    {
        foodX = (int) (Math.random() * WIDTH) * DOT_SIZE;
        foodY = (int) (Math.random() * HEIGHT) * DOT_SIZE;
    }
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        for (int i = 0; i < snakeSize; i++) {
            g.fillOval(x[i], y[i], DOT_SIZE, DOT_SIZE);
        }

        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, DOT_SIZE, DOT_SIZE);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + foodEaten, 10, 20);

        if (!isRunning) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over!", WIDTH * DOT_SIZE / 2 - 100, HEIGHT * DOT_SIZE / 2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Snake Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.add(new snake_game());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
