#include <iostream>
#include <cctype>

using namespace std;

const int HEADER_LEFT_PADDING = 3;
const char PAWN = 'P';
const char EMPTY = '_';
const string HORIZONTAL_GRID_VALUES = "abcdefgh";
const string SPECIAL_PIECES = "RKB$QBKR";

char** board = new char*[8];

void intializeBoard() {
    for (int row = 0 ; row < 8 ; row++) {
        board[row] = new char[8];
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
                cout<<cellValue;
            }
        }
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

int main() {
    intializeBoard();
    printBoard();
    string input;
    cin>>input;
    int* coord = getBoardIndexCoordinates(input);
    cout<<board[coord[0]][coord[1]]<<endl;
    return 0;
}
