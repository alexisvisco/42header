package fr.ft.aviscogl.action;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import fr.ft.aviscogl.Header;
import fr.ft.aviscogl.Util;

import java.util.Optional;
import java.util.stream.Stream;

public class UpdateAllHeaders
{
    public static void update() {
        Optional<Project> activeProject = Util.getActiveProject();
        activeProject.ifPresent(project -> Stream.of(FilenameIndex.getAllFilenames(project)).forEach(filename -> {
            FilenameIndex.getVirtualFilesByName(
                    project,
                    filename,
                    GlobalSearchScope.projectScope(project)
            ).forEach(file -> {
                if (file.isDirectory()) return;
                if (Util.hasHeader(file)) {
                    String header = Header.buildHeader(file.getName(), file.getName().equalsIgnoreCase("Makefile"));

                    Document doc = FileDocumentManager.getInstance().getDocument(file);
                    if (doc == null) return;

                    Runnable updateHeader = () -> doc.replaceString(0, header.length(), header);
                    WriteCommandAction.runWriteCommandAction(
                            project,
                            updateHeader
                    );
                }
            });
        }));
    }
}
