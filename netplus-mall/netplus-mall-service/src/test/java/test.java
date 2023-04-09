import netplus.utils.date.DateUtil;

import java.util.Date;

public class test {

    public static void main(String[] ag) {
//        List<String> a = Arrays.asList("1", "2", "3","4");
//
//        System.out.println(a.subList(0, 1));
//        System.out.println(a.subList(0, 2));
//        System.out.println(a.subList(0, 3));
//        System.out.println(a.subList(0, 4));
//        System.out.println(a.size());
//
//
//        System.out.println( new Gson().fromJson("[\"2011\"]", ArrayList.class));
        int count =0;
        for (int i = 0; i < 4100; i=i+100) {
            count= count+i;
        }
        System.out.println(count);
    }

}
