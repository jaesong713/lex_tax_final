## 1 ~ 4 
will be in the 1 ~ 4 folder within the lexer.java file
## 5 - 8 
Is in the word document.
## 9. 
Inside of the word document.
## 10. 
Will be in the number10 folder in a power point form!
## 11. Is in the word document.


## Grammar Rules
 - <start> -> `lecture` `<block> $<end>
 - <block> -> `{` <funky> `}`        $ indicating that the block should end with the end statement
 - <funky> -> `funky` [data_type] `id` `(` <data_type> <ident> [`,` <data_type> <ident>]`)` `{` <stmt>* `}`
 - <stmt> -> <assignment> | <ha_stmt> | <during_stmt> | <eq1> | <eq2>
 - <assignment> -> `$`<data_type> <ident>`;`
 - <ha_stmt> -> `ha` `(` <bool_expr> [(ehh | uhh) <bool_expr>] `)` <stmt>* [ `lol` <stmt>* ]      ha = if stmt
 - <during_stmt> -> `during` `(` <bool_expr> `)` <stmt>*  // might need to put muldiv mod first and then make ident go  down to ident some way
 - <eq1> -> `$$` <ident> { (* | - | = | ***) <ident> } `;` *** = exponentiation
 - <eq2> -> `$$$` <ident> { (+ | / | %) <ident> } `;`
 - <ident> -> id | oat_lit | ger_lit | char_lit | string_lit | <bool_value>
 - <factor> -> 'int_lit' | 'float_int'
 - <bool_expr> -> <b_eq>
 - <b_eq> -> [!]<b_fact> { ( `==` | `!=` | `<=` | `<` | `>=` | `>` ) <b_fact> }
 - <b_fact> -> `id` | `digit` | `letter`
 - <data_type> -> ger | oat | string | car | bool
 - <bool_value> -> 'true' | 'false'
 - <end> -> `hahaha`
