package org.superside.constants.util

fun String.toEnumStyle(): String = this
    .camelCaseToSnakeCase()
    .replace("-", "_")
    .replace(".", "_")
    .toUpperCase()

fun String.camelCaseToSnakeCase(): String = this
    .replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")
    .replace("([A-Z])([A-Z][a-z])".toRegex(), "$1_$2")
    .replace("-", "_")
    .toLowerCase()

fun String.toArrayOfWords(): Array<String> = this
    .split(" ").toTypedArray()

fun Array<String>.addLineDelimiterAfterEachFifthWord(): String {
    var result = ""
    for (i in this.indices) {
        result += this[i]
        result += if (i % 7 == 0 && i != 0) {
            "\n"
        } else {
            " "
        }
    }
    return result
}
