package day10

import readInput

typealias VisitedPath = MutableMap<Pos, Int>
typealias Maze = MutableList<MutableList<String>>

class Day10(val input: List<String>) {
    private val maze: Maze = input.map { it.map { c -> c.toString() }.toMutableList() }.toMutableList()

    fun part1(): Int {
        val visited = buildLongestPath()
        return visited.values.max() / 2 + 1
    }

    private fun buildLongestPath(): VisitedPath {
        val visited: VisitedPath = mutableMapOf()
        val start = locateStartingPos()
        markLocationAsVisited(visited, start, 0)
        buildLongestPath(visited, start, 1)
        return visited
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

    private fun buildLongestPath(visited: VisitedPath, from: Pos, direction: Direction, pathLength: Int) {
        if (canMoveInDirection(visited, from, direction)) {
            val newPosition = getNewPosition(from, direction)
            markLocationAsVisited(visited, newPosition, pathLength)
            buildLongestPath(visited, newPosition, pathLength + 1)
        }
    }

    private fun canMoveInDirection(visited: VisitedPath, from: Pos, direction: Direction): Boolean {
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

    private fun notVisited(path: VisitedPath, position: Pos): Boolean =
        !path.containsKey(position)

    private fun canMoveInDirection(from: Pos, direction: Direction): Boolean =
        if (elementAt(from) == "S") {
            canMoveFromStartTo(from, direction)
        } else {
            canMoveTo(from, direction)
        }

    private fun canMoveFromStartTo(from: Pos, direction: Direction): Boolean =
        when (direction) {
            Direction.NORTH -> listOf("|", "7", "F").contains(elementAt(getNewPosition(from, Direction.NORTH)))
            Direction.SOUTH -> listOf("|", "J", "L").contains(elementAt(getNewPosition(from, Direction.SOUTH)))
            Direction.EAST -> listOf("-", "7", "J").contains(elementAt(getNewPosition(from, Direction.EAST)))
            Direction.WEST -> listOf("-", "L", "F").contains(elementAt(getNewPosition(from, Direction.WEST)))
        }

    private fun canMoveTo(from: Pos, direction: Direction): Boolean =
        when (direction) {
            Direction.NORTH -> listOf("|", "L", "J").contains(elementAt(Pos(from.x, from.y)))
            Direction.SOUTH -> listOf("|", "F", "7").contains(elementAt(Pos(from.x, from.y)))
            Direction.EAST -> listOf("-", "L", "F").contains(elementAt(Pos(from.x, from.y)))
            Direction.WEST -> listOf("-", "7", "J").contains(elementAt(Pos(from.x, from.y)))
        }

    private fun locateStartingPos(): Pos {
        val y = maze.indices.first { maze[it].contains("S") }
        val x = maze[y].indexOf("S")
        return Pos(x, y)
    }

    private fun elementAt(pos: Pos): String =
        maze[pos.y][pos.x]


    private fun printMaze(path: VisitedPath) {
        listTiles(maze).forEach {
            if (it.x == 0) {
                println()
            }
            print(withColor(unicode(elementAt(it)), path.containsKey(it)))
        }
        println("")
    }

    private fun unicode(s: String): String =
        when (s) {
            "|" -> "\u2503"
            "-" -> "\u2501"
            "7" -> "\u2513"
            "L" -> "\u2517"
            "F" -> "\u250F"
            "J" -> "\u251B"
            "." -> "X"
            else -> s
        }

    private fun withColor(text: String, visited: Boolean): String {
        val green = "\u001b[32m"
        val blue = "\u001b[34m"
        return if (visited)
            "$green$text\u001b[0m"
        else
            "$blue$text\u001b[0m"
    }

    fun part2(): Int {
        val visitedPath = buildLongestPath()

        // path not visited may be tiles inside the loop
        listTiles(maze)
            .filter { !visitedPath.containsKey(it) }
            .forEach { markItemAs(maze, it, ".") }

        // ground with clear sight of edges are outside the loop
        listTiles(maze)
            .filter { visitedPath[it] == null }
            .filter { clearlyOutsideTheBox(maze, it) }
            .forEach {
                markItemAsOutside(maze, it)
            }
        // use a 2x2 square to find squares outside the loop
        val visitedSquares = hashSetOf<String>()
        makeSquareRegions(maze)
            .filter { isOutsideSquare(it) }
            .map { markAllAsOutside(maze, it, visitedSquares) }
        printMaze(visitedPath)
        return countTiles(maze, ".")
    }

    private fun markAllAsOutside(maze: Maze, square: Square, visitedSquares: HashSet<String>) {
        if (!visitedSquares.contains(square.hash())) {
            visitedSquares.add(square.hash())
            square.list().forEach {
                if (elementAt(it) == ".") {
                    markItemAsOutside(maze, it)
                }
            }
            Direction.entries.forEach { direction ->
                if (squareContainsOpening(maze, square, direction) &&
                    openingLeadsToSomething( maze, square, direction )) {
                    markAllAsOutside(maze, square.shiftInDirection(direction), visitedSquares)
                }
            }
        }
    }

    private fun openingLeadsToSomething(maze: Maze, square: Square, direction: Direction): Boolean {
        val maxX = maze[0].lastIndex
        val maxY: Int = maze.lastIndex
        return when (direction) {
            Direction.NORTH -> square.nw.y > 0
            Direction.SOUTH -> square.sw.y < maxY
            Direction.WEST -> square.sw.x > 0
            Direction.EAST -> square.ne.x < maxX
        }
    }

    private fun squareContainsOpening(maze: Maze, square: Square, direction: Direction): Boolean {
        val verticalOpenings = listOf( Pair("7", "F"), Pair("J", "L"), Pair("7", "L"), Pair("|", "|"), Pair("J", "F"), Pair("|", "L"), Pair("J", "|"), Pair("7", "|"), Pair("|", "F") )
        val horizontalOpenings = listOf( Pair("-", "7"), Pair("L", "-"), Pair("J", "-"), Pair("-", "-"), Pair("-", "F"), Pair("L", "7"), Pair("L", "F"), Pair("J", "F"), Pair("J", "7") )
        return when (direction) {
            Direction.NORTH -> verticalOpenings.contains(Pair(elementAt(square.nw), elementAt(square.ne))) || elementAt( square.nw ) == "O" || elementAt(square.ne) == "O"
            Direction.SOUTH -> verticalOpenings.contains(Pair(elementAt(square.sw), elementAt(square.se))) || elementAt( square.sw ) == "O" || elementAt(square.se) == "O"
            Direction.EAST -> horizontalOpenings.contains( Pair( elementAt(square.ne), elementAt(square.se) ) ) || elementAt(square.ne) == "O" || elementAt(square.se) == "O"
            Direction.WEST -> horizontalOpenings.contains( Pair( elementAt(square.nw), elementAt(square.sw) ) ) || elementAt(square.nw) == "O" || elementAt(square.sw) == "O" }
    }

    private fun isOutsideSquare(square: Square): Boolean =
        square.values().contains("O")

    private fun makeSquareRegions(maze: Maze): List<Square> {
        val squares = mutableListOf<Square>()
        val maxX = maze[0].lastIndex
        val maxY: Int = maze.lastIndex
        (0..maxY).windowed(2).forEach { ys ->
            (0..maxX).windowed(2).forEach { xs ->
                squares.add(Square(Pos(xs[0], ys[0]), Pos(xs[1], ys[0]), Pos(xs[0], ys[1]), Pos(xs[1], ys[1])))
            }
        }
        return squares
    }

    private fun countTiles(maze: Maze, s: String): Int =
        listTiles(maze).count { elementAt(it) == s }

    private fun listTiles(maze: Maze): List<Pos> {
        val tilesList = mutableListOf<Pos>()
        (0..maze.lastIndex).forEach { y ->
            (0..maze[0].lastIndex).forEach { x ->
                tilesList.add(Pos(x, y))
            }
        }
        return tilesList.toList()
    }

    private fun clearlyOutsideTheBox(maze: Maze, pos: Pos): Boolean {
        val maxX = maze[0].lastIndex
        val maxY: Int = maze.lastIndex
        if (elementAt(pos) == "O") {
            return true
        }
        return (0..<pos.x).none { networkExistsAt(maze, Pos(it, pos.y)) } ||
                (pos.x + 1..maxX).none { networkExistsAt(maze, Pos(it, pos.y)) } ||
                (0..<pos.y).none { networkExistsAt(maze, Pos(pos.x, it)) } ||
                (pos.y + 1..maxY).none { networkExistsAt(maze, Pos(pos.x, it)) }
    }

    private fun networkExistsAt(maze: Maze, pos: Pos): Boolean =
        pos.x >= 0 && pos.x < maze[0].size && pos.y >= 0 && pos.y < maze.size &&
                "|-LJ7FS".contains(maze[pos.y][pos.x])


    private fun markItemAsOutside(maze: Maze, pos: Pos) {
        maze[pos.y][pos.x] = "O"
    }

    private fun markItemAs(maze: Maze, pos: Pos, value: String) {
        maze[pos.y][pos.x] = value
    }

    inner class Square(val nw: Pos, val ne: Pos, val sw: Pos, val se: Pos) {
        fun list(): List<Pos> = listOf(nw, ne, sw, se)
        fun values(): List<String> =
            list().map { pos -> elementAt(pos) }

        override fun toString() = "${list()}: ${values()}"

        fun hash(): String {
            return "${nw.x},${nw.y},${ne.x},${ne.y},${sw.x},${sw.y},${se.x},${se.y}"
        }

        fun shiftInDirection(direction: Direction): Square =
            Square(nw.shift(direction), ne.shift(direction), sw.shift(direction), se.shift(direction))
    }
}

fun main() {
    val input = readInput("day10")
    println("part 1 input for " + input.size + "  " + Day10(input).part1())
    println("part 2 input for " + input.size + "  " + Day10(input).part2())
}
