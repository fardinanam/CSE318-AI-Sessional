#ifndef NPUZZLE_H
#define NPUZZLE_H

#include <iostream>
#include <string>
#include <queue>
#include <set>
#include <unordered_set>
#include <vector>

#include "node.h"

class NPuzzle {
   private:
    int k;  // length of square grid
    Node* goalBoard;
    struct manhattanComparator {
        bool operator()(Node* a, Node* b) {
            return a->calculateManhattanCost() > b->calculateManhattanCost();
        }
    };

    struct hammingComparator {
        bool operator()(Node* a, Node* b) {
            return a->calculateHammingCost() > b->calculateHammingCost();
        }
    };

    struct nodeComparator {
        bool operator()(Node* a, Node* b) const {
            if(a == nullptr || b == nullptr) return false;

            int k = a->getGridSize();
            if (k != b->getGridSize()) {
                std::cout << "Error: Grid sizes are not equal" << std::endl;
                return false;
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    if (a->getGrid()[i][j] != b->getGrid()[i][j]) {
                        return false;
                    }
                }
            }

            return true;
        }
    };

    struct nodeHasher {
        size_t operator()( Node* n) const {
            int32_t hash = 0;
            std::string s = "";
            for(int i = 0; i < n->getGridSize(); i++) {
                for(int j = 0; j < n->getGridSize(); j++) {
                    s += std::to_string(n->getGrid()[i][j]) + ",";
                }
            }
            for (int i = 0; i < s.length(); i++) {
                int c = s[i];
                hash = c + (hash << 6) + (hash << 16) - hash;
            }

            return hash;
        }
    };

    int countInversions(Node* node) {
        // O(n^2) solution
        int inversions = 0, n = k * k;
        int* nodeGridRowMajorOrder = new int[n];
        /* index of the array below represents the values from 0 to n-1
        value of every element of the array below represents the row major
        index of those values in the goalBoard
        */
        int* goalGridRowMajorOrderIndices = new int[n];
        int idx = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                nodeGridRowMajorOrder[idx] = node->getGrid()[i][j];
                goalGridRowMajorOrderIndices[goalBoard->getGrid()[i][j]] = idx++;
            }
        }

        // now check the orders w.r.t the goalGridRowMajorOrderIndices
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nodeGridRowMajorOrder[i] && nodeGridRowMajorOrder[j] &&
                    goalGridRowMajorOrderIndices[nodeGridRowMajorOrder[i]] >
                    goalGridRowMajorOrderIndices[nodeGridRowMajorOrder[j]])
                    inversions++;
            }
        }

        delete[] nodeGridRowMajorOrder;
        delete[] goalGridRowMajorOrderIndices;
        return inversions;
    }

    bool isSolvable(Node* initialBoard) {
        int inversions = countInversions(initialBoard);
        // if k is odd then no. of inversions should be even
        if (k % 2) {
            return !(inversions % 2);
        }

        /* if k is even then,
            if
                i.  the blank is on an even row counting from the bottom and
           number of inversions is odd, ii. the blank is on an odd row counting
           from the bottom and number of inversions is even. then it is solvable
        */
        // when k is even, even from the bottom means odd from the top
        if (initialBoard->getBlankTile().first % 2) {
            return inversions % 2;
        } else {
            return !(inversions % 2);
        }

        // for all other cases, the puzzle is not solvable
        return false;
    }

    /*
    prints the solution path and deletes the nodes
    
    */
    void printSteps(Node* node) {
        if (node->getPrevious() != nullptr) {
            printSteps(node->getPrevious());
        }
        node->printGrid();
        std::cout << std::endl;
    }

    void deleteNodes(Node* node) {
        if (node->getPrevious() != nullptr) {
            deleteNodes(node->getPrevious());
        }
        delete node;
    }

   public:
    NPuzzle(int k, Node* goalBoard) {
        this->k = k;
        this->goalBoard = goalBoard;
    }

    void solveWithManhattanDistance(Node* initialBoard) {
        if (!isSolvable(initialBoard)) {
            std::cout << "The puzzle instance is not solvable\n";
            return;
        } else {
            std::cout << "The puzzle instance is solvable\n";
        }

        int totalExpandedNodes = 0;  // No. of nodes entered into the open list
        int totalExploredNodes = 0;  // No. of nodes entered into the closed list
        std::priority_queue<Node*, std::vector<Node*>, manhattanComparator>
            openList;
        // std::set<Node*, nodeComparator> closedList;
        std::unordered_set<Node*, nodeHasher, nodeComparator> closedList;
        openList.push(initialBoard);
        totalExpandedNodes++;

        while (!openList.empty()) {
            Node* currentBoard = openList.top();
            openList.pop();
            totalExploredNodes++;

            if (currentBoard->isEqual(goalBoard)) {
                std::cout << "Steps:" << std::endl;
                printSteps(currentBoard);
                std::cout << "Total steps: " << currentBoard->getMoves()
                          << std::endl
                          << "Total expanded nodes: " << totalExpandedNodes
                          << std::endl
                          << "Total explored nodes: " << totalExploredNodes
                          << std::endl;
                return;
            }

            std::vector<Node*> children = currentBoard->getChildren();
            // std::cout << "Exploring" << std::endl;
            // currentBoard->printGrid();
            for (int i = 0; i < children.size(); i++) {
                if (closedList.find(children[i]) == closedList.end()) {
                    // std::cout << "pushed" << std::endl;
                    // children[i]->printGrid();
                    openList.push(children[i]);
                    totalExpandedNodes++;
                } else {
                    delete children[i];
                }

                // if (children[i]->isEqual(currentBoard->getPrevious())) {
                //     delete children[i];
                // } else {
                //     openList.push(children[i]);
                //     totalExpandedNodes++;
                // }
            }

            closedList.insert(currentBoard);
        }
    }

    void solveWithHammingDistance(Node* initialBoard) {
        if (!isSolvable(initialBoard)) {
            std::cout << "The puzzle instance is not solvable\n";
            return;
        } else {
            std::cout << "The puzzle instance is solvable\n";
        }

        int totalExpandedNodes = 0;  // No. of nodes entered into the open list
        int totalExploredNodes = 0;  // No. of nodes entered into the closed list
        std::priority_queue<Node*, std::vector<Node*>, hammingComparator> openList;
        // std::set<Node*, nodeComparator> closedList;
        std::unordered_set<Node*, nodeHasher, nodeComparator> closedList;
        openList.push(initialBoard);
        totalExpandedNodes++;

        while (!openList.empty()) {
            Node* currentBoard = openList.top();
            openList.pop();
            totalExploredNodes++;

            if (currentBoard->isEqual(goalBoard)) {
                std::cout << "Steps:" << std::endl;
                printSteps(currentBoard);
                std::cout << "Total steps: " << currentBoard->getMoves()
                          << std::endl
                          << "Total expanded nodes: " << totalExpandedNodes
                          << std::endl
                          << "Total explored nodes: " << totalExploredNodes
                          << std::endl;
                return;
            }

            std::vector<Node*> children = currentBoard->getChildren();
            // std::cout << "Exploring" << std::endl;
            // currentBoard->printGrid();
            for (int i = 0; i < children.size(); i++) {
                if (closedList.find(children[i]) == closedList.end()) {
                    // std::cout << "pushed" << std::endl;
                    // children[i]->printGrid();
                    openList.push(children[i]);
                    totalExpandedNodes++;
                } else {
                    delete children[i];
                }
                // if (children[i]->isEqual(currentBoard->getPrevious())) {
                //     delete children[i];
                // } else {
                //     openList.push(children[i]);
                //     totalExpandedNodes++;
                // }
            }

            closedList.insert(currentBoard);
        }
    }
};

#endif