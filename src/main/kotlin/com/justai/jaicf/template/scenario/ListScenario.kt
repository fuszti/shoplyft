package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object ListScenario : Scenario() {
    object states {
        val firstItem = "firstItem"
    }

    init {
        state(states.firstItem) {
            activators {
                regex(".*")
            }
            action {
                reactions.run {
                    sayRandom(
                            "Anything else?"
                    )
                }
            }

            state("yep") {
                activators {
                    intent("YesIntent")
                }

                action {
                    reactions.run {
                        say("What do you need?")
                        go(states.firstItem)
                    }
                }
            }

            state("nah") {
                activators {
                    intent("NoIntent")
                }
                action {
                    reactions.run {
                        say("Okay")
                        go(MainScenario.states.byeState)
                    }
                }
            }

            fallback {
                reactions.run {
                    // Save that stuff
                    sayRandom("Anything else?", "What else?")
                }
            }
        }
    }
}
