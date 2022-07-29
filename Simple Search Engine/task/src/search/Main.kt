package search

import java.io.File
import java.io.IOException


fun main(args: Array<String>) {

    // specify run mode, either in debugging mode or as desired with main args
    val debugging = false

    // desired usage with main args from command prompt
    if(!debugging)
        Interpreter.processArguments(args.toMutableList())

    // run interpreter in endless loop for easy debugging
    while(debugging) {
        print("args: ")
        val arguments = mutableListOf<String>()
        arguments.addAll(readLine()!!.split(" "))
        Interpreter.processArguments(arguments)
        if(arguments[0] == "done")
            break
    }

    // endless user loop
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
                "--data" -> true
                else -> println("'${args[0]}' is unknown command.")
            }

        if (args.size == 2)
            when (args[0]) {
                "--data" -> readInputFile(args[1])
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
            "1" -> findPerson(InputMatrix.content)
            "2" -> printAllPeople(InputMatrix.content)
            "0" -> return false
            else -> println("\nIncorrect option! Try again.")
        }
        return true
    }

    fun readInputFile(name: String) {
        val rootDirectory = File(System.getProperty("user.dir"))
        try {
            var lines = File(rootDirectory.absolutePath + "\\" +name)!!.readLines()
            for(line in lines)
                InputMatrix.content.add(line.split(' '))
        }
        catch (e: IOException)  {
            println("Can't read input file!")
        }
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
        println("")
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
}

object InputMatrix {
    val content = mutableListOf<List<String>>(listOf<String>())
}