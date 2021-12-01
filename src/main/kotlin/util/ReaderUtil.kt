package util

class ReaderUtil {
    companion object {
        fun readResourseAsStrings(fileName: String) : List<String> =
            this::class.java.classLoader.getResourceAsStream(fileName)?.bufferedReader()!!.readLines()
    }
}
