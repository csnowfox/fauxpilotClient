package org.csnowfox.fauxpilot.client;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;
import com.vladsch.flexmark.util.misc.Utils;
import org.csnowfox.fauxpilot.client.api.OpenAPI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

public class CodeAssistAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        executeAssist(event);
    }

    public void executeAssist(AnActionEvent event) {
        final Editor mEditor = (Editor) event.getData(PlatformDataKeys.EDITOR);
        if (null == mEditor) {
            return;
        }

        int maxTokenSize = 200;
        final Document document = mEditor.getDocument();
        Caret primaryCaret = mEditor.getCaretModel().getPrimaryCaret();
        int currentOffset = primaryCaret.getOffset();
        String promptText = (currentOffset - maxTokenSize) > 0 ? document.getText(new TextRange(currentOffset - maxTokenSize, currentOffset)) : document.getText(new TextRange(0, currentOffset));

        final SelectionModel selectionModel = mEditor.getSelectionModel();
        String selectText = selectionModel.getSelectedText();

        if (!Utils.isBlank(selectText)) {
            showPopupBalloon(selectText, mEditor);
        } else {
            showPopupBalloon(promptText, mEditor);
        }
    }

    /**
     * Text pop-up display
     */
    private void showPopupBalloon(final String prompt, Editor editor) {
        ApplicationManager.getApplication().invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
                String resultContent = OpenAPI.prompt(prompt);
                final JBPopupFactory factory = JBPopupFactory.getInstance();
                factory.createHtmlTextBalloonBuilder(resultContent, (Icon) null, (Color) null, (HyperlinkListener) null).createBalloon().show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });
    }

}
