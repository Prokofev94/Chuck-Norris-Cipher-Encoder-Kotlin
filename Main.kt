package chucknorris

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val input = readln()) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> {
                println("Bye!")
                break
            }
            else -> println("There is no '$input' operation")
        }
        println("\n")
    }
}

fun encode() {
    println("Input string:")
    val string = readln()
    println("Encoded string:")
    var bin = ""
    for (ch in string) {
        bin += String.format("%07d", ch.code.toString(2).toInt())
    }
    var last = ' '
    for (b in bin) {
        if (b == last) {
            print("0")
        } else {
            print(
                if (b == '0') {
                    " 00 0"
                } else {
                    " 0 0"
                }
            )
        }
        last = b
    }
}

fun decode() {
    println("Input encoded string:")
    val cipher = readln().split(" ")
    if (isNotValid(cipher)) {
        println("Encoded string is not valid.")
        return
    }
    var zero = cipher[0] == "00"
    var bin = ""
    for (i in 1 until cipher.size step 2) {
        val c = if (zero) "0" else "1"
        bin += c.repeat(cipher[i].length)
        zero = !zero
    }
    println("Decoded string:")
    for (i in bin.indices step 7) {
        print(Integer.parseInt(bin.substring(i, i + 7), 2).toChar())
    }
}

fun isNotValid(cipher: List<String>): Boolean {
    if (cipher.size % 2 != 0) {
        return true
    }
    var len = 0
    for (i in 1 until cipher.size step 2) {
        len += cipher[i].length
    }
    if (len % 7 != 0) {
        return true
    }
    for (i in cipher.indices step 2) {
        if (!"00?".toRegex().matches(cipher[i])) {
            return true
        }
    }
    for (i in cipher) {
        if (!"0+".toRegex().matches(i)) {
            return true
        }
    }
    return false
}