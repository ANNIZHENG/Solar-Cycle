public class Test {
    static final int year = 2000;
    public static void main(String[] args){
        String value = "\n";
        System.out.println(Character.isDigit(value.toString().charAt(1)));
        if (value.toString().contains("\t")){ // for files with 'modif'
            String[] values = value.toString().split("\t");
            String day = values[0];
            int index = 1;
            int month = 1;
            while (index < values.length){
                System.out.println(year + "," + month + "," + day + "," + values[index].trim());
                index += 1;
                month += 1;
            }
        }
    }
}