package day10

import readInput

typealias VisitedPath = MutableMap<Pos, Int>
typealias Maze = List<List<String>>

class Day10(val input: List<String>) {

    private val maze: Maze = input.map { it.map { c -> c.toString() } }

    fun part1(): Int {
        return findLongestPath()
    }
    fun part2(): Int {
        return countTilesInLoop()
    }

    private fun findLongestPath(): Int {
        val visited = buildLongestPath()
        return visited.values.max() / 2 + 1
    }

    private fun buildLongestPath(): VisitedPath {
        val visited: VisitedPath = mutableMapOf()
        val start = locateStartingPos()
        markLocationAsVisited(visited, start, 0)
        return buildLongestPath(visited, start, 1)
    }

    private fun markLocationAsVisited(visited: VisitedPath, pos: Pos, pathLength: Int) {
        visited[pos] = pathLength
    }

    private fun buildLongestPath(visited: VisitedPath, start: Pos, pathLength: Int): VisitedPath {
        Direction.entries.forEach { direction ->
            buildLongestPath(visited, start, direction, pathLength)
        }
        return visited
    }

    private fun buildLongestPath(visited: VisitedPath, from: Pos, direction: Direction, pathLength: Int): VisitedPath {
        return if (canMove(visited, from, direction)) {
            val newPosition = moveAndRememberNextPosition(visited, from, direction, pathLength)
            buildLongestPath(visited, newPosition, pathLength + 1)
        } else {
            visited
        }
    }

    private fun canMove(visited: VisitedPath, from: Pos, direction: Direction): Boolean {
        val newPosition = getNewPosition(from, direction)
        return positionOnMap(newPosition) && notVisited(visited, newPosition) && canMoveInDirection(from, direction)
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

    private fun notVisited(path: VisitedPath, newPosition: Pos): Boolean =
        !path.containsKey(newPosition)

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

    private fun canMoveFromStartTo(from: Pos, direction: Direction): Boolean =
        when (direction) {
            Direction.NORTH -> listOf("|", "7", "F").contains(elementAt(getNewPosition(from, Direction.NORTH)))
            Direction.SOUTH -> listOf("|", "J", "L").contains(elementAt(getNewPosition(from, Direction.SOUTH)))
            Direction.EAST -> listOf("|", "7", "J").contains(elementAt(getNewPosition(from, Direction.EAST)))
            Direction.WEST -> listOf("|", "L", "F").contains(elementAt(getNewPosition(from, Direction.WEST)))
        }

    private fun moveAndRememberNextPosition(
        path: VisitedPath,
        from: Pos,
        direction: Direction,
        pathLength: Int
    ): Pos {
        val newPos = getNewPosition(from, direction)
        markLocationAsVisited(path, newPos, pathLength)
        return newPos
    }

    private fun locateStartingPos(): Pos {
        val y = maze.indices.first { maze[it].contains("S") }
        val x = maze[y].indexOf("S")
        return Pos(x, y)
    }

    private fun elementAt(pos: Pos): String =
        maze[pos.y][pos.x]

    private fun printMaze(path: VisitedPath) {
        maze.forEach { println(it.joinToString("     ")) }
        println("--------------------------------------------------")
        maze.indices.forEach { y ->
            maze[y].indices.forEach { x ->
                print("%5d ".format(path[Pos(x, y)] ?: -1))
            }
            print("\n")
        }
    }

    private fun countTilesInLoop(): Int {
        val visited = buildLongestPath()
        val tiles = listTiles(maze)
        return 0
    }

    private fun listTiles(maze: Maze): List<Pos> {
        val tilesList = mutableListOf<Pos>()
        (0..maze.lastIndex).forEach { y ->
            (0..maze[0].lastIndex).forEach { x ->
                tilesList.add(Pos(x, y))
            }
        }
        return tilesList.toList()
    }

    private fun clearlyOutsideTheBox(maze: List<CharArray>, x: Int, y: Int): Boolean {
        val maxX = maze[0].lastIndex
        val maxY: Int = maze.lastIndex
        if (elementAt(Pos(x, y)) == "O") {
            return true
        }
        return (0..<x).none { networkExistsAt(maze, Pos(it, y)) } ||
                (x + 1..maxX).none { networkExistsAt(maze, Pos(it, y)) } ||
                (0..<y).none { networkExistsAt(maze, Pos(x, it)) } ||
                (y + 1..maxY).none { networkExistsAt(maze, Pos(x, it)) }
    }

    private fun networkExistsAt(maze: List<CharArray>, pos: Pos): Boolean =
        pos.x >= 0 && pos.x < maze[0].size && pos.y >= 0 && pos.y < maze.size &&
                "|-LJ7FS".contains(maze[pos.y][pos.x])
}




fun main() {
    val input = readInput("day10")
    println("part 1 input for " + input.size + "  " + Day10(input).part1())
    println("part 2 input for " + input.size + "  " + Day10(input).part2())
}
