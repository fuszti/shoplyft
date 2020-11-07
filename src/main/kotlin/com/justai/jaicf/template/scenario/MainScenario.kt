package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario(
    dependencies = listOf(ListScenario)
) {

    private const val startState = "/start"
    const val byeState = "/bye"

    init {

        state(startState) {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.say("Hello and welcome to Shoplyft! Need bread?")
            }

            state("yes") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.say("What do you need?")
                    reactions.go(ListScenario.firstItem)
                }
            }

            state("no") {
                activators {
                    intent("No")
                }

                action {
                    reactions.say("Sorry to hear that!")
                    reactions.go(byeState)
                }
            }
        }



        state(byeState) {
            activators {
                intent("Bye")
            }

            action {
                val thanksForShopping = "Thanks for shopping with Shoplyft! "
                reactions.sayRandom(
                    thanksForShopping + "See you soon!",
                    thanksForShopping + "Bye-bye!"
                )
            }
        }

        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that...",
                "Sorry, could you repeat please?"
            )
        }
    }
}