
/**
 * Write a description of class RecursiveAdd here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecursiveAdd
{
    static int doRecursiveAdd(int n, int m) 
    {
        if (m == 0)
        {
            return n; // base case
        }
        return doRecursiveAdd(n, m-1) + 1;
    }

}
