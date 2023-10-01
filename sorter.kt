package main
import java.io.File
import kotlin.reflect.KClass

fun main() {
//    C:\Users\garon\IdeaProjects\IKT_II_projekt_001\src\main\kotlin\main\ki.txt
    val FILEPATH = input("Enter exact filepath: ").convert(String::class)
    val sort = Sorter(FILEPATH)
    val DATA = sort.dp.processedData(FILEPATH)
    val insertionSort = Sorter.insertionSort()
    val sortNumsFromMergeSort = Sorter.mergeSort.sortNums()
    val sortStringsFromMergeSort = Sorter.mergeSort.sortStrings()
    val sortOrder = sort.getSortOrder()
    var sortedData: MutableList<String>
    val algChoice = input("Enter which sort would you like to use:\n\t[A] > Insertion Sort\n\t[B] > Merge Sort\n\\|").convert(String::class).uppercase()
    when (algChoice) {
        "A" -> {
            if (sort.dataType == DataType.Numbers) {
                sortedData = insertionSort.sortNumsByInsertionSort(DATA, sortOrder)
                println(sortedData)
            }
            else {
                sortedData = insertionSort.sortStringsByInsertionSort(DATA, sortOrder)
                println(sortedData)
            }
        }
        "B" -> {
            if (sort.dataType == DataType.Numbers) {
                val MSINTDATA = DATA.map { it.convert(Int::class) }.toMutableList()
                if (!sortOrder) { sortedData = sortNumsFromMergeSort.sortNumsByMergeSort(MSINTDATA).map { it.convert(String::class) }.reversed().toMutableList() }
                else { sortedData = sortNumsFromMergeSort.sortNumsByMergeSort(MSINTDATA).map { it.convert(String::class) }.toMutableList() }
                println(sortedData)
            } else {
                if (!sortOrder) { sortedData = sortStringsFromMergeSort.sortStringsByMergeSort(DATA).reversed().toMutableList() }
                else { sortedData = sortStringsFromMergeSort.sortStringsByMergeSort(DATA) }
                println(sortedData)
            }
        }
        else -> throw CustomException("Your input is incorrect!")
    }
    val NDE = newDataElement()
    if (input("Would you like to add a new element? ([y]/[n]):  ").toString() == "y") {
        sortedData = when (algChoice) {
            "A" -> {
                if (sort.dataType == DataType.Numbers) { insertionSort.sortNumsByInsertionSort(NDE.insertIntoSortedData(sortedData, NDE.getNewDataElement(sort.dataType), sort.dataType), sortOrder) }
                else { insertionSort.sortStringsByInsertionSort(NDE.insertIntoSortedData(sortedData, NDE.getNewDataElement(sort.dataType), sort.dataType), sortOrder) }
            }
            "B" -> {
                if (sort.dataType == DataType.Numbers) {
                    val MSINTDATA = (NDE.insertIntoSortedData(sortedData, NDE.getNewDataElement(sort.dataType), sort.dataType)).map { it.convert(Int::class) }.toMutableList()
                    if (!sortOrder) { sortNumsFromMergeSort.sortNumsByMergeSort(MSINTDATA).map { it.convert(String::class) }.reversed().toMutableList() }
                    else { sortNumsFromMergeSort.sortNumsByMergeSort(MSINTDATA).map { it.convert(String::class) }.toMutableList() }
                } else {
                    if (!sortOrder) { sortStringsFromMergeSort.sortStringsByMergeSort(NDE.insertIntoSortedData(sortedData, NDE.getNewDataElement(sort.dataType), sort.dataType)).reversed().toMutableList() }
                    else { sortStringsFromMergeSort.sortStringsByMergeSort(NDE.insertIntoSortedData(sortedData, NDE.getNewDataElement(sort.dataType), sort.dataType)) }
                }
            }
            else -> throw CustomException("Your input is incorrect!")
        }
        println(sortedData)
    } else { System.exit(0) }

}

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())
fun String.isAlpha(): Boolean = this.matches("[a-zA-Z]+".toRegex())

class dataPrep {
    fun fileReader(filePath: String): MutableList<*> {
        File(filePath).takeIf { it.exists() }?.let { file ->
            file.readText().let {
//            println(content)
                val content = it.trim().split(";").toMutableList()
                content.removeLast()
                return content
            }
        } ?: throw CustomException("File does not exist.")
    }

    fun processedData(FILEPATH: String): MutableList<String> {
        val vd = verifyData()
        val readData = fileReader(FILEPATH)
        return vd.dataVerifier(readData)
    }
}

enum class DataType { Numbers, Strings }
class verifyData {
    fun dataError(): Nothing = throw CustomException("The provided dataset does not match the required criteria!")
    fun dataVerifier(data: MutableList<*>): MutableList<String> {
        val verifiedData = mutableListOf<String>()
        data.filter { it != null }.map { verifiedData.add(it.toString()) }
        return verifiedData
    }
    fun determineDataType(data: MutableList<String>): DataType {
        // Check if the data contains numbers or strings
        return if (data.all { it.isNumeric() }) DataType.Numbers // Numbers
        else if (data.all { it.isAlpha() }) DataType.Strings // Strings
        else dataError() // Data incorrect
    }
}

class Sorter(FILEPATH: String) {
    val dp = dataPrep()
    val vd = verifyData()
    val dataType = vd.determineDataType(dp.processedData(FILEPATH))

    // Get the user's choice for sorting order (true for ascending, false for descending)
    fun getSortOrder(): Boolean = input("Choose sorting order [1] for ascending, [0] for descending:  ").convert(Int::class) == 1

    class insertionSort {
        fun sortNumsByInsertionSort(dataIn: MutableList<String>, direction: Boolean): MutableList<String> { // Insertion Sort
            val data = dataIn.map { it.convert(Int::class) }.toMutableList()
            val n = data.size
            for (i in 1..n-1) {
                val key = data[i]
                var j = i -1
                while (j >= 0 && data[j] > key) {
                    data[j+1] = data[j]
                    j = j - 1
                }
                data[j+1] = key
            }
            val dataOut = data.map { it.convert(String::class) }.toMutableList()
            return if (direction) { dataOut } else { dataOut.reversed().toMutableList() }
        }

        fun sortStringsByInsertionSort(data: MutableList<String>, direction: Boolean): MutableList<String> { // Insertion Sort
            val n = data.size
            for (i in 1..n-1) {
                val key = data[i]
                var j = i -1
                while (j >= 0 && data[j] > key) {
                    data[j+1] = data[j]
                    j = j - 1
                }
                data[j+1] = key
            }
            return if (direction) { data } else { data.reversed().toMutableList() }
        }
    }

    class mergeSort {
        class sortNums {
            fun mergeNums(left: MutableList<Int>, right: MutableList<Int>): MutableList<Int> {
                var result = mutableListOf<Int>()
                var (leftIndex, rightIndex) = Pair(0, 0)

                while (leftIndex < left.size && rightIndex < right.size) {
                    if (left[leftIndex] < right[rightIndex]) {
                        result.add(left[leftIndex])
                        leftIndex++
                    } else {
                        result.add(right[rightIndex])
                        rightIndex++
                    }
                }

                result.addAll(left.subList(leftIndex, left.size))
                result.addAll(right.subList(rightIndex, right.size))

                return result
            }

            fun sortNumsByMergeSort(data: MutableList<Int>): MutableList<Int> {
                if (data.size <= 1) {
                    return data
                }

                var middle = (data.size).div(2)
                var leftHalf = data.subList(0, middle)
                var rightHalf = data.subList(middle, data.size)

                leftHalf = sortNumsByMergeSort(leftHalf)
                rightHalf = sortNumsByMergeSort(rightHalf)

                return mergeNums(leftHalf, rightHalf)
            }
        }

        class sortStrings {
            fun mergeStrings(left: MutableList<String>, right: MutableList<String>): MutableList<String> {
                var result = mutableListOf<String>()
                var (leftIndex, rightIndex) = Pair(0, 0)

                while (leftIndex < left.size && rightIndex < right.size) {
                    if (left[leftIndex] < right[rightIndex]) {
                        result.add(left[leftIndex])
                        leftIndex++
                    } else {
                        result.add(right[rightIndex])
                        rightIndex++
                    }
                }

                result.addAll(left.subList(leftIndex, left.size))
                result.addAll(right.subList(rightIndex, right.size))

                return result
            }

            fun sortStringsByMergeSort(data: MutableList<String>): MutableList<String> {
                if (data.size <= 1) {
                    return data
                }

                var middle = (data.size).div(2)
                var leftHalf = data.subList(0, middle)
                var rightHalf = data.subList(middle, data.size)

                leftHalf = sortStringsByMergeSort(leftHalf)
                rightHalf = sortStringsByMergeSort(rightHalf)

                return mergeStrings(leftHalf, rightHalf)
            }

        }
    }
}

class newDataElement {
    fun getNewDataElement(dataType: DataType): String {
        var newData = input("Enter a new ${if (dataType == DataType.Numbers) "number" else "string"}:  ").convert(String::class)
        while ((dataType == DataType.Numbers && !newData.isNumeric()) || (dataType == DataType.Strings && !newData.isAlpha())) {
            newData = input("Enter a new ${if (dataType == DataType.Numbers) "number" else "string"}:  ").convert(String::class)
        }
        return newData
    }

    fun insertIntoSortedData(sortedData: MutableList<String>, newData: String, dataType: DataType): MutableList<String> {
        if (dataType == DataType.Numbers) { sortedData.map { it.convert(Int::class) }.toMutableList(); newData.convert(Int::class) }
        sortedData.add(sortedData.size, newData)
        return sortedData
    }
}

// Utilities
class CustomException(message: String) : Exception(message)

fun input (prompt: Any): Any {
    print(prompt)
    return readln()
}


inline fun <reified T : Any> Any.convert(type: KClass<T>): T {
    return when (type) {
        Boolean::class -> this.toString().toBoolean() as T
        Byte::class -> this.toString().toByte() as T
        Short::class -> this.toString().toShort() as T
        Int::class -> this.toString().toInt() as T
        Long::class -> this.toString().toLong() as T
        Float::class -> this.toString().toFloat() as T
        Double::class -> this.toString().toDouble() as T
        Char::class -> this.toString().toInt().toChar() as T
        String::class -> this.toString() as T
        List::class -> listOf(this) as T
        MutableList::class -> mutableListOf(this) as T
        Array::class -> arrayOf(this) as T
        Set::class -> setOf(this) as T
        Map::class -> { val pair = this as Pair<Any?, Any?>; mapOf(pair) as T }
        else -> throw ClassCastException("Unsupported type: $type")
    }
}
