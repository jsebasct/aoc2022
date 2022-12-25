import java.math.BigInteger

data class FileDevice(
    val name: String,
    var size: Int = 0,
    val content: MutableMap<String, FileDevice>? = null,
    val isDirectory: Boolean = false,
    val parentDirectory: FileDevice? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileDevice

        if (name != other.name) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + size
        return result
    }

    fun getTotalSize(): Int {
        return if (this.isDirectory) {
            this.content?.toList()?.fold(0) { acc, element ->
                acc + element.second.getTotalSize()
            } ?: 0
        } else {
            this.size
        }
    }

    fun setFolderSizes() {
        if (this.isDirectory) {
            val size = this.content?.toList()?.fold(0) { acc, element ->
                if (element.second.isDirectory) {
                    element.second.setFolderSizes()
                }
                acc + element.second.getTotalSize()
            } ?: 0
            this.size = size
        } else {
            this.size
        }
    }

    fun getDirectoriesWithAtMost(maxSize: Int): BigInteger {
        return this.content?.values?.fold(BigInteger.ZERO) { acc, element ->
            val temp = if (element.isDirectory && element.size <= maxSize) element.size.toBigInteger() else BigInteger.ZERO
            val temp2 = if (element.isDirectory) element.getDirectoriesWithAtMost(maxSize) else BigInteger.ZERO
            acc + temp + temp2
        } ?: BigInteger.ZERO
    }

    fun getAllFolders(acc: MutableList<FileDevice>) {
        if (this.isDirectory) {
            acc.add(this)
            this.content?.values?.forEach {
                it.getAllFolders(acc)
            }
        }
    }
}

fun main() {

    fun parseInput(input: List<String>, rootDirectory: FileDevice) {
        var currentDirectory = rootDirectory

        input.forEachIndexed { index, line ->

            if (index > 0) { //&& index < 19
//                println(line)
                // check if command
                if (line.startsWith("$")) {
                    // means its a command
                    // tehre are two types of commands
                    if (line.contains("cd")) {

                        val (_, _, nameDirectory) = line.split(" ")
//                        println("change to this directory: $nameDirectory")
                        if (nameDirectory == "..") {
//                            println("que hacemos ?")
                            currentDirectory = currentDirectory.parentDirectory!!
                        } else {
                            currentDirectory = currentDirectory.content!![nameDirectory]!!
                        }
                    } else {
                        // ls
//                        println("ahi viene una lista de contenido")
                    }
                } else {
                    // salida de un comando LS
                    if (line.startsWith("dir")) {
                        val (_, name) = line.split(" ")
                        currentDirectory.content!![name] = FileDevice(
                            name = name, isDirectory = true, content = mutableMapOf(),
                            parentDirectory = currentDirectory
                        )
                    } else {
                        val (size, name) = line.split(" ")
                        currentDirectory.content!![name] = FileDevice(
                            name = name, size = size.toInt(),
                            parentDirectory = currentDirectory
                        )
                    }
                }
            }
        }
    }

    fun part71(input: List<String>, rootDirectory: FileDevice): BigInteger {
        parseInput(input, rootDirectory)
        rootDirectory.setFolderSizes()

        val res1 = rootDirectory.getDirectoriesWithAtMost(100000)
        println("total size: $res1")

        return res1
    }

    fun part72(input: List<String>, rootDirectory: FileDevice): Int {
        val requiredUnusedSpace = 30000000

        parseInput(input, rootDirectory)
        rootDirectory.setFolderSizes()

        val unusedSpace = 70000000 - rootDirectory.size

        val neededAtLeast = 30000000 - unusedSpace
        println("needed: $neededAtLeast")

        val acc = mutableListOf<FileDevice>()
        rootDirectory.getAllFolders(acc)


        val res2 = acc.filter { it.size >= neededAtLeast}.minBy { it.size }
//        accumulatorFiltered.forEach { println("${it.name}: ${it.size}") }

//        val res1 = rootDirectory.getDirectoriesWithAtMost(100000)
//        println("total size: $res1")

        return res2.size
    }


    val input = readInput("Day07_test")

    val rootDirectory = FileDevice(
        name="root/",
        content = mutableMapOf<String, FileDevice>(),
        isDirectory = true
    )

    val part71Response = part71(input, rootDirectory)
//    check(part71Response == 95437.toBigInteger())

    val rootDirectory2 = FileDevice(
        name="root/",
        content = mutableMapOf<String, FileDevice>(),
        isDirectory = true
    )

    val part72Response = part72(input, rootDirectory2)
    println("response part2: $part72Response")
//    check(part72Response == 24933642)

}
