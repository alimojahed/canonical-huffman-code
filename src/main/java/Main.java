public class Main {
    public static void main(String[] args) {
//        System.out.println(Integer.parseInt("110", 2) << 0);
        //printf("%*c", n, ' ');
//        System.out.println(String.format("%*s", 2, 0, "Hello"));
        int temp = 10;
        String s = "Hello";
//        System.out.println(String.format("%-"+(temp)+"d%s", 0, s));
        System.out.println((String.format("%-" + temp + "s", s)).replace(' ', '0'));
        char t = (char)127;
        byte b = (byte)((int)t);
        System.out.println(Integer.toBinaryString(b));
        System.out.println(b);
        System.out.println(Character.valueOf((char) b));
        String a = "111";
        String a2 = "0123456789abcdef";
        System.out.println(a2.substring(0, 8));
        System.out.println(a2.substring(8, 16));
        System.out.println(String.format("%-8s", a).replace(' ', '0'));
        b = 8;
        System.out.println(Integer.toBinaryString(8));
    }
}
