package com.github.toweron2.`go-error-folds`

import com.intellij.lang.ASTNode;

import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.Nullable;
import java.util.regex.Pattern

class GoFoldCaseDefaultBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val lx = mutableListOf<FoldingDescriptor>()
        val txt = document.text
        run {
            val pattern = Pattern.compile(
                "[\\r\\n]\\s+if [^\\r\\n]+ [{]" +
                        "(?<s1>\\s+)\\S[^\\r\\n]*((?<s3>\\s+)(continue|break))?" +
                        "(?<s2>\\s+)[}](?! else )"
            )
            val matcher = pattern.matcher(txt)
            while (matcher.find()) {
                val a = matcher.group(0)
                if (a.length > 80) {
                    continue
                }
                val g = FoldingGroup.newGroup("")
                for (i in 1..3) {
                    val sss = matcher.start("s$i")
                    val eee = matcher.end("s$i")
                    if (sss >= 0 && eee >= 0) {
                        lx.add(FoldingDescriptor(root.node, TextRange(sss, eee), g, " "))
                    }
                }
            }
        }
        run {
            val pattern = Pattern.compile(
                "[\\r\\n]\\s+" +
                        "(?:case|default)[^\\r\\n]*:" +
                        "(?<s1>\\s+)\\S[^\\r\\n]*((?<s2>\\s+)(continue|break))?" +
                        "(?=\\s+(?:case|default|[}]))"
            )
            val matcher = pattern.matcher(txt)
            while (matcher.find()) {
                val a = matcher.group(0)
                if (a.length > 80) {
                    continue
                }
                val g = FoldingGroup.newGroup("")
                for (i in 1..2) {
                    val sss = matcher.start("s$i")
                    val eee = matcher.end("s$i")
                    if (sss >= 0 && eee >= 0) {
                        lx.add(FoldingDescriptor(root.node, TextRange(sss, eee), g, " "))
                    }
                }
            }
        }
        run {
            val pattern = Pattern.compile(
                "[\\r\\n]\\s+" +
                        "(var |\\S+, )*(?<var1>\\S+)(?<stm>[^\\r\\n]+)[\\r\\n]+" +
                        "[^\\S\\r\\n]+" +
                        "defer (?<when>[^\\r\\n]+)"
            )
            val matcher = pattern.matcher(txt)
            while (matcher.find()) {
                val ss = matcher.end("stm")
                val ee = matcher.start("when") - 1
                lx.add(FoldingDescriptor(root.node, TextRange(ss, ee), null, " defer "))
            }
        }
        return lx.toTypedArray()
    }

    @Nullable
    override fun getPlaceholderText(node: ASTNode): String {
        return " ... "
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return true
    }
}