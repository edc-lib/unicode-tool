package com.jin4074.unicodetool

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import org.apache.commons.text.StringEscapeUtils

abstract class SelectConvertAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        val project = event.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd
        val selectedText = document.getText(TextRange.from(start, end - start))
        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(start, end, convertString(selectedText)!!)
        }
    }

    abstract fun convertString(selectText: String?): String?
}

class SelectEscapeUnicodeAction : SelectConvertAction() {
    override fun convertString(selectText: String?): String? {
        return StringEscapeUtils.escapeJava(selectText)
    }
}

class SelectUnescapeUnicodeAction : SelectConvertAction() {
    override fun convertString(selectText: String?): String? {
        return StringEscapeUtils.unescapeJava(selectText)
    }
}