package com.github.toweron2.`go-error-folds`

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.DumbAware


class GoErrorCollapse : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(CommonDataKeys.EDITOR)

        if (editor !is EditorEx) {
            return
        }
        val foldingModel = (editor as EditorEx?)!!.foldingModel
        // val foldingModel = editor.foldingModel

        // 获取当前节点，可以根据实际情况传入
        val root = e.getData(CommonDataKeys.PSI_FILE)?.navigationElement ?: return

        // 调用折叠方法
        val foldingDescriptors = GoFoldErrorBuilder().buildFoldRegions(root, editor.document, true)
        val foldingDescriptors2 = GoFoldCaseDefaultBuilder().buildFoldRegions(root, editor.document, true)

        // 清除现有的折叠区域
        foldingModel.runBatchFoldingOperation {
            foldingModel.clearFoldRegions()
        }

        // 在这里执行折叠操作，你可能需要根据自己的需求调用不同的 API
        foldingModel.runBatchFoldingOperation {
            for (descriptor in foldingDescriptors) {
                println(descriptor.range.startOffset)
                println(descriptor.range.endOffset)
                println(descriptor.placeholderText!!)

                foldingModel.addFoldRegion(
                    descriptor.range.startOffset, descriptor.range.endOffset, descriptor.placeholderText!!
                )?.isExpanded = false
            }

            for (descriptor in foldingDescriptors2) {
                println(descriptor.range.startOffset)
                println(descriptor.range.endOffset)
                println(descriptor.placeholderText!!)

                foldingModel.addFoldRegion(
                    descriptor.range.startOffset, descriptor.range.endOffset, descriptor.placeholderText!!
                )?.isExpanded = false
            }
        }
        // for (foldRegion in foldingModel.allFoldRegions) {
        //    foldingModel.runBatchFoldingOperation { foldRegion.setExpanded(false) }
        // }
    }
}