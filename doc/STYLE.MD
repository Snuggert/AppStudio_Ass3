AppStudio_Ass3
==============
# Scrooge
![Image of RawDesign](https://raw.githubusercontent.com/Snuggert/AppStudio_Ass3/master/doc/design.jpg)

## Classes
* MainActivity
    * A google generated map drawn in opengl as a background.
    * provides with buttons to find the cheapest mode of taxi transport, from current position to new position.
![Image of RawDesign](https://raw.githubusercontent.com/Snuggert/AppStudio_Ass3/master/doc/main_design.jpg)
(To be later implemented is to move from a position that is not current.)
* CompareActivity
    * Activity in which the prices from tca and uber are compared
    * Parent is Main, so you can easily return to the map.
![Image of RawDesign](https://raw.githubusercontent.com/Snuggert/AppStudio_Ass3/master/doc/price_design.jpg)

Databases:
-------------
*  SharedPreferences API to support recent destinations saving.

# Style
Because i work a lot with python i tend to stick to the style guide i use with python whilst programming in other languages. The styleguide is PEP8. The rules can be read here: http://legacy.python.org/dev/peps/pep-0008/

Comments
--------
Comments van één regel worden als volgt gedaan:
```
/* Dit is een comment. */
```
Comments van meerdere regels worden anders gedaan:
```
/*
 * Dit is een comment.
 * Met meerdere regels.
 */
```
Braces
------
Braces worden geopend op dezelfde line en gesloten op een new line.
```
int voorbeeld(int a){
    return a;
}
```

Linebreaking
------------
When a line continues past the 80 character line, break off like this.
```
int voorbeeld(int a, int b, int c,
              int d){
    return a;
}
```
or if impossible/ugly double indent.
```
int voorbeeld(int a, int b, int c,
        int d){
    return a;
}
```

## APIS
* SharedPreferences API
