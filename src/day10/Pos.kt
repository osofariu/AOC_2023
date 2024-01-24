package day10

data class Pos(val x: Int, val y: Int) {
    fun shift(direction: Direction): Pos =
        when (direction) {
            Direction.NORTH -> Pos(x, y - 1)
            Direction.EAST -> Pos(x + 1, y)
            Direction.WEST -> Pos(x - 1, y)
            Direction.SOUTH -> Pos(x, y + 1)
        }
}
