package org.example.util;

/**
 * Convenience functions for working with Strings.
 * It was quicker to write a simple one-liner than find a library to do the same.
 *
 * You are welcome to add other convenience functions for strings here.
 */
public class Strings {
    /**
     * Change the first letter of a string to upper case.
     * @param string The string to transform
     * @return the string with the first letter capitalized
     */
    public static String capitaliseFirstLetter(String string) {
        return string.substring(0, 1)
                .toUpperCase() + string.substring(1);
    }

    public static String initialiseAuthor(String str)
    {
        int len = str.length();

        // to remove any leading or trailing spaces
        str = str.trim();

        // to store extracted words
        String letter = "";
        String t = "";
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);

            if (ch != ' ') {

                // forming the word
                t = t + ch;
            }

            // when space is encountered
            // it means the name is completed
            // and thereby extracted
            else {
                // printing the first letter
                // of the name in capital letters
                letter = (Character.toUpperCase(t.charAt(0))
                        + ". ");
                t = "";
            }
        }

        String l_name = "";

        // for the surname, we have to print the entire
        // surname and not just the initial
        // string "t" has the surname now
        for (int j = 0; j < t.length(); j++) {

            // first letter of surname in capital letter
            if (j == 0)
                l_name = l_name + Character.toUpperCase(t.charAt(0));

                // rest of the letters in small
            else
                l_name = l_name + Character.toLowerCase(t.charAt(j));
        }

        // printing surname
        return (letter +" "+ l_name);
    }
}
