// Класс, где игра всё будет делать
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game extends JFrame {
    public final int standartCount = 3;
    private final int SIZE_IMAGE = 70; // Размер одной картинки
    private final int WIDTH = SIZE_IMAGE * standartCount; // Ширина
    private final int HEIGHT = WIDTH; // Высота
    private Object back; // Для загрузки и инициализации фона
    private PlayersControl players; // Данные об игроках и их настройки
    public static int countStep;
    private int countClick;

    public Game () {
        countClick = 0;
        initPics(); // Инициализируем картинки
        initPanel(); // Инициализируем поле игры
        players = new PlayersControl(standartCount, countClick);
        countStep = 0; // Считаем ходы
        initFrame(); // Инициализируем окно игры и настраиваем его
    }

    private void initPanel() {
        JPanel place = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage((Image) back, 0, 0, this); // фон
                // Разметка
                g.setColor(Color.WHITE);
                for (int i = 1; i < standartCount; i++) {
                    g.drawLine(SIZE_IMAGE * i, 0, SIZE_IMAGE * i, Game.this.HEIGHT);
                    g.drawLine(0, SIZE_IMAGE * i, Game.this.WIDTH, SIZE_IMAGE * i);
                }

                // Рисуем
                for (int i = 0; i < standartCount; i++) {
                    for (int j = 0; j < standartCount; j++) {
                        if (PlayersControl.placeGame[i][j] != -1) {
                            g.drawImage((Image) Pictures.values()[PlayersControl.placeGame[i][j]].image,
                                    j * SIZE_IMAGE, i * SIZE_IMAGE, this);
                        }
                    }
                }

                isWin();
                isLose();
            }
        };
        place.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(place, BorderLayout.CENTER);

        // Обрабатываем клик
        place.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(players.getClick() == 1){
                    repaint();
                    players.setClick(0);
                    return;
                }
                countStep++;
                int x = e.getY() / SIZE_IMAGE;
                int y = e.getX() / SIZE_IMAGE;
                players.changeQueue(x, y);
                repaint();
            }
        });
    }

    private void isLose() {
        int count = 0;

        for (int i = 0; i < standartCount; i++) {
            for (int j = 0; j < standartCount; j++) {
                if(PlayersControl.placeGame[i][j] != -1) count++;
            }
        }

        // Ничья
        if(count == standartCount * standartCount) {
            countStep = 0;
            players = new PlayersControl(standartCount, 1);
        }
    }

    private void isWin() {
        if(players.isWinPlayer1() || players.isWinPlayer2()) {
            countStep = 0;
            players = new PlayersControl(standartCount, 1);
        }
    }

    // Инициализируем картинки
    private void initPics() {
        Random random = new Random();

        // Инициализация крестиков-ноликов
        for (Pictures pictures : Pictures.values()) {
            String file = pictures.name() + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(file));
            pictures.image = icon.getImage();
        }

        // Инициализация задников
        for (Backgrounds back : Backgrounds.values()) {
            String file = "bg\\" + back.name() + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(file));
            back.image = icon.getImage();
        }

        back = Backgrounds.values()[random.nextInt(Backgrounds.values().length)].image;  // Устанавливаем фон
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cross - Zero");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
}
