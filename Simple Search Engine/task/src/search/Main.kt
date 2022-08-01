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
    if(debugging) {
        print("args: ")
        val arguments = mutableListOf<String>()
        arguments.addAll(readLine()!!.split(" "))
        Interpreter.processArguments(arguments)
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
    var listOfLinesWithListOfWords = mutableListOf(listOf<String>()) // list if lines where each line itself is a list of words
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
                listOfLinesWithListOfWords.add(line.split(' '))
        }
        catch (e: IOException)  {
            println("Can't read input file!")
        }
    }

    private fun createContentMap() {
        for(lineIndex in 0 until listOfLinesWithListOfWords.count())
            for (word in listOfLinesWithListOfWords[lineIndex])
                if(!mapPairingWordsWithListOfIndices.containsKey(word.lowercase()))
                    mapPairingWordsWithListOfIndices.put(word.lowercase(), mutableListOf(lineIndex))
                else
                    mapPairingWordsWithListOfIndices[word.lowercase()]!!.add(lineIndex)
    }

    fun printAllPeople() {
        println("")
        println("=== List of people ===")
        for(line in listOfLinesWithListOfWords)
            println(line.joinToString(" "))
    }
}

object SearchEngine {
    fun findPerson() {
        val (searchMode, termToSearch) = getSearchQueryAndSearchMode()
        val foundIndices = mutableListOf<Int>()
        when(searchMode) {
            "ALL" -> foundIndices.addAll(findAll(termToSearch))
            "ANY" -> foundIndices.addAll(findAny(termToSearch))
            "NONE" -> foundIndices.addAll(findNone(termToSearch))
            else -> println("$searchMode is unknown command.")
        }
        printSearchResults(foundIndices)
    }

    private fun getSearchQueryAndSearchMode(): Pair<String, String> {
        println()
        println("Select a matching strategy: ALL, ANY, NONE")
        val searchMode = readLine()!!
        println()
        println("Enter a name or email to search all matching people.")
        val termToSearch = readLine()!!.lowercase()
        return Pair(searchMode, termToSearch)
    }

    private fun findAll(termToSearch:String):MutableList<Int> {
        val wordsToSearch = termToSearch.split(" ")
        val lineIndicesToReturn = mutableListOf<Int>()
        for(lineIndex in 0 until InputData.listOfLinesWithListOfWords.size) {
            var lineContainsAllWords = true
            for(word in wordsToSearch) {
                if(!InputData.listOfLinesWithListOfWords[lineIndex].joinToString().contains(word, ignoreCase = true))
                    lineContainsAllWords = false
            }
            if(lineContainsAllWords)
                lineIndicesToReturn.add(lineIndex)
        }
        return lineIndicesToReturn
    }

    private fun findAny(termToSearch:String):MutableList<Int> {
        val wordsToSearch = termToSearch.split(" ")
        val lineIndicesToReturn = mutableListOf<Int>()
        for(word in wordsToSearch)
            lineIndicesToReturn.addAll(findWord(word))
        return lineIndicesToReturn
    }

    private fun findNone(termToSearch:String):MutableList<Int> {
        val wordsToSearch = termToSearch.split(" ")
        val lineIndicesToReturn = mutableListOf<Int>()
        for(lineIndex in 0 until InputData.listOfLinesWithListOfWords.size) {
            var lineContainsNoWord = true
            for(word in wordsToSearch) {
                if(InputData.listOfLinesWithListOfWords[lineIndex].joinToString().contains(word, ignoreCase = true))
                    lineContainsNoWord = false
            }
            if(lineContainsNoWord)
                lineIndicesToReturn.add(lineIndex)
        }
        return lineIndicesToReturn
    }

    private fun findWord(termToSearch:String): MutableList<Int> {
        val foundIndices = mutableListOf<Int>()
        if(InputData.mapPairingWordsWithListOfIndices.containsKey(termToSearch))
            foundIndices.addAll(InputData.mapPairingWordsWithListOfIndices[termToSearch]!!)
        return foundIndices
    }

    private fun printSearchResults(foundIndices: MutableList<Int>) {
        if (foundIndices.isNotEmpty()) {
            println("${foundIndices.count()} persons found:")
            for (index in foundIndices)
                println(InputData.listOfLinesWithListOfWords[index].joinToString(" "))
        } else
            println("No matching people found.")
    }
}