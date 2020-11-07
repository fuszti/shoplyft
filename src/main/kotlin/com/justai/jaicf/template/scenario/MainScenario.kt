package com.justai.jaicf.template.scenario

import com.justai.jaicf.api.BotRequestType
import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario(
    dependencies = listOf(ListScenario)
) {

    private const val startState = "/start"
    private const val helloState = "/start/hello"
    const val addressState = "/finalize/address"

    init {

        state(startState){
            activators {
                regex("/start")
                intent("Groceries")
            }
            action {
                reactions.say("Hello and welcome to Shoplyft!")
            }

            state(helloState) {
                activators {
                    intent("Hello")
                }
                action {
                    reactions.say("Welcome to Shoplyft! Need groceries?")
                }

                state("/start/yes") {
                    activators {
                        intent("Yes")
                    }

                    action {
                        reactions.say("What do you need?")
                        reactions.go(ListScenario.listItems)
                    }
                }

                state("no") {
                    activators {
                        intent("No")
                    }

                    action {
                        reactions.say("Sorry to hear that!")
                        reactions.go(CheckoutScenario.byeState)
                    }
                }
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