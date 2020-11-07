package com.justai.jaicf.template.scenario

import com.justai.jaicf.api.BotRequestType
import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario(
    dependencies = listOf(ListScenario)
) {

    private const val startState = "/start"
    private const val helloState = "/start/hello"
    const val addressState = "/finalize/address"
    var lastMessage = ""

    fun getDidNotUnderstandResponses(question: String): List<String>{
        return listOf(
            "Sorry, I didn't get that...$question",
            "Sorry, I did not understand.$question"
        )
    }

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
                    lastMessage = "Welcome to Shoplyft! Need groceries?"
                    reactions.say(lastMessage)
                }

                state("/start/yes") {
                    activators {
                        intent("Yes")
                    }

                    action {
                        lastMessage = "What do you need?"
                        reactions.say(lastMessage)
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
            reactions.sayRandom(getDidNotUnderstandResponses(lastMessage))
        }
    }
}