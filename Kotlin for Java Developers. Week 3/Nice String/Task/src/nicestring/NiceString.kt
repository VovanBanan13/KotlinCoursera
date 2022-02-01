package nicestring

fun String.isNice(): Boolean {
    var globalCount = 0
    val substrings = listOf("bu", "ba", "be")
    val vowels = listOf('a', 'e', 'i', 'o', 'u')

    val substringContain = !contains("bu") && !contains("ba") && !contains("be")
    if (substringContain) globalCount += 1

    var countV = 0
    for (ch in this) {
        if (ch in vowels) {
            countV += 1
        }
    }
    if (countV >= 3) globalCount += 1

    for ((i, ch) in this.withIndex()) {
        if (i != this.length-1) {
            val n = this[i+1]
            if (ch == n) { globalCount += 1 }
        }
    }

    return globalCount >= 2
}
