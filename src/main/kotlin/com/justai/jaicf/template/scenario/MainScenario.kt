package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario() {

    object states {
        val startState = "start"
        val byeState = "bye"
    }

    init {

        state(states.startState) {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
                    sayRandom(
                        "Hello and welcome to Shoplyft! Need bread?"
                    )
                }
            }

            state("yes") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.run {
                        sayRandom("What do you need?")
                        go(ListScenario.states.firstItem)
                    }
                }
            }

            state("no") {
                activators {
                    intent("No")
                }

                action {
                    reactions.run {
                        say("Sorry to hear that!")
                        go(states.byeState)
                    }
                }
            }
        }



        state(states.byeState) {
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