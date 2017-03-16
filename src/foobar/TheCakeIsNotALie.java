package foobar;

public class TheCakeIsNotALie {

	public static void main(String[] args) {
		System.out.println(answer("abbcdaabbcda"));
	}
	
	public static int answer(String s) {
		int stringLength = s.length();
        // Go though half of the lenght of the sting 
        for (int substringlength = 1; substringlength <= stringLength / 2; substringlength++)
        {
            // Make sure that the string can be divided in equal parts otherwise skip
            if (stringLength % substringlength != 0)
            {
                continue;
            }
        	boolean comparison = true;
    		String substr = s.substring(0, substringlength);
    		for (int j = 1; j < stringLength / substringlength; j++)
    		{
    			String currentString = s.substring(j * substringlength , (j + 1) * substringlength);
    			if (!substr.equals(currentString))
    			{
    				comparison = false;
    				break;
    			}
    		}
    		if (comparison)
			{
				return stringLength / substringlength;
			}
        }
        // If the string can't be divided in equal parts less than half of its length than the entire string should be returned.
        return 1;
	}
}
