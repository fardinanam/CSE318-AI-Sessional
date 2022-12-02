#ifndef NODE_H
#define NODE_H

#include <iostream>
// #include <pair>
#include <vector>

int calculateHammingDistance(int k, int** source, int** destination) {
    int totalDistance = 0;
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            if (source[i][j] != 0 && source[i][j] != destination[i][j])
                totalDistance++;
        }
    }

    return totalDistance;
}

int calculateManhattanDistance(int k, int** source, int** destination) {
    int* rowMajorOrderIndices = new int[k * k];  // indices of this array represents the numbers in the grid
    int count = 0;
    int distance = 0;

    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            rowMajorOrderIndices[destination[i][j]] = count++;
        }
    }

    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            if (source[i][j] == 0) continue;

            int rowIdx = rowMajorOrderIndices[source[i][j]] / k;
            int columnIdx = rowMajorOrderIndices[source[i][j]] % k;
            distance += abs(rowIdx - i) + abs(columnIdx - j);
        }
    }

    delete[] rowMajorOrderIndices;
    return distance;
}

class Node {
private:
    int k; // length of square grid
    int moves; // number of moves to reach the current config from the initial config
    std::pair<int, int> blankTile; // row and column index of the blank tile
    int** grid;
    int** goalGrid;
    Node* previous;
public:
    Node(int k, int** grid, int** goalGrid) {
        this->moves = 0;
        this->k = k;
        previous = nullptr;
        this->goalGrid = goalGrid;

        this->grid = new int*[k];
        for (int i = 0; i < k; i++) {
            this->grid[i] = new int[k];
            for (int j = 0; j < k; j++) {
                this->grid[i][j] = grid[i][j];
                if(grid[i][j] == 0) {
                    blankTile.first = i;
                    blankTile.second = j;
                }
            }
        }
    }

    Node(int k, int** grid, int moves, Node &previous, int** goalGrid) : Node(k, grid, goalGrid) {
        this->moves = moves;
        this->previous = &previous;
    }

    Node(const Node &other) {
        this->k = other.k;
        this->moves = other.moves;
        this->previous = other.previous;

        this->grid = new int*[k];
        for (int i = 0; i < k; i++) {
            this->grid[i] = new int[k];
            for (int j = 0; j < k; j++) {
                this->grid[i][j] = other.grid[i][j];
            }
        }
    }

    int** getGrid() {
        int** newGrid = new int*[k];
        for(int i = 0; i < k; i++) {
            newGrid[i] = new int[k];
            for(int j = 0; j < k; j++) {
                 newGrid[i][j] = grid[i][j];
            }
        }

        return newGrid;
    }

    void printGrid() {
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(grid[i][j] == 0) {
                    std::cout << "* ";
                } else {
                    std::cout << grid[i][j] << " ";
                }
            }
            std::cout << std::endl;
        }
    }

    int calculateManhattanCost() {
        return moves + calculateManhattanDistance(k, grid, goalGrid);
    }

    int calculateHammingCost() {
        return moves + calculateHammingDistance(k, grid, goalGrid);
    }

    bool isEqual(Node* other) {
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(grid[i][j] != other->grid[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    std::vector<Node*> getChildren() {
        std::vector<Node*> children;
        
        // up
        if(blankTile.first > 0) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first - 1][blankTile.second];
            newGrid[blankTile.first - 1][blankTile.second] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this, goalGrid));
        }

        // down
        if(blankTile.first < k - 1) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first + 1][blankTile.second];
            newGrid[blankTile.first + 1][blankTile.second] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this, goalGrid));
        }

        // left
        if(blankTile.second > 0) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first][blankTile.second - 1];
            newGrid[blankTile.first][blankTile.second - 1] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this, goalGrid));
        }

        // right
        if(blankTile.second < k - 1) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first][blankTile.second + 1];
            newGrid[blankTile.first][blankTile.second + 1] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this, goalGrid));
        }

        return children;
    }

    bool operator == (Node* other) {
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(grid[i][j] != other->grid[i][j])
                    return false;
            }
        }

        return true;
    }

    int getMoves() {
        return moves;
    }

    Node* getPrevious() {
        return previous;
    }

    ~Node() {
        for(int i = 0; i < k; i++) {
            delete[] grid[i];
        }
        delete[] grid;
    }
};

#endif