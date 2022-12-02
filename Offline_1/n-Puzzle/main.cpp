#include <iostream>
#include <queue>
#include "node.h"
#include "nPuzzle.h"
using namespace std;

int main() {
    int k;
    int **initialBoard;
    int **goalBoard;
    cin >> k;
    initialBoard = new int*[k];
    goalBoard = new int*[k];
    for(int i = 0; i < k; i++) {
        initialBoard[i] = new int[k];
        goalBoard[i] = new int[k];
        for(int j = 0; j < k; j++) {
            int n;
            cin >> n;
            initialBoard[i][j] = n == '*' ? 0 : n;
        }
    }
    for(int i = 0; i < k; i++) {
        for(int j = 0; j < k; j++) {
            int n;
            cin >> n;
            goalBoard[i][j] = n == '*' ? 0 : n;
        }
    }
    cout << "Initial Board:" << endl;
    // int** arr1 = new int*[3];
    // int** arr2 = new int*[3];
    // for(int i=0; i<3; i++) {
    //     arr1[i] = new int[3];
    //     arr2[i] = new int[3];
    // }

    // arr1[0][0] = 0;
    // arr1[0][1] = 1;
    // arr1[0][2] = 3;
    // arr1[1][0] = 4;
    // arr1[1][1] = 2;
    // arr1[1][2] = 5;
    // arr1[2][0] = 7;
    // arr1[2][1] = 8;
    // arr1[2][2] = 6;

    // arr2[0][0] = 1;
    // arr2[0][1] = 2;
    // arr2[0][2] = 3;
    // arr2[1][0] = 4;
    // arr2[1][1] = 5;
    // arr2[1][2] = 6;
    // arr2[2][0] = 7;
    // arr2[2][1] = 8;
    // arr2[2][2] = 0;
    
    // Node s(3, arr1, arr2);
    // Node d(3, arr2, arr2);
    // s.printGrid();
    // cout << endl;
    // d.printGrid();
    // cout << endl;
    // cout << endl;
    // cout << calculateHammingDistance(3, s.getGrid(), d.getGrid()) << "," << calculateManhattanDistance(3, s.getGrid(), d.getGrid()) << endl;
    // cout << calculateHammingDistance(3, d.getGrid(), s.getGrid()) << ","
    //      << calculateManhattanDistance(3, d.getGrid(), s.getGrid()) << endl;
    
    NPuzzle p(k, new Node(k, goalBoard, goalBoard));
    
    Node* initial = new Node(k, initialBoard, goalBoard);
    cout << "solving..." << endl;
    p.solveWithHammingDistance(initial);
}
