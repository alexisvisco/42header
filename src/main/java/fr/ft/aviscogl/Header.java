package fr.ft.aviscogl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class Header
{
    public static String buildHeader(String file, boolean isMakefile)
    {
        String header = HeaderState.getHeader();

        header = maySetHeaderForMakefile(header, isMakefile);

        header = replaceValue(header, "{file}", file);

        header = replaceValue(header, "{author_and_email}",
                String.format("%s <%s>",
                        HeaderState.getAuthor(),
                        HeaderState.getEmail()
                )
        );

        header = replaceDateAndAuthor(header, false);
        header = replaceDateAndAuthor(header, true);

        return header;
    }

    public static Optional<String> buildUpdateLineHeader(boolean isMakefile)
    {
        return Stream.of(HeaderState.getHeader().split("\n"))
                .filter(e -> e.contains("{updated_date_and_author}"))
                .findFirst()
                .map(e -> {
                    e = maySetHeaderForMakefile(e, isMakefile);
                    System.out.println(e);
                    System.out.println(replaceDateAndAuthor(e, true));
                    System.out.println("----");
                    return replaceDateAndAuthor(e, true);
                });
    }

    private static String maySetHeaderForMakefile(String line, boolean isMakefile)
    {
        if (isMakefile) {
            line = line.replaceAll("/\\*", "#");
            line = line.replaceAll("\\*/", "#");
        }
        return line;
    }

    private static String replaceValue(String header, String template, String value)
    {
        if (value.length() < template.length()) {
            value += " ".repeat(template.length() - value.length());
        } else if (value.length() > template.length()) {
            template += " ".repeat(value.length() - template.length());
        }

        return header.replace(template, value);
    }

    private static String replaceDateAndAuthor(String text, boolean isUpdate)
    {
        String template = isUpdate ? "{updated_date_and_author}" : "{created_date_and_author}";

        return replaceValue(text, template,
                String.format("%s by %s",
                        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()),
                        HeaderState.getAuthor()
                )
        );
    }

    /**
     * @param isMakefile for recognize a header which start with '#' instead of '/*'
     * @return -1 if no {update_date_and_author} found
     */
    public static int getOffsetUpdatedLine(boolean isMakefile)
    {
        String header = maySetHeaderForMakefile(HeaderState.getHeader(), isMakefile);

        int offset = 0;
        for (String s : header.split("\n")) {
            if (s.contains("{updated_date_and_author}")) {
                return offset;
            }
            offset += s.length() + 1;
        }
        return -1;
    }
}
