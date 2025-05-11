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
GAME_END_DELAY = 1500 # Milliseconds to show final board before fading (1.5 seconds)

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

# Fade the current screen content to black
def fade_to_black():
    # This function assumes 'screen' currently holds the image to be faded.
    fade_overlay = pygame.Surface((WIDTH, HEIGHT))  # Create an overlay surface
    fade_overlay.fill(BLACK)                        # Fill it with the target fade color (black)
    
    # Iterate alpha from 0 (transparent) to 255 (fully opaque)
    # Adjust the step (e.g., 10 or 15) and delay (e.g., 20-30ms) to control fade speed
    for alpha in range(0, 255 + 1, 15):
        fade_overlay.set_alpha(alpha)             # Set current alpha for the overlay
        # We need to re-blit the current screen content if it wasn't persistent,
        # but since we just drew the final board on 'screen' and flipped,
        # 'screen' holds the image. We blit the overlay on top.
        screen.blit(fade_overlay, (0, 0))         # Blit the semi-transparent overlay onto the screen
        pygame.display.flip()                       # Update the display to show this fade step
        pygame.time.delay(30)                       # Pause for smoothness
    
    # After the loop, the screen is covered by a fully opaque black surface.
    # Ensure the screen is purely black for the text that follows.
    screen.fill(BLACK)
    pygame.display.flip() # Show the pure black screen briefly (optional, but good practice)


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

    play_move_button = pygame.Rect(WIDTH // 2 - 70, HEIGHT - 80, 140, 50)

    while True:
        screen.fill(WHITE)
        draw_grid()
        draw_markers(board)

        if not game_over:
            if player_turn:
                turn_text = small_font.render("X's Turn", True, BLACK)
            else:
                turn_text = small_font.render("O's Turn", True, BLACK)
            screen.blit(turn_text, (WIDTH // 2 - turn_text.get_width() // 2, HEIGHT - 100))

            if mode == "single" and not player_turn:
                pygame.draw.rect(screen, GREEN, play_move_button)
                play_move_text = small_font.render("Play Move", True, BLACK)
                screen.blit(play_move_text, (play_move_button.x + 10, play_move_button.y + 10))

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.MOUSEBUTTONDOWN and not game_over:
                mouse_x, mouse_y = event.pos

                if mode == "single" and not player_turn and play_move_button.collidepoint(event.pos):
                    computer_move(board)
                    if check_winner(board):
                        winner = "O"
                        game_over = True
                    elif check_draw(board): # Check for draw after computer move
                        winner = "Draw"
                        game_over = True
                    player_turn = not player_turn
                else:
                    clicked_row = mouse_y // CELL_SIZE
                    clicked_col = mouse_x // CELL_SIZE

                    if 0 <= clicked_row < BOARD_SIZE and 0 <= clicked_col < BOARD_SIZE:
                        if board[clicked_row][clicked_col] == "":
                            current_player_mark = "X" if player_turn else "O"
                            
                            if player_turn: # X's turn (human in both modes)
                                board[clicked_row][clicked_col] = "X"
                                if check_winner(board):
                                    winner = "X"
                                    game_over = True
                                elif check_draw(board): # Check for draw after X's move
                                    winner = "Draw"
                                    game_over = True
                                player_turn = not player_turn
                            
                            elif mode == "multiplayer" and not player_turn: # O's turn (human in multiplayer)
                                board[clicked_row][clicked_col] = "O"
                                if check_winner(board):
                                    winner = "O"
                                    game_over = True
                                elif check_draw(board): # Check for draw after O's move
                                    winner = "Draw"
                                    game_over = True
                                player_turn = not player_turn
        
        # This check is a bit redundant if we check draw after each move, but good as a fallback.
        if not game_over and check_draw(board) and not winner:
            winner = "Draw"
            game_over = True

        if game_over:
            # --- Step 1: Display the final board state clearly ---
            screen.fill(WHITE)    # Clear screen for the final board view
            draw_grid()           # Redraw grid
            draw_markers(board)   # Redraw X/O positions with the final move
            pygame.display.flip() # Update the display to show this final board state
            
            # --- Step 2: Pause to allow player to see the final move ---
            pygame.time.delay(GAME_END_DELAY) # Pause for the defined duration

            # --- Step 3: Fade the current view to black ---
            fade_to_black()  # Call the revised fade function

            # --- Step 4: Display game over message on the now black screen ---
            # screen.fill(BLACK) # This is now handled by the end of fade_to_black()
            
            if winner and winner != "Draw":
                text = font.render(f"{winner} wins!", True, WHITE)
            else: # Covers "Draw"
                text = font.render("It's a Draw!", True, WHITE)
            screen.blit(text, (WIDTH // 4, HEIGHT // 3))

            return_text = small_font.render("Press 'R' to Return to Menu", True, WHITE)
            screen.blit(return_text, (WIDTH // 4, HEIGHT // 2 + 50))
            pygame.display.flip() # Show the text

            waiting = True
            while waiting:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        pygame.quit()
                        sys.exit()
                    if event.type == pygame.KEYDOWN:
                        if event.key == pygame.K_r:
                            waiting = False
            return  # Exit the main loop to return to the menu

        pygame.display.flip()
        pygame.time.Clock().tick(FPS)

# Start the game
if __name__ == "__main__":
    while True:
        mode = pre_game_menu()
        main(mode)
