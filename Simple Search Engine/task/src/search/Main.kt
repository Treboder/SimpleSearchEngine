package search

import java.io.File
import java.io.IOException
import java.lang.Exception

fun main(args: Array<String>) {

    // specify run mode, either in debugging mode or as desired with main args
    val debugging = false

    // desired usage with main args from command prompt, skip this in debugging mode
    if(!debugging)
        Interpreter.processArguments(args.toMutableList())

    // simulate program input via args array in debugging mode, otherwise skip
    while(debugging) {
        print("args: ")
        val arguments = mutableListOf<String>()
        arguments.addAll(readLine()!!.split(" "))
        Interpreter.processArguments(arguments)
        if(arguments[0] == "done")
            break
    }

    // main loop with user menu
    var repeat = true
    while(repeat)
        repeat = Interpreter.userMenuLoop()

    println()
    println("Bye!")
}

object Interpreter {
    fun processArguments(args: MutableList<String>) {
        if (args.size == 1)
            when (args[0]) {
                "--data" -> throw Exception("--data is unknown command.")
                "done" -> println("program arguments processed in debug mode")
                else -> println("'${args[0]}' is unknown command.")
            }
        else if (args.size == 2)
            when (args[0]) {
                "--data" -> InputData.initializeWithFile(args[1])
                else -> println("'${args[1]}' is unknown command.")
            }
    }

    fun userMenuLoop():Boolean {
        println("")
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
        //print("> ")
        when(readln()) {
            "1" -> SearchEngine.findPerson()
            "2" -> InputData.printAllPeople()
            "0" -> return false
            else -> println("\nIncorrect option! Try again.")
        }
        return true
    }
}

object InputData {
    var contentList2D = mutableListOf(listOf<String>()) // list if lines where each line itself is a list of words
    var mapPairingWordsWithListOfIndices = mutableMapOf<String, MutableList<Int>>() // map that pairs every word from input with a list of line indices (corresponding with contentList2D)

    fun initializeWithFile(fileName: String) {
        readInputFile(fileName)
        createContentMap()
    }

    private fun readInputFile(name: String) {
        val rootDirectory = File(System.getProperty("user.dir"))
        try {
            val lines = File(rootDirectory.absolutePath + "\\" +name).readLines()
            for(line in lines)
                contentList2D.add(line.split(' '))
        }
        catch (e: IOException)  {
            println("Can't read input file!")
        }
    }

    private fun createContentMap() {
        for(lineIndex in 0 until contentList2D.count())
            for (word in contentList2D[lineIndex])
                if(!mapPairingWordsWithListOfIndices.containsKey(word.lowercase()))
                    mapPairingWordsWithListOfIndices.put(word.lowercase(), mutableListOf(lineIndex))
                else
                    mapPairingWordsWithListOfIndices[word.lowercase()]!!.add(lineIndex)
    }

    fun printAllPeople() {
        println("")
        println("=== List of people ===")
        for(line in contentList2D)
            println(line.joinToString(" "))
    }
}

object SearchEngine {
    fun findPerson() {
        println()
        println("Enter a name or email to search all matching people.")
        //print("> ")
        val termToSearch = readLine()!!.lowercase()
        //val searchResults = searchForInputLinesThatContainTheTerm(termToSearch)
        if(InputData.mapPairingWordsWithListOfIndices.containsKey(termToSearch)) {
            val foundIndices = InputData.mapPairingWordsWithListOfIndices[termToSearch]
            println("${foundIndices!!.count()} persons found:")
            for (index in foundIndices)
                println(InputData.contentList2D[index].joinToString(" "))
        }
        else
            println("No matching people found.")
    }
}