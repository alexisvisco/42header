package fr.ft.aviscogl.action;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import fr.ft.aviscogl.Header;
import fr.ft.aviscogl.Util;


public class UpdateHeaderEditLine
{
    public static void update(VirtualFile file)
    {
        if (file == null) return;
        if (file.isDirectory()) return;
        if (!Util.hasHeader(file)) return;


        System.out.println("update line event ");
        Document doc = FileDocumentManager.getInstance().getDocument(file);
        if (doc == null) return;

        boolean isMakefile = file.getName().equalsIgnoreCase("Makefile");

        System.out.println(Header.buildUpdateLineHeader(isMakefile));
        System.out.println(Util.getActiveProject());

        Header.buildUpdateLineHeader(isMakefile).ifPresent(line -> {
            Util.getActiveProject().ifPresent(project -> {
                int offset = Header.getOffsetUpdatedLine(isMakefile);

                // if getOffsetUpdatedLine return -1 this mean that there is no 'edit line'.
                if (offset != -1) {
                    Runnable updateHeader = () -> doc.replaceString(offset, offset + line.length(), line);
                    if (doc.getModificationStamp() > 0) {
                        WriteCommandAction.runWriteCommandAction(project, updateHeader);
                    }
                }
            });
        });


    }
}
