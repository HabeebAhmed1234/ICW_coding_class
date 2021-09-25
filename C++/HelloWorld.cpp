#include <iostream>
#include <cctype>

using namespace std;

const int PLAYER_ID_NONE = -1;
const int PLAYER_ID_1 = 1;
const int PLAYER_ID_2 = 2;

const string PLAYER_1_COLOUR = "31";
const string PLAYER_2_COLOUR = "33";

const int HEADER_LEFT_PADDING = 3;
const char PAWN = 'P';
const char EMPTY = '_';
const string HORIZONTAL_GRID_VALUES = "abcdefgh";
const string SPECIAL_PIECES = "RKB$QBKR";

char** board = new char*[8];
int** playerPieceIds = new int*[8];

void intializeBoard() {
    for (int row = 0 ; row < 8 ; row++) {
        board[row] = new char[8];
        playerPieceIds[row] = new int[8];
        for (int col = 0 ; col < 8 ; col++) {
            char cellValue = EMPTY; 
            switch(row) {
                case 0:
                    cellValue = SPECIAL_PIECES[col];
                    break;
                case 7:
                    cellValue = SPECIAL_PIECES[col];
                    break;
                case 1:
                case 6:
                    cellValue = PAWN;
                    break;
            }
            board[row][col] = cellValue;
            int playerId = PLAYER_ID_NONE;
            if (row <= 1) {
                // This piece belongs to player 1
                playerId = PLAYER_ID_1;
            } else if (row >= 6) {
                // This piece belongs to player 2
                playerId = PLAYER_ID_2;
            }
            playerPieceIds[row][col] = playerId;
        }
    }
}

// Prints the given char 'repeat' number of times
void print(char c, int repeat) {
    for (int i = 0 ; i < repeat ; i++) {
        cout<<c;
    }
}

// Converts a row index (0-7) into a row label that
// is shown to the user (8-1).
int convertRowIndexToRowLabel(int rowIndex) {
    return 8 - rowIndex;
}

void resetColor() {
    cout<<"\033[0m";
}

void setColor(string colorCode) {
    cout<<"\033[0;"<<colorCode<<"m";
}

void printPlayerPiece(char piece, int playerId) {
    setColor(playerId == PLAYER_ID_1
        ? PLAYER_1_COLOUR : PLAYER_2_COLOUR);
    cout<<piece;
    resetColor();
}

void printBoard() {
    // Print horizontal grid
    print(' ', HEADER_LEFT_PADDING);
    for (int i = 0 ; i < HORIZONTAL_GRID_VALUES.length() ; i++) {
        cout<<' '<<HORIZONTAL_GRID_VALUES[i];
    }
    cout<<endl;

    // Print the horizontal border.
    print(' ', HEADER_LEFT_PADDING);
    for (int i = 0 ; i < HORIZONTAL_GRID_VALUES.length() ; i++) {
        cout<<' '<<'_';
    }
    cout<<endl;

    // Print the board
    for (int row = 0 ; row < 8 ; row++) {
        // print the row number
        cout<<convertRowIndexToRowLabel(row)<<" |";
        for (int col = 0 ; col < 8 ; col++) {
            char cellValue = board[row][col];
            cout<<' ';
            if (cellValue == EMPTY) {
                cout<<"\u25A2";
            } else {
                printPlayerPiece(
                    cellValue, playerPieceIds[row][col]);
            }
        }
        cout<<endl;
    }
}

void clearScreen() {
    for (int i = 0 ; i < 50 ; i++) {
        cout<<endl;
    }
}

// Converts a row label (8 - 1) into a row index (0 -7).
int convertRowLabelToRowIndex(char rowLabel) {
    if (rowLabel >= '1' && rowLabel <= '8') {
        int rowLabelInteger = rowLabel - '0';
        return (rowLabelInteger - 8) / -1;
    } else {
        cout<<"Error: " <<rowLabel<<" is not a valid row label"<<endl;
        return -1;
    }
}

// Converts a label for a column (a - h or A - H) into an index (0-7).
int convertColLabelToColIndex(char colLabel) {
    if ((colLabel >= 'A' && colLabel <= 'H')
     || (colLabel >= 'a' && colLabel <= 'h')) {
        char colLabelLowerCase = tolower(colLabel);
        return colLabelLowerCase - 'a';
    } else {
        cout<<"Error: "<<colLabel<<" is not a valid column label."<<endl;
        return -1;
    }
}

// Returns an integer array [row][col] given a user
// typed board coordinate.
int* getBoardIndexCoordinates(string userCoordinate) {
    int* coordinates = new int[2];
    coordinates[0] = -1;
    coordinates[1] = -1;
    if (userCoordinate.length() != 2) {
        cout<<"Error: userCoordinate must be of length 2"<<endl;
        return coordinates;
    }
    coordinates[0] = convertRowLabelToRowIndex(userCoordinate[1]);
    coordinates[1] = convertColLabelToColIndex(userCoordinate[0]);
    return coordinates;
}

char getPiece(int* coord) {
    return board[coord[0]][coord[1]];
}

int getPlayerPieceId(int* coord) {
    return playerPieceIds[coord[0]][coord[1]];
}

void setPiece(int* coord, char piece, int playerId) {
    board[coord[0]][coord[1]] = piece;
    playerPieceIds[coord[0]][coord[1]] = playerId;
}

void movePiece(int* coordFrom, int* coordTo) {
    // Check if piece at coordFrom exists.
    char fromPiece = getPiece(coordFrom);
    if (fromPiece == EMPTY) {
        cout<<"No piece to move at given coord"<<endl;
        return;
    }

    int fromPiecePlayerId = getPlayerPieceId(coordFrom);
    if (fromPiecePlayerId == PLAYER_ID_NONE) {
        cout<<"No player piece to move at given coord"<<endl;
        return;
    }

    // Remove piece from coordFrom
    setPiece(coordFrom, EMPTY, PLAYER_ID_NONE);
    // Add piece to coordTo
    setPiece(coordTo, fromPiece, fromPiecePlayerId);
}

int main() {
    intializeBoard();
    while (true) {
        printBoard();
        string input;
        cin>>input;
        if (input.length() != 4) {
            cout<<"Error: your command must be 4 chars in length (eg. a6a6)"<<endl;
            continue;
        }
        int* coordFrom = getBoardIndexCoordinates(input.substr(0,2));
        int* coordTo = getBoardIndexCoordinates(input.substr(2,4));
        movePiece(coordFrom, coordTo);
        clearScreen();
    }
    return 0;
}
