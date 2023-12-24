package com.github.toweron2.`go-error-folds`

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GoErrorFolds: AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        println(p0)
    }
}