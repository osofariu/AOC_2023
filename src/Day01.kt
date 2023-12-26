 class Day01 {
      fun part1(input: List<String>): Int {
          return input.sumOf { extractCalibrationCode(it) }
      }

     private fun extractCalibrationCode(calibrationText: String): Int {
         val firstDigit = calibrationText.first { it.isDigit() }.toString().toInt()
         val lastDigit = calibrationText.last { it.isDigit() }.toString().toInt()
         return 10 * firstDigit + lastDigit;
     }
}

 fun main(args: Array<String>) {
     val input = readInput("day01")
     println("input for " + input.size + " records: " + Day01().part1(input))
 }
