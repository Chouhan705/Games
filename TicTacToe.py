import pygame
import sys
import random

# Constants
WIDTH, HEIGHT = 600, 600  # Square window
LINE_WIDTH = 15
BOARD_SIZE = 3
CELL_SIZE = WIDTH // BOARD_SIZE
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)
FPS = 60

# Initialize Pygame
pygame.init()

# Create the screen
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Tic Tac Toe")

# Load fonts
font = pygame.font.Font(None, 74)
small_font = pygame.font.Font(None, 36)

# Draw the grid
def draw_grid():
    for i in range(1, BOARD_SIZE):
        pygame.draw.line(screen, BLACK, (0, CELL_SIZE * i), (WIDTH, CELL_SIZE * i), LINE_WIDTH)
        pygame.draw.line(screen, BLACK, (CELL_SIZE * i, 0), (CELL_SIZE * i, HEIGHT), LINE_WIDTH)

# Draw X and O
def draw_markers(board):
    for row in range(BOARD_SIZE):
        for col in range(BOARD_SIZE):
            if board[row][col] == "X":
                pygame.draw.line(screen, BLUE, (col * CELL_SIZE + 20, row * CELL_SIZE + 20),
                                 (col * CELL_SIZE + CELL_SIZE - 20, row * CELL_SIZE + CELL_SIZE - 20), LINE_WIDTH)
                pygame.draw.line(screen, BLUE, (col * CELL_SIZE + CELL_SIZE - 20, row * CELL_SIZE + 20),
                                 (col * CELL_SIZE + 20, row * CELL_SIZE + CELL_SIZE - 20), LINE_WIDTH)
            elif board[row][col] == "O":
                pygame.draw.circle(screen, RED, (col * CELL_SIZE + CELL_SIZE // 2, row * CELL_SIZE + CELL_SIZE // 2),
                                   CELL_SIZE // 3, LINE_WIDTH)

# Check for a winner
def check_winner(board):
    for row in range(BOARD_SIZE):
        if board[row][0] == board[row][1] == board[row][2] != "":
            return board[row][0]
    for col in range(BOARD_SIZE):
        if board[0][col] == board[1][col] == board[2][col] != "":
            return board[0][col]
    if board[0][0] == board[1][1] == board[2][2] != "":
        return board[0][0]
    if board[0][2] == board[1][1] == board[2][0] != "":
        return board[0][2]
    return None

# Check for a draw
def check_draw(board):
    for row in board:
        if "" in row:
            return False
    return True

# Fade to black animation
def fade_to_black():
    for alpha in range(0, 256, 5):
        screen.fill(BLACK)
        surface = pygame.Surface((WIDTH, HEIGHT))
        surface.set_alpha(alpha)  # Set the alpha level
        screen.blit(surface, (0, 0))  # Draw the fading surface
        pygame.display.flip()
        pygame.time.delay(20)

# Pregame Menu
def pre_game_menu():
    while True:
        screen.fill(WHITE)
        title_text = font.render("Tic Tac Toe", True, BLACK)
        single_player_text = small_font.render("Press '1' for Single Player", True, BLACK)
        multiplayer_text = small_font.render("Press '2' for Multiplayer", True, BLACK)

        screen.blit(title_text, (WIDTH // 4, HEIGHT // 4))
        screen.blit(single_player_text, (WIDTH // 4, HEIGHT // 2))
        screen.blit(multiplayer_text, (WIDTH // 4, HEIGHT // 2 + 50))

        pygame.display.flip()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_1:
                    return "single"
                elif event.key == pygame.K_2:
                    return "multiplayer"

# Play computer move in single-player mode
def computer_move(board):
    empty_cells = [(row, col) for row in range(BOARD_SIZE) for col in range(BOARD_SIZE) if board[row][col] == ""]
    if empty_cells:
        row, col = random.choice(empty_cells)
        board[row][col] = "O"

# Game loop
def main(mode):
    board = [["" for _ in range(BOARD_SIZE)] for _ in range(BOARD_SIZE)]
    player_turn = True  # True for X's turn, False for O's turn
    game_over = False
    winner = None

    # Button for computer move
    play_move_button = pygame.Rect(WIDTH // 2 - 70, HEIGHT - 80, 140, 50)

    while True:
        screen.fill(WHITE)
        draw_grid()
        draw_markers(board)

        # Highlight the current player's turn
        if not game_over:
            if player_turn:
                turn_text = small_font.render("X's Turn", True, BLACK)
            else:
                turn_text = small_font.render("O's Turn", True, BLACK)
            screen.blit(turn_text, (WIDTH // 2 - turn_text.get_width() // 2, HEIGHT - 100))

        # Draw "Play Move" button only in single-player mode and if it's O's turn
        if mode == "single" and not player_turn and not game_over: # Added not player_turn and not game_over
            pygame.draw.rect(screen, GREEN, play_move_button)
            play_move_text = small_font.render("Play Move", True, BLACK)
            screen.blit(play_move_text, (play_move_button.x + 10, play_move_button.y + 10))

        # Event handling
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.MOUSEBUTTONDOWN and not game_over:
                mouse_x, mouse_y = event.pos

                # --- Handle "Play Move" button click in SINGLE PLAYER ---
                if mode == "single" and not player_turn and play_move_button.collidepoint(event.pos):
                    computer_move(board)
                    if check_winner(board):
                        winner = "O"
                        game_over = True
                    player_turn = not player_turn  # Switch turns
                
                # --- Handle clicks on the BOARD itself ---
                else: # Not a button click, or mode is multiplayer (where button isn't active for placing marks)
                    clicked_row = mouse_y // CELL_SIZE
                    clicked_col = mouse_x // CELL_SIZE

                    # Ensure the click is within the board grid
                    if 0 <= clicked_row < BOARD_SIZE and 0 <= clicked_col < BOARD_SIZE:
                        if board[clicked_row][clicked_col] == "":  # If the cell is empty
                            if player_turn:  # X's turn (applies to both single and multiplayer)
                                board[clicked_row][clicked_col] = "X"
                                if check_winner(board):
                                    winner = "X"
                                    game_over = True
                                player_turn = not player_turn  # Switch turns
                            
                            elif mode == "multiplayer" and not player_turn:  # O's turn in MULTIPLAYER
                                board[clicked_row][clicked_col] = "O"
                                if check_winner(board):
                                    winner = "O"
                                    game_over = True
                                player_turn = not player_turn  # Switch turns
        
        if check_draw(board) and not winner: # Check for draw only if no winner yet
            winner = "Draw" # Store "Draw" as winner type
            game_over = True

        if game_over:
            fade_to_black()
            screen.fill(BLACK)
            if winner and winner != "Draw":
                text = font.render(f"{winner} wins!", True, WHITE)
            else: # Covers None (though unlikely here) and "Draw"
                text = font.render("It's a Draw!", True, WHITE)
            screen.blit(text, (WIDTH // 4, HEIGHT // 3))

            return_text = small_font.render("Press 'R' to Return to Menu", True, WHITE)
            screen.blit(return_text, (WIDTH // 4, HEIGHT // 2 + 50))
            
            pygame.display.flip()

            waiting = True
            while waiting:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        pygame.quit()
                        sys.exit()
                    if event.type == pygame.KEYDOWN:
                        if event.key == pygame.K_r:
                            waiting = False
            return

        pygame.display.flip()
        pygame.time.Clock().tick(FPS)

# Start the game
if __name__ == "__main__":
    while True:
        mode = pre_game_menu()
        main(mode)
