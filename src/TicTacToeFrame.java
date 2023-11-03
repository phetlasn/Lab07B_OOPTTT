import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame {
    Border empty;
    JPanel mainPanel, titlePanel, gamePanel, messagePanel, countPanel;
    JPanel controlPanel;

    JLabel title;
    JLabel message;
    JLabel xWins, oWins, tieCountLabel;

    JButton quitButton, clearButton;

    JTextField xWinCountField, oWinCountField, tieCountField;

    ImageIcon image;

    int xWinCount = 0;
    int oWinCount = 0;
    int tieCount = 0;
    int numberTurns = 0;

    public static final int ROW = 3;
    public static final int COL = 3;

    public static String[][] board = new String[ROW][COL];
    private static final JButton[][] GUIboard = new JButton[ROW][COL];
    String player = "X";
    boolean again = false;

    public TicTacToeFrame() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        createTitlePanel();
        mainPanel.add(titlePanel);

        createMessagePanel();
        mainPanel.add(message);

        createDisplay();
        mainPanel.add(gamePanel);

        createCountPanel();
        mainPanel.add(countPanel);

        createControlPanel();
        mainPanel.add(controlPanel);

        add(mainPanel);
        mainPanel.setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
        setLocationRelativeTo(null);
    }

    private void createTitlePanel() {
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        title = new JLabel("Tic Tac Toe", image, JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 45));
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        titlePanel.add(title);
    }

    private void createDisplay() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setBackground(Color.WHITE);
        CreateGame();
    }

    private void createMessagePanel() {
        messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        message = new JLabel("Player " + player + ", it is your turn!", null, JLabel.CENTER);
        message.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setVerticalTextPosition(JLabel.BOTTOM);
        messagePanel.add(message);
    }

    private void createCountPanel() {
        countPanel = new JPanel();
        countPanel.setBackground(Color.WHITE);
        countPanel.setLayout(new GridLayout(0, 1));

        xWins = new JLabel("Player X Win Count", JLabel.CENTER);
        xWins.setFont(new Font("Dialog", Font.PLAIN, 16));
        xWins.setVerticalTextPosition(JLabel.BOTTOM);
        xWins.setHorizontalTextPosition(JLabel.CENTER);

        xWinCountField = new JTextField();
        xWinCountField.setHorizontalAlignment(JTextField.CENTER);
        empty = BorderFactory.createEmptyBorder();
        xWinCountField.setBorder(empty);
        xWinCountField.setFont(new Font("Dialog", Font.PLAIN, 18));
        xWinCountField.setEditable(false);

        tieCountLabel = new JLabel("Tie Count", JLabel.CENTER);
        tieCountLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        tieCountLabel.setVerticalTextPosition(JLabel.BOTTOM);
        tieCountLabel.setHorizontalTextPosition(JLabel.CENTER);

        tieCountField = new JTextField();
        tieCountField.setHorizontalAlignment(JTextField.CENTER);
        tieCountField.setBorder(empty);
        tieCountField.setFont(new Font("Dialog", Font.PLAIN, 18));
        tieCountField.setEditable(false);

        oWins = new JLabel("Player O Win Count", JLabel.CENTER);
        oWins.setFont(new Font("Dialog", Font.PLAIN, 16));
        oWins.setVerticalTextPosition(JLabel.BOTTOM);
        oWins.setHorizontalTextPosition(JLabel.CENTER);

        oWinCountField = new JTextField();
        oWinCountField.setHorizontalAlignment(JTextField.CENTER);
        oWinCountField.setBorder(empty);
        oWinCountField.setFont(new Font("Dialog", Font.PLAIN, 18));
        oWinCountField.setEditable(false);

        countPanel.add(xWins);
        countPanel.add(xWinCountField);
        countPanel.add(tieCountLabel);
        countPanel.add(tieCountField);
        countPanel.add(oWins);
        countPanel.add(oWinCountField);

        xWinCountField.setText("0");
        oWinCountField.setText("0");
        tieCountField.setText("0");
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        controlPanel.setBackground(Color.WHITE);
        clearButton = new JButton("Reset");
        clearButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        clearButton.addActionListener((ActionEvent ae) -> {
            int res = JOptionPane.showOptionDialog(null, "Do you want to reset the game board and scores?", "Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                clearBoard();
                for (int row1 = 0; row1 <= 2; row1++) {
                    for (int col1 = 0; col1 <= 2; col1++) {
                        GUIboard[row1][col1].setText(" ");
                    }
                }

                oWinCountField.setText("0");
                xWinCountField.setText("0");
                tieCountField.setText("0");
                oWinCount = 0;
                xWinCount = 0;
                tieCount = 0;
                JOptionPane.showMessageDialog(null, "Game Board & Scores Reset", "Message", JOptionPane.INFORMATION_MESSAGE, image);
            }

            else if (res == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Reset Request Canceled", "Message", JOptionPane.INFORMATION_MESSAGE, image);
            }

            else if (res == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Reset Request Canceled", "Message", JOptionPane.INFORMATION_MESSAGE, image);
            }
        });

        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        quitButton.addActionListener((ActionEvent ae) -> {
            int res = JOptionPane.showOptionDialog(null, "Are You Sure You Want To Quit?", "Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

            else if (res == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Quit Request Canceled", "Message", JOptionPane.INFORMATION_MESSAGE, image);
            }

            else if (res == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Reset Request Canceled", "Message", JOptionPane.INFORMATION_MESSAGE, image);
            }
        });

        controlPanel.add(quitButton);
        controlPanel.add(clearButton);
    }

    public static void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }
    }

    private void CreateGame() {
        do {
            clearBoard();
            numberTurns = 0;
            for (int row = 0; row <= 2; row++) {
                for (int col = 0; col <= 2; col++) {
                    GUIboard[row][col] = new TicTacToeButton(row, col);
                    GUIboard[row][col].setBorder(new LineBorder(Color.BLUE));
                    GUIboard[row][col].setForeground(Color.BLACK);
                    GUIboard[row][col].setFont(new Font("Times New Roman", Font.ITALIC, 25));
                    gamePanel.add(GUIboard[row][col]);
                    GUIboard[row][col].setText(" ");
                    GUIboard[row][col].addActionListener(e -> {
                        TicTacToeButton clicked = (TicTacToeButton) e.getSource();
                        JFrame frame = new JFrame("JOptionPane");
                        numberTurns++;

                        if (!clicked.getText().isBlank()) {
                            JOptionPane.showMessageDialog(frame, "Spot Already Taken! Make A Valid Move!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        clicked.setText(String.valueOf(player));
                        TicTacToeWins.validMove(player, clicked.getRow(), clicked.getCol());

                        if (numberTurns >= 5 ) {
                            if (TicTacToeWins.isWin(player)) {
                                int res = JOptionPane.showOptionDialog(frame, "Player " + player + " Wins!\nWant To Play Again?", "Results", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
                                if (res == JOptionPane.YES_OPTION) {
                                    again = true;
                                } else if (res == JOptionPane.NO_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                } else if (res == JOptionPane.CLOSED_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                }

                                if (player.equals("X")) {
                                    xWinCount = xWinCount + 1;
                                    xWinCountField.setText(String.valueOf(xWinCount));
                                }

                                if (player.equals("O")) {
                                    oWinCount = oWinCount + 1;
                                    oWinCountField.setText(String.valueOf(oWinCount));
                                }

                                clearBoard();
                                numberTurns = 0;
                                for (int row1 = 0; row1 <= 2; row1++) {
                                    for (int col1 = 0; col1 <= 2; col1++) {
                                        GUIboard[row1][col1].setText(" ");
                                    }
                                }

                                player = "O";
                                message.setText("Player " + player + ", It's Your Turn!");
                            }
                        } if (numberTurns >= 7  ) {

                            if (TicTacToeWins.isWin(player)) {
                                int res = JOptionPane.showOptionDialog(frame, "Player " + player + " Wins!\nWant To Play Again?", "Results", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
                                if (res == JOptionPane.YES_OPTION) {
                                    again = true;
                                } else if (res == JOptionPane.NO_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                } else if (res == JOptionPane.CLOSED_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                }

                                if (player.equals("X")) {
                                    xWinCount = xWinCount + 1;
                                    xWinCountField.setText(String.valueOf(xWinCount));
                                }

                                if (player.equals("O")) {
                                    oWinCount = oWinCount + 1;
                                    oWinCountField.setText(String.valueOf(oWinCount));
                                }

                                clearBoard();
                                numberTurns = 0;
                                for (int row1 = 0; row1 <= 2; row1++) {
                                    for (int col1 = 0; col1 <= 2; col1++) {
                                        GUIboard[row1][col1].setText(" ");
                                    }
                                }

                                player = "O";
                                message.setText("Player " + player + ", It's Your Turn!");
                            }

                            if (TicTacToeWins.isTie()) {
                                int res = JOptionPane.showOptionDialog(frame, "Tie! \nWant To Play Again?", "Results", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
                                if (res == JOptionPane.YES_OPTION) {
                                    again = true;
                                } else if (res == JOptionPane.NO_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                } else if (res == JOptionPane.CLOSED_OPTION) {
                                    clearBoard();
                                    numberTurns = 0;
                                    for (int row1 = 0; row1 <= 2; row1++) {
                                        for (int col1 = 0; col1 <= 2; col1++) {
                                            GUIboard[row1][col1].setText(" ");
                                        }
                                    }
                                    System.exit(0);
                                }

                                tieCount = tieCount + 1;
                                tieCountField.setText(String.valueOf(tieCount));
                                clearBoard();
                                numberTurns = 0;
                                for (int row1 = 0; row1 <= 2; row1++) {
                                    for (int col1 = 0; col1 <= 2; col1++) {
                                        GUIboard[row1][col1].setText(" ");
                                    }
                                }

                                player = "O";
                                message.setText("Player " + player + ", It's Your Turn!");
                            }
                        }

                        if (player.equals("X")) {
                            player = "O";
                        } else {
                            player = "X";
                        }

                        message.setText("Player " + player + ", It's Your Turn!");
                    });
                }
            }
        } while (again);
    }


}
