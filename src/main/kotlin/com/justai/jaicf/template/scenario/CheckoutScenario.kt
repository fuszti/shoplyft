package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.shoppingcart.LocalShoppingCart

object CheckoutScenario : Scenario() {
    const val checkoutState = "/checkout"
    const val confirmState = "/checkout/confirm"
    const val addressState = "/checkout/confirm/address"
    const val removeState = "/checkout/remove"
    const val byeState = "/bye"

    init{
        state(checkoutState) {
            state(confirmState) {
                state("yes") {
                    activators {
                        intent("Yes")
                    }
                    action {
                        MainScenario.lastMessage = "Thank you! Please enter your address!"
                        reactions.say(MainScenario.lastMessage)
                        reactions.go(addressState)
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

                state(addressState) {
                    state("get address") {
                        activators {
                            catchAll()
                        }
                        action {
                            val responseString = "Your items will be delivered to ${request.input}. Goodbye!"
                            reactions.say(responseString)
                            reactions.go(byeState)
                        }
                    }
                }
            }

            state(removeState) {
                state("yes") {
                    activators {
                        intent("Yes")
                    }
                    action {
                        reactions.say("What do you want to remove?")
                    }
                }

                state("nothing to remove") {
                    activators {
                        intent("No")
                    }
                    action {
                        val responseString = "Thank you! Your items are: " +
                                LocalShoppingCart.getAll(request.clientId.toString())?.joinToString() +
                                " Is it correct?"
                        reactions.say(responseString)
                        reactions.go(confirmState)
                    }
                }

                state("remove stuff") {
                    activators {
                        catchAll()
                    }
                    action {
                        val cartList = LocalShoppingCart.getAll(request.clientId.toString())
                        if(!cartList.isNullOrEmpty() && cartList.contains(request.input)){
                            LocalShoppingCart.remove(request.clientId.toString(), request.input)
                            reactions.say(request.input + " removed from shopping cart. What else do you want to remove?")
                        }
                        else{
                            reactions.say("Shopping cart does not contain " + request.input +
                                    ". Do you want to remove something else?")
                        }
                    }
                }
            }
        }
        state(byeState) {
            state("say bye") {
                activators {
                    intent("Bye")
                }

                action {
                    val thanksForShopping = "Thanks for shopping with Shoplyft! "
                    reactions.sayRandom(
                        thanksForShopping + "See you soon!",
                        thanksForShopping + "Bye-bye!"
                    )
                }
            }
        }
    }
}