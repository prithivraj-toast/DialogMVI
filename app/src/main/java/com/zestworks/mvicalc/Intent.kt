package com.zestworks.mvicalc

sealed class Intent

class InputAModified(val text: String): Intent()
class InputBModified(val text: String): Intent()
object AddClicked: Intent()
object NoIntent: Intent()