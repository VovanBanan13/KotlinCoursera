package mastermind

data class Evaluation(var rightPosition: Int, var wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var newSecret: String = secret
    var newGuess: String = guess
    val evaluation = Evaluation(0, 0)
    for (i in 0..3) {
        if (newSecret[i] == newGuess[i]) {
            evaluation.rightPosition += 1
            newSecret = newSecret.replaceFirst(newSecret[i], (i+10).toChar())
            newGuess = newGuess.replaceFirst(newGuess[i], (i+20).toChar())
        }
    }
    for (i in 0..3) {
        if (newGuess[i] in newSecret) {
            evaluation.wrongPosition += 1
            newSecret = newSecret.replaceFirst(newGuess[i], (i+30).toChar())
        }
    }

    return evaluation
}
