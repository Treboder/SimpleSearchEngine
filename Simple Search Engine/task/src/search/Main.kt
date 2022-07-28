package search

fun main() {

    print("> ")
    var input = readLine()!!.split(' ')
    print("> ")
    var item = readLine()!!


    var position = input.indexOf(item)+1 
    if(position != 0)
        println(position)
    else            
        println("Not found")

}
