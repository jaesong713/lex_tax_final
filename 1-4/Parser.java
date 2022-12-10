import java.io.IOException;

/*
<start> -> `lecture` <block>
<block> -> `{` <stmt> `}` $<end>        $ indicating that the block should end with the end statement
<stmt> -> <assignment> | <looping> | <addminus> | <muldivmod>
<assignment> -> `$`<factor> <ident>`;`
<looping> -> <ha_stmt> | <during_stmt>
<addminus> -> `$$` <ident> { (+ | - | =) <ident> } `;`
<muldivmod> -> `$$$` <ident> { (* | / | %) <ident> } `;`
<ident> -> 'id' | 'digit' | 'letter'
<factor> -> 'int_lit' | 'float_int'
<ha_stmt> -> `ha` `(` <bool_expr> `)` <stmt> [ `lol` <stmt> ]      ha = if stmt
<during_stmt> -> `during` `(` <bool_expr> `)` <stmt>                during = while stmt
<bool_expr> -> <b_eq>
<b_eq> -> [!]<b_fact> { ( `==` | `!=` | `<=` | `<` | `>=` | `>` ) <b_fact> }
<b_fact> -> `id` | `digit` | `letter`
<end> -> `hahaha`
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

public class Parser extends Lexer {
    public static int currentToken;
    public static int nextToken;
    public static int i = 0;
    public static void main(String[] args) throws IOException {
        mapping();
        listing();
        currentToken = lexArr.get(i); 
        start(currentToken);
        while (i < lexArr.size()) {
            stmt();
            i++;
        }

    }
    public static void start(int token_Now) {
        System.out.println("Start of program: ");
        while (token_Now == myMap.get("START")) {
            block();
            if (token_Now == myMap.get("END")) {
                end();
            } else {
                error();
            }
        }
    }

    public static void block() {
        i++;
        currentToken = lexArr.get(i);
        // get the nextToken
        System.out.println("START <block>: ");
        if (currentToken == myMap.get("LEFT_CURLY")) {
            stmt();
        }
    }

    public static void stmt() {
        System.out.println("START <stmt>: ");
        i++;
        currentToken = lexArr.get(i);
        // get the next Token 
        switch (currentToken) {
            case 40: // if "ha" statement == "ha"?
                looping(currentToken);
            case 42: // while "during" statement == "during"?
                looping(currentToken);
            case 45: // assignment == "$"?
                assignment();
            case 46: // addition or subtraction = "$$"?
                addminus();
            case 47: // multiplication, division or modulus == "$$$"?
                muldivmod();
            default:
                error();
        }
        System.out.println("END <stmt>");
    }

    public static void assignment() {
        System.out.println("START <assignment>: ");
        factor();
        ident();
        if (nextToken == myMap.get("SEMICO")) {
            i++; 
            nextToken = lexArr.get(i);
        } else {
            error();
        }
        System.out.println("END <assignment>");
    }

    public static void looping(int currentToken) {
        // if current token is equal to ha or during
        // already detected before if statemnt was called to decide now, which loop to 
        // send to 
        if (currentToken == myMap.get("HA_STMT")) {
            ha_stmt();
        } else if (currentToken == myMap.get("DURING_STMT")) {
            during_stmt();
        } else {
            error();
        }
    }

    public static void addminus() {
        // already detected $$
        // if the nextToken is a ident
        System.out.println("START <addminus>: ");
        // is it going to be an identifier or digit?
        ident();
        // and then will the nextToken be an operator symbol?
        if (nextToken == myMap.get("ADD_OP")) {
            ident();
        } else if (nextToken == myMap.get("SUB_OP")) {
            ident();
        } else if (nextToken == myMap.get("ASSIGN_OP")) {
            ident();
        } else {
            error();
        }
        System.out.println("END <addminus>: ");
    }

    public static void muldivmod() {
        //already detected $$$
        System.out.println("START <muldivmod>: ");
        // will next token be an identifier? or digit?
        factor();
        // get next token and will it be an operator symbol
        if (nextToken == myMap.get("MULT_OP")) {
            factor();
        } else if (nextToken == myMap.get("DIV_OP")) {
            factor();
        } else if (nextToken == myMap.get("MODU")) {
            factor();
        } else {
            error();
        }
        System.out.println("END <muldivmod>");
    }

    public static void factor() {
        // already detected an operator || $ || $$ || $$$
        // will next token be an identifier?
        i++;
        nextToken = lexArr.get(i);
        System.out.println("START <factor>: ");
        if (nextToken == myMap.get("INT_LIT")) {
            
        } else if (nextToken == myMap.get("FLOAT_LIT")) {

        } else {
            error();
        }
        System.out.println("END <factor>");
    }

    public static void ident() {

    }
    public static void ha_stmt() {
        // ha is already detected
        // is next token a left parentheses?
        if (nextToken == myMap.get("LEFT_PAREN")) {
            bool_expr();
            // now is the next token a right parentheses?
            if (nextToken == myMap.get("RIGHT_PAREN")) {
                stmt();
                if (nextToken == myMap.get("ELSE_STMT")) {
                    stmt();
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    public static void during_stmt() {
        // during is already detected now go to
        // the next token
        if (nextToken == myMap.get("LEFT_PAREN")) {
            bool_expr();
            if (nextToken == myMap.get("RIGHT_PAREN")) {
                stmt();
            } else {
                error();
            }
        } else {
            error();
        }
    }

    public static void bool_expr() {
        System.out.println("START <bool_expr>: ");
        // left parentheses already detectd
        b_eq();
        System.out.println("END <bool_expr>");
    }

    public static void b_eq() {
        if (nextToken == myMap.get("NO")) {
            // if there is a ! must get next token if not it is ok to stay with current
            b_fact();
            // part to recognize the comparison operators
            // get next token
            switch(nextToken) {
                case 31:
                    b_fact();
                    break;
                case 32:
                    b_fact();
                    break;
                case 33:    
                    b_fact();
                    break;
                case 34:    
                    b_fact();
                    break;
                case 35:
                    b_fact();
                    break;
                case 36:
                    b_fact();
                    break;
                default: 
                    error();
                    break;
            }
        } else if (nextToken != myMap.get("NO")) {
            // stay with current token
            b_fact2();
            // part to recognize the comparison operators
            // get next token
            switch(nextToken) {
                case 31:
                    b_fact();
                    break;
                case 32:
                    b_fact();
                    break;
                case 33:    
                    b_fact();
                    break;
                case 34:    
                    b_fact();
                    break;
                case 35:
                    b_fact();
                    break;
                case 36:
                    b_fact();
                    break;
                default: 
                    error();
                    break;
            }
        } else {
            error();
        }
    }

    public static void b_fact() {
        // get nextToken
        System.out.println("START <factor>: ");
        if (nextToken == myMap.get("IDENT")) {
            // move on to the next token
        } else if (nextToken == myMap.get("DIGIT")) {
            // move on to the next token
        } else if (nextToken == myMap.get("LETTER")) {
            // move on to the next token
        } else {
            error();
        }
    }

    public static void b_fact2() {
        // stay with current token
        System.out.println("START <factor>: ");
        if (nextToken == myMap.get("IDENT")) {
            // move on to the next token
        } else if (nextToken == myMap.get("DIGIT")) {
            // move on to the next token
        } else if (nextToken == myMap.get("LETTER")) {
            // move on to the next token
        } else {
            error();
        }
    }

    public static void end() {

    }

    public static void error() {
        System.out.println("ERROR - there has been an error in your program.");
    }
}
