package com.sergeysav.idleshutdown.config

/**
 * An exception raised in the process of parsing config data.
 * This should be raised when data doesn't fall within the correct bounds.
 *
 * @author sergeys
 *
 * @param reason the reason for this config exception
 */
class ConfigException(reason: String) : Exception(reason)
