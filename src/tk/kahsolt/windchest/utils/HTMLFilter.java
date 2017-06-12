package tk.kahsolt.windchest.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kahsolt on 17-6-11.
 */
public class HTMLFilter {

    private static final Map<Character, String> EscapeDictionary;

    static {
        EscapeDictionary=new HashMap<Character, String>();
        EscapeDictionary.put('\n',"<br/>");
        EscapeDictionary.put('<',"&lt;");
        EscapeDictionary.put('>',"&gt;");
        EscapeDictionary.put('&',"&amp;");
        EscapeDictionary.put('"',"&quot;");
        EscapeDictionary.put('\'',"&apos;");
        EscapeDictionary.put(' ',"&nbsp;");
    }

    public static String HTMLEscape(String message) {
        if (message == null)
            return null;

        message=message.trim();
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);

        for (char c : content) {
            String ec=HTMLFilter.EscapeDictionary.get(c);
            result.append(ec!=null ? ec : c);
        }
        return result.toString();
    }
}
