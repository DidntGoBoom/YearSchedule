public class SplitString
{
    static String[] stuffRA;
    static String stuff="'a,b'	c	d";
    
    static void main()
    {
        stuffRA = stuff.split("\t");
        for( String s : stuffRA )
        {
            System.out.println(s);
        }
    }
}