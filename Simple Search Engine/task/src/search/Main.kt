package search

fun main() {

    // clarify how many lines of input to be used
    println("Enter the number of people:")
    print("> ")
    var numberOfInputLines = readLine()!!.toInt()

    // fill input matrix line by line
    var inputMatrix = mutableListOf<List<String>>(listOf<String>())
    println("Enter all people:")
    repeat(numberOfInputLines) {
        print("> ")
        inputMatrix.add(readLine()!!.split(' '))
    }

    // clarify how many search queries to perform
    println()
    println("Enter the number of search queries:")
    print("> ")
    var numberOfSearchQueries = readLine()!!.toInt()

    // perform search queries in sequence
    repeat(numberOfSearchQueries) {
        println()
        println("Enter data to search people:")
        print("> ")
        var termToSearch = readLine()!!
        var searchResults = search(termToSearch, inputMatrix)
        if(searchResults.size > 0) {
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
    var results = mutableListOf<String>()
    for(line in inputs)
        for(item in line)
            if(item.contains(term, ignoreCase = true)) {
                results.add(line.joinToString(" "))
                break
            }
    return results
}
