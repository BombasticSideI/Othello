class Board {
    private static final int SIZE = 8;
    private Position[][] positions;


    public Board() {
        positions = new Position[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                positions[row][col] = new PlayablePosition();
            }
        }

        //unplayable positions
        for (int row = 0; row < SIZE; row++) {
            positions[row][0] = new Position();
            positions[row][0].setDisk('*');
        }


        // Setting starting positions
            positions[3][3].setDisk('X');
            positions[3][4].setDisk('O');
            positions[4][3].setDisk('O');
            positions[4][4].setDisk('X');

    }

    public void draw() {
        System.out.print("  ");
        for (int col = 0; col < SIZE; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        for (int row = 0; row < SIZE; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < SIZE; col++) {
                System.out.print(positions[row][col].getDisk() + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int row, int col, char disk) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || !positions[row][col].canPlay()) {
            return false;
        }
        return checkDirections(row, col, disk, false);
    }

    public void makeMove(int row, int col, char disk) {
        positions[row][col].setDisk(disk);
        checkDirections(row, col, disk, true);
    }

    private boolean checkDirections(int row, int col, char disk, boolean flip) {
        boolean valid = false;
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},{0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            boolean hasOpponentDisk = false;

            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && positions[r][c].getDisk() != disk && positions[r][c].getDisk() != '_') {
                hasOpponentDisk = true;
                r += dir[0];
                c += dir[1];
            }

            if (hasOpponentDisk && r >= 0 && r < SIZE && c >= 0 && c < SIZE && positions[r][c].getDisk() == disk) {
                valid = true;
                if (flip) {
                    r = row + dir[0];
                    c = col + dir[1];
                    while (positions[r][c].getDisk() != disk) {
                        positions[r][c].setDisk(disk);
                        r += dir[0];
                        c += dir[1];
                    }
                }
            }
        }
        return valid;
    }

    public boolean hasValidMoves(char disk) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isValidMove(row, col, disk)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return !hasValidMoves('X') && !hasValidMoves('O');
    }

    public String getWinner(Player player1, Player player2) {
        int countX = 0;
        int countO = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (positions[row][col].getDisk() == 'X') {
                    countX++;
                } else if (positions[row][col].getDisk() == 'O') {
                    countO++;
                }
            }
        }
        if (countX > countO) {
            return player1.getName();
        } else if (countO > countX) {
            return player2.getName();
        } else {
            return "The Game is a Draw";
        }
    }

    public void loadBoardState(String state) {
        int index = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                positions[row][col].setDisk(state.charAt(index++));
            }
        }
    }

    public String getBoardState() {
        StringBuilder state = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                state.append(positions[row][col].getDisk());
            }
        }
        return state.toString();
    }
}