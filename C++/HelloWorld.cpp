#include <iostream>
#include <cctype>
#include <sstream>
#include <cmath> 

using namespace std;

const int PLAYER_ID_NONE = -1;
const int PLAYER_ID_1 = 1;
const int PLAYER_ID_2 = 2;

const string ERROR_COLOUR = "36";
const string PLAYER_1_COLOUR = "31";
const string PLAYER_2_COLOUR = "33";

const int HEADER_LEFT_PADDING = 3;
const char PAWN = 'P';
const char EMPTY = '_';
const string HORIZONTAL_GRID_VALUES = "abcdefgh";
const string SPECIAL_PIECES = "RKB$QBKR";

char** board = new char*[8];
int** playerPieceIds = new int*[8];
int currentPlayerTurnId = PLAYER_ID_1;
ostringstream error;
ostringstream player1CapturedPieces;
ostringstream player2CapturedPieces;

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

void printHorizontalBorder(char borderSymbol) {
    // Print the horizontal border.
    print(' ', HEADER_LEFT_PADDING);
    for (int i = 0 ; i < HORIZONTAL_GRID_VALUES.length() ; i++) {
        cout<<' '<<borderSymbol;
    }
    cout<<endl;
}

void printCapturedPieces() {
    if (player1CapturedPieces.str() != "") {
        cout<<"Player 1 lost pieces: ";
        setColor(PLAYER_1_COLOUR);
        cout<<player1CapturedPieces.str()<<endl;
        resetColor();
    }
    if (player2CapturedPieces.str() != "") {
        cout<<"Player 2 lost pieces: ";
        setColor(PLAYER_2_COLOUR);
        cout<<player2CapturedPieces.str()<<endl;
        resetColor();
    }
}

void printBoard() {
    // Print horizontal grid
    print(' ', HEADER_LEFT_PADDING);
    for (int i = 0 ; i < HORIZONTAL_GRID_VALUES.length() ; i++) {
        cout<<' '<<HORIZONTAL_GRID_VALUES[i];
    }
    cout<<endl;

    printHorizontalBorder('_');

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

    printHorizontalBorder('-');
    printCapturedPieces();
}

void printError() {
    setColor(ERROR_COLOUR);
    cout<<error.str()<<endl;
    resetColor();
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
        error<<"Error: " <<rowLabel<<" is not a valid row label";
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
        error<<"Error: "<<colLabel<<" is not a valid column label.";
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
        error<<"Error: userCoordinate must be of length 2";
        return coordinates;
    }
    coordinates[0] = convertRowLabelToRowIndex(userCoordinate[1]);
    coordinates[1] = convertColLabelToColIndex(userCoordinate[0]);
    return coordinates;
}

char getPiece(int* coord) {
    return board[coord[0]][coord[1]];
}

char getPiece(int row, int col) {
    return board[row][col];
}

int getPlayerPieceId(int* coord) {
    return playerPieceIds[coord[0]][coord[1]];
}

void setPiece(int* coord, char piece, int playerId) {
    board[coord[0]][coord[1]] = piece;
    playerPieceIds[coord[0]][coord[1]] = playerId;
}

bool isValidKnightMove(int* coordFrom, int* coordTo) {
    return (abs(coordTo[0] - coordFrom[0]) == 1 && abs(coordTo[1] - coordFrom[1]) == 2)
        || (abs(coordTo[0] - coordFrom[0]) == 2 && abs(coordTo[1] - coordFrom[1]) == 1);
}

bool isValidRookMove(int* coordFrom, int* coordTo) {
    bool isValidDirection = false;
    bool isColliding = false;
    if (coordFrom[0] == coordTo[0] && coordFrom[1] != coordTo[1]) {
        // moving horizontally
        isValidDirection = true;
        int direction = coordTo[1] - coordFrom[1];
        if (direction > 0) {
            // moving right
            for (int col = (coordFrom[1] + 1) ; col <= (coordTo[1] - 1) ; col++) {
                char piece = getPiece(coordFrom[0], col);
                if (piece != EMPTY) {
                    isColliding = true;
                    break;
                }
            }
        } else if (direction < 0) {
            // moving left
        }
    } else if (coordFrom[1] == coordTo[1] && coordFrom[0] != coordTo[0]) {
        // moving vertically
        isValidDirection = true;

    }
    return isValidDirection && !isColliding;
}

bool isValidMove(char piece, int playerId, int* coordFrom, int* coordTo) {
    bool isValid = true;

    switch(piece) {
       case 'K':
        isValid = isValidKnightMove(coordFrom, coordTo);
        break;
       case 'R':
        isValid = isValidRookMove(coordFrom, coordTo);
        break;
    }    

    if (!isValid) {
        error<<piece<<" cannot be moved in that way. Try again.";
    }
    return isValid;
}

void movePiece(int* coordFrom, int* coordTo) {
    // Check if piece at coordFrom exists.
    char fromPiece = getPiece(coordFrom);
    if (fromPiece == EMPTY) {
        error<<"No piece to move at given coord";
        return;
    }

    int fromPiecePlayerId = getPlayerPieceId(coordFrom);
    if (fromPiecePlayerId == PLAYER_ID_NONE) {
        error<<"No player piece to move at given coord";
        return;
    }
    if (fromPiecePlayerId != currentPlayerTurnId) {
        error<<"Piece belonging to player "<<fromPiecePlayerId<<" cannot be moved by player "<<currentPlayerTurnId;
        return;
    }
    int toPiecePlayerId = getPlayerPieceId(coordTo);
    if (toPiecePlayerId != PLAYER_ID_NONE && toPiecePlayerId == currentPlayerTurnId) {
        error<<"you cannot take your own piece";
        return;
    }

    // Validate direction and collision of piece
    if (!isValidMove(fromPiece, currentPlayerTurnId, coordFrom, coordTo)) {
        return;
    }

    // Move piece
    char toPiece = getPiece(coordTo);
    if (toPiecePlayerId != PLAYER_ID_NONE) {
        // Track this captured piece
        if (toPiecePlayerId == PLAYER_ID_1) {
            player1CapturedPieces<<" "<<toPiece;
        } else {
            player2CapturedPieces<<" "<<toPiece;
        }
    }

    // Remove piece from coordFrom
    setPiece(coordFrom, EMPTY, PLAYER_ID_NONE);
    // Add piece to coordTo
    setPiece(coordTo, fromPiece, fromPiecePlayerId);
}

void clearError() {
    error.str("");
    error.clear();
}

void switchPlayerTurn() {
    if (currentPlayerTurnId == PLAYER_ID_1) {
        currentPlayerTurnId = PLAYER_ID_2;
    } else {
        currentPlayerTurnId = PLAYER_ID_1;
    }
}

int main() {
    intializeBoard();
    while (true) {
        printBoard();
        if (error.str() != "") {
            printError();
            clearError();
        } else {
            switchPlayerTurn();
        }
        cout<<"Player "<<currentPlayerTurnId<<" what is your move?"<<endl;
        string input;
        cin>>input;
        if (input.length() != 4) {
            error<<"Error: your command must be 4 chars in length (eg. a6a6)";
            continue;
        }
        int* coordFrom = getBoardIndexCoordinates(input.substr(0,2));
        int* coordTo = getBoardIndexCoordinates(input.substr(2,4));
        movePiece(coordFrom, coordTo);
        clearScreen();
    }
    return 0;
}
