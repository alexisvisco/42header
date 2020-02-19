package fr.ft.aviscogl.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import fr.ft.aviscogl.HeaderState;
import fr.ft.aviscogl.action.UpdateAllHeaders;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HeaderSettings implements SearchableConfigurable
{
    fr.ft.aviscogl.gui.SettingsGui settings;

    String originAuthor = "";
    String originEmail = "";
    String originHeader = "";

    @NotNull
    @Override
    public String getId()
    {
        return "preferences.42Header";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName()
    {
        return "42 Header";
    }

    @Nullable
    @Override
    public JComponent createComponent()
    {
        settings = new fr.ft.aviscogl.gui.SettingsGui();

        settings.email.setText(HeaderState.getEmail());
        settings.author.setText(HeaderState.getAuthor());
        settings.header.setText(HeaderState.getHeader());

        settings.updateAllHeaders.addActionListener(e -> {
            try {
                apply();
            } catch (ConfigurationException ex) {
                ex.printStackTrace();
                return;
            }
            UpdateAllHeaders.update();
        });

        setOriginValues();

        return settings.panel;
    }


    private void setOriginValues()
    {
        originAuthor = settings.author.getText();
        originEmail = settings.email.getText();
        originHeader = settings.header.getText();
    }

    @Override
    public boolean isModified()
    {
        if (settings == null) {
            return false;
        }
        boolean isModified = !(settings.email.getText().equals(originEmail) &&
                settings.author.getText().equals(originAuthor) &&
                settings.header.getText().equals(originHeader));

        settings.updateAllHeaders.setEnabled(isModified);

        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException
    {
        HeaderState.setHeader(settings.header.getText());
        HeaderState.setEmail(settings.email.getText());
        HeaderState.setAuthor(settings.author.getText());

        setOriginValues();
    }
}
