package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.ShoppingCart.LocalShoppingCart

object ListScenario : Scenario() {

    const val firstItem = "/firstItem"

    init {
        state(firstItem) {
            activators {
                catchAll()
            }
            action {
                LocalShoppingCart.add(request.clientId.toString(), request.input)
                reactions.say("Anything else?")
            }

            state("yep") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.say("What else do you need?")
                    reactions.go(firstItem)
                }
            }

            state("nah") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Okay")
                    reactions.go(MainScenario.byeState)
                }
            }

            fallback {
                // Save stuff
                reactions.sayRandom("Anything else?", "What else?")
            }
        }
    }
}
