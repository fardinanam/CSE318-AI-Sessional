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
    
    NPuzzle p(k, new Node(k, goalBoard, goalBoard));
    
    Node* initial = new Node(k, initialBoard, goalBoard);
    cout << "Manhattan" << endl;
    p.solveWithManhattanDistance(initial);
    cout << "Hamming" << endl;
    p.solveWithHammingDistance(initial);
}
