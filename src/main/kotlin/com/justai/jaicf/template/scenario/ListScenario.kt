package com.justai.jaicf.template.scenario

import com.justai.jaicf.api.BotRequestType
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.shoppingcart.LocalShoppingCart

object ListScenario : Scenario(
    dependencies = listOf(CheckoutScenario)
) {

    const val firstItem = "/list/firstItem"
    const val listItems = "/list"
    private val what_else_strings = listOf(
        "Anything else?",
        "What else do you need?",
        "Any other items you need?",
        "What else you'd like to buy?",
        "What else?"
    )

    init {
        state(listItems) {
            state(firstItem) {
                activators {
                    catchAll()
                }
                action {
                    if (request.type == BotRequestType.QUERY) {
                        LocalShoppingCart.add(request.clientId.toString(), request.input)
                    }
                    reactions.sayRandom(what_else_strings)
                }

                state("/list/yes") {
                    activators {
                        intent("Yes")
                    }

                    action {
                        reactions.sayRandom(what_else_strings)
                    }
                }

                state("nah") {
                    activators {
                        intent("No")
                    }
                    action {
                        val responseString = "Thank you! Your items are: " +
                                LocalShoppingCart.getAll(request.clientId.toString())?.joinToString() +
                                " Is it correct?"
                        reactions.say(responseString)
                        reactions.go(CheckoutScenario.confirmState)
                    }
                }

                fallback {
                    if (request.type == BotRequestType.QUERY) {
                        LocalShoppingCart.add(request.clientId.toString(), request.input)
                    }
                    reactions.sayRandom(what_else_strings)
                }
            }
        }
    }
}
