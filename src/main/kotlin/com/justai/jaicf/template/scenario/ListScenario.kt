package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object ListScenario : Scenario() {

    const val firstItem = "/firstItem"

    init {
        state(firstItem) {
            activators {
                catchAll()
            }
            action {
                reactions.say("Anything else? In List")
            }

            state("yep") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.say("What do you need? In List")
                    reactions.go(firstItem)
                }
            }

            state("nah") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Okay In List")
                    reactions.go(MainScenario.byeState)
                }
            }

            fallback {
                // Save stuff
                reactions.sayRandom("Anything else?", "What else? In List")
            }
        }
        fallback {
            reactions.say("In LIST top level fallback")
        }
    }
}
