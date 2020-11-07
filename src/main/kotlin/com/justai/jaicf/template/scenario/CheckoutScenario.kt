package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.shoppingcart.LocalShoppingCart

object CheckoutScenario : Scenario() {
    const val checkoutState = "/checkout"
    const val addressState = "/checkout/address"
    init{
        state(checkoutState) {
            state(addressState) {
                activators {
                    catchAll()
                }
                action {
                    val responseString = "Thank you! Your items are: " +
                            LocalShoppingCart.getAll(request.clientId.toString()) + "\n" +
                            "Your items will be delivered to ${request.input}"
                    reactions.say(responseString)
                    reactions.go(MainScenario.byeState)
                }
            }
        }
    }
}