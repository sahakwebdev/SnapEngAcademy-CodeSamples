public static int strtol( char[] buffer, int radix) {

    int digit;
    int value;
    int i;
    int x;

    int negativeOne;

    negativeOne = -1;

    value = 0;
    
    for (i = 0, x = buffer[i]; x != '\0'; i++, x = buffer[i]) {
        digit = glyph2int((char) x, radix);

        if (digit == negativeOne) {
            break;
        }
        value = value * radix;
        value = value + digit;
        
        continue;
    }

    return value;
}

public static int glyph2int(char glyph, int radix){
    int value;
    value = -1;

    if ('0' <= glyph) {
        if (glyph <= '9') {
            value = glyph - '0';
        }
    }

    if ('a' <= glyph) {
        if (glyph <= 'f') {
            value = glyph - 'a';
            value = value + 10;
        }
    }

    if ('A' <= glyph) {
        if (glyph <= 'F') {
            value = glyph - 'A';
            value = value + 10;
        }
    }

    if (value >= radix) {
        value = -1;
    }

    return value;
}