package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object ListScenario : Scenario() {

    const val firstItem = "/firstItem"
    private val what_else_strings = listOf(
        "Anything else?",
        "What else do you need?",
        "Any other items you need?",
        "What else you'd like to buy?",
        "What else?"
    )

    init {
        state(firstItem) {
            activators {
                catchAll()
            }
            action {
                reactions.sayRandom(what_else_strings)
            }

            state("yep") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.sayRandom(what_else_strings)
                    reactions.go(firstItem)
                }
            }

            state("nah") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Okay. Please, enter your delivery address.")
                    reactions.go(MainScenario.addressState)
                }
            }

            fallback {
                // Save stuff
                reactions.sayRandom(what_else_strings)
            }
        }
    }
}
