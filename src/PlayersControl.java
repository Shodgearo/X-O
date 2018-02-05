// �����, ��� �������� �� ����� ����

public class PlayersControl {
    private boolean winPlayer1, winPlayer2; // ������
    private int queue;
    public static int placeGame[][];
    private int click;

    public PlayersControl (int count, int click) {
        this.click = click;
        winPlayer1 = winPlayer2 = false; // �� ��������� ������ �� ��������
        queue = 0; // ������� �������� ������
        placeGame = new int[count][count]; // ������ ����� �����

        for (int i = 0; i < placeGame.length; i++) {
            for (int j = 0; j < placeGame.length; j++) {
                placeGame[i][j] = -1; // ����������������� -1, ����� ����� ���� � ����� ����� ���������
            }
        }
    }

    public void changeQueue(int x, int y) {
        // ���� ����� ������� ����, ��� ��� ���� ��������, �� ������ �� ������
        if(placeGame[x][y] != -1) return;

        placeGame[x][y] = queue; // ������ ����� �����

        // ���� ���-�� ��������� 3 ��������, �� ����������, ���� �� ����������
        if(Game.countStep >= 5) whenEndGame();

        // ������ ����������
        if(queue == 0) queue++;
        else queue--;

    }

    // ����� ���-������ �������?
    private void whenEndGame() {
        for (int i = 0; i < placeGame.length; i++) {
            if(winPlayer1 || winPlayer2) break;

            for (int j = 0; j < placeGame.length; j++) {
                if (winPlayer1 || winPlayer2) break;

                switch (i) {
                    case 0:
                        switch (j) {
                            case 0:
                                countValueForIterJZero(i, j);
                                countValueForIterIZero(i, j);
                                countValueForIterIJZero(i);
                                break;

                            case 1:
                                countValueForIterIZero(i, j);
                                break;

                            case 2:
                                countValueForIterIZero(i, j);
                                countValueForIterIZeroJLength(j);
                                break;
                        }
                        break;

                    case 1:
                        if(j == 0) countValueForIterJZero(i, j);
                        break;

                    case 2:
                        if(j == 0) countValueForIterJZero(i, j);
                        break;
                }
            }
        }
    }

    private void countValueForIterIZeroJLength(int j) {
        int count = 0;

        for (int k = 0; k < placeGame.length; k++, j--) {
            if (queue == placeGame[k][j]) count++;
        }

        if(count == 3) win();
    }

    private void countValueForIterIJZero(int i) {
        int count = 0;

        for (int k = i; k < placeGame.length; k++) {
            if (queue == placeGame[k][k]) count++;
        }

        if(count == 3) win();
    }

    private void countValueForIterIZero(int i, int j) {
        int count = 0;

        for (int k = i; k < placeGame.length; k++) {
            if (queue == placeGame[k][j]) count++;
        }

        if(count == 3) win();
    }

    private void countValueForIterJZero(int i, int j) {
        int count = 0;

        for (int k = j; k < placeGame.length; k++) {
            if (queue == placeGame[i][k]) count++;
        }

        if(count == 3) win();
    }

    private void win() {
        if(queue == 0) winPlayer1 = true;
        else winPlayer2 = true;
    }

    public boolean isWinPlayer1() {
        return winPlayer1;
    }

    public boolean isWinPlayer2() {
        return winPlayer2;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}
