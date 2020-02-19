package fr.ft.aviscogl.action;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import fr.ft.aviscogl.Header;
import fr.ft.aviscogl.Util;

public class InsertHeader
{
    public static void insert(VirtualFile file, Editor editor) {
        if (file == null) return;
        if (file.isDirectory()) return;
        if (editor == null) return;

        String filename = file.getName();
        boolean isMakefile = filename.equalsIgnoreCase("Makefile");

        String header = Header.buildHeader(filename, isMakefile);

        if (!Util.hasHeader(file)) {
            Util.getActiveProject().ifPresent(project -> {
                Runnable runnable = () -> editor.getDocument().insertString(0, header);
                WriteCommandAction.runWriteCommandAction(project, runnable);
            });
        }
    }
}
