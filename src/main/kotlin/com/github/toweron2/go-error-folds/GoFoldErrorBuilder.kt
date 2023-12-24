package com.github.toweron2.`go-error-folds`

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import java.util.regex.Pattern

class GoFoldErrorBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val lx = mutableListOf<FoldingDescriptor>()
        val txt = document.getText()

        val pattern = Pattern.compile(
            "[\\r\\n]\\s+(var |\\S+, )*(?<var1>\\S+)\\s+:?=(?<stm>[^\\r\\n]+)[\\r\\n]+"
                    + "[^\\S\\r\\n]+if (?<when>[^\\r\\n]+) [{]"
        )
        val matcher = pattern.matcher(txt)
        while (matcher.find()) {
            val var1 = matcher.group("var1")
            var when1 = matcher.group("when")
            val ss = matcher.end("stm")
            var ee = matcher.end("when") + 1
            if (when1.equals(var1 + " == nil")) {
                when1 = " empty "
            } else if (when1.equals(var1 + " != nil")) {
                when1 = " catch "
            } else if (when1.endsWith("(" + var1 + ")")
                || when1.startsWith(var1) || when1.startsWith("!" + var1)
            ) {
                when1 = " when "
                ee = matcher.start("when") - 1
            } else {
                continue
            }
            lx.add(FoldingDescriptor(root.node, TextRange(ss, ee), null, when1))
        }
        return lx.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String? {
        return " ... "
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return true
    }

}