#ifndef NODE_H
#define NODE_H

#include <iostream>
#include <vector>

int calculateHammingDistance(int k, int** source) {
    int distance = 0;
    int rowMajorIndex = 0;
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            if (source[i][j] && source[i][j] != rowMajorIndex + 1)
                distance++;
            rowMajorIndex++;
        }
    }

    return distance;
}

int calculateManhattanDistance(int k, int** source) {
    int distance = 0;

    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            int value = source[i][j];
            if (value == 0) continue;

            int rowIdx = (value - 1) / k;
            int columnIdx = (value - 1) % k;
            distance += abs(rowIdx - i) + abs(columnIdx - j);            
        }
    }

    return distance;
}

class Node {
private:
    int k; // length of square grid
    int moves; // number of moves to reach the current config from the initial config
    std::pair<int, int> blankTile; // row and column index of the blank tile
    int** grid;
    Node* previous;
public:
    Node(int k, int** grid) {
        this->moves = 0;
        this->k = k;
        previous = nullptr;

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

    Node(int k, int** grid, int moves, Node &previous) : Node(k, grid) {
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
        return moves + calculateManhattanDistance(k, grid);
    }

    int calculateHammingCost() {
        return moves + calculateHammingDistance(k, grid);
    }

    bool isEqual(Node* other) {
        if (other == nullptr)
            return false;
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(grid[i][j] != other->grid[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    bool isGoal() {
        int count = 1;
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(grid[i][j] && grid[i][j] != count) {
                    return false;
                }
                count++;
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
            children.push_back(new Node(k, newGrid, moves + 1, *this));

            for(int i = 0; i < k; i++) {
                delete[] newGrid[i];
            }
            delete[] newGrid;
        }

        // down
        if(blankTile.first < k - 1) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first + 1][blankTile.second];
            newGrid[blankTile.first + 1][blankTile.second] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this));

            for (int i = 0; i < k; i++) {
                delete[] newGrid[i];
            }
            delete[] newGrid;
        }

        // left
        if(blankTile.second > 0) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first][blankTile.second - 1];
            newGrid[blankTile.first][blankTile.second - 1] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this));

            for (int i = 0; i < k; i++) {
                delete[] newGrid[i];
            }
            delete[] newGrid;
        }

        // right
        if(blankTile.second < k - 1) {
            int** newGrid = getGrid();
            newGrid[blankTile.first][blankTile.second] = newGrid[blankTile.first][blankTile.second + 1];
            newGrid[blankTile.first][blankTile.second + 1] = 0;
            children.push_back(new Node(k, newGrid, moves + 1, *this));

            for (int i = 0; i < k; i++) {
                delete[] newGrid[i];
            }
            delete[] newGrid;
        }

        return children;
    }

    int getGridSize() {
        return k;
    }

    int getMoves() {
        return moves;
    }

    std::pair<int, int> getBlankTile() {
        return blankTile;
    }

    Node* getPrevious() {
        return previous;
    }

    ~Node() {
        // delete grid
        for(int i = 0; i < k; i++) {
            delete[] grid[i];
        }
        delete[] grid;
    }
};

#endif