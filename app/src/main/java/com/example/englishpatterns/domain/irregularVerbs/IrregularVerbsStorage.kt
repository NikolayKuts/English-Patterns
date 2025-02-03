package com.example.englishpatterns.domain.irregularVerbs

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.FullyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyConsistent
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.Unchanging

object IrregularVerbsStorage {

    val unchanging = listOf(
        VerbDetails(
            word = "Стоить",
            v1 = Verb(word = "cost", ipa = "[kost]"),
            v2 = Verb(word = "cost", ipa = "[kost]"),
            v3 = Verb(word = "cost", ipa = "[kost]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Подходить по размеру",
            v1 = Verb(word = "fit", ipa = "[fɪt]"),
            v2 = Verb(word = "fit", ipa = "[fɪt]"),
            v3 = Verb(word = "fit", ipa = "[fɪt]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Попадать в цель",
            v1 = Verb(word = "hit", ipa = "[hɪt]"),
            v2 = Verb(word = "hit", ipa = "[hɪt]"),
            v3 = Verb(word = "hit", ipa = "[hɪt]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Ушибить",
            v1 = Verb(word = "hurt", ipa = "[hɜːt]"),
            v2 = Verb(word = "hurt", ipa = "[hɜːt]"),
            v3 = Verb(word = "hurt", ipa = "[hɜːt]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Позволять",
            v1 = Verb(word = "let", ipa = "[let]"),
            v2 = Verb(word = "let", ipa = "[let]"),
            v3 = Verb(word = "let", ipa = "[let]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Ставить",
            v1 = Verb(word = "set", ipa = "[set]"),
            v2 = Verb(word = "set", ipa = "[set]"),
            v3 = Verb(word = "set", ipa = "[set]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Мочить",
            v1 = Verb(word = "wet", ipa = "[wet]"),
            v2 = Verb(word = "wet", ipa = "[wet]"),
            v3 = Verb(word = "wet", ipa = "[wet]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Положить",
            v1 = Verb(word = "put", ipa = "[put]"),
            v2 = Verb(word = "put", ipa = "[put]"),
            v3 = Verb(word = "put", ipa = "[put]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Резать",
            v1 = Verb(word = "cut", ipa = "[kʌt]"),
            v2 = Verb(word = "cut", ipa = "[kʌt]"),
            v3 = Verb(word = "cut", ipa = "[kʌt]"),
            type = Unchanging
        ),
        VerbDetails(
            word = "Закрывать",
            v1 = Verb(word = "shut", ipa = "[ʃʌt]"),
            v2 = Verb(word = "shut", ipa = "[ʃʌt]"),
            v3 = Verb(word = "shut", ipa = "[ʃʌt]"),
            type = Unchanging
        ),
    )

    val partiallyChanging = listOf(
        VerbDetails(
            word = "Становиться",
            v1 = Verb(word = "become", ipa = "[bɪ'kʌm]"),
            v2 = Verb(word = "became", ipa = "[bɪ'keɪm]"),
            v3 = Verb(word = "become", ipa = "[bɪ'kʌm]"),
            type = PartiallyChanging
        ),
        VerbDetails(
            word = "Приходить",
            v1 = Verb(word = "come", ipa = "[kʌm]"),
            v2 = Verb(word = "came", ipa = "[keɪm]"),
            v3 = Verb(word = "come", ipa = "[kʌm]"),
            type = PartiallyChanging
        ),
        VerbDetails(
            word = "Бежать",
            v1 = Verb(word = "run", ipa = "[rʌn]"),
            v2 = Verb(word = "ran", ipa = "[ræn]"),
            v3 = Verb(word = "run", ipa = "[rʌn]"),
            type = PartiallyChanging
        ),
    )

    val fullyChanging = listOf(
        VerbDetails(
            word = "Быть",
            v1 = Verb(word = "be", ipa = "[bi:]"),
            v2 = Verb(word = "was/were", ipa = "[wɔz]/[wɜː]"),
            v3 = Verb(word = "been", ipa = "[bi:n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Идти",
            v1 = Verb(word = "go", ipa = "[gəu]"),
            v2 = Verb(word = "went", ipa = "[went]"),
            v3 = Verb(word = "gone", ipa = "[gɔn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Делать",
            v1 = Verb(word = "do", ipa = "[du:]"),
            v2 = Verb(word = "did", ipa = "[dɪd]"),
            v3 = Verb(word = "done", ipa = "[dʌn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Начинать",
            v1 = Verb(word = "begin", ipa = "[bɪ'gɪn]"),
            v2 = Verb(word = "began", ipa = "[bɪgæn]"),
            v3 = Verb(word = "begun", ipa = "[bɪgʌn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Пить",
            v1 = Verb(word = "drink", ipa = "[drɪŋk]"),
            v2 = Verb(word = "drank", ipa = "[dræŋk]"),
            v3 = Verb(word = "drunk", ipa = "[drʌŋk]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Звенеть",
            v1 = Verb(word = "ring", ipa = "[rɪŋ]"),
            v2 = Verb(word = "rang", ipa = "[ræŋ]"),
            v3 = Verb(word = "rung", ipa = "[rʌŋ]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Петь",
            v1 = Verb(word = "sing", ipa = "[sɪŋ]"),
            v2 = Verb(word = "sang", ipa = "[sæŋ]"),
            v3 = Verb(word = "sung", ipa = "[sʌŋ]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Плавать",
            v1 = Verb(word = "swim", ipa = "[swɪm]"),
            v2 = Verb(word = "swam", ipa = "[swæm]"),
            v3 = Verb(word = "swum", ipa = "[swʌm]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Ломать",
            v1 = Verb(word = "break", ipa = "[breɪk]"),
            v2 = Verb(word = "broke", ipa = "[brəuk]"),
            v3 = Verb(word = "broken", ipa = "[brəuk(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Выбирать",
            v1 = Verb(word = "choose", ipa = "[ʧuːz]"),
            v2 = Verb(word = "chose", ipa = "[tʃəuz]"),
            v3 = Verb(word = "chosen", ipa = "['ʧəuz(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Говорить",
            v1 = Verb(word = "speak", ipa = "[spi:k]"),
            v2 = Verb(word = "spoke", ipa = "[spəuk]"),
            v3 = Verb(word = "spoken", ipa = "[spəuk(e)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Красть",
            v1 = Verb(word = "steal", ipa = "[sti:l]"),
            v2 = Verb(word = "stole", ipa = "[stəul]"),
            v3 = Verb(word = "stolen", ipa = "['stəulən]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Замерзать",
            v1 = Verb(word = "freeze", ipa = "[friːz]"),
            v2 = Verb(word = "froze", ipa = "[frəuz]"),
            v3 = Verb(word = "frozen", ipa = "['frəuz(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Просыпаться",
            v1 = Verb(word = "wake", ipa = "[weɪk]"),
            v2 = Verb(word = "woke", ipa = "[wəuk]"),
            v3 = Verb(word = "woken", ipa = "[wəuk(e)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Водить",
            v1 = Verb(word = "drive", ipa = "[draɪv]"),
            v2 = Verb(word = "drove", ipa = "[drəuv]"),
            v3 = Verb(word = "driven", ipa = "[drɪv(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Ездить верхом",
            v1 = Verb(word = "ride", ipa = "[raɪd]"),
            v2 = Verb(word = "rode", ipa = "[rəud]"),
            v3 = Verb(word = "ridden", ipa = "[rɪd(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Подниматься",
            v1 = Verb(word = "rise", ipa = "[raɪz]"),
            v2 = Verb(word = "rose", ipa = "[rəuz]"),
            v3 = Verb(word = "risen", ipa = "[riz(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Писать",
            v1 = Verb(word = "write", ipa = "[raɪt]"),
            v2 = Verb(word = "wrote", ipa = "[rəut]"),
            v3 = Verb(word = "written", ipa = "[rit(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Забывать",
            v1 = Verb(word = "forget", ipa = "[fəget]"),
            v2 = Verb(word = "forgot", ipa = "[fəgɒt]"),
            v3 = Verb(word = "forgotten", ipa = "[fəgɒt(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Брать, взять",
            v1 = Verb(word = "take", ipa = "[teɪk]"),
            v2 = Verb(word = "took", ipa = "[tuk]"),
            v3 = Verb(word = "taken", ipa = "['teɪk(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Носить (одежду)",
            v1 = Verb(word = "wear", ipa = "[weə]"),
            v2 = Verb(word = "wore", ipa = "[wɔː]"),
            v3 = Verb(word = "worn", ipa = "[wɔːn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Летать",
            v1 = Verb(word = "fly", ipa = "[flaɪ]"),
            v2 = Verb(word = "flew", ipa = "[flu:]"),
            v3 = Verb(word = "flown", ipa = "[fləun]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Знать",
            v1 = Verb(word = "know", ipa = "[nəu]"),
            v2 = Verb(word = "knew", ipa = "[nju:]"),
            v3 = Verb(word = "known", ipa = "[nəun]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Рисовать, тащить",
            v1 = Verb(word = "draw", ipa = "[drɔː]"),
            v2 = Verb(word = "drew", ipa = "[dru:]"),
            v3 = Verb(word = "drawn", ipa = "[drɔːn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Прощать",
            v1 = Verb(word = "forgive", ipa = "[fəgɪv]"),
            v2 = Verb(word = "forgave", ipa = "[fəgeɪv]"),
            v3 = Verb(word = "forgiven", ipa = "[fəgɪv(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Давать",
            v1 = Verb(word = "give", ipa = "[gɪv]"),
            v2 = Verb(word = "gave", ipa = "[geɪv]"),
            v3 = Verb(word = "given", ipa = "[gɪv(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Есть",
            v1 = Verb(word = "eat", ipa = "[i:t]"),
            v2 = Verb(word = "ate", ipa = "[et]"),
            v3 = Verb(word = "eaten", ipa = "[i:t(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Падать",
            v1 = Verb(word = "fall", ipa = "[fɔːl]"),
            v2 = Verb(word = "fell", ipa = "[fel]"),
            v3 = Verb(word = "fallen", ipa = "['fɔːlən]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Лежать",
            v1 = Verb(word = "lie", ipa = "[laɪ]"),
            v2 = Verb(word = "lay", ipa = "[leɪ]"),
            v3 = Verb(word = "lain", ipa = "[leɪn]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Видеть",
            v1 = Verb(word = "see", ipa = "[si:]"),
            v2 = Verb(word = "saw", ipa = "[sɔː]"),
            v3 = Verb(word = "seen", ipa = "[si:n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Кусать",
            v1 = Verb(word = "bite", ipa = "[baɪt]"),
            v2 = Verb(word = "bit", ipa = "[bɪt]"),
            v3 = Verb(word = "bitten", ipa = "['bɪt(ə)n]"),
            type = FullyChanging
        ),
        VerbDetails(
            word = "Показывать",
            v1 = Verb(word = "show", ipa = "[ʃəu]"),
            v2 = Verb(word = "showed", ipa = "[ʃəud]"),
            v3 = Verb(word = "shown", ipa = "[ʃəun]"),
            type = FullyChanging
        )
    )

    val partiallyConsistent = listOf(
        VerbDetails(
            word = "Приносить",
            v1 = Verb(word = "bring", ipa = "[brɪŋ]"),
            v2 = Verb(word = "brought", ipa = "[brɔːt]"),
            v3 = Verb(word = "brought", ipa = "[brɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Покупать",
            v1 = Verb(word = "buy", ipa = "[baɪ]"),
            v2 = Verb(word = "bought", ipa = "[bɔːt]"),
            v3 = Verb(word = "bought", ipa = "[bɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Ловить, хватать, успеть",
            v1 = Verb(word = "catch", ipa = "[kæʧ]"),
            v2 = Verb(word = "caught", ipa = "[kɔːt]"),
            v3 = Verb(word = "caught", ipa = "[kɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Бороться",
            v1 = Verb(word = "fight", ipa = "[faɪt]"),
            v2 = Verb(word = "fought", ipa = "[fɔːt]"),
            v3 = Verb(word = "fought", ipa = "[fɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Учить",
            v1 = Verb(word = "teach", ipa = "[tiːʧ]"),
            v2 = Verb(word = "taught", ipa = "[tɔːt]"),
            v3 = Verb(word = "taught", ipa = "[tɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Думать",
            v1 = Verb(word = "think", ipa = "[θɪŋk]"),
            v2 = Verb(word = "thought", ipa = "[θɔːt]"),
            v3 = Verb(word = "thought", ipa = "[θɔːt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Стрелять",
            v1 = Verb(word = "shoot", ipa = "[ʃuːt]"),
            v2 = Verb(word = "shot", ipa = "[ʃɒt]"),
            v3 = Verb(word = "shot", ipa = "[ʃɒt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Строить",
            v1 = Verb(word = "build", ipa = "[bɪld]"),
            v2 = Verb(word = "built", ipa = "[bɪlt]"),
            v3 = Verb(word = "built", ipa = "[bɪlt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Чувствовать",
            v1 = Verb(word = "feel", ipa = "[fi:l]"),
            v2 = Verb(word = "felt", ipa = "[felt]"),
            v3 = Verb(word = "felt", ipa = "[felt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Содержать, хранить",
            v1 = Verb(word = "keep", ipa = "[ki:p]"),
            v2 = Verb(word = "kept", ipa = "[kept]"),
            v3 = Verb(word = "kept", ipa = "[kept]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Спать",
            v1 = Verb(word = "sleep", ipa = "[sli:p]"),
            v2 = Verb(word = "slept", ipa = "[slept]"),
            v3 = Verb(word = "slept", ipa = "[slept]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Оставлять, покидать",
            v1 = Verb(word = "leave", ipa = "[li:v]"),
            v2 = Verb(word = "left", ipa = "[left]"),
            v3 = Verb(word = "left", ipa = "[left]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Мечтать, дремать",
            v1 = Verb(word = "dream", ipa = "[dri:m]"),
            v2 = Verb(word = "dreamt", ipa = "[dremt]"),
            v3 = Verb(word = "dreamt", ipa = "[dremt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Встречать",
            v1 = Verb(word = "meet", ipa = "[mi:t]"),
            v2 = Verb(word = "met", ipa = "[met]"),
            v3 = Verb(word = "met", ipa = "[met]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Иметь в виду, намереваться",
            v1 = Verb(word = "mean", ipa = "[mi:n]"),
            v2 = Verb(word = "meant", ipa = "[ment]"),
            v3 = Verb(word = "meant", ipa = "[ment]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Вести, руководить",
            v1 = Verb(word = "lead", ipa = "[li:d]"),
            v2 = Verb(word = "led", ipa = "[led]"),
            v3 = Verb(word = "led", ipa = "[led]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Плакать",
            v1 = Verb(word = "weep", ipa = "[wi:p]"),
            v2 = Verb(word = "wept", ipa = "[wept]"),
            v3 = Verb(word = "wept", ipa = "[wept]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Кормить",
            v1 = Verb(word = "feed", ipa = "[fi:d]"),
            v2 = Verb(word = "fed", ipa = "[fed]"),
            v3 = Verb(word = "fed", ipa = "[fed]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Читать",
            v1 = Verb(word = "read", ipa = "[ri:d]"),
            v2 = Verb(word = "read", ipa = "[red]"),
            v3 = Verb(word = "read", ipa = "[red]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Говорить",
            v1 = Verb(word = "say", ipa = "[seɪ]"),
            v2 = Verb(word = "said", ipa = "[sed]"),
            v3 = Verb(word = "said", ipa = "[sed]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Находить",
            v1 = Verb(word = "find", ipa = "[faɪnd]"),
            v2 = Verb(word = "found", ipa = "[faund]"),
            v3 = Verb(word = "found", ipa = "[faund]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Продавать",
            v1 = Verb(word = "sell", ipa = "[sel]"),
            v2 = Verb(word = "sold", ipa = "[səuld]"),
            v3 = Verb(word = "sold", ipa = "[səuld]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Рассказывать",
            v1 = Verb(word = "tell", ipa = "[tel]"),
            v2 = Verb(word = "told", ipa = "[təuld]"),
            v3 = Verb(word = "told", ipa = "[təuld]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Стоять",
            v1 = Verb(word = "stand", ipa = "[stænd]"),
            v2 = Verb(word = "stood", ipa = "[stuːd]"),
            v3 = Verb(word = "stood", ipa = "[stuːd]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Понимать",
            v1 = Verb(word = "understand", ipa = "[ˌʌndə'stænd]"),
            v2 = Verb(word = "understood", ipa = "[ˌʌndə'stud]"),
            v3 = Verb(word = "understood", ipa = "[ˌʌndə'stud]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Получать",
            v1 = Verb(word = "get", ipa = "[get]"),
            v2 = Verb(word = "got", ipa = "[gɒt]"),
            v3 = Verb(word = "got", ipa = "[gɒt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Терять",
            v1 = Verb(word = "lose", ipa = "[lu:z]"),
            v2 = Verb(word = "lost", ipa = "[lɒst]"),
            v3 = Verb(word = "lost", ipa = "[lɒst]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Тратить",
            v1 = Verb(word = "spend", ipa = "[spend]"),
            v2 = Verb(word = "spent", ipa = "[spent]"),
            v3 = Verb(word = "spent", ipa = "[spent]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Иметь",
            v1 = Verb(word = "have", ipa = "[hæv]"),
            v2 = Verb(word = "had", ipa = "[hæd]"),
            v3 = Verb(word = "had", ipa = "[hæd]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Слышать",
            v1 = Verb(word = "hear", ipa = "[hɪə]"),
            v2 = Verb(word = "heard", ipa = "[hɜːd]"),
            v3 = Verb(word = "heard", ipa = "[hɜːd]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Платить",
            v1 = Verb(word = "pay", ipa = "[peɪ]"),
            v2 = Verb(word = "paid", ipa = "[peɪd]"),
            v3 = Verb(word = "paid", ipa = "[peɪd]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Посылать",
            v1 = Verb(word = "send", ipa = "[send]"),
            v2 = Verb(word = "sent", ipa = "[sent]"),
            v3 = Verb(word = "sent", ipa = "[sent]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Сидеть",
            v1 = Verb(word = "sit", ipa = "[sɪt]"),
            v2 = Verb(word = "sat", ipa = "[sæt]"),
            v3 = Verb(word = "sat", ipa = "[sæt]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Выигрывать",
            v1 = Verb(word = "win", ipa = "[wɪn]"),
            v2 = Verb(word = "won", ipa = "[wʌn]"),
            v3 = Verb(word = "won", ipa = "[wʌn]"),
            type = PartiallyConsistent
        ),
        VerbDetails(
            word = "Делать, производить",
            v1 = Verb(word = "make", ipa = "[meɪk]"),
            v2 = Verb(word = "made", ipa = "[meɪd]"),
            v3 = Verb(word = "made", ipa = "[meɪd]"),
            type = PartiallyConsistent
        )
    )

    val allVerbsDetails = unchanging + partiallyChanging + fullyChanging + partiallyConsistent
}