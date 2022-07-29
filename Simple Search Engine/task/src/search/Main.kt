package search

fun main() {

    // clarify how many lines of input to be used
    println("Enter the number of people:")
    print("> ")
    val numberOfInputLines = readLine()!!.toInt()

    // fill input matrix line by line
    val inputMatrix = mutableListOf<List<String>>(listOf<String>())
    println("Enter all people:")
    repeat(numberOfInputLines) {
        print("> ")
        inputMatrix.add(readLine()!!.split(' '))
    }

    // user menu loop
    while(true) {
        println("")
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
        //print("> ")
        when(readln()) {
            "1" -> findPerson(inputMatrix)
            "2" -> printAllPeople(inputMatrix)
            "0" -> break
            else -> println("\nIncorrect option! Try again.")
        }
    }
    println()
    println("Bye!")
}

fun findPerson(inputMatrix: MutableList<List<String>>) {
    println()
    println("Enter a name or email to search all suitable people.")
    //print("> ")
    val termToSearch = readLine()!!
    val searchResults = searchForInputLinesThatContainTheTerm(termToSearch, inputMatrix)
    if (searchResults.isNotEmpty())
        for (line in searchResults)
            println(line)
    else
        println("No matching people found.")
}

fun printAllPeople(inputMatrix: MutableList<List<String>>) {
    println("=== List of people ===")
    for(line in inputMatrix)
        println(line.joinToString(" "))
}

fun searchForInputLinesThatContainTheTerm(term:String, inputs:List<List<String>>):List<String> {
    val results = mutableListOf<String>()
    for(line in inputs)
        for(item in line)
            if(item.contains(term, ignoreCase = true)) {
                results.add(line.joinToString(" "))
                break
            }
    return results
}
