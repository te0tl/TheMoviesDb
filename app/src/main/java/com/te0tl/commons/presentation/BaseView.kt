package com.te0tl.commons.presentation

/**
 * Base class for common methods and attributes
 * to avoid boilerplate code for base activities and fragments.
 */
interface BaseView {

    /**
     * Set to false if you don't want standard setContentView or Inflate...
     */
    val standardViewCreation: Boolean

    val idViewResource: Int

    /**
     * Method for checking arguments/extras,
     * throw exceptions if required and not present
     */
    fun parseArguments() {}

    /**
     * At this point, view has been created and arguments/extras params parsed
     */
    fun onViewAndExtrasReady() {}

}