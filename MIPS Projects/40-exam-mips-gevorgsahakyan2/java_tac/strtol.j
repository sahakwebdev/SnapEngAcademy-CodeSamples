public static int strtol( char[] buffer, int radix) {

        int digit;
        int value;
        int i;
        int x;
    
        int negativeOne;
    
        negativeOne = -1;
        value = 0;

init:  ;
        i = 0;
        x = buffer[i];
    
loop1:  for (;x != '\0';) {
body1:      ;
            digit = glyph2int((char) x, radix);
            if (digit == negativeOne) break loop1;
            value = value * radix;
            value = value + digit;
next:       ;
            i = i + 1;
            x = buffer[i];
            continue loop1;
        }
done:   ;
        return value;
}

public static int glyph2int(char glyph, int radix){
        int value;
        value = -1;

init1:  ;
        if ('0' <= glyph) {
cons1:      ;
initA:      ;
            if (glyph <= '9') {
consA:          ;
                value = glyph - '0';
                // goto doneA;
            }
doneA:      ;
            // goto done1;
        }
done1:  ;
init2:  ;
        if ('a' <= glyph) {
cons2:      ;
initB:      ;
            if (glyph <= 'f') {
consB:          ;
                value = glyph - 'a';
                value = value + 10;
                // goto doneB;
            }
doneB:      ;
            // goto done2;
        }
done2:  ;
init3:  ;
        if ('A' <= glyph) {
cons3:      ;
initC:      ;
            if (glyph <= 'F') {
consC:          ;
                value = glyph - 'A';
                value = value + 10;
                // goto doneC;
            }
doneC:      ;
            // goto done3;
        }
done3:  ;
init4:  ;
        if (value >= radix) {
cons4:      ;
            value = -1;
            // goto done4;
        }
done4:  ;

        return value;
}