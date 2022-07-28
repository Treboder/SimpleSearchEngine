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

    // clarify how many search queries to perform
    println()
    println("Enter the number of search queries:")
    print("> ")
    val numberOfSearchQueries = readLine()!!.toInt()

    // perform search queries in sequence
    repeat(numberOfSearchQueries) {
        println()
        println("Enter data to search people:")
        print("> ")
        val termToSearch = readLine()!!
        val searchResults = search(termToSearch, inputMatrix)
        if(searchResults.isNotEmpty()) {
            println()
            println("People found:")
            for(line in searchResults)
                println(line)
        }
        else
            println("No matching people found.")
    }
}

fun search(term:String, inputs:List<List<String>>):List<String> {
    val results = mutableListOf<String>()
    for(line in inputs)
        for(item in line)
            if(item.contains(term, ignoreCase = true)) {
                results.add(line.joinToString(" "))
                break
            }
    return results
}
