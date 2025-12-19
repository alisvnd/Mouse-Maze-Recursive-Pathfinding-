# Mouse-Maze-Recursive-Pathfinding-
This project is a Java Swing GUI application that simulates a mouse navigating through a maze to reach a piece of cheese. The maze is represented as a 5×5 grid, where the user can interactively design the maze and control how the path is found.

The user can set the starting position (mouse) and ending position (cheese), as well as add or remove walls using mouse interactions. Walls are impassable, and the mouse can move only horizontally or vertically.

The application uses two synchronized frames:

a maze frame that visually displays the grid and renders the mouse, cheese, walls, and path using custom painting (paintComponent) and images

a control frame with buttons arranged using GridLayout to switch modes such as setting start/end positions, adding or removing walls, finding a path, and resetting the maze

Mouse events are handled via MouseListener, allowing users to modify the maze by clicking directly on grid cells.

When the “Find Path” button is pressed, the program uses a recursive pathfinding algorithm to compute one of the shortest possible paths from the mouse to the cheese. The discovered path is then highlighted on the maze. If no valid path exists, the application displays an informative error message using a dialog.

The project demonstrates the use of recursion, grid-based pathfinding, event-driven GUI programming, custom graphics rendering, and image handling with BufferedImage in Java.
