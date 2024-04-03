public static int whole2bin(int whole) {
	int counter = 0;
    int poppedElem;
    int remainder;

	int number = whole;
    while (number != 0) {
        remainder = number % 2;
    	mips.push(remainder);
        number = number / 2;
        counter++;
    }

    while (counter != 0) {
        poppedElem = mips.pop();
        mips.print_d(poppedElem);
        counter--;
    }
    return 1;
}
