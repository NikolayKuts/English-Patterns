package com.example.englishpatterns.domain.irregularVerbs

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.EndChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.FullyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyConsistent
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.SecondChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.Unchanging

object IrregularVerbsStorage {

    val unchanging = IrregularVerbsGroup.Unchanging(
        details = listOf(
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
    )

    val secondChanging = IrregularVerbsGroup.PartiallyChanging(
        details = listOf(
            VerbDetails(
                word = "Становиться",
                v1 = Verb(word = "become", ipa = "[bɪ'kʌm]"),
                v2 = Verb(word = "became", ipa = "[bɪ'keɪm]"),
                v3 = Verb(word = "become", ipa = "[bɪ'kʌm]"),
                type = SecondChanging
            ),
            VerbDetails(
                word = "Приходить",
                v1 = Verb(word = "come", ipa = "[kʌm]"),
                v2 = Verb(word = "came", ipa = "[keɪm]"),
                v3 = Verb(word = "come", ipa = "[kʌm]"),
                type = SecondChanging
            ),
            VerbDetails(
                word = "Бежать",
                v1 = Verb(word = "run", ipa = "[rʌn]"),
                v2 = Verb(word = "ran", ipa = "[ræn]"),
                v3 = Verb(word = "run", ipa = "[rʌn]"),
                type = SecondChanging
            ),
        )
    )

    val fullyChanging = IrregularVerbsGroup.FullyChanging(
        first = IrregularVerbsGroup.FullyChanging.SubGroup.First(
            data = listOf(
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
            )
        ),
        second = IrregularVerbsGroup.FullyChanging.SubGroup.Second(
            data = listOf(
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
                )
            )
        ),
        third = IrregularVerbsGroup.FullyChanging.SubGroup.Third(
            data = listOf(
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
                )
            )
        ),
        fourth = IrregularVerbsGroup.FullyChanging.SubGroup.Fourth(
            data = listOf(
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
                )
            )
        ),
        fifth = IrregularVerbsGroup.FullyChanging.SubGroup.Fifth(
            data = listOf(
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
            )
        ),
        sixth = IrregularVerbsGroup.FullyChanging.SubGroup.Sixth(
            data = listOf(
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
            )
        ),
        seventh = IrregularVerbsGroup.FullyChanging.SubGroup.Seventh(
            data = listOf(
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
        )
    )

    val partiallyConsistent = IrregularVerbsGroup.PartiallyConsistent(
        first = IrregularVerbsGroup.PartiallyConsistent.SubGroup.First(
            data = listOf(
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
            )
        ),
        second = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Second(
            data = listOf(
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
                    word = "* Мечтать, дремать",
                    v1 = Verb(word = "dream", ipa = "[dri:m]"),
                    v2 = Verb(word = "dreamt/dreamed", ipa = "[dremt]"),
                    v3 = Verb(word = "dreamt/dreamed", ipa = "[dremt]"),
                    type = PartiallyConsistent
                ),
            )
        ),
        third = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Third(
            data = listOf(
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
            )
        ),
        fourth = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Fourth(
            data = listOf(
                VerbDetails(
                    word = "Находить",
                    v1 = Verb(word = "find", ipa = "[faɪnd]"),
                    v2 = Verb(word = "found", ipa = "[faund]"),
                    v3 = Verb(word = "found", ipa = "[faund]"),
                    type = PartiallyConsistent
                ),
            )
        ),
        fifth = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Fifth(
            data = listOf(
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
            )
        ),
        sixth = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Sixth(
            data = listOf(
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
            )
        ),
        seventh = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Seventh(
            data = listOf(
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
            )
        ),
        eighth = IrregularVerbsGroup.PartiallyConsistent.SubGroup.Eighth(
            data = listOf(
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
        )
    )
    // bleed, blow, burn, deal, dig, forbid, hang, hide, hold, seek, smell, stick, withdraw, arise, awake, bear, beat, bend, bet, bind, breed, broadcast, cling, creep, deal, flee, grind, leap, light, overcome, prove, quit, rid, saw, seek, sew, shrink, sink, slide, slay, spit, split, spoil, spring, sting, stink, swear, swing, tear, thrust, tread, wind, wring, forbear, string,

    val mixed = IrregularVerbsGroup.Mixed(
        unchanging = IrregularVerbsGroup.Mixed.SubGroup.Unchanging(
            data = listOf(
                VerbDetails(
                    word = "Делать ставку",
                    v1 = Verb(word = "bet", ipa = "[bet]"),
                    v2 = Verb(word = "bet", ipa = "[bet]"),
                    v3 = Verb(word = "bet", ipa = "[bet]"),
                    type = Unchanging
                ),
                VerbDetails(
                    word = "Транслировать",
                    v1 = Verb(word = "broadcast", ipa = "[ˈbrɔːdkɑːst]"),
                    v2 = Verb(word = "broadcast", ipa = "[ˈbrɔːdkɑːst]"),
                    v3 = Verb(word = "broadcast", ipa = "[ˈbrɔːdkɑːst]"),
                    type = Unchanging
                ),
                VerbDetails(
                    word = "Бросать",
                    v1 = Verb(word = "quit", ipa = "[kwɪt]"),
                    v2 = Verb(word = "quit", ipa = "[kwɪt]"),
                    v3 = Verb(word = "quit", ipa = "[kwɪt]"),
                    type = Unchanging
                ),
                VerbDetails(
                    word = "Избавляться",
                    v1 = Verb(word = "rid", ipa = "[rɪd]"),
                    v2 = Verb(word = "rid", ipa = "[rɪd]"),
                    v3 = Verb(word = "rid", ipa = "[rɪd]"),
                    type = Unchanging
                ),
                VerbDetails(
                    word = "Разделять",
                    v1 = Verb(word = "split", ipa = "[splɪt]"),
                    v2 = Verb(word = "split", ipa = "[splɪt]"),
                    v3 = Verb(word = "split", ipa = "[splɪt]"),
                    type = Unchanging
                ),
//                VerbDetails(
//                    word = "Резко толкнуть, вонзить, всадить",
//                    v1 = Verb(word = "thrust", ipa = "[θrʌst]"),
//                    v2 = Verb(word = "thrust", ipa = "[θrʌst]"),
//                    v3 = Verb(word = "thrust", ipa = "[θrʌst]"),
//                    type = Unchanging
//                ),
            ),
        ),
        secondChanging = IrregularVerbsGroup.Mixed.SubGroup.SecondChanging(
            data = listOf(
                VerbDetails(
                    word = "Преодолевать",
                    v1 = Verb(word = "overcome", ipa = "[ˌəʊvəˈkʌm]"),
                    v2 = Verb(word = "overcame", ipa = "[ˌəʊvəˈkeɪm]"),
                    v3 = Verb(word = "overcome", ipa = "[ˌəʊvəˈkʌm]"),
                    type = SecondChanging
                ),
            )
        ),
        fullyChanging = IrregularVerbsGroup.Mixed.SubGroup.FullyChanging(
            data = listOf(
                VerbDetails(
                    word = "Дуть",
                    v1 = Verb(word = "blow", ipa = "[bloʊ]"),
                    v2 = Verb(word = "blew", ipa = "[bluː]"),
                    v3 = Verb(word = "blown", ipa = "[bloʊn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Бросать, кинуть",
                    v1 = Verb(word = "throw", ipa = "[θrəʊ]"),
                    v2 = Verb(word = "threw", ipa = "[θruː]"),
                    v3 = Verb(word = "thrown", ipa = "[θrəʊn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Изымать",
                    v1 = Verb(word = "withdraw", ipa = "[wɪðˈdrɔː]"),
                    v2 = Verb(word = "withdrew", ipa = "[wɪðˈdruː]"),
                    v3 = Verb(word = "withdrawn", ipa = "[wɪðˈdrɔːn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Запрещать",
                    v1 = Verb(word = "forbid", ipa = "[fərˈbɪd]"),
                    v2 = Verb(word = "forbade", ipa = "[fərˈbeɪd]"),
                    v3 = Verb(word = "forbidden", ipa = "[fərˈbɪdn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Прятать",
                    v1 = Verb(word = "hide", ipa = "[haɪd]"),
                    v2 = Verb(word = "hid", ipa = "[hɪd]"),
                    v3 = Verb(word = "hidden", ipa = "[ˈhɪdn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Возникать",
                    v1 = Verb(word = "arise", ipa = "[əˈraɪz]"),
                    v2 = Verb(word = "arose", ipa = "[əˈroʊz]"),
                    v3 = Verb(word = "arisen", ipa = "[əˈrɪzn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Пробуждаться",
                    v1 = Verb(word = "awake", ipa = "[əˈweɪk]"),
                    v2 = Verb(word = "awoke", ipa = "[əˈwoʊk]"),
                    v3 = Verb(word = "awoken", ipa = "[əˈwoʊkən]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Доказывать",
                    v1 = Verb(word = "prove", ipa = "[pruːv]"),
                    v2 = Verb(word = "proved", ipa = "[pruːvd]"),
                    v3 = Verb(word = "proven", ipa = "[ˈpruːvən]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "* Пилить",
                    v1 = Verb(word = "saw", ipa = "[sɔː]"),
                    v2 = Verb(word = "sawed", ipa = "[sɔːd]"),
                    v3 = Verb(word = "sawn/sawed", ipa = "[sɔːn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "* Шить",
                    v1 = Verb(word = "sew", ipa = "[soʊ]"),
                    v2 = Verb(word = "sewed", ipa = "[soʊd]"),
                    v3 = Verb(word = "sewn/sewed", ipa = "[soʊn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Сжиматься",
                    v1 = Verb(word = "shrink", ipa = "[ʃrɪŋk]"),
                    v2 = Verb(word = "shrank", ipa = "[ʃræŋk]"),
                    v3 = Verb(word = "shrunk", ipa = "[ʃrʌŋk]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Тонуть",
                    v1 = Verb(word = "sink", ipa = "[sɪŋk]"),
                    v2 = Verb(word = "sank", ipa = "[sæŋk]"),
                    v3 = Verb(word = "sunk", ipa = "[sʌŋk]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Прыгать (более живо)",
                    v1 = Verb(word = "spring", ipa = "[sprɪŋ]"),
                    v2 = Verb(word = "sprang", ipa = "[spræŋ]"),
                    v3 = Verb(word = "sprung", ipa = "[sprʌŋ]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Вонять",
                    v1 = Verb(word = "stink", ipa = "[stɪŋk]"),
                    v2 = Verb(word = "stank", ipa = "[stæŋk]"),
                    v3 = Verb(word = "stunk", ipa = "[stʌŋk]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Выносить, вынашивать",
                    v1 = Verb(word = "bear", ipa = "[beə(r)]"),
                    v2 = Verb(word = "bore", ipa = "[bɔː(r)]"),
                    v3 = Verb(word = "borne", ipa = "[bɔːn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Клясться",
                    v1 = Verb(word = "swear", ipa = "[sweə(r)]"),
                    v2 = Verb(word = "swore", ipa = "[swɔː(r)]"),
                    v3 = Verb(word = "sworn", ipa = "[swɔːn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "Рвать",
                    v1 = Verb(word = "tear", ipa = "[teə(r)]"),
                    v2 = Verb(word = "tore", ipa = "[tɔː(r)]"),
                    v3 = Verb(word = "torn", ipa = "[tɔːn]"),
                    type = FullyChanging
                ),
                VerbDetails(
                    word = "* Ступать, наступать",
                    v1 = Verb(word = "tread", ipa = "[tred]"),
                    v2 = Verb(word = "trod", ipa = "[trɒd]"),
                    v3 = Verb(word = "trodden/trod", ipa = "[ˈtrɒdn]"),
                    type = FullyChanging
                ),
//                VerbDetails(
//                    word = "Воздерживаться",
//                    v1 = Verb(word = "forbear", ipa = "[fɔːˈbeə(r)]"),
//                    v2 = Verb(word = "forbore", ipa = "[fɔːˈbɔː(r)]"),
//                    v3 = Verb(word = "forborne", ipa = "[fɔːˈbɔːn]"),
//                    type = FullyChanging
//                ),
//                VerbDetails(
//                    word = "Убивать (dragon)",
//                    v1 = Verb(word = "slay", ipa = "[sleɪ]"),
//                    v2 = Verb(word = "slew", ipa = "[sluː]"),
//                    v3 = Verb(word = "slain", ipa = "[sleɪn]"),
//                    type = FullyChanging
//                ),
            )
        ),
        partiallyConsistent = IrregularVerbsGroup.Mixed.SubGroup.PartiallyConsistent(
            data = listOf(
                VerbDetails(
                    word = "Приклеивать",
                    v1 = Verb(word = "stick", ipa = "[stɪk]"),
                    v2 = Verb(word = "stuck", ipa = "[stʌk]"),
                    v3 = Verb(word = "stuck", ipa = "[stʌk]"),
                    type = PartiallyConsistent
                ),
//                VerbDetails(
//                    word = "Цепляться, держаться, льнуть, прилипать",
//                    v1 = Verb(word = "cling", ipa = "[klɪŋ]"),
//                    v2 = Verb(word = "clung", ipa = "[klʌŋ]"),
//                    v3 = Verb(word = "clung", ipa = "[klʌŋ]"),
//                    type = PartiallyConsistent
//                ),
//                VerbDetails(
//                    word = "Жалить",
//                    v1 = Verb(word = "sting", ipa = "[stɪŋ]"),
//                    v2 = Verb(word = "stung", ipa = "[stʌŋ]"),
//                    v3 = Verb(word = "stung", ipa = "[stʌŋ]"),
//                    type = PartiallyConsistent
//                ),
                VerbDetails(
                    word = "Качаться",
                    v1 = Verb(word = "swing", ipa = "[swɪŋ]"),
                    v2 = Verb(word = "swung", ipa = "[swʌŋ]"),
                    v3 = Verb(word = "swung", ipa = "[swʌŋ]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Выжимать, скручивать",
                    v1 = Verb(word = "wring", ipa = "[rɪŋ]"),
                    v2 = Verb(word = "wrung", ipa = "[rʌŋ]"),
                    v3 = Verb(word = "wrung", ipa = "[rʌŋ]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Натягивать",
                    v1 = Verb(word = "string", ipa = "[strɪŋ]"),
                    v2 = Verb(word = "strung", ipa = "[strʌŋ]"),
                    v3 = Verb(word = "strung", ipa = "[strʌŋ]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Вешать",
                    v1 = Verb(word = "hang", ipa = "[hæŋ]"),
                    v2 = Verb(word = "hung", ipa = "[hʌŋ]"),
                    v3 = Verb(word = "hung", ipa = "[hʌŋ]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Копать",
                    v1 = Verb(word = "dig", ipa = "[dɪɡ]"),
                    v2 = Verb(word = "dug", ipa = "[dʌɡ]"),
                    v3 = Verb(word = "dug", ipa = "[dʌɡ]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Убегать",
                    v1 = Verb(word = "flee", ipa = "[fliː]"),
                    v2 = Verb(word = "fled", ipa = "[fled]"),
                    v3 = Verb(word = "fled", ipa = "[fled]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Иметь дело",
                    v1 = Verb(word = "deal", ipa = "[diːl]"),
                    v2 = Verb(word = "dealt", ipa = "[delt]"),
                    v3 = Verb(word = "dealt", ipa = "[delt]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Красться",
                    v1 = Verb(word = "creep", ipa = "[kriːp]"),
                    v2 = Verb(word = "crept", ipa = "[krept]"),
                    v3 = Verb(word = "crept", ipa = "[krept]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Сгибать",
                    v1 = Verb(word = "bend", ipa = "[bend]"),
                    v2 = Verb(word = "bent", ipa = "[bent]"),
                    v3 = Verb(word = "bent", ipa = "[bent]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Держать",
                    v1 = Verb(word = "hold", ipa = "[hoʊld]"),
                    v2 = Verb(word = "held", ipa = "[held]"),
                    v3 = Verb(word = "held", ipa = "[held]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Искать",
                    v1 = Verb(word = "seek", ipa = "[siːk]"),
                    v2 = Verb(word = "sought", ipa = "[sɔːt]"),
                    v3 = Verb(word = "sought", ipa = "[sɔːt]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Связывать",
                    v1 = Verb(word = "bind", ipa = "[baɪnd]"),
                    v2 = Verb(word = "bound", ipa = "[baʊnd]"),
                    v3 = Verb(word = "bound", ipa = "[baʊnd]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Заводить (watch), наматывать",
                    v1 = Verb(word = "wind", ipa = "[waɪnd]"),
                    v2 = Verb(word = "wound", ipa = "[waʊnd]"),
                    v3 = Verb(word = "wound", ipa = "[waʊnd]"),
                    type = PartiallyConsistent
                ),
//                VerbDetails(
//                    word = "Молоть",
//                    v1 = Verb(word = "grind", ipa = "[ɡraɪnd]"),
//                    v2 = Verb(word = "ground", ipa = "[ɡraʊnd]"),
//                    v3 = Verb(word = "ground", ipa = "[ɡraʊnd]"),
//                    type = PartiallyConsistent
//                ),
                VerbDetails(
                    word = "* Гореть",
                    v1 = Verb(word = "burn", ipa = "[bɜːn]"),
                    v2 = Verb(word = "burnt/burned", ipa = "[bɜːnt]"),
                    v3 = Verb(word = "burnt/burned", ipa = "[bɜːnt]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "* Пахнуть",
                    v1 = Verb(word = "smell", ipa = "[smel]"),
                    v2 = Verb(word = "smelt/smelled", ipa = "[smelt]"),
                    v3 = Verb(word = "smelt/smelled", ipa = "[smelt]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "* Портить",
                    v1 = Verb(word = "spoil", ipa = "[spɔɪl]"),
                    v2 = Verb(word = "spoilt/spoiled", ipa = "[spɔɪlt]"),
                    v3 = Verb(word = "spoilt/spoiled", ipa = "[spɔɪlt]"),
                    type = PartiallyConsistent
                ),
//                VerbDetails(
//                    word = "* Скакнуть, совершить большой прыжок",
//                    v1 = Verb(word = "leap", ipa = "[liːp]"),
//                    v2 = Verb(word = "leapt/leaped", ipa = "[lept]"),
//                    v3 = Verb(word = "leapt/leaped", ipa = "[lept]"),
//                    type = PartiallyConsistent
//                ),
                VerbDetails(
                    word = "Освещать",
                    v1 = Verb(word = "light", ipa = "[laɪt]"),
                    v2 = Verb(word = "lit", ipa = "[lɪt]"),
                    v3 = Verb(word = "lit", ipa = "[lɪt]"),
                    type = PartiallyConsistent
                ),
                VerbDetails(
                    word = "Скользить",
                    v1 = Verb(word = "slide", ipa = "[slaɪd]"),
                    v2 = Verb(word = "slid", ipa = "[slɪd]"),
                    v3 = Verb(word = "slid", ipa = "[slɪd]"),
                    type = PartiallyConsistent
                ),
//                VerbDetails(
//                    word = "Плевать",
//                    v1 = Verb(word = "spit", ipa = "[spɪt]"),
//                    v2 = Verb(word = "spat", ipa = "[spæt]"),
//                    v3 = Verb(word = "spat", ipa = "[spæt]"),
//                    type = PartiallyConsistent
//                ),
//                VerbDetails(
//                    word = "Разводить (dogs), выводить, размножаться",
//                    v1 = Verb(word = "breed", ipa = "[briːd]"),
//                    v2 = Verb(word = "bred", ipa = "[bred]"),
//                    v3 = Verb(word = "bred", ipa = "[bred]"),
//                    type = PartiallyConsistent
//                ),

//                VerbDetails(
//                    word = "Кровоточить",
//                    v1 = Verb(word = "bleed", ipa = "[bliːd]"),
//                    v2 = Verb(word = "bled", ipa = "[bled]"),
//                    v3 = Verb(word = "bled", ipa = "[bled]"),
//                    type = PartiallyConsistent
//                ),
            )
        ), endChanging = IrregularVerbsGroup.Mixed.SubGroup.EndChanging(
            data = listOf(
                VerbDetails(
                    word = "Бить (удар), биться (heart)",
                    v1 = Verb(word = "beat", ipa = "[biːt]"),
                    v2 = Verb(word = "beat", ipa = "[biːt]"),
                    v3 = Verb(word = "beaten", ipa = "[ˈbiːtn]"),
                    type = EndChanging
                ),
            )
        )
    )

    val irregularVerbsGroups =
        unchanging + secondChanging + fullyChanging + partiallyConsistent + mixed

}