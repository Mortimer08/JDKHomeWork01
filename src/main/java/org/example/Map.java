package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPY_DOT = 0;
    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private boolean isGameOver;
    private boolean isInitialized;
    private static final String MSG_WIN_HUMAN = "Human won!";
    private static final String MSG_WIN_AI = "Computer won!";
    private static final String MSG_DRAW = "It's draw!";
    private int fieldSizeX = 3;
    private int fieldSizeY = 3;
    private int winLen;
    private int[][] field;

    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;
    private static final int DOT_PADDING = 5;

    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);

            }
        });
        isInitialized = false;
    }

    private void initMap(int fieldSizeX, int fieldSizeY, int winLen) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLen = winLen;
        field = new int[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPY_DOT;
            }
        }
    }

    //region logic
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPY_DOT;
    }

    private boolean checkWin(int dot) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLen, dot)) return true;
                if (checkLine(i, j, 1, 1, winLen, dot)) return true;
                if (checkLine(i, j, 0, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, -1, winLen, dot)) return true;
            }
        }
        return false;
    }
    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot){
        int far_x = x + (len - 1) * vx;
        int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y)){
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != dot){
                return false;
            }
        }
        return true;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPY_DOT) return false;
            }
        }
        return true;
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }
        if (isMapFull()) {
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    private void aiTurn() {
        int cellX, cellY;
        do {
            cellX = RANDOM.nextInt(fieldSizeX);
            cellY = RANDOM.nextInt(fieldSizeY);
        } while (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY));
        field[cellY][cellX] = AI_DOT;
        System.out.println("AI moves: x=" + cellX + " y=" + cellY + " " + field[cellY][cellX]);
    }

    //endregion
    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
        System.out.println("Human moves: x=" + cellX + " y=" + cellY + " " + field[cellX][cellY]);
        repaint();
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (checkEndGame(AI_DOT, STATE_WIN_AI)) return;
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen) {
        System.out.printf("Mode: %d, size: %d x %d, win len = %d\n", mode, fSzX, fSzY, wLen);
        initMap(fSzX,fSzY,wLen);
        isGameOver = false;
        isInitialized = true;

        repaint();
    }

    //region rendering
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        render(graphics);
    }

    void render(Graphics graphics) {
        if (!isInitialized) return;
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;
        graphics.setColor(Color.BLACK);
        for (int h = 0; h < fieldSizeY; h++) {
            int y = h * cellHeight;
            graphics.drawLine(0, y, panelWidth, y);
        }
        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            graphics.drawLine(x, 0, x, panelHeight);
        }
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPY_DOT) continue;
                if (field[y][x] == HUMAN_DOT) {
                    graphics.setColor(Color.BLUE);
                    graphics.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == AI_DOT) {
                    graphics.setColor(new Color(0xff0000));
                    graphics.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Unexpected value " + field[y][x] + " in cell: x=" + x + ", y=" + y);
                }
                ;
            }
            ;
        }
        if (isGameOver) showMessageGameOver(graphics);
    }

    private void showMessageGameOver(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 200, getWidth(), 70);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(new Font("Tames new roman", Font.BOLD, 48));
        switch (gameOverType) {
            case STATE_DRAW:
                graphics.drawString(MSG_DRAW, 180, getHeight() / 2);
                break;
            case STATE_WIN_HUMAN:
                graphics.drawString(MSG_WIN_HUMAN, 70, getHeight() / 2);
                break;
            case STATE_WIN_AI:
                graphics.drawString(MSG_WIN_AI, 20, getHeight() / 2);
                break;
            default:
                throw new RuntimeException("Unexpected game state: " + gameOverType);
        }
    }
    //endregion
}


