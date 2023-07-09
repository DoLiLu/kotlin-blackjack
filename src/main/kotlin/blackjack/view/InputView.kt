package blackjack.view

const val USER_NAME_SPLIT_SYMBOL = ","

fun enterUserNames(): List<String> {
    println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)")
    return readln().trim().split(USER_NAME_SPLIT_SYMBOL)
}

fun printCardReceiveNotWant(name: String): Boolean {
    println("${name}는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)")

    val receiveWant = readln()
    if (receiveWant == "y") {
        return false
    }

    if (receiveWant == "n") {
        return true
    }

    return printCardReceiveNotWant(name)
}
