package fr.ft.aviscogl;

import com.intellij.ide.util.PropertiesComponent;

public class HeaderState
{

    final private static String DEFAULT_HEADER =
            "/* ************************************************************************** */\n" +
                    "/*                                                                            */\n" +
                    "/*                                                        :::      ::::::::   */\n" +
                    "/*   {file}                                             :+:      :+:    :+:   */\n" +
                    "/*                                                    +:+ +:+         +:+     */\n" +
                    "/*   By: {author_and_email}                         +#+  +:+       +#+        */\n" +
                    "/*                                                +#+#+#+#+#+   +#+           */\n" +
                    "/*   Created: {created_date_and_author}                #+#    #+#             */\n" +
                    "/*   Updated: {updated_date_and_author}               ###   ########lyon.fr   */\n" +
                    "/*                                                                            */\n" +
                    "/* ************************************************************************** */\n";


    public static void setAuthor(String author)
    {
        PropertiesComponent.getInstance().setValue("42header_author", author);
    }

    public static void setEmail(String email)
    {
        PropertiesComponent.getInstance().setValue("42header_email", email);
    }

    public static void setHeader(String header)
    {
        PropertiesComponent.getInstance().setValue("42header_header", header);
    }

    public static String getAuthor()
    {
        if (!PropertiesComponent.getInstance().isValueSet("42header_author")) {
            setAuthor(System.getenv("USER"));
        }

        return PropertiesComponent.getInstance().getValue("42header_author");
    }

    public static String getEmail()
    {
        if (!PropertiesComponent.getInstance().isValueSet("42header_email")) {
            setEmail(System.getenv("USER") + "@student.le-101.fr");
        }

        return PropertiesComponent.getInstance().getValue("42header_email");
    }

    public static String getHeader()
    {
        if (!PropertiesComponent.getInstance().isValueSet("42header_header")) {
            setHeader(DEFAULT_HEADER);
        }

        return PropertiesComponent.getInstance().getValue("42header_header");
    }
}
