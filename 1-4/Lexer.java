import java.util.*;
import java.io.*;

/*
 * Developed for a small and simple language that I have developed. 
 * This code will work for any file that is correctly formatted for the language that I have developed.
 */

 /* 
  * <start> -> `lecture` `<block> $<end>
  * <block> -> `{` <funky> `}`        $ indicating that the block should end with the end statement
  * <funky> -> `funky` [data_type] `id` `(` <data_type> <ident> [`,` <data_type> <ident>]`)` `{` <stmt>* `}`
  * <stmt> -> <assignment> | <ha_stmt> | <during_stmt> | <eq1> | <eq2>
  * <assignment> -> `$`<data_type> <ident>`;`
  * <ha_stmt> -> `ha` `(` <bool_expr> [(ehh | uhh) <bool_expr>] `)` <stmt>* [ `lol` <stmt>* ]      ha = if stmt
  * <during_stmt> -> `during` `(` <bool_expr> `)` <stmt>*  // might need to put muldiv mod first and then make ident go  down to ident some way
  * <eq1> -> `$$` <ident> { (* | - | = | ***) <ident> } `;` *** = exponentiation
  * <eq2> -> `$$$` <ident> { (+ | / | %) <ident> } `;`
  * <ident> -> 'id' | 'digit' | 'letter'
  * <factor> -> 'int_lit' | 'float_int'
  * <data_type> -> ger | oat
  * <bool_expr> -> <b_eq>
  * <b_eq> -> [!]<b_fact> { ( `==` | `!=` | `<=` | `<` | `>=` | `>` ) <b_fact> }
  * <b_fact> -> `id` | `digit` | `letter`
  * <end> -> `hahaha`
  */

  /* EDITTED
    <start> -> `lecture` `<block> $<end>
    <block> -> `{` <funky> `}`        $ indicating that the block should end with the end statement
    <funky> -> `funky` [data_type] `id` `(` <data_type> <ident> [`,` <data_type> <ident>]`)` `{` <stmt>* `}`
    <stmt> -> <assignment> | <ha_stmt> | <during_stmt> | <eq1> | <eq2>
    <assignment> -> `$`<data_type> <ident>`;`
    <ha_stmt> -> `ha` `(` <bool_expr> [(ehh | uhh) <bool_expr>] `)` <stmt>* [ `lol` <stmt>* ]      ha = if stmt
    <during_stmt> -> `during` `(` <bool_expr> `)` <stmt>*  // might need to put muldiv mod first and then make ident go  down to ident some way
    <eq1> -> `$$` <eq2> { (* | - | = | ***) <eq2> } `;` *** = exponentiation
    <eq2> -> `$$$` <ident> { (+ | / | %) <ident> } `;`
    <ident> -> id | oat | ger | char | string | <bool_value>
    <factor> -> 'int_lit' | 'float_int'
    <bool_expr> -> <b_eq>
    <b_eq> -> [!]<b_fact> { ( `==` | `!=` | `<=` | `<` | `>=` | `>` ) <b_fact> }
    <b_fact> -> `id` | `digit` | `letter`
    <data_type> -> ger | oat | string | car | bool
    <bool_value> -> 'true' | 'false'
    <end> -> `hahaha`
   */
class token {
    /* 
    String lexemes; // string for lexemes 
    int tokenCodes; // int for token codes
    */
    private String lexeme;
    private int tokenCode;

    public void Token(String lexeme, int tokenCode) {
        this.lexeme = lexeme;
        this.tokenCode = tokenCode;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public int getTokenCode() {
        return this.tokenCode;
    }
}

class Compiler {
    public static String fileToString(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        FileInputStream fstream = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line;
        
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();

        return sb.toString();
    }
}

public class Lexer {
    public static int token; // the token variable for current token
    public static int semiToken;
    public static int lexCount = 0; // this variable stands for the amount of lexemes that have been counted
    public static HashMap<String, Integer> myMap = new HashMap<String, Integer>(); // HashMap for all the tokens
    public static ArrayList<Character> arr = new ArrayList<Character>(); // array that the file reader will store into for comparison
    public static ArrayList<String> arr2 = new ArrayList<String>(); // array that will convert the already read file elements to string(s) for comparison
    public static String youTube;
    public static String youTube2;
    public static String anotherInString;
    public static char firstLet;
    public static char secondLet;
    public static char lastLet;
    public static int count1 = 0;
    public static ArrayList<Integer> lexArr = new ArrayList<Integer>(); // array for storing tokens for syntax analysis

    public static void main(String[] args) throws IOException {
        Lexer obj1 = new Lexer();
        
        mapping();
        listing();
    }
    /* listing(): method for reading in the file and storing all of the elements into a Character array */
    public static void listing() throws IOException {
        File f = new File("pass3.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        int c = 0;
        while ((c = br.read()) != -1) {
            char character = (char) c;
            arr.add(character);
        }
        formComparison();
        // System.out.print(arr);
    }
    // i to keep track of the index for the array "file" stored
    /* formComparison(): method for converting all of the elements within arr to a String to compare for lexeme count and token identification */
    public static void formComparison() throws IOException {
        ArrayList<Character> youtube = new ArrayList<Character>();
        StringBuilder result = new StringBuilder(youtube.size());

        for (int i = 0; i < arr.size(); i++) {
            // detecting a space character
            // it will only put the lexeme into the arr2 if it is rightfully formatted
            // hence, there should we spacing between each but not more than one space
            if (Character.isWhitespace(arr.get(i)) && !Character.isWhitespace(arr.get(i + 1))) {
                // while count1 less than i because it will add from arr to youtube array
                while (count1 < i) {
                    youtube.add(arr.get(count1));
                    count1++;
                }
                count1 = i + 1;
                // process of converting the characters to a string
                for (Character c : youtube) {
                    result.append(c);
                }
                String output = result.toString();
                arr2.add(output);
                youtube.clear(); // youtube the character is cleared because the next element will be stored into arr2
                result.setLength(0); // clearing the StringBuilder to reset what is going to be stored
            }
        }
        //System.out.println(arr2); prints out array to be compared
   
        // call start in syntax with the token array
        compareNow(arr2);
        //outWithSyntax();
        
        // bytheStringMan(arr2);
    }
    /* compareNow(): method for comparing the string for token identification along with lexeme count 
     * it will simply compare every single element that is in arr2. Taking in a String arraylist as a paremeter.
    */
    public static void compareNow(ArrayList<String> arr3) {
        for (int bread = 0; bread < arr3.size(); bread++) {
            youTube = arr3.get(bread);
            getChar(youTube);
        }
    }
    /*  
    public static void syntaxCompare(ArrayList<String> arr4) {
        for (int theRain = 0; theRain < arr4.size(); theRain++) {
            String kimchi = arr4.get(theRain);
            for (int jay = 0; jay < kimchi.length(); jay++) {
                char jayy = kimchi.charAt(jay);
               // wont print out this System.out.println(jayy);
            }
        }
        for (int bread2 = 0; bread2 < arr4.size(); bread2++) {
            youTube2 = arr4.get(bread2);
        }
    }
    */

    public static void outWithSyntax() {
        System.out.println(lexArr);
    }
    /* mapping(): this will list out all the tokens along with their respective token ID 
     * adding all to the myMap HashMap */
    public static void mapping() {
        myMap.put("LETTER", 0);         // letter
        myMap.put("DIGIT", 1);          // digit 
        myMap.put("UNKNOWN", 99);       // unknown input to look up to match
        myMap.put("NATTY_TYPE", 10);     // integer or natural lit
        myMap.put("REAL_TYPE", 11);      // real or float lit
        myMap.put("IDENT", 12);         // identifiers
        myMap.put("ASSIGN_OP", 20);     // assignment operator ( = )
        myMap.put("ADD_OP", 21);        // addition operator ( + )  
        myMap.put("SUB_OP", 22);        // subtraction operator ( - )
        myMap.put("MULT_OP", 23);       // multiplication operator ( * )
        myMap.put("DIV_OP", 24);        // division operator ( / )
        myMap.put("MODU", 25);          // modulus operator ( % )
        myMap.put("LEFT_PAREN", 26);    // left parentheses operator ( ( )
        myMap.put("RIGHT_PAREN", 27);   // right parenthese operator ( ) ) 
        myMap.put("LEFT_CURLY", 28);    // left curly brace ( { )
        myMap.put("RIGHT_CURLY", 29);   // right curly brace ( } )
        myMap.put("SEMICO", 30);        // semicolon ( ; )
        myMap.put("EQ", 31);            // equal operator ( == )
        myMap.put("NOT_EQ", 32);        // not equal operator ( != )
        myMap.put("LESS_EQ", 33);       // less than or equal to operator ( <=< )
        myMap.put("LESSTH", 34);        // less than operator ( < )
        myMap.put("GRE_EQ", 35);        // greater than or equal to operator ( >=> )
        myMap.put("GRE", 36);           // greather than operator ( > )
        myMap.put("NO", 37);            // not operator ( ! )
        myMap.put("START", 38);         // statement for start of program ( lecture )
        myMap.put("END", 39);           // statement for end of program ( hahaha )
        myMap.put("HA_STMT", 40);       // statement to start ha statement (equivalent to if statement) ( ha )
        myMap.put("ELSE_STMT", 41);     // else statement to start the equivalent of an else statement to an if ( lol )
        myMap.put("DURING_STMT", 42);   // statment to start during statement, equivalent to a while statement ( during )
        myMap.put("AAND", 43);          // statement to start aand statement equivalent to AND ( ehh )
        myMap.put("OOR", 44);           // statement to start oor statement equivalent to OR ( uhh )
        myMap.put("INITIALIZE", 45);    // statement to start initialization ( $ )
        myMap.put("ADD_SUB", 46);       // statement to start multiplication, sub or exponentiation equation ( $$ )
        myMap.put("MUL_DIV_MOD", 47);   // statement to start add, division or modulus ( $$$ )
        myMap.put("CHAR_TYPE", 48);     // declaration for character type
        myMap.put("HYPH", 49);          // recognizing hyphens
        myMap.put("EXPONEN", 50);       // exponentiation operator ( *** )
        myMap.put("NEGATIVE", 51);      // to determine a negative value ( nega )
        myMap.put("PARAM_SEP", 52);     // to separate parameters ( , )
        myMap.put("QUOTE", 53);         // quotation marks
        myMap.put("STRING_LIT", 54);    // string literal
        myMap.put("BOOLTYPE", 55);      // boolean lit declaration
        myMap.put("BOOL_TRUE", 56);     // boolean true value
        myMap.put("BOOL_FALSE", 57);    // boolean false value
        myMap.put("FUNKY", 58);         // required statement for function
        myMap.put("NATTY_LIT", 59);     // integer or natural lit
        myMap.put("REAL_LIT", 60);      // real or float lit
        myMap.put("STRING_TYPE", 61);   // string type declaration
        myMap.put("CHAR_LIT", 62);      // char literal 
    }
    /* forSyntax(): this will be the method for comparison for the syntax analysis */

    /* lookup(): method for comparison to assign token(s) for lexical analysis */
    public static int lookup(String inString) {
        char firstChar = inString.charAt(0);
        if (inString.equals("lecture")) {
            lexCount++;
            token = myMap.get("START");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("hahaha")) {
            lexCount++;
            token = myMap.get("END");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("ha")) {
            lexCount++;
            token = myMap.get("HA_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("during")) {
            lexCount++;
            token = myMap.get("DURING_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("ehh")) {
            lexCount++;
            token = myMap.get("AAND");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("uhh")) {
            lexCount++;
            token = myMap.get("OOR");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("lol")) {
            lexCount++;
            token = myMap.get("ELSE_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.charAt(0) == '$') {
            if (inString.charAt(1) == '$') {
                if (inString.charAt(2) == '$') {
                    lexCount++;
                    token = myMap.get("MUL_DIV_MOD");
                    lexArr.add(token);
                    System.out.println("Current token is: " + token);
                    System.out.println("Lexeme is: " + inString.substring(0, 3));
                    System.out.println("Your program has " + lexCount + " lexemes!");
                    if (Character.isDigit(inString.charAt(3))) {
                        lexCount++;
                        token = myMap.get("IDENT");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(3));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (Character.isAlphabetic(inString.charAt(3))) {
                        lexCount++;
                        token = myMap.get("DIGIT");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(3));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    }
                } else {
                    lexCount++;
                    token = myMap.get("ADD_SUB");
                    lexArr.add(token);
                    System.out.println("Current token is: " + token);
                    System.out.println("Lexeme is: " + inString.substring(0, 2));
                    System.out.println("Your program has " + lexCount + " lexemes!");
                    if (Character.isDigit(inString.charAt(2))) {
                        lexCount++;
                        token = myMap.get("IDENT");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(2));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (Character.isAlphabetic(inString.charAt(2))) {
                        lexCount++;
                        token = myMap.get("DIGIT");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(2));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    }
                }
            } else {
                    lexCount++;
                    token = myMap.get("INITIALIZE");
                    lexArr.add(token);
                    System.out.println("Current token is: " + token);
                    System.out.println("Lexeme is: " + inString.charAt(0));
                    System.out.println("Your program has " + lexCount + " lexemes!");
                    if (inString.charAt(1) == 'g') {
                        lexCount++;
                        token = myMap.get("NATTY_TYPE");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(1));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (inString.charAt(1) == 'o') {
                        lexCount++;
                        token = myMap.get("REAL_TYPE");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(1));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (inString.charAt(1) == 's') {
                        lexCount++;
                        token = myMap.get("STRING_TYPE");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(1));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (inString.charAt(1) == 'c') {
                        lexCount++;
                        token = myMap.get("CHAR_TYPE");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(1));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    } else if (inString.charAt(1) == 'b') {
                        lexCount++;
                        token = myMap.get("BOOLTYPE");
                        lexArr.add(token);
                        System.out.println("Current token is: " + token);
                        System.out.println("Lexeme is: " + inString.substring(1));
                        System.out.println("Your program has " + lexCount + " lexemes!");
                    }
                }
        } else if (inString.equals("!")) {
            lexCount++;
            token = myMap.get("NO");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("!=!")) { // editted to be more than two char
            lexCount++;
            token = myMap.get("NOT_EQ");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("==")) {
            lexCount++;
            token = myMap.get("EQ");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("<=<")) { // editted to be more than two char
            lexCount++;
            token = myMap.get("LESS_EQ");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("<<")) { // editted
            lexCount++;
            token = myMap.get("LESS");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals(">=>")) { // editted to be more than two char
            lexCount++;
            token = myMap.get("GRE_EQ");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals(">>")) { // editted
            lexCount++;
            token = myMap.get("GRE");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("(")) {
            lexCount++;
            token = myMap.get("LEFT_PAREN");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals(")")) {
            lexCount++;
            token = myMap.get("RIGHT_PAREN");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("=")) {
            lexCount++;
            token = myMap.get("ASSIGN_OP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("+")) {
            lexCount++;
            token = myMap.get("ADD_OP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("-")) {
            lexCount++;
            token = myMap.get("SUB_OP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("*")) {
            lexCount++;
            token = myMap.get("MULT_OP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("/")) {
            lexCount++;
            token = myMap.get("DIV_OP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("%")) {
            lexCount++;
            token = myMap.get("MODU");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.contentEquals("{")) {
            lexCount++;
            token = myMap.get("LEFT_CURLY");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("}")) {
            lexCount++;
            token = myMap.get("RIGHT_CURLY");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("funky")) {
            lexCount++;
            token = myMap.get("FUNKY");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } 
        else if (inString.equals(";")) {
            lexCount++;
            token = myMap.get("SEMICO");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("haha")) {
            lexCount++;
            token = myMap.get("HA_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("lol")) {
            lexCount++;
            token = myMap.get("ELSE_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("***")) {
            lexCount++;
            token = myMap.get("EXPONEN");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("during")) {
            lexCount++;
            token = myMap.get("DURING_STMT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("nega")) {
            lexCount++;
            token = myMap.get("NEGATIVE");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals(",")) {
            lexCount++;
            token = myMap.get("PARAM_SEP");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (inString.equals("\"")) {
            lexCount++;
            token = myMap.get("QUOTE");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString);
            System.out.println("Your program has " + lexCount + " lexemes!");
        }
        /* else if (inString.length() == 1) {
            if (Character.isAlphabetic(firstChar)) {
                lexCount++;
                token = myMap.get("CHAR_LIT");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + inString);
                System.out.println("Your program has " + lexCount + " lexemes!");
            }
        }
        */
        else {
            token = -1;
            System.out.println("There is an error in your program.");
        }
        return token;
    }
    /* getChar(): method for getting each character for comparison for token assignment */
    public static void getChar(String inString2) {
        firstLet = inString2.charAt(0);
        lastLet = inString2.charAt(inString2.length() - 1);
        if (Character.isAlphabetic(firstLet) && lastLet != ';') {
            lookup(inString2);
        } else if (Character.isAlphabetic(firstLet)) {
            semiLex(inString2);
        } else if (Character.isDigit(firstLet)) {
            semiLex3(inString2);
        } else if (Character.isAlphabetic(firstLet) && lastLet == ';') {
            semiLex(inString2);
        } else if (firstLet == '"' && lastLet == ';') {
            semiLex2(inString2);
        } else if (firstLet == '\'' && lastLet == ';') {
            semiLex2(inString2);
        } else {
            lookup(inString2);
        }
    }
    /* semiLex(): special method do detect if there is a semicolon within the code 
     * if semicolon is detected (1), then it will process it differently than if 
     * a semicolon is not detected (2). 
     * (1): it will process everything except for the semicolon because the semicolon must
     * be the last element within the string 
     * (2): it will process it as a regular string  
    */
    public static void semiLex(String inString5) {
        String inString4 = "";
        char lastsemi = inString5.charAt(inString5.length() - 1);
        if (lastsemi == ';') {
        for (int rain = 0; rain < inString5.length() - 1; rain++) {
            inString4 += inString5.charAt(rain);
        } 
            if (inString4.equals("true")) {
                lexCount++;
                token = myMap.get("BOOL_TRUE");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + inString4);
                System.out.println("Your program has " + lexCount + " lexemes!");
                lexCount++;
                inString4 = inString5.substring(inString5.length() - 1);
                token = myMap.get("SEMICO");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + lastsemi);
                System.out.println("Your program has " + lexCount + " lexemes!");
            } else if (inString4.equals("false")) {
                lexCount++;
                token = myMap.get("BOOL_FALSE");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + inString4);
                System.out.println("Your program has " + lexCount + " lexemes!");
                lexCount++;
                inString4 = inString5.substring(inString5.length() - 1);
                token = myMap.get("SEMICO");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + lastsemi);
                System.out.println("Your program has " + lexCount + " lexemes!");
            } else {
                lexCount++;
                token = myMap.get("IDENT");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + inString4);
                System.out.println("Your program has " + lexCount + " lexemes!");
                lexCount++;
                inString4 = inString5.substring(inString5.length() - 1);
                token = myMap.get("SEMICO");
                lexArr.add(token);
                System.out.println("Current token is: " + token);
                System.out.println("Lexeme is: " + lastsemi);
                System.out.println("Your program has " + lexCount + " lexemes!");
            }
        } else {
            lexCount++;
            token = myMap.get("IDENT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
        }
    }
    /* semiLex2(): special method to detect if there is a semicolon within the code
     * however, when it is a digit. Processing if a semicolon is detected and vacant. Hence,
     * (1): it will process everything except for the semicolon because the semicolon must
     * be the last element within the string/digit 
     * (2): it will process it as a regular string/digit  
     */
    public static void semiLex2(String inString5) {
        String inString4 = "";
        char firstsemi = inString5.charAt(0);
        char lastsemi = inString5.charAt(inString5.length() - 1);
        if (firstsemi == '"' && lastsemi == ';') {
        for (int rain = 1; rain < inString5.length() - 2; rain++) {
            inString4 += inString5.charAt(rain);
        } 
            lexCount++;
            token = myMap.get("QUOTE");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString5.charAt(0));
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            inString4 = inString5.substring(1, inString5.length() - 2);
            token = myMap.get("STRING_LIT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            token = myMap.get("QUOTE");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString5.charAt(inString5.length() - 2));
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            token = myMap.get("SEMICO");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + lastsemi);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else if (firstsemi == '\'' && lastsemi == ';' ) {
            lexCount++;
            token = myMap.get("HYPH");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString5.charAt(0));
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            inString4 = inString5.substring(1, inString5.length() - 2);
            token = myMap.get("CHAR_LIT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            token = myMap.get("HYPH");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString5.charAt(inString5.length() - 2));
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            token = myMap.get("SEMICO");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + lastsemi);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else {
            lexCount++;
            token = myMap.get("STRING_LIT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
        }
    }

    public static void semiLex3(String inString5) {
        String inString4 = "";
        char lastsemi = inString5.charAt(inString5.length() - 1);
        if (lastsemi == ';') {
        for (int rain = 0; rain < inString5.length() - 1; rain++) {
            inString4 += inString5.charAt(rain);
        } 
            lexCount++;
            token = myMap.get("DIGIT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
            lexCount++;
            inString4 = inString5.substring(inString5.length() - 1);
            token = myMap.get("SEMICO");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + lastsemi);
            System.out.println("Your program has " + lexCount + " lexemes!");
        } else {
            lexCount++;
            token = myMap.get("DIGIT");
            lexArr.add(token);
            System.out.println("Current token is: " + token);
            System.out.println("Lexeme is: " + inString4);
            System.out.println("Your program has " + lexCount + " lexemes!");
        }
    }

    /* getCharSyntax(): method for comparison for syntax analysis */
/* 
        secondLet = inString3.charAt(0);
        if (Character.isAlphabetic(firstLet)) {
            forSyntax(inString3);
        } else if (Character.isAlphabetic(firstLet)) {
            token = myMap.get("IDENT");
            lexArr.add(token);
        } else if (Character.isDigit(firstLet)) {
            token = myMap.get("DIGIT");
            lexArr.add(token);
        } else {
            forSyntax(inString3);
        }
        */
}

