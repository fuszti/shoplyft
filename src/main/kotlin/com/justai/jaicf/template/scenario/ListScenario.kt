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
                        reactions.go(CheckoutScenario.checkoutState)
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
