package fr.ft.aviscogl.event;

import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import fr.ft.aviscogl.action.UpdateHeaderEditLine;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnSave implements BulkFileListener
{

    @Override
    public void after(@NotNull List<? extends VFileEvent> events)
    {
        events.forEach(e -> {
            if (e.isFromSave() || e.isFromRefresh()) {
                UpdateHeaderEditLine.update(e.getFile());
            }
        });
    }

}
