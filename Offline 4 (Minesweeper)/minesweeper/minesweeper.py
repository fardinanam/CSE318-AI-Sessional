import itertools
import random


class Minesweeper():
    """
    Minesweeper game representation
    """

    def __init__(self, height=8, width=8, mines=8):

        # Set initial width, height, and number of mines
        self.height = height
        self.width = width
        self.mines = set()

        # Initialize an empty field with no mines
        self.board = []
        for i in range(self.height):
            row = []
            for j in range(self.width):
                row.append(False)
            self.board.append(row)

        # Add mines randomly
        while len(self.mines) != mines:
            i = random.randrange(height)
            j = random.randrange(width)
            if not self.board[i][j]:
                self.mines.add((i, j))
                self.board[i][j] = True

        # At first, player has found no mines
        self.mines_found = set()

    def print(self):
        """
        Prints a text-based representation
        of where mines are located.
        """
        for i in range(self.height):
            print("--" * self.width + "-")
            for j in range(self.width):
                if self.board[i][j]:
                    print("|X", end="")
                else:
                    print("| ", end="")
            print("|")
        print("--" * self.width + "-")

    def is_mine(self, cell):
        i, j = cell
        return self.board[i][j]

    def nearby_mines(self, cell):
        """
        Returns the number of mines that are
        within one row and column of a given cell,
        not including the cell itself.
        """

        # Keep count of nearby mines
        count = 0

        # Loop over all cells within one row and column
        for i in range(cell[0] - 1, cell[0] + 2):
            for j in range(cell[1] - 1, cell[1] + 2):

                # Ignore the cell itself and the cells in the corners
                if (i, j) == cell\
                or (i, j) == (cell[0] - 1, cell[1] - 1) or (i, j) == (cell[0] - 1, cell[1] + 1)\
                or (i, j) == (cell[0] + 1, cell[1] - 1) or (i, j) == (cell[0] + 1, cell[1] + 1):
                    continue

                # Update count if cell in bounds and is mine
                if 0 <= i < self.height and 0 <= j < self.width:
                    if self.board[i][j]:
                        count += 1

        return count

    def won(self):
        """
        Checks if all mines have been flagged.
        """
        return self.mines_found == self.mines


class Sentence():
    """
    Logical statement about a Minesweeper game
    A sentence consists of a set of board cells,
    and a count of the number of those cells which are mines.
    """

    def __init__(self, cells, count):
        self.cells = set(cells)
        self.count = count

    def __eq__(self, other):
        return self.cells == other.cells and self.count == other.count

    def __str__(self):
        return f"{self.cells} = {self.count}"

    def known_mines(self):
        """
        Returns the set of all cells in self.cells known to be mines.
        """
        if len(self.cells) == self.count:
            return self.cells
        else:
            return None

    def known_safes(self):
        """
        Returns the set of all cells in self.cells known to be safe.
        """
        if self.count == 0:
            return self.cells
        else:
            return None

    def mark_mine(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be a mine.
        """
        if cell in self.cells:
            self.cells.remove(cell)
            self.count -= 1


    def mark_safe(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be safe.
        """
        if cell in self.cells:
            self.cells.remove(cell)


class MinesweeperAI():
    """
    Minesweeper game player
    """

    def __init__(self, height=8, width=8):

        # Set initial height and width
        self.height = height
        self.width = width

        # Keep track of which cells have been clicked on
        self.moves_made = set()

        # Keep track of cells known to be safe or mines
        self.mines = set()
        self.safes = set()

        # List of sentences about the game known to be true
        self.knowledge = []

    def mark_mine(self, cell):
        """
        Marks a cell as a mine, and updates all knowledge
        to mark that cell as a mine as well.
        """
        self.mines.add(cell)
        for sentence in self.knowledge:
            sentence.mark_mine(cell)

    def mark_safe(self, cell):
        """
        Marks a cell as safe, and updates all knowledge
        to mark that cell as safe as well.
        """
        self.safes.add(cell)
        for sentence in self.knowledge:
            sentence.mark_safe(cell)
    
    def is_known(self, cell):
        """
        returns True if a cell is either safe or a mine
        """
        print(f"Known mines: {self.mines}")
        print(f"Known safes: {self.safes}")

        return cell in self.safes or cell in self.mines
    
    def make_sentence(self, cell, count):
        """
        returns a sentence of all unknown neighbors of a cell
        """
        neighbors = set()
        
        for i in range(cell[0] - 1, cell[0] + 2):
            for j in range(cell[1] - 1, cell[1] + 2):
                # Ignore the cell itself and the cells in the corners
                if (i, j) == cell\
                or (i, j) == (cell[0] - 1, cell[1] - 1) or (i, j) == (cell[0] - 1, cell[1] + 1)\
                or (i, j) == (cell[0] + 1, cell[1] - 1) or (i, j) == (cell[0] + 1, cell[1] + 1):
                    continue
                
                if 0 <= i < self.height and 0 <= j < self.width:
                    # if cell is a mine, decrement count
                    if (i, j) in self.mines:
                        count -= 1
                    # if cell is safe, add do nothing
                    # for any other case, add cell to neighbors
                    elif (i, j) not in self.safes:
                        neighbors.add((i, j))

        return None if len(neighbors) == 0 else Sentence(neighbors, count)
    
    def replace_with_subsets(self, sentence):
        # knowledge_copy = self.knowledge.copy()

        # for other_sentence in knowledge_copy:
        #     if sentence != other_sentence:
        #         # remove the other sentence from the knowledge
        #         self.knowledge.remove(other_sentence)

        #         larger_sentence = None
        #         smaller_sentence = None

        #         if len(sentence.cells) > len(other_sentence.cells):
        #             larger_sentence = sentence
        #             smaller_sentence = other_sentence
        #         else: 
        #             larger_sentence = other_sentence
        #             smaller_sentence = sentence

        #         if smaller_sentence.cells.issubset(larger_sentence.cells):
        #             # make a new sentence with the difference of the two sets
        #             # add the difference sentence
        #             new_cells = larger_sentence.cells - smaller_sentence.cells
        #             new_count = larger_sentence.count - smaller_sentence.count
        #             new_sentence = Sentence(new_cells, new_count)
                    
        #             if new_sentence not in self.knowledge:
        #                 self.knowledge.append(new_sentence)
        
        #             #  add the smaller sentence to the knowledge
        #             if smaller_sentence not in self.knowledge:
        #                 self.knowledge.append(smaller_sentence)
                    
        #         else:
        #             # add the other sentence back to the knowledge
        #             self.knowledge.append(other_sentence)

        # # if the passed sentence is not in the knowledge, add it
        # if sentence not in self.knowledge:
        #     self.knowledge.append(sentence)
        knowledge_copy = self.knowledge.copy()

        for other_sentence in knowledge_copy:
            new_sentence = None

            if sentence.cells.issubset(other_sentence.cells):
                new_sentence = Sentence(other_sentence.cells - sentence.cells, other_sentence.count - sentence.count)
            
            elif other_sentence.cells.issubset(sentence.cells):
                new_sentence = Sentence(sentence.cells - other_sentence.cells, sentence.count - other_sentence.count)

            if new_sentence and new_sentence not in self.knowledge:
                self.knowledge.append(new_sentence)
    
    def infer_and_mark(self):
        """
        infers new sentences and marks cells as safe or mines.
        returns True if a new inference was made, False otherwise
        """
        has_infered = False
        knowledge_copy = self.knowledge.copy()

        for sentence in knowledge_copy:
            known_mines = sentence.known_mines().copy() if sentence.known_mines() else None
            known_safes = sentence.known_safes().copy() if sentence.known_safes() else None

            if known_mines:
                self.knowledge.remove(sentence)
                for cell in known_mines:
                    self.mark_mine(cell)
                has_infered = True

            if known_safes:
                self.knowledge.remove(sentence)
                for cell in known_safes:
                    self.mark_safe(cell)
                has_infered = True

        return has_infered
            

    def add_knowledge(self, cell, count):
        """
        Called when the Minesweeper board tells us, for a given
        safe cell, how many neighboring cells have mines in them.

        This function should:
            1) mark the cell as a move that has been made
            2) mark the cell as safe
            3) add a new sentence to the AI's knowledge base
               based on the value of `cell` and `count`
            4) mark any additional cells as safe or as mines
               if it can be concluded based on the AI's knowledge base
            5) add any new sentences to the AI's knowledge base
               if they can be inferred from existing knowledge
        """
        self.moves_made.add(cell)
        self.mark_safe(cell)

        new_sentence = self.make_sentence(cell, count)

        if new_sentence and new_sentence not in self.knowledge:
            self.replace_with_subsets(new_sentence)
            self.knowledge.append(new_sentence)
            print(f"Added new knowledge: {new_sentence}\n")
            
        while self.infer_and_mark():
            pass

    def make_safe_move(self):
        """
        Returns a safe cell to choose on the Minesweeper board.
        The move must be known to be safe, and not already a move
        that has been made.

        This function may use the knowledge in self.mines, self.safes
        and self.moves_made, but should not modify any of those values.
        """
        unrevealed_safes = self.safes - self.moves_made
        return None if not unrevealed_safes else random.choice(list(unrevealed_safes))

    def make_random_move(self):
        """
        Returns a move to make on the Minesweeper board.
        Should choose randomly among cells that:
            1) have not already been chosen, and
            2) are not known to be mines
        """
        unrevealed_cells = set()
        for i in range(self.height):
            for j in range(self.width):
                if (i, j) not in self.moves_made and (i, j) not in self.mines:
                    unrevealed_cells.add((i, j))
        return None if not unrevealed_cells else random.choice(list(unrevealed_cells))
