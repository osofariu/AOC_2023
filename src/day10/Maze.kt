package day10

class Maze(input: List<String>) {
    private val maze = input.map { it.toCharArray() }
    private var visited: MutableMap<Pos, Int> = mutableMapOf()

    fun findLongestPath(): Int {
        val start = locateStartingPos()
        markLocationAsVisited(start, 0)
        val res = findLongestPath(start, 1)
        printMaze()
        return res / 2
    }

    private fun markLocationAsVisited(pos: Pos, pathLength: Int) {
        visited[pos] = pathLength
    }

    private fun findLongestPath(start: Pos, pathLength: Int): Int {
        return Direction.entries.maxOf { direction -> longestPath(start, direction, pathLength) }
    }

    private fun longestPath(from: Pos, direction: Direction, pathLength: Int): Int {
        return if (canMove(from, direction)) {
            val newPosition = moveAndRememberNextPosition(from, direction, pathLength)
            findLongestPath(newPosition, pathLength + 1)
        } else {
            pathLength
        }
    }

    private fun canMove(from: Pos, direction: Direction): Boolean {
        val newPosition = getNewPosition(from, direction)
        return positionOnMap(newPosition) && notVisited(newPosition) && canMoveInDirection(from, direction)
    }

    private fun getNewPosition(from: Pos, direction: Direction): Pos {
        val newPosition = when (direction) {
            Direction.NORTH -> Pos(from.x, from.y - 1)
            Direction.SOUTH -> Pos(from.x, from.y + 1)
            Direction.EAST -> Pos(from.x + 1, from.y)
            Direction.WEST -> Pos(from.x - 1, from.y)
        }
        return newPosition
    }

    private fun positionOnMap(candidatePosition: Pos): Boolean =
         candidatePosition.x in 0..maze[0].lastIndex && candidatePosition.y in 0..maze.lastIndex

    private fun notVisited(newPosition: Pos): Boolean =
        !this.visited.containsKey(newPosition)

    private fun canMoveInDirection(from: Pos, direction: Direction): Boolean =
        if (elementAt(from) == "S") {
            canMoveFromStartTo(from, direction)
        } else {
            canMoveTo(from, direction)
        }

    private fun canMoveTo(from: Pos, direction: Direction): Boolean =
        when (direction) {
            Direction.NORTH -> listOf("|", "L", "J").contains(elementAt(Pos(from.x, from.y)))
            Direction.SOUTH -> listOf("|", "F", "7").contains(elementAt(Pos(from.x, from.y)))
            Direction.EAST -> listOf("-", "L", "F").contains(elementAt(Pos(from.x, from.y)))
            Direction.WEST -> listOf("-", "7", "J").contains(elementAt(Pos(from.x, from.y)))
        }

    private fun canMoveFromStartTo(from: Pos,direction: Direction) : Boolean =
        when (direction) {
            Direction.NORTH -> listOf("|", "7", "F").contains(elementAt(getNewPosition(from, Direction.NORTH)))
            Direction.SOUTH -> listOf("|", "J", "L").contains(elementAt(getNewPosition(from, Direction.SOUTH)))
            Direction.EAST -> listOf("|", "7", "J").contains(elementAt(getNewPosition(from, Direction.EAST)))
            Direction.WEST -> listOf("|", "L", "F").contains(elementAt(getNewPosition(from, Direction.WEST)))
        }

    private fun moveAndRememberNextPosition(from: Pos, direction: Direction, pathLength: Int): Pos {
        val newPos = getNewPosition(from, direction)
        markLocationAsVisited(newPos, pathLength)
        return newPos
    }

    private fun locateStartingPos(): Pos {
        val y = maze.indices.first { maze[it].contains('S') }
        val x = maze[y].indexOf('S')
        return Pos(x, y)
    }

    private fun elementAt(pos: Pos): String =
        maze[pos.y][pos.x].toString()

    private fun printMaze() {
        maze.forEach { println(it.joinToString("     ")) }
        println("--------------------------------------------------")
        maze.indices.forEach { y ->
            maze[y].indices.forEach { x ->
                print("%5d ".format(visited[Pos(x, y)] ?: -1))
            }
            print("\n")
        }
    }

    fun countTilesInLoop(): Int {
        TODO("Not yet implemented")
    }
}

