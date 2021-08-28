#include <iostream>

using namespace std;

const char PAWN = 'P';
const char EMPTY = '_';
const string SPECIAL_PIECES = "RKBQ$BKR";

char** board = new char*[8];

void intializeBoard() {
    for (int row = 0 ; row < 8 ; row++) {
        board[row] = new char[8];
        for (int col = 0 ; col < 8 ; col++) {
            board[row][col] = 'X';
        }
    }
}

void printBoard() {
    for (int row = 0 ; row < 8 ; row++) {
        for (int col = 0 ; col < 8 ; col++) {
            cout<<' '<<board[row][col];
        }
        cout<<endl;
    }
}

int main() {
    intializeBoard();
    printBoard();
    return 0;
}
