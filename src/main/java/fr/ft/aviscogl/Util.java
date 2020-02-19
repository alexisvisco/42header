package fr.ft.aviscogl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class Util
{
    public static boolean hasHeader(VirtualFile file)
    {
        try {
            String str = new String(file.getInputStream().readNBytes(3));

            System.out.println("has header" + (str.startsWith("/* ") || str.startsWith("# *")));
            return str.startsWith("/* ") || str.startsWith("# *");
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static Optional<Project> getActiveProject()
    {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                return Optional.of(project);
            }
        }
        return Optional.empty();
    }
}
