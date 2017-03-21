package foobar;

public class EnRouteSalute {

    public static void main(String[] args) {
        System.out.println(answer("<<>><"));
    }

    public static int answer(String salute) {
        int length = salute.length();
        int counter = 0;
        int result = 0;
        for (int i = 0; i < length; i++)
        {
            if (salute.charAt(i) == '-' )
                continue;
            else if (salute.charAt(i) == '>')
                counter++;
            else
                result += 2 * counter;
        }
        return result;
    }
}
