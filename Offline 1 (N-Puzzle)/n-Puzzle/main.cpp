#include <iostream>
#include <queue>
#include "node.h"
#include "nPuzzle.h"
using namespace std;

int main() {
    int k;
    int **initialBoard;
    cin >> k;
    initialBoard = new int*[k];
    for(int i = 0; i < k; i++) {
        initialBoard[i] = new int[k];
        for(int j = 0; j < k; j++) {
            int n;
            cin >> n;
            initialBoard[i][j] = n == '*' ? 0 : n;
        }
    }
    
    NPuzzle p(k);
    
    cout << "\nUsing Manhattan heuristic:" << endl;
    p.solveWithManhattanDistance(initialBoard);
    cout << "\nUsing Hamming heuristic:" << endl;
    p.solveWithHammingDistance(initialBoard);
}
