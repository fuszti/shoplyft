package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object ListScenario : Scenario() {
    object states {
        val firstItem = "/firstItem"
    }

    init {
        state(states.firstItem) {
            activators {
                catchAll()
            }
            action {
                reactions.run {
                    sayRandom(
                            "Anything else? In List"
                    )
                }
            }

            state("yep") {
                activators {
                    intent("Yes")
                }

                action {
                    reactions.run {
                        say("What do you need? In List")
                        go(states.firstItem)
                    }
                }
            }

            state("nah") {
                activators {
                    intent("No")
                }
                action {
                    reactions.run {
                        say("Okay In List")
                        go(MainScenario.states.byeState)
                    }
                }
            }

            fallback {
                reactions.run {
                    // Save that stuff
                    sayRandom("Anything else?", "What else? In List")
                }
            }
        }
        fallback {
            reactions.sayRandom(
                "In LIST top level fallback"
            )
        }
    }
}
