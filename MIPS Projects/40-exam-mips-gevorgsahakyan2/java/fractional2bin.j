public static int fractional2bin(int fractional, int max_bits) {
	int counter = 0;

	int max = value_of_max(fractional);
	int number = fractional;
	while (number != 0 && counter != max_bits) {
	    number = number * 2;
		if ( number >= max ) {
	       mips.print_d(1);
	       number = number - max;
	    } else {
	       mips.print_d(0);
	    }
	    counter++;
 	}
 	return 0;
}

public static int value_of_max(int number) {
	int max;

	max = 10;
	for (int i=0;  number >= max ;i++) {
		if( i > 8) break;
		max = max * 10;
	}
	return max;
}