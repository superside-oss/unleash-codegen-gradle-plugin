package org.superside.unleash.codegen.util

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

/**
 * Converts a string of words to map of words and calculated length of each word,
 * used for reformatting long strings based in maxLength value, to avoid linter failures.
 */
fun String.toMapOfWords(): Map<String, Int> = this
    .split(" ")
    .mapIndexed { index, s ->
        s to s.length + (if (index > 0) this.split(" ")[index - 1].length else 0)
    }
    .toMap()

/**
 * Reformat a string, breaking a line right before the maxLength value, to avoid linter failures.
 */
fun Map<String, Int>.toFormattedLine(maxLength: Int = 99): String = this
    .mapKeys { if (it.value in maxLength - 30..maxLength) "${it.key}\n" else "${it.key}" }.keys
    .toList()
    .joinToString(separator = " ")
