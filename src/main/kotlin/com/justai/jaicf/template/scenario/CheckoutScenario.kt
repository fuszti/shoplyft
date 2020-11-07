package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.shoppingcart.LocalShoppingCart

object CheckoutScenario : Scenario() {
    const val checkoutState = "/checkout"
    const val confirmState = "/checkout/confirm"
    const val removeState = "/checkout/remove"

    init{
        state(checkoutState) {
            state(confirmState) {
                state("yes") {
                    activators {
                        intent("Yes")
                    }
                    action {
                        reactions.say("Thank you! Please enter your address!")
                    }
                }

                state("no") {
                    activators {
                        intent("No")
                    }
                    action {
                        reactions.say("What do you want to remove?")
                        reactions.go(removeState)
                    }
                }

                state("address") {
                    activators {
                        catchAll()
                    }
                    action {
                        val responseString = "Your items will be delivered to ${request.input}"
                        reactions.say(responseString)
                        reactions.go(MainScenario.byeState)
                    }
                }
            }

            state(removeState) {
                state("remove stuff") {
                    activators {
                        catchAll()
                    }
                    action {
                        LocalShoppingCart.remove(request.clientId.toString(), request.input)
                        reactions.say("Do you want to remove anything else?")
                    }
                }

                state("yes") {
                    activators {
                        intent("yes")
                    }
                    action {
                        reactions.say("What do you want to remove?")
                        reactions.go(removeState)
                    }
                }

                state("nothing to remove") {
                    activators {
                        intent("No")
                    }
                    action {
                        val responseString = "Thank you! Your items are: " +
                                LocalShoppingCart.getAll(request.clientId.toString()) +
                                " Is it correct?"
                        reactions.say(responseString)
                        reactions.go(confirmState)
                    }
                }
            }
        }
    }
}