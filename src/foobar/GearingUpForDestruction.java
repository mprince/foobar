package foobar;

import java.lang.Math;

public class GearingUpForDestruction {

    public static void main(String[] args) {
        int[] pegs = {10, 19};
        int[] result = answer4(pegs);
        for (int i = 0; i < result.length; i++)
            System.out.println(result[i]);
    }

    public static int[] answer4(int[] pegs){
        // Solving the system of linear equations in term of x
        // where 2*x + a[0] = pegs[1]-pegs[0] 
        // and a[i] + a[i+1] = pegs[i+1] - pegs[i]
        // until a[last] + x = pegs[last] - pegs[last-1]
        int[] result = new int[2];
        int sumOfPegs = -pegs[0];
        for (int i = 1; i < pegs.length - 1; i++) {
            sumOfPegs += 2 * Math.pow(-1, i + 1 % 2) * pegs[i];
        }
        sumOfPegs += Math.pow(-1, pegs.length % 2) * pegs[pegs.length -1];

        // then sumOfPegs = -pegs[0] + 2*pegs[1] _ 2*pegs[2] + ... + (-1)^n*pegs[last]
        // if n is even with n is the number of pegs
        // then the solution of the system is sumOfPegs / 3 
        // otherwise it's just sumOfPegs
        if (pegs.length % 2 == 0) {
            if (sumOfPegs % 3 == 0) {
                result[0] = 2 * sumOfPegs / 3;
                result[1] = 1;
            }
            else {
                result[0] = 2 * sumOfPegs;
                result[1] = 3;
            } 
        }
        else {
            result[0] = 2 * sumOfPegs;
            result[1] = 1;
        }

        // Solutiuon can't be negative
        if (sumOfPegs <= 0)
            result[0] = result[1] = -1;
        else {	
            // Check no intermediate values are negative
            int x = sumOfPegs;
            if (pegs.length % 2 == 0)
                x = x/3;
            int[] a = new int[pegs.length-1];
            a[0] = pegs[1] - pegs[0] - 2 * x;
            if (a[0] <= 0)
                result[0] = result[1] = -1;
            for (int i = 1; i < pegs.length - 1; i++) {
                a[i] = pegs[i+1] - pegs[i] - a[i-1];
                if (a[i] <= 0) {
                    result[0] = result[1] = -1;
                    break;
                }
            }
        }
        return result;
    }

    public static int[] answer3(int[] pegs)
    {
        // Initialize result
        int[] result = {-1,-1};

        // Create radiusDiff array that represents distance between pegs
        int[] radiusDiff = new int [pegs.length -1];
        for (int i = 0; i < pegs.length - 1; i++)
            radiusDiff[i] = pegs[i+1] - pegs[i];

        if (pegs.length == 2) {
            if (radiusDiff[0] % 3 == 0) {
                result[0] = 2 * radiusDiff[0] / 3;
                result[1] = 1;
            }
            else {
                result[0] = 2 * radiusDiff[0];
                result[1] = 3;
            }
        }
        else {
            int x = 2;
            int[] a = new int[pegs.length -1];

            while (true) {
                a[0] = radiusDiff[0] - 2 * x;
                if (a[0] <= 0)
                    break;
                for (int i = 1; i < a.length; i++) {
                    a[i] = radiusDiff[i] - a[i-1];
                }
                if (a[a.length-1] == x) {
                    result[0] = 2*x;
                    result[1] = 1;
                    break;
                }
                else
                    x++;
            }
        }

        return result;
    }

    public static int[] answer(int[] pegs)
    {
        // Initialize result
        int[] result = {-1,-1};

        // Create radiusDiff array that represents distance between pegs
        int[] radiusDiff = new int [pegs.length -1];
        for (int i = 0; i < pegs.length - 1; i++)
            radiusDiff[i] = pegs[i+1] - pegs[i];

        // Initialize radius array.
        // If radius is negative then increase first radius or the radius just before?
        // Assume at beginning that negativeRadius is going to be false but then set it back to true if it happens and break from the loop
        int[] radius = new int [pegs.length];
        radius[0] = 2;
        // If the negative radius happens on odd index then it means that radius[0] should be decreased which makes finding a solution impossible
        boolean evenIndexOfNegativeRadius = true;
        for (int i = 1; i < pegs.length; i++) {
            radius[i] = radiusDiff[i-1] - radius[i-1];
            if (radius[i] <= 0 && i % 2 == 1) {
                evenIndexOfNegativeRadius = false;
                break;
            }
        }

        boolean negativeRadius = true;
        while(negativeRadius && evenIndexOfNegativeRadius) {
            negativeRadius = false;
            for (int i = 1; i < pegs.length; i++)
            {
                radius[i] = radiusDiff[i-1] - radius[i-1];
                if (radius[i] < 0)
                    negativeRadius = true;
            }
            if (negativeRadius)
                radius[0]++;
        }

        // Initialize boolean conditions
        boolean isRadiusZero = false;
        boolean isFirstDoubleLast = false;

        // If radius reaches 0 then we can't have a solution
        // Increase and decrease radiuses sequentially until you get 
        while (!isRadiusZero && !isFirstDoubleLast && evenIndexOfNegativeRadius)
        {
            for (int i = 0; i < pegs.length; i++) 
            {
                radius[i] += Math.pow(-1, i % 2);
                if (radius[i] == 0) {
                    isRadiusZero = true;
                    break;
                }
            }	
            if (radius[0] == 2 * radius[pegs.length - 1]) {
                isFirstDoubleLast = true;
                break;
            }
            // Might save time instead of keeping on increasing and decreasing until we get a 0 radius
            // if (radius[0] > 2 * radius[pegs.length - 1]) 
            // break;
        }

        if (isFirstDoubleLast)
        {
            result[0] = radius[0];
            result[1] = 1; //radius[pegs.length-1] if need to show the last one instead;
        }

        return result;
    }

    public static int[] answer2(int[] pegs)
    {
        // Initialize result
        int[] result = {-1,-1};

        // Create radiusDiff array that represents distance between pegs
        int[] radiusDiff = new int [pegs.length -1];
        for (int i = 0; i < pegs.length - 1; i++)
            radiusDiff[i] = pegs[i+1] - pegs[i];

        // Initialize radius array.
        // If radius is negative then increase first radius or the radius just before?
        // Assume at beginning that negativeRadius is going to be false but then set it back to true if it happens and break from the loop
        int[] radius = new int [pegs.length];
        radius[0] = 2;
        boolean negativeRadius = true;
        // If the negative radius happens on odd index then it means that radius[0] should be decreased which makes finding a solution impossible
        boolean oddIndexOfNegativeRadius = false;
        while(negativeRadius && !oddIndexOfNegativeRadius) {
            negativeRadius = false;
            for (int i = 1; i < pegs.length; i++)
            {
                radius[i] = radiusDiff[i-1] - radius[i-1];
                if (radius[i] <= 0)
                {
                    if (i % 2 == 1)
                    {
                        oddIndexOfNegativeRadius = true;
                        break;
                    }
                    else {
                        radius[0]++;
                        negativeRadius = true;;
                        break;
                    }
                }
            }
        }

        // Initialize boolean conditions
        boolean isRadiusZero = false;
        boolean isFirstDoubleLast = false;

        // If radius reaches 0 then we can't have a solution
        // Increase and decrease radiuses sequentially until you get 
        while (!isRadiusZero && !isFirstDoubleLast)
        {
            for (int i = 0; i < pegs.length; i++) 
            {
                radius[i] += Math.pow(-1, i % 2);
                if (radius[i] == 0) {
                    isRadiusZero = true;
                    break;
                }
            }	
            if (radius[0] == 2 * radius[pegs.length - 1]) {
                isFirstDoubleLast = true;
                break;
            }
            // Might save time instead of keeping on increasing and decreasing until we get a 0 radius
            // if (radius[0] > 2 * radius[pegs.length - 1])
            // break;
        }

        if (isFirstDoubleLast)
        {
            result[0] = radius[0];
            result[1] = 1; //radius[pegs.length-1] if need to show the last one instead;
        }

        return result;
    }
}