import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;

    int playerStaerPosX = frameWidth/2;
    int playerStaerPosY = frameHeight/2;
    int playerWidth = 34;
    int playerHeight = 24;

    //Tambahkan atribut posisi dan ukuran pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer gameLoop;
    Timer pipeCooldown;
    int gravity = 1;

    int pipeVelocityX = -2;

    View view;
    Image birdImage;
    Player player;

    // Tambahkan list pipa dan gambarnya
    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    // State permainan
    int score = 0;
    boolean gameOver = false;
    boolean started = false; // false selama masih di Main Menu

    // Audio
    Sound bgm;
    Sound jumpSound;
    Sound pointSound;
    Sound gameOverSound;

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStaerPosX, playerStaerPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        // Muat audio
        bgm = new Sound("assets/bgm.wav");
        bgm.setVolume(-8f);
        jumpSound = new Sound("assets/jump.wav");
        pointSound = new Sound("assets/point.wav");
        gameOverSound = new Sound("assets/gameover.wav");

        pipeCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });

        gameLoop = new Timer(1000/60, this);
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // Dipanggil saat tombol "Mulai Permainan" ditekan
    public void startGame() {
        started = true;
        gameOver = false;
        pipeCooldown.start();
        gameLoop.start();
        bgm.loop();
    }

    // Dipanggil saat menekan tombol R setelah Game Over
    public void restartGame() {
        player.setPosX(playerStaerPosX);
        player.setPosY(playerStaerPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        if (view != null) {
            view.updateScore(score);
        }
        startGame();
    }

    private void endGame() {
        gameOver = true;
        gameLoop.stop();
        pipeCooldown.stop();
        bgm.stop();
        gameOverSound.play();
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowePipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth,
                pipeHeight, lowerPipeImage);
        pipes.add(lowePipe);
    }

    private boolean isColliding(Player p, Pipe pipe) {
        Rectangle playerRect = new Rectangle(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight());
        Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
        return playerRect.intersects(pipeRect);
    }

    public void move() {
        if (gameOver) return;

        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Tabrakan dengan pipa -> Game Over
            if (isColliding(player, pipe)) {
                endGame();
                return;
            }

            // Player melewati pipa -> skor +1
            // (pipa atas dan bawah berpasangan, jadi hitung dari pipa atas saja: index genap)
            if (!pipe.getPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true);
                if (i % 2 == 0) {
                    score++;
                    pointSound.play();
                    if (view != null) {
                        view.updateScore(score);
                    }
                }
            }
        }

        // Hapus pipa yang sudah keluar layar (selalu sepasang)
        while (pipes.size() >= 2 && pipes.get(0).getPosX() + pipeWidth < 0) {
            pipes.remove(0);
            pipes.remove(0);
        }

        // Jatuh ke batas bawah JFrame -> Game Over
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            player.setPosY(frameHeight - player.getHeight());
            endGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!started) return;

        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            }
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
            jumpSound.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }
}