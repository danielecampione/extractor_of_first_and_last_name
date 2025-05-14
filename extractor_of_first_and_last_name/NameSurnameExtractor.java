package extractor_of_first_and_last_name;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.util.Pair;

/**
 * Questa classe contiene la logica di business per l'estrazione di nomi e
 * cognomi.
 */
public class NameSurnameExtractor {

    private static final Set<String> ITALIAN_PREPOSITIONS_COMPLETE = new HashSet<>(
            Arrays.asList("di", "a", "da", "in", "con", "su", "per", "tra", "fra", "de", "del", "dello", "della", "dei",
                    "degli", "delle", "dell'", "al", "allo", "alla", "ai", "agli", "alle", "all'", "dal", "dallo",
                    "dalla", "dai", "dagli", "dalle", "dall'", "nel", "nello", "nella", "nei", "negli", "nelle",
                    "nell'", "sul", "sullo", "sulla", "sui", "sugli", "sulle", "sull'", "d'"));

    private static final List<String> ITALIAN_PREPOSITION_PREFIXES = Arrays.asList("dello", "della", "degli", "delle",
            "dell'", "allo", "alla", "agli", "alle", "all'", "dallo", "dalla", "dagli", "dalle", "dall'", "nello",
            "nella", "negli", "nelle", "nell'", "sullo", "sulla", "sugli", "sulle", "sull'", "col", "coi", "del", "dei",
            "al", "ai", "dal", "dai", "nel", "nei", "sul", "sui", "di", "da", "in", "su", "a", "de", "d'");

    static {
        ITALIAN_PREPOSITION_PREFIXES.sort(Comparator.comparingInt(String::length).reversed());
    }

    /**
     * Estrae il nome e il cognome da una stringa, gestendo le righe vuote.
     *
     * @param inputLine La stringa da analizzare.
     * @return Un Pair contenente il nome e il cognome. Se la riga è vuota, entrambi
     *         gli elementi del Pair saranno stringhe vuote.
     */
    public static Pair<String, String> extractNameAndSurname(String inputLine) {
        if (inputLine == null || inputLine.trim().isEmpty()) {
            return new Pair<>("", "");
        }
        String trimmedLine = inputLine.trim();
        String[] words = trimmedLine.split("\\s+");
        int n = words.length;

        if (n == 0) {
            return new Pair<>("[ERRORE]", "[ERRORE]");
        }

        if (n == 1) {
            return new Pair<>("", words[0]);
        }

        String surname;
        String name;

        String lastWord = words[n - 1];
        String penultimateWord = words[n - 2];

        if (wordIsAPreposition(penultimateWord)) {
            surname = penultimateWord + " " + lastWord;
            name = String.join(" ", Arrays.copyOfRange(words, 0, n - 2));
        } else if (wordStartsWithPreposition(lastWord)) {
            surname = lastWord;
            name = String.join(" ", Arrays.copyOfRange(words, 0, n - 1));
        } else {
            surname = lastWord;
            name = String.join(" ", Arrays.copyOfRange(words, 0, n - 1));
        }

        return new Pair<>(name.trim(), surname.trim());
    }

    private static boolean wordIsAPreposition(String word) {
        if (word == null || word.isEmpty())
            return false;
        return ITALIAN_PREPOSITIONS_COMPLETE.contains(word.toLowerCase());
    }

    private static boolean wordStartsWithPreposition(String word) {
        if (word == null || word.isEmpty())
            return false;
        String lowerWord = word.toLowerCase();
        for (String prefix : ITALIAN_PREPOSITION_PREFIXES) {
            if (lowerWord.startsWith(prefix)) {
                if (prefix.equals("d'")) {
                    return lowerWord.length() > 1 && Character.isLetter(lowerWord.charAt(1));
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Elabora una singola riga di input per estrarre nome e cognome.
     *
     * @param line       La riga di input da processare.
     * @param returnNomi Indica se restituire il nome (true) o il cognome (false).
     * @return La stringa contenente il nome o il cognome estratto, o una stringa
     *         vuota se la riga è vuota.
     */
    public static String processLine(String line, boolean returnNomi) {
        if (line == null) {
            return "";
        }
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return "";
        }
        Pair<String, String> extractedParts = extractNameAndSurname(trimmedLine); // Usa la funzione di estrazione
        if (returnNomi) {
            return extractedParts.getKey().isEmpty() ? "[NOME NON TROVATO]" : extractedParts.getKey();
        } else {
            return extractedParts.getValue().isEmpty() ? "[COGNOME NON TROVATO]" : extractedParts.getValue();
        }
    }
}
