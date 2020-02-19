package fr.ft.aviscogl.event;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import fr.ft.aviscogl.action.InsertHeader;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;


public class OnKeystroke extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent actionEvent)
    {
        VirtualFile file = actionEvent.getData(VIRTUAL_FILE);
        Editor editor = actionEvent.getData(LangDataKeys.EDITOR);

        InsertHeader.insert(file, editor);
    }
}
