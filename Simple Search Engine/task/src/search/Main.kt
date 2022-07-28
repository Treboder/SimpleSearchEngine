package search

fun main() {

    println("Enter the number of people:")
    print("> ")
    var inputLines = readLine()!!.toInt()

    var inputs = mutableListOf<List<String>>(listOf<String>())
    println("Enter all people:")
    repeat(inputLines)
    {
        print("> ")
        inputs.add(readLine()!!.split(' '))
    }

    println()
    println("Enter the number of search queries:")
    print("> ")
    var queries = readLine()!!.toInt()

    repeat(queries)
    {
        println()
        println("Enter data to search people:")
        print("> ")
        var term = readLine()!!
        var results = search(term, inputs)
        if(results.size>0)
        {
            println()
            println("People found:")
            for(line in results)
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
        {
            if(item.lowercase() == term.lowercase() || item.contains(term, ignoreCase = true))
            {
                results.add(line.joinToString(" "))
                break
            }
        }
    return results
}
