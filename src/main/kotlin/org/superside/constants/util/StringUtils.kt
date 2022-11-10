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

fun String.toMapOfWords(): Map<String, Int> = this
    .split(" ")
    .mapIndexed { index, s ->
        s to s.length + (if (index > 0) this.split(" ")[index - 1].length else 0)
    }
    .toMap()

fun Map<String, Int>.toFormattedLine(maxLength: Int = 99): String = this
    .mapKeys { if (it.value in maxLength - 30..maxLength) "${it.key}\n" else "${it.key}" }.keys
    .toList()
    .joinToString(separator = " ")
