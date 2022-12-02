#ifndef NPUZZLE_H
#define NPUZZLE_H

#include <iostream>
#include <vector>
#include <queue>

#include "node.h"

class NPuzzle {
private:
    int k; // length of square grid
    Node* goalBoard;
    struct manhattanComparator {
        bool operator()(Node* a, Node* b) {
            return a->calculateManhattanCost() > b->calculateManhattanCost(); 
        }
    };

    struct hammingCompataor {
        bool operator()(Node* a, Node* b) {
            return a->calculateHammingCost() > b->calculateHammingCost();
        }
    };

    void printSteps(Node* node) {
        if (node->getPrevious() != nullptr) {
            printSteps(node->getPrevious());
        }
        node->printGrid();
        std::cout << std::endl;
    }

public:
    NPuzzle(int k, Node* goalBoard) {
        this->k = k;
        this->goalBoard = goalBoard;
    }

    int solveWithManhattanDistance(Node* initialBoard) {
        std::priority_queue<Node*, std::vector<Node*>, manhattanComparator> openList;
        openList.push(initialBoard);

        while (!openList.empty()) {
            Node* currentBoard = openList.top();
            openList.pop();

            if(currentBoard->isEqual(goalBoard)) {
                std::cout << "Steps:" << std::endl;
                printSteps(currentBoard);
                std::cout << "Total cost: " << currentBoard->getMoves() << std::endl;
                return currentBoard->getMoves();
            }
            
            std::vector<Node*> children = currentBoard->getChildren();
            for(int i = 0; i < children.size(); i++) {
                openList.push(children[i]);
            }
        }
        return -1;
    }

    int solveWithHammingDistance(Node* initialBoard) {
        std::priority_queue<Node*, std::vector<Node*>, hammingCompataor> openList;
        openList.push(initialBoard);

        while (!openList.empty()) {
            Node* currentBoard = openList.top();
            openList.pop();

            if (currentBoard->isEqual(goalBoard)) {
                std::cout << "Steps:" << std::endl;
                printSteps(currentBoard);
                std::cout << "Total steps: " << currentBoard->getMoves() << std::endl;
                return currentBoard->getMoves();
            }

            std::vector<Node*> children = currentBoard->getChildren();
            for(int i = 0; i < children.size(); i++) {
                openList.push(children[i]);
            }
        }
        return -1;
    }
};

#endif