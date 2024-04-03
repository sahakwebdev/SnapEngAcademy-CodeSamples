public static int fractional2bin(int fractional, int max_bits) {

        int counter;
        int max;
        int number;

        max = value_of_max(fractional);

init1:  ;
        counter = 0;
        number = fractional;
loop1:  while (number != 0 && counter != max_bits) {
body1:      ;
            number = number * 2;
initif:     ;
            if (number >= max) {
cons:           ;
                mips.print_d(1);
                number = number - max;
                // goto doneif;
            } else {
alt:            ;
                mips.print_d(0);
                // goto doneif;
            }
doneif:     ;
next1:      ;
            counter = counter + 1;
            continue loop1;
        }
done1:  ;
        return 0;
}

public static int value_of_max(int number) {

        int _8;
        int _10;

        int max;
        int i;
 
        _8  =  8;
        _10 = 10;
 
        max = 10;
        i   =  0;

loop2:  for (; number >= max ;) {
            if( i > _8) break loop2;
            max = max * _10;
            i++;
            continue loop2;
        }
done2:  ;
        return max;
}