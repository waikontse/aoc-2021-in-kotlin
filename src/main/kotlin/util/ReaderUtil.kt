package util

class ReaderUtil {
    companion object {
        fun readResourceAsStrings(fileName: String) : List<String> =
            this::class.java.classLoader.getResourceAsStream(fileName)?.bufferedReader()!!.readLines()
    }
}
