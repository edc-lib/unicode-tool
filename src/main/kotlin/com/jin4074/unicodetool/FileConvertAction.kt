package com.jin4074.unicodetool

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import org.apache.commons.text.StringEscapeUtils

abstract class FileConvertAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        val project = event.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document
        for (i in 0 until document.lineCount) {
            val start = document.getLineStartOffset(i)
            val end = document.getLineEndOffset(i)
            val lineText = document.getText(TextRange.from(start, end - start))
            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(start, end, convertString(lineText)!!)
            }
        }
    }

    abstract fun convertString(lineText: String?): String?
}

class FileEscapeUnicodeAction : FileConvertAction() {
    override fun convertString(lineText: String?): String? {
        return StringEscapeUtils.escapeJava(StringEscapeUtils.unescapeJava(lineText))
    }
}

class FileUnescapeUnicodeAction : FileConvertAction() {
    override fun convertString(lineText: String?): String? {
        return StringEscapeUtils.unescapeJava(lineText)
    }
}